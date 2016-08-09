package com.example.Inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by lav on 2/8/16.
 */
@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;
   @Transactional
    public boolean insert(Inventory inventory)
    {
        if(inventoryRepository.save(inventory)!=null)
            return true;
        else
            return false;
    }
    @Transactional
    public List<Inventory> viewAll()
    {
       if(inventoryRepository.findAll().size()>0)
           return inventoryRepository.findAll();
        else
           return null;
    }
    @Transactional
    public Inventory findByName(String name)
    {
        Inventory inventory=inventoryRepository.findByName(name);
        if(inventory!=null)
            return inventory;
        else
            return null;
    }
    @Transactional
    public boolean update(String name,int quantity)
    {
        Inventory inventory=inventoryRepository.findByName(name);
        if(inventory!=null)
        {
            inventory.setCurrentBalance(inventory.getCurrentBalance()+quantity);

            return true;
        }
        else
            return false;
    }


    @Transactional
    public boolean decreaseCurrentQuantity(String name,int quantituRequired)
    {
        Inventory inventory=inventoryRepository.findByName(name);
        if(inventory!=null)
        {
            inventory.setCurrentBalance(inventory.getCurrentBalance()-quantituRequired);

            return true;
        }
        else
            return false;
    }

    @Transactional
    public List<Inventory>updateInventory(String name,int cost)
    {
        Inventory i=inventoryRepository.findByName(name);
        if(i!=null)
        {

            i.setUnitCost(cost);
            return inventoryRepository.findAll();
        }
        else return null;
    }
    @Transactional
    public List<Inventory> deleteInventory(String name)
    {
        Inventory inventory=inventoryRepository.findByName(name);
        if(inventory.getCurrentBalance()==0)
            {
                inventoryRepository.delete(inventory);
                return inventoryRepository.findAll();
            }
        else
            return null;
    }
}
