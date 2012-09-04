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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.jar.Attributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.RssContent;
import de.fzi.ALERT.actor.SubscriptionController.SubscriptionControllService;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.Loginform;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.PatternForm;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.Preferences;
import de.fzi.ALERT.actor.SubscriptionEditor.Service.PatternListService;
import de.fzi.ALERT.actor.SubscriptionEditor.Service.UserLoginService;

@Controller
/*
 * to set the SessionAttribute to save the Username in loginform and save the
 * selected Pattern in sPattern
 */
@SessionAttributes({ "loginform", "sPattern", "sPatternName", "patternDescription",
		"patternList", "status", "preferences", "patternForm",
		"messageAccount", "ifPatternModify", "patternNur", "uid" })
public class ActionController {

	private PatternListService patternListService;

	@Autowired
	public void setPatternListService(PatternListService patternListService) {
		this.patternListService = patternListService;
	}

	private SubscriptionControllService actionService;

	@Autowired
	public void setActionService(SubscriptionControllService actionService) {
		this.actionService = actionService;
	}

	private UserLoginService userService;

	@Autowired
	public void setUserLoginService(UserLoginService userService) {
		this.userService = userService;
	}

	@ModelAttribute("messageAccount")
	public String setMessageAccount(@ModelAttribute("uid") String uid) {
		
		return userService.getMsgAccount(uid);
	}

	@ModelAttribute("sPattern")
	public String setSPattern() {
		return "";
	}
	
	@ModelAttribute("sPatternName")
	public String setSPatternName() {
		return "";
	}

	@ModelAttribute("ifPatternModify")
	public boolean setIfPatternModify() {
		return false;
	}

	@ModelAttribute("preferences")
	public Preferences getPatternForm() {
		return new Preferences();
	}

	@RequestMapping(value = "/action", method = RequestMethod.POST)
	public String actionHandling(ModelMap model,
			@ModelAttribute("preferences") Preferences preferences,
			@ModelAttribute("sPattern") String sPattern,
			@ModelAttribute("patternList") List<PatternForm> patternFormList,
			@ModelAttribute("uid") String uid) {

		PatternForm patternform = actionService.createNewPatternForm(sPattern,
				preferences);

		actionService.updateSubscription(uid, patternform);
		actionService.updatePatternFormList(patternFormList, patternform);

		model.addAttribute("status",
				actionService.preferencesToString(preferences));

		return "redirect:/listPattern.html";
	}

	/**
	 * beginning page
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/listPattern.html", method = RequestMethod.GET)
	public String showMessageList(ModelMap model,
			@ModelAttribute("uid") String uid) {
		// calling service to get the username
		List<PatternForm> list = actionService.getSubscriptionFormList(uid);
		model.addAttribute("patternList", list);
		model.addAttribute("patternNur", list.size());
		String msgacc = userService.getMsgAccount(uid);
		model.addAttribute("messageAccount", msgacc);

		return "action";
	}

	@RequestMapping(value = "/ifModify.html", method = RequestMethod.GET)
	public String patternStatus(ModelMap model,
			@ModelAttribute("patternNur") int patternNum) {

		String ifMod = patternListService.checkIfModify(patternNum);
		// System.out.println("The pattern list is modified? "+ifMod);
		model.addAttribute("ifPatternModify", ifMod);
		return "patternUpdate";
	}

	@RequestMapping(value = "/choosedPattern", method = RequestMethod.GET)
	public String choosingPattern(ModelMap model,
			@RequestParam("patternId") String patternId,
			@ModelAttribute("patternList") List<PatternForm> patternFormList) {

		model.addAttribute("sPattern", patternId);
		model.addAttribute("sPatternName", patternListService.findPatternById(patternId).getPatternName());
		model.addAttribute("patternDescription",
				patternListService.getDescription(patternId));

		Preferences prerefences = actionService.getSubscriptionTypeForPattern(
				patternFormList, patternId);
		model.addAttribute("status",
				actionService.preferencesToString(prerefences));
		model.addAttribute("preferences", prerefences);

		return "redirect:/listPattern.html";
	}

	@RequestMapping(value = "/modifyMsgAccount", method = RequestMethod.GET)
	public String modifyMsgAccount(ModelMap model,
			@ModelAttribute("uid") String uid,
			@RequestParam("msgAccount") String msgAccount) {

		actionService.modifyMessageAccount(msgAccount, uid);
		model.addAttribute("messageAccount", msgAccount);
		return "redirect:/listPattern.html";
	}
	@RequestMapping(value = "/logout.html", method = RequestMethod.GET)
	public String logout(ModelMap model){		
		model.clear();			
		return "redirect:loginform.html";
	}
}





























