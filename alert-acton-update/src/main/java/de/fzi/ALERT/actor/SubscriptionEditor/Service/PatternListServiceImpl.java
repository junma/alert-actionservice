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
package de.fzi.ALERT.actor.SubscriptionEditor.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.fzi.ALERT.actor.Dao.MessageDAO;
import de.fzi.ALERT.actor.Dao.PatternDAO;
import de.fzi.ALERT.actor.Model.Pattern;

public class PatternListServiceImpl implements PatternListService {

	private PatternDAO patternDAO;

	@Autowired
	public void setpatternDAO(PatternDAO patternDAO) {
		this.patternDAO = patternDAO;
	}

	public void createTestPattern() {
	}

	public List<String> getPatternNameList() {

		List<String> patternNameList = new ArrayList<String>();
		List<Pattern> patternList = patternDAO.listPattern();
		int patternListSize = patternList.size();
		for (int i = 0; i < patternListSize; i++) {
			patternNameList.add(patternList.get(i).getPatternName());
		}

		return patternNameList;
	}

	public List<Pattern> getPatternList() {
		// TODO Auto-generated method stub
		List<Pattern> patternList = patternDAO.listPattern();

		return patternList;
	}

	/*
	 * public String getPatternDescription(String patternName) { // TODO
	 * Auto-generated method stub String patternDescription =
	 * "no pattern description"; if(patternName.equals("pattern1")){
	 * patternDescription = "This is the first pattern"; }
	 * if(patternName.equals("pattern2")){ patternDescription =
	 * "This is the second pattern"; } return patternDescription; }
	 */

	public Pattern findPatternByName(String patternName) {
		// TODO Auto-generated method stub

		return patternDAO.findByPatternName(patternName);
	}

	public Pattern findPatternById(String patternId) {
		return patternDAO.findById(patternId);
	}

	public String checkIfModify(int num) {
		// TODO Auto-generated method stub
		boolean ifmod = patternDAO.checkIfModify();
		boolean addOrDel = false;

		List<Pattern> list = patternDAO.listPattern();
		if (list.size() != num) {
			addOrDel = true;
		}
		if (ifmod || addOrDel) {
			return "true";
		} else {
			return "false";
		}
	}

	public String getDescription(String patternId) {
		// TODO Auto-generated method stub
		Pattern pattern = patternDAO.findById(patternId);
		String description = pattern.getDescription();
		if (description == null) {
			description = "No description!";
		}
		return description;
	}

}
