package com.bankonet.dao;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionSQL {

	/**
	 * M�thode cr�ant la connexion avec la BDD
	 * 
	 * @return Statement
	 */
	private java.sql.Statement statement = null;

	public java.sql.Statement connectBDD() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/bankonet", "root",
						"");
				statement = connection.createStatement();
			} catch (SQLException e) {
				System.out.println("Probl�me lors de la a connexion � la BDD");
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Probl�me lors de l'instalation du driver");
			e.printStackTrace();

		}
		return statement;
	}

	/**
	 * M�thode renvoyant le resultat d'une requete (au format String) de type
	 * select sous la forme d'un Resultset
	 * 
	 * @param request
	 * @return ResultSet
	 */
	public ResultSet BDDQuery(String request) {
		ResultSet resultat = null;
		try {
			resultat = statement.executeQuery(request);
		} catch (SQLException e) {
			System.out.println("Probl�me � l'execution de la requ�te");
			e.printStackTrace();
		}
		return resultat;
	}

	public int BDDUpdate(String request) {
		int resultat = 0;
		try {
			resultat = statement.executeUpdate(request);
		} catch (SQLException e) {
			System.out.println("Probl�me � l'execution de la requ�te");
			e.printStackTrace();
		}
		return resultat;
	}

	public void closeBDD() {
		try {
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
