package edu.javaproject.model;

import java.time.LocalDateTime;

public interface DataModel {
	
	LocalDateTime getDate();
	void setDate(LocalDateTime date);
	
	int getId();
	void setId(int id);

	int getMoney();
	void setMoney(int money);

	String getKategorie();
	void setKategorie(String kategorie);

	String getText();
	void setText(String text);
	
}
