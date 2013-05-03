import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class StoreUserTest {
	
	private ArrayList<SMS> smsbox;
	private ArrayList<Mail> mailbox;
	private ArrayList<Print> printbox;
	private User user1;
	private User user2;
	private StoreUser storeUser1;
	private SMS sms1;

	@Before
	public void setUp() throws Exception {
		//smsbox = new ArrayList<SMS>();
		//mailbox = new ArrayList<Mail>();
		//printbox = new ArrayList<Print>();
		user1 = new User("baumgsam", "none", "0791234567", "baumgsam@gmail.com", "samPrinter");
		user2 = new User("baumgsam2", "none", "07912345567", "baumgsam2@gmail.com", "samPrinter2");
		storeUser1 = new StoreUser();
		sms1 = new SMS(user1, user2, "bla bla", "12.12.12");
		user1.addSMS(sms1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWrite() throws IOException {
		assertEquals(true, storeUser1.write(user1));
		//fail("Not yet implemented");
	}

	@Test
	public void testRead() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() throws IOException, InterruptedException {
		assertEquals(true, storeUser1.write(user2));
		assertEquals(true, storeUser1.delete(user2));
		//fail("Not yet implemented");
	}

}
