package zad1;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.swing.JOptionPane;

public class PhoneDirectoryClient {

	private static Pattern reqPatt = Pattern.compile(" +", 3);
	private static String messages[] = { "Ok", "Invalid request", "Not found", "Couldn't add - entry already exists",
			"Couldn't replace non-existing entry", };
	private PhoneDirectoryInterface phoneDirectoryInterface;

	private static Context context;
	private static Object objectRef;


	private void request(String request) throws IOException {
		
		String[] req = reqPatt.split(request, 3); 
		String cmd = req[0];
		String msg = "";

		if (cmd.equals("bye")) {
			showResponse(0, "bye");
		} else if (cmd.equals("get")) { 
										
			if (req.length != 2)
				showResponse(1, null);
			else {
				String num = (String) phoneDirectoryInterface.getPhoneNumber(req[1]); // pobranie
				if (num == null) {
					showResponse(2, null);
				} else {
					msg = req[1] + " number is: " + num;
					showResponse(0, msg); 
				}
			}
		} else if (cmd.equals("add")) {
			if (req.length != 3)
				showResponse(1, null);
			else {
				boolean added = phoneDirectoryInterface.addPhoneNumber(req[1], req[2]); // dodany?
				if (added) {
					msg = req[1] + " number: " + req[2] + " added";
					showResponse(0, msg); 
				} else
					showResponse(3, null); 
			}
		} else if (cmd.equals("replace")) { 
			if (req.length != 3)
				showResponse(1, null);
			else {
				boolean replaced = phoneDirectoryInterface.replacePhoneNumber(req[1], req[2]);
				if (replaced) {
					msg = req[1] + " number: " + req[2] + " replaced";
					showResponse(0, msg);
				} else
					showResponse(4, null);
			}
		} else
			showResponse(1, null); // nieznane zlecenie

	}

	private void showResponse(int rc, String addMsg) throws IOException {
		if (addMsg != null) {
			JOptionPane.showMessageDialog(null, rc + " " + messages[rc] + " " + addMsg);
		} else {
			JOptionPane.showMessageDialog(null, rc + " " + messages[rc]);
		}
	}

	public static void main(String[] args) {

		try {
			PhoneDirectoryClient client = new PhoneDirectoryClient();

			Properties props = new Properties();
			props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.cosnaming.CNCtxFactory");
			props.setProperty(Context.PROVIDER_URL, "iiop://localhost:3333");

			context = new InitialContext(props);
			objectRef = context.lookup("PhoneDirectoryService");

			client.phoneDirectoryInterface = (PhoneDirectoryInterface) PortableRemoteObject.narrow(objectRef,
					PhoneDirectoryInterface.class);

			client.request("get Jan");
			client.request("add Ana 568-985-588");


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
