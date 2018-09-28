package com.samjameskennedy.contentextractor.extractor;

import java.util.Objects;

import org.jsoup.nodes.Document;

public class TitleExtractor {

    public static String extractTitle(Document document) {
        String title = document.title();
        if (Objects.nonNull(title)) {
            return title;
        }
        title = document.select("head title").text();
        if (Objects.nonNull(title)) {
            return title;
        }
        title = document.select("head meta[name=title]").attr("content");
        if (Objects.nonNull(title)) {
            return title;
        }
        title = document.select("head meta[property=og:title]").attr("content");
        return title;
    }

}
