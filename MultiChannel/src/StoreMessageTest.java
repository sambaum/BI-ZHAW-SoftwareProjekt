import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class StoreMessageTest {

	private ArrayList<SMS> smsbox;
	private ArrayList<Mail> mailbox;
	private ArrayList<Print> printbox;
	private User user1;
	private User user2;
	private StoreMessage storeMes1;
	
	@Before
	public void setUp() throws Exception {
		smsbox = new ArrayList<SMS>();
		mailbox = new ArrayList<Mail>();
		printbox = new ArrayList<Print>();
		user1 = new User("baumgsam", "none", "0791234567", "baumgsam@gmail.com", "samPrinter", smsbox, mailbox, printbox);
		user2 = new User("baumgsam2", "none", "07912345567", "baumgsam2@gmail.com", "samPrinter2", smsbox, mailbox, printbox);
		storeMes1 = new StoreMessage();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWrite() {
		fail("Not yet implemented");
	}

	@Test
	public void testRead() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

}
