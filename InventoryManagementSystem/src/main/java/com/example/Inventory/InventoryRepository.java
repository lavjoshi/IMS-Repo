package com.example.Inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lav on 2/8/16.
 */
@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {
Inventory findByName(String name);

}
