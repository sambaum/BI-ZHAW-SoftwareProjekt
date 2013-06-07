import java.text.SimpleDateFormat;

public class Demo {

	public boolean createDemoUsers(){
		
		try {
			User fritz = new User("Fritz", "Management", "0791234567", "fritz.manager@importantcompany.com", "FritzWirelessPrinter");
			User hans = new User("Hans", "Management", "079987567", "hans.manager@importantcompany.com", "HansWirelessPrinter");
			User susi = new User("Susi", "Personal", "0732134567", "susi.pers@importantcompany.com", "PersPrinter");
			User anna = new User("Anna", "Personal", "073217667", "jason.pers@importantcompany.com", "PersPrinter");
			
			Mail mail_susi2fritz = new Mail(fritz, susi,"Ich kündige", new SimpleDateFormat("dd.MM.yyyy").parse("02.01.2013"));
			fritz.addMail(mail_susi2fritz);
			
			SMS sms_jason2hans = new SMS(hans, anna, "Wir müssen uns dringed treffen", new SimpleDateFormat("dd.MM.yyyy").parse("02.01.2013"));
			hans.addSMS(sms_jason2hans);
			
			Print druck_susi2jason = new Print(anna, susi, "Unser Chef spinnt", new SimpleDateFormat("dd.MM.yyyy").parse("02.01.2010"));
			anna.addPrint(druck_susi2jason);
			
			SMS sms_hans2susi = new SMS(susi, hans, "Wenn du das hier liest (2015), benn ich schon längst weg", new SimpleDateFormat("dd.MM.yyyy").parse("02.01.2015"));
			susi.addSMS(sms_hans2susi);
			
			StoreUser storeUser = new StoreUser();
			storeUser.write(fritz);
			storeUser.write(hans);
			storeUser.write(susi);
			storeUser.write(anna);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
}
