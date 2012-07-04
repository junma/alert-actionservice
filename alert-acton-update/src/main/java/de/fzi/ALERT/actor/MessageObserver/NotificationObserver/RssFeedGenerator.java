package de.fzi.ALERT.actor.MessageObserver.NotificationObserver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.context.request.RequestScope;

public class RssFeedGenerator {
	/**
	 * @param args
	 */
	public static void main(String args[]) {
		RssFeedGenerator newRssFeedGenerator = new RssFeedGenerator();
		// newRssFeedGenerator.RssFeedXml("Example2","http://google.de/","Example");
	}
	
	
	public void RssFeedXmlAddItem(String title, String Link, String Desc)
			throws IOException {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"dd MMM yyyy HH:mm:ss Z");
		String today = formatter.format(new Date());
		RssFeedGenerator newRssFeedGenerator = new RssFeedGenerator();
		Channel newChannel = newRssFeedGenerator.new Channel();		

		Item item = newRssFeedGenerator.new Item();
		item.setTitle(title);
		item.setLink(Link);
		item.setDescription(Desc);
		newChannel.setPubDate(today);
		newChannel.addItem(item);

		// FileOutputStream fouthost = new
		// FileOutputStream("E:\\java 64\\apache-tomcat-7.0.22\\webapps\\alert-acton\\rssfeed.xml");
		// FileOutputStream fouthost = new
		// FileOutputStream("src\\main\\resources\\rssfeed.xml");
		System.out.println("Now try to Generate Rss.XML!");
		URL path = Thread.currentThread().getContextClassLoader()
				.getResource("");
		OutputStream fouthost = new FileOutputStream(path.getPath()
				+ "..\\..\\rssfeed.xml");
		newChannel.render(fouthost);
		fouthost.close();
		System.out.println("END!!!!!");	
		
		
	}
	

	public void RssFeedXml(String title, String Link, String Desc)
			throws IOException {

		SimpleDateFormat formatter = new SimpleDateFormat(
				"dd MMM yyyy HH:mm:ss Z");
		String today = formatter.format(new Date());
		RssFeedGenerator newRssFeedGenerator = new RssFeedGenerator();
		Channel newChannel = newRssFeedGenerator.new Channel();

		newChannel.setCopyright("HIWI FZI");
		newChannel.setTitle("Alert RSS von FZI");
		newChannel.setDescription("Alert Rss");
		newChannel.setLink("http://fzi.de");
		newChannel.setLanguage("en");
		newChannel.setPubDate(today);

		Item item = newRssFeedGenerator.new Item();
		item.setTitle(title);
		item.setLink(Link);
		item.setDescription(Desc);
		newChannel.setPubDate(today);
		newChannel.addItem(item);

		// FileOutputStream fouthost = new
		// FileOutputStream("E:\\java 64\\apache-tomcat-7.0.22\\webapps\\alert-acton\\rssfeed.xml");
		// FileOutputStream fouthost = new
		// FileOutputStream("src\\main\\resources\\rssfeed.xml");
		System.out.println("Now try to Generate Rss.XML!");
		URL path = Thread.currentThread().getContextClassLoader()
				.getResource("");
		OutputStream fouthost = new FileOutputStream(path.getPath()
				+ "..\\..\\rssfeed.xml");
		newChannel.render(fouthost);
		fouthost.close();
		System.out.println("END!!!!!");
				
		
		
	}

	public class Item implements Serializable {

		/**
		     * 
		     */
		private static final long serialVersionUID = -2535241576243936839L;

		// -------------------------------------------------------------
		// Properties

		/**
		 * The item description (1-500 characters).
		 */
		protected String description = null;

		public String getDescription() {
			return (this.description);
		}

		public void setDescription(String description) {
			this.description = description;
		}

		/**
		 * The item link (1-500 characters).
		 */
		protected String link = null;

		public String getLink() {
			return (this.link);
		}

		public void setLink(String link) {
			this.link = link;
		}

		/**
		 * The item title (1-100 characters).
		 */
		protected String title = null;

		public String getTitle() {
			return (this.title);
		}

		public void setTitle(String title) {
			this.title = title;
		}

		// -------------------------------------------------------- Package
		// Methods

		/**
		 * Render this channel as XML conforming to the RSS 0.91 specification,
		 * to the specified writer.
		 * 
		 * @param writer
		 *            The writer to render output to
		 */
		void render(PrintWriter writer) {
			writer.println("    <item>");

			writer.print("      <title>");
			writer.print(title);
			writer.println("</title>");

			writer.print("      <link>");
			writer.print(link);
			writer.println("</link>");

			if (description != null) {
				writer.print("      <description>");
				writer.print(description);
				writer.println("</description>");
			}

			writer.println("    </item>");
		}

	}

	public class Channel implements Serializable {

		/**
	     * 
	     */
		private static final long serialVersionUID = -7358941908590407568L;

		// ----------------------------------------------------- Instance
		// Variables

		/**
		 * The set of items associated with this Channel.
		 */
		protected ArrayList<Item> items = new ArrayList<Item>();

		/**
		 * The set of skip days for this channel.
		 */
		protected ArrayList<String> skipDays = new ArrayList<String>();

		/**
		 * The set of skip hours for this channel.
		 */
		protected ArrayList<String> skipHours = new ArrayList<String>();

		// -------------------------------------------------------------
		// Properties

		/**
		 * The channel copyright (1-100 characters).
		 */
		protected String copyright = null;

		public String getCopyright() {
			return (this.copyright);
		}

		public void setCopyright(String copyright) {
			this.copyright = copyright;
		}

		/**
		 * The channel description (1-500 characters).
		 */
		protected String description = null;

		public String getDescription() {
			return (this.description);
		}

		public void setDescription(String description) {
			this.description = description;
		}

		/**
		 * The channel description file URL (1-500 characters).
		 */
		protected String docs = null;

		public String getDocs() {
			return (this.docs);
		}

		public void setDocs(String docs) {
			this.docs = docs;
		}

		/**
		 * The image describing this channel.
		 */

		/**
		 * The channel language (2-5 characters).
		 */
		protected String language = null;

		public String getLanguage() {
			return (this.language);
		}

		public void setLanguage(String language) {
			this.language = language;
		}

		/**
		 * The channel last build date (1-100 characters).
		 */
		protected String lastBuildDate = null;

		public String getLastBuildDate() {
			return (this.lastBuildDate);
		}

		public void setLastBuildDate(String lastBuildDate) {
			this.lastBuildDate = lastBuildDate;
		}

		/**
		 * The channel link (1-500 characters).
		 */
		protected String link = null;

		public String getLink() {
			return (this.link);
		}

		public void setLink(String link) {
			this.link = link;
		}

		/**
		 * The managing editor (1-100 characters).
		 */
		protected String managingEditor = null;

		public String getManagingEditor() {
			return (this.managingEditor);
		}

		public void setManagingEditor(String managingEditor) {
			this.managingEditor = managingEditor;
		}

		/**
		 * The channel publication date (1-100 characters).
		 */
		protected String pubDate = null;

		public String getPubDate() {
			return (this.pubDate);
		}

		public void setPubDate(String pubDate) {
			this.pubDate = pubDate;
		}

		/**
		 * The channel rating (20-500 characters).
		 */
		protected String rating = null;

		public String getRating() {
			return (this.rating);
		}

		public void setRating(String rating) {
			this.rating = rating;
		}

		/**
		 * The text input description for this channel.
		 */

		/**
		 * The channel title (1-100 characters).
		 */
		protected String title = null;

		public String getTitle() {
			return (this.title);
		}

		public void setTitle(String title) {
			this.title = title;
		}

		/**
		 * The RSS specification version number used to create this Channel.
		 */
		protected double version = 0.91;

		public double getVersion() {
			return (this.version);
		}

		public void setVersion(double version) {
			this.version = version;
		}

		/**
		 * The webmaster email address (1-100 characters).
		 */
		protected String webMaster = null;

		public String getWebMaster() {
			return (this.webMaster);
		}

		public void setWebMaster(String webMaster) {
			this.webMaster = webMaster;
		}

		// --------------------------------------------------------- Public
		// Methods

		/**
		 * Add an additional item.
		 * 
		 * @param item
		 *            The item to be added
		 */
		public void addItem(Item item) {
			synchronized (items) {
				items.add(item);
			}
		}

		/**
		 * Add an additional skip day name.
		 * 
		 * @param skipDay
		 *            The skip day to be added
		 */
		public void addSkipDay(String skipDay) {
			synchronized (skipDays) {
				skipDays.add(skipDay);
			}
		}

		/**
		 * Add an additional skip hour name.
		 * 
		 * @param skipHour
		 *            The skip hour to be added
		 */
		public void addSkipHour(String skipHour) {
			synchronized (skipHours) {
				skipHours.add(skipHour);
			}
		}

		/**
		 * Return the items for this channel.
		 */
		public Item[] findItems() {
			synchronized (items) {
				Item items[] = new Item[this.items.size()];
				return this.items.toArray(items);
			}
		}

		/**
		 * Return the items for this channel.
		 */
		public Item[] getItems() {
			return findItems();
		}

		/**
		 * Return the skip days for this channel.
		 */
		public String[] findSkipDays() {
			synchronized (skipDays) {
				String skipDays[] = new String[this.skipDays.size()];
				return this.skipDays.toArray(skipDays);
			}
		}

		/**
		 * Return the skip hours for this channel.
		 */
		public String[] getSkipHours() {
			return findSkipHours();
		}

		/**
		 * Return the skip hours for this channel.
		 */
		public String[] findSkipHours() {
			synchronized (skipHours) {
				String skipHours[] = new String[this.skipHours.size()];
				return this.skipHours.toArray(skipHours);
			}
		}

		/**
		 * Return the skip days for this channel.
		 */
		public String[] getSkipDays() {
			return findSkipDays();
		}

		/**
		 * Remove an item for this channel.
		 * 
		 * @param item
		 *            The item to be removed
		 */
		public void removeItem(Item item) {
			synchronized (items) {
				items.remove(item);
			}
		}

		/**
		 * Remove a skip day for this channel.
		 * 
		 * @param skipDay
		 *            The skip day to be removed
		 */
		public void removeSkipDay(String skipDay) {
			synchronized (skipDays) {
				skipDays.remove(skipDay);
			}
		}

		/**
		 * Remove a skip hour for this channel.
		 * 
		 * @param skipHour
		 *            The skip hour to be removed
		 */
		public void removeSkipHour(String skipHour) {
			synchronized (skipHours) {
				skipHours.remove(skipHour);
			}
		}

		/**
		 * Render this channel as XML conforming to the RSS 0.91 specification,
		 * to the specified output stream, with no indication of character
		 * encoding.
		 * 
		 * @param stream
		 *            The output stream to write to
		 */
		public void render(OutputStream stream) {
			try {
				render(stream, null);
			} catch (UnsupportedEncodingException e) {
				// Can not happen
			}
		}

		/**
		 * Render this channel as XML conforming to the RSS 0.91 specification,
		 * to the specified output stream, with the specified character
		 * encoding.
		 * 
		 * @param stream
		 *            The output stream to write to
		 * @param encoding
		 *            The character encoding to declare, or <code>null</code>
		 *            for no declaration
		 * 
		 * @exception UnsupportedEncodingException
		 *                if the named encoding is not supported
		 */
		public void render(OutputStream stream, String encoding)
				throws UnsupportedEncodingException {
			PrintWriter pw = null;
			if (encoding == null) {
				pw = new PrintWriter(stream);
			} else {
				pw = new PrintWriter(new OutputStreamWriter(stream, encoding));
			}
			render(pw, encoding);
			pw.flush();
		}

		/**
		 * Render this channel as XML conforming to the RSS 0.91 specification,
		 * to the specified writer, with no indication of character encoding.
		 * 
		 * @param writer
		 *            The writer to render output to
		 */
		public void render(Writer writer) {
			render(writer, null);
		}

		/**
		 * Render this channel as XML conforming to the RSS 0.91 specification,
		 * to the specified writer, indicating the specified character encoding.
		 * 
		 * @param writer
		 *            The writer to render output to
		 * @param encoding
		 *            The character encoding to declare, or <code>null</code>
		 *            for no declaration
		 */
		public void render(Writer writer, String encoding) {
			PrintWriter pw = new PrintWriter(writer);
			render(pw, encoding);
			pw.flush();
		}

		/**
		 * Render this channel as XML conforming to the RSS 0.91 specification,
		 * to the specified writer, with no indication of character encoding.
		 * 
		 * @param writer
		 *            The writer to render output to
		 */
		public void render(PrintWriter writer) {
			render(writer, null);
		}

		/**
		 * Render this channel as XML conforming to the RSS 0.91 specification,
		 * to the specified writer, indicating the specified character encoding.
		 * 
		 * @param writer
		 *            The writer to render output to
		 * @param encoding
		 *            The character encoding to declare, or <code>null</code>
		 *            for no declaration
		 */
		public void render(PrintWriter writer, String encoding) {
			writer.print("<?xml version=\"1.0\"");
			if (encoding != null) {
				writer.print(" encoding=\"");
				writer.print(encoding);
				writer.print("\"");
			}
			writer.println("?>");
			writer.println();

			writer.println("<rss version=\"0.91\">");
			writer.println();

			writer.println("  <channel>");
			writer.println();

			writer.print("    <title>");
			writer.print(title);
			writer.println("</title>");

			writer.print("    <description>");
			writer.print(description);
			writer.println("</description>");

			writer.print("    <link>");
			writer.print(link);
			writer.println("</link>");

			writer.print("    <language>");
			writer.print(language);
			writer.println("</language>");

			if (rating != null) {
				writer.print("    <rating>");
				writer.print(rating);
				writer.println("</rating>");
			}

			if (copyright != null) {
				writer.print("    <copyright>");
				writer.print(copyright);
				writer.print("</copyright>");
			}

			if (pubDate != null) {
				writer.print("    <pubDate>");
				writer.print(pubDate);
				writer.println("</pubDate>");
			}

			if (lastBuildDate != null) {
				writer.print("    <lastBuildDate>");
				writer.print(lastBuildDate);
				writer.println("</lastBuildDate>");
			}

			if (docs != null) {
				writer.print("    <docs>");
				writer.print(docs);
				writer.println("</docs>");
			}

			if (managingEditor != null) {
				writer.print("    <managingEditor>");
				writer.print(managingEditor);
				writer.println("</managingEditor>");
			}

			if (webMaster != null) {
				writer.print("    <webMaster>");
				writer.print(webMaster);
				writer.println("</webMaster>");
			}

			writer.println();

			String skipDays[] = findSkipDays();
			if (skipDays.length > 0) {
				writer.println("    <skipDays>");
				for (int i = 0; i < skipDays.length; i++) {
					writer.print("      <skipDay>");
					writer.print(skipDays[i]);
					writer.println("</skipDay>");
				}
				writer.println("    </skipDays>");
			}

			String skipHours[] = findSkipHours();
			if (skipHours.length > 0) {
				writer.println("    <skipHours>");
				for (int i = 0; i < skipHours.length; i++) {
					writer.print("      <skipHour>");
					writer.print(skipHours[i]);
					writer.println("</skipHour>");
				}
				writer.println("    </skipHours>");
				writer.println();
			}

			Item items[] = findItems();
			for (int i = 0; i < items.length; i++) {
				items[i].render(writer);
				writer.println();
			}

			writer.println("  </channel>");
			writer.println();

			writer.println("</rss>");
		}

	}

}
