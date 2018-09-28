package com.samjameskennedy.contentextractor.helpers;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class URLConverter {

    public static Document toDocument(URL url) throws IOException {
        return Jsoup.connect(url.toString()).get();
    }

}
