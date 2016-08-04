/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.project.ejb.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import my.project.entities.Parkings;
import my.project.utils.Helper;

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

    //@Inject
    // private CarsonparkingsFacadeREST carsonparkings;
    public void StatisticInfo() {

    }

    @PostConstruct
    public void init() {
        Parkings p = em.find(Parkings.class, 1);
        carsLimit = p.getCarslimit();
        taxPerHour = p.getTariff();
        freeSeats = countFreeSeats();
        dayRevenue = calculationRevenue();
    }

    private BigDecimal calculationRevenue() {
        BigDecimal result = BigDecimal.ZERO;
        Query query = em.createQuery("SELECT DATEDIFF('second',  c.begtime, c.endtime) FROM Carsonparkings c  WHERE c.endtime BETWEEN :startDate AND :endDate");

        Date day = new Date();
        Date startDate = Helper.getStartOfDay(day), endDate = Helper.getEndOfDay(day);

        query.setParameter("startDate", startDate, TemporalType.TIMESTAMP);
        query.setParameter("endDate", endDate, TemporalType.TIMESTAMP);
        List<Integer> secList = null;
        try {
            secList = (List<Integer>) query.getResultList();
        } catch (NoResultException e) {

        }
        BigDecimal secInHour = BigDecimal.valueOf(3600L);
        if (secList != null) {
            for (Iterator<Integer> i = secList.iterator(); i.hasNext();) { //FIXME:Как это все сделать в JPQL без nativQuery?
                Integer sec = i.next();
                BigDecimal res = BigDecimal.valueOf(sec.longValue());
                res = res.divide(secInHour, RoundingMode.CEILING).multiply(taxPerHour);
                result = result.add(res);

            }

           
        }
        //List<Carsonparkings> l = query.getResultList();
        return result;
    }

    private int countFreeSeats() {
        int countNow = countCarsOnParcking(null);
        Parkings p = em.find(Parkings.class, 1);
        return p.getCarslimit() - countNow;
    }

    public int countCarsOnParcking(String sSearch) {
        String sqlSearch = (sSearch != null && !sSearch.isEmpty() && sSearch.matches("^[a-zA-Z0-9]+$")) ? " AND upper(c.carid.number) LIKE :number" : "";
        Query query1 = em.createQuery("SELECT  count(c) FROM Carsonparkings c  JOIN  c.carid  JOIN  c.parkingid WHERE c.endtime is NULL" + sqlSearch);
        if (!sqlSearch.isEmpty()) {
            query1.setParameter("number", '%' + sSearch.toUpperCase() + '%');
        }
        return ((Long) query1.getSingleResult()).intValue();
    }

    @Override
    public void onDataChange(@Observes ICarsOnParkingChangeEvent event) {
        int delta = event.getDelta();
        freeSeats += delta;
        if (delta == 1) {
            dayRevenue = calculationRevenue();
        }

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

    @GET
    @Path("dayRevenue")
    @Override
    public BigDecimal getDayRevenue() {
        return dayRevenue;
    }

    @Override
    public BigDecimal getTaxPerHour() {
        return taxPerHour;

    }

}
