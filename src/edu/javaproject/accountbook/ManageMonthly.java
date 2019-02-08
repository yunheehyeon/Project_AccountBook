package edu.javaproject.accountbook;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import edu.javaproject.controller.AccountBookDaoImple;
import edu.javaproject.model.DataModel;
import edu.javaproject.model.InputModel;
import edu.javaproject.model.MonthlyModel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ManageMonthly extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable table;
	private JTextField textMoney;
	private JTextField textDay;
	private JTextArea textArea;
	
	private static String[] COLUMN_NAMES = { "날짜(일)", "분류", "금액" };
	private DefaultTableModel model;
	ArrayList<DataModel> list;
	AccountBookDaoImple dao;

	private JComboBox<String> cbkg;
	private ArrayList<String> cbList;
	/**
	 * Launch the application.
	 */
	public static void showManageMonthly(ArrayList<String> cbList) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageMonthly frame = new ManageMonthly(cbList);
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
	public ManageMonthly(ArrayList<String> cbList) {
		try {
			dao = AccountBookDaoImple.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.cbList = cbList;
		initialize();
		createTable();
		setkg();
	}

	private void initialize() {
		setTitle("반복 관리");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 454, 376);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 144, 414, 183);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deleteMonthly();
			}
		});
		scrollPane.setViewportView(table);

		textArea = new JTextArea();
		textArea.setFont(new Font("Dialog", Font.PLAIN, 13));
		textArea.setBounds(12, 72, 414, 62);
		contentPane.add(textArea);

		JButton btnInsert = new JButton("추가");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertDate();
			}
		});
		btnInsert.setFont(new Font("Dialog", Font.PLAIN, 12));
		btnInsert.setBounds(366, 41, 60, 23);
		contentPane.add(btnInsert);

		textMoney = new JTextField();
		textMoney.setBounds(265, 10, 116, 21);
		contentPane.add(textMoney);
		textMoney.setColumns(10);

		JLabel lblMoney = new JLabel("금액 :");
		lblMoney.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblMoney.setBounds(229, 13, 57, 15);
		contentPane.add(lblMoney);

		cbkg = new JComboBox<>();
		cbkg.setFont(new Font("Dialog", Font.PLAIN, 12));
		cbkg.setBounds(157, 9, 60, 23);
		contentPane.add(cbkg);

		JLabel lblKg = new JLabel("분류 :");
		lblKg.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblKg.setBounds(117, 13, 57, 15);
		contentPane.add(lblKg);

		JLabel lblMonth = new JLabel("매달 :");
		lblMonth.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblMonth.setBounds(12, 13, 57, 15);
		contentPane.add(lblMonth);

		textDay = new JTextField();
		textDay.setBounds(45, 10, 44, 21);
		contentPane.add(textDay);
		textDay.setColumns(10);

		JLabel lblText = new JLabel("내용 :");
		lblText.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblText.setBounds(12, 47, 57, 15);
		contentPane.add(lblText);

		JLabel lblDay = new JLabel("일");
		lblDay.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblDay.setBounds(93, 13, 18, 15);
		contentPane.add(lblDay);
	}
	
	private void createModel() {
		model = new DefaultTableModel(null, COLUMN_NAMES) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	}

	private void createTable() {
		createModel();
		list = dao.select();	
		Object[] rowdate = new Object[3];

		for (Object ele : list) {
			if (ele instanceof InputModel) {

			} else {
				rowdate[0] = ((MonthlyModel) ele).getDate().getDayOfMonth();
				rowdate[1] = ((MonthlyModel) ele).getKategorie();
				rowdate[2] = ((MonthlyModel) ele).getMoney();
				model.addRow(rowdate);
			}
		}
		table.setModel(model);
	}

	private void insertDate(){
		int d = Integer.parseInt(textDay.getText());
		int y = LocalDate.now().getYear();
		int m = LocalDate.now().getMonthValue();
		LocalDateTime day = LocalDateTime.of(y, m, d, 0, 0, 0);
		String kg = (String)cbkg.getSelectedItem();
		int money = Integer.parseInt(textMoney.getText());
		String text = textArea.getText();
		
		MonthlyModel mmodel = new MonthlyModel(0, day, kg, money, text);
		
		dao.insert(mmodel);
		createTable();
		textDay.setText("");
		textMoney.setText("");
		textArea.setText("");
	}
	
	private void deleteMonthly() {
	int result = JOptionPane.showConfirmDialog(this, "삭제하시겠습니까?", "삭제확인", JOptionPane.YES_NO_OPTION);
		
		if(result == JOptionPane.YES_OPTION ) {
			int row = table.getSelectedRow();
			MonthlyModel mmodel = (MonthlyModel) list.get(row);
			dao.delete(mmodel);
			createTable();
		}

	}
	
	private void setkg() {
		for (String s : cbList) {
			cbkg.addItem(s);
		}
	}
	
}
