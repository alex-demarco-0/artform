package it.artform.pojos;

public class Badge {
    private final String name, description;
    private final int points;
    private final long userId;

    public Badge(String name, String description, int points, long userId){
        this.name = name;
        this.description = description;
        this.points = points;
        this.userId = userId;
    }
}
