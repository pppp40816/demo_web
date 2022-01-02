/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "performance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Performance.findAll", query = "SELECT p FROM Performance p")
    , @NamedQuery(name = "Performance.findById", query = "SELECT p FROM Performance p WHERE p.id = :id")
    , @NamedQuery(name = "Performance.findByType", query = "SELECT p FROM Performance p WHERE p.type = :type")
    , @NamedQuery(name = "Performance.findByUsage", query = "SELECT p FROM Performance p WHERE p.usageValue = :usageValue")
    , @NamedQuery(name = "Performance.findByEventTime", query = "SELECT p FROM Performance p WHERE p.eventTime = :eventTime")
    , @NamedQuery(name = "Performance.findByIsAlarm", query = "SELECT p FROM Performance p WHERE p.isAlarm = :isAlarm")})
public class Performance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "performance_gen")
    @SequenceGenerator(name = "performance_gen", sequenceName = "performance_sequence", allocationSize = 1)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @Column(name = "usage_value")
    private String usageValue;
    @Basic(optional = false)
    @Column(name = "event_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventTime;
    @Basic(optional = false)
    @Column(name = "is_alarm")
    private boolean isAlarm;
    @JoinColumn(name = "uid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User uid;

    public Performance() {
    }

    public Performance(Integer id) {
        this.id = id;
    }

    public Performance(Integer id, String type, String usageValue, Date eventTime, boolean isAlarm) {
        this.id = id;
        this.type = type;
        this.usageValue = usageValue;
        this.eventTime = eventTime;
        this.isAlarm = isAlarm;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsageValue() {
        return usageValue;
    }

    public void setUsageValue(String usageValue) {
        this.usageValue = usageValue;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public boolean getIsAlarm() {
        return isAlarm;
    }

    public void setIsAlarm(boolean isAlarm) {
        this.isAlarm = isAlarm;
    }

    public User getUid() {
        return uid;
    }

    public void setUid(User uid) {
        this.uid = uid;
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
        if (!(object instanceof Performance)) {
            return false;
        }
        Performance other = (Performance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Performance[ id=" + id + " ]";
    }
    
}
