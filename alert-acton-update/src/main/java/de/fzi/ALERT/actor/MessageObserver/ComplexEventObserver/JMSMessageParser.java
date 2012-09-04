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
package de.fzi.ALERT.actor.MessageObserver.ComplexEventObserver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import de.fzi.ALERT.actor.Dao.MessageDAO;
import de.fzi.ALERT.actor.Dao.PatternDAO;
import de.fzi.ALERT.actor.Model.Message;
import de.fzi.ALERT.actor.Model.Pattern;
import de.fzi.ALERT.actor.SubscriptionController.JMSMessageManager;

@Service
public class JMSMessageParser {

	private int errorCount = 0;

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

	SAXBuilder builder;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public JMSMessageParser() {
		builder = new SAXBuilder();
	}

	public Message parse(String msgString) {
		Message message = new Message();
		StringReader in = new StringReader(msgString);
		try {
			Document doc = builder.build(in);
			Element root = doc.getRootElement();
			Namespace ns = root.getNamespace();

			String id = root.getChild("MsgID").getTextTrim();
			message.setMsgID(Integer.valueOf(id));

			String patternID = root.getChild("PatternID").getTextTrim();
			Pattern pattern = (Pattern) patternDAO.find(Integer
					.valueOf(patternID));
			message.setPatternId(pattern);
			// Collection<Message> messageList = pattern.getMsgtbCollection();
			// messageList.add(message);
			// pattern.setMsgtbCollection(messageList);

			message.setContent(root.getChild("Content").getTextTrim());

			String date = root.getChild("MsgDate").getTextTrim();
			// System.out.println("The input time is: "+date);
			message.setMsgDate(format.parse(date));

			// int testID = message.getPatternId().getPatternID();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}

	public Message XMLFileParse(String msgString) {

		Message message = new Message();

		try {

			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			builder.setErrorHandler(new MyErrorHandler());

			InputStream in = new ByteArrayInputStream(
					msgString.getBytes("UTF-8"));
			org.w3c.dom.Document doc = builder.parse(in);

			XPath xpath = XPathFactory.newInstance().newXPath();
			// XPath Query for showing all nodes value
			javax.xml.xpath.XPathExpression expr = xpath
					.compile("//patternID/text()");

			Object result = expr.evaluate(doc, XPathConstants.NODESET);

			NodeList nodes = (NodeList) result;
			Pattern pattern = new Pattern();
			if (nodes.getLength() > 0) {
				pattern = patternDAO.findById(nodes.item(0).getNodeValue());
			}
			if (pattern != null) {

				message.setPatternId(pattern);
				for (int i = 0; i < nodes.getLength(); i++) {
					System.out.println(nodes.item(i).getNodeValue());
				}

				javax.xml.xpath.XPathExpression expr1 = xpath
						.compile("//alertcomplex/*/text()");

				Object result1 = expr1.evaluate(doc, XPathConstants.NODESET);
				NodeList nodes1 = (NodeList) result1;
				String content = "";
				if (nodes.getLength() > 0) {
					for (int i = 0; i < nodes1.getLength(); i++) {
						System.out.println("modes "
								+ nodes1.item(i).getParentNode().getNodeName());
						System.out.println(nodes1.item(i).getNodeValue());
						content += nodes1.item(i).getNodeValue();
					}
				}
				message.setSubject("complex event");
				message.setSummary("default summary");
				message.setContent(content);
				message.setMsgDate(new Date());
				message.setMsgID(1);
			} else {
				message.setContent("ERROR!");
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			message.setContent("ERROR!");
			System.out.println(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			message.setContent("ERROR!");
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			message.setContent("ERROR!");
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			message.setContent("ERROR!");
			System.out.println(e.getMessage());
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			message.setContent("ERROR!");
			System.out.println(e.getMessage());
		}

		return message;

	}

	public void validateXml(Schema schema, org.w3c.dom.Document doc) {
		try {
			// creating a Validator instance
			Validator validator = schema.newValidator();
			System.out.println();
			System.out.println("Validator Class: "
					+ validator.getClass().getName());

			// setting my own error handler
			validator.setErrorHandler(new MyErrorHandler());

			// validating the document against the schema
			validator.validate(new DOMSource(doc));
			System.out.println();
			if (errorCount > 0) {
				System.out.println("Failed with errors: " + errorCount);
			} else {
				System.out.println("Passed.");
			}

		} catch (Exception e) {
			// catching all validation exceptions
			System.out.println();
			System.out.println(e.toString());
		}
	}

	public Message parseJsoup(String msgString) {
		Message message = null;
		try {

			String s = msgString.replaceAll("[\n\r]", "");
			org.jsoup.nodes.Document doc = Jsoup.parse(s);

			Elements pID = doc.select("patternID");
			String patternId = pID.get(0).text();
			System.out.println("patternID:" + patternId + "--");
			// String message = doc.select(query);
			Pattern pattern;
			pattern = patternDAO.findById(patternId);

			String content = "";
			if (pattern != null) {
				message = new Message();
				message.setPatternId(pattern);
				Elements events = doc.select("ns1|event");
				org.jsoup.nodes.Element event = events.get(0);
				Elements elements = event.getAllElements();
				for (org.jsoup.nodes.Element element : elements) {
					if (!element.tagName().contains("ns1:event")
							&& !element.tagName().contains("patternid"))
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

	public Schema loadSchema(String name) {
		Schema schema = null;
		try {
			String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
			SchemaFactory factory = SchemaFactory.newInstance(language);
			schema = factory.newSchema(new File(name));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return schema;
	}

	class MyErrorHandler implements ErrorHandler {

		public void error(SAXParseException exception) throws SAXException {
			// TODO Auto-generated method stub
			System.out.println(exception.toString());
			errorCount++;
		}

		public void fatalError(SAXParseException exception) throws SAXException {
			// TODO Auto-generated method stub
			System.out.println(exception.toString());
			throw exception;
		}

		public void warning(SAXParseException exception) throws SAXException {
			// TODO Auto-generated method stub
			System.out.println(exception.toString());
		}

	}
}
