package org.integratedmodelling.mca.contrib.j_electre.ui;

//	Copyright � 2017 by Valdecy Pereira, Helder Gomes Costa and Livia Dias de Oliveira Nepomuceno.

//	This file is part of J-ELECTRE.
//
//	J-ELECTRE is free software: you can redistribute it and/or modify
//	it under the terms of the GNU General Public License as published by
//	the Free Software Foundation, either version 3 of the License, or
//	(at your option) any later version.
//
//	J-ELECTRE is distributed in the hope that it will be useful,
//	but WITHOUT ANY WARRANTY; without even the implied warranty of
//	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//	GNU General Public License for more details.
//
//	You should have received a copy of the GNU General Public License
//	along with J-ELECTRE.  If not, see <http://www.gnu.org/licenses/>.

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.integratedmodelling.mca.contrib.j_electre.matrix.ELECTRE_I;
import org.integratedmodelling.mca.contrib.j_electre.matrix.ELECTRE_II;
import org.integratedmodelling.mca.contrib.j_electre.matrix.ELECTRE_III;
import org.integratedmodelling.mca.contrib.j_electre.matrix.ELECTRE_IV;
import org.integratedmodelling.mca.contrib.j_electre.matrix.ELECTRE_I_s;
import org.integratedmodelling.mca.contrib.j_electre.matrix.ELECTRE_I_v;
import org.integratedmodelling.mca.contrib.j_electre.matrix.ELECTRE_Tri;
import org.integratedmodelling.mca.contrib.j_electre.matrix.ExcelAdapter;

import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class Interface {
	
	// AAARGH these must turn into parameters
	public  static int    C = 0;
	public  static int    A = 0;
	public  static int    MaximumCycles = 15;
	public  static double EI_Q;
	public  static double EI_P;
	public  static double EI_s_Lambda = 0.5;
	public  static double EI_v_Q;
	public  static double EI_v_P;
	public  static double EII_Cm;
	public  static double EII_C;
	public  static double EII_Cp;
	public  static double EII_d1;
	public  static double EII_d2;
	public  static int    ETri_Classes;
	public  static double ETri_Lambda;
	public  static int    ETriMe_Classes;
	public  static double ETriMe_Lambda;
	public  static int    ETriMe_Evaluators;
	private static JTable table;
	private static JTable table_1;
	public  static int    electre;

	@SuppressWarnings("serial")
	public static void main(String[] args) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
		}

		JFrame f = new JFrame("A JFrame");
		f.setTitle("J-ELECTRE - github.com/Valdecy");
		f.setSize(1085, 643);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		f.setLocation(dim.width/2-f.getSize().width/2, dim.height/2-f.getSize().height/2);
		f.setResizable(false);

		JPanel panel = new JPanel();
		panel.setBounds(7, 7, 531, 253);
		panel.setBackground(Color.WHITE);
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(7, 328, 531, 269);

		JDialog dialog = new JDialog();
		JLabel label = new JLabel("        Please Wait...");
		dialog.setLocationRelativeTo(null);
		dialog.setTitle("     Please Wait...");
		dialog.getContentPane().add(label);
		dialog.pack();
		dialog.setSize(210,65);
		dialog.setVisible(false);
		dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		int p = 7;
		int q = 14;

		String[] criteria = new String[p + 1];
		String[][] alternatives = new String[q + 1][p + 1];

		for (int i = 0; i < p + 1; i++){
			criteria[i] = "";
		}
		criteria[0] = "     Matrix";
		for (int i = 0; i < q + 1; i++){		
			for (int j = 0; j < p + 1; j++){		
				alternatives[i][j] = "";
			}
		}
		DefaultTableModel model = new DefaultTableModel( alternatives, criteria );
		table = new JTable( model ){

			public boolean isCellEditable(int row,int column){
				int modelRow = convertRowIndexToModel(row);
				int modelCol = convertColumnIndexToModel(column);
				String typeRow = (String)getModel().getValueAt(modelRow, 0);
				String typeCol = (String) table.getColumnModel().getColumn(modelCol).getHeaderValue();
				if(column == 0) return false;
				if (!"".equals(typeCol) & !"".equals(typeRow)) return true;
				return false;
			}

			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) // Change Row Color
			{
				Component c = super.prepareRenderer(renderer, row, column);

				if (!isRowSelected(row))
				{
					c.setBackground(getBackground());
					int modelRow = convertRowIndexToModel(row);
					String type = (String)getModel().getValueAt(modelRow, 0);
					if (type.indexOf("b") >= 0) c.setBackground(Color.cyan);
					if ("Q".equals(type)) c.setBackground(Color.GREEN);
					if ("P".equals(type)) c.setBackground(Color.GREEN);
					if ("V".equals(type)) c.setBackground(Color.GREEN);
					if ("W".equals(type)) c.setBackground(Color.YELLOW);
				}

				return c;
			}			
		};
		table.setCellSelectionEnabled(true);
		table.setShowVerticalLines(true);
		table.setShowHorizontalLines(true);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer(); // Center values in the first column
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		table.putClientProperty("terminateEditOnFocusLost", true);// Confirm all entered values 
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		@SuppressWarnings("unused")
		ExcelAdapter enable = new ExcelAdapter(table); // Copy & Paste	

		scrollPane.setViewportView(table);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(542, 7, 520, 590);

		int r = 35;
		int s = 7;
		DefaultTableModel model_1 = new DefaultTableModel( r, s  );
		table_1 = new JTable( model_1);

		scrollPane_1.setViewportView(table_1);
		table_1.setCellSelectionEnabled(true);
		table_1.setBackground(Color.WHITE);
		table_1.setSelectionBackground(Color.BLACK);
		table_1.setShowVerticalLines(true);
		table_1.setShowHorizontalLines(true);
//		table_1.getColumnModel().getColumn(0).setPreferredWidth(170);
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		DefaultTableCellRenderer centerRenderer2 = new DefaultTableCellRenderer(); // Center values in the first column
		centerRenderer2.setHorizontalAlignment(JLabel.CENTER);
		table_1.getColumnModel().getColumn(0).setCellRenderer(centerRenderer2);
		DefaultCellEditor editor = (DefaultCellEditor) table_1.getDefaultEditor(Object.class);
		editor.setClickCountToStart(300000);	

		JLabel lblEIQ = new JLabel("d:");
		lblEIQ.setBackground(Color.WHITE);
		lblEIQ.setBounds(126, 10, 16, 14);
		panel.add(lblEIQ);

		JSpinner spinnerEIQ = new JSpinner();
		spinnerEIQ.setBounds(146, 7, 58, 25);
		spinnerEIQ.setModel(new SpinnerNumberModel(0, 0, 1, 0.01)); // SpinnerNumberModel(value, min, max, step);
		panel.add(spinnerEIQ);

		JLabel lblEIP = new JLabel("c:");
		lblEIP.setBackground(Color.WHITE);
		lblEIP.setBounds(220, 10, 16, 14);
		panel.add(lblEIP);

		JSpinner spinnerEIP = new JSpinner();
		spinnerEIP.setBounds(240, 7, 58, 25);
		spinnerEIP.setModel(new SpinnerNumberModel(0, 0, 1, 0.01));
		panel.add(spinnerEIP);

		JLabel lblEI_sLambda = new JLabel("L:");
		lblEI_sLambda.setBackground(Color.WHITE);
		lblEI_sLambda.setBounds(126, 37, 16, 14);
		panel.add(lblEI_sLambda);

		JSpinner spinnerEI_sLambda = new JSpinner();
		spinnerEI_sLambda.setBounds(146, 34, 58, 25);
		spinnerEI_sLambda.setModel(new SpinnerNumberModel(0.5, 0.5, 1, 0.01));
		panel.add(spinnerEI_sLambda);

		JLabel lblEI_vQ = new JLabel("d:");
		lblEI_vQ.setBackground(Color.WHITE);
		lblEI_vQ.setBounds(126, 66, 16, 14);
		panel.add(lblEI_vQ);

		JSpinner spinnerEI_vQ = new JSpinner();
		spinnerEI_vQ.setBounds(146, 61, 58, 25);
		spinnerEI_vQ.setModel(new SpinnerNumberModel(0.0, 0, 1, 1));
		panel.add(spinnerEI_vQ);

		JLabel lblEI_vP = new JLabel("c:");
		lblEI_vP.setBackground(Color.WHITE);
		lblEI_vP.setBounds(220, 66, 16, 14);
		panel.add(lblEI_vP);

		JSpinner spinnerEI_vP = new JSpinner();
		spinnerEI_vP.setBounds(240, 61, 58, 25);
		spinnerEI_vP.setModel(new SpinnerNumberModel(0, 0, 1, 0.01));
		panel.add(spinnerEI_vP);

		JLabel lblEIICp = new JLabel("c+:");
		lblEIICp.setBackground(Color.WHITE);
		lblEIICp.setBounds(309, 91, 20, 14);
		panel.add(lblEIICp);

		JSpinner spinnerEIICm = new JSpinner();
		JSpinner spinnerEIIC = new JSpinner();
		JSpinner spinnerEIICp = new JSpinner();

		spinnerEIICp.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if ((double) spinnerEIICp.getValue()<(double) spinnerEIIC.getValue() & (double) spinnerEIICp.getValue()<(double) spinnerEIICm.getValue()){
					spinnerEIIC.setModel(new SpinnerNumberModel((double) spinnerEIICp.getValue(), 0.5, 1, 0.01));
					spinnerEIICm.setModel(new SpinnerNumberModel((double) spinnerEIICp.getValue(), 0.5, 1, 0.01));
				}
				if ((double) spinnerEIICp.getValue()<(double) spinnerEIIC.getValue()){
					spinnerEIIC.setModel(new SpinnerNumberModel((double) spinnerEIICp.getValue(), 0.5, 1, 0.01));
				}
			}
		});
		spinnerEIICp.setBounds(329, 88, 58, 25);
		spinnerEIICp.setModel(new SpinnerNumberModel(0.5, 0.5, 1, 0.01));
		panel.add(spinnerEIICp);

		JLabel lblEIIC = new JLabel("c:");
		lblEIIC.setBackground(Color.WHITE);
		lblEIIC.setBounds(220, 91, 20, 14);
		panel.add(lblEIIC);

		spinnerEIIC.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				spinnerEIICp.setModel(new SpinnerNumberModel((double) spinnerEIIC.getValue(), 0.5, 1, 0.01));
				if ((double) spinnerEIIC.getValue()<(double) spinnerEIICm.getValue()){
					spinnerEIICm.setModel(new SpinnerNumberModel((double) spinnerEIIC.getValue(), 0.5, 1, 0.01));
				}
			}
		});
		spinnerEIIC.setBounds(240, 88, 58, 25);
		spinnerEIIC.setModel(new SpinnerNumberModel(0.5, 0.5, 1, 0.01));
		panel.add(spinnerEIIC);

		JLabel lblEIICm = new JLabel("c-:");
		lblEIICm.setBackground(Color.WHITE);
		lblEIICm.setBounds(126, 91, 20, 14);
		panel.add(lblEIICm);

		spinnerEIICm.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				spinnerEIIC.setModel(new SpinnerNumberModel((double) spinnerEIICm.getValue(), 0.5, 1, 0.01));
				spinnerEIICp.setModel(new SpinnerNumberModel((double) spinnerEIICm.getValue(), 0.5, 1, 0.01));
			}
		});
		spinnerEIICm.setBounds(146, 88, 58, 25);
		spinnerEIICm.setModel(new SpinnerNumberModel(0.5, 0.5, 1, 0.01));
		panel.add(spinnerEIICm);

		JLabel lblEIId1 = new JLabel("d-:");
		lblEIId1.setBackground(Color.WHITE);
		lblEIId1.setBounds(126, 118, 20, 14);
		panel.add(lblEIId1);

		JSpinner spinnerEIId1 = new JSpinner();
		JSpinner spinnerEIId2 = new JSpinner();

		spinnerEIId1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				spinnerEIId2.setModel(new SpinnerNumberModel((double) spinnerEIId1.getValue(), 0, 1, 0.01));
			}
		});
		spinnerEIId1.setBounds(146, 115, 58, 25);
		spinnerEIId1.setModel(new SpinnerNumberModel(0, 0, 1, 0.01));
		panel.add(spinnerEIId1);

		JLabel lblEIId2 = new JLabel("d+:");
		lblEIId2.setBackground(Color.WHITE);
		lblEIId2.setBounds(219, 118, 20, 14);
		panel.add(lblEIId2);

		spinnerEIId2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if ((double) spinnerEIId2.getValue()<(double) spinnerEIId1.getValue()){
					spinnerEIId1.setModel(new SpinnerNumberModel((double) spinnerEIId2.getValue(), 0, 1, 0.01));
				}
			}
		});
		spinnerEIId2.setBounds(240, 115, 58, 25);
		spinnerEIId2.setModel(new SpinnerNumberModel(0, 0, 1, 0.01));
		panel.add(spinnerEIId2);

		JLabel lblETriB = new JLabel("Classes:");
		lblETriB.setBackground(Color.WHITE);
		lblETriB.setBounds(126, 198, 55, 14);
		panel.add(lblETriB);

		JSpinner spinnerETriB = new JSpinner();
		spinnerETriB.setBounds(186, 196, 58, 25);
		spinnerETriB.setModel(new SpinnerNumberModel(2, 2, 100, 1));
		panel.add(spinnerETriB);

		JLabel lblEtriMeB = new JLabel("Classes:");
		lblEtriMeB.setBackground(Color.WHITE);
		lblEtriMeB.setBounds(126, 227, 55, 14);
		panel.add(lblEtriMeB);

		JSpinner spinnerETriMeB = new JSpinner();
		spinnerETriMeB.setBounds(186, 225, 58, 25);
		spinnerETriMeB.setModel(new SpinnerNumberModel(2, 2, 100, 1));
		panel.add(spinnerETriMeB);

		JLabel lblETriMeE = new JLabel("Evaluators:");
		lblETriMeE.setBackground(Color.WHITE);
		lblETriMeE.setBounds(254, 227, 58, 14);
		panel.add(lblETriMeE);

		JSpinner spinnerETriMeE = new JSpinner();
		spinnerETriMeE.setBounds(318, 223, 58, 25);
		spinnerETriMeE.setModel(new SpinnerNumberModel(2, 2, 100, 1));
		panel.add(spinnerETriMeE);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(7, 276, 531, 36);
		panel_1.setBackground(new Color(255, 255, 102));
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_1.setLayout(null);

		JLabel lblA = new JLabel("Alternatives:");
		lblA.setBounds(10, 12, 75, 14);
		lblA.setBackground(Color.WHITE);
		panel_1.add(lblA);

		JSpinner spinnerA = new JSpinner();
		spinnerA.setBounds(83, 9, 58, 25);
		spinnerA.setModel(new SpinnerNumberModel(2, 2, 1000, 1));
		panel_1.add(spinnerA);

		JLabel lblC = new JLabel("Criteria:");
		lblC.setBackground(Color.WHITE);
		lblC.setBounds(153, 12, 51, 14);
		panel_1.add(lblC);

		JSpinner spinnerC = new JSpinner();
		spinnerC.setBounds(203, 9, 58, 25);
		spinnerC.setModel(new SpinnerNumberModel(2, 2, 1000, 1));
		panel_1.add(spinnerC);

		JLabel lblETri_Lambda = new JLabel("Lambda:");
		lblETri_Lambda.setBackground(Color.WHITE);
		lblETri_Lambda.setBounds(397, 198, 41, 14);
		panel.add(lblETri_Lambda);

		JSpinner spinnerETri_Lambda = new JSpinner();
		spinnerETri_Lambda.setBounds(450, 196, 58, 25);
		spinnerETri_Lambda.setModel(new SpinnerNumberModel(0.5, 0.5, 1, 0.01));
		panel.add(spinnerETri_Lambda);

		JLabel lblETriMe_Lambda = new JLabel("Lambda:");
		lblETriMe_Lambda.setBackground(Color.WHITE);
		lblETriMe_Lambda.setBounds(397, 227, 41, 14);
		panel.add(lblETriMe_Lambda);

		JSpinner spinnerETriMe_Lambda = new JSpinner();
		spinnerETriMe_Lambda.setBounds(450, 223, 58, 25);
		spinnerETriMe_Lambda.setModel(new SpinnerNumberModel(0.5, 0.5, 1, 0.01));
		panel.add(spinnerETriMe_Lambda);
		
		JLabel lblEI_sCycles = new JLabel("Cycles:");
		lblEI_sCycles.setBackground(Color.WHITE);
		lblEI_sCycles.setBounds(397, 37, 41, 14);
		panel.add(lblEI_sCycles);
		
		JSpinner spinnerEI_sCycles = new JSpinner();
		spinnerEI_sCycles.setBounds(450, 34, 58, 25);
		spinnerEI_sCycles.setModel(new SpinnerNumberModel(30, 30, 9000, 1));
		panel.add(spinnerEI_sCycles);
		
		JLabel lblEIICycles = new JLabel("Cycles:");
		lblEIICycles.setBackground(Color.WHITE);
		lblEIICycles.setBounds(397, 91, 41, 14);
		panel.add(lblEIICycles);
		
		JSpinner spinnerEIICycles = new JSpinner();
		spinnerEIICycles.setBounds(450, 88, 58, 25);
		spinnerEIICycles.setModel(new SpinnerNumberModel(30, 30, 9000, 1));
		panel.add(spinnerEIICycles);

		JButton buttonSolve = new JButton("Solve");

		JButton buttonSave = new JButton("Save");

		buttonSave.setBounds(441, 8, 75, 23);
		panel_1.add(buttonSave);

		JButton btnBuildMatrix = new JButton("Matrix");
		btnBuildMatrix.setBounds(271, 8, 75, 23);
		panel_1.add(btnBuildMatrix);
		btnBuildMatrix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (electre == 1 || electre == 4){
					
					dialog.setVisible(true);
					buttonSolve.setEnabled(true);

					int p  = (Integer) spinnerC.getValue();
					int q  = (Integer) spinnerA.getValue();
					
					model.setColumnCount(p + 1);
					model.setRowCount(q + 1);
					
					for (int j = 1; j < table.getColumnCount(); j++){
						table.getColumnModel().getColumn(j).setHeaderValue("");
						table.getTableHeader().repaint();
					}
					for (int j = 1; j < p + 1; j++){
						table.getColumnModel().getColumn(j).setHeaderValue("g" + j);
						table.getTableHeader().repaint();
					}
					for (int i = 0; i < table.getRowCount(); i++){		
						for (int j = 0; j < table.getColumnCount(); j++){
							table.getModel().setValueAt("", i, j);
							if (table.getValueAt(i, 0) == ""){
								break;
							}
						}
					}
					table.getModel().setValueAt("W", 0, 0);
					for (int i = 1; i < q + 1; i++){
						table.getModel().setValueAt("a" + i, i, 0);		
					}	
					for (int i = 0; i < table.getRowCount(); i++){
						for (int j = 0; j < table.getColumnCount() - 1; j++){
							table.getModel().setValueAt("", i, j + 1);		
						}
					}	
					table.repaint();
					dialog.setVisible(false);
				}

				if (electre == 2 || electre == 5){
					
					dialog.setVisible(true);
					buttonSolve.setEnabled(true);

					int p  = (Integer) spinnerC.getValue();
					int q  = (Integer) spinnerA.getValue();
					
					model.setColumnCount(p + 1);
					model.setRowCount(q + 4);
					
					for (int j = 1; j < table.getColumnCount(); j++){
						table.getColumnModel().getColumn(j).setHeaderValue("");
						table.getTableHeader().repaint();
					}
					for (int j = 1; j < p + 1; j++){
						table.getColumnModel().getColumn(j).setHeaderValue("g" + j);
						table.getTableHeader().repaint();
					}
					for (int i = 0; i < table.getRowCount(); i++){		
						for (int j = 0; j < table.getColumnCount(); j++){
							table.getModel().setValueAt("", i, j);
							if (table.getValueAt(i, 0) == ""){
								break;
							}
						}
					}
					table.getModel().setValueAt("Q", 0, 0);
					table.getModel().setValueAt("P", 1, 0);
					table.getModel().setValueAt("V", 2, 0);
					table.getModel().setValueAt("W", 3, 0);
					for (int i = 4; i < q + 4; i++){
						table.getModel().setValueAt("a" + (i - 3), i, 0);		
					}
					for (int i = 0; i < table.getRowCount(); i++){
						for (int j = 0; j < table.getColumnCount() - 1; j++){
							table.getModel().setValueAt("", i, j + 1);		
						}
					}	
					table.repaint();
					dialog.setVisible(false);
				}

				if (electre == 3){

					dialog.setVisible(true);
					buttonSolve.setEnabled(true);

					int p  = (Integer) spinnerC.getValue();
					int q  = (Integer) spinnerA.getValue();
					
					model.setColumnCount(p + 1);
					model.setRowCount(q + 2);
					
					for (int j = 1; j < table.getColumnCount(); j++){
						table.getColumnModel().getColumn(j).setHeaderValue("");
						table.getTableHeader().repaint();
					}
					for (int j = 1; j < p + 1; j++){
						table.getColumnModel().getColumn(j).setHeaderValue("g" + j);
						table.getTableHeader().repaint();
					}
					for (int i = 0; i < table.getRowCount(); i++){		
						for (int j = 0; j < table.getColumnCount(); j++){
							table.getModel().setValueAt("", i, j);
							if (table.getValueAt(i, 0) == ""){
								break;
							}
						}
					}
					table.getModel().setValueAt("V", 0, 0);
					table.getModel().setValueAt("W", 1, 0);
					for (int i = 2; i < q + 2; i++){
						table.getModel().setValueAt("a" + (i - 1), i, 0);		
					}
					for (int i = 0; i < table.getRowCount(); i++){
						for (int j = 0; j < table.getColumnCount() - 1; j++){
							table.getModel().setValueAt("", i, j + 1);		
						}
					}
					table.repaint();
					dialog.setVisible(false);
				}

				if (electre == 6){

					dialog.setVisible(true);
					buttonSolve.setEnabled(true);

					int p  = (Integer) spinnerC.getValue();
					int q  = (Integer) spinnerA.getValue();
					
					model.setColumnCount(p + 1);
					model.setRowCount(q + 3);
					
					for (int j = 1; j < table.getColumnCount(); j++){
						table.getColumnModel().getColumn(j).setHeaderValue("");
						table.getTableHeader().repaint();
					}
					for (int j = 1; j < p + 1; j++){
						table.getColumnModel().getColumn(j).setHeaderValue("g" + j);
						table.getTableHeader().repaint();
					}
					for (int i = 0; i < table.getRowCount(); i++){		
						for (int j = 0; j < table.getColumnCount(); j++){
							table.getModel().setValueAt("", i, j);
							if (table.getValueAt(i, 0) == ""){
								break;
							}
						}
					}
					table.getModel().setValueAt("Q", 0, 0);
					table.getModel().setValueAt("P", 1, 0);
					table.getModel().setValueAt("V", 2, 0);
					for (int i = 3; i < q + 3; i++){
						table.getModel().setValueAt("a" + (i - 2), i, 0);		
					}
					for (int i = 0; i < table.getRowCount(); i++){
						for (int j = 0; j < table.getColumnCount() - 1; j++){
							table.getModel().setValueAt("", i, j + 1);		
						}
					}
					table.repaint();
					dialog.setVisible(false);
				}

				if (electre == 7){

					dialog.setVisible(true);
					buttonSolve.setEnabled(true);

					int p  = (Integer) spinnerC.getValue();
					int q  = (Integer) spinnerA.getValue();
					int b  = (Integer) spinnerETriB.getValue() - 1;
					
					model.setColumnCount(p + 1);
					model.setRowCount(q + 4 + b);
					
					for (int j = 1; j < table.getColumnCount(); j++){
						table.getColumnModel().getColumn(j).setHeaderValue("");
						table.getTableHeader().repaint();
					}
					for (int j = 1; j < p + 1; j++){
						table.getColumnModel().getColumn(j).setHeaderValue("g" + j);
						table.getTableHeader().repaint();
					}
					for (int i = 0; i < table.getRowCount(); i++){		
						for (int j = 0; j < table.getColumnCount(); j++){
							table.getModel().setValueAt("", i, j);
							if (table.getValueAt(i, 0) == ""){
								break;
							}
						}
					}
					
					for (int i = 0; i < b; i++){
						table.getModel().setValueAt("b" + (b - i), i, 0);		
					}
					table.getModel().setValueAt("Q", 0 + b, 0);
					table.getModel().setValueAt("P", 1 + b, 0);
					table.getModel().setValueAt("V", 2 + b, 0);
					table.getModel().setValueAt("W", 3 + b, 0);
					for (int i = 4 + b; i < q + 4 + b; i++){
						table.getModel().setValueAt("a" + (i - 3 - b), i, 0);		
					}
					for (int i = 0; i < table.getRowCount(); i++){
						for (int j = 0; j < table.getColumnCount() - 1; j++){
							table.getModel().setValueAt("", i, j + 1);		
						}
					}
					table.repaint();
					dialog.setVisible(false);
				}

				if (electre == 8){

					dialog.setVisible(true);
					buttonSolve.setEnabled(true);

					int p  = (Integer) spinnerC.getValue();
					int q  = (Integer) spinnerA.getValue();
					int b  = (Integer) spinnerETriMeB.getValue() - 1;
					int e  = (Integer) spinnerETriMeE.getValue();
					int f = 1;
					
					model.setColumnCount(p*e + 1);
					model.setRowCount(q + 4 + b);
					
					for (int j = 1; j < table.getColumnCount(); j++){
						table.getColumnModel().getColumn(j).setHeaderValue("");
						table.getTableHeader().repaint();
					}
					for (int k = 1; k <= e; k++){
						for (int j = 1; j < p + 1; j++){
							table.getColumnModel().getColumn(f).setHeaderValue("EV" + k + "( g" + j + " )");
							table.getTableHeader().repaint();
							f = f + 1;
						}
					}
					for (int i = 0; i < table.getRowCount(); i++){		
						for (int j = 0; j < table.getColumnCount(); j++){
							table.getModel().setValueAt("", i, j);
							if (table.getValueAt(i, 0) == ""){
								break;
							}
						}
					}
					
					for (int i = 0; i < b; i++){
						table.getModel().setValueAt("b" + (b - i), i, 0);		
					}
					table.getModel().setValueAt("Q", 0 + b, 0);
					table.getModel().setValueAt("P", 1 + b, 0);
					table.getModel().setValueAt("V", 2 + b, 0);
					table.getModel().setValueAt("W", 3 + b, 0);
					for (int i = 4 + b; i < q + 4 + b; i++){
						table.getModel().setValueAt("a" + (i - 3 - b), i, 0);		
					}
					for (int i = 0; i < table.getRowCount(); i++){
						for (int j = 0; j < table.getColumnCount() - 1; j++){
							table.getModel().setValueAt("", i, j + 1);		
						}
					}
					table.repaint();
					dialog.setVisible(false);
				}

			}
		});

		lblEIQ.setEnabled(false);
		spinnerEIQ.setEnabled(false);
		lblEIP.setEnabled(false);
		spinnerEIP.setEnabled(false);
		lblEIICm.setEnabled(false);
		spinnerEIICm.setEnabled(false);
		lblEIIC.setEnabled(false);
		spinnerEIIC.setEnabled(false);
		lblEIICp.setEnabled(false);
		spinnerEIICp.setEnabled(false);
		lblEIId1.setEnabled(false);
		spinnerEIId1.setEnabled(false);
		lblEIId2.setEnabled(false);
		spinnerEIId2.setEnabled(false);
		lblETriB.setEnabled(false);
		spinnerETriB.setEnabled(false);
		lblEtriMeB.setEnabled(false);
		spinnerETriMeB.setEnabled(false);
		lblETriMeE.setEnabled(false);
		spinnerETriMeE.setEnabled(false);
		lblEI_sLambda.setEnabled(false);
		spinnerEI_sLambda.setEnabled(false);
		lblEI_vQ.setEnabled(false);
		spinnerEI_vQ.setEnabled(false);
		lblEI_vP.setEnabled(false);
		spinnerEI_vP.setEnabled(false);
		lblA.setEnabled(false);
		lblC.setEnabled(false);
		spinnerA.setEnabled(false);
		spinnerC.setEnabled(false);
		btnBuildMatrix.setEnabled(false);
		lblETri_Lambda.setEnabled(false);
		spinnerETri_Lambda.setEnabled(false);
		lblETriMe_Lambda.setEnabled(false);
		spinnerETriMe_Lambda.setEnabled(false);
		lblEI_sCycles.setEnabled(false);
		spinnerEI_sCycles.setEnabled(false);
		lblEIICycles.setEnabled(false);
		spinnerEIICycles.setEnabled(false);
		buttonSave.setEnabled(false);

		buttonSolve.setEnabled(false);
		buttonSolve.setBounds(356, 8, 75, 23);
		panel_1.add(buttonSolve);

		buttonSolve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				btnBuildMatrix.setEnabled(false);

				if (electre == 1){

					dialog.setVisible(true);
					buttonSave.setEnabled(true);

					EI_Q = (Double) spinnerEIQ.getValue();
					EI_P = (Double) spinnerEIP.getValue();

					int p  = (Integer) spinnerC.getValue();
					int q  = (Integer) spinnerA.getValue();

					Object[][] getData = new Object[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							getData[i - 1][j - 1] = table.getValueAt(i, j);	
							if (getData[i - 1][j - 1] == null) {
								getData[i - 1][j - 1] = "0";
							} 
						}
					}
					String[][] getString = new String[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							getString[i - 1][j - 1] = (String)getData[i - 1][j - 1];
							getString[i - 1][j - 1] = getString[i - 1][j - 1].replace(",", ".");	

						}
					}
					double[][] getValue = new double[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							try {
								Double.parseDouble(getString[i - 1][j - 1]);
							} catch (NumberFormatException e1) {
								getString[i - 1][j - 1] = "0";
							}
							getValue[i - 1][j - 1] = Double.parseDouble(getString[i - 1][j - 1]);		
						}
					}
					Object[] getDataW = new Object[p];
					for (int j = 1; j < p + 1; j++){
						getDataW[j - 1] = table.getValueAt(0, j);	
						if (getDataW[j - 1] == null) {
							getDataW[j - 1] = "0";
						}
					}
					String[] getStringW = new String[p];
					for (int j = 1; j < p + 1; j++){
						getStringW[j - 1] = (String)getDataW[j - 1];
						getStringW[j - 1] = getStringW[j - 1].replace(",", ".");
					}		
					double[] getValueW = new double[p];
					for (int j = 1; j < p + 1; j++){
						try {
							Double.parseDouble(getStringW[j - 1]);
						} catch (NumberFormatException e1) {
							getStringW[j - 1] = "0";
						}
						getValueW[j - 1] = Double.parseDouble(getStringW[j - 1]);		
					}
					String[][] arraySolutionEI = ELECTRE_I.e_I_Algorithm(getValue, getValueW);
					
					for (int i = 0; i < table_1.getRowCount(); i++){
						for (int j = 0; j < table_1.getColumnCount() - 1; j++){
							table_1.getModel().setValueAt("", i, j);		
						}
					}
					
					model_1.setColumnCount(arraySolutionEI[0].length);
					model_1.setRowCount(arraySolutionEI.length  + 8);
					
					for (int i = 0; i < arraySolutionEI.length; i++){
						for (int j = 0; j < arraySolutionEI[0].length; j++){
							table_1.getModel().setValueAt(arraySolutionEI[i][j], i + 2, j);		
						}
					}
					table_1.getModel().setValueAt("## ELECTRE I ##", 0, 0);
					table_1.getModel().setValueAt("## github.com/Valdecy ##",  arraySolutionEI.length + 2, 0);
					table_1.getModel().setValueAt("Valdecy Pereira", arraySolutionEI.length + 4, 0);
					table_1.getModel().setValueAt("Helder Gomes Costa", arraySolutionEI.length + 5, 0);
					table_1.getModel().setValueAt("Livia D. de O. Nepomuceno", arraySolutionEI.length + 6, 0);

					dialog.setVisible(false);

				}//	EI

				if (electre == 2){

					dialog.setVisible(true);
					buttonSave.setEnabled(true);

					EI_s_Lambda   = (Double) spinnerEI_sLambda.getValue();
					MaximumCycles = (Integer) spinnerEI_sCycles.getValue();

					int p  = (Integer) spinnerC.getValue();
					int q  = (Integer) spinnerA.getValue();

					Object[][] getData = new Object[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							getData[i - 1][j - 1] = table.getValueAt(i + 3, j);	
							if (getData[i - 1][j - 1] == null) {
								getData[i - 1][j - 1] = "0";
							} 
						}
					}
					String[][] getString = new String[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							getString[i - 1][j - 1] = (String)getData[i - 1][j - 1];
							getString[i - 1][j - 1] = getString[i - 1][j - 1].replace(",", ".");	

						}
					}
					double[][] getValue = new double[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							try {
								Double.parseDouble(getString[i - 1][j - 1]);
							} catch (NumberFormatException e1) {
								getString[i - 1][j - 1] = "0";
							}
							getValue[i - 1][j - 1] = Double.parseDouble(getString[i - 1][j - 1]);		
						}
					}

					Object[] getDataW = new Object[p];
					for (int j = 1; j < p + 1; j++){
						getDataW[j - 1] = table.getValueAt(3, j);	
						if (getDataW[j - 1] == null) {
							getDataW[j - 1] = "0";
						}
					}
					String[] getStringW = new String[p];
					for (int j = 1; j < p + 1; j++){
						getStringW[j - 1] = (String)getDataW[j - 1];
						getStringW[j - 1] = getStringW[j - 1].replace(",", ".");
					}		
					double[] getValueW = new double[p];
					for (int j = 1; j < p + 1; j++){
						try {
							Double.parseDouble(getStringW[j - 1]);
						} catch (NumberFormatException e1) {
							getStringW[j - 1] = "0";
						}
						getValueW[j - 1] = Double.parseDouble(getStringW[j - 1]);		
					}

					Object[] getDataV = new Object[p];
					for (int j = 1; j < p + 1; j++){
						getDataV[j - 1] = table.getValueAt(2, j);	
						if (getDataV[j - 1] == null) {
							getDataV[j - 1] = "0";
						}
					}
					String[] getStringV = new String[p];
					for (int j = 1; j < p + 1; j++){
						getStringV[j - 1] = (String)getDataV[j - 1];
						getStringV[j - 1] = getStringV[j - 1].replace(",", ".");
					}		
					double[] getValueV = new double[p];
					for (int j = 1; j < p + 1; j++){
						try {
							Double.parseDouble(getStringV[j - 1]);
						} catch (NumberFormatException e1) {
							getStringV[j - 1] = "0";
						}
						getValueV[j - 1] = Double.parseDouble(getStringV[j - 1]);		
					}

					Object[] getDataP = new Object[p];
					for (int j = 1; j < p + 1; j++){
						getDataP[j - 1] = table.getValueAt(1, j);	
						if (getDataP[j - 1] == null) {
							getDataP[j - 1] = "0";
						}
					}
					String[] getStringP = new String[p];
					for (int j = 1; j < p + 1; j++){
						getStringP[j - 1] = (String)getDataP[j - 1];
						getStringP[j - 1] = getStringP[j - 1].replace(",", ".");
					}		
					double[] getValueP = new double[p];
					for (int j = 1; j < p + 1; j++){
						try {
							Double.parseDouble(getStringP[j - 1]);
						} catch (NumberFormatException e1) {
							getStringP[j - 1] = "0";
						}
						getValueP[j - 1] = Double.parseDouble(getStringP[j - 1]);		
					}
					Object[] getDataQ = new Object[p];
					for (int j = 1; j < p + 1; j++){
						getDataQ[j - 1] = table.getValueAt(0, j);	
						if (getDataQ[j - 1] == null) {
							getDataQ[j - 1] = "0";
						}
					}
					String[] getStringQ = new String[p];
					for (int j = 1; j < p + 1; j++){
						getStringQ[j - 1] = (String)getDataQ[j - 1];
						getStringQ[j - 1] = getStringQ[j - 1].replace(",", ".");
					}		
					double[] getValueQ = new double[p];
					for (int j = 1; j < p + 1; j++){
						try {
							Double.parseDouble(getStringQ[j - 1]);
						} catch (NumberFormatException e1) {
							getStringQ[j - 1] = "0";
						}
						getValueQ[j - 1] = Double.parseDouble(getStringQ[j - 1]);		
					}
					String [][] arraySolutionEI_s = ELECTRE_I_s.e_I_s_Algorithm(getValue, getValueW, getValueP, getValueQ, getValueV);

					for (int i = 0; i < table_1.getRowCount(); i++){
						for (int j = 0; j < table_1.getColumnCount() - 1; j++){
							table_1.getModel().setValueAt("", i, j);		
						}
					}
					
					model_1.setColumnCount(arraySolutionEI_s[0].length);
					model_1.setRowCount(arraySolutionEI_s.length  + 8);
					
					for (int i = 0; i < arraySolutionEI_s.length; i++){
						for (int j = 0; j < arraySolutionEI_s[0].length; j++){
							table_1.getModel().setValueAt(arraySolutionEI_s[i][j], i + 2, j);		
						}
					}
					table_1.getModel().setValueAt("## ELECTRE I_s ##", 0, 0);
					table_1.getModel().setValueAt("## github.com/Valdecy ##",  arraySolutionEI_s.length + 2, 0);
					table_1.getModel().setValueAt("Valdecy Pereira", arraySolutionEI_s.length + 4, 0);
					table_1.getModel().setValueAt("Helder Gomes Costa", arraySolutionEI_s.length + 5, 0);
					table_1.getModel().setValueAt("Livia D. de O. Nepomuceno", arraySolutionEI_s.length + 6, 0);

					dialog.setVisible(false);

				}//	EI_s

				if (electre == 3){

					dialog.setVisible(true);

					buttonSave.setEnabled(true);

					EI_v_Q = (Double) spinnerEI_vQ.getValue();
					EI_v_P = (Double) spinnerEI_vP.getValue();

					int p  = (Integer) spinnerC.getValue();
					int q  = (Integer) spinnerA.getValue();

					Object[][] getData = new Object[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							getData[i - 1][j - 1] = table.getValueAt(i + 1, j);	
							if (getData[i - 1][j - 1] == null) {
								getData[i - 1][j - 1] = "0";
							} 
						}
					}
					String[][] getString = new String[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							getString[i - 1][j - 1] = (String)getData[i - 1][j - 1];
							getString[i - 1][j - 1] = getString[i - 1][j - 1].replace(",", ".");	

						}
					}
					double[][] getValue = new double[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							try {
								Double.parseDouble(getString[i - 1][j - 1]);
							} catch (NumberFormatException e1) {
								getString[i - 1][j - 1] = "0";
							}
							getValue[i - 1][j - 1] = Double.parseDouble(getString[i - 1][j - 1]);		
						}
					}

					Object[] getDataW = new Object[p];
					for (int j = 1; j < p + 1; j++){
						getDataW[j - 1] = table.getValueAt(1, j);	
						if (getDataW[j - 1] == null) {
							getDataW[j - 1] = "0";
						}
					}
					String[] getStringW = new String[p];
					for (int j = 1; j < p + 1; j++){
						getStringW[j - 1] = (String)getDataW[j - 1];
						getStringW[j - 1] = getStringW[j - 1].replace(",", ".");
					}		
					double[] getValueW = new double[p];
					for (int j = 1; j < p + 1; j++){
						try {
							Double.parseDouble(getStringW[j - 1]);
						} catch (NumberFormatException e1) {
							getStringW[j - 1] = "0";
						}
						getValueW[j - 1] = Double.parseDouble(getStringW[j - 1]);		
					}

					Object[] getDataV = new Object[p];
					for (int j = 1; j < p + 1; j++){
						getDataV[j - 1] = table.getValueAt(0, j);	
						if (getDataV[j - 1] == null) {
							getDataV[j - 1] = "0";
						}
					}
					String[] getStringV = new String[p];
					for (int j = 1; j < p + 1; j++){
						getStringV[j - 1] = (String)getDataV[j - 1];
						getStringV[j - 1] = getStringV[j - 1].replace(",", ".");
					}		
					double[] getValueV = new double[p];
					for (int j = 1; j < p + 1; j++){
						try {
							Double.parseDouble(getStringV[j - 1]);
						} catch (NumberFormatException e1) {
							getStringV[j - 1] = "0";
						}
						getValueV[j - 1] = Double.parseDouble(getStringV[j - 1]);		
					}

					String [][] arraySolutionEI_v = ELECTRE_I_v.e_I_v_Algorithm(getValue, getValueV, getValueW);

					for (int i = 0; i < table_1.getRowCount(); i++){
						for (int j = 0; j < table_1.getColumnCount() - 1; j++){
							table_1.getModel().setValueAt("", i, j);		
						}
					}
					
					model_1.setColumnCount(arraySolutionEI_v[0].length);
					model_1.setRowCount(arraySolutionEI_v.length  + 8);
					
					for (int i = 0; i < arraySolutionEI_v.length; i++){
						for (int j = 0; j < arraySolutionEI_v[0].length; j++){
							table_1.getModel().setValueAt(arraySolutionEI_v[i][j], i + 2, j);		
						}
					}
					table_1.getModel().setValueAt("## ELECTRE I_v ##", 0, 0);
					table_1.getModel().setValueAt("## github.com/Valdecy ##",  arraySolutionEI_v.length + 2, 0);
					table_1.getModel().setValueAt("Valdecy Pereira", arraySolutionEI_v.length + 4, 0);
					table_1.getModel().setValueAt("Helder Gomes Costa", arraySolutionEI_v.length + 5, 0);
					table_1.getModel().setValueAt("Livia D. de O. Nepomuceno", arraySolutionEI_v.length + 6, 0);

					dialog.setVisible(false);

				}//	EI_v

				if (electre == 4){

					dialog.setVisible(true);
					buttonSave.setEnabled(true);

					EII_Cm        = (Double) spinnerEIICm.getValue();
					EII_C         = (Double) spinnerEIIC.getValue();
					EII_Cp        = (Double) spinnerEIICp.getValue();
					EII_d1        = (Double) spinnerEIId1.getValue();
					EII_d2        = (Double) spinnerEIId2.getValue();
					MaximumCycles = (Integer) spinnerEIICycles.getValue();

					int p  = (Integer) spinnerC.getValue();
					int q  = (Integer) spinnerA.getValue();

					Object[][] getData = new Object[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							getData[i - 1][j - 1] = table.getValueAt(i, j);	
							if (getData[i - 1][j - 1] == null) {
								getData[i - 1][j - 1] = "0";
							} 
						}
					}
					String[][] getString = new String[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							getString[i - 1][j - 1] = (String)getData[i - 1][j - 1];
							getString[i - 1][j - 1] = getString[i - 1][j - 1].replace(",", ".");	

						}
					}
					double[][] getValue = new double[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							try {
								Double.parseDouble(getString[i - 1][j - 1]);
							} catch (NumberFormatException e1) {
								getString[i - 1][j - 1] = "0";
							}
							getValue[i - 1][j - 1] = Double.parseDouble(getString[i - 1][j - 1]);		
						}
					}
					Object[] getDataW = new Object[p];
					for (int j = 1; j < p + 1; j++){
						getDataW[j - 1] = table.getValueAt(0, j);	
						if (getDataW[j - 1] == null) {
							getDataW[j - 1] = "0";
						}
					}
					String[] getStringW = new String[p];
					for (int j = 1; j < p + 1; j++){
						getStringW[j - 1] = (String)getDataW[j - 1];
						getStringW[j - 1] = getStringW[j - 1].replace(",", ".");
					}		
					double[] getValueW = new double[p];
					for (int j = 1; j < p + 1; j++){
						try {
							Double.parseDouble(getStringW[j - 1]);
						} catch (NumberFormatException e1) {
							getStringW[j - 1] = "0";
						}
						getValueW[j - 1] = Double.parseDouble(getStringW[j - 1]);		
					}
					String [][] arraySolutionEII = ELECTRE_II.rankFinal(getValue, getValueW);

					for (int i = 0; i < table_1.getRowCount(); i++){
						for (int j = 0; j < table_1.getColumnCount() - 1; j++){
							table_1.getModel().setValueAt("", i, j);		
						}
					}
					
					model_1.setColumnCount(arraySolutionEII[0].length);
					model_1.setRowCount(arraySolutionEII.length  + 8);
					
					for (int i = 0; i < arraySolutionEII.length; i++){
						for (int j = 0; j < arraySolutionEII[0].length; j++){
							table_1.getModel().setValueAt(arraySolutionEII[i][j], i + 2, j);		
						}
					}
					table_1.getModel().setValueAt("## ELECTRE II ##", 0, 0);
					table_1.getModel().setValueAt("## github.com/Valdecy ##",  arraySolutionEII.length + 2, 0);
					table_1.getModel().setValueAt("Valdecy Pereira", arraySolutionEII.length + 4, 0);
					table_1.getModel().setValueAt("Helder Gomes Costa", arraySolutionEII.length + 5, 0);
					table_1.getModel().setValueAt("Livia D. de O. Nepomuceno", arraySolutionEII.length + 6, 0);

					dialog.setVisible(false);

				}//	EII

				if (electre == 5){

					dialog.setVisible(true);

					buttonSave.setEnabled(true);

					int p  = (Integer) spinnerC.getValue();
					int q  = (Integer) spinnerA.getValue();

					Object[][] getData = new Object[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							getData[i - 1][j - 1] = table.getValueAt(i + 3, j);	
							if (getData[i - 1][j - 1] == null) {
								getData[i - 1][j - 1] = "0";
							} 
						}
					}
					String[][] getString = new String[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							getString[i - 1][j - 1] = (String)getData[i - 1][j - 1];
							getString[i - 1][j - 1] = getString[i - 1][j - 1].replace(",", ".");	

						}
					}
					double[][] getValue = new double[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							try {
								Double.parseDouble(getString[i - 1][j - 1]);
							} catch (NumberFormatException e1) {
								getString[i - 1][j - 1] = "0";
							}
							getValue[i - 1][j - 1] = Double.parseDouble(getString[i - 1][j - 1]);		
						}
					}

					Object[] getDataW = new Object[p];
					for (int j = 1; j < p + 1; j++){
						getDataW[j - 1] = table.getValueAt(3, j);	
						if (getDataW[j - 1] == null) {
							getDataW[j - 1] = "0";
						}
					}
					String[] getStringW = new String[p];
					for (int j = 1; j < p + 1; j++){
						getStringW[j - 1] = (String)getDataW[j - 1];
						getStringW[j - 1] = getStringW[j - 1].replace(",", ".");
					}		
					double[] getValueW = new double[p];
					for (int j = 1; j < p + 1; j++){
						try {
							Double.parseDouble(getStringW[j - 1]);
						} catch (NumberFormatException e1) {
							getStringW[j - 1] = "0";
						}
						getValueW[j - 1] = Double.parseDouble(getStringW[j - 1]);		
					}

					Object[] getDataV = new Object[p];
					for (int j = 1; j < p + 1; j++){
						getDataV[j - 1] = table.getValueAt(2, j);	
						if (getDataV[j - 1] == null) {
							getDataV[j - 1] = "0";
						}
					}
					String[] getStringV = new String[p];
					for (int j = 1; j < p + 1; j++){
						getStringV[j - 1] = (String)getDataV[j - 1];
						getStringV[j - 1] = getStringV[j - 1].replace(",", ".");
					}		
					double[] getValueV = new double[p];
					for (int j = 1; j < p + 1; j++){
						try {
							Double.parseDouble(getStringV[j - 1]);
						} catch (NumberFormatException e1) {
							getStringV[j - 1] = "0";
						}
						getValueV[j - 1] = Double.parseDouble(getStringV[j - 1]);		
					}

					Object[] getDataP = new Object[p];
					for (int j = 1; j < p + 1; j++){
						getDataP[j - 1] = table.getValueAt(1, j);	
						if (getDataP[j - 1] == null) {
							getDataP[j - 1] = "0";
						}
					}
					String[] getStringP = new String[p];
					for (int j = 1; j < p + 1; j++){
						getStringP[j - 1] = (String)getDataP[j - 1];
						getStringP[j - 1] = getStringP[j - 1].replace(",", ".");
					}		
					double[] getValueP = new double[p];
					for (int j = 1; j < p + 1; j++){
						try {
							Double.parseDouble(getStringP[j - 1]);
						} catch (NumberFormatException e1) {
							getStringP[j - 1] = "0";
						}
						getValueP[j - 1] = Double.parseDouble(getStringP[j - 1]);		
					}
					Object[] getDataQ = new Object[p];
					for (int j = 1; j < p + 1; j++){
						getDataQ[j - 1] = table.getValueAt(0, j);	
						if (getDataQ[j - 1] == null) {
							getDataQ[j - 1] = "0";
						}
					}
					String[] getStringQ = new String[p];
					for (int j = 1; j < p + 1; j++){
						getStringQ[j - 1] = (String)getDataQ[j - 1];
						getStringQ[j - 1] = getStringQ[j - 1].replace(",", ".");
					}		
					double[] getValueQ = new double[p];
					for (int j = 1; j < p + 1; j++){
						try {
							Double.parseDouble(getStringQ[j - 1]);
						} catch (NumberFormatException e1) {
							getStringQ[j - 1] = "0";
						}
						getValueQ[j - 1] = Double.parseDouble(getStringQ[j - 1]);		
					}
					String [][] arraySolutionEIII = ELECTRE_III.rankFinal(getValue, getValueW, getValueP, getValueQ, getValueV);

					for (int i = 0; i < table_1.getRowCount(); i++){
						for (int j = 0; j < table_1.getColumnCount() - 1; j++){
							table_1.getModel().setValueAt("", i, j);		
						}
					}
					
					model_1.setColumnCount(arraySolutionEIII[0].length);
					model_1.setRowCount(arraySolutionEIII.length  + 8);
					
					for (int i = 0; i < arraySolutionEIII.length; i++){
						for (int j = 0; j < arraySolutionEIII[0].length; j++){
							table_1.getModel().setValueAt(arraySolutionEIII[i][j], i + 2, j);		
						}
					}
					table_1.getModel().setValueAt("## ELECTRE III ##", 0, 0);
					table_1.getModel().setValueAt("## github.com/Valdecy ##",  arraySolutionEIII.length + 2, 0);
					table_1.getModel().setValueAt("Valdecy Pereira", arraySolutionEIII.length + 4, 0);
					table_1.getModel().setValueAt("Helder Gomes Costa", arraySolutionEIII.length + 5, 0);
					table_1.getModel().setValueAt("Livia D. de O. Nepomuceno", arraySolutionEIII.length + 6, 0);

					dialog.setVisible(false);

				}//	EIII

				if (electre == 6){

					dialog.setVisible(true);

					buttonSave.setEnabled(true);

					int p  = (Integer) spinnerC.getValue();
					int q  = (Integer) spinnerA.getValue();

					Object[][] getData = new Object[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							getData[i - 1][j - 1] = table.getValueAt(i + 2, j);	
							if (getData[i - 1][j - 1] == null) {
								getData[i - 1][j - 1] = "0";
							} 
						}
					}
					String[][] getString = new String[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							getString[i - 1][j - 1] = (String)getData[i - 1][j - 1];
							getString[i - 1][j - 1] = getString[i - 1][j - 1].replace(",", ".");	

						}
					}
					double[][] getValue = new double[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							try {
								Double.parseDouble(getString[i - 1][j - 1]);
							} catch (NumberFormatException e1) {
								getString[i - 1][j - 1] = "0";
							}
							getValue[i - 1][j - 1] = Double.parseDouble(getString[i - 1][j - 1]);		
						}
					}

					Object[] getDataV = new Object[p];

					for (int j = 1; j < p + 1; j++){
						getDataV[j - 1] = table.getValueAt(2, j);	
						if (getDataV[j - 1] == null) {
							getDataV[j - 1] = "0";
						}
					}
					String[] getStringV = new String[p];
					for (int j = 1; j < p + 1; j++){
						getStringV[j - 1] = (String)getDataV[j - 1];
						getStringV[j - 1] = getStringV[j - 1].replace(",", ".");
					}		
					double[] getValueV = new double[p];
					for (int j = 1; j < p + 1; j++){
						try {
							Double.parseDouble(getStringV[j - 1]);
						} catch (NumberFormatException e1) {
							getStringV[j - 1] = "0";
						}
						getValueV[j - 1] = Double.parseDouble(getStringV[j - 1]);		
					}

					Object[] getDataP = new Object[p];
					for (int j = 1; j < p + 1; j++){
						getDataP[j - 1] = table.getValueAt(1, j);	
						if (getDataP[j - 1] == null) {
							getDataP[j - 1] = "0";
						}
					}
					String[] getStringP = new String[p];
					for (int j = 1; j < p + 1; j++){
						getStringP[j - 1] = (String)getDataP[j - 1];
						getStringP[j - 1] = getStringP[j - 1].replace(",", ".");
					}		
					double[] getValueP = new double[p];
					for (int j = 1; j < p + 1; j++){
						try {
							Double.parseDouble(getStringP[j - 1]);
						} catch (NumberFormatException e1) {
							getStringP[j - 1] = "0";
						}
						getValueP[j - 1] = Double.parseDouble(getStringP[j - 1]);		
					}
					Object[] getDataQ = new Object[p];
					for (int j = 1; j < p + 1; j++){
						getDataQ[j - 1] = table.getValueAt(0, j);	
						if (getDataQ[j - 1] == null) {
							getDataQ[j - 1] = "0";
						}
					}
					String[] getStringQ = new String[p];
					for (int j = 1; j < p + 1; j++){
						getStringQ[j - 1] = (String)getDataQ[j - 1];
						getStringQ[j - 1] = getStringQ[j - 1].replace(",", ".");
					}		
					double[] getValueQ = new double[p];
					for (int j = 1; j < p + 1; j++){
						try {
							Double.parseDouble(getStringQ[j - 1]);
						} catch (NumberFormatException e1) {
							getStringQ[j - 1] = "0";
						}
						getValueQ[j - 1] = Double.parseDouble(getStringQ[j - 1]);		
					}
					String [][] arraySolutionEIV = ELECTRE_IV.rankFinal(getValue, getValueP, getValueQ, getValueV);

					for (int i = 0; i < table_1.getRowCount(); i++){
						for (int j = 0; j < table_1.getColumnCount() - 1; j++){
							table_1.getModel().setValueAt("", i, j);		
						}
					}
					
					model_1.setColumnCount(arraySolutionEIV[0].length);
					model_1.setRowCount(arraySolutionEIV.length  + 8);
					
					for (int i = 0; i < arraySolutionEIV.length; i++){
						for (int j = 0; j < arraySolutionEIV[0].length; j++){
							table_1.getModel().setValueAt(arraySolutionEIV[i][j], i + 2, j);		
						}
					}
					table_1.getModel().setValueAt("## ELECTRE IV ##", 0, 0);
					table_1.getModel().setValueAt("## github.com/Valdecy ##",  arraySolutionEIV.length + 2, 0);
					table_1.getModel().setValueAt("Valdecy Pereira", arraySolutionEIV.length + 4, 0);
					table_1.getModel().setValueAt("Helder Gomes Costa", arraySolutionEIV.length + 5, 0);
					table_1.getModel().setValueAt("Livia D. de O. Nepomuceno", arraySolutionEIV.length + 6, 0);

					dialog.setVisible(false);

				}//	EIV

				if (electre == 7){

					dialog.setVisible(true);

					buttonSave.setEnabled(true);

					ETri_Lambda = (Double)  spinnerETri_Lambda.getValue();
					int p       = (Integer) spinnerC.getValue();
					int q       = (Integer) spinnerA.getValue();
					int e_bh    = (Integer) spinnerETriB.getValue();

					Object[][] getData_bh = new Object[e_bh - 1][p];
					for (int i = 1; i < e_bh; i++) {
						for (int j = 1; j < p + 1; j++){
							getData_bh[i - 1][j - 1] = table.getValueAt(i - 1, j);	
							if (getData_bh[i - 1][j - 1] == null) {
								getData_bh[i - 1][j - 1] = "0";
							} 
						}
					}
					String[][] getString_bh = new String[e_bh - 1][p];
					for (int i = 1; i < e_bh; i++) {
						for (int j = 1; j < p + 1; j++){
							getString_bh[i - 1][j - 1] = (String)getData_bh[i - 1][j - 1];
							getString_bh[i - 1][j - 1] = getString_bh[i - 1][j - 1].replace(",", ".");	

						}
					}
					double[][] getValue_bh = new double[e_bh - 1][p];
					for (int i = 1; i < e_bh; i++) {
						for (int j = 1; j < p + 1; j++){
							try {
								Double.parseDouble(getString_bh[i - 1][j - 1]);
							} catch (NumberFormatException e1) {
								getString_bh[i - 1][j - 1] = "0";
							}
							getValue_bh[i - 1][j - 1] = Double.parseDouble(getString_bh[i - 1][j - 1]);		
						}
					}

					Object[][] getData = new Object[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							getData[i - 1][j - 1] = table.getValueAt(i + 3 + (e_bh - 1), j);	
							if (getData[i - 1][j - 1] == null) {
								getData[i - 1][j - 1] = "0";
							} 
						}
					}
					String[][] getString = new String[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							getString[i - 1][j - 1] = (String)getData[i - 1][j - 1];
							getString[i - 1][j - 1] = getString[i - 1][j - 1].replace(",", ".");	

						}
					}
					double[][] getValue = new double[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							try {
								Double.parseDouble(getString[i - 1][j - 1]);
							} catch (NumberFormatException e1) {
								getString[i - 1][j - 1] = "0";
							}
							getValue[i - 1][j - 1] = Double.parseDouble(getString[i - 1][j - 1]);		
						}
					}

					Object[] getDataW = new Object[p];
					for (int j = 1; j < p + 1; j++){
						getDataW[j - 1] = table.getValueAt(3 + (e_bh - 1), j);	
						if (getDataW[j - 1] == null) {
							getDataW[j - 1] = "0";
						}
					}
					String[] getStringW = new String[p];
					for (int j = 1; j < p + 1; j++){
						getStringW[j - 1] = (String)getDataW[j - 1];
						getStringW[j - 1] = getStringW[j - 1].replace(",", ".");
					}		
					double[] getValueW = new double[p];
					for (int j = 1; j < p + 1; j++){
						try {
							Double.parseDouble(getStringW[j - 1]);
						} catch (NumberFormatException e1) {
							getStringW[j - 1] = "0";
						}
						getValueW[j - 1] = Double.parseDouble(getStringW[j - 1]);		
					}

					Object[] getDataV = new Object[p];
					for (int j = 1; j < p + 1; j++){
						getDataV[j - 1] = table.getValueAt(2 + (e_bh - 1), j);	
						if (getDataV[j - 1] == null) {
							getDataV[j - 1] = "0";
						}
					}
					String[] getStringV = new String[p];
					for (int j = 1; j < p + 1; j++){
						getStringV[j - 1] = (String)getDataV[j - 1];
						getStringV[j - 1] = getStringV[j - 1].replace(",", ".");
					}		
					double[] getValueV = new double[p];
					for (int j = 1; j < p + 1; j++){
						try {
							Double.parseDouble(getStringV[j - 1]);
						} catch (NumberFormatException e1) {
							getStringV[j - 1] = "0";
						}
						getValueV[j - 1] = Double.parseDouble(getStringV[j - 1]);		
					}

					Object[] getDataP = new Object[p];
					for (int j = 1; j < p + 1; j++){
						getDataP[j - 1] = table.getValueAt(1 + (e_bh - 1), j);	
						if (getDataP[j - 1] == null) {
							getDataP[j - 1] = "0";
						}
					}
					String[] getStringP = new String[p];
					for (int j = 1; j < p + 1; j++){
						getStringP[j - 1] = (String)getDataP[j - 1];
						getStringP[j - 1] = getStringP[j - 1].replace(",", ".");
					}		
					double[] getValueP = new double[p];
					for (int j = 1; j < p + 1; j++){
						try {
							Double.parseDouble(getStringP[j - 1]);
						} catch (NumberFormatException e1) {
							getStringP[j - 1] = "0";
						}
						getValueP[j - 1] = Double.parseDouble(getStringP[j - 1]);		
					}
					Object[] getDataQ = new Object[p];
					for (int j = 1; j < p + 1; j++){
						getDataQ[j - 1] = table.getValueAt(0 + (e_bh - 1), j);	
						if (getDataQ[j - 1] == null) {
							getDataQ[j - 1] = "0";
						}
					}
					String[] getStringQ = new String[p];
					for (int j = 1; j < p + 1; j++){
						getStringQ[j - 1] = (String)getDataQ[j - 1];
						getStringQ[j - 1] = getStringQ[j - 1].replace(",", ".");
					}		
					double[] getValueQ = new double[p];
					for (int j = 1; j < p + 1; j++){
						try {
							Double.parseDouble(getStringQ[j - 1]);
						} catch (NumberFormatException e1) {
							getStringQ[j - 1] = "0";
						}
						getValueQ[j - 1] = Double.parseDouble(getStringQ[j - 1]);		
					}
					String [][] arraySolutionETri = ELECTRE_Tri.e_Tri_Algorithm(getValue, getValueP, getValueQ, getValueV, getValueW, getValue_bh);

					for (int i = 0; i < table_1.getRowCount(); i++){
						for (int j = 0; j < table_1.getColumnCount() - 1; j++){
							table_1.getModel().setValueAt("", i, j);		
						}
					}
					
					model_1.setColumnCount(arraySolutionETri[0].length);
					model_1.setRowCount(arraySolutionETri.length  + 8);
					
					for (int i = 0; i < arraySolutionETri.length; i++){
						for (int j = 0; j < arraySolutionETri[0].length; j++){
							table_1.getModel().setValueAt(arraySolutionETri[i][j], i + 2, j);		
						}
					}
					table_1.getModel().setValueAt("## ELECTRE Tri ##", 0, 0);
					table_1.getModel().setValueAt("## github.com/Valdecy ##",  arraySolutionETri.length + 2, 0);
					table_1.getModel().setValueAt("Valdecy Pereira", arraySolutionETri.length + 4, 0);
					table_1.getModel().setValueAt("Helder Gomes Costa", arraySolutionETri.length + 5, 0);
					table_1.getModel().setValueAt("Livia D. de O. Nepomuceno", arraySolutionETri.length + 6, 0);

					dialog.setVisible(false);

				}//	ETri

				if (electre == 8){

					dialog.setVisible(true);

					buttonSave.setEnabled(true);

					ETriMe_Lambda     = (Double)  spinnerETriMe_Lambda.getValue();
					ETriMe_Evaluators = (Integer) spinnerETriMeE.getValue();
					C                 = (Integer) spinnerC.getValue();

					int p       = (Integer) spinnerC.getValue();
					int q       = (Integer) spinnerA.getValue();
					int e_bh    = (Integer) spinnerETriMeB.getValue();

					p = p*ETriMe_Evaluators;

					Object[][] getData_bh = new Object[e_bh - 1][p];
					for (int i = 1; i < e_bh; i++) {
						for (int j = 1; j < p + 1; j++){
							getData_bh[i - 1][j - 1] = table.getValueAt(i - 1, j);	
							if (getData_bh[i - 1][j - 1] == null) {
								getData_bh[i - 1][j - 1] = "0";
							} 
						}
					}
					String[][] getString_bh = new String[e_bh - 1][p];
					for (int i = 1; i < e_bh; i++) {
						for (int j = 1; j < p + 1; j++){
							getString_bh[i - 1][j - 1] = (String)getData_bh[i - 1][j - 1];
							getString_bh[i - 1][j - 1] = getString_bh[i - 1][j - 1].replace(",", ".");	

						}
					}
					double[][] getValue_bh = new double[e_bh - 1][p];
					for (int i = 1; i < e_bh; i++) {
						for (int j = 1; j < p + 1; j++){
							try {
								Double.parseDouble(getString_bh[i - 1][j - 1]);
							} catch (NumberFormatException e1) {
								getString_bh[i - 1][j - 1] = "0";
							}
							getValue_bh[i - 1][j - 1] = Double.parseDouble(getString_bh[i - 1][j - 1]);		
						}
					}

					Object[][] getData = new Object[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							getData[i - 1][j - 1] = table.getValueAt(i + 3 + (e_bh - 1), j);	
							if (getData[i - 1][j - 1] == null) {
								getData[i - 1][j - 1] = "0";
							} 
						}
					}
					String[][] getString = new String[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							getString[i - 1][j - 1] = (String)getData[i - 1][j - 1];
							getString[i - 1][j - 1] = getString[i - 1][j - 1].replace(",", ".");	

						}
					}
					double[][] getValue = new double[q][p];
					for (int i = 1; i < q + 1; i++) {
						for (int j = 1; j < p + 1; j++){
							try {
								Double.parseDouble(getString[i - 1][j - 1]);
							} catch (NumberFormatException e1) {
								getString[i - 1][j - 1] = "0";
							}
							getValue[i - 1][j - 1] = Double.parseDouble(getString[i - 1][j - 1]);		
						}
					}

					Object[] getDataW = new Object[p];
					for (int j = 1; j < p + 1; j++){
						getDataW[j - 1] = table.getValueAt(3 + (e_bh - 1), j);	
						if (getDataW[j - 1] == null) {
							getDataW[j - 1] = "0";
						}
					}
					String[] getStringW = new String[p];
					for (int j = 1; j < p + 1; j++){
						getStringW[j - 1] = (String)getDataW[j - 1];
						getStringW[j - 1] = getStringW[j - 1].replace(",", ".");
					}		
					double[] getValueW = new double[p];
					for (int j = 1; j < p + 1; j++){
						try {
							Double.parseDouble(getStringW[j - 1]);
						} catch (NumberFormatException e1) {
							getStringW[j - 1] = "0";
						}
						getValueW[j - 1] = Double.parseDouble(getStringW[j - 1]);		
					}

					Object[] getDataV = new Object[p];
					for (int j = 1; j < p + 1; j++){
						getDataV[j - 1] = table.getValueAt(2 + (e_bh - 1), j);	
						if (getDataV[j - 1] == null) {
							getDataV[j - 1] = "0";
						}
					}
					String[] getStringV = new String[p];
					for (int j = 1; j < p + 1; j++){
						getStringV[j - 1] = (String)getDataV[j - 1];
						getStringV[j - 1] = getStringV[j - 1].replace(",", ".");
					}		
					double[] getValueV = new double[p];
					for (int j = 1; j < p + 1; j++){
						try {
							Double.parseDouble(getStringV[j - 1]);
						} catch (NumberFormatException e1) {
							getStringV[j - 1] = "0";
						}
						getValueV[j - 1] = Double.parseDouble(getStringV[j - 1]);		
					}

					Object[] getDataP = new Object[p];
					for (int j = 1; j < p + 1; j++){
						getDataP[j - 1] = table.getValueAt(1 + (e_bh - 1), j);	
						if (getDataP[j - 1] == null) {
							getDataP[j - 1] = "0";
						}
					}
					String[] getStringP = new String[p];
					for (int j = 1; j < p + 1; j++){
						getStringP[j - 1] = (String)getDataP[j - 1];
						getStringP[j - 1] = getStringP[j - 1].replace(",", ".");
					}		
					double[] getValueP = new double[p];
					for (int j = 1; j < p + 1; j++){
						try {
							Double.parseDouble(getStringP[j - 1]);
						} catch (NumberFormatException e1) {
							getStringP[j - 1] = "0";
						}
						getValueP[j - 1] = Double.parseDouble(getStringP[j - 1]);		
					}
					Object[] getDataQ = new Object[p];
					for (int j = 1; j < p + 1; j++){
						getDataQ[j - 1] = table.getValueAt(0 + (e_bh - 1), j);	
						if (getDataQ[j - 1] == null) {
							getDataQ[j - 1] = "0";
						}
					}
					String[] getStringQ = new String[p];
					for (int j = 1; j < p + 1; j++){
						getStringQ[j - 1] = (String)getDataQ[j - 1];
						getStringQ[j - 1] = getStringQ[j - 1].replace(",", ".");
					}		
					double[] getValueQ = new double[p];
					for (int j = 1; j < p + 1; j++){
						try {
							Double.parseDouble(getStringQ[j - 1]);
						} catch (NumberFormatException e1) {
							getStringQ[j - 1] = "0";
						}
						getValueQ[j - 1] = Double.parseDouble(getStringQ[j - 1]);		
					}
					String [][] arraySolutionETri = ELECTRE_Tri.e_Tri_Algorithm(getValue, getValueP, getValueQ, getValueV, getValueW, getValue_bh);

					for (int i = 0; i < table_1.getRowCount(); i++){
						for (int j = 0; j < table_1.getColumnCount() - 1; j++){
							table_1.getModel().setValueAt("", i, j);		
						}
					}
					
					model_1.setColumnCount(arraySolutionETri[0].length);
					model_1.setRowCount(arraySolutionETri.length  + 8);
					
					for (int i = 0; i < arraySolutionETri.length; i++){
						for (int j = 0; j < arraySolutionETri[0].length; j++){
							table_1.getModel().setValueAt(arraySolutionETri[i][j], i + 2, j);		
						}
					}
					table_1.getModel().setValueAt("## ELECTRE Tri Me##", 0, 0);
					table_1.getModel().setValueAt("## github.com/Valdecy ##",  arraySolutionETri.length + 2, 0);
					table_1.getModel().setValueAt("Valdecy Pereira", arraySolutionETri.length + 4, 0);
					table_1.getModel().setValueAt("Helder Gomes Costa", arraySolutionETri.length + 5, 0);
					table_1.getModel().setValueAt("Livia D. de O. Nepomuceno", arraySolutionETri.length + 6, 0);

					dialog.setVisible(false);

				}//	ETriMe

			}
		});

		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser txt = new JFileChooser();
				int option = txt.showSaveDialog(f);
				if(option == JFileChooser.APPROVE_OPTION){
					String filename = txt.getSelectedFile().getName(); 
					String path = txt.getSelectedFile().getParentFile().getPath();
					int stringL = filename.length();
					String external = "";
					String save = "";
					if(stringL > 4){
						external = filename.substring(stringL - 4, stringL);
					}
					if(external.equals(".xls")){
						save = path + "\\" + filename; 
					}else{
						save = path + "\\" + filename + ".xls"; 
					}
					try{
						Export.xt(table_1, new File(save));
					}catch(IOException e1){ System.out.println(e1);
					}
				}

			}
		});

		spinnerETriB.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				btnBuildMatrix.setEnabled(true);
				buttonSolve.setEnabled(false);
				buttonSave.setEnabled(false);
			}
		});
		spinnerETriMeB.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				btnBuildMatrix.setEnabled(true);
				buttonSolve.setEnabled(false);
				buttonSave.setEnabled(false);
			}
		});
		spinnerETriMeE.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				btnBuildMatrix.setEnabled(true);
				buttonSolve.setEnabled(false);
				buttonSave.setEnabled(false);
			}
		});
		spinnerC.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				btnBuildMatrix.setEnabled(true);
				buttonSolve.setEnabled(false);
				buttonSave.setEnabled(false);
			}
		});
		spinnerA.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				btnBuildMatrix.setEnabled(true);
				buttonSolve.setEnabled(false);
				buttonSave.setEnabled(false);
			}
		});

		JRadioButton rdbtnEI = new JRadioButton("Electre I");
		rdbtnEI.setBounds(6, 7, 109, 23);
		panel.add(rdbtnEI);
		rdbtnEI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				electre = 1;
				lblEIQ.setEnabled(true);
				spinnerEIQ.setEnabled(true);
				lblEIP.setEnabled(true);
				spinnerEIP.setEnabled(true);
				lblEI_vQ.setEnabled(false);
				spinnerEI_vQ.setEnabled(false);
				lblEI_vP.setEnabled(false);
				spinnerEI_vP.setEnabled(false);
				lblEIICm.setEnabled(false);
				spinnerEIICm.setEnabled(false);
				lblEIIC.setEnabled(false);
				spinnerEIIC.setEnabled(false);
				lblEIICp.setEnabled(false);
				spinnerEIICp.setEnabled(false);
				lblEIId1.setEnabled(false);
				spinnerEIId1.setEnabled(false);
				lblEIId2.setEnabled(false);
				spinnerEIId2.setEnabled(false);
				lblETriB.setEnabled(false);
				spinnerETriB.setEnabled(false);
				lblEtriMeB.setEnabled(false);
				spinnerETriMeB.setEnabled(false);
				lblETriMeE.setEnabled(false);
				spinnerETriMeE.setEnabled(false);
				lblEI_sLambda.setEnabled(false);
				spinnerEI_sLambda.setEnabled(false);
				lblA.setEnabled(true);
				lblC.setEnabled(true);
				spinnerA.setEnabled(true);
				spinnerC.setEnabled(true);
				btnBuildMatrix.setEnabled(true);
				lblETri_Lambda.setEnabled(false);
				spinnerETri_Lambda.setEnabled(false);
				lblETriMe_Lambda.setEnabled(false);
				spinnerETriMe_Lambda.setEnabled(false);
				buttonSolve.setEnabled(false);
				buttonSave.setEnabled(false);
				lblEI_sCycles.setEnabled(false);
				spinnerEI_sCycles.setEnabled(false);
				lblEIICycles.setEnabled(false);
				spinnerEIICycles.setEnabled(false);
			}
		});

		JRadioButton rdbtnEI_s = new JRadioButton("Electre I_s");
		rdbtnEI_s.setBounds(6, 34, 109, 23);
		panel.add(rdbtnEI_s);
		rdbtnEI_s.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				electre = 2;
				lblEIQ.setEnabled(false);
				spinnerEIQ.setEnabled(false);
				lblEIP.setEnabled(false);
				spinnerEIP.setEnabled(false);
				lblEI_vQ.setEnabled(false);
				spinnerEI_vQ.setEnabled(false);
				lblEI_vP.setEnabled(false);
				spinnerEI_vP.setEnabled(false);
				lblEIICm.setEnabled(false);
				spinnerEIICm.setEnabled(false);
				lblEIIC.setEnabled(false);
				spinnerEIIC.setEnabled(false);
				lblEIICp.setEnabled(false);
				spinnerEIICp.setEnabled(false);
				lblEIId1.setEnabled(false);
				spinnerEIId1.setEnabled(false);
				lblEIId2.setEnabled(false);
				spinnerEIId2.setEnabled(false);
				lblETriB.setEnabled(false);
				spinnerETriB.setEnabled(false);
				lblEtriMeB.setEnabled(false);
				spinnerETriMeB.setEnabled(false);
				lblETriMeE.setEnabled(false);
				spinnerETriMeE.setEnabled(false);
				lblEI_sLambda.setEnabled(false);
				spinnerEI_sLambda.setEnabled(true);
				lblA.setEnabled(true);
				lblC.setEnabled(true);
				spinnerA.setEnabled(true);
				spinnerC.setEnabled(true);
				btnBuildMatrix.setEnabled(true);
				lblETri_Lambda.setEnabled(false);
				spinnerETri_Lambda.setEnabled(false);
				lblETriMe_Lambda.setEnabled(false);
				spinnerETriMe_Lambda.setEnabled(false);
				buttonSolve.setEnabled(false);
				buttonSave.setEnabled(false);
				lblEI_sCycles.setEnabled(true);
				spinnerEI_sCycles.setEnabled(true);
				lblEIICycles.setEnabled(false);
				spinnerEIICycles.setEnabled(false);
			}
		});

		JRadioButton rdbtnEI_v = new JRadioButton("Electre I_v");
		rdbtnEI_v.setBounds(6, 61, 109, 23);
		panel.add(rdbtnEI_v);
		rdbtnEI_v.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				electre = 3;
				lblEIQ.setEnabled(false);
				spinnerEIQ.setEnabled(false);
				lblEIP.setEnabled(false);
				spinnerEIP.setEnabled(false);
				lblEI_vQ.setEnabled(true);
				spinnerEI_vQ.setEnabled(true);
				lblEI_vP.setEnabled(true);
				spinnerEI_vP.setEnabled(true);
				lblEIICm.setEnabled(false);
				spinnerEIICm.setEnabled(false);
				lblEIIC.setEnabled(false);
				spinnerEIIC.setEnabled(false);
				lblEIICp.setEnabled(false);
				spinnerEIICp.setEnabled(false);
				lblEIId1.setEnabled(false);
				spinnerEIId1.setEnabled(false);
				lblEIId2.setEnabled(false);
				spinnerEIId2.setEnabled(false);
				lblETriB.setEnabled(false);
				spinnerETriB.setEnabled(false);
				lblEtriMeB.setEnabled(false);
				spinnerETriMeB.setEnabled(false);
				lblETriMeE.setEnabled(false);
				spinnerETriMeE.setEnabled(false);
				lblEI_sLambda.setEnabled(false);
				spinnerEI_sLambda.setEnabled(false);
				lblA.setEnabled(true);
				lblC.setEnabled(true);
				spinnerA.setEnabled(true);
				spinnerC.setEnabled(true);
				btnBuildMatrix.setEnabled(true);
				lblETri_Lambda.setEnabled(false);
				spinnerETri_Lambda.setEnabled(false);
				lblETriMe_Lambda.setEnabled(false);
				spinnerETriMe_Lambda.setEnabled(false);
				buttonSolve.setEnabled(false);
				buttonSave.setEnabled(false);
				lblEI_sCycles.setEnabled(false);
				spinnerEI_sCycles.setEnabled(false);
				lblEIICycles.setEnabled(false);
				spinnerEIICycles.setEnabled(false);
			}
		});

		JRadioButton rdbtnEII = new JRadioButton("Electre II");
		rdbtnEII.setBounds(6, 88, 109, 23);
		panel.add(rdbtnEII);
		rdbtnEII.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				electre = 4;
				lblEIQ.setEnabled(false);
				spinnerEIQ.setEnabled(false);
				lblEIP.setEnabled(false);
				spinnerEIP.setEnabled(false);
				lblEI_vQ.setEnabled(false);
				spinnerEI_vQ.setEnabled(false);
				lblEI_vP.setEnabled(false);
				spinnerEI_vP.setEnabled(false);
				lblEIICm.setEnabled(true);
				spinnerEIICm.setEnabled(true);
				lblEIIC.setEnabled(true);
				spinnerEIIC.setEnabled(true);
				lblEIICp.setEnabled(true);
				spinnerEIICp.setEnabled(true);
				lblEIId1.setEnabled(true);
				spinnerEIId1.setEnabled(true);
				lblEIId2.setEnabled(true);
				spinnerEIId2.setEnabled(true);
				lblETriB.setEnabled(false);
				spinnerETriB.setEnabled(false);
				lblEtriMeB.setEnabled(false);
				spinnerETriMeB.setEnabled(false);
				lblETriMeE.setEnabled(false);
				spinnerETriMeE.setEnabled(false);
				lblEI_sLambda.setEnabled(false);
				spinnerEI_sLambda.setEnabled(false);
				lblA.setEnabled(true);
				lblC.setEnabled(true);
				spinnerA.setEnabled(true);
				spinnerC.setEnabled(true);
				btnBuildMatrix.setEnabled(true);
				lblETri_Lambda.setEnabled(false);
				spinnerETri_Lambda.setEnabled(false);
				lblETriMe_Lambda.setEnabled(false);
				spinnerETriMe_Lambda.setEnabled(false);
				buttonSolve.setEnabled(false);
				buttonSave.setEnabled(false);
				lblEI_sCycles.setEnabled(false);
				spinnerEI_sCycles.setEnabled(false);
				lblEIICycles.setEnabled(true);
				spinnerEIICycles.setEnabled(true);
			}
		});

		JRadioButton rdbtnEIII = new JRadioButton("Electre III");
		rdbtnEIII.setBounds(6, 141, 109, 23);
		panel.add(rdbtnEIII);
		rdbtnEIII.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				electre = 5;
				lblEIQ.setEnabled(false);
				spinnerEIQ.setEnabled(false);
				lblEIP.setEnabled(false);
				spinnerEIP.setEnabled(false);
				lblEI_vQ.setEnabled(false);
				spinnerEI_vQ.setEnabled(false);
				lblEI_vP.setEnabled(false);
				spinnerEI_vP.setEnabled(false);
				lblEIICm.setEnabled(false);
				spinnerEIICm.setEnabled(false);
				lblEIIC.setEnabled(false);
				spinnerEIIC.setEnabled(false);
				lblEIICp.setEnabled(false);
				spinnerEIICp.setEnabled(false);
				lblEIId1.setEnabled(false);
				spinnerEIId1.setEnabled(false);
				lblEIId2.setEnabled(false);
				spinnerEIId2.setEnabled(false);
				lblETriB.setEnabled(false);
				spinnerETriB.setEnabled(false);
				lblEtriMeB.setEnabled(false);
				spinnerETriMeB.setEnabled(false);
				lblETriMeE.setEnabled(false);
				spinnerETriMeE.setEnabled(false);
				lblEI_sLambda.setEnabled(false);
				spinnerEI_sLambda.setEnabled(false);
				lblA.setEnabled(true);
				lblC.setEnabled(true);
				spinnerA.setEnabled(true);
				spinnerC.setEnabled(true);
				btnBuildMatrix.setEnabled(true);
				lblETri_Lambda.setEnabled(false);
				spinnerETri_Lambda.setEnabled(false);
				lblETriMe_Lambda.setEnabled(false);
				spinnerETriMe_Lambda.setEnabled(false);
				buttonSolve.setEnabled(false);
				buttonSave.setEnabled(false);
				lblEI_sCycles.setEnabled(false);
				spinnerEI_sCycles.setEnabled(false);
				lblEIICycles.setEnabled(false);
				spinnerEIICycles.setEnabled(false);
			}
		});

		JRadioButton rdbtnEIV = new JRadioButton("Electre IV");
		rdbtnEIV.setBounds(6, 168, 109, 23);
		panel.add(rdbtnEIV);
		rdbtnEIV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				electre = 6;
				lblEIQ.setEnabled(false);
				spinnerEIQ.setEnabled(false);
				lblEIP.setEnabled(false);
				spinnerEIP.setEnabled(false);
				lblEI_vQ.setEnabled(false);
				spinnerEI_vQ.setEnabled(false);
				lblEI_vP.setEnabled(false);
				spinnerEI_vP.setEnabled(false);
				lblEIICm.setEnabled(false);
				spinnerEIICm.setEnabled(false);
				lblEIIC.setEnabled(false);
				spinnerEIIC.setEnabled(false);
				lblEIICp.setEnabled(false);
				spinnerEIICp.setEnabled(false);
				lblEIId1.setEnabled(false);
				spinnerEIId1.setEnabled(false);
				lblEIId2.setEnabled(false);
				spinnerEIId2.setEnabled(false);
				lblETriB.setEnabled(false);
				spinnerETriB.setEnabled(false);
				lblEtriMeB.setEnabled(false);
				spinnerETriMeB.setEnabled(false);
				lblETriMeE.setEnabled(false);
				spinnerETriMeE.setEnabled(false);
				lblEI_sLambda.setEnabled(false);
				spinnerEI_sLambda.setEnabled(false);
				lblA.setEnabled(true);
				lblC.setEnabled(true);
				spinnerA.setEnabled(true);
				spinnerC.setEnabled(true);
				btnBuildMatrix.setEnabled(true);
				lblETri_Lambda.setEnabled(false);
				spinnerETri_Lambda.setEnabled(false);
				lblETriMe_Lambda.setEnabled(false);
				spinnerETriMe_Lambda.setEnabled(false);
				buttonSolve.setEnabled(false);
				buttonSave.setEnabled(false);
				lblEI_sCycles.setEnabled(false);
				spinnerEI_sCycles.setEnabled(false);
				lblEIICycles.setEnabled(false);
				spinnerEIICycles.setEnabled(false);
			}
		});

		JRadioButton rdbtnETri = new JRadioButton("Electre TRI");
		rdbtnETri.setBounds(6, 195, 109, 23);
		panel.add(rdbtnETri);
		rdbtnETri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				electre = 7;
				lblEIQ.setEnabled(false);
				spinnerEIQ.setEnabled(false);
				lblEIP.setEnabled(false);
				spinnerEIP.setEnabled(false);
				lblEI_vQ.setEnabled(false);
				spinnerEI_vQ.setEnabled(false);
				lblEI_vP.setEnabled(false);
				spinnerEI_vP.setEnabled(false);
				lblEIICm.setEnabled(false);
				spinnerEIICm.setEnabled(false);
				lblEIIC.setEnabled(false);
				spinnerEIIC.setEnabled(false);
				lblEIICp.setEnabled(false);
				spinnerEIICp.setEnabled(false);
				lblEIId1.setEnabled(false);
				spinnerEIId1.setEnabled(false);
				lblEIId2.setEnabled(false);
				spinnerEIId2.setEnabled(false);
				lblETriB.setEnabled(true);
				spinnerETriB.setEnabled(true);
				lblEtriMeB.setEnabled(false);
				spinnerETriMeB.setEnabled(false);
				lblETriMeE.setEnabled(false);
				spinnerETriMeE.setEnabled(false);
				lblEI_sLambda.setEnabled(false);
				spinnerEI_sLambda.setEnabled(false);
				lblA.setEnabled(true);
				lblC.setEnabled(true);
				spinnerA.setEnabled(true);
				spinnerC.setEnabled(true);
				btnBuildMatrix.setEnabled(true);
				lblETri_Lambda.setEnabled(true);
				spinnerETri_Lambda.setEnabled(true);
				lblETriMe_Lambda.setEnabled(false);
				spinnerETriMe_Lambda.setEnabled(false);
				buttonSolve.setEnabled(false);
				buttonSave.setEnabled(false);
				lblEI_sCycles.setEnabled(false);
				spinnerEI_sCycles.setEnabled(false);
				lblEIICycles.setEnabled(false);
				spinnerEIICycles.setEnabled(false);
			}
		});

		JRadioButton rdbtnETriMe = new JRadioButton("Electre TRI ME");
		rdbtnETriMe.setBounds(6, 222, 109, 23);
		panel.add(rdbtnETriMe);
		rdbtnETriMe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				electre = 8;
				lblEIQ.setEnabled(false);
				spinnerEIQ.setEnabled(false);
				lblEIP.setEnabled(false);
				spinnerEIP.setEnabled(false);
				lblEI_vQ.setEnabled(false);
				spinnerEI_vQ.setEnabled(false);
				lblEI_vP.setEnabled(false);
				spinnerEI_vP.setEnabled(false);
				lblEIICm.setEnabled(false);
				spinnerEIICm.setEnabled(false);
				lblEIIC.setEnabled(false);
				spinnerEIIC.setEnabled(false);
				lblEIICp.setEnabled(false);
				spinnerEIICp.setEnabled(false);
				lblEIId1.setEnabled(false);
				spinnerEIId1.setEnabled(false);
				lblEIId2.setEnabled(false);
				spinnerEIId2.setEnabled(false);
				lblETriB.setEnabled(false);
				spinnerETriB.setEnabled(false);
				lblEtriMeB.setEnabled(true);
				spinnerETriMeB.setEnabled(true);
				lblETriMeE.setEnabled(true);
				spinnerETriMeE.setEnabled(true);
				lblEI_sLambda.setEnabled(false);
				spinnerEI_sLambda.setEnabled(false);
				lblA.setEnabled(true);
				lblC.setEnabled(true);
				spinnerA.setEnabled(true);
				spinnerC.setEnabled(true);
				btnBuildMatrix.setEnabled(true);
				lblETri_Lambda.setEnabled(false);
				spinnerETri_Lambda.setEnabled(false);
				lblETriMe_Lambda.setEnabled(true);
				spinnerETriMe_Lambda.setEnabled(true);
				buttonSolve.setEnabled(false);
				buttonSave.setEnabled(false);
				lblEI_sCycles.setEnabled(false);
				spinnerEI_sCycles.setEnabled(false);
				lblEIICycles.setEnabled(false);
				spinnerEIICycles.setEnabled(false);
			}
		});

		ButtonGroup group = new ButtonGroup();
		f.getContentPane().setLayout(null);
		group.add(rdbtnEI);
		group.add(rdbtnEI_s);
		group.add(rdbtnEI_v);
		group.add(rdbtnEII);
		group.add(rdbtnEIII);
		group.add(rdbtnEIV);
		group.add(rdbtnETri);
		group.add(rdbtnETriMe);
		f.getContentPane().add(panel);
		
		f.getContentPane().add(scrollPane);
		f.getContentPane().add(scrollPane_1);
		f.getContentPane().add(panel_1);

		f.setVisible( true );
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
}