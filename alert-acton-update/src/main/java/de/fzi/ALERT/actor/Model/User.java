/*******************************************************************************
 * Copyright 2012 FZI-HIWI
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.fzi.ALERT.actor.Model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author cibobo
 */
@Entity
@Table(name = "usertb")
@XmlRootElement
public class User implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Username")
    private String username;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
   
    @Size(max = 50)
    @NotNull
    @Column(name = "Email")
    private String email;
    
    @Size(max = 255)
    @Column(name = "MessageAccount")
    private String messageAccount;
    
    @Column(name = "LastDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastDate;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "uid")
    private String userID;
    
    @Size(max = 1024)
    @Column(name="unreadmsg")
    private String unreadmsg;
    
    @OneToMany(mappedBy = "uid")
    @Cascade({CascadeType.MERGE,CascadeType.PERSIST})
    private Collection<Subscription> subscriptionCollection;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, String uid) {
        this.username = username;
        this.userID = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getMessageAccount() {
        return messageAccount;
    }

    public void setMessageAccount(String messageAccount) {
        this.messageAccount = messageAccount;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public String getUid() {
        return userID;
    }

    public void setUid(String uid) {
        this.userID = uid;
    }
    
    public String getUnreadmsg() {
        return unreadmsg;
    }

    public void setUnreadmsg(String unreadmsg) {
        this.unreadmsg = unreadmsg;
    }

    @XmlTransient
    public Collection<Subscription> getSubscriptiontbCollection() {
        return subscriptionCollection;
    }

    public void setSubscriptiontbCollection(Collection<Subscription> subscriptiontbCollection) {
        this.subscriptionCollection = subscriptiontbCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Usertb[ userID=" + userID + " ]";
    }
    
}

