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
package de.fzi.ALERT.actor.SubscriptionEditor.Controller;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import de.fzi.ALERT.actor.Model.User;
import de.fzi.ALERT.actor.SubscriptionController.SubscriptionControllService;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.Loginform;
import de.fzi.ALERT.actor.SubscriptionEditor.Service.PatternListService;
import de.fzi.ALERT.actor.SubscriptionEditor.Service.UserLoginService;

import javax.swing.JOptionPane;

/**
 * Controller of Login Form
 * 
 * @author cibobo
 * 
 */
@Controller
@SessionAttributes({ "loginform", "sPattern", "uid" })
public class LoginformController {

	private UserLoginService userLoginService;

	/**
	 * set service
	 * 
	 * @param loginformService
	 */

	@Autowired
	public void setUserLoginService(UserLoginService userLoginService) {
		this.userLoginService = userLoginService;
	}

	/**
	 * beginning page
	 * 
	 * @param model
	 * @return
	 */

	@ModelAttribute("loginform")
	public Loginform getLoginform() {
		return new Loginform();
	}

	@RequestMapping(value = "/loginform.html", method = RequestMethod.GET)
	public String showLoginform(ModelMap model,
			@ModelAttribute("loginform") Loginform loginform) {

		return "loginform";
	}

//	/**
//	 * funtion will be called, when the Sumit button is push
//	 * 
//	 * @param loginform
//	 *            set an Instance as the Input
//	 * @return
//	 */
//	@RequestMapping(value = "/loginform.html", method = RequestMethod.POST)
//	public String onSubmit(ModelMap model,
//			@ModelAttribute("loginform") Loginform loginform) {
//
//		int result = userLoginService.checkUser(loginform);
//
//		if (result == 1) {
//			String uid = userLoginService.findUserByName(
//					loginform.getUsername()).getUid();
//			model.addAttribute("uid", uid);
//			return "redirect:main.html";
//		} else if (result == 0) {
//
//			return "redirect:register.html";
//		} else {
//			// JOptionPane.showMessageDialog(null, "Please check your input!");
//			return "redirect:loginform.html";
//		}
//	}
	
	
	/**
	 * funtion will be called, when the Sumit button is push. This function is only used for ALERT UI
	 * 
	 * @param loginform
	 *            set an Instance as the Input
	 * @return
	 */
	@RequestMapping(value = "/loginform.html", method = RequestMethod.POST)
	public String onSubmit(ModelMap model,
			@ModelAttribute("loginform") Loginform loginform) {

		boolean isNew = userLoginService.isNewUID(loginform.getUid());
		if (!isNew) {
			model.addAttribute("uid", loginform.getUid());
			return "redirect:main.html";
		} else {

			userLoginService.newuser(loginform, loginform.getUid());
			model.addAttribute("uid", loginform.getUid());			
			return "redirect:main.html";
		}
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)	
	public String register(ModelMap model,
			@ModelAttribute("loginform") Loginform loginform,
			@RequestParam("newuid") String newuid) {
		//model.addAttribute("uid", uid);
     
		if (userLoginService.isNewUID(newuid) == false) {
			
			return "redirect:register.html";
		} else {
			
			userLoginService.newuser(loginform, newuid);
			model.addAttribute("uid", newuid);			
			return "redirect:main.html";
		}

	}
	
	@RequestMapping(value = "/register.html", method = RequestMethod.GET)
	public String registerview(ModelMap model,
			@ModelAttribute("loginform") Loginform loginform){
		
		/*model.addAttribute("loginform", loginform);
		 * int n = JOptionPane.showConfirmDialog(null,
		 * "Do you want to register?", "No User Information!!",
		 * JOptionPane.YES_NO_OPTION);
		 * 
		 * if (n == JOptionPane.YES_OPTION) {
		 * 
		 * String uid = JOptionPane.showInputDialog(null,
		 * "Please input your User-ID"); if (uid == null) { return
		 * "redirect:loginform.html"; } else { if
		 * (userLoginService.checkUID(uid) == false) {
		 * JOptionPane.showMessageDialog(null,
		 * "Your User-ID is already registered!"); return
		 * "redirect:loginform.html"; } else {
		 * userLoginService.newuser(loginform, uid); // String uid = //
		 * userLoginService.findUserByName(loginform.getUsername()).getUid();
		 * model.addAttribute("uid", uid); return "redirect:main.html"; } } }
		 * else {
		 * 
		 * return "redirect:loginform.html"; }
		 */
		return "registerview";
	}

}
