package pwr.itapps.meetme.helper;

import java.util.HashMap;
import java.util.Map;

public class GlobalDataExchanger {

	private static GlobalDataExchanger instance;

	private Map<String, Object> dictionary;

	private GlobalDataExchanger() {
		dictionary = new HashMap<String, Object>();
	}

	public static GlobalDataExchanger getInstance() {

		if (instance == null) {

			synchronized (GlobalDataExchanger.class) {

				if (instance == null) {
					instance = new GlobalDataExchanger();
				}
			}
		}
		return instance;
	}

	public void put(String key, Object value) {
		dictionary.put(key, value);
	}

	public Object get(String key) {
		return dictionary.get(key);
	}

}
