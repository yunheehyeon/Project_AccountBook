package edu.javaproject.accountbook;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ManageKg extends JFrame  {

	private static final long serialVersionUID = 1L;

	interface DataInputCallback{
		void notifyDataInput(ArrayList<String> kg_exp, ArrayList<String> kg_puc);
	}
	
	private JPanel contentPane;
	private JTextField textKg_exp;
	private JTextField textKg_puc;

	private JTable kgTable_exp;
	private JTable kgTable_puc;
	private static String[] COLUMN_NAMES = {"항목"};
	private DefaultTableModel model_exp;
	private DefaultTableModel model_puc;

	private ArrayList<String> kg_exp;
	private ArrayList<String> kg_puc;	
	private DataInputCallback callback;

	/**
	 * Launch the application.
	 */
	public static void showManageKg(ArrayList<String> kg_exp, ArrayList<String> kg_puc
			, DataInputCallback callback) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageKg frame = new ManageKg(kg_exp, kg_puc, callback);
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
	public ManageKg(ArrayList<String> kg_exp, ArrayList<String> kg_puc, DataInputCallback callback) {
		this.kg_exp = kg_exp;
		this.kg_puc = kg_puc;
		this.callback = callback;
		initialize();

	}
	
	private void initialize() {
		
		setTitle("분류 관리");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 370, 345);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_exp = new JLabel("지출 :");
		lbl_exp.setFont(new Font("Dialog", Font.PLAIN, 12));
		lbl_exp.setBounds(12, 29, 50, 15);
		contentPane.add(lbl_exp);
		
		JScrollPane kgPane_exp = new JScrollPane();
		kgPane_exp.setBounds(12, 54, 143, 242);
		contentPane.add(kgPane_exp);
		
		kgTable_exp = new JTable();
		kgTable_exp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deleteKgExp();
			}
		});
		createTableExp();
		kgPane_exp.setViewportView(kgTable_exp);
		
		textKg_exp = new JTextField();
		textKg_exp.setFont(new Font("Dialog", Font.PLAIN, 12));
		textKg_exp.setBounds(44, 26, 50, 20);
		contentPane.add(textKg_exp);
		textKg_exp.setColumns(10);
		
		JButton btn_exp = new JButton("추가");
		btn_exp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kg_exp.add(textKg_exp.getText());
				textKg_exp.setText("");
				callback.notifyDataInput(kg_exp, kg_puc);
				createTableExp();
			}
		});
		btn_exp.setFont(new Font("Dialog", Font.PLAIN, 12));
		btn_exp.setBounds(95, 26, 60, 20);
		contentPane.add(btn_exp);
		
		JScrollPane kgPane_puc = new JScrollPane();
		kgPane_puc.setBounds(199, 54, 143, 242);
		contentPane.add(kgPane_puc);
		
		kgTable_puc = new JTable();
		kgTable_puc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deleteKgPuc();
			}
		});
		createTablePuc();
		kgPane_puc.setViewportView(kgTable_puc);
		
		JLabel lbl_puc = new JLabel("수입 :");
		lbl_puc.setFont(new Font("Dialog", Font.PLAIN, 12));
		lbl_puc.setBounds(199, 29, 50, 15);
		contentPane.add(lbl_puc);
		
		textKg_puc = new JTextField();
		textKg_puc.setFont(new Font("Dialog", Font.PLAIN, 12));
		textKg_puc.setColumns(10);
		textKg_puc.setBounds(231, 26, 50, 20);
		contentPane.add(textKg_puc);
		
		JButton btn_puc = new JButton("추가");
		btn_puc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kg_puc.add(textKg_puc.getText());
				textKg_puc.setText("");
				callback.notifyDataInput(kg_exp, kg_puc);
				createTablePuc();
			}
		});
		btn_puc.setFont(new Font("Dialog", Font.PLAIN, 12));
		btn_puc.setBounds(282, 26, 60, 20);
		contentPane.add(btn_puc);
	}
	
	private void createKgModel() {

		model_exp = new DefaultTableModel(null, COLUMN_NAMES) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		model_puc = new DefaultTableModel(null, COLUMN_NAMES) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
	}
	private void createTableExp() {
		createKgModel();
		
		for(String s : kg_exp) {
			model_exp.addRow(new String[]{s});
		}
		kgTable_exp.setModel(model_exp);
	}
	private void createTablePuc() {
		createKgModel();
		
		for(String s : kg_puc) {
			model_puc.addRow(new String[]{s});
		}
		kgTable_puc.setModel(model_puc);
	}
	
	private void deleteKgExp() {
		int result = JOptionPane.showConfirmDialog(this, "삭제하시겠습니까?", "삭제확인", JOptionPane.YES_NO_OPTION);
		
		if(result == JOptionPane.YES_OPTION ) {
			int row = kgTable_exp.getSelectedRow();
			int column = kgTable_exp.getSelectedColumn();
			String s = (String) kgTable_exp.getValueAt(row, column);
			kg_exp.remove(s);
			
			createTableExp();
			callback.notifyDataInput(kg_exp, kg_puc);
		}
		
	}
	
	private void deleteKgPuc() {
		int result = JOptionPane.showConfirmDialog(this, "삭제하시겠습니까?", "삭제확인", JOptionPane.YES_NO_OPTION);
		
		if(result == JOptionPane.YES_OPTION ) {

			int row = kgTable_puc.getSelectedRow();
			int column = kgTable_puc.getSelectedColumn();
			String s = (String) kgTable_puc.getValueAt(row, column);
			kg_puc.remove(s);
	
			createTablePuc();
			callback.notifyDataInput(kg_exp, kg_puc);
		}
		
	}
	
	
}
