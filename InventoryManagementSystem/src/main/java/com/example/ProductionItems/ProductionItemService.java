package com.example.ProductionItems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lav on 2/8/16.
 */
@Service
public class ProductionItemService {
   @Autowired
   ProductionItemRepository productionItemRepository;
    public List<ProductionItem> findByName(String name)
    {
        return productionItemRepository.findByName(name);
    }
}
