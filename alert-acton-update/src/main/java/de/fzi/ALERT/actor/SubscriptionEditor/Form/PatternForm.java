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

import java.util.List;

public class PatternForm {	
	
	private String patternName;
	private boolean status;
	private List<String> actionList;

	public PatternForm(String name, boolean status , List<String> actionList){
		this.patternName = name;
		this.status = status;
		this.actionList = actionList;
	}
	
	public PatternForm(){
		
	}
	

	public boolean isStatus() {
		return status;
	}



	public void setStatus(boolean status) {
		this.status = status;
	}



	public List<String> getActionList() {
		return actionList;
	}



	public void setActionList(List<String> actionList) {
		this.actionList = actionList;
	}



	public String getPatternName() {
		return patternName;
	}



	public void setPatternName(String patternName) {
		this.patternName = patternName;
	}

}
