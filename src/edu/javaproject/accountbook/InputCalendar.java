package edu.javaproject.accountbook;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InputCalendar extends JFrame {

	private static final long serialVersionUID = 1L;


	interface DataInputCallback{
		void notifyDataInput(LocalDateTime selectDay);
	}
		
	
	private LocalDate nowDay = LocalDate.now();
	private static String[] COLUMN_NAMES = { "Sun", "Mon", "Tus", "Wen", "Thu", "Fri", "Sat" };
	ArrayList<String> COMBOBOX_MONTH = new ArrayList<String>(
			Arrays.asList("1","2","3","4","5","6","7","8","9","10","11","12"));
	private ArrayList<String> years;
	private JPanel contentPane;

	DefaultTableModel calendarModel;
	private JTable table;
	private JComboBox<String> cbMonth;
	private JComboBox<String> cbYear;
	
	DataInputCallback callback;
	/**
	 * Launch the application.
	 */
	public static void showCalenderFrame(ArrayList<String> years,DataInputCallback callback) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InputCalendar frame = new InputCalendar(years,callback);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InputCalendar(ArrayList<String> years, DataInputCallback callback) {
		this.years = years;
		this.callback = callback;
		initialize();

	}

	private void initialize() {
		setTitle("날짜 선택");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		cbYear = new JComboBox<>();
		for(String s : years) {
			cbYear.addItem(s);
		}
		cbYear.setSelectedItem(Integer.toString(nowDay.getYear()));
		panel.add(cbYear);
		cbYear.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				cbUpdate(e);
			}
		});

		JLabel lblNewLabel = new JLabel("년");
		panel.add(lblNewLabel);

		cbMonth = new JComboBox<>();
		for(String s : COMBOBOX_MONTH) {
			cbMonth.addItem(s);
		}
		cbMonth.setSelectedItem(Integer.toString(nowDay.getMonthValue()));
		panel.add(cbMonth);
		cbMonth.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				cbUpdate(e);
			}
		});

		JLabel lblNewLabel_1 = new JLabel("월");
		panel.add(lblNewLabel_1);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectDate();
			}
		});
		scrollPane.setViewportView(table);
		table.setFont(new Font("HY궁서", Font.PLAIN, 20));
		table.setRowHeight(30);
		table.setColumnSelectionAllowed(true);
		createCalendarModel();
		table.setModel(calendarModel);
		setTable(nowDay.getYear(), nowDay.getMonthValue());

	}
	private void cbUpdate(ItemEvent e) {
		System.out.println(e.getItem());
		String y =(String) cbYear.getSelectedItem();
		int m = cbMonth.getSelectedIndex();
		createCalendarModel();
		table.setModel(calendarModel);
		setTable(Integer.parseInt(y), m+1);
	}
	
	private void setTable(int y, int m) {
		// 오늘 날짜 생성
		LocalDate date = LocalDate.of(y, m, 1);
		// 오늘은 몇월인지 저장 -> 이번달 마지막 날인지 체크할 때 사용
		int month = date.getMonthValue();
		// 오늘은 몇일인지 저장 -> 오늘 날짜 * 추가할 때 사용
		int today = date.getDayOfMonth();
		// 달력을 출력 그달의 1일 부터 출력
		date = date.minusDays(today - 1);
		// 1일이 무슨 요일인지 알아냄
		DayOfWeek weekday = date.getDayOfWeek();
		// 월(1),..., 일(7)
		// 1일 앞에 공백을 몇칸 출력할 지 결정할 때 사용
		int weekValue = weekday.getValue();
	
		int i = 0;
		String[] rowdata = new String[7];
		for (i = 0; i < weekValue && weekValue != 7; i++) {
			rowdata[i] = "";
		}
		// 1일 부터 그달의 마지막날까지 출력
		while (date.getMonthValue() == month) {
			rowdata[i] = Integer.toString(date.getDayOfMonth());
			i++;
			date = date.plusDays(1);

			if (date.getDayOfWeek().getValue() == 7) {
				i = 0;
				calendarModel.addRow(rowdata);
			}
		}
		
		if(i != 0) {
			for (int j = i; j < 7; j++) {
				rowdata[j] = "";
			}
			calendarModel.addRow(rowdata);
		}
		
	}
	
	private void createCalendarModel() {

		calendarModel = new DefaultTableModel(null, COLUMN_NAMES) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
	}
	
	
	private void selectDate() {
		int row = table.getSelectedRow();
		int column = table.getSelectedColumn();
		int d = Integer.parseInt((String) calendarModel.getValueAt(row, column));
		int y = Integer.parseInt((String)cbYear.getSelectedItem());
		int m = Integer.parseInt((String)cbMonth.getSelectedItem());	
		LocalDateTime selectDay = LocalDateTime.of(y, m, d, 0, 0, 0);
		callback.notifyDataInput(selectDay);
		dispose();
	}


}
