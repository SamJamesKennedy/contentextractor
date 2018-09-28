package com.samjameskennedy.contentextractor.extractor;

import org.jsoup.nodes.Element;

public class URLExtractor {

    public static String extractUrl(Element comment) {
        Element commentHeader = comment.select("div>a[id^=CommentTopMeta]").first();
        return "www.reddit.com" + commentHeader.attr("href");
    }
}
