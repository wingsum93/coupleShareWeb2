package com.ericho.coupleShare.dao;

import com.ericho.coupleShare.model.ProductQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by steve_000 on 15/4/2017.
 */
public interface ProductDetailDao extends JpaRepository<ProductQuantity, Long> {

}
