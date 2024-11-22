package com.projectgym.repository;

import com.projectgym.Entity.Product;
import com.projectgym.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
