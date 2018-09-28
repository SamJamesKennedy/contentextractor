package com.samjameskennedy.contentextractor;

import static java.lang.System.out;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.samjameskennedy.contentextractor.extractor.CommentExtractor;
import com.samjameskennedy.contentextractor.helpers.URLConverter;
import com.samjameskennedy.contentextractor.helpers.ContentExtractorHelper;
import com.samjameskennedy.contentextractor.mention.ExtractedContent;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ContentExtractorApplication {

    public static void main(String[] args) {
        List<ExtractedContent> content = ContentExtractorApplication.getUrls()
                .map(ContentExtractorApplication::convertToDocument)
                .map(ContentExtractorHelper::getComments)
                .flatMap(Set::stream)
                .map(ContentExtractorApplication::extractContent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        content.forEach(out::println);

        try {
            writeToCsv(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToCsv(List<ExtractedContent> content) throws IOException {
        try (Writer writer = new FileWriter("content.csv")) {
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
            beanToCsv.write(content);
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }

    public static Document convertToDocument(URL url) {
        Document document;
        try {
            document = URLConverter.toDocument(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return document;
    }

    public static ExtractedContent extractContent(Element comment) {
        ExtractedContent content = CommentExtractor.extractComment(comment);
        return content;
    }

    public static Stream<URL> getUrls() {
        try {
            Stream<String> urls = Files.lines(Paths.get(ClassLoader.getSystemResource("urls.txt").toURI()));
            return urls
                .peek(out::println)
                .map(spec -> {
                try {
                    return new URL(spec);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return null;
                }
            }).filter(Objects::nonNull);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }
}
