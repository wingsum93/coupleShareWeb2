package com.ericho.coupleShare.service;

import com.ericho.coupleShare.model.Transaction;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created by steve_000 on 15/4/2017.
 */
public interface TransactionService {

    List<Transaction> findAll();

    List<Transaction> findByCustomerId(Long id);

    List<Transaction> findByFarmerId(Long id);

    List<Transaction> findBySpec(Specification<Transaction> spec);

}
