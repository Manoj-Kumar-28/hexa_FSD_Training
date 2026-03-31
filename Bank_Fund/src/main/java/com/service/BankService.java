package com.service;

import com.exceptions.IdNotFoundException;
import com.model.Fund;
import com.model.Manager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BankService {


    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void addManager(Manager manager) {
        em.persist(manager);
    }

    public List<?> getAllManagers() {
        String jpql="select m from Manager m";
        String hql="from manager m";
        Query query=em.createQuery(jpql, Manager.class);
        return query.getResultList();
    }

    public Manager getManagerById(long managerId) {
        Manager manager= em.find(Manager.class,managerId);
        if(manager==null){
            throw new IdNotFoundException("Enter the correct Id...");
        }
        return manager;
    }

    @Transactional
    public void insertFund(Fund fund, Manager manager1) {
        fund.setManager(manager1);
        em.persist(fund);
    }


    public List<?> getFundsByManager(long managerId) {
        String jpql = "SELECT f FROM Fund f WHERE f.manager.id = :managerId";

        return em.createQuery(jpql, Fund.class)
                .setParameter("managerId", managerId)
                .getResultList();
    }
}
