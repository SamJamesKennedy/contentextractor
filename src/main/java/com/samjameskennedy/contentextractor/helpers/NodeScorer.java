package com.samjameskennedy.contentextractor.helpers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

public class NodeScorer {

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
