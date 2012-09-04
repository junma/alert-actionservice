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
package de.fzi.ALERT.actor.SubscriptionEditor.Form;

import java.util.Date;

public class MessageForm {

	private String messageContent;

	private Date messageDate;

	private String patternName;

	private int messageId;

	private boolean status;

	private String messageSubject;
	
	private String messageSummary;

	public String getMessageSummary() {
		return messageSummary;
	}

	public void setMessageSummary(String messageSummary) {
		this.messageSummary = messageSummary;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public MessageForm() {
	};

	public String getPatternName() {
		return patternName;
	}

	public void setPatternName(String patternName) {
		this.patternName = patternName;
	}

	public MessageForm(String content, Date time, String patternName, int id,
			boolean status) {
		this.messageContent = content;
		this.messageDate = time;
		this.patternName = patternName;
		this.messageId = id;
		this.status = status;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;

	}

	public void setMessageSubject(String subject) {
		this.messageSubject = subject;
	}

	public String getMessageSubject() {
		return messageSubject;
	}

	public Date getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

}
