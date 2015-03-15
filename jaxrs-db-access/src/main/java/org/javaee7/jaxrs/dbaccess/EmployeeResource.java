package org.javaee7.jaxrs.dbaccess;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Arun Gupta
 */
@Path("employee")
@Stateless
public class EmployeeResource {

    @EJB
    private EmployeeService employeeService;

    @GET
    @Produces("application/xml")
    public Employee[] get() {
        return employeeService.get();
    }

    @POST
    @Path("{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Employee handlePost(@PathParam("name") String name) {
        return employeeService.store(name);
    }

}
