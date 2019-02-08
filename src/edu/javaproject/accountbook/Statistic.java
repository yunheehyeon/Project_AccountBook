package edu.javaproject.accountbook;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import edu.javaproject.model.DataModel;
import edu.javaproject.model.InputModel;

public class Statistic extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<String> KgList;
	private List<DataModel> list;
	private JPanel contentPane;
	private JLabel lbltop1;
	private JLabel lbltop2;
	private JLabel lbltop3;
	private JLabel lblKgMoney;
	private JLabel lblPercent;
	private JComboBox<String> comboBox;
	private JLabel lblTotal;
	
	int[] kgmoney;
	int total = 0;
	double[] rate;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	

	/**
	 * Launch the application.
	 */
	public static void showStatistic(ArrayList<String> kgList, List<DataModel> list) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Statistic frame = new Statistic(kgList, list);
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
	public Statistic(ArrayList<String> kgList, List<DataModel> list) {
		this.KgList = kgList;
		this.list = list;
		initialize();
		dataInitialize();
		setkg();
	}

	private void initialize() {
		setTitle("통계");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 290, 300);
		contentPane = new JPanel();
		contentPane.setFont(new Font("Dialog", Font.PLAIN, 12));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		comboBox = new JComboBox<>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataStatisic();
			}
		});
		comboBox.setBounds(63, 10, 63, 23);
		contentPane.add(comboBox);

		lbltop1 = new JLabel("");
		lbltop1.setFont(new Font("Dialog", Font.PLAIN, 12));
		lbltop1.setBounds(5, 118, 70, 15);
		contentPane.add(lbltop1);

		lbltop2 = new JLabel("");
		lbltop2.setFont(new Font("Dialog", Font.PLAIN, 12));
		lbltop2.setBounds(5, 158, 70, 15);
		contentPane.add(lbltop2);

		lbltop3 = new JLabel("");
		lbltop3.setFont(new Font("Dialog", Font.PLAIN, 12));
		lbltop3.setBounds(5, 198, 70, 15);
		contentPane.add(lbltop3);

		JLabel label_2 = new JLabel("분류    :");
		label_2.setFont(new Font("Dialog", Font.PLAIN, 12));
		label_2.setBounds(12, 15, 57, 15);
		contentPane.add(label_2);

		JSeparator separator = new JSeparator();
		separator.setBackground(Color.BLACK);
		separator.setBounds(12, 97, 250, 8);
		contentPane.add(separator);

		JLabel lblTop = new JLabel("Top 3");
		lblTop.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblTop.setBounds(12, 85, 57, 15);
		contentPane.add(lblTop);

		JLabel lbl = new JLabel("금액    :");
		lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		lbl.setBounds(12, 40, 57, 15);
		contentPane.add(lbl);

		JLabel label_4 = new JLabel("백분율  :");
		label_4.setFont(new Font("Dialog", Font.PLAIN, 12));
		label_4.setBounds(12, 65, 57, 15);
		contentPane.add(label_4);

		JLabel label_5 = new JLabel("총 수입    :");
		label_5.setFont(new Font("Dialog", Font.PLAIN, 12));
		label_5.setBounds(138, 14, 57, 15);
		contentPane.add(label_5);

		lblTotal = new JLabel("");
		lblTotal.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblTotal.setBounds(195, 14, 70, 15);
		contentPane.add(lblTotal);

		lblKgMoney = new JLabel("");
		lblKgMoney.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblKgMoney.setBounds(63, 39, 70, 15);
		contentPane.add(lblKgMoney);

		lblPercent = new JLabel("");
		lblPercent.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblPercent.setBounds(63, 65, 70, 15);
		contentPane.add(lblPercent);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.BLACK);
		panel.setBounds(60, 150, 200, 30);
		contentPane.add(panel);
		
		panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(Color.BLACK);
		panel_1.setBounds(60, 110, 200, 30);
		contentPane.add(panel_1);
		
		panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBackground(Color.BLACK);
		panel_2.setBounds(60, 190, 200, 30);
		contentPane.add(panel_2);
	}

	private void dataInitialize() {
		kgmoney = new int[KgList.size()];
		total = 0;
		rate = new double[KgList.size()];
		
		for (DataModel m : list) {
			if(m instanceof InputModel) {
				total += m.getMoney();
			}
		}
			
		for (int i = 0; i < KgList.size(); i++) {
			for (DataModel m : list) {
				if( m.getKategorie().equals(KgList.get(i)) ){
					kgmoney[i] += m.getMoney();
				}
			}
		}
		for(int i = 0; i < KgList.size(); i++) {
			rate[i] = (double) kgmoney[i] / total * 100;
		}
		
		Map<Double, String> map = new TreeMap<>();
		ArrayList<Double> d = new ArrayList<>();
		for(int i = 0; i < KgList.size(); i++) {
			map.put(rate[i], KgList.get(i));
			d.add(rate[i]);
		
		}
		d.sort(new Comparator<Double>() {
			@Override
			public int compare(Double o1, Double o2) {
				if(o1>o2) return -1;
				else if(o1==o2) return 0;
			    else return 1;
			}
		});
		
		panel.setBounds(80, 110, 200*d.get(0).intValue()/100, 30);
		lbltop1.setText(map.get(d.get(0))+"("+d.get(0) + "%)");
		panel_1.setBounds(80, 150, 200*d.get(1).intValue()/100, 30);
		lbltop2.setText(map.get(d.get(1))+"("+d.get(1) + "%)");
		panel_2.setBounds(80, 190, 200*d.get(2).intValue()/100, 30);
		lbltop3.setText(map.get(d.get(2))+"("+d.get(2) + "%)");
		
	}
	
	private void dataStatisic() {
		int i = comboBox.getSelectedIndex();
		lblKgMoney.setText(Integer.toString(kgmoney[i])+" 원");
		lblPercent.setText(Double.toString(rate[i])+" %");
	}
	
	private void setkg() {
		for (String s : KgList) {
			comboBox.addItem(s);
		}
		lblTotal.setText(Integer.toString(total)+" 원");
	}
	

}
