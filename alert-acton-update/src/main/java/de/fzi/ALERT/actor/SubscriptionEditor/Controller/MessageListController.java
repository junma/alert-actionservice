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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import de.fzi.ALERT.actor.ActionActuator.MessageListService;
import de.fzi.ALERT.actor.Model.Message;
import de.fzi.ALERT.actor.Model.Pattern;
import de.fzi.ALERT.actor.Model.User;
import de.fzi.ALERT.actor.SubscriptionController.SubscriptionControllService;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.Loginform;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.MessageForm;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.PatternForm;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.RssContent;
import de.fzi.ALERT.actor.SubscriptionEditor.Service.PatternListService;
import de.fzi.ALERT.actor.SubscriptionEditor.Service.UserLoginService;

@Controller
/*
 * to set the SessionAttribute to save the Username in Session
 */
@SessionAttributes({ "loginform", "msgList", "messageNum", "unReadMsgNum",
		"unReadMsgList", "uid" })
public class MessageListController {
	private PatternListService patternListService;

	@Autowired
	public void setPatternListService(PatternListService patternListService) {
		this.patternListService = patternListService;
	}

	private MessageListService messageListService;

	@Autowired
	public void serMessageListService(MessageListService messageListService) {
		this.messageListService = messageListService;
	}

	private UserLoginService userLoginService;

	@Autowired
	public void setUserLoginService(UserLoginService userLoginService) {
		this.userLoginService = userLoginService;
	}

	@ModelAttribute("unReadMsgList")
	public List<MessageForm> getMsgList(@ModelAttribute("uid") String uid) {
		return messageListService.getUnreadedMsg(uid);
	}

	@ModelAttribute("msgList")
	public List<MessageForm> getUnReadMsgList(@ModelAttribute("uid") String uid) {
		User user = userLoginService.findUserById(uid);
		return messageListService.getAllMsgForUser(user);
	}

	@ModelAttribute("showUnread")
	public boolean showUnread() {
		return false;
	}

	/**
	 * beginning page
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/showmessagelist.html", method = RequestMethod.GET)
	public String showMessageList(ModelMap model,
			@ModelAttribute("msgList") List<MessageForm> msgList,
			@ModelAttribute("unReadMsgList") List<MessageForm> unReadMsgList) {

		model.addAttribute("messageNum", msgList.size());
		model.addAttribute("unReadMsgNum", 0);
		model.addAttribute("newMessageList",
				messageListService.get5Msg(msgList, unReadMsgList.size(), 0));

		return "messagelist";
	}

	@RequestMapping(value = "/messagelist.html", method = RequestMethod.GET)
	public String show5Messages(ModelMap model,
			@ModelAttribute("msgList") List<MessageForm> msgList,
			@ModelAttribute("unReadMsgList") List<MessageForm> unReadMsgList,
			@RequestParam("pageIndex") int pageIndex,
			@ModelAttribute("showUnread") boolean showUnread,
			@ModelAttribute("loginform") Loginform loginform) {

		String userName = loginform.getUsername();
		User user = userLoginService.findUserByName(userName);
		msgList = messageListService.getAllMsgForUser(user);
		model.addAttribute("msgList", msgList);
		model.addAttribute("messageNum", msgList.size());
		model.addAttribute("newMessageList", messageListService.get5Msg(
				msgList, unReadMsgList.size(), pageIndex));

		return "messagelist";
	}

	@RequestMapping(value = "/getunreadmsg.html", method = RequestMethod.GET)
	public String getUnreadMsg(ModelMap model, @ModelAttribute("uid") String uid) {

		model.addAttribute("unReadMsgNum", messageListService.getUnreadMsg(uid)
				.size());

		return "unread";

	}

	@RequestMapping(value = "/showUnreadedMsg.html", method = RequestMethod.GET)
	public String showUnreadMsg(ModelMap model,
			@ModelAttribute("uid") String uid) {

		User user = userLoginService.findUserById(uid);
		List<MessageForm> unreadedMsg = messageListService.getUnreadedMsg(uid);
		List<MessageForm> allMsg = messageListService.getAllMsgForUser(user);
		System.out.println("user "+uid+"  unrMSG "+unreadedMsg.size()+"  msgs "+allMsg.size());
		model.addAttribute("msgList", allMsg);
		model.addAttribute("unReadMsgList", unreadedMsg);
		model.addAttribute("messageNum", unreadedMsg.size());
		model.addAttribute("newMessageList",
				messageListService.get5Msg(allMsg, unreadedMsg.size(), 0));

		return "messagelist";

	}

	@RequestMapping(value = "/rssFeed", method = RequestMethod.GET)
	public ModelAndView getFeedInRss(ModelMap model,
			@RequestParam("patternName") String patternName) {
		Pattern rssPattern = patternListService.findPatternByName(patternName);
		List<MessageForm> rssMsg = messageListService
				.getAllMsgForPattern(rssPattern);

		List<RssContent> items = new ArrayList<RssContent>();

		if (rssMsg.isEmpty() == false) {
			if (rssMsg.size() > 10) {
				for (int i = rssMsg.size()-1; i >rssMsg.size()-11; i--) {
					RssContent content = new RssContent();
					content.setTitle(rssMsg.get(i).getMessageSubject());
					content.setUrl("#");
					content.setSummary(rssMsg.get(i).getMessageContent());
					content.setCreatedDate(rssMsg.get(i).getMessageDate());
					items.add(content);
				}
			} else {
				for (int i = rssMsg.size()-1; i > -1; i--) {
					RssContent content = new RssContent();
					content.setTitle(rssMsg.get(i).getMessageSubject());
					content.setUrl("#");
					content.setSummary(rssMsg.get(i).getMessageContent());
					content.setCreatedDate(rssMsg.get(i).getMessageDate());
					items.add(content);
				}
			}
		}
		;

		ModelAndView mav = new ModelAndView();
		mav.setViewName("rssViewer");
		mav.addObject("feedContent", items);
		mav.addObject("feed", patternName);

		return mav;

	}
	
	@RequestMapping(value = "/showMsg.html", method = RequestMethod.GET)
	public ModelAndView showMsg(ModelMap model,
			@ModelAttribute("msgList") List<MessageForm> msgList,
			@RequestParam("msgId") int msgId) {

		MessageForm msg = new MessageForm();
		int msgListSize = msgList.size();
		if(msgListSize>0){
			for(int i=0;i<msgListSize;i++){
				if(msgList.get(i).getMessageId() == msgId){
					msg = msgList.get(i);
				}
			}
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("showMsg");
		mav.addObject("message",msg);

		return mav;
	}
}
