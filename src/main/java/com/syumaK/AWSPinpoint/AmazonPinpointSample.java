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

	public static void main(String[] args) throws IOException 
	{


		// Try to send the email.
		try {

			System.out.println("===============================================");

			System.out.println("Getting Started with Amazon PinpointEmail API "
					+"using the AWS SDK for Java...");
			System.out.println("===============================================\n");

			System.out.println("Email sent!");
			// Display an error if something goes wrong.
		} catch (Exception ex) {
			System.out.println("Email Failed");
			System.err.println("Error message: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

}
