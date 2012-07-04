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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fzi.ALERT.actor.Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author cibobo
 */
@Entity
@Table(name = "subscriptiontb")
@XmlRootElement
public class Subscription implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "KeyID")
    private Integer keyID;
    
    @Column(name = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    
    @NotNull
    @JoinColumn(name = "Action", referencedColumnName = "ActionID")
    @ManyToOne(fetch=FetchType.EAGER)
    @Cascade({CascadeType.MERGE,CascadeType.PERSIST,CascadeType.SAVE_UPDATE})
    private ActionType actiontype;
    
    @JoinColumn(name = "UID", referencedColumnName = "uid")
    @ManyToOne(fetch=FetchType.EAGER)
    @Cascade({CascadeType.MERGE,CascadeType.PERSIST,CascadeType.SAVE_UPDATE})
    private User uid;
    
    @JoinColumn(name = "PatternID", referencedColumnName = "PatternID")
    @ManyToOne(fetch=FetchType.EAGER)
    @Cascade({CascadeType.MERGE,CascadeType.PERSIST,CascadeType.SAVE_UPDATE})
    private Pattern patternID;

    public Subscription() {
    }

    public Subscription(Integer keyID) {
        this.keyID = keyID;
    }

    public Subscription(Integer keyID, ActionType action) {
        this.keyID = keyID;
        this.actiontype = action;
    }

    public Integer getKeyID() {
        return keyID;
    }

    public void setKeyID(Integer keyID) {
        this.keyID = keyID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ActionType getAction() {
        return actiontype;
    }

    public void setAction(ActionType action) {
        this.actiontype = action;
    }

    public User getUid() {
        return uid;
    }

    public void setUid(User uid) {
        this.uid = uid;
    }

    public Pattern getPatternID() {
        return patternID;
    }

    public void setPatternID(Pattern patternID) {
        this.patternID = patternID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (keyID != null ? keyID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subscription)) {
            return false;
        }
        Subscription other = (Subscription) object;
        if ((this.keyID == null && other.keyID != null) || (this.keyID != null && !this.keyID.equals(other.keyID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Subscription[ keyID=" + keyID + " ]";
    }
    
}
