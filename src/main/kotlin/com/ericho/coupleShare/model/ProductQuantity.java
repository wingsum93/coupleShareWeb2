package com.ericho.coupleShare.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by steve_000 on 14/4/2017.
 */
@Entity(name = "products_q")
public class ProductQuantity {
    @Id
    @GeneratedValue
    private Long product_id;
    @Column(name = "quantity")
    private String quantity;
    @Column(name = "p_date")
    private Date stockInDate;

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Date getStockInDate() {
        return stockInDate;
    }

    public void setStockInDate(Date stockInDate) {
        this.stockInDate = stockInDate;
    }
}
