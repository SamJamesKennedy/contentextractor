package com.samjameskennedy.contentextractor.tree;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TreeSimplifier {

    public static Document simplifyDomTree(Document inputTree) {
        cleanUpUselessTags(inputTree);
        removeAdverts(inputTree);
        return inputTree;
    }

    private static void cleanUpUselessTags(Document document) {
        Elements comments = document.getElementsByTag("#comment");
        for (Element item : comments) {
            item.remove();
        }

        Elements scripts = document.getElementsByTag("script");
        for (Element item : scripts) {
            item.remove();
        }

        Elements noscripts = document.getElementsByTag("noscript");
        for (Element item : noscripts) {
            item.remove();
        }

        Elements styles = document.getElementsByTag("style");
        for (Element item : styles) {
            item.remove();
        }

        Elements forms = document.getElementsByTag("form");
        for (Element item : forms) {
            item.remove();
        }
    }

    private static void removeAdverts(Document document) {
        Elements adverts = document.select("div:containsOwn(advert)");
        for (Element item : adverts) {
            item.remove();
        }
        Elements ads = document.select("div:containsOwn(ad)");
        for (Element item : ads) {
            item.remove();
        }
    }

}
