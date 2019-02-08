package edu.javaproject.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

import edu.javaproject.model.DataModel;

public interface AccountBookDao {

	// 내역 출력 
	ArrayList<DataModel> selectPuc(LocalDateTime from, LocalDateTime to);
	
	ArrayList<DataModel> selectExp(LocalDateTime from, LocalDateTime to);
	
	ArrayList<DataModel> select();
	// 내역 입력
	int insert(DataModel dmodel);
	// 내역 업데이트
	int update(DataModel dmodel);
	// 내역 삭제
	int delete(DataModel dmodel);
}
