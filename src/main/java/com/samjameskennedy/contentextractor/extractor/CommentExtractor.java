package com.samjameskennedy.contentextractor.extractor;

import com.samjameskennedy.contentextractor.mention.ExtractedContent;
import org.jsoup.nodes.Element;

public class CommentExtractor {

    public static ExtractedContent extractComment(Element comment) {
        return new ExtractedContent(
                URLExtractor.extractUrl(comment),
                comment.attr("THREAD-TITLE"),
                ContentExtractor.extractContent(comment),
                AuthorExtractor.extractAuthor(comment),
                DateExtractor.extractDate(comment)
        );
    }
}
