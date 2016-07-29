/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.project.ejb.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import my.project.entities.Cars;

import my.project.entities.Carsonparkings;
import my.project.entities.Parkings;
import my.project.utils.JQueryDataTable;
import my.project.utils.MediaTypeCustom;

/**
 *
 * @author www
 */
@Stateless
@Path("carsonparkings")
public class CarsonparkingsFacadeREST extends AbstractFacade<Carsonparkings> {

    @Inject
    private Logger log;
    @Inject
    private Validator validator;
    @PersistenceContext(unitName = "primary")
    private EntityManager em;
    
    @Inject ParkingsFacadeREST parkings;

    @Resource
    private SessionContext context;

    public CarsonparkingsFacadeREST() {
        super(Carsonparkings.class);
    }

    /**
     * Creates a JAX-RS "Bad Request" response including a map of all violation
     * fields, and their message. This can then be used by clients to show
     * violations.
     *
     * @param violations A set of violations that needs to be reported
     * @return JAX-RS response containing all violations
     */
    private <T> Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<T>> violations) {
        log.fine("Проверка завершена. Количество ошибок: " + violations.size());

        Map<String, String> responseObj = new HashMap<>();

        for (ConstraintViolation<T> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }

    Response buildMsg(Response.Status status, String name, String msg) {
        Map<String, String> responseObj = new HashMap<>();
        responseObj.put(name, msg);
        return Response.status(status).entity(responseObj).build();
    }
    
    public int countCarsOnParcking(String sSearch){ 
        String sqlSearch = (sSearch != null && !sSearch.isEmpty() && sSearch.matches("^[a-zA-Z0-9]+$")) ? " AND upper(c.carid.number) LIKE :number" : "";
        Query query1 = em.createQuery("SELECT  count(c) FROM Carsonparkings c  JOIN  c.carid  JOIN  c.parkingid WHERE c.endtime is NULL" + sqlSearch);
        if (!sqlSearch.isEmpty()) {
            query1.setParameter("number", '%' + sSearch.toUpperCase() + '%');
        }
        return ((Long) query1.getSingleResult()).intValue();
    }
    
    
    public int leftSeats(){
        int countNow = countCarsOnParcking(null);
        Parkings p = parkings.find(1);
        return p.getCarslimit()-countNow;
    }
    
    

    @POST
    @Consumes(MediaTypeCustom.APPLICATION_JSON)
    @Produces(MediaTypeCustom.APPLICATION_JSON)
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Response acceptCar(Cars entity) {

        Set<ConstraintViolation<Cars>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            return createViolationResponse(violations).build();
        }

        try {
            Cars car = null;
            List<Carsonparkings> carsonparkings = findByNumber(entity.getNumber()); //TODO: Заменить like на =
            if (carsonparkings != null && !carsonparkings.isEmpty()) { 
                return buildMsg(Response.Status.CONFLICT, "number", "Машина с таким номером уже на стоянке");

            }
            
            if  (leftSeats()<=0){
                return buildMsg(Response.Status.CONFLICT, "number", "Не осталось мест");
            }

            try {
                car = em.createNamedQuery("Cars.findByNumber", Cars.class).setParameter("number", entity.getNumber()).getSingleResult();
            } catch (NoResultException e) {
                car = entity;
                em.persist(car);
            }
            Parkings parking = em.find(Parkings.class, 1); //TODO: пока одна парковка
            Carsonparkings park = new Carsonparkings();
            park.setBegtime(new Date());
            park.setCarid(car);
            park.setParkingid(parking);
            em.persist(park);
            return Response.ok().entity(entity).build();

        } catch (Exception e) {
            context.setRollbackOnly();
            return buildMsg(Response.Status.BAD_REQUEST, "error", e.getMessage());

        }

        

        //super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaTypeCustom.APPLICATION_JSON)
    public void edit(@PathParam("id") Integer id, Carsonparkings entity) {
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
    public Carsonparkings find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    public List<Carsonparkings> findByNumber(String sSearch, int iDisplayStart, int iDisplayLength) {
        String sqlSearch = (sSearch != null && !sSearch.isEmpty() && sSearch.matches("^[a-zA-Z0-9]+$")) ? " AND upper(c.carid.number) LIKE :number" : "";
        TypedQuery<Carsonparkings> query = em.createQuery("SELECT  c FROM Carsonparkings c  JOIN FETCH c.carid   JOIN FETCH c.parkingid WHERE c.endtime is NULL" + sqlSearch, Carsonparkings.class);
        if (!sqlSearch.isEmpty()) {
            query.setParameter("number", '%' + sSearch.toUpperCase() + '%');
        }
        if (iDisplayStart != -1) {
            query.setFirstResult(iDisplayStart);
        }
        if (iDisplayLength != -1) {
            query.setMaxResults(iDisplayLength);
        }
        return query.getResultList();

    }

    public List<Carsonparkings> findByNumber(String sSearch) {
        return findByNumber(sSearch, -1, -1);
    }

    @GET
    @Produces(MediaTypeCustom.APPLICATION_JSON)
    public JQueryDataTable findAll(@QueryParam("sEcho") String sEcho,
            @QueryParam("iDisplayLength") int iDisplayLength,
            @QueryParam("iDisplayStart") int iDisplayStart,
            @QueryParam("sSearch") String sSearch
    ) {
        //TODO:validation
        List<Carsonparkings> carsonparkings = findByNumber(sSearch, iDisplayStart, iDisplayLength);
       
        int count = countCarsOnParcking(sSearch);

        return new JQueryDataTable<Carsonparkings>(sEcho, count, count, carsonparkings);
    }

    /* @GET
    @Produces(MediaTypeCustom.APPLICATION_JSON)
    public JQueryDataTable findAll(@Context UriInfo uriInfo) {
        int offset = 0, max = 0;
        final String search;
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();

        offset = Integer.valueOf(queryParams.get("iDisplayStart").get(0));
        max = Integer.valueOf(queryParams.get("iDisplayLength").get(0));
        search = queryParams.get("sSearch").get(0);
        //TODO: validation

        Filter<Carsonparkings> filter = new Filter<Carsonparkings>() {
            @Override
            public Expression<Boolean> run(Root<Carsonparkings> rt) {
                CriteriaBuilder bilder = em.getCriteriaBuilder();
                Expression<Boolean> result=null;
                if (search != null && !search.isEmpty()) {
                    Expression<String> path = rt.get("lastName");
                    return bilder.and(
                            bilder.isNull(rt.get("endtime")), 
                            bilder.like(rt.get("endtime"), search) 
                        );
                } else {
    return bilder.isNull (rt.get



("endtime"));
                }
                return result;
                
            }
        };

        Integer total = super.count(filter);
        List<Carsonparkings> carsonparkings = super.findRange(offset, max, filter);
        JQueryDataTable result = new JQueryDataTable<Carsonparkings>(queryParams, carsonparkings, total, total);

        return result;// super.findAll();
    }*/
    @GET
    @Path("{from}/{to}")
    @Produces(MediaTypeCustom.APPLICATION_JSON)
    public List<Carsonparkings> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
