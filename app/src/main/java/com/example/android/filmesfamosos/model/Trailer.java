package com.example.android.filmesfamosos.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Trailer {

    @SerializedName("id")
    private String id;
    @SerializedName("key")
    private String key;
    @SerializedName("name")
    private String name;
    @SerializedName("site")
    private String site;
    @SerializedName("type")
    private String type;

    public Trailer() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public static class TrailerResult {
        private List<Trailer> results;

        public List<Trailer> getResults() {
            return results;
        }
    }
}