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

import de.fzi.ALERT.actor.Model.User;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.Loginform;

public interface UserLoginService {
	/**
	 * check whether the username exist or not
	 * 
	 * @param loginform
	 * @return
	 */

	public int checkUser(Loginform loginform);

	public String getMsgAccount(String uid);

	public User findUserByName(String userName);

	public User findUserById(String uid);

	public void newuser(Loginform loginform, String uid);
	public boolean isNewUID(String uid);
}
