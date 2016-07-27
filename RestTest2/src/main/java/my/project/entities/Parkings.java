/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.project.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author www
 */
@Entity
@Table(name = "parkings")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parkings.findAll", query = "SELECT p FROM Parkings p"),
    @NamedQuery(name = "Parkings.findById", query = "SELECT p FROM Parkings p WHERE p.id = :id"),
    @NamedQuery(name = "Parkings.findByTariff", query = "SELECT p FROM Parkings p WHERE p.tariff = :tariff"),
    @NamedQuery(name = "Parkings.findByCarslimit", query = "SELECT p FROM Parkings p WHERE p.carslimit = :carslimit")})
public class Parkings implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
   
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "Tariff")
    private BigDecimal tariff;
    
    
    private BigDecimal revenue;
    
    @Basic(optional = false)
    @Column(name = "Cars_limit")
    private int carslimit;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parkingid")
    private Set<Carsonparkings> carsonparkingsSet;

    public Parkings() {
    }

    public Parkings(Integer id) {
        this.id = id;
    }

    public Parkings(Integer id, BigDecimal tariff, int carslimit) {
        this.id = id;
        this.tariff = tariff;
        this.carslimit = carslimit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getTariff() {
        return tariff;
    }

    public void setTariff(BigDecimal tariff) {
        this.tariff = tariff;
    }

    public int getCarslimit() {
        return carslimit;
    }

    public void setCarslimit(int carslimit) {
        this.carslimit = carslimit;
    }

    @XmlTransient
    public Set<Carsonparkings> getCarsonparkingsSet() {
        return carsonparkingsSet;
    }

    public void setCarsonparkingsSet(Set<Carsonparkings> carsonparkingsSet) {
        this.carsonparkingsSet = carsonparkingsSet;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
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
        if (!(object instanceof Parkings)) {
            return false;
        }
        Parkings other = (Parkings) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ent.Parkings[ id=" + id + " ]";
    }
    
}
