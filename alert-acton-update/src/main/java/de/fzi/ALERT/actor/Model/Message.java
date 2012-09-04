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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cibobo
 */
@Entity
@Table(name = "msgtb")
@XmlRootElement
public class Message implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "MsgID")
    private Integer msgID;
    
	@Basic(optional = false)
    @NotNull
    @Column(name = "Content")
    private String content;
	
	@Basic(optional = false)
    @Column(name = "Subject")
    private String subject;
	
	@Basic(optional = false)
    @Column(name = "Summary")
    private String summary;
    
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "MsgDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date msgDate;
    
	@JoinColumn(name = "PatternId", referencedColumnName = "PatternID")
    @ManyToOne(optional = false)
    private Pattern patternId;

    public Message() {
    }

    public Message(Integer msgID) {
        this.msgID = msgID;
    }

    public Message(Integer msgID, String content) {
        this.msgID = msgID;
        this.content = content;
    }

    public Integer getMsgID() {
        return msgID;
    }

    public void setMsgID(Integer msgID) {
        this.msgID = msgID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(Date msgDate) {
        this.msgDate = msgDate;
    }

    public Pattern getPatternId() {
        return patternId;
    }

    public void setPatternId(Pattern patternId) {
        this.patternId = patternId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (msgID != null ? msgID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.msgID == null && other.msgID != null) || (this.msgID != null && !this.msgID.equals(other.msgID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Message[ msgID=" + msgID + " ]";
    }
    
}

