/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.project.rest;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import my.project.entities.Parkings;

/**
 *
 * @author www
 */
@Path("/parkings")
@RequestScoped
@Local
public interface IParkingsService extends IService<Parkings> {

   

    
    /**
     * Выручка за период
     * @param begtime
     * @param endtime
     * @return 
     */
    
    @GET
    @Path("/{id:[0-9]+}/revenue")
    @Produces(MediaType.APPLICATION_JSON)
    public Parkings getRevenue(@PathParam("begtime") Date begtime, @PathParam("endtime") Date endtime);
    
    /**
     * Выручка за день 
     * @return 
     */
    @GET
    @Path("/{id:[0-9]+}/revenue")
    @Produces(MediaType.APPLICATION_JSON)
    public Parkings getRevenue();
    
    
}
