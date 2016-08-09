package com.example.PurchaseOrder;

import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;


/**
 * Created by lav on 2/8/16.
 */
@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder,Long> {

    PurchaseOrder findByPurchaseno(int purchaseno);

    List<PurchaseOrder> findByUsername(String username);



}
