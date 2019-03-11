package com.ashutosh.grab.data.model;

public class Article {

    public final Source source;
    public final String author;
    public final String title;
    public final String description;
    public final String url;
    public final String urlToImage;
    public final String publishedAt;
    public final String content;

    public Article(Source source, String author, String title, String description, String url,
            String urlToImage, String publishedAt, String content) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }
}
