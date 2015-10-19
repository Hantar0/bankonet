package com.bankonet.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.bankonet.dto.Client;

public class CacheClient {
	public Map<String,Client> listClient = new HashMap<>();

	public boolean ajouterClient(Client client) {
		if (!listClient.containsKey(client.getLogin())) {			
			listClient.put(client.getLogin(),client);
			return true;
		}
		return false;
	}
	
	public  Client getClient(String login) {
		return listClient.get(login);
	}
	
	public  String showData() {
		String data="";
		for(Entry<String, Client> entry : listClient.entrySet()) {
			data += entry.getValue().getInfo()+"\n";
		}
		return data;
	}
	
	public String showLoginClient() {
		String data="";
		for(Entry<String, Client> entry : listClient.entrySet()) {
			data += entry.getValue().getLogin()+"\n";
		}
		return data;
	}

}
