package com.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AppServiceTest {


    private AppService appService=new AppService();

    @Test
    public void addTest(){
        Assertions.assertEquals(3,appService.add(2,1));
        Assertions.assertEquals(0,appService.add(0,0));
        Assertions.assertEquals(1,appService.add(2,-1));
    }
}
