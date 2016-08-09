package com.example;

import com.example.Inventory.Inventory;
import com.example.Inventory.InventoryService;
import com.example.Production.Production;
import com.example.Production.ProductionService;
import com.example.ProductionItems.ProductionItem;
import com.example.ProductionItems.ProductionItemService;
import com.example.PurchaseItems.PurchaseItem;
import com.example.PurchaseItems.PurchaseItemService;
import com.example.PurchaseItems.Table;
import com.example.PurchaseOrder.PurchaseOrder;

import com.example.PurchaseOrder.PurchaseOrderService;


import com.example.UserCredentials.UserCredentials;
import com.example.UserCredentials.UserCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;



import java.util.*;
import java.util.Date;

/**
 * Created by lav on 2/8/16.
 */
@RestController
public class MainController {


    @Autowired
    InventoryService inventoryService;

    @Autowired
    PurchaseOrderService purchaseOrderService;

    @Autowired
    ProductionService productionService;

    @Autowired
    UserCredentialsService userCredentialsService;

    @Autowired
    ProductionItemService productionItemService;

    @Autowired
    PurchaseItemService purchaseItemService;


    @RequestMapping("/createnewInventory" )
    public ResponseEntity createInventory(@RequestParam("name") String name,@RequestParam("unitOfMeasurement") String unitOfMeasurement,@RequestParam("unitCost")String unitCost,@RequestParam("dimension") String dimension)
    {
        Inventory inventory =new Inventory();
        Message msg=new Message();
        inventory.setName(name);
        inventory.setCurrentBalance(0);
        inventory.setOpeningBalance(0);
        inventory.setDimensions(Float.parseFloat(dimension));
        inventory.setUnitCost(Integer.parseInt(unitCost));
        inventory.setUnitOfMeasurement(unitOfMeasurement);


        if(inventoryService.findByName(name)==null)
        {
            inventoryService.insert(inventory);
            msg.setData("Inventory created. Name: "+name);
            msg.setError(null);
            return new ResponseEntity<Object>(msg,HttpStatus.OK);
        }
        else
        {
            msg.setError("Inventory already exists !");
            msg.setData(null);
            return new ResponseEntity<Object>(msg,HttpStatus.BAD_REQUEST);

        }
    }


    @RequestMapping("viewAllInventory")
    public List<Inventory> viewAll()
    {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        System.out.println(name);
        List<Inventory> list=inventoryService.viewAll();
        if(list!=null)
            return list;
        else
            return null;
    }

    @RequestMapping(value="/createNewPurchase", method = RequestMethod.POST)
    public ResponseEntity createPurchase(@RequestBody Table table) {
        Message msg = new Message();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();


       int flag = 0;



        HashSet<PurchaseItem> hashSet = new HashSet<>();
        String itemName[] = table.getItemName();
        float totalCost = 0;
        float cost[] = table.getCost();
        int quantity[] = table.getQuantity();


        if (table.getVendorName() == null || cost.length == 0 || quantity.length == 0 || itemName.length == 0)
        {
           flag=1;
        }

        else
        {
            for(int i=0;i<itemName.length;i++)
            {
                if(cost[i]==0.0||quantity[i]==0||itemName[i].isEmpty())
                {
                    flag=1;
                    break;
                }

            }
        }

           if(flag==0)
           {
               for (int i = 0; i < itemName.length; i++) {


                   PurchaseItem purchaseItem = new PurchaseItem();
                   purchaseItem.setCost(cost[i]);
                   purchaseItem.setName(itemName[i]);
                   purchaseItem.setQuantity(quantity[i]);

                   hashSet.add(purchaseItem);
                   inventoryService.update(purchaseItem.getName(), purchaseItem.getQuantity());

                   float cost1 = quantity[i] * cost[i];
                   cost1 = (cost1 * 0.05f) + cost1;
                   totalCost += cost1;
               }
               PurchaseOrder purchaseOrder = new PurchaseOrder();
               purchaseOrder.setVendorname(table.getVendorName());
               purchaseOrder.setUsername(name);
               purchaseOrder.setTotalCost(totalCost);
               purchaseOrder.setPurchaseItems(hashSet);
               java.sql.Date date = new java.sql.Date(new Date().getTime());
               purchaseOrder.setDate(date);


               PurchaseOrder p = purchaseOrderService.insert(purchaseOrder);
               if (p != null) {
                   msg.setData("purchase order placed vendor: " + p.getVendorname() + " id is: " + p.getPurchaseno());
                  msg.setError(null);
                   return new ResponseEntity<Object>(msg, HttpStatus.OK);
               }


           }
        else
           {
               msg.setError("Required data missing");
               msg.setData(null);
               return new ResponseEntity<Object>(msg,HttpStatus.BAD_REQUEST);

           }

        return null;
    }



    @RequestMapping("viewPurchaseForUser")
    public Object viewPurchaseForUser()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        List<PurchaseOrder> purchaseOrder=purchaseOrderService.findByUsername(name);
        if(purchaseOrder.size()>0)
            return purchaseOrder;
        else
        {
            Message message=new Message();
            message.setError("NO Order Placed By you!!");
            message.setData(null);
            return message;
        }

    }

    @RequestMapping(value = "createNewProduct",method = RequestMethod.POST)
    public Object createNewProduct(@RequestBody Production production)
    {

        Message msg=new Message();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        production.setDate(new java.sql.Date(new Date().getTime()));
        production.setUserName(name);

        String productName=production.getProductName();
        int unitManufactured=production.getTotalManufactured();

        inventoryService.update(productName,unitManufactured);

        Set<ProductionItem> productionItem=production.getProductionItem();


       Iterator iterator=productionItem.iterator();
        while(iterator.hasNext())
        {
            ProductionItem p= (ProductionItem) iterator.next();
            inventoryService.decreaseCurrentQuantity(p.getName(),p.getQuantityRequired());
        }

        Production p=productionService.insert(production);
        if(p!=null)
        {

            msg.setData(p.getTotalManufactured()+" unit(s) of Product created with name: "+p.getProductName()+" and production Id: "+p.getProductionNo());
            msg.setError(null);
            return msg;
        }

        else
        {
            msg.setError("Failed to create Product");
            msg.setData(null);
            return msg;
        }
    }

    @RequestMapping("checkAvailableQuantity")
    public ResponseEntity checkAvailableQuantity(@RequestBody Production production)
    {

        Message message=new Message();
        int flag=1;


        if(production.getProductName()==null||production.getTotalManufactured()==0||production.getProductionItem().isEmpty())
        {
            flag=0;
        }


        if(flag!=0)
        {
            Set<ProductionItem> productionItem=production.getProductionItem();
            String name="";
            for(ProductionItem p:productionItem)
            {
                System.out.println(name);
                if(p.getQuantityRequired()==0||p.getName().isEmpty()||p.getName().equals(name))
                    {

                        flag=0;
                        break;
                    }
                name=p.getName();
                System.out.println(name);
            }
        }


        if(flag!=0)
        {
            Set<ProductionItem> productionItem=production.getProductionItem();
            Iterator i = productionItem.iterator();
            while (i.hasNext()) {
                ProductionItem p = (ProductionItem) i.next();
                Inventory inventory = inventoryService.findByName(p.getName());
                if (inventory.getCurrentBalance() < p.getQuantityRequired()) {
                    flag = -1;
                    message.setData(null);
                    message.setError(p.getName() + " quantity limit exceed. Available: " + inventory.getCurrentBalance());
                    break;
                }
            }
        }


        if(flag==-1)
       {

           return new ResponseEntity<Object>(message, HttpStatus.BAD_REQUEST);
       }
        else if(flag==0)
       {

                message.setError("Required Data Missing/Duplicate !");
                 message.setData(null);
                 return new ResponseEntity<Object>(message,HttpStatus.BAD_REQUEST);
       }
        else {
            message.setData("Items Available for production");
            message.setError(null);
            return new ResponseEntity<Object>(message,HttpStatus.OK);
              }
    }


    @RequestMapping("viewProductForUser")
    public Object viewProductForUser()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        List<Production> productions=productionService.findByUserName(name);
        if(productions.size()>0)
            return productions;
        else
        {
            Message message=new Message();
            message.setError("NO Production Initiated By You!!");
            message.setData(null);
            return message;
        }
    }


    @RequestMapping("createNewUser")
    public ResponseEntity createUser(@RequestBody UserCredentials userCredentials)
    {
        int flag=0;
        Message message=new Message();
        UserCredentials u=null;
        if(userCredentials.getRole()==null)
            flag=-1;


       if(flag==0)
       {
          u = userCredentialsService.findUser(userCredentials);
       }




        if(u==null && flag!=-1)
        {
            u=userCredentialsService.createUser(userCredentials);
            message.setData("User create with UserName: "+u.getUserName()+" and Role: "+u.getRole());
            message.setError(null);
            return new ResponseEntity<Object>(message,HttpStatus.OK);
        }
        else if(flag==-1)
        {
            message.setError("Please select Role for the user!");
            message.setData(null);
            return  new ResponseEntity<Object>(message,HttpStatus.BAD_REQUEST);

        }
        else
        {
            message.setError("User already exists with Username: "+userCredentials.getUserName());
            message.setData(null);
            return  new ResponseEntity<Object>(message,HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("viewAllUsers")
    public List<UserCredentials> viewAllByRoles()
    {
            return userCredentialsService.viewAllByRoles();
    }

    @RequestMapping("deleteUser")
    public List<UserCredentials>deleteUser(@RequestParam("userName") String name)
    {
        return userCredentialsService.deleteUser(name);
    }

    @RequestMapping("deletePurchaseItem")
    public ResponseEntity deletePurchaseItem(@RequestParam("purchaseno") int purchaseno,@RequestParam("name") String name)
    {
            List<ProductionItem> list=productionItemService.findByName(name);
            float updatedCost=0;
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            Message message=new Message();
            if(list.size()>0)
            {

                message.setData("Cannot delete/cancel. Item already used in production.");
                message.setError(null);
                return new ResponseEntity<Object>(message,HttpStatus.BAD_REQUEST);
            }
        else {
                PurchaseItem purchaseItem = purchaseItemService.findByNameAndPurchaseNo(name, purchaseno);

                inventoryService.decreaseCurrentQuantity(name, purchaseItem.getQuantity());
                purchaseItemService.delete(purchaseItem);
                PurchaseOrder purchaseOrder = purchaseOrderService.findOne(purchaseno);
                Set<PurchaseItem> set = purchaseOrder.getPurchaseItems();
                if (set.size() > 0)
                {
                    for (PurchaseItem p : set) {
                        float cost = p.getCost() * p.getQuantity();
                        cost = (cost * 0.05f) + cost;
                        updatedCost += cost;

                    }

                    purchaseOrderService.updateCost(purchaseno, updatedCost);



                }
                else
                {
                    purchaseOrderService.delete(purchaseOrder);
                }
                List<PurchaseOrder> purchaseOrders = purchaseOrderService.findByUsername(username);

                System.out.print(list.size() + " size");
                return new ResponseEntity<Object>(purchaseOrders, HttpStatus.OK);
            }

    }


    @RequestMapping("editPurchaseItem")
    public ResponseEntity editPurchaseItem(@RequestParam("purchaseno") int purchaseno,@RequestParam("name") String name)
    {


        List<ProductionItem> list=productionItemService.findByName(name);
        Message message=new Message();
        if(list.size()>0)
        {

            message.setData(null);
            message.setError("Cannot edit items. Item already used in production.");
            return new ResponseEntity<Object>(message,HttpStatus.BAD_REQUEST);
        }
        else {
            PurchaseItem purchaseItem=purchaseItemService.findByNameAndPurchaseNo(name,purchaseno);

            return new ResponseEntity<Object>(purchaseItem, HttpStatus.OK);

        }
    }


    @RequestMapping("updatePurchaseOrder")
    public List<PurchaseOrder> updatePurchaseOrder(@RequestParam("purchaseno") int purchaseno,@RequestParam("itemid")int itemId,@RequestParam("updatedQuantity")int updatedQuantity)

    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println("purchase no"+purchaseno);
        System.out.println("itemid"+itemId);

        System.out.println("newquant"+updatedQuantity);



        PurchaseItem purchaseItem=purchaseItemService.findByItemId(itemId);
        int previousQuantity=purchaseItem.getQuantity();
        float updatedCost=0;


        System.out.println("prevquant"+previousQuantity);

        int diff=previousQuantity-updatedQuantity;
        System.out.println("diff"+diff);
        if(diff<0)
        {
            //add diff
            System.out.println("add diff");
            inventoryService.update(purchaseItem.getName(),Math.abs(diff));
        }
        else
        {
            //subtract diff
            System.out.println("sub diff");
            inventoryService.decreaseCurrentQuantity(purchaseItem.getName(),Math.abs(diff));
        }


        purchaseItemService.updateQuantity(itemId,updatedQuantity);



        PurchaseOrder purchaseOrder=purchaseOrderService.findOne(purchaseno);

        Set<PurchaseItem> set=purchaseOrder.getPurchaseItems();

        for(PurchaseItem p:set)
        {
            float cost=p.getCost()*p.getQuantity();
            cost=(cost*0.05f)+cost;
            updatedCost+=cost;

        }

        purchaseOrderService.updateCost(purchaseno,updatedCost);
        List<PurchaseOrder> list=purchaseOrderService.findByUsername(username);

            System.out.print(list.size()+" size");
            return list;



    }


    @RequestMapping("productionHistory")
    public ResponseEntity productionHistory(@RequestParam("name") String name)
    {
        List<Production>list= productionService.findByProductName(name);
        if(list.size()>0)
            return new ResponseEntity<Object>(list,HttpStatus.OK);
        else
        {
            Message msg=new Message();
            msg.setError("NO Production Record Found!!");
            msg.setData(null);
          return new ResponseEntity<Object>(msg,HttpStatus.BAD_REQUEST);
        }
    }

     @RequestMapping("purchaseHistory")
    public ResponseEntity purchaseHistory(@RequestParam("name") String name) {
         System.out.println("sdfdsfs");
         List<Integer> list = purchaseItemService.findPurchaseNoByItemName(name);
         if (list.size() > 0)
         {
             List<PurchaseOrder> purchaseOrders = new ArrayList<>();

             for (int i : list) {
                    System.out.println(i);
                 PurchaseOrder purchaseOrder = purchaseOrderService.findOne(i);
                 PurchaseItem purchaseItem = purchaseItemService.findByNameAndPurchaseNo(name, i);
                 Set<PurchaseItem> set = new HashSet<>();
                 set.add(purchaseItem);
                 purchaseOrder.setPurchaseItems(set);


                 purchaseOrders.add(purchaseOrder);
             }


             return new ResponseEntity<Object>(purchaseOrders,HttpStatus.OK);
         }
         else
         {
             Message msg=new Message();
             msg.setError("NO Purchase Record Found!!");
             msg.setData(null);
             return new ResponseEntity<Object>(msg,HttpStatus.BAD_REQUEST);
         }
     }

    @RequestMapping("updateInventory")
    public Object updateInventory(@RequestParam("name") String name,@RequestParam("modifiedCost") int modifiedCost)
    {
        return inventoryService.update(name,modifiedCost);

    }

    @RequestMapping("deleteInventory")
    public ResponseEntity deleteInventory(@RequestParam("name") String name)
    {
        List<Inventory> list=inventoryService.deleteInventory(name);
        if(list!=null)
        {
            return new ResponseEntity<Object>(list,HttpStatus.OK);
        }
        else
        {
            Message message=new Message();
            message.setData(null);
            message.setError("Cannot delete. Already purchased!");
            return new ResponseEntity<Object>(message,HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("updatePassword")
    public ResponseEntity updatePassword(@RequestBody UserCredentials userCredentials,@RequestParam("newpassword") String newpass)
    {
        Message message=new Message();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        userCredentials.setUserName(username);
        boolean b=userCredentialsService.updatePass(userCredentials,newpass);
        if(b)
        {
            message.setData("Password Changed!!");
            message.setError(null);
            return  new ResponseEntity<Object>(message,HttpStatus.OK);
        }
        else
        {
            message.setData(null);
            message.setError("Wrong Password!");
            return  new ResponseEntity<Object>(message,HttpStatus.BAD_REQUEST);
        }

    }

}
