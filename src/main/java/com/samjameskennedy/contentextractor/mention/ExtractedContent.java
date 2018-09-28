package com.samjameskennedy.contentextractor.mention;

import java.util.Date;

import com.opencsv.bean.CsvBindByName;

public class ExtractedContent {

    @CsvBindByName
    private String url;
    @CsvBindByName
    private String title;
    @CsvBindByName
    private String content;
    @CsvBindByName
    private String author;
    @CsvBindByName
    private Date publishedDate;

    public ExtractedContent() {

    }

    public ExtractedContent(String url, String title, String content, String author, Date publishedDate) {
        this.url = url;
        this.title = title;
        this.content = content;
        this.author = author;
        this.publishedDate = publishedDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Override
    public String toString() {
        return "ExtractedContent{" + "url='" + url + '\'' + ", title='" + title + '\''
                + ", content='" + content + '\'' + ", author='" + author + '\'' + ", publishedDate="
                + publishedDate + '}';
    }
}
