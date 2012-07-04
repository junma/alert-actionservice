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

import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.fzi.ALERT.actor.Dao.UserDAO;
import de.fzi.ALERT.actor.Model.User;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.Loginform;

@Service
public class UserLoginServiceImpl implements UserLoginService {

	private UserDAO userDAO;

	@Autowired
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Transactional
	public int checkUser(Loginform loginform) {
		// TODO Auto-generated method stub
		int userExist = 0;
		String userName = loginform.getUsername();
		String email = loginform.getEmail();

		List<User> userList = userDAO.findByUserName(userName);
		if (!userList.isEmpty()) {
			int userListSize = userList.size();
			for (int i = 0; i < userListSize; i++) {
				if (userList.get(i).getEmail().equals(email)) {
					userExist = 1;
				} else {
					userExist = 2;
				}

			}

		}

		return userExist;
	}

	public String getMsgAccount(String uid) {
		// TODO Auto-generated method stub
		User user = userDAO.findUserByUserid(uid).get(0);
		
		String msgacc=user.getMessageAccount();
        if (msgacc==null){
        	
        	return user.getEmail();
        }else{
        	
		return user.getMessageAccount();}
	}

	public User findUserByName(String userName) {
		// TODO Auto-generated method stub
		return userDAO.findByUserName(userName).get(0);
	}

	public User findUserById(String uid) {
		// TODO Auto-generated method stub
		return userDAO.findUserByUserid(uid).get(0);
	}

	public boolean isNewUID(String uid) {

		List<User> userList = userDAO.findUserByUserid(uid);
		if (userList.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public void newuser(Loginform loginform, String uid) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setUsername(loginform.getUsername());
		user.setEmail(loginform.getEmail());
		user.setUid(uid);
		user.setLastDate(Calendar.getInstance().getTime());
		user.setMessageAccount(loginform.getEmail());
		user.setUnreadmsg("0");
		
		userDAO.addUser(user);
	}
}
