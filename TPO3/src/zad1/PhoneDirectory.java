package zad1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.rmi.PortableRemoteObject;

public class PhoneDirectory extends PortableRemoteObject implements PhoneDirectoryInterface{

	@SuppressWarnings("rawtypes")
	private Map pbMap = new HashMap();

	@SuppressWarnings({ "resource", "unchecked" })
	public PhoneDirectory(String fileName) throws RemoteException  {

		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) {
				String[] info = line.split(" +", 2);
				pbMap.put(info[0], info[1]);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
			System.exit(1);
		}
	}


	public String getPhoneNumber(String name) {
		return (String) pbMap.get(name);
	}

	@SuppressWarnings("unchecked")
	public boolean addPhoneNumber(String name, String num) {
		if (pbMap.containsKey(name))
			return false;
		pbMap.put(name, num);
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean replacePhoneNumber(String name, String num) {
		if (!pbMap.containsKey(name))
			return false;
		pbMap.put(name, num);
		return true;
	}
}
