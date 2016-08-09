package com.example.PurchaseItems;

import com.example.ProductionItems.ProductionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lav on 2/8/16.
 */
@Repository
public interface PurchaseItemRepository extends JpaRepository<PurchaseItem,Long> {

    @Query(value = "select * from purchase_item as u  where u.name=?1 and u.purchase_order_purchaseno=?2",nativeQuery = true)
    PurchaseItem findByNameAndPruchaseno(String name,int purchaseno);

    PurchaseItem findByItemid(int itemid);

    @Query(value = "select purchase_order_purchaseno from purchase_item as u where u.name=?1",nativeQuery = true)
    List<Integer> findPurchaseNoByItemName(String name);

}
