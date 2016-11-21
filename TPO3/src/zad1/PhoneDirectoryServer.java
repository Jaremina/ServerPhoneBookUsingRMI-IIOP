package zad1;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.JOptionPane;

public class PhoneDirectoryServer {
 	
	public static void main(String[] args) {

		try {
			//zamiast pliku jndi.properties 
			Properties props = new Properties();
			props.setProperty(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.cosnaming.CNCtxFactory");
			props.setProperty(Context.PROVIDER_URL,"iiop://localhost:3333");
			
			// tworzymy zdalny obiekt
			PhoneDirectory phoneDirectory = new PhoneDirectory("phoneBook.txt");

			// Rejestracja obiektu w serwisie nazw

			Context context = new InitialContext(props);
			context.rebind("PhoneDirectoryService", phoneDirectory);
			
			JOptionPane.showMessageDialog(null, "start servera");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
