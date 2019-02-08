package edu.javaproject.accountbook;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import edu.javaproject.accountbook.InputCalendar.DataInputCallback;
import edu.javaproject.controller.AccountBookDaoImple;
import edu.javaproject.controller.AccountBookFileDaoImple;
import edu.javaproject.model.DataModel;
import edu.javaproject.model.InputModel;
import edu.javaproject.model.OutputModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;


public class AppMain implements DataInputCallback, ManageKg.DataInputCallback, ManageYear.DataInputCallback {

	@Override
	public void notifyDataInput(LocalDateTime selectDay) {
		textDate_exp.setText(selectDay.toLocalDate().toString());
	}

	public void notifyDataInput(ArrayList<String> years) {
		daof.yearUpdate(years);
		initializeYear();
	}

	public void notifyDataInput(ArrayList<String> kg_exp, ArrayList<String> kg_puc) {
		daof.kgUpdate(kg_exp, kg_puc);
		initializeDate();
	}

	private JFrame frame;
	private static final String[] MAIN_COLUMN_NAMES = { "날짜", "분류", "수입", "지출" };
	private static final String[] EXP_COLUMN_NAMES = { "날짜", "분류", "지출" };
	private static final String[] PUC_COLUMN_NAMES = { "날짜", "분류", "수입" };

	private static LocalDateTime NOWDAY = LocalDateTime.now();
	private static final String[] COMBOBOX_MONTH = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", };
	private static final String[] COMBOBOX_YEAR = { Integer.toString(NOWDAY.getYear()) };
	private ArrayList<String> years = new ArrayList<>();
	private JComboBox<String> cbFromYear;
	private JComboBox<String> cbFromMonth;
	private JComboBox<String> cbToYear;
	private JComboBox<String> cbToMonth;

	private JPanel cardPanel;
	private static final String PANEL_MAIN_ID = "main";
	private static final String PANEL_EXP_ID = "exp";
	private static final String PANEL_PUC_ID = "puc";
	private JPanel mainCard;
	private JPanel expenseCard;
	private JPanel purchaseCard;

	private JTable mainTable;
	private JTable expenseTable;
	private JTable purchaseTable;
	private DefaultTableModel model_main;
	private DefaultTableModel model_exp;
	private DefaultTableModel model_puc;

	private JLabel textDate_puc;
	private JLabel textDate_exp;
	private JTextField textMoney_puc;
	private JTextField textMoney_exp;

	private AccountBookDaoImple dao;
	private AccountBookFileDaoImple daof;
	
	private List<DataModel> list_all = new ArrayList<>();
	private List<DataModel> list_exp = new ArrayList<>();
	private List<DataModel> list_puc = new ArrayList<>();

	private JButton btnInsertTable;

	private JComboBox<String> cbKategorie_all;
	private JComboBox<String> cbKategorie_exp;
	private JComboBox<String> cbKategorie_puc;
	private ArrayList<String> kategorie_all;
	private ArrayList<String> kategorie_exp;
	private ArrayList<String> kategorie_puc;
	private JTextArea textArea_exp;
	private JTextArea textArea_puc;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnUpdate_exp;
	private JButton btnInsert_exp;
	private JButton btnInsert_puc;
	private JButton btnUpdate_puc;
	private JLabel lblTotalExp;
	private JLabel lblTotalPuc;
	private JLabel lblTotalCost;
	private JButton btnMain;
	private JButton btnExpense;
	private JButton btnPurchase;
	private JButton btnStats;
	private JButton btnMonthly;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppMain window = new AppMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppMain() {
		dao = AccountBookDaoImple.getInstance();
		try {
			daof = AccountBookFileDaoImple.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		initialize();
		initializeYear();
		initializeDate();
		initializeMonthly();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("가계부");
		frame.getContentPane().setFont(new Font("Gulim", Font.PLAIN, 12));
		frame.getContentPane().setForeground(Color.WHITE);
		frame.setBounds(100, 100, 560, 740);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		frame.getContentPane().setLayout(null);

		cardPanel = new JPanel();
		cardPanel.setBounds(12, 108, 423, 570);
		frame.getContentPane().add(cardPanel);
		cardPanel.setLayout(new CardLayout(0, 0));

		mianCardView();

		expenseCardView();

		purchaseCardView();

		btnMain = new JButton("메인");
		btnMain.addActionListener((e) -> {
			changePanel(PANEL_MAIN_ID);
			btntoggle(e);
		});
		btnMain.setFont(new Font("Dialog", Font.PLAIN, 25));
		btnMain.setBounds(12, 10, 87, 47);
		frame.getContentPane().add(btnMain);

		btnExpense = new JButton("지출");
		btnExpense.addActionListener((e) -> {
			changePanel(PANEL_EXP_ID);
			btntoggle(e);
		});
		btnExpense.setBounds(111, 10, 87, 47);
		frame.getContentPane().add(btnExpense);
		btnExpense.setFont(new Font("Dialog", Font.PLAIN, 25));

		btnPurchase = new JButton("수입");
		btnPurchase.addActionListener((e) -> {
			changePanel(PANEL_PUC_ID);
			btntoggle(e);
		});
		btnPurchase.setBounds(210, 10, 87, 47);
		frame.getContentPane().add(btnPurchase);
		btnPurchase.setFont(new Font("Dialog", Font.PLAIN, 25));

		btnStats = new JButton("통계");
		btnStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Statistic.showStatistic(kategorie_exp, list_all);
			}
		});
		btnStats.setFont(new Font("Dialog", Font.PLAIN, 25));
		btnStats.setBounds(309, 10, 87, 47);
		frame.getContentPane().add(btnStats);
		// 날짜 선택
		JPanel dateData = new JPanel();
		dateData.setBounds(12, 67, 365, 35);
		frame.getContentPane().add(dateData);

		cbFromYear = new JComboBox<>();
		for (String s : COMBOBOX_YEAR) {
			cbFromYear.addItem(s);
		}
		dateData.add(cbFromYear);

		JLabel lblFromYear = new JLabel("년");
		lblFromYear.setFont(new Font("Dialog", Font.PLAIN, 12));
		dateData.add(lblFromYear);

		cbFromMonth = new JComboBox<>();
		for (String s : COMBOBOX_MONTH) {
			cbFromMonth.addItem(s);
		}
		cbFromMonth.setSelectedItem(Integer.toString(NOWDAY.getMonthValue()));
		dateData.add(cbFromMonth);

		JLabel lblFromMonth = new JLabel("월 ~");
		lblFromMonth.setFont(new Font("Dialog", Font.PLAIN, 12));
		dateData.add(lblFromMonth);

		cbToYear = new JComboBox<>();
		for (String s : COMBOBOX_YEAR) {
			cbToYear.addItem(s);
		}
		cbToYear.setSelectedItem(Integer.toString(NOWDAY.getYear()));
		dateData.add(cbToYear);

		JLabel labToYear = new JLabel("년");
		labToYear.setFont(new Font("Dialog", Font.PLAIN, 12));
		dateData.add(labToYear);

		cbToMonth = new JComboBox<>();
		for (String s : COMBOBOX_MONTH) {
			cbToMonth.addItem(s);
		}
		cbToMonth.setSelectedItem(Integer.toString(NOWDAY.getMonthValue()));
		dateData.add(cbToMonth);

		JLabel labToMonth = new JLabel("월");
		labToMonth.setFont(new Font("Dialog", Font.PLAIN, 12));
		dateData.add(labToMonth);

		btnInsertTable = new JButton("검색");
		btnInsertTable.setFont(new Font("Dialog", Font.PLAIN, 12));
		btnInsertTable.addActionListener((e) -> {
			selectData();
			showMianTable();
			showExpTable();
			showPucTable();
		});
		dateData.add(btnInsertTable);
		// end
		
		JButton btnKg = new JButton("분류관리");
		btnKg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManageKg.showManageKg(kategorie_exp, kategorie_puc, AppMain.this);
			}
		});
		btnKg.setFont(new Font("Dialog", Font.PLAIN, 15));
		btnKg.setBounds(435, 47, 93, 27);
		frame.getContentPane().add(btnKg);

		JButton btnYear = new JButton("연도관리");
		btnYear.addActionListener((e) -> {
			if (years.size() != 0)
				ManageYear.showManageYear(years, AppMain.this);
			else {
				years.add(Integer.toString(NOWDAY.getYear()));
				ManageYear.showManageYear(years, AppMain.this);
			}
		});
		btnYear.setFont(new Font("Dialog", Font.PLAIN, 15));
		btnYear.setBounds(435, 10, 93, 27);
		frame.getContentPane().add(btnYear);
		
		btnMonthly = new JButton("반복관리");
		btnMonthly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManageMonthly.showManageMonthly(kategorie_all);
			}
		});
		btnMonthly.setFont(new Font("Dialog", Font.PLAIN, 15));
		btnMonthly.setBounds(435, 84, 93, 27);
		frame.getContentPane().add(btnMonthly);

		JLabel labelPuc = new JLabel("총 수입 :");
		labelPuc.setFont(new Font("Dialog", Font.PLAIN, 12));
		labelPuc.setBounds(440, 128, 57, 18);
		frame.getContentPane().add(labelPuc);

		JLabel labelExp = new JLabel("총 지출 :");
		labelExp.setFont(new Font("Dialog", Font.PLAIN, 12));
		labelExp.setBounds(440, 174, 57, 18);
		frame.getContentPane().add(labelExp);
		
		JLabel labelCost = new JLabel("총 수입 - 총 지출:");
		labelCost.setFont(new Font("Dialog", Font.PLAIN, 12));
		labelCost.setBounds(440, 215, 95, 18);
		frame.getContentPane().add(labelCost);

		lblTotalPuc = new JLabel("0 원");
		lblTotalPuc.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblTotalPuc.setBounds(440, 145, 95, 15);
		frame.getContentPane().add(lblTotalPuc);

		lblTotalExp = new JLabel("0 원");
		lblTotalExp.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblTotalExp.setBounds(440, 190, 95, 15);
		frame.getContentPane().add(lblTotalExp);
		
		lblTotalCost = new JLabel("0 원");
		lblTotalCost.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblTotalCost.setBounds(440, 234, 95, 15);
		frame.getContentPane().add(lblTotalCost);
	}

	private void mianCardView() {

		mainCard = new JPanel();
		mainCard.setLayout(null);
		cardPanel.add(mainCard, "main");

		JScrollPane resultList = new JScrollPane();
		resultList.setBounds(12, 33, 399, 527);
		mainCard.add(resultList);

		mainTable = new JTable();
		mainTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deleteData();
			}
		});
		resultList.setViewportView(mainTable);
		model_main = new DefaultTableModel(null, MAIN_COLUMN_NAMES);
		mainTable.setModel(model_main);

		JRadioButton rdbtnAll = new JRadioButton("전체");
		rdbtnAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showMianTable();
			}
		});
		rdbtnAll.setSelected(true);
		buttonGroup.add(rdbtnAll);
		rdbtnAll.setFont(new Font("Dialog", Font.PLAIN, 12));
		rdbtnAll.setBounds(12, 4, 49, 23);
		mainCard.add(rdbtnAll);

		JRadioButton rdbtnExp = new JRadioButton("지출");
		rdbtnExp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showMainTableExp();
			}
		});
		buttonGroup.add(rdbtnExp);
		rdbtnExp.setFont(new Font("Dialog", Font.PLAIN, 12));
		rdbtnExp.setBounds(65, 4, 49, 23);
		mainCard.add(rdbtnExp);

		JRadioButton rdbtnPuc = new JRadioButton("수입");
		rdbtnPuc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showMainTablePuc();
			}
		});
		buttonGroup.add(rdbtnPuc);
		rdbtnPuc.setFont(new Font("Dialog", Font.PLAIN, 12));
		rdbtnPuc.setBounds(118, 4, 49, 23);
		mainCard.add(rdbtnPuc);

		cbKategorie_all = new JComboBox<>();
		cbKategorie_all.setFont(new Font("Dialog", Font.PLAIN, 12));
		cbKategorie_all.setBounds(265, 4, 80, 25);
		mainCard.add(cbKategorie_all);

		JLabel lblNewLabel = new JLabel("분류 :");
		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblNewLabel.setBounds(230, 10, 42, 15);
		mainCard.add(lblNewLabel);
		
		JButton button = new JButton("검색");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showMainTableKg();
			}
		});
		button.setFont(new Font("Dialog", Font.PLAIN, 12));
		button.setBounds(351, 3, 60, 27);
		mainCard.add(button);
	}

	private void expenseCardView() {

		expenseCard = new JPanel();
		cardPanel.add(expenseCard, "exp");
		expenseCard.setLayout(null);

		JScrollPane expensePane = new JScrollPane();
		expensePane.setBounds(12, 140, 399, 420);
		expenseCard.add(expensePane);

		expenseTable = new JTable();
		expenseTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clickTableExp();
			}
		});
		expensePane.setViewportView(expenseTable);
		model_exp = new DefaultTableModel(null, EXP_COLUMN_NAMES);
		expenseTable.setModel(model_exp);

		JLabel lblDate_exp = new JLabel("날짜:");
		lblDate_exp.setBounds(12, 10, 27, 21);
		expenseCard.add(lblDate_exp);
		lblDate_exp.setFont(new Font("Dialog", Font.PLAIN, 12));

		textDate_exp = new JLabel();
		textDate_exp.setFont(new Font("Dialog", Font.PLAIN, 12));
		textDate_exp.setBounds(44, 10, 99, 21);
		textDate_exp.setText(NOWDAY.toLocalDate().toString());
		expenseCard.add(textDate_exp);
		textDate_exp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				InputCalendar.showCalenderFrame(years, AppMain.this);
			}
		});

		JLabel lblKategorie_exp = new JLabel("분류:");
		lblKategorie_exp.setBounds(140, 10, 27, 21);
		expenseCard.add(lblKategorie_exp);
		lblKategorie_exp.setFont(new Font("Dialog", Font.PLAIN, 12));

		cbKategorie_exp = new JComboBox<>();
		cbKategorie_exp.setBounds(170, 10, 80, 21);
		expenseCard.add(cbKategorie_exp);
		cbKategorie_exp.setFont(new Font("Dialog", Font.PLAIN, 12));

		JLabel lblMoney_exp = new JLabel("금액:");
		lblMoney_exp.setBounds(270, 10, 27, 21);
		expenseCard.add(lblMoney_exp);
		lblMoney_exp.setFont(new Font("Dialog", Font.PLAIN, 12));

		textMoney_exp = new JTextField();
		textMoney_exp.setFont(new Font("Dialog", Font.PLAIN, 12));
		textMoney_exp.setBounds(299, 10, 101, 21);
		expenseCard.add(textMoney_exp);
		textMoney_exp.setColumns(10);

		JLabel lbltext_exp = new JLabel("내용 :");
		lbltext_exp.setFont(new Font("Dialog", Font.PLAIN, 12));
		lbltext_exp.setBounds(12, 35, 57, 15);
		expenseCard.add(lbltext_exp);

		textArea_exp = new JTextArea();
		textArea_exp.setBounds(12, 52, 399, 50);
		expenseCard.add(textArea_exp);

		btnInsert_exp = new JButton("추가");
		btnInsert_exp.addActionListener((e) -> {
			insertDateExp();
		});
		btnInsert_exp.setFont(new Font("Dialog", Font.PLAIN, 12));
		btnInsert_exp.setBounds(351, 107, 60, 23);
		expenseCard.add(btnInsert_exp);

		btnUpdate_exp = new JButton("수정");
		btnUpdate_exp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateExp();
			}
		});
		btnUpdate_exp.setEnabled(false);
		btnUpdate_exp.setFont(new Font("Dialog", Font.PLAIN, 12));
		btnUpdate_exp.setBounds(289, 107, 60, 23);
		expenseCard.add(btnUpdate_exp);
	}

	private void purchaseCardView() {

		purchaseCard = new JPanel();
		cardPanel.add(purchaseCard, "puc");
		purchaseCard.setLayout(null);

		JScrollPane purchasePane = new JScrollPane();
		purchasePane.setBounds(12, 140, 399, 420);
		purchaseCard.add(purchasePane);

		purchaseTable = new JTable();
		purchaseTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clickTablePcu();
			}
		});

		purchasePane.setViewportView(purchaseTable);
		model_puc = new DefaultTableModel(null, PUC_COLUMN_NAMES);
		purchaseTable.setModel(model_puc);

		JLabel lblDate_puc = new JLabel("날짜:");
		lblDate_puc.setBounds(12, 10, 27, 21);
		purchaseCard.add(lblDate_puc);
		lblDate_puc.setFont(new Font("Dialog", Font.PLAIN, 12));

		textDate_puc = new JLabel();
		textDate_puc.setText(NOWDAY.toLocalDate().toString());
		textDate_puc.setFont(new Font("Dialog", Font.PLAIN, 12));
		textDate_puc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				InputCalendar.showCalenderFrame(years, AppMain.this);
			}
		});
		textDate_puc.setBounds(44, 10, 99, 21);
		purchaseCard.add(textDate_puc);

		JLabel lblKategorie_puc = new JLabel("분류:");
		lblKategorie_puc.setBounds(140, 10, 27, 21);
		purchaseCard.add(lblKategorie_puc);
		lblKategorie_puc.setFont(new Font("Dialog", Font.PLAIN, 12));

		cbKategorie_puc = new JComboBox<>();
		cbKategorie_puc.setBounds(170, 10, 80, 21);
		purchaseCard.add(cbKategorie_puc);
		cbKategorie_puc.setFont(new Font("Dialog", Font.PLAIN, 12));

		JLabel lblMoney_puc = new JLabel("금액:");
		lblMoney_puc.setBounds(270, 10, 27, 21);
		purchaseCard.add(lblMoney_puc);
		lblMoney_puc.setFont(new Font("Dialog", Font.PLAIN, 12));

		textMoney_puc = new JTextField();
		textMoney_puc.setBounds(299, 10, 101, 21);
		purchaseCard.add(textMoney_puc);
		textMoney_puc.setColumns(10);

		JLabel lbltext_puc = new JLabel("내용 :");
		lbltext_puc.setFont(new Font("Dialog", Font.PLAIN, 12));
		lbltext_puc.setBounds(12, 35, 57, 15);
		purchaseCard.add(lbltext_puc);

		textArea_puc = new JTextArea();
		textArea_puc.setBounds(12, 52, 399, 50);
		purchaseCard.add(textArea_puc);

		btnInsert_puc = new JButton("추가");
		btnInsert_puc.addActionListener((e) -> {
			insertDatePuc();
		});
		btnInsert_puc.setFont(new Font("Dialog", Font.PLAIN, 12));
		btnInsert_puc.setBounds(351, 107, 60, 23);
		purchaseCard.add(btnInsert_puc);
		
		btnUpdate_puc = new JButton("수정");
		btnUpdate_puc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePuc();
			}
		});
		btnUpdate_puc.setEnabled(false);
		btnUpdate_puc.setFont(new Font("Dialog", Font.PLAIN, 12));
		btnUpdate_puc.setBounds(289, 107, 60, 23);
		purchaseCard.add(btnUpdate_puc);

	}

	private void selectData() {

		int yfrom = Integer.parseInt((String) cbFromYear.getSelectedItem());
		int mfrom = Integer.parseInt((String) cbFromMonth.getSelectedItem());
		LocalDateTime datefrom = LocalDateTime.of(yfrom, mfrom, 1, 0, 0, 0);

		int yto = Integer.parseInt((String) cbToYear.getSelectedItem());
		int mto = Integer.parseInt((String) cbToMonth.getSelectedItem());
		LocalDate temp = LocalDate.of(yto, mto, 1);
		temp = temp.plusMonths(1);
		temp = temp.minusDays(1);
		int maxday = temp.getDayOfMonth();
		LocalDateTime dateto = LocalDateTime.of(yto, mto, maxday, 0, 0, 0);

		list_puc = dao.selectPuc(datefrom, dateto);
		list_exp = dao.selectExp(datefrom, dateto);
		list_all.removeAll(list_all);
		list_all.addAll(list_puc);
		list_all.addAll(list_exp);

		Collections.sort(list_all, new CompareDateAsc());

	}
	
	private void showTotalCost(List<DataModel> list) {
		int p = 0;
		int e = 0;
		for(DataModel m : list) {
			if ( m instanceof InputModel) {
				p += m.getMoney();
			} else {
				e += m.getMoney();
			}
		}
		lblTotalPuc.setText(Integer.toString(p) + "원");
		lblTotalExp.setText(Integer.toString(e) + "원");
		lblTotalCost.setText(Integer.toString(p-e) + "원");
	}

	private void showMianTable() {

		createTableModelMain();
		Object[] rowdate = new Object[4];

		for (DataModel ele : list_all) {
			if (ele instanceof InputModel) {
				rowdate[0] = ((InputModel) ele).getDate().toLocalDate();
				rowdate[1] = ((InputModel) ele).getKategorie();
				rowdate[2] = ((InputModel) ele).getMoney();
				rowdate[3] = "";
				model_main.addRow(rowdate);
			} else {
				rowdate[0] = ((OutputModel) ele).getDate().toLocalDate();
				rowdate[1] = ((OutputModel) ele).getKategorie();
				rowdate[2] = "";
				rowdate[3] = ((OutputModel) ele).getMoney();
				model_main.addRow(rowdate);
			}
		}
		showTotalCost(list_all);
	}

	private void showMainTableExp() {
		createTableModelMain();
		Object[] rowdate = new Object[4];

		for (DataModel ele : list_all) {
			if (ele instanceof InputModel) {

			} else {
				rowdate[0] = ((OutputModel) ele).getDate().toLocalDate();
				rowdate[1] = ((OutputModel) ele).getKategorie();
				rowdate[3] = ((OutputModel) ele).getMoney();
				model_main.addRow(rowdate);
			}
		}
	}

	private void showMainTablePuc() {
		createTableModelMain();
		Object[] rowdate = new Object[4];

		for (DataModel ele : list_all) {
			if (ele instanceof InputModel) {
				rowdate[0] = ((InputModel) ele).getDate().toLocalDate();
				rowdate[1] = ((InputModel) ele).getKategorie();
				rowdate[2] = ((InputModel) ele).getMoney();
				model_main.addRow(rowdate);
			}
		}
	}

	private void showMainTableKg() {
		createTableModelMain();
		Object[] rowdate = new Object[4];
		String kg = (String) cbKategorie_all.getSelectedItem();

		for (DataModel ele : list_all) {
			if (kg.equals(ele.getKategorie())) {
				if (ele instanceof InputModel) {
					rowdate[0] = ((InputModel) ele).getDate().toLocalDate();
					rowdate[1] = ((InputModel) ele).getKategorie();
					rowdate[2] = ((InputModel) ele).getMoney();
					rowdate[3] = "";
					model_main.addRow(rowdate);
				} else {
					rowdate[0] = ((OutputModel) ele).getDate().toLocalDate();
					rowdate[1] = ((OutputModel) ele).getKategorie();
					rowdate[2] = "";
					rowdate[3] = ((OutputModel) ele).getMoney();
					model_main.addRow(rowdate);
				}
			}
		}
	}

	private void showExpTable() {
		createTableModelExp();
		Object[] rowdate = new Object[3];

		for (DataModel ele : list_exp) {
			rowdate[0] = ((OutputModel) ele).getDate().toLocalDate();
			rowdate[1] = ((OutputModel) ele).getKategorie();
			rowdate[2] = ((OutputModel) ele).getMoney();
			model_exp.addRow(rowdate);
		}
	}

	private void showPucTable() {
		createTableModelPuc();
		Object[] rowdate = new Object[3];

		for (DataModel ele : list_puc) {
			rowdate[0] = ((InputModel) ele).getDate().toLocalDate();
			rowdate[1] = ((InputModel) ele).getKategorie();
			rowdate[2] = ((InputModel) ele).getMoney();
			model_puc.addRow(rowdate);
		}
	}

	private void createTableModelMain() {
		model_main = new DefaultTableModel(null, MAIN_COLUMN_NAMES) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		mainTable.setModel(model_main);
	}

	private void createTableModelExp() {
		model_exp = new DefaultTableModel(null, EXP_COLUMN_NAMES) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		expenseTable.setModel(model_exp);
	}

	private void createTableModelPuc() {
		model_puc = new DefaultTableModel(null, PUC_COLUMN_NAMES) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		purchaseTable.setModel(model_puc);
	}

	private void insertDateExp() {
		String s = textDate_exp.getText();
		String[] parsing = s.split("-");
		LocalDateTime date = LocalDateTime.of(Integer.parseInt(parsing[0]),
				Integer.parseInt(parsing[1]),
				Integer.parseInt(parsing[2]), 0,0,0);
		
		String kg = (String) cbKategorie_exp.getSelectedItem();
		int money = Integer.parseInt(textMoney_exp.getText());
		String text = textArea_exp.getText();

		OutputModel input = new OutputModel(0, date, kg, money, text);
		int result = dao.insert(input);
		if (result == 1) {
			JOptionPane.showMessageDialog(frame, "데이터 삽입 성공");
		} else {

		}
		upDateTable();
		cleartext();
	}

	private void insertDatePuc() {
		String s = textDate_puc.getText();
		String[] parsing = s.split("-");
		LocalDateTime date = LocalDateTime.of(Integer.parseInt(parsing[0]),
				Integer.parseInt(parsing[1]),
				Integer.parseInt(parsing[2]), 0,0,0);
		
		String kg = (String) cbKategorie_puc.getSelectedItem();
		int money = Integer.parseInt(textMoney_puc.getText());
		String text = textArea_puc.getText();

		InputModel input = new InputModel(0, date, kg, money, text);
		int result = dao.insert(input);
		if (result == 1) {
			JOptionPane.showMessageDialog(frame, "데이터 삽입 성공");
		} else {

		}
		upDateTable();
		cleartext();
	}


	private void deleteData() {
		int conf = JOptionPane.showConfirmDialog(frame, "삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
		if (conf == JOptionPane.YES_OPTION) {
			int row = mainTable.getSelectedRow();

			DataModel dmodel = list_all.get(row);

			int result = dao.delete(dmodel);
			if (result == 1) {
				JOptionPane.showMessageDialog(frame, "삭제 완료");
			}
		}
		upDateTable();
	}

	private void updateExp(){
		int row = expenseTable.getSelectedRow();
		String s = textDate_exp.getText();
		String[] parsing = s.split("-");
		LocalDateTime day = LocalDateTime.of(Integer.parseInt(parsing[0]),
				Integer.parseInt(parsing[1]),
				Integer.parseInt(parsing[2]), 0,0,0);
		String kg = (String) cbKategorie_exp.getSelectedItem();
		int money = Integer.parseInt(textMoney_exp.getText());
		String text = textArea_exp.getText();
		OutputModel m = new OutputModel(list_exp.get(row).getId(), day, kg, money, text);
		dao.update(m);
		
		btnInsert_exp.setEnabled(true);
		btnUpdate_exp.setEnabled(false);
		upDateTable();
		cleartext();
	}
	
	private void updatePuc(){
		int row = purchaseTable.getSelectedRow();
		String s = textDate_puc.getText();
		String[] parsing = s.split("-");
		LocalDateTime day = LocalDateTime.of(Integer.parseInt(parsing[0]),
				Integer.parseInt(parsing[1]),
				Integer.parseInt(parsing[2]), 0,0,0);
		String kg = (String) cbKategorie_puc.getSelectedItem();
		int money = Integer.parseInt(textMoney_puc.getText());
		String text = textArea_puc.getText();
		InputModel m = new InputModel(list_puc.get(row).getId(), day, kg, money, text);
		dao.update(m);
		
		btnInsert_puc.setEnabled(true);
		btnUpdate_puc.setEnabled(false);
		upDateTable();
		cleartext();
	}
	
	private void clickTableExp() {
		btntoggle_exp();
		int row = expenseTable.getSelectedRow();
		DataModel dmodel = list_exp.get(row);
		textDate_exp.setText(dmodel.getDate().toLocalDate().toString());
		cbKategorie_exp.setSelectedItem(dmodel.getKategorie());
		textMoney_exp.setText(Integer.toString(dmodel.getMoney()));
		textArea_exp.setText(dmodel.getText());
			
	}
	
	private void clickTablePcu() {
		btntoggle_puc();
		int row = purchaseTable.getSelectedRow();
		DataModel dmodel = list_puc.get(row);
		textDate_puc.setText(dmodel.getDate().toLocalDate().toString());
		cbKategorie_puc.setSelectedItem(dmodel.getKategorie());
		textMoney_puc.setText(Integer.toString(dmodel.getMoney()));
		textArea_puc.setText(dmodel.getText());
	}

	private void cleartext() {
		textArea_exp.setText("");
		textArea_puc.setText("");
		textMoney_exp.setText("");
		textMoney_puc.setText("");
	}
	
	private void upDateTable() {
		selectData();
		showMianTable();
		showExpTable();
		showPucTable();
	}
	
	private void btntoggle_exp() {
		if (btnInsert_exp.isEnabled()) {
			btnInsert_exp.setEnabled(false);
			btnUpdate_exp.setEnabled(true);
		} else if(btnUpdate_exp.isEnabled()==false) {
			btnInsert_exp.setEnabled(true);
			btnUpdate_exp.setEnabled(false);
		}
	}

	private void btntoggle_puc() {
		if (btnInsert_puc.isEnabled()) {
			btnInsert_puc.setEnabled(false);
			btnUpdate_puc.setEnabled(true);
		} else if(btnUpdate_puc.isEnabled()==false) {
			btnInsert_puc.setEnabled(true);
			btnUpdate_puc.setEnabled(false);
		}
	}
	
	private void btntoggle(ActionEvent e) {
		
		if (e.getActionCommand() == "메인") {
			btnMain.setEnabled(false);
			btnExpense.setEnabled(true);
			btnPurchase.setEnabled(true);
		} else if(e.getActionCommand()=="지출") {
			btnMain.setEnabled(true);
			btnExpense.setEnabled(false);
			btnPurchase.setEnabled(true);
		} else if(e.getActionCommand() == "수입") {
			btnMain.setEnabled(true);
			btnExpense.setEnabled(true);
			btnPurchase.setEnabled(false);
		}
	}
	
	
	private void changePanel(String id) {
		CardLayout layout = (CardLayout) cardPanel.getLayout();
		layout.show(cardPanel, id);

	}

	private void initializeYear() {
		years = daof.getYear();
		cbFromYear.removeAllItems();
		cbToYear.removeAllItems();

		Collections.sort(years);
		if (years.size() == 0) {
			for (String s : COMBOBOX_YEAR) {
				cbFromYear.addItem(s);
				cbToYear.addItem(s);
			}
		} else {
			for (String s : years) {
				cbFromYear.addItem(s);
				cbToYear.addItem(s);
			}
		}

		cbFromYear.setSelectedItem(Integer.toString(NOWDAY.getYear()));
		cbToYear.setSelectedItem(Integer.toString(NOWDAY.getYear()));
	}

	private void initializeDate() {
		kategorie_all = daof.getKategorie_all();
		kategorie_exp = daof.getKategorie_exp();
		kategorie_puc = daof.getKategorie_puc();

		cbKategorie_all.removeAllItems();
		cbKategorie_exp.removeAllItems();
		cbKategorie_puc.removeAllItems();

		for (String s : kategorie_all) {
			cbKategorie_all.addItem(s);
		}
		for (String s : kategorie_exp) {
			cbKategorie_exp.addItem(s);
		}
		for (String s : kategorie_puc) {
			cbKategorie_puc.addItem(s);
		}
	}
	
	private void initializeMonthly(){
		ArrayList<DataModel> mmodel = dao.select();
		for(DataModel m : mmodel) {
			if(m.getDate().compareTo(NOWDAY) <= 0) {
				
				OutputModel out = new OutputModel(m.getId(),
						m.getDate(), m.getKategorie(), m.getMoney(), m.getText());
				
				dao.insert(out);
				LocalDateTime t = m.getDate();
				t = t.plusMonths(1);
				m.setDate(t);
				dao.update(m);
			}
		}
	}
	
}

class CompareDateAsc implements Comparator<DataModel> {

	@Override
	public int compare(DataModel o1, DataModel o2) {
		// TODO Auto-generated method stub
		return o1.getDate().compareTo(o2.getDate());
	}
}
