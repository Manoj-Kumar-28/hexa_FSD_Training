package com.service;


import com.exceptions.ItemNotFoundException;
import com.model.CartItem;
import com.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {


    //Dependency Injection using Constructor
    private final CartRepository cartRepository;
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Transactional
    public List<CartItem> fetchAllItems() {
        return cartRepository.fetchAllItems();
    }

    @Transactional
    public List<CartItem> getItemsByUsername(String username) {
        return cartRepository.getitemsByUsername(username);
    }

    @Transactional
    public void delete(int id) {
        int count=cartRepository.delete(id);
        if(count==0){
            throw new ItemNotFoundException("Enter the correct id to delete");
        }
    }
}
