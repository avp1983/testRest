/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.project.utils;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author www
 */
public class Helper {

    public static Date setDate(Date date, int hour, int minute, int second, int millisec) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, millisec);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.HOUR, hour);

        return calendar.getTime();
    }

    public static Date getStartOfDay(Date date) {

        return setDate(date, 0, 0, 0, 0);
    }

    public static Date getEndOfDay(Date date) {

        return setDate(date, 23, 59, 59, 9999);
    }

}
