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
package de.fzi.ALERT.actor.Dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.fzi.ALERT.actor.Model.Pattern;
import de.fzi.ALERT.actor.Model.User;

@Repository
@Transactional
public class PatternDAO extends AbstractDAO<Pattern> {

	public PatternDAO() {
		super(Pattern.class);
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	protected SessionFactory getSessionFactory() {
		// TODO Auto-generated method stub
		return this.sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public Pattern findByPatternName(String patternName) {
		// TODO Auto-generated method stub

		List<Pattern> patternList = new ArrayList<Pattern>();

		patternList = sessionFactory.getCurrentSession()
				.createQuery("from Pattern where patternName = ?")
				.setParameter(0, patternName).list();

		if (patternList.size() > 0) {
			return patternList.get(0);
		} else {
			return null;
		}
	}

	// list all the patterns from pattern table
	@SuppressWarnings("unchecked")
	public List<Pattern> listPattern() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from Pattern")
				.list();
	}

	@SuppressWarnings("unchecked")
	public Pattern findById(String patternId) {
		List<Pattern> patternList = new ArrayList<Pattern>();

		patternList = sessionFactory.getCurrentSession()
				.createQuery("from Pattern where patternID = ?")
				.setParameter(0, patternId).list();

		if (patternList.size() > 0) {
			return patternList.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public boolean checkIfModify(){
		
		List<Pattern> patternList = new ArrayList<Pattern>();

		patternList = sessionFactory.getCurrentSession()
				.createQuery("from Pattern where status > 0").list();

		if (patternList.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
}
