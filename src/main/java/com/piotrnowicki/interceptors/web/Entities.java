package com.piotrnowicki.interceptors.web;

import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.piotrnowicki.interceptors.boundary.CustomerService;
import com.piotrnowicki.interceptors.entity.Customer;
import com.piotrnowicki.interceptors.entity.HistoryLog;

/**
 * Defines RESTful resource. Allows for reading data and invoking exemplary business methods of the EJBs.
 * 
 * @author Piotr Nowicki
 *
 */
@Path("/")
public class Entities {

    @EJB
    CustomerService customerService;

    @PersistenceContext
    EntityManager em;

    @Path("add")
    @GET
    public String add() {
        customerService.execute("John", "Doe");
        
        return "";
    }
    

    @Path("addWithoutTx")
    @GET
    public String addWithoutTx() {
        customerService.executeWithoutTx("John1", "Doe1");
        
        return "";
    }
    
    @Path("addWithMethodException")
    @GET
    public String addWithMethodException() {
        customerService.executeWithMethodException("John2", "Doe2");
        
        return "";
    }

    @Path("view")
    @GET
    public String getAll() {
        List<Customer> customers = em.createNamedQuery(Customer.FIND_ALL, Customer.class).getResultList();
        List<HistoryLog> logs = em.createNamedQuery(HistoryLog.FIND_ALL, HistoryLog.class).getResultList();

        StringBuilder sb = new StringBuilder();

        sb.append("<strong>Customers:</strong><br />");
        for (Customer customer : customers) {
            sb.append(customer);
            sb.append("<br />");
        }
        sb.append("<hr />");

        sb.append("<strong>Logs:</strong><br />");
        for (HistoryLog historyLog : logs) {
            sb.append(historyLog);
            sb.append("<br />");
        }

        return sb.toString();
    }
}
