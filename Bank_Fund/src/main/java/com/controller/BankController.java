package com.controller;

import com.config.ProjConfig;
import com.exceptions.IdNotFoundException;
import com.model.Fund;
import com.model.Manager;
import com.service.BankService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class BankController {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjConfig.class);
        LocalContainerEntityManagerFactoryBean emf =
                context.getBean(LocalContainerEntityManagerFactoryBean.class);
        Scanner sc=new Scanner(System.in);

        BankService bankService=context.getBean(BankService.class);

        while(true){
            System.out.println("1. Add Manager");
            System.out.println("2. Get All managers");
            System.out.println("3. Add Fund...");
            System.out.println("4. Get Fund by Manager");
            System.out.println("0. Exit...");
            System.out.println();
            System.out.println("Enter the choice:");
            int input=sc.nextInt();
            if(input==0){
                break;
            }

            switch (input){
                case 1:
                    Manager manager=new Manager();
                    System.out.println("Enter Manager's Name:");
                    manager.setName(sc.next());
                    System.out.println("Enter Email:");
                    manager.setEmail(sc.next());
                    bankService.addManager(manager);
                    System.out.println("Manager inserted...");
                    break;
                case 2:
                    List<?> list=bankService.getAllManagers();
                    list.forEach(System.out::println);
                    break;
                case 3:
                    try{
                        Fund fund=new Fund();
                        System.out.println("Enter the Manager Id to add Fund:");
                        long managerId=sc.nextLong();
                        Manager manager1=bankService.getManagerById(managerId);

                        System.out.println("Enter Fund name:");
                        fund.setName(sc.next());
                        System.out.println("Enter Amount:");
                        fund.setAmount(sc.nextBigDecimal());
                        System.out.println("Enter Expense Ratio:");
                        fund.setExpenseRatio(sc.nextBigDecimal());
                        System.out.println("Enter Created date:(yyyy-mm-dd)");
                        String date=sc.next();
                        LocalDate createdDate=LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
                        fund.setCreatedAt(createdDate);

                        bankService.insertFund(fund,manager1);
                        System.out.println("Fund inserted...");
                    }catch (RuntimeException e){
                        e.getMessage();
                    }
                    break;
                case 4:
                    System.out.println("Enter the Manager ID to find the Fund");
                    long managerId=sc.nextLong();
                    List<?> list1=bankService.getFundsByManager(managerId);
                    list1.forEach(System.out::println);
                    break;
                default:
                    break;
            }
        }
    }
}
