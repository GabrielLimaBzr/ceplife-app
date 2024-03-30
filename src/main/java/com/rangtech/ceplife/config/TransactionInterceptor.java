package com.rangtech.ceplife.config;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Interceptor
@Transacional
@Priority(Interceptor.Priority.APPLICATION)
public class TransactionInterceptor {
	
	@Inject
    private EntityManager entityManager;

    @AroundInvoke
    public Object manageTransaction(InvocationContext context) throws Exception {
    	System.out.println("INICANDO TRANSAÇÃO");
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            // Executa o método de negócio
            Object result = context.proceed();

            // Commita a transação
            transaction.commit();

            System.out.println("ENCERRANDO TRANSAÇÃO");
            return result;
        } catch (Exception e) {
            // Rollback em caso de exceção
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
