package com.radcortez.samples.log4j.mail.filter;

import org.junit.Test;
import org.jvnet.mock_javamail.Mailbox;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Roberto Cortez
 */
public class Log4jFilterTest {
    @Test
    public void testStringMatchFilter() throws Exception {
        Log4jFilter log4jFilter = new Log4jFilter();
        log4jFilter.executeBusinessLogic();
        log4jFilter.executeBusinessLogic();

        Mailbox mailbox = Mailbox.get("someone@somemail.com");

        assertEquals(1, mailbox.getNewMessageCount());
        Object content = mailbox.get(0).getContent();
        if (content instanceof MimeMultipart) {
            MimeMultipart mimeMultipart = (MimeMultipart) content;
            assertTrue(getText(mimeMultipart.getParent()).contains("ERROR02"));
        } else {
            assertTrue(false);
        }
    }

    @Test
    public void testStringMatchAndExpressionFilter() throws Exception {
        Log4jExpressionFilter log4jExpressionFilter = new Log4jExpressionFilter();
        log4jExpressionFilter.executeBusinessLogic();
        log4jExpressionFilter.executeBusinessLogic();

        Mailbox mailbox = Mailbox.get("someone@somemail.com");

        assertEquals(0, mailbox.getNewMessageCount());
    }

    private String getText(Part p) throws MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            return (String) p.getContent();
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart) p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null) { text = getText(bp); }
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null) { return s; }
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null) { return s; }
            }
        }

        return null;
    }
}
