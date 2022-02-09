package it.artform.pojos;

public class User {
    private String name, surname, username, email, phone, password;
    private int points;

    public User(String name, String surname, String username, String email, String phone, String password, int points) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.points = points;
    }

    @Override
    public String toString() {
        return name + ", " + surname + ", " + username + ", " + phone + ", " + password + ", " + points;
    }

}
