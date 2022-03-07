package it.artform.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Commission {
    @SerializedName("Id")
    private final int id; // server DB PK
    @SerializedName("titolo")
    private final String title;
    @SerializedName("prezzo")
    private final double price;
    @SerializedName("descrizione")
    private final String description;
    @SerializedName("data")
    private final Date date;
    @SerializedName("dataTermine")
    private final Date endDate;
    @SerializedName("artistaUsername")
    private final String artistUsername;
    @SerializedName("clienteUsername")
    private final String customerUsername;
    @SerializedName("indirizzoConto")
    private final String accountAddress;

    public Commission(int id, String title, double price, String description, Date date, Date endDate, String artistUsername, String customerUsername, String accountAddress) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.date = date;
        this.endDate = endDate;
        this.artistUsername = artistUsername;
        this.customerUsername = customerUsername;
        this.accountAddress = accountAddress;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getArtist() {
        return artistUsername;
    }

    public String getCustomer() {
        return customerUsername;
    }

    public String getAccountAddress() {
        return accountAddress;
    }

}
