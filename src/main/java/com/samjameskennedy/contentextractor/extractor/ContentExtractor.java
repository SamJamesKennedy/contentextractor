package com.samjameskennedy.contentextractor.extractor;

import java.util.Set;

import com.samjameskennedy.contentextractor.helpers.ContentExtractorHelper;
import com.samjameskennedy.contentextractor.helpers.NodeScorer;
import org.jsoup.nodes.Element;

public class ContentExtractor {

    public static String extractContent(Element comment) {
        return getContent(comment);
    }

    private static String getContent(Element comment) {
        int maxScore = 0;
        Element bestCandidate = null;

        Set<Element> nodes = ContentExtractorHelper.getImportantNodes(comment);
        for (Element node : nodes) {
            int score = NodeScorer.scoreNode(node);
            if (score > maxScore) {
                maxScore = score;
                bestCandidate = node;
            }
        }
        if (bestCandidate == null) {
            return null;
        }
        return bestCandidate.text();
    }
}
