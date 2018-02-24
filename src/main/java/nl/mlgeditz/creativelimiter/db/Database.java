package nl.mlgeditz.creativelimiter.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by MLGEditz and/or other contributors
 * No part of this publication may be reproduced,
 * distrubuted, of transmitted in any form or by any means.
 * Copyright © 24 feb. 2018 2018 by MLGEditz
*/

public class Database {
	
	private Connection c = null;
	
	public Database(File dbFile) throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getPath());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		getNewStatement().executeUpdate("CREATE TABLE IF NOT EXISTS block (loc VARCHAR(255) NOT NULL default '')");
	}
	
	public Statement getNewStatement() throws SQLException {
		return c.createStatement();
	}

}
