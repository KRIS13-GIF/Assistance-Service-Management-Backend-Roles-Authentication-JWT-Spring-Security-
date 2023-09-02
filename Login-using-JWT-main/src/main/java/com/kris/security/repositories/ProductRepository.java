package com.kris.security.repositories;

import com.kris.security.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>{


    @Query("SELECT MAX(p.fileNum) FROM Product p")
    Integer findLatestFileNum();

    @Query(value = "SELECT p.file_num FROM Product p WHERE p.customer_id = ?", nativeQuery = true)
    List<Integer> findFileNumByCustomerId(String customerId);

    Product findProductByFileNumAndCustomerId(int nr, int id);


}
