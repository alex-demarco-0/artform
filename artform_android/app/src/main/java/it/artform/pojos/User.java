package it.artform.pojos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("nome")
    private String name;
    @SerializedName("cognome")
    private String surname;
    private String username /* server DB PK */, email;
    @SerializedName("numeroTelefono")
    private String phone;
    private String password;
    private String bio;
    @SerializedName("punteggio")
    private int points;

    public User(String name, String surname, String username, String email, String phone, String password, String bio, int points) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.bio = bio;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void increasePoints(int points) {
        this.points += points;
    }

    @Override
    public String toString() {
        return name + ", " + surname + ", " + username + ", " + email + ", " + phone + ", " + password + ", " + bio + ", " + points;
    }

}
