/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.project.ejb.service;

import java.math.BigDecimal;
import javax.ejb.Local;
import javax.enterprise.event.Observes;

/**
 *
 * @author www
 */
@Local
public interface IStatisticInfo {

    /**
     * Количество свободных мест
     *
     * @return
     */
    public int getFreeSeats();

    /**
     * Выручка за день
     * @return
     */
    public BigDecimal getDayRevenue();

    /**
     * Количество мест
     * @return 
     */
    public int getCarsLimit();
    
    /**
     * Стоимость часа в руб.
     * @return 
     */
    public BigDecimal getTaxPerHour();
    
    
    public  void onDataChange(@Observes ICarsOnParkingChangeEvent event);

}
