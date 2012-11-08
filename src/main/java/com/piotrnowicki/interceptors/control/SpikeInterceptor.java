package com.piotrnowicki.interceptors.control;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.TransactionSynchronizationRegistry;

import com.piotrnowicki.interceptors.entity.HistoryLog;

/**
 * Interceptor for EJB business methods. Can be activated using annotations (@Interceptors) or in the DD.
 * 
 * @author Piotr Nowicki
 * 
 */
public class SpikeInterceptor {

    @PersistenceContext
    EntityManager em;

    /**
     * We'll use it for checking user's credentials.
     */
    @Resource
    SessionContext sctx;

    /**
     * We'll use it for checking interceptor's transaction key and saving it for EJB to visualize we're in the same Tx in
     * interceptor and business method.
     */
    @Resource
    TransactionSynchronizationRegistry tsk;

    @Inject
    Logger logger;

    @AroundInvoke
    Object mdbInterceptor(InvocationContext ctx) throws Exception {

        String userName = sctx.getCallerPrincipal().getName();
        String className = ctx.getTarget().getClass().getName();
        String methodName = ctx.getMethod().getName();

        em.persist(new HistoryLog("Invoked by " + userName));

        logger.log(Level.INFO, "##### User ''{0}'' intercepted while calling ''{1}#{2}''", new Object[] { userName, className,
                methodName });

        ctx.getContextData().put("Tx", tsk.getTransactionKey());

        // Uncomment to simulate rollback from the interceptor (tx of the business method should be rolled-back as well).
        // if (1 == 1)
        // throw new IllegalStateException();

        return ctx.proceed();
    }
}
