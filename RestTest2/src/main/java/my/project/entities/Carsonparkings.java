/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.project.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author www
 */
@Entity
@Table(name = "carsonparkings")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Carsonparkings.findAll", query = "SELECT c FROM Carsonparkings c"),
    @NamedQuery(name = "Carsonparkings.findById", query = "SELECT c FROM Carsonparkings c WHERE c.id = :id"),
    @NamedQuery(name = "Carsonparkings.findByBegtime", query = "SELECT c FROM Carsonparkings c WHERE c.begtime = :begtime"),
    @NamedQuery(name = "Carsonparkings.findByEndtime", query = "SELECT c FROM Carsonparkings c WHERE c.endtime = :endtime")})
public class Carsonparkings implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Beg_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date begtime;
    
    @Column(name = "End_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endtime;
    
    
    @JoinColumn(name = "Car_id", referencedColumnName = "Id")
    @ManyToOne(optional = true)
    private Cars carid;
    
    
    @JoinColumn(name = "Parking_id", referencedColumnName = "Id")
    @ManyToOne(optional = true)   
    private Parkings parkingid;

    public Carsonparkings() {
    }

    public Carsonparkings(Integer id) {
        this.id = id;
    }

    public Carsonparkings(Integer id, Date begtime) {
        this.id = id;
        this.begtime = begtime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getBegtime() {
        return begtime;
    }

    public void setBegtime(Date begtime) {
        this.begtime = begtime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Cars getCarid() {
        return carid;
    }

    public void setCarid(Cars carid) {
        this.carid = carid;
    }

    public Parkings getParkingid() {
        return parkingid;
    }

    public void setParkingid(Parkings parkingid) {
        this.parkingid = parkingid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Carsonparkings)) {
            return false;
        }
        Carsonparkings other = (Carsonparkings) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ent.Carsonparkings[ id=" + id + " ]";
    }
    
}
