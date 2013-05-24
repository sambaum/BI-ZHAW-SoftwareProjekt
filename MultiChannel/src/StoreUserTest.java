import static org.junit.Assert.*;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StoreUserTest {

	private User user1;
	private User user2;
	private User user3;
	private StoreUser storeUser1;
	private SMS sms1;
	private SMS sms2;
	private SMS sms3;
	private Mail mail1;
	private Print print1;

	@Before
	public void setUp() throws Exception {
		user1 = new User("baumgsam", "NoGroup", "0791234567",
				"baumgsam@gmail.com", "samPrinter");
		user2 = new User("fritz", "NoGroup", "07912345567", "fritz@gmail.com",
				"samPrinter2");
		user3 = new User("susi", "NoGroup", "07912345567", "fritz@gmail.com",
				"samPrinter2");
		storeUser1 = new StoreUser();

		sms1 = new SMS(user1, user2, "Hallo du, hier eine tolle SMS von mir",
				new SimpleDateFormat("dd.MM.yyyy").parse("05.06.2013"));
		sms2 = new SMS(user1, user2,
				"Hallo du, hier eine noch tollere SMS von mir", new SimpleDateFormat("dd.MM.yyyy").parse("02.01.2013"));
		sms3 = new SMS(user2, user1, "Hall? Warum antwortest du nicht?",
				new SimpleDateFormat("dd.MM.yyyy").parse("05.03.2013"));
		mail1 = new Mail(user1, user2, "Ein super Email von mir", new SimpleDateFormat("dd.MM.yyyy").parse("03.06.2014"));
		print1 = new Print(user1, user2,
				"Sehr geehrter Herr Bla bla. Bitte Zahlen Sie Ihre Rechnung",
				new SimpleDateFormat("dd.MM.yyyy").parse("05.06.2013"));

		user1.addSMS(sms1);
		user1.addSMS(sms2);
		user1.addMail(mail1);
		user1.addPrint(print1);
		user2.addSMS(sms3);
	}

	@After
	public void tearDown() throws Exception {
		// assertEquals(true,storeUser1.cleanDirectory());
	}

	@Test
	public void testWrite() throws IOException {
		assertEquals(true, storeUser1.write(user1));
		assertEquals(true, storeUser1.write(user2));
	}

	@Test
	public void testRead() throws IOException {
		// user in file schreiben
		assertEquals(true, storeUser1.write(user1));
		// username merken
		String userName = user1.getUserName();
		// user objekt löschen
		user1 = null;
		// user auf file lesen
		assertNotNull(storeUser1.read());
		// ueberpruefen ob der user wieder vorhanden ist
		user1 = storeUser1.getUserMap().get(userName);
		assertEquals("baumgsam", user1.getUserName());
		// ueberpruefen ob User Messages hat.
		assertNotNull(user1.getSmsBox());
	}

	@Test
	public void testDelete() throws IOException, InterruptedException {
		assertEquals(true, storeUser1.delete(user1));
		assertEquals(true, storeUser1.delete(user2));
		assertEquals(true, storeUser1.write(user1));
		assertEquals(true, storeUser1.write(user2));

	}

}
