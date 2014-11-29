package net.arunoday;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by Aparna on 11/29/14.
 */
@Path("/")
public class JndiReader {

    //@Resource(lookup = "java:global/connectionUrl")
    //private String connectionUrl;
    
    @Resource(lookup="java:jboss/param/demoParam")
    private String demoParam;

    @GET
    @Path("/jndi")
    @Produces({ "application/json" })
    public String getConfig(){
        return demoParam;
    }
}
