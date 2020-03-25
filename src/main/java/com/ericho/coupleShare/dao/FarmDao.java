package com.ericho.coupleShare.dao;

import com.ericho.coupleShare.model.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface FarmDao extends JpaRepository<Farm, Long> {

}
