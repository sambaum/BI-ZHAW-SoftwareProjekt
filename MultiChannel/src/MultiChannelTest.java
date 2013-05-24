import static org.junit.Assert.*;

import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MultiChannelTest {
	private User user1;
	private SMS sms1;
	private SMS sms2;
	private SMS sms3;
	private Mail mail1;
	private Print print1;
	private User user2;

	@Before
	public void setUp() throws Exception {
		user1 = new User("baumgsam", "NoGroup", "0791234567",
				"baumgsam@gmail.com", "samPrinter");
		user2 = new User("fritz", "NoGroup", "07912345567", "fritz@gmail.com",
				"samPrinter2");
		sms1 = new SMS(user1, user2, "Hallo du, hier eine tolle SMS von mir",
				new SimpleDateFormat("dd.MM.yyyy").parse("05.06.2013"));
		sms2 = new SMS(user1, user2,
				"Hallo du, hier eine noch tollere SMS von mir", new SimpleDateFormat("dd.MM.yyyy").parse("02.01.2013"));
		sms3 = new SMS(user2, user1, "Hall? Warum antwortest du nicht?",
				new SimpleDateFormat("dd.MM.yyyy").parse("05.03.2013"));
		mail1 = new Mail(user1, user2, "Ein super Email von mir", new SimpleDateFormat("dd.MM.yyyy").parse("03.06.2014"));
		print1 = new Print(user1, user2,
				"Sehr geehrter Herr Bla bla.\n Bitte Zahlen Sie Ihre Rechnung",
				new SimpleDateFormat("dd.MM.yyyy").parse("05.06.2013"));
		user1.addSMS(sms1);
		user1.addSMS(sms2);
		user1.addMail(mail1);
		user1.addPrint(print1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {

		for (String line : user1.getFullInbox()) {
			// System.out.println(user1.getFullInbox().size());
			System.out.println(line);
		}
	}
}
