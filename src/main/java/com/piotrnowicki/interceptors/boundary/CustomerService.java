package com.piotrnowicki.interceptors.boundary;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.TransactionSynchronizationRegistry;

import com.piotrnowicki.interceptors.entity.Customer;

/**
 * Business logic class. Defines few methods to observe different results when using it with interceptors.
 * 
 * @author Piotr Nowicki
 * 
 */
@Stateless
public class CustomerService {

    @PersistenceContext
    EntityManager em;

    @Resource
    SessionContext sctx;

    /**
     * We'll use this for checking the transaction key and see if it's equal with the actual transaction.
     */
    @Resource
    TransactionSynchronizationRegistry tsk;

    @Inject
    Logger logger;

    /**
     * Executes default REQUIRED transactional method.
     */
    public void execute(String firstName, String lastName) {
        printTxData();

        Customer customer = new Customer(firstName, lastName);

        em.persist(customer);
    }

    /**
     * Executes method without transaction (should always fail)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void executeWithoutTx(String firstName, String lastName) {
        printTxData();

        Customer customer = new Customer(firstName, lastName);

        em.persist(customer);
    }

    /**
     * Executes method which throws non-application exception. Interceptor should not be executed as well.
     */
    public void executeWithMethodException(String string, String string2) {
        throw new IllegalStateException();
    }

    private void printTxData() {
        Object txFromInterceptor = sctx.getContextData().get("Tx");
        Object tx = tsk.getTransactionKey();
        Boolean equalTxs = tx.equals(txFromInterceptor);

        logger.log(Level.INFO,
                "Transaction key from Interceptor: ''{0}'', from business method: ''{1}''. Are the Txs equal: ''{2}''.",
                new Object[] { txFromInterceptor, tx, equalTxs });
    }

}
