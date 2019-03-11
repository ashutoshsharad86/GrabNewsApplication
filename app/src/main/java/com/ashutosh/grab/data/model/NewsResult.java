package com.ashutosh.grab.data.model;

import java.util.List;

public class NewsResult {

    public final String status;
    public final int totalResults;
    public final List<Article> articles;

    public NewsResult(String status, int totalResults,
            List<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Article article: articles) {
            builder.append(article.title);
        }
        return builder.toString();
    }
}
