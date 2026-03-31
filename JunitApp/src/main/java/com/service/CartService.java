package com.service;

import com.exception.InvalidNameException;
import com.exception.InvalidPriceException;
import com.model.CartItems;
import com.model.User;

import java.math.BigDecimal;
import java.util.List;

public class CartService {

    public BigDecimal computeTotalCost(List<CartItems> items, User user){

        if(items==null){
            throw new NullPointerException("Items cannot be null");
        }

        if(user==null){
            throw  new NullPointerException("User cannot be null");
        }

        if(user.getUsername()==null ||user.getUsername().trim().isEmpty() ){
            throw  new IllegalArgumentException("Invalid Username");
        }
        if(!user.getStatus().equals("Normal") && !user.getStatus().equals("Premium")){
            throw  new IllegalArgumentException("Invalid Status");
        }

        BigDecimal total=BigDecimal.ZERO;

        for(CartItems item:items){

            if(item.getName()==null ||item.getName().isEmpty()){
                throw new InvalidNameException("Item Cannot be Empty");
            }
            if(item.getPrice().compareTo(BigDecimal.ZERO)<0){
                throw new InvalidPriceException("Price cannot be less than Zero");
            }

            BigDecimal totalPrice=item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

            total=total.add(totalPrice);
        }
        if(total.compareTo(new BigDecimal("1000"))>0){
            total=total.multiply(new BigDecimal("0.95"));
        }

        if(total.compareTo(new BigDecimal("500"))>0 && user.getStatus().equals("Premium")){
            total=total.multiply(new BigDecimal("0.90"));
        }


        return total;
    }
}
