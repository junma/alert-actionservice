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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author cibobo
 */
@Entity
@Table(name = "patterntb")
@XmlRootElement
public class Pattern implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PatternID")
    @Size(min = 1, max = 108)
    private String patternID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 96)
    @Column(name = "PatternName")
    private String patternName;
    @Size(max = 1539)
    @Column(name = "Description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Status")
    private int status;
    @Column(name = "complex_event")
    private byte[] complex_event;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patternId")
    private Collection<Message> messageCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patternID")
    private Collection<Subscription> subscriptionCollection;

    public Pattern() {
    }

    public Pattern(String patternID) {
        this.patternID = patternID;
    }

    public Pattern(String patternID, String patternName, int status) {
        this.patternID = patternID;
        this.patternName = patternName;
        this.status = status;
    }

    public String getPatternID() {
        return patternID;
    }

    public void setPatternID(String patternID) {
        this.patternID = patternID;
    }

    public String getPatternName() {
        return patternName;
    }

    public void setPatternName(String patternName) {
        this.patternName = patternName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public byte[] getComplex_event() {
        return complex_event;
    }

    public void setComplex_event(byte[] complex_event) {
        this.complex_event = complex_event;
    }

    @XmlTransient
    public Collection<Message> getMsgtbCollection() {
        return messageCollection;
    }

    public void setMsgtbCollection(Collection<Message> msgtbCollection) {
        this.messageCollection = msgtbCollection;
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
        hash += (patternID != null ? patternID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pattern)) {
            return false;
        }
        Pattern other = (Pattern) object;
        if ((this.patternID == null && other.patternID != null) || (this.patternID != null && !this.patternID.equals(other.patternID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Patterntb[ patternID=" + patternID + " ]";
    }
    
}

