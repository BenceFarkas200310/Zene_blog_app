package com.example.zene_blog_app.models;

public class BlogPost {
    private String id, title, content, authorId;
    private long timestamp;

    public BlogPost() {
        // Firestore számára szükséges üres konstruktor
    }

    public BlogPost(String id, String title, String content, String authorId, long timestamp) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.timestamp = timestamp;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getAuthorId() { return authorId; }
    public void setAuthorId(String authorId) { this.authorId = authorId; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}