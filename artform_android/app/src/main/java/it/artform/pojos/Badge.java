package it.artform.pojos;

import com.google.gson.annotations.SerializedName;

public class Badge {
    @SerializedName("nome")
    private final String name; // server DB PK
    @SerializedName("descrizione")
    private final String description;
    @SerializedName("punteggio")
    private final int points;

    public Badge(String name, String description, int points) {
        this.name = name;
        this.description = description;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPoints() {
        return points;
    }
}
