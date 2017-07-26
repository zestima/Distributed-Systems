/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author zsdaking
 */
@Entity
@Table(name = "TOPICS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Topics.findAll", query = "SELECT t FROM Topics t")
    , @NamedQuery(name = "Topics.findByTopic", query = "SELECT t FROM Topics t WHERE t.topic = :topic")})
public class Topics implements Serializable {

    @OneToMany(mappedBy = "topicFk")
    private Collection<News> newsCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "TOPIC")
    private String topic;
    @JoinTable(name = "SUBS", joinColumns = {
        @JoinColumn(name = "TOPIC_FK", referencedColumnName = "TOPIC")}, inverseJoinColumns = {
        @JoinColumn(name = "USERNAME_FK", referencedColumnName = "USERNAME")})
    @ManyToMany
    private Collection<Users> usersCollection;
    @JoinColumn(name = "USERNAME_FK", referencedColumnName = "USERNAME")
    @ManyToOne
    private Users usernameFk;

    public Topics() {
    }

    public Topics(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @XmlTransient
    public Collection<Users> getUsersCollection() {
        return usersCollection;
    }

    public void setUsersCollection(Collection<Users> usersCollection) {
        this.usersCollection = usersCollection;
    }

    public Users getUsernameFk() {
        return usernameFk;
    }

    public void setUsernameFk(Users usernameFk) {
        this.usernameFk = usernameFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (topic != null ? topic.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Topics)) {
            return false;
        }
        Topics other = (Topics) object;
        if ((this.topic == null && other.topic != null) || (this.topic != null && !this.topic.equals(other.topic))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.Topics[ topic=" + topic + " ]";
    }

    @XmlTransient
    public Collection<News> getNewsCollection() {
        return newsCollection;
    }

    public void setNewsCollection(Collection<News> newsCollection) {
        this.newsCollection = newsCollection;
    }
    
}
