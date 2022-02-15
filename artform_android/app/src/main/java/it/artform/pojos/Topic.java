package it.artform.pojos;

import com.google.gson.annotations.SerializedName;

public class Topic {
    @SerializedName("nome")
    private final String name;

    public Topic(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
