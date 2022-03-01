package it.artform.pojos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Topic implements Serializable {
    @SerializedName("nome")
    private final String name;

    public Topic(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
