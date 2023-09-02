package com.kris.security.repositories;

import com.kris.security.entities.Ordering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Ordering, Integer> {

}
