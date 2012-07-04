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

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Entity
@Table(name = "actiontb")
@XmlRootElement
public class ActionType implements Serializable {
	
	private static final long serialVersionUID = 1L;
    
	@Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ActionID")
    private Integer actionID;
    
    @NotNull
    @Column(name = "ActionName")
    private String actionName;
    
    @OneToMany(mappedBy = "actiontype")
    @Cascade({CascadeType.MERGE,CascadeType.PERSIST})
    private Collection<Subscription> subscriptionCollection;
	
    public ActionType(){
    	
    }
    
    public int getActionID(){
    	return this.actionID;
    }
    
    public void setActionID(int actionID){
    	this.actionID = actionID;
    }
    
    public String getActionName(){
    	return this.actionName;
    }
    
    public void setActionName(String actionName){
    	this.actionName = actionName;
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
        hash += (actionID != null ? actionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        ActionType other = (ActionType) object;
        if ((this.actionID == null && other.actionID != null) || (this.actionID != null && !this.actionID.equals(other.actionID))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "model.Actiontypetb[ ActionID=" + actionID + " ]";
    }
}
