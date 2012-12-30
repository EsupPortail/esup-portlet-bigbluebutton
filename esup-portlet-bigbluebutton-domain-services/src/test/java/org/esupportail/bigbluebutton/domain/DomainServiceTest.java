package org.esupportail.bigbluebutton.domain;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.esupportail.bigbluebutton.domain.beans.Meeting;
import org.esupportail.bigbluebutton.utils.WebUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.ModelMap;
import org.w3c.dom.Document;

import junit.framework.Assert;
import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:META-INF/testApplicationContext.xml")
public class DomainServiceTest extends TestCase {
	
	@Autowired
	DomainService domainService;
	
	@Test
	public void testAddMeetingStringStringStringStringIntegerDateStringStringDate() {
		Meeting m=new Meeting("junittest", "test", "test", "test", 71000, new Date(), "02:00", "admin", null, new Date());
		domainService.addMeeting(m);
		List<Meeting> l = domainService.getAllMeetings();
		
		Meeting meeting=null;
		for (int i = 0; i < l.size(); i++) {
			if(l.get(i).getName().equals("junittest"))
				meeting=l.get(i);	
		}
		Assert.assertNotNull("Test meeting not found in database" , meeting);
	}
	
	@Test
    public void testCreateMeeting() {
    

	ModelMap model = new ExtendedModelMap();

	// ask the controller to handle the request

	String url = domainService.createMeetingUrl("test0528", "test", "welcome", "viewerPassword", "moderatorPassword", 78000, "admin");
	WebUtils utils = new WebUtils();
	Document doc = null;
	try {
		// Attempt to create a test meeting on BBB server
		String xml = utils.getURL(url);
		doc = utils.parseXml(xml);
	} catch (Exception e) {
		fail("Could not complete the request : check your bbb server url and security salt in config.properties " + url);
	}
	assertTrue("Request completed but could not create the meeting on server with url : " + url, 
			(doc.getElementsByTagName("returncode").item(0).getTextContent().trim().equals("SUCCESS") 
			|| doc.getElementsByTagName("messageKey").item(0).getTextContent().trim().equals("idNotUnique")));
    }
}
