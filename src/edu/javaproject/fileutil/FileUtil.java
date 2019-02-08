package edu.javaproject.fileutil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

// 파일과 관련된 기능들 (폴더 생성, 파일 read/write)을 담당하는 유틸리티 클래스
public class FileUtil {
	// 데이터 파일 이름, 데이터 파일이 저장되는 폴더 이름 - 상수로 정의
	public static final String DATA_DIR = "data";
	public static final String KG_EXP = "kategorie_exp.dat";
	public static final String KG_PUC = "kategorie_puc.dat";
	public static final String DATA_YEAR = "year.dat";
	// public static final String DATA_FLIE

	// 다른 클래스에서 인스턴스를 생성하지 못하도록 생성자를 private으로 선언
	private FileUtil() {
	}

	/**
	 * 데이터 파일(contact.dat)을 읽어서 ArrayList<Contact>를 리턴하는 메소드
	 * 
	 * @param None
	 * @return 파일에서 읽기 성공 ArrayList<Contact>를 리턴 읽기 실패인 경우는 null을 리턴
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<String> readDataFromFile(int code) {
		ArrayList<String> list = null;
		String fileName;
		
		if (code == 1)
			fileName = KG_EXP;
		else if(code == 0)
			fileName = KG_PUC;
		else
			fileName = DATA_YEAR;

		try (FileInputStream in = new FileInputStream(DATA_DIR + File.separator + fileName);
				BufferedInputStream bin = new BufferedInputStream(in);
				ObjectInputStream oin = new ObjectInputStream(bin);) {

			list = (ArrayList<String>) oin.readObject();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * ArrayList<Contact> 를 매개변수로 전달받아서, 파일에 write(씀)
	 * 
	 * @param ArrayList<Contact>
	 * @return 파일쓰기 성공 true, 쓰기 실패 false
	 */
	public static boolean writeDataToFile(ArrayList<String> list, int code) {
		boolean result = false;
		
		String fileName;
		
		if (code == 1)
			fileName = KG_EXP;
		else if(code == 0)
			fileName = KG_PUC;
		else
			fileName = DATA_YEAR;
		
		try (FileOutputStream out = new FileOutputStream(DATA_DIR + File.separator + fileName);
				BufferedOutputStream bout = new BufferedOutputStream(out);
				ObjectOutputStream oout = new ObjectOutputStream(bout);) {

			oout.writeObject(list);
			result = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 데이터 파일을 저장할 폴더가 있는지 검사하고 , 폴더가 없는 경우 새로 생성 만약 폴더 생성이 실패하는 경우는 프로그램을 계속 실행하면
	 * 안되므로 Exception을 생성해서 throw
	 * 
	 * @return void
	 * @throws Exception
	 */
	public static void initDataDir() throws Exception {
		// File 클래스의 인스턴스 생성
		File dataDir = new File(DATA_DIR);
		System.out.println("데이터 폴더" + dataDir.getAbsolutePath());
		if (dataDir.exists()) {
			System.out.println("데이터 폴더가 이미 존재합니다.");
		} else { // 데이터 폴더가 없는 경우
			if (dataDir.mkdir()) {// 데이터 폴더 생성
				System.out.println("데이터 폴더 생성 성공...");
			} else {// 폴더 생성 실패
				throw new Exception("data 폴더 생성 실패");
			}
		}
	}

	/**
	 * 데이터 파일(contact.dat)이 존재하는 지 검사 데이터 파일이 있다면, 데이터 파일을 읽어서 ArrayList를 리턴 데이터 파일이
	 * 없다면, Null를 리턴
	 */
	public static ArrayList<String> loadInitData() {
		ArrayList<String> list = null;
		// 파일 존재 여부를 검사
		File dataFile_exp = new File(DATA_DIR, KG_EXP);
		File dataFile_puc = new File(DATA_DIR, KG_PUC);
		System.out.println("데이터 파일:" + dataFile_exp.getAbsolutePath());
		System.out.println("데이터 파일:" + dataFile_puc.getAbsolutePath());
		
		if (dataFile_exp.exists() && dataFile_puc.exists()) {
			list = new ArrayList<>();
			ArrayList<String> list_puc = readDataFromFile(0);
			ArrayList<String> list_exp = readDataFromFile(1);			
			if(list_exp != null) {
				list.addAll(list_exp);
			}
			if(list_puc != null) {
				list.addAll(list_puc);
			}

			System.out.println("기존 데이터 로딩...");
		} else {
			System.out.println("새로운 데이터 생성...");
			System.out.println();
		}

		return list;
	}
	public static ArrayList<String> loadInitYearData() {
		ArrayList<String> list = null;
		// 파일 존재 여부를 검사
		File dataFile_year = new File(DATA_DIR, DATA_YEAR);
		System.out.println("데이터 파일:" + dataFile_year.getAbsolutePath());
		
		if (dataFile_year.exists()) {
			list= readDataFromFile(2);			
			System.out.println("기존 데이터 로딩...");
		} else {
			System.out.println("새로운 데이터 생성...");
			System.out.println();
			list = new ArrayList<>();
		}

		return list;
	}
	

}
