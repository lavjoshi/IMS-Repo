package com.example.ProductionItems;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lav on 2/8/16.
 */
@Repository
public interface ProductionItemRepository extends JpaRepository<ProductionItem,Long> {
    @Transactional
    List<ProductionItem> findByName(String name);
}
