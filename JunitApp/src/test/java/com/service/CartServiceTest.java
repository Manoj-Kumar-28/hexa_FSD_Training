package com.service;

import com.exception.InvalidNameException;
import com.exception.InvalidPriceException;
import com.model.CartItems;
import com.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartServiceTest {

    private CartService cartService=new CartService();

    @Test
    public void itemsNullTest(){

        User user1=new User(1,"Manoj","Normal");
        //checking for items==null
        NullPointerException e=Assertions.assertThrows(NullPointerException.class,
                ()->cartService.computeTotalCost(null,user1));
        //checking for message
        Assertions.assertEquals("Items cannot be null",e.getMessage());
    }

    @Test
    public void userNullTest(){
        //List<CartItems> items= List.of(new CartItems(1,"Laptop",new BigDecimal("4500"),1));
        List<CartItems> items=new ArrayList<>();
        items.add(new CartItems(1,"Laptop",new BigDecimal("4500"),1));

        //checking if user==null
        NullPointerException e1=Assertions.assertThrows(NullPointerException.class,
                ()->cartService.computeTotalCost(items,null));
    }


    @Test
    public void userNameNullTest(){

        //checking username=null
        User user2=new User(2,null,"Normal");


        List<CartItems> items= List.of(new CartItems(1,"Laptop",new BigDecimal("4500"),1));

        IllegalArgumentException e1=Assertions.assertThrows(IllegalArgumentException.class,
                ()->cartService.computeTotalCost(items,user2));

        Assertions.assertEquals("Invalid Username",e1.getMessage());

        //checking username==empty
        User user3=new User(2,"","Normal");
        IllegalArgumentException e3=Assertions.assertThrows(IllegalArgumentException.class,
                ()->cartService.computeTotalCost(items,user3));
        Assertions.assertEquals("Invalid Username",e3.getMessage());
    }

    @Test
    public void invalidStatusTest(){

        //checking status if normal and premium
        User user3=new User(2,"Manoj","silvererrrvr");

        List<CartItems> items= List.of(new CartItems(1,"Laptop",new BigDecimal("4500"),1));

        IllegalArgumentException e2=Assertions.assertThrows(IllegalArgumentException.class,
                ()->cartService.computeTotalCost(items,user3));
        //checking the message
        Assertions.assertEquals("Invalid Status",e2.getMessage());
    }


    @Test
    public void itemNameNullTest(){

        //checking itemname==null
        List<CartItems> list1= List.of(new CartItems(1,null,new BigDecimal("4500"),1));
        User user5=new User(4,"Achii","Normal");

        //checking itemname==empty
        List<CartItems> list2= List.of(new CartItems(1,"",new BigDecimal("4500"),1));
        Assertions.assertThrows(InvalidNameException.class,
                ()->cartService.computeTotalCost(list1,user5));

        Assertions.assertThrows(InvalidNameException.class,
                ()->cartService.computeTotalCost(list2,user5));
    }



    @Test
    public void invalidPriceTest(){
        //negative price checking
        User user4=new User(3,"Kumar","Premium");

        List<CartItems> list= List.of(new CartItems(1,"Laptop",new BigDecimal("-200"),1));

        Assertions.assertThrows(InvalidPriceException.class,
                ()->cartService.computeTotalCost(list,user4));
        //Assertions.assertEquals("Price cannot be less than Zero",e3.getMessage());
    }


    @Test
    void testNormalStatusAbove1000DiscountPrice(){

        //Discount Price if price>1000
        User user=new User(1,"Manoj","Normal");
        List<CartItems> list= List.of(new CartItems(1,"Laptop",new BigDecimal("1200"),1));

        BigDecimal result=cartService.computeTotalCost(list,user);

        Assertions.assertEquals(new BigDecimal("1140.00"),result.setScale(2));

    }

    @Test
    void testPremiumStatusAbove500DiscountPrice(){
        //checking if >500 and staus=premium
        User user=new User(1,"Manoj","Premium");
        List<CartItems> list= List.of(new CartItems(1,"Laptop",new BigDecimal("600"),1));


        BigDecimal result=cartService.computeTotalCost(list,user);

        Assertions.assertEquals(new BigDecimal("540.00"),result.setScale(2));
    }


    @Test
    void testNoDiscount(){
        // No discount with status normal <1000
        User user=new User(1,"Manoj","Normal");

        //no discount with status premium <500
        User user1=new User(1,"Manoj","Premium");
        List<CartItems> list= List.of(new CartItems(1,"Laptop",new BigDecimal("200"),1));


        BigDecimal result=cartService.computeTotalCost(list,user);
        BigDecimal result2=cartService.computeTotalCost(list,user1);

        Assertions.assertEquals(new BigDecimal("200.00"),result.setScale(2));
        Assertions.assertEquals(new BigDecimal("200.00"),result2.setScale(2));
    }
}
