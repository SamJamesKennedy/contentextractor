package com.samjameskennedy.contentextractor.helpers;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.samjameskennedy.contentextractor.extractor.TitleExtractor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ContentExtractorHelper {
    private static final Pattern IMPORTANT_NODES =
            Pattern.compile("p|div");
    private static final Predicate<Element> isElementImportant
            = (el -> IMPORTANT_NODES.matcher(el.tagName()).matches());

    public static Set<Element> getComments(Document document) {
        Elements comments = document.select("div[class^=comment]");
        comments.forEach(e -> e.attr("THREAD-TITLE", TitleExtractor.extractTitle(document)));
        return new HashSet<>(comments);
    }

    public static Set<Element> getImportantNodes(Element comment) {
        return comment.select("*").stream()
                .filter(isElementImportant)
                .collect(Collectors.toSet());
    }
}
