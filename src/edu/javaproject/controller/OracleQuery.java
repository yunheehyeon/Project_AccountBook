package edu.javaproject.controller;

public interface OracleQuery {
	String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	String USER = "scott";
	String PASSWORD = "tiger";
	
	String TABLE_PUC = "account_in";
	String ID_PUC = "id_in";
	String DATE_PUC = "date_in";
	String KATEGORIE_PUC = "kategorie_in";
	String MONEY_PUC = "money_in";
	String TEXT_PUC = "text_in";
	
	String SQL_SELECT_PUC = "select * from " + TABLE_PUC
							+ " where " + DATE_PUC + " between ? and ?"
							+ " order by " + DATE_PUC;
	String SQL_INSERT_PUC = "insert into " + TABLE_PUC +" ("+ DATE_PUC+", "+ KATEGORIE_PUC+", "+ MONEY_PUC+", "+ TEXT_PUC +")" 
			+ " values (?, ?, ?, ?)";
	String SQL_UPDATE_PUC = 
			"update " + TABLE_PUC
			+" set " + DATE_PUC + " = ?, "
					+ KATEGORIE_PUC + " = ?, "
					+ MONEY_PUC + " = ?, "
					+ TEXT_PUC + " = ? "
			+" where " + ID_PUC + " = ?";
	String SQL_DELETE_PUC = "delete from " + TABLE_PUC +" where " + ID_PUC + " = ? ";
	
	
	String TABLE_EXP = "account_out";
	String ID_EXP = "id_out";
	String DATE_EXP = "date_out";
	String KATEGORIE_EXP = "kategorie_out";
	String MONEY_EXP = "money_out";
	String TEXT_EXP = "text_out";
	
	String SQL_SELECT_EXP = "select * from " + TABLE_EXP
							+ " where " + DATE_EXP + " between ? and ?"
							+ " order by " + DATE_EXP;;
	String SQL_INSERT_EXP = "insert into " + TABLE_EXP +" ("+ DATE_EXP+", "+ KATEGORIE_EXP+", "+ MONEY_EXP+", "+ TEXT_EXP +")" 
			+ " values (?, ?, ?, ?)";
	String SQL_UPDATE_EXP = 
			"update " + TABLE_EXP
			+" set " + DATE_EXP + " = ?, "
					+ KATEGORIE_EXP + " = ?, "
					+ MONEY_EXP + " = ?, "
					+ TEXT_EXP + " = ? "
			+" where " + ID_EXP + " = ?";
	String SQL_DELETE_EXP = "delete from " + TABLE_EXP +" where " + ID_EXP + " = ? ";
	

	String TABLE_M = "account_m";
	String ID_M = "id_m";
	String DATE_M = "date_m";
	String KATEGORIE_M = "kategorie_m";
	String MONEY_M = "money_m";
	String TEXT_M = "text_m";
	
	String SQL_SELECT_M = "select * from " + TABLE_M
							+ " order by " + DATE_M;
	String SQL_INSERT_M = "insert into " + TABLE_M +" ("+ DATE_M+", "+ KATEGORIE_M+", "+ MONEY_M+", "+ TEXT_M +")" 
			+ " values (?, ?, ?, ?)";
	String SQL_UPDATE_M = 
			"update " + TABLE_M
			+" set " + DATE_M + " = ?, "
					+ KATEGORIE_M + " = ?, "
					+ MONEY_M + " = ?, "
					+ TEXT_M + " = ? "
			+" where " + ID_M + " = ?";
	String SQL_DELETE_M = "delete from " + TABLE_M +" where " + ID_M + " = ? ";
	



}
