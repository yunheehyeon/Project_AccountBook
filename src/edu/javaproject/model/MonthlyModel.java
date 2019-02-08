package edu.javaproject.model;

import java.time.LocalDateTime;

public class MonthlyModel implements DataModel{

	private int id;
	private LocalDateTime date;
	private String kategorie;
	private int money;
	private String text;
	
	public MonthlyModel(int id, LocalDateTime date, String kategorie, int money, String text) {
		this.id = id;
		this.date = date;
		this.kategorie = kategorie;
		this.money = money;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getKategorie() {
		return kategorie;
	}

	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
