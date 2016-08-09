package com.example.PurchaseItems;

import com.example.ProductionItems.ProductionItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lav on 2/8/16.
 */
@Service
public class PurchaseItemService {

    @Autowired
    PurchaseItemRepository purchaseItemRepository;
  @Transactional
   public PurchaseItem findByNameAndPurchaseNo(String name,int purchaseno)
    {
        return purchaseItemRepository.findByNameAndPruchaseno(name, purchaseno);
    }
    @Transactional
    public Boolean delete(PurchaseItem purchaseItem)
    {
        purchaseItemRepository.delete(purchaseItem);
        return true;

    }
    @Transactional
    public PurchaseItem findByItemId(int itemId)
    {
        return purchaseItemRepository.findByItemid(itemId);
    }
    @Transactional
    public void updateQuantity(int itemid,int quantity)
    {
        PurchaseItem purchaseItem=purchaseItemRepository.findByItemid(itemid);
        if(purchaseItem!=null)
        {
            purchaseItem.setQuantity(quantity);


        }

    }

    @Transactional
    public List<Integer> findPurchaseNoByItemName(String name)
    {
       return purchaseItemRepository.findPurchaseNoByItemName(name);
    }

}
