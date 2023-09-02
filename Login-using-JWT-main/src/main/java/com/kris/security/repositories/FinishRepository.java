package com.kris.security.repositories;

import com.kris.security.entities.Finish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinishRepository extends JpaRepository<Finish, Integer> {
}
