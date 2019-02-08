package edu.javaproject.controller;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import edu.javaproject.model.DataModel;
import edu.javaproject.model.InputModel;
import edu.javaproject.model.MonthlyModel;
import edu.javaproject.model.OutputModel;

import static edu.javaproject.controller.OracleQuery.*;


public class AccountBookDaoImple implements AccountBookDao {

	
	private  static AccountBookDaoImple instance;
	private AccountBookDaoImple() {
	};
	
	public static AccountBookDaoImple getInstance() {
		if(instance == null) {
			instance = new AccountBookDaoImple();
		}
		return instance;
	}
	
	///////////////////////////////////////////////////////////////////
	private ArrayList<DataModel> accountList;
		
	private void closeResources(Connection conn, Statement stmt) {
		try {
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void closeResources(Connection conn, Statement stmt, ResultSet rs) {
		try {
			rs.close();
			closeResources(conn, stmt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public ArrayList<DataModel> selectPuc(LocalDateTime from, LocalDateTime to) {
		accountList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Timestamp tfrom = Timestamp.valueOf(from);
		Timestamp tto = Timestamp.valueOf(to);

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_PUC);
			pstmt.setTimestamp(1, tfrom);
			pstmt.setTimestamp(2, tto);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(ID_PUC);
				LocalDateTime date =  rs.getTimestamp(DATE_PUC).toLocalDateTime();
				String kategorie = rs.getString(KATEGORIE_PUC);
				int money = rs.getInt(MONEY_PUC);
				String text = rs.getString(TEXT_PUC);
				
				InputModel e = new InputModel(id, date, kategorie, money, text);
				accountList.add(e);
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, pstmt, rs);
		}
		
		return accountList;
	}
	@Override
	public ArrayList<DataModel> selectExp(LocalDateTime from, LocalDateTime to) {
		accountList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Timestamp tfrom = Timestamp.valueOf(from);
		Timestamp tto = Timestamp.valueOf(to);
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_EXP);
			pstmt.setTimestamp(1, tfrom);
			pstmt.setTimestamp(2, tto);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(ID_EXP);
				LocalDateTime date =  rs.getTimestamp(DATE_EXP).toLocalDateTime();
				String kategorie = rs.getString(KATEGORIE_EXP);
				int money = rs.getInt(MONEY_EXP);
				String text = rs.getString(TEXT_EXP);
				
				OutputModel e = new OutputModel(id, date, kategorie, money, text);
				accountList.add(e);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, pstmt, rs);
		}
		
		return accountList;
	}

	@Override
	public ArrayList<DataModel> select() {
		accountList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_M);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(ID_M);
				LocalDateTime date =  rs.getTimestamp(DATE_M).toLocalDateTime();
				String kategorie = rs.getString(KATEGORIE_M);
				int money = rs.getInt(MONEY_M);
				String text = rs.getString(TEXT_M);
				
				MonthlyModel e = new MonthlyModel(id, date, kategorie, money, text);
				accountList.add(e);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, pstmt, rs);
		}
		
		return accountList;
	}
	
	
	@Override
	public int insert(DataModel dmodel) {
		int result = 0;
		String oracleQuery;
		
		if(dmodel instanceof InputModel) oracleQuery = SQL_INSERT_PUC;
		else if(dmodel instanceof OutputModel) {
			oracleQuery = SQL_INSERT_EXP;
		}else {
			oracleQuery = SQL_INSERT_M;
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(oracleQuery);
			System.out.println(dmodel.getDate());
			Timestamp t = Timestamp.valueOf(dmodel.getDate());
			System.out.println(t);
			pstmt.setTimestamp(1, Timestamp.valueOf(dmodel.getDate()));
			pstmt.setString(2, dmodel.getKategorie());
			pstmt.setInt(3, dmodel.getMoney());
			pstmt.setString(4, dmodel.getText());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, pstmt);
		}
				
		return result;
	}

	@Override
	public int update(DataModel dmodel) {
		int result = 0;
		String oracleQuery;
		
		if(dmodel instanceof InputModel) oracleQuery = SQL_UPDATE_PUC;
		else if(dmodel instanceof OutputModel) {
			oracleQuery = SQL_UPDATE_EXP;
		}else {
			oracleQuery = SQL_UPDATE_M;
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println(dmodel.getId());
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(oracleQuery);
			pstmt.setTimestamp(1, Timestamp.valueOf(dmodel.getDate()));
			pstmt.setString(2, dmodel.getKategorie());
			pstmt.setInt(3, dmodel.getMoney());
			pstmt.setString(4, dmodel.getText());
			pstmt.setInt(5, dmodel.getId());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, pstmt);
		}
			
		return result;
	}

	@Override
	public int delete(DataModel dmodel) {
		int result = 0;
	
		String oracleQuery;
		
		if(dmodel instanceof InputModel) oracleQuery = SQL_DELETE_PUC;
		else if(dmodel instanceof OutputModel) {
			oracleQuery = SQL_DELETE_EXP;
		}else {
			oracleQuery = SQL_DELETE_M;
		}
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(oracleQuery);
			pstmt.setInt(1, dmodel.getId());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, pstmt);
		}
		
		return result;
	}

	
	

}
