/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.project.entities;

import java.io.Serializable;
import java.util.Objects;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author www
 */
@Entity
@Table(name = "cars", uniqueConstraints = @UniqueConstraint(columnNames = "Number"))
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cars.findAll", query = "SELECT c FROM Cars c"),
    @NamedQuery(name = "Cars.findById", query = "SELECT c FROM Cars c WHERE c.id = :id"),
    @NamedQuery(name = "Cars.findByNumber", query = "SELECT c FROM Cars c WHERE c.number = :number"),
    @NamedQuery(name = "Cars.findByModel", query = "SELECT c FROM Cars c WHERE c.model = :model")})
public class Cars implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Number")
    @NotNull
    @Size(min = 4, max = 10, message = "Номер должен быть от 4 до 10 символов")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Неверный формат номера. Убедитесь, что вводите буквы на латинице!")
    private String number;

    @Column(name = "Model")
    private String model;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "carid")
    private Set<Carsonparkings> carsonparkingsSet;

    public Cars() {
    }

    public Cars(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @XmlTransient
    public Set<Carsonparkings> getCarsonparkingsSet() {
        return carsonparkingsSet;
    }

    public void setCarsonparkingsSet(Set<Carsonparkings> carsonparkingsSet) {
        this.carsonparkingsSet = carsonparkingsSet;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.number);
        hash = 67 * hash + Objects.hashCode(this.model);
        hash = 67 * hash + Objects.hashCode(this.carsonparkingsSet);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cars other = (Cars) obj;
        if (!Objects.equals(this.number, other.number)) {
            return false;
        }
        if (!Objects.equals(this.model, other.model)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.carsonparkingsSet, other.carsonparkingsSet)) {
            return false;
        }
        return true;
    }

   
    
    

    @Override
    public String toString() {
        return "ent.Cars[ id=" + id + " ]";
    }

}
