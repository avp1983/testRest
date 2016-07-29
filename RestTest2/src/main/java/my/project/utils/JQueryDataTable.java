/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.project.utils;

import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author www
 */
@XmlRootElement
public class JQueryDataTable<T> {

    private String sEcho;
    private int iTotalRecords;
    private int iTotalDisplayRecords;
    private List<T> aaData;

    public JQueryDataTable() {
    }
    
    /*public JQueryDataTable(MultivaluedMap<String, String> queryParams, List<T> aa, Integer totalRecords, Integer totalDispalay) {
        iTotalDisplayRecords = Integer.valueOf(queryParams.get("iDisplayLength").get(0));
        sEcho = queryParams.get("sEcho").get(0);
        iTotalRecords = totalRecords;
        iTotalDisplayRecords = totalDispalay;
        aaData = aa;

    }*/

    public JQueryDataTable(String sEcho, int iTotalRecords, int iTotalDisplayRecords, List<T> aaData) {
        this.sEcho = sEcho;
        this.iTotalRecords = iTotalRecords;
        this.iTotalDisplayRecords = iTotalDisplayRecords;
        this.aaData = aaData;
    }

    public String getsEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public int getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public List<T> getAaData() {
        return aaData;
    }

    public void setAaData(List<T> aaData) {
        this.aaData = aaData;
    }


    
    

    
    

}
