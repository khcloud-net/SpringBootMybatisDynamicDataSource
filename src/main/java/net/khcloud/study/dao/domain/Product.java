package net.khcloud.study.dao.domain;

import java.io.Serializable;

/**
 * Created by GBC on 2017/10/20.
 */
public class Product implements Serializable {
    private static final long serialVersionUID = -157669836361594225L;

    private long id;
    private String name;
    private long price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
