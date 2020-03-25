package com.ericho.coupleShare.service;

import com.ericho.coupleShare.dao.TransactionDao;
import com.ericho.coupleShare.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by steve_000 on 15/4/2017.
 */
@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionDao transactionDao;

    @Override
    public List<Transaction> findAll() {
        return transactionDao.findAll();
    }

    @Override
    public List<Transaction> findByCustomerId(Long id) {
        return null;
    }

    @Override
    public List<Transaction> findByFarmerId(Long id) {
        return null;
    }

    @Override
    public List<Transaction> findBySpec(Specification<Transaction> spec) {
        return null;
    }
}
