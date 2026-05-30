package com.devsuperior.dscommercem.repositories;

import com.devsuperior.dscommercem.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository <Product, Long> {
}
