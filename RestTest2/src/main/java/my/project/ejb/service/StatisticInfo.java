/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.project.ejb.service;

import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import my.project.entities.Parkings;

/**
 *
 * @author www
 */

@ApplicationScoped
@Path("statisticInfo")
public class StatisticInfo implements IStatisticInfo {

    private int freeSeats;
    private BigDecimal dayRevenue;
    private int carsLimit;
    private BigDecimal taxPerHour;

    @Inject
    private EntityManager em;

    @Inject
    private CarsonparkingsFacadeREST carsonparkings;

    public void StatisticInfo() {

    }

    @PostConstruct
    public void init() {
        Parkings p = em.find(Parkings.class, 1);
        carsLimit = p.getCarslimit();
        taxPerHour = p.getTariff();
        freeSeats = carsonparkings.getFreeSeats();
    }

    private void refresh() {

    }

    @Override
    public void onDataChange(@Observes  ICarsOnParkingChangeEvent event) {
        freeSeats += event.getDelta();
    }

    @Override
    public int getCarsLimit() {
        return carsLimit;
    }

    @GET
    @Path("freeSeats")
    @Override
    public int getFreeSeats() {
        return freeSeats;
    }

    @Override
    public BigDecimal getDayRevenue() {
        return dayRevenue;
    }

    @Override
    public BigDecimal getTaxPerHour() {
        return taxPerHour;

    }

}
