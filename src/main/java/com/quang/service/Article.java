package com.quang.service;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/**
 * @author Vu Ngoc Quang
 */
public class Article {

    private String link;
    private String headline;

    @JsonFormat(pattern = "MMM dd, yyyy")
    private LocalDate publishedDate;
    private String body;

    public Article(String link, String headline, LocalDate publishedDate, String body) {
        this.link = link;
        this.headline = headline;
        this.publishedDate = publishedDate;
        this.body = body;
    }

    public String getLink() {
        return link;
    }

    public String getHeadline() {
        return headline;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public String getBody() {
        return body;
    }

    public String toString() {
        return "Link: " + this.link
                + "\nHeadline: " + this.headline
                + "\nPublished Date: " + this.publishedDate.toString()
                + "\nBody: " + this.body;
    }
}
