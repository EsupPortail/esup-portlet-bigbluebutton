package org.esupportail.bigbluebutton.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.digest.DigestUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;




public class WebUtils {
	
	//
	// urlEncode() -- URL encode the string
	//
	public static String urlEncode(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	//
	// parseXml() -- return a DOM of the XML
	//
	public static Document parseXml(String xml)
	throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
		.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(new InputSource(new StringReader(xml)));
		return doc;
	}
	
	//
	// checksum() -- create a hash based on the shared salt
	//
	public static String checksum(String s) {
		String checksum = "";
		try {
			checksum = DigestUtils.shaHex(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checksum;
	}
	
	//
	// getURL() -- fetch a URL and return its contents as a String
	//
	public static String getURL(String url) {
		StringBuffer response = null;

		try {
			URL u = new URL(url);
			HttpURLConnection httpConnection = (HttpURLConnection) u
			.openConnection();

			httpConnection.setUseCaches(false);
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("GET");

			httpConnection.connect();
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream input = httpConnection.getInputStream();

				// Read server's response.
				response = new StringBuffer();
				Reader reader = new InputStreamReader(input, "UTF-8");
				reader = new BufferedReader(reader);
				char[] buffer = new char[1024];
				for (int n = 0; n >= 0;) {
					n = reader.read(buffer, 0, buffer.length);
					if (n > 0)
					response.append(buffer, 0, n);
				}

				input.close();
				httpConnection.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (response != null) {
			return response.toString();
		} else {
			return "";
		}
	}
	
	// format date "dd-MM-yyyy HH:mm"
	public static String formatDate(Date sysDate)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.FRANCE);
		String date = dateFormat.format(sysDate);
		return date;
	}
}
