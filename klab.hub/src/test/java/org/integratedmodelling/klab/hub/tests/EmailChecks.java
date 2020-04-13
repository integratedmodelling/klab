package org.integratedmodelling.klab.hub.tests;

import static org.junit.Assert.assertEquals;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.integratedmodelling.klab.hub.emails.services.EmailManager;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.Retriever;

public class EmailChecks extends ApplicationCheck {
	
	@Autowired
	EmailManager emailManager;
	
	@Test
	public void testEmailRecieved() throws MessagingException {
		String body = "is there anybody out there";
		emailManager.sendFromMainEmailAddress("recipient2@email.com", "Test", body);
		Retriever r = new Retriever(greenMail.getPop3());
		Message[] msgs = r.getMessages("recipient2@email.com", "password");
		String recv = GreenMailUtil.getBody(msgs[0]).trim().toString();
		r.close();
		assertEquals(body, recv);
	}
}
