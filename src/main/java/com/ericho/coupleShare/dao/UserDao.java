package com.ericho.coupleShare.dao;

import com.ericho.coupleShare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserDao extends JpaRepository<User, Long> ,JpaSpecificationExecutor<User>{
    User findByUsername(String name);
}
