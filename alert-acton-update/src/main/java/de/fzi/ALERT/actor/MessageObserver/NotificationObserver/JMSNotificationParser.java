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
package de.fzi.ALERT.actor.MessageObserver.NotificationObserver;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.input.SAXBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.fzi.ALERT.actor.Dao.MessageDAO;
import de.fzi.ALERT.actor.Dao.PatternDAO;
import de.fzi.ALERT.actor.Model.Message;
import de.fzi.ALERT.actor.Model.Pattern;

@Service
public class JMSNotificationParser {

	private static final String IdentityVerification = "ALERT.Recommender.IdentityVerification";
	private static final String IssueRecommendation = "ALERT.Recommender.IssueRecommendation";
	private static final String IdentityRecommendation = "ALERT.Recommender.IdentityRecommendation";
	private static final String SimilarIssueRssfeed = "ALERT.Recommender.AlertIssue";
	private static final String SimilarIssues = "ALERT.CEP.SimilarIssues";
	private PatternDAO patternDAO;

	@Autowired
	public void setPatternDAO(PatternDAO patternDAO) {
		this.patternDAO = patternDAO;
	}

	SAXBuilder builder;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public JMSNotificationParser() {
		builder = new SAXBuilder();
	}

	public Message parseJsoup(String msgString) {
		Message message = null;
		try {

			String s = msgString.replaceAll("[\n\r]", "");
			org.jsoup.nodes.Document doc = Jsoup.parse(s);
			String eventName = doc.select("ns1|eventName").text();
			if (eventName.equals(IdentityRecommendation))
				message = parseIdentityRecommendation(doc);
			else if (eventName.equals(IdentityVerification))
				message = parseIdentityVerification(doc);
			else if (eventName.equals(IssueRecommendation))
				message = parseIssueRecommendation(doc);
			else if (eventName.equals(SimilarIssueRssfeed)) 
				parseIssueAlertIusse(doc);
			else if(eventName.equals(SimilarIssues))
				message = parseSimilarIssues(doc);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return message;
	}

	private void parseIssueAlertIusse(Document doc) {
		// TODO Auto-generated method stub
		Message message = null;
		RssFeedGenerator newRssFeedGenerator = new RssFeedGenerator();
		try {

			String content = "EventName: AlertIusse\n";
			message = new Message();
			Elements events = doc.select("ns1|eventData");
			org.jsoup.nodes.Element event = events.get(0);
			Elements elements = event.getAllElements();
			for (org.jsoup.nodes.Element element : elements) {
				content = content + element.tagName() + " : "
						+ element.ownText() + "\n";
			}
			System.out.println("content:" + content);
			newRssFeedGenerator.RssFeedXml("title", "Link", content);
			System.out.println("!!!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private Message parseIdentityRecommendation(Document doc) {
		Message message = null;
		try {

			Elements pID = doc.select("p|patternId");
			String patternId = pID.get(0).text();
			// String message = doc.select(query);
			Pattern pattern;
			pattern = patternDAO.findById(patternId);

			String content = "EventName: IdentityRecommendation\n";

			if (pattern != null) {
				message = new Message();
				message.setPatternId(pattern);
				Elements events = doc.select("ns1|eventData");
				org.jsoup.nodes.Element event = events.get(0);
				Elements elements = event.getAllElements();
				for (org.jsoup.nodes.Element element : elements) {
					content = content + element.tagName() + " : "
							+ element.ownText() + "\n";
				}

				message.setContent(content);
				message.setMsgDate(new Date());
				message.setMsgID(1);
			} else
				System.out.println("can't find patternID of the complex event:"
						+ patternId);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return message;
	}

	public Message parseIssueRecommendation(Document doc) {
		Message message = null;
		try {

			Elements pID = doc.select("p|patternId");
			String patternId = pID.get(0).text();
			// String message = doc.select(query);
			Pattern pattern;
			pattern = patternDAO.findById(patternId);

			String content = "EventName: IssueRecommendation\n";

			if (pattern != null) {
				message = new Message();
				message.setPatternId(pattern);

				Elements events = doc.select("ns1|eventData");
				org.jsoup.nodes.Element event = events.get(0);
				Elements elements = event.getAllElements();
				for (org.jsoup.nodes.Element element : elements) {
					content = content + element.tagName() + " : "
							+ element.ownText() + "\n";
				}
				message.setContent(content);
				message.setMsgDate(new Date());
				message.setMsgID(1);
			} else
				System.out.println("can't find patternID of the complex event:"
						+ patternId);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return message;
	}

	public Message parseIdentityVerification(Document doc) {
		Message message = null;
		try {

			Elements pID = doc.select("p|patternId");
			String patternId = pID.get(0).text();
			// String message = doc.select(query);
			Pattern pattern;
			pattern = patternDAO.findById(patternId);

			String content = "EventName: IdentityVerification\n";

			if (pattern != null) {
				message = new Message();
				message.setPatternId(pattern);
				Elements events = doc.select("ns1|eventData");
				org.jsoup.nodes.Element event = events.get(0);
				Elements elements = event.getAllElements();
				for (org.jsoup.nodes.Element element : elements) {
					content = content + element.tagName() + " : "
							+ element.ownText() + "\n";
				}
				message.setContent(content);
				message.setMsgDate(new Date());
				message.setMsgID(1);
			} else
				System.out.println("can't find patternID of the complex event:"
						+ patternId);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return message;
	}
	
	public Message parseSimilarIssues(Document doc) {
		Message message = null;
		try {

			Elements pID = doc.select("p|patternId");
			String patternId = pID.get(0).text();
			// String message = doc.select(query);
			Pattern pattern;
			pattern = patternDAO.findById(patternId);

			String content = "Similar Issues\n";

			if (pattern != null) {
				message = new Message();
				message.setPatternId(pattern);
				
				Elements items = doc.select("item");
				for (org.jsoup.nodes.Element item : items) {
					String subject = item.getElementsByTag("subject").text();
					String issueurl=item.getElementsByTag("url").text();
					String similarity = item.getElementsByTag("similarity").text();
					content = content+ subject + "\n" +issueurl + "\n" +similarity + "\n";
				}
				
				message.setContent(content);
				message.setMsgDate(new Date());
				message.setMsgID(1);
			} else
				System.out.println("can't find patternID of the complex event:"
						+ patternId);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return message;
	}
}
