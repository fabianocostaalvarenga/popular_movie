package com.udacity.filmesfamosos.model;

import java.io.Serializable;

/**
 * Created by fabiano.alvarenga on 17/02/18.
 */

public class ReviewModel implements Serializable {

    private String id;

    private String author;

    private String content;

    private String url;

    public ReviewModel(String id, String author, String content, String url) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
