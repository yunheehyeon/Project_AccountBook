package edu.javaproject.accountbook;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ManageYear extends JFrame {

	private static final long serialVersionUID = 1L;

	interface DataInputCallback{
		void notifyDataInput(ArrayList<String> year);
	}
	
	private JPanel contentPane;
	private JTextField textField;
	private DataInputCallback callback;
	private JScrollPane scrollPane;
	
	private static String[] COLUMN_NAMES = {"연도"};
	private ArrayList<String> years;
	private DefaultTableModel model;
	private JTable tableYear;
	/**
	 * Launch the application.
	 */
	public static void showManageYear(ArrayList<String> years,DataInputCallback callback) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageYear frame = new ManageYear(years, callback);
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
	public ManageYear(ArrayList<String> years, DataInputCallback callback) {
		this.callback = callback;
		this.years = years;
		
		initialize();
		createTable();

	}

	
	private void initialize() {
		setTitle("연도 관리");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 200, 345);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("년도 :");
		label.setFont(new Font("Dialog", Font.PLAIN, 12));
		label.setBounds(12, 29, 50, 15);
		contentPane.add(label);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 54, 160, 242);
		contentPane.add(scrollPane);
		
		tableYear = new JTable();
		tableYear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deleteYear();
			}
		});
		scrollPane.setViewportView(tableYear);
		
		textField = new JTextField();
		textField.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField.setColumns(10);
		textField.setBounds(44, 26, 56, 20);
		contentPane.add(textField);
		
		JButton button = new JButton("추가");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = textField.getText();
				years.add(s);
				textField.setText("");
				callback.notifyDataInput(years);
				createTable();
			}
		});
		button.setFont(new Font("Dialog", Font.PLAIN, 12));
		button.setBounds(112, 26, 60, 20);
		contentPane.add(button);
		
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
		
		//if(years.size() == 0) tableYear.setModel(model);
		
		Collections.sort(years);
		for(String s : years) {
			model.addRow(new String[]{s});
		}
		tableYear.setModel(model);
	}
	
	private void deleteYear(){
		int result = JOptionPane.showConfirmDialog(this, "삭제하시겠습니까?", "삭제확인", JOptionPane.YES_NO_OPTION);
		
		if(result == JOptionPane.YES_OPTION ) {
			int row = tableYear.getSelectedRow();
			int column = tableYear.getSelectedColumn();
			String s = (String) tableYear.getValueAt(row, column);
			years.remove(s);
			
			createTable();
			callback.notifyDataInput(years);
		}
		
	}

}
