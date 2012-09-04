package de.fzi.ALERT.actor.SubscriptionEditor.Form;

import java.util.List;

public class PatternWithMsg {

	private String patternId;
	private String patternName;
	private String patternurl;
	private List<RssContent> rsscontList;

	
	public String getPatternId() {
		return patternId;
	}

	public void setPatternId(String patternId) {
		this.patternId = patternId;
	}

	public String getPatternName() {
		return patternName;
	}

	public void setPatternName(String patternName) {
		this.patternName = patternName;
	}

	public List<RssContent> getRsscontList() {
		return rsscontList;
	}

	public void setRsscontList(List<RssContent> rsscontList) {
		this.rsscontList = rsscontList;
	}

	public String getPatternurl() {
		return patternurl;
	}

	public void setPatternurl(String patternurl) {
		this.patternurl = patternurl;
	}

};
