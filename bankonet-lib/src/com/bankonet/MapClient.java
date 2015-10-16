package com.bankonet;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MapClient {
	static Map<String,Client> listClient = new HashMap<>();

	public static boolean ajouterClient(Client client) {
		if (!listClient.containsKey(client.getLogin())) {			
			listClient.put(client.getLogin(),client);
			return true;
		}
		return false;
	}
	
	public static Client getClient(String login) {
		return listClient.get(login);
	}
	
	public static String showData() {
		String data="";
		for(Entry<String, Client> entry : MapClient.listClient.entrySet()) {
			data += entry.getValue().getInfo()+"\n";
		}
		return data;
	}
	
	public static String showLoginClient() {
		String data="";
		for(Entry<String, Client> entry : MapClient.listClient.entrySet()) {
			data += entry.getValue().getLogin()+"\n";
		}
		return data;
	}

}
