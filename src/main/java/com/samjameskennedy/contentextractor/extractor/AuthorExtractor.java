package com.samjameskennedy.contentextractor.extractor;

import org.jsoup.nodes.Element;

public class AuthorExtractor {

    public static String extractAuthor(Element comment) {
        return comment.select("a[href^=/user/").text();
    }

}
