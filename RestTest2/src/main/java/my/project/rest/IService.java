/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.project.rest;

import my.project.utils.Paginator;
import java.util.List;
import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author www
 */
@Local
public interface IService<K> {

    /**
     * Получить список
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<K> listAll();

    /**
     * Получить список (пагинатор)
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<K> listAll(Paginator paginator);

    /**
     * Получить один экземпляр по id
     *
     * @param id
     * @return
     */
    @GET
    @Path("/{id:[0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public K lookuById(@PathParam("id") long id);
}
