package com.bankonet.dao;

import java.sql.SQLException;

public abstract class GestionData {

	public abstract void importData(String filecomptes) throws SQLException;

}
