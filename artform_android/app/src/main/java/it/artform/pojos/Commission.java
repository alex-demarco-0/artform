package it.artform.pojos;

import java.util.Date;

public class Commission {
    private final String title, accountAddress;
    private final double price;
    private final Date date;
    private final long artistId, customerId;

    public Commission(String title, double price, Date date, long artistId, long customerId, String accountAddress) {
        this.title = title;
        this.price = price;
        this.date = new Date();
        this.artistId = artistId;
        this.customerId = customerId;
        this.accountAddress = accountAddress;
    }
}
