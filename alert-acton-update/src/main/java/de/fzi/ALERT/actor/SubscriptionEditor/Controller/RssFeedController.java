/*copyright*/
package de.fzi.ALERT.actor.SubscriptionEditor.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import de.fzi.ALERT.actor.ActionActuator.MessageListService;
import de.fzi.ALERT.actor.Model.Message;
import de.fzi.ALERT.actor.Model.Pattern;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.MessageForm;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.PatternWithMsg;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.RssContent;
import de.fzi.ALERT.actor.SubscriptionEditor.Service.PatternListService;

@Controller
/*
 * to set the SessionAttribute to save the Username in Session
 */
public class RssFeedController {
	private PatternListService patternListService;
	private static String RSS_FEED_LINK_SUFFIX = "/rssfeedContent";
	private static String RSS_FEED_LINK_FULL = "/alert-acton/rssfeedContent";
	private static String RSS_FEED_LINK_PATTERN = "/alert-acton/rssFeed?patternId=";

	@Autowired
	public void setPatternListService(PatternListService patternListService) {
		this.patternListService = patternListService;
	}

	private MessageListService messageListService;

	@Autowired
	public void serMessageListService(MessageListService messageListService) {
		this.messageListService = messageListService;
	}

	@RequestMapping(value = "/rssFeed", method = RequestMethod.GET)
	public ModelAndView getFeedInRss(@RequestParam("patternId") String patternId) {
		Pattern rssPattern = patternListService.findPatternById(patternId);

		List<RssContent> items = new ArrayList<RssContent>();

		if (patternId.isEmpty()||patternId==null) {
			List<MessageForm> rssMsgAll = messageListService.getAllMsg();
			for (int i = 0; i < rssMsgAll.size(); i++) {
				RssContent content = new RssContent();
				content.setTitle(rssMsgAll.get(i).getMessageSubject());
				content.setUrl(RSS_FEED_LINK_SUFFIX + "?msgId="
						+ rssMsgAll.get(i).getMessageId());
				content.setSummary(rssMsgAll.get(i).getMessageSummary());
				content.setCreatedDate(rssMsgAll.get(i).getMessageDate());
				items.add(content);
			}
			ModelAndView mav = new ModelAndView();
			mav.setViewName("rssViewer");
			mav.addObject("feedContent", items);
			mav.addObject("feed", "all");

			return mav;
		} else {
			List<MessageForm> rssMsg = messageListService
					.getAllMsgForPattern(rssPattern);
			if (rssMsg.isEmpty() == false) {
				if (rssMsg.size() > 10) {
					for (int i = 0; i < 10; i++) {
						RssContent content = new RssContent();
						content.setTitle(rssMsg.get(i).getMessageSubject());
						content.setUrl(RSS_FEED_LINK_SUFFIX + "?msgId="
								+ rssMsg.get(i).getMessageId());
						content.setSummary(rssMsg.get(i).getMessageSummary());
						content.setCreatedDate(rssMsg.get(i).getMessageDate());
						items.add(content);
					}
				} else {
					for (int i = 0; i < rssMsg.size(); i++) {
						RssContent content = new RssContent();
						content.setTitle(rssMsg.get(i).getMessageSubject());
						content.setUrl(RSS_FEED_LINK_SUFFIX + "?msgId="
								+ rssMsg.get(i).getMessageId());
						content.setSummary(rssMsg.get(i).getMessageSummary());
						content.setCreatedDate(rssMsg.get(i).getMessageDate());
						items.add(content);
					}
				}
			}

			ModelAndView mav = new ModelAndView();
			mav.setViewName("rssViewer");
			mav.addObject("feedContent", items);
			mav.addObject("feed", rssPattern.getPatternName().toString());

			return mav;
		}
	}

	@RequestMapping(value = "/rssFeedsAll", method = RequestMethod.GET)
	public ModelAndView getFeedsAllInRss() {

		ModelAndView mav = new ModelAndView();

		List<PatternWithMsg> pwmList = new ArrayList<PatternWithMsg>();
		List<Pattern> patternList = patternListService.getPatternList();
		for (Pattern pattern : patternList) {

			PatternWithMsg pwm = new PatternWithMsg();
			pwm.setPatternName(pattern.getPatternName());
			pwm.setPatternId(pattern.getPatternID());
			pwm.setPatternurl(RSS_FEED_LINK_PATTERN + pattern.getPatternID());

			List<MessageForm> msgList = messageListService
					.getAllMsgForPattern(pattern);
			List<RssContent> rsscontList = new ArrayList<RssContent>();

			if (msgList.size() <= 5) {
				for (int i = 0; i < msgList.size(); i++) {
					RssContent content = new RssContent();
					content.setTitle(msgList.get(i).getMessageSubject());
					content.setUrl(RSS_FEED_LINK_FULL + "?msgId="
							+ msgList.get(i).getMessageId());
					content.setSummary(msgList.get(i).getMessageSummary());
					content.setCreatedDate(msgList.get(i).getMessageDate());
					rsscontList.add(content);
				}

			} else {
				for (int i = 0; i < 5; i++) {
					RssContent content = new RssContent();
					content.setTitle(msgList.get(i).getMessageSubject());
					content.setUrl(RSS_FEED_LINK_FULL + "?msgId="
							+ msgList.get(i).getMessageId());
					content.setSummary(msgList.get(i).getMessageSummary());
					content.setCreatedDate(msgList.get(i).getMessageDate());
					rsscontList.add(content);
				}
			}
			pwm.setRsscontList(rsscontList);
			pwmList.add(pwm);
		}

		mav.setViewName("rssFeedsAll");
		mav.addObject("pwmList", pwmList);

		return mav;

	}

	@RequestMapping(value = "/rssfeedContent", method = RequestMethod.GET)
	public ModelAndView showMsg(@RequestParam("msgId") int msgId) {

		MessageForm msg = messageListService.getMessgeByID(msgId);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("showMsg");
		mav.addObject("message", msg);

		return mav;
	}
}
