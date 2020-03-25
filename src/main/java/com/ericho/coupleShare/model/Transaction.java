package com.ericho.coupleShare.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by steve_000 on 15/4/2017.
 */
@Entity(name = "trans")
public class Transaction {
    @Id
    @Column(name = "tid")
    private Long id;
    @Column(name = "t_date")
    private Date date;

    @Column(name = "t_status")
    private String status;
    @Column(name = "t_total")
    private Integer total;
    @Column(name = "f_id")
    private Long farmId;

    @Column(name = "c_id")
    private Long customerId;
    @Column(name = "final_total")
    private Long finalTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getFarmId() {
        return farmId;
    }

    public void setFarmId(Long farmId) {
        this.farmId = farmId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(Long finalTotal) {
        this.finalTotal = finalTotal;
    }
}
