package com.sachet.springmultithreadedweb.repository;

import com.sachet.springmultithreadedweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
