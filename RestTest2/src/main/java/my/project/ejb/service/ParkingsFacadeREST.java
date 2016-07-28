/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.project.ejb.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import my.project.entities.Parkings;
import my.project.utils.MediaTypeCustom;

/**
 *
 * @author www
 */
@Stateless
@Path("parkings")
@Encoded
public class ParkingsFacadeREST extends AbstractFacade<Parkings> {

    @Inject
    private EntityManager em;

    public ParkingsFacadeREST() {
        super(Parkings.class);
    }

    @POST
    @Override
    @Consumes(MediaTypeCustom.APPLICATION_JSON)
    public void create(Parkings entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaTypeCustom.APPLICATION_JSON)
    public void edit(@PathParam("id") Integer id, Parkings entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces(MediaTypeCustom.APPLICATION_JSON)
    public Parkings find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces(MediaTypeCustom.APPLICATION_JSON)
    public List<Parkings> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces(MediaTypeCustom.APPLICATION_JSON)
    public List<Parkings> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaTypeCustom.APPLICATION_JSON)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
