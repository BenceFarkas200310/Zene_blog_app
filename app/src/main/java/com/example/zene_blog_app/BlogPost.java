
package com.example.zene_blog_app;

import java.util.ArrayList;
import java.util.List;

public class BlogPost {
    private String id;
    private String title;
    private String content;
    private String authorId;
    private long timestamp;

    private String category;
    private boolean published;
    private List<String> tags;

    public BlogPost() {}

    public BlogPost(String id, String title, String content, String authorId, long timestamp,
                    String category, boolean published, List<String> tags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.timestamp = timestamp;
        this.category = category;
        this.published = published;
        this.tags = tags;
    }

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

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public boolean isPublished() { return published; }
    public void setPublished(boolean published) { this.published = published; }

    public List<String> getTags() {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}