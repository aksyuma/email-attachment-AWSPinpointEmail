package com.syumaK.AWSPinpoint;

/**
 * Author:syumaK (Amos K Syuma)
 *
 */

import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.Properties;
import java.io.ByteArrayOutputStream;

// Download JavaMail libraries from https://mvnrepository.com/artifact/javax.mail/mail
import javax.activation.DataSource;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.AddressException;

// Download AWS SDK libraries from https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-pinpointemail
import com.amazonaws.regions.Regions;
import com.amazonaws.services.pinpointemail.model.Body;
import com.amazonaws.services.pinpointemail.model.Content;
import com.amazonaws.services.pinpointemail.model.RawMessage;
import com.amazonaws.services.pinpointemail.model.Destination;
import com.amazonaws.services.pinpointemail.model.EmailContent;
import com.amazonaws.services.pinpointemail.AmazonPinpointEmail;
import com.amazonaws.services.pinpointemail.model.SendEmailRequest;
import com.amazonaws.services.pinpointemail.AmazonPinpointEmailClientBuilder;


public class AmazonPinpointSample 
{
	// Replace sender@example.com with the "FromAddress". If your Amazon Pinpoint account is in
	// the sandbox mode, then this address must be verified in Amazon Pinpoint.
	private static String senderAddress = "sender@example.com";

	// Replace recipient@example.com with a "ToAdress". If your Amazon Pinpoint account is in
	// the sandbox mode, then this address must be verified in Amazon Pinpoint.
	private static String toAddress = "recipient@example.com";

	// The subject line for the email.
	private static String subject = "Amazon Pinpoint Email API test";
	
    // The character encoding the you want to use for the subject line and
    // message body of the email.
    static final String charset = "UTF-8";

	// The full path to the file that will be attached to the email.
	// If you're using Windows, escape backslashes as shown here : "C:\\Users\\{sender}\\customers-to-contact.xlsx"
	// If you're using MacOSX, escape backslashes as shown here : "/Users/{sender}/Documents/customers-to-contact.xlsx"
	// If you're using Linux, escape backslashes as shown here: "/home/{sender}/Downloads/customers-list.xls"
	private static String ATTACHMENT = "/home/syumak/Downloads/customers-list.xls";

	// The email body for recipients with non-HTML email clients.
	private static String BODY_TEXT = "Hello,\r\n"
			+ "Please see the attached file for a list "
			+ "of customers to contact.";

	// The HTML body of the email.
	private static String BODY_HTML = "<html>"
			+ "<head></head>"
			+ "<body>"
			+ "<h1>Hello!</h1>"
			+ "<p>Please see the attached file for a "
			+ "list of customers to contact.</p>"
			+ "</body>"
			+ "</html>";

	public static void main(String[] args) throws AddressException, MessagingException, IOException 
	{

		Session session = Session.getDefaultInstance(new Properties());

		// Create a new MimeMessage object.
		MimeMessage message = new MimeMessage(session);

		// Add subject, from and to lines.
		message.setSubject(subject, "UTF-8");
		message.setFrom(new InternetAddress(senderAddress));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));

		// Create a multipart/alternative child container.
		MimeMultipart msg_body = new MimeMultipart("alternative");

		// Create a wrapper for the HTML and text parts.        
		MimeBodyPart wrap = new MimeBodyPart();

		// Define the text part.
		MimeBodyPart textPart = new MimeBodyPart();
		textPart.setContent(BODY_TEXT, "text/plain; charset=UTF-8");

		// Define the HTML part.
		MimeBodyPart htmlPart = new MimeBodyPart();
		htmlPart.setContent(BODY_HTML,"text/html; charset=UTF-8");

		// Add the text and HTML parts to the child container.
		msg_body.addBodyPart(textPart);
		msg_body.addBodyPart(htmlPart);

		// Add the child container to the wrapper object.
		wrap.setContent(msg_body);

		// Create a multipart/mixed parent container.
		MimeMultipart msg = new MimeMultipart("mixed");

		// Add the parent container to the message.
		message.setContent(msg);

		// Add the multipart/alternative part to the message.
		msg.addBodyPart(wrap);

		// Define the attachment
		MimeBodyPart att = new MimeBodyPart();
		DataSource fds = new FileDataSource(ATTACHMENT);
		att.setDataHandler(new DataHandler(fds));
		att.setFileName(fds.getName());

		// Add the attachment to the message.
		msg.addBodyPart(att);

		// Try to send the email.
		try {

			System.out.println("===============================================");

			System.out.println("Getting Started with Amazon PinpointEmail API "
					+"using the AWS SDK for Java...");
			System.out.println("===============================================\n");


			// Instantiate an Amazon PinpointEmail client, which will make the service call with the supplied AWS credentials.
			AmazonPinpointEmail client = AmazonPinpointEmailClientBuilder.standard()
				.withRegion(Regions.US_EAST_1).build();

			// Send the email.
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			message.writeTo(outputStream);
			
				  SendEmailRequest rawEmailRequest = new SendEmailRequest()
						  .withFromEmailAddress(senderAddress)
						  .withDestination(new Destination()
							  .withToAddresses(toAddress)
						  )
						  .withContent(new EmailContent()
								  .withRaw(new RawMessage().withData(ByteBuffer.wrap(outputStream.toByteArray())))
						  );

			client.sendEmail(rawEmailRequest);

			System.out.println("Email sent!");
			// Display an error if something goes wrong.
		} catch (Exception ex) {
			System.out.println("Email Failed");
			System.err.println("Error message: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

}
