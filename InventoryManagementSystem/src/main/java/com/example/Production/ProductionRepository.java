package com.example.Production;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lav on 2/8/16.
 */
@Repository
public interface ProductionRepository extends JpaRepository<Production,Long> {

    List<Production> findByUserName(String userName);

    List<Production> findByProductName(String productName);
}
