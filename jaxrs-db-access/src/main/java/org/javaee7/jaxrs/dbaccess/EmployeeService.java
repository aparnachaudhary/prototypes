package org.javaee7.jaxrs.dbaccess;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Aparna on 3/15/15.
 */
@Stateful
public class EmployeeService {

    @PersistenceContext
    EntityManager em;

    public Employee[] get() {
        return em.createNamedQuery("Employee.findAll", Employee.class).getResultList().toArray(new Employee[0]);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Employee store(String name) {
        Employee employee = new Employee(name);
        em.persist(employee);
        em.flush();
        return employee;
    }
}
