import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Demo {

	public void createDemoUsers() throws ParseException, IOException{
		
		User fritz = new User("Fritz", "Management", "0791234567", "fritz.manager@importantcompany.com", "FritzWirelessPrinter");
		User hans = new User("Hans", "Management", "079987567", "hans.manager@importantcompany.com", "HansWirelessPrinter");
		User susi = new User("Susi", "Personal", "0732134567", "susi.pers@importantcompany.com", "PersPrinter");
		User jason = new User("Jason", "Personal", "073217667", "jason.pers@importantcompany.com", "PersPrinter");
		
		Mail mail_susi2fritz = new Mail(fritz, susi,"Ich kündige", new SimpleDateFormat("dd.MM.yyyy").parse("02.01.2013"));
		fritz.addMail(mail_susi2fritz);
		
		SMS sms_jason2hans = new SMS(hans, jason, "Wir müssen und dringed treffen", new SimpleDateFormat("dd.MM.yyyy").parse("02.01.2013"));
		hans.addSMS(sms_jason2hans);
		
		StoreUser storeUser = new StoreUser();
		storeUser.write(fritz);
		storeUser.write(hans);
		storeUser.write(susi);
		storeUser.write(jason);
		
	}
	
}
