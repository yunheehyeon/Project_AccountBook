package edu.javaproject.controller;

import java.util.ArrayList;
import edu.javaproject.fileutil.FileUtil;

public class AccountBookFileDaoImple {

	private  static AccountBookFileDaoImple instance;
	
	private AccountBookFileDaoImple() throws Exception {
		FileUtil.initDataDir();
		kategorie_all = FileUtil.loadInitData();
		
		if(kategorie_all != null) {
			kategorie_exp = FileUtil.readDataFromFile(1);
			kategorie_puc = FileUtil.readDataFromFile(0);
		}else {
			kategorie_all = new ArrayList<>();
			kategorie_exp = new ArrayList<>();
			kategorie_puc = new ArrayList<>();
		}
		
		year = FileUtil.loadInitYearData();
	};
	
	public static AccountBookFileDaoImple getInstance() throws Exception {
		if(instance == null) {
			instance = new AccountBookFileDaoImple();
		}
		return instance;
	}
	private ArrayList<String> kategorie_all;
	private ArrayList<String> kategorie_exp;
	private ArrayList<String> kategorie_puc;
	private ArrayList<String> year;

	public ArrayList<String> getKategorie_all() {
		return kategorie_all;
	}

	public void setKategorie_all(ArrayList<String> kategorie_all) {
		this.kategorie_all = kategorie_all;
	}

	public ArrayList<String> getKategorie_exp() {
		return kategorie_exp;
	}

	public void setKategorie_exp(ArrayList<String> kategorie_exp) {
		this.kategorie_exp = kategorie_exp;
	}

	public ArrayList<String> getKategorie_puc() {
		return kategorie_puc;
	}

	public void setKategorie_puc(ArrayList<String> kategorie_puc) {
		this.kategorie_puc = kategorie_puc;
	}

	public ArrayList<String> getYear() {
		return year;
	}

	public void setYear(ArrayList<String> year) {
		this.year = year;
	}

	public void kgUpdate(ArrayList<String> kategorie_exp, ArrayList<String> kategorie_puc) {
		FileUtil.writeDataToFile(kategorie_exp, 1);
		FileUtil.writeDataToFile(kategorie_puc, 0);
		
		this.kategorie_exp = FileUtil.readDataFromFile(1);
		this.kategorie_puc = FileUtil.readDataFromFile(0);
		kategorie_all.removeAll(kategorie_all);
		kategorie_all.addAll(this.kategorie_exp);
		kategorie_all.addAll(this.kategorie_puc);
	
	}
	public void yearUpdate(ArrayList<String> year) {
		FileUtil.writeDataToFile(year, 2);
		this.year = FileUtil.readDataFromFile(2);
	}
	
}
