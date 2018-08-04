package com.example.android.filmesfamosos.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Review {

    @SerializedName("id")
    private String id;
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }


    public static class ReviewResult {
        private List<Review> results;

        public List<Review> getResults() {
            return results;
        }
    }
}