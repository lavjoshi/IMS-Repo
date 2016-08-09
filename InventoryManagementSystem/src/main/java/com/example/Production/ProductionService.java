package com.example.Production;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lav on 2/8/16.
 */
@Service
public class ProductionService {

    @Autowired
    ProductionRepository productionRepository;

    @Transactional
    public Production insert(Production production)
     {
         return productionRepository.save(production);
     }


    @Transactional
    public List<Production> findByUserName(String name)
    {
        return productionRepository.findByUserName(name);
    }

    @Transactional
    public List<Production> findByProductName(String name)
    {
        return productionRepository.findByProductName(name);
    }


}
