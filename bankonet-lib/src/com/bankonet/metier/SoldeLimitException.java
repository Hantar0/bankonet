package com.bankonet.metier;

public class SoldeLimitException extends Exception {
	public String toString() {
		return "La valeur dépasse la solde";
	}
}
