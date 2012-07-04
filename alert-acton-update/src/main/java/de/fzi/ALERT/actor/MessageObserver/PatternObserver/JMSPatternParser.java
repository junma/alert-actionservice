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
package de.fzi.ALERT.actor.MessageObserver.PatternObserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Hashtable;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.Text;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.InputSource;

import de.fzi.ALERT.actor.Dao.MessageDAO;
import de.fzi.ALERT.actor.Dao.PatternDAO;
import de.fzi.ALERT.actor.Model.Pattern;

public class JMSPatternParser {
	int actionType;
	SAXBuilder builder;

	private static final String wsntNsURI = "http://docs.oasis-open.org/wsn/b-2";
	private static final String patternNsURI = "http://www.alert-project.eu/panteon-events";
	private static final String alertNsURI = "http://www.alert-project.eu/";
	private static final String PATTERN_CREATED = "ALERT.Panteon.createPattern";
	private static final String PATTERN_MODIFIED = "ALERT.Panteon.modifyPattern";
	private static final String PATTERN_DELETED = "ALERT.Panteon.deletePattern";

	public JMSPatternParser() {
		this.builder = new SAXBuilder();
	}

	private PatternDAO patternDAO;

	@Autowired
	public void setPatternDAO(PatternDAO patternDAO) {
		this.patternDAO = patternDAO;
	}

	private MessageDAO messageDAO;

	@Autowired
	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}

	public Pattern xpathParsePatternEvent(String msgString) {
		System.out.println("pattern event:");
		String s2 = msgString.replaceAll("[\n\r]", "");
		System.out.println("new message: " + s2);

		System.out.println(msgString);
		Pattern pattern = new Pattern();
		StringReader in = new StringReader(s2);

		try {
			Document doc = this.builder.build(in);
			Element root = doc.getRootElement();
			String wsntNsPrefix;
			String patternNsPrefix;
			String alertNsPrefix;
			String patternEventName = null;
			String patternId = null;
			String cepatName = null;
			String cepatDescription = null;

			Hashtable<String, String> nstable = new Hashtable<String, String>();

			String rootprefix = root.getNamespacePrefix();
			if (rootprefix != null)
				nstable.put(root.getNamespaceURI(), rootprefix);

			List otherNs = root.getAdditionalNamespaces();
			if (otherNs != null) {
				for (int i = 0; i < otherNs.size(); i++) {
					Namespace ns = (Namespace) otherNs.get(i);
					nstable.put(ns.getURI(), ns.getPrefix());
				}
			}
			wsntNsPrefix = nstable.get(wsntNsURI);

			if (wsntNsPrefix != null) {
				XPath xpath = XPath.newInstance("//" + wsntNsPrefix
						+ ":Message/*");
				xpath.addNamespace(wsntNsPrefix, wsntNsURI);
				Object result = xpath.selectSingleNode(doc);
				if (result != null) {
					Element eventElement = (Element) result;
					String eventElementPrefix = eventElement
							.getNamespacePrefix();
					if (eventElementPrefix != null)
						nstable.put(eventElement.getNamespaceURI(),
								eventElementPrefix);
					List atrs = eventElement.getAdditionalNamespaces();
					for (int j = 0; j < atrs.size(); j++) {
						Namespace ans = (Namespace) atrs.get(j);
						nstable.put(ans.getURI(), ans.getPrefix());
					}
					patternNsPrefix = nstable.get(patternNsURI);
					alertNsPrefix = nstable.get(alertNsURI);
					if (alertNsPrefix != null && patternNsPrefix != null) {
						xpath.addNamespace(alertNsPrefix, alertNsURI);
						xpath.addNamespace(patternNsPrefix, patternNsURI);
						Object eventNameElementObj = XPath.selectSingleNode(
								eventElement, "//" + alertNsPrefix
										+ ":eventName/text()");
						if (eventNameElementObj != null) {
							Text eventNameElement = (Text) eventNameElementObj;
							patternEventName = eventNameElement.getText();
						}
						Object patternIdElementObj = XPath.selectSingleNode(
								eventElement, "//" + patternNsPrefix
										+ ":cepatUID");
						if (patternIdElementObj != null) {
							Element patternIdElement = (Element) patternIdElementObj;
							patternId = patternIdElement
									.getAttributeValue("value");
						}
						Object cepatNameElementObj = XPath.selectSingleNode(
								eventElement, "//" + patternNsPrefix
										+ ":cepatName");
						if (cepatNameElementObj != null) {
							Element cepatNameElement = (Element) cepatNameElementObj;
							cepatName = cepatNameElement
									.getAttributeValue("value");
						}
						Object cepatDescriptionElementObj = XPath
								.selectSingleNode(eventElement, "//"
										+ patternNsPrefix + ":cepatDescription");
						if (cepatDescriptionElementObj != null) {
							Element cepatDescriptionElement = (Element) cepatDescriptionElementObj;
							cepatDescription = cepatDescriptionElement
									.getAttributeValue("value");
						}

						if (patternEventName != null) {
							if (patternEventName.equals(PATTERN_CREATED))
								pattern.setStatus(0);
							else if (patternEventName.equals(PATTERN_MODIFIED))
								pattern.setStatus(1);
							else if (patternEventName.equals(PATTERN_DELETED))
								pattern.setStatus(2);
							else {
								System.out
										.println("The action type of the Pattern Message is wrong!");
							}
							System.out.println("pattern event type: "
									+ patternEventName + "  status:"
									+ pattern.getStatus());
							pattern.setPatternID(patternId);
							pattern.setPatternName(cepatName);
							pattern.setDescription(cepatDescription);

						}
					}
				}

			}
		} catch (JDOMException e) {
			System.out.println(msgString + " is not valid.");
			System.out.println(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Could not check " + msgString);
			System.out.println(" because " + e.getMessage());
		}
		return pattern;
	}

	public Pattern parseJsoup(String msgString) {
		Pattern pattern = new Pattern();
		try {
			String s = msgString.replaceAll("[\n\r]", "");
			org.jsoup.nodes.Document doc = Jsoup.parse(s);

			
			String patternId = doc.select("p|cepatUID, cepatUID").attr("value");
			String patternEventName = doc.select("ns1|eventName").text();
			String cepatName = doc.select("p|cepatName, cepatName").attr("value");
			String cepatDescription = doc.select("p|cepatDescription, cepatDescription").attr("value");

			if (patternEventName != null) {
				if (patternEventName.equals(PATTERN_CREATED))
					pattern.setStatus(0);
				else if (patternEventName.equals(PATTERN_MODIFIED))
					pattern.setStatus(1);
				else if (patternEventName.equals(PATTERN_DELETED))
					pattern.setStatus(2);
				else {
					System.out
							.println("The action type of the Pattern Message is wrong!");
				}
				System.out.println("pattern event type: " + patternEventName
						+ "  status:" + pattern.getStatus());
				patternId = patternId.replace("-", "");
				patternId = "a"+patternId;
				pattern.setPatternID(patternId);
				pattern.setPatternName(cepatName);
				pattern.setDescription(cepatDescription);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return pattern;
	}

	public Pattern parse(String msgString) {
		Pattern pattern = new Pattern();
		StringReader in = new StringReader(msgString);
		try {
			Document doc = this.builder.build(in);
			Element root = doc.getRootElement();
			Namespace ns = root.getNamespace();

			String patternID = root.getChild("PatternID").getTextTrim();
			pattern.setPatternID(patternID);

			String patternName = root.getChild("PatternName").getTextTrim();
			pattern.setPatternName(patternName);

			String action = root.getChild("Action").getTextTrim();
			if (action.equals("Create"))
				this.actionType = 1;
			else if (action.equals("Update"))
				this.actionType = 2;
			else if (action.equals("Delete"))
				this.actionType = 3;
			else {
				System.out
						.println("The action type of the Pattern Message is wrong!");
			}

			String description = root.getChild("Description").getTextTrim();
			pattern.setDescription(description);

			int defaultStatus = 0;
			pattern.setStatus(defaultStatus);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pattern;
	}

	public int getActionType() {
		return this.actionType;
	}
}