package de.fzi.ALERT.actor.SubscriptionEditor.Form;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.RssContent;
import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Content;
import com.sun.syndication.feed.rss.Item;
public class RssViewer extends AbstractRssFeedView  {

	@Override
	protected void buildFeedMetadata(Map<String, Object> model, Channel feed,
			HttpServletRequest request) {
		
		feed.setTitle(model.get("feed").toString());
		feed.setDescription("RSS Feed for"+ model.get("feed").toString());
		feed.setLink("http://fzi.de");
		
		super.buildFeedMetadata(model, feed, request);
	}
	
	
	@Override
	protected List<Item> buildFeedItems(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		@SuppressWarnings("unchecked")		
		List<RssContent> listContent = (List<RssContent>) model.get("feedContent");
		
		List<Item> items = new ArrayList<Item>(listContent.size());
		
		for(RssContent tempContent : listContent ){
		
			Item item = new Item();
			
			Content content = new Content();
			content.setValue(tempContent.getSummary());
			item.setContent(content);
			
			item.setTitle(tempContent.getTitle());
			item.setLink(tempContent.getUrl());
			item.setPubDate(tempContent.getCreatedDate());
			
			items.add(item);
		}
		
		return items;
	}






	
}
