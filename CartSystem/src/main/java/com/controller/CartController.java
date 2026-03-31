package com.controller;

import com.config.ProjConfig;
import com.exceptions.ItemNotFoundException;
import com.model.CartItem;
import com.model.User;
import com.service.CartService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Scanner;

public class CartController {
    public static void main(String[] args) {

        Scanner sc=new Scanner(System.in);
        var context=new AnnotationConfigApplicationContext(ProjConfig.class);
        CartService cartService=context.getBean(CartService.class);
        CartItem cartItem=context.getBean(CartItem.class);
        User user=context.getBean(User.class);

        while(true){

            System.out.println("1. Fetch All item");
            System.out.println("2. Fetch Items by given Username");
            System.out.println("3. Delete an Item");
            System.out.println("0. Exit");
            int input=sc.nextInt();
            if(input==0){
                break;
            }

            switch (input){
                case 1:
                    List<CartItem> items=cartService.fetchAllItems();
                    items.forEach(System.out::println);
                    break;
                case 2:
                    System.out.println("Enter username:");
                    String username=sc.next();
                    List<CartItem> item=cartService.getItemsByUsername(username);
                    item.forEach(System.out::println);
                    break;
                case 3:
                    System.out.println("Enter Id to delete");
                    int id=sc.nextInt();
                    try{
                        cartService.delete(id);
                        System.out.println("Item deleted");
                    }catch(ItemNotFoundException e){
                        System.out.println(e.getMessage());
                    }

            }
        }

    }
}
