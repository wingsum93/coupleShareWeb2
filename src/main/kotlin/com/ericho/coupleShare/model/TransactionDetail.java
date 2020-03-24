package com.ericho.coupleShare.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by steve_000 on 15/4/2017.
 */
@Entity(name = "trans_details")
public class TransactionDetail {
    @Id
    @Column(name = "tid")
    private Long id;
    @Column(name = "p_id")
    private Long product_id;

    @Column(name = "pq")
    private String priduct_quantity;
    @Column(name = "actual_quantity")
    private Integer actualQuantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getPriduct_quantity() {
        return priduct_quantity;
    }

    public void setPriduct_quantity(String priduct_quantity) {
        this.priduct_quantity = priduct_quantity;
    }

    public Integer getActualQuantity() {
        return actualQuantity;
    }

    public void setActualQuantity(Integer actualQuantity) {
        this.actualQuantity = actualQuantity;
    }
}
