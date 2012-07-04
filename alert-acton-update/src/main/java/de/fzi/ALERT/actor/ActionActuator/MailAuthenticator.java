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
package de.fzi.ALERT.actor.ActionActuator;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
	
public class MailAuthenticator extends Authenticator{
	private final String username;
	private final String password;
	
    public MailAuthenticator(String user, String password) {
        this.username = user;
        this.password = password;
    }
    
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(this.username, this.password);
    }


}
