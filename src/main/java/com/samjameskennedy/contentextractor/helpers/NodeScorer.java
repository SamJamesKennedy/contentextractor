package com.samjameskennedy.contentextractor.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

public class NodeScorer {

    private static final Pattern UNLIKELY_CSS_CLASSES_AND_IDS =
            Pattern.compile("com(bx|ment|munity)|dis(qus|cuss)|e(xtra|[-]?mail)|foot|"
                    + "header|menu|re(mark|ply)|rss|sh(are|outbox)|sponsor"
                    + "a(d|ll|gegate|rchive|ttachment)|(pag(er|ination))|popup|print|"
                    + "login|si(debar|gn|ngle)|facebook|twitter|email");

    private static final Pattern POSITIVE_CSS_CLASSES_AND_IDS =
            Pattern.compile("(^(body|content|h?entry|main|page|post|text|blog|story|haupt))"
                    + "|arti(cle|kel)|instapaper_body");

    public static final Pattern NEGATIVE_CSS_CLASSES_AND_IDS =
            Pattern.compile("nav($|igation)|user|com(ment|bx)|(^com-)|contact|"
                    + "foot|masthead|(me(dia|ta))|outbrain|promo|related|scroll|(sho(utbox|pping))|"
                    + "sidebar|sponsor|tags|tool|widget|player|disclaimer|toc|infobox|vcard|post-ratings");

    private static final Pattern NEGATIVE_CSS_STYLES =
            Pattern.compile("hidden|display: ?none|font-size: ?small");

    private static final Weights WEIGHTS = new Weights();

    public static int scoreNode(Element element) {
        int score = calculateScore(element);
        score += scoreChildElements(element, score);
        return score;
    }

    private static int calculateScore(Element element) {
        int score = 0;
        if (element.tagName().equalsIgnoreCase("p")) score += 50;
        return score;
    }

    private static int scoreChildElements(Element element, int score) {
        List<Element> pEls = new ArrayList<>(5);
        for (Element child : element.children()) {
            String ownText = child.ownText();

            // if you are on a paragraph, grab all the text including that surrounded by additional formatting.
            if (child.tagName().equals("p"))
                ownText = child.text();

            int ownTextLength = ownText.length();

            if (ownTextLength > 10)
                score += Math.max(50, ownTextLength / 10);

            if (child.tagName().equals("li")) {
                score += 30;
            } else if (child.tagName().equals("div") || child.tagName().equals("p")) {
                score += calcWeightForChild(ownText);
                if (child.tagName().equals("p") && ownTextLength > 1)
                    pEls.add(child);
            }
        }
        return score;
    }

    private static int calcWeightForChild(String ownText) {
        int c = StringUtils.countMatches(ownText, "&quot;");
        c += StringUtils.countMatches(ownText, "&lt;");
        c += StringUtils.countMatches(ownText, "&gt;");
        c += StringUtils.countMatches(ownText, "px");
        int val;
        if (c > 5) {
            val = -30;
        } else {
            val = (int) Math.round(ownText.length() / 25.0);
        }
        return val;
    }
}
