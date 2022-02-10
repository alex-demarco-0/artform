package it.artform.pojos;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("nome")
    private String name;
    @SerializedName("cognome")
    private String surname;
    private String username, email;
    @SerializedName("numeroTelefono")
    private String phone;
    private String password;
    @SerializedName("punteggio")
    private int points;

    public User(String name, String surname, String username, String email, String phone, String password, int points) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.points = points;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return name + ", " + surname + ", " + username + ", " + email + ", " + phone + ", " + password + ", " + points;
    }

}
