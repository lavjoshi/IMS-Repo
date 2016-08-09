package com.example.PurchaseOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lav on 2/8/16.
 */
@Service
public class PurchaseOrderService {

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;
    @Transactional
    public PurchaseOrder insert(PurchaseOrder purchaseOrder)
    {
        return purchaseOrderRepository.save(purchaseOrder);
    }
    @Transactional
    public PurchaseOrder findOne(int purchaseNo)
    {
        return purchaseOrderRepository.findByPurchaseno(purchaseNo);
    }
    @Transactional
    public List<PurchaseOrder> findByUsername(String username)
    {
        return purchaseOrderRepository.findByUsername(username);
    }

    @Transactional
    public void updateCost(int purchaseno,float updatedCost)
    {
        PurchaseOrder purchaseOrder=purchaseOrderRepository.findByPurchaseno(purchaseno);
        if(purchaseOrder!=null)
            purchaseOrder.setTotalCost(updatedCost);
    }

    @Transactional
    public void delete(PurchaseOrder purchaseOrder)
    {
        purchaseOrderRepository.delete(purchaseOrder);
    }

}
