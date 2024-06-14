package com.corbinelli.lorenzo.swimming.view.swing;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.corbinelli.lorenzo.swimming.model.Swimmer;
import com.corbinelli.lorenzo.swimming.view.SwimmerView;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ListSelectionModel;

public class SwimmerSwingView extends JFrame implements SwimmerView {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtId;
	private JLabel lblName;
	private JTextField txtName;
	private JLabel lblGender;
	private JRadioButton rdBtnMale;
	private JRadioButton rdBtnFemale;
	private JLabel lblMainStroke;
	private JComboBox<String> combBoxStrokes;
	private JButton btnAdd;
	private JScrollPane scrollPane;
	private JList<Swimmer> swimmerList;
	private DefaultListModel<Swimmer> listSwimmerModel;
	private JButton btnRemoveSwimmer;
	private JLabel errorMessageLabel;

	DefaultListModel<Swimmer> getListSwimmerModel() {
		return listSwimmerModel;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwimmerSwingView frame = new SwimmerSwingView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SwimmerSwingView() {
		setTitle("Swimming App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 448);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 120, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblId = new JLabel("id");
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.fill = GridBagConstraints.VERTICAL;
		gbc_lblId.insets = new Insets(0, 0, 5, 5);
		gbc_lblId.gridx = 0;
		gbc_lblId.gridy = 0;
		contentPane.add(lblId, gbc_lblId);
		
		txtId = new JTextField();
		KeyAdapter btnAddEnabler = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnAdd.setEnabled(
						!txtId.getText().trim().isEmpty() && 
						!txtName.getText().trim().isEmpty());
			}
		};
		txtId.addKeyListener(btnAddEnabler);
		txtId.setName("idTextBox");
		GridBagConstraints gbc_txtId = new GridBagConstraints();
		gbc_txtId.gridwidth = 5;
		gbc_txtId.insets = new Insets(0, 0, 5, 5);
		gbc_txtId.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtId.gridx = 1;
		gbc_txtId.gridy = 0;
		contentPane.add(txtId, gbc_txtId);
		txtId.setColumns(10);
		
		lblName = new JLabel("name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 1;
		contentPane.add(lblName, gbc_lblName);
		
		txtName = new JTextField();
		txtName.addKeyListener(btnAddEnabler);
		txtName.setName("nameTextBox");
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.gridwidth = 5;
		gbc_txtName.insets = new Insets(0, 0, 5, 5);
		gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtName.gridx = 1;
		gbc_txtName.gridy = 1;
		contentPane.add(txtName, gbc_txtName);
		txtName.setColumns(10);
		
		lblGender = new JLabel("gender");
		GridBagConstraints gbc_lblGender = new GridBagConstraints();
		gbc_lblGender.insets = new Insets(0, 0, 5, 5);
		gbc_lblGender.gridx = 0;
		gbc_lblGender.gridy = 2;
		contentPane.add(lblGender, gbc_lblGender);
		
		rdBtnMale = new JRadioButton("Male");
		rdBtnMale.setSelected(true);
		rdBtnMale.setName("rdBtnMale");
		GridBagConstraints gbc_rdBtnMale = new GridBagConstraints();
		gbc_rdBtnMale.insets = new Insets(0, 0, 5, 5);
		gbc_rdBtnMale.gridx = 1;
		gbc_rdBtnMale.gridy = 2;
		contentPane.add(rdBtnMale, gbc_rdBtnMale);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdBtnMale);
		
		rdBtnFemale = new JRadioButton("Female");
		rdBtnFemale.addKeyListener(btnAddEnabler);
		rdBtnFemale.setName("rdBtnFemale");
		GridBagConstraints gbc_rdBtnFemale = new GridBagConstraints();
		gbc_rdBtnFemale.insets = new Insets(0, 0, 5, 5);
		gbc_rdBtnFemale.gridx = 2;
		gbc_rdBtnFemale.gridy = 2;
		contentPane.add(rdBtnFemale, gbc_rdBtnFemale);
		buttonGroup.add(rdBtnFemale);
		
		lblMainStroke = new JLabel("main stroke");
		GridBagConstraints gbc_lblMainStroke = new GridBagConstraints();
		gbc_lblMainStroke.insets = new Insets(0, 0, 5, 5);
		gbc_lblMainStroke.gridx = 0;
		gbc_lblMainStroke.gridy = 3;
		contentPane.add(lblMainStroke, gbc_lblMainStroke);
		
		String[] strokes = {"Freestyle", "Backstroke", "Breaststroke", "Butterfly", "Mixed"};
		combBoxStrokes = new JComboBox<>(strokes);
		combBoxStrokes.setName("strokes");
		GridBagConstraints gbc_combBoxStrokes = new GridBagConstraints();
		gbc_combBoxStrokes.insets = new Insets(0, 0, 5, 5);
		gbc_combBoxStrokes.gridwidth = 5;
		gbc_combBoxStrokes.fill = GridBagConstraints.HORIZONTAL;
		gbc_combBoxStrokes.gridx = 1;
		gbc_combBoxStrokes.gridy = 3;
		contentPane.add(combBoxStrokes, gbc_combBoxStrokes);
		
		btnAdd = new JButton("Add");
		btnAdd.setEnabled(false);
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdd.gridwidth = 5;
		gbc_btnAdd.gridx = 1;
		gbc_btnAdd.gridy = 5;
		contentPane.add(btnAdd, gbc_btnAdd);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.anchor = GridBagConstraints.NORTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridwidth = 6;
		gbc_scrollPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 6;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		listSwimmerModel = new DefaultListModel<>();
		swimmerList = new JList<>(listSwimmerModel);
		swimmerList.addListSelectionListener(arg0 -> 
			btnRemoveSwimmer.setEnabled(swimmerList.getSelectedIndex() != -1));
		swimmerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		swimmerList.setName("swimmerList");
		scrollPane.setColumnHeaderView(swimmerList);
		
		btnRemoveSwimmer = new JButton("Remove Swimmer");
		btnRemoveSwimmer.setEnabled(false);
		GridBagConstraints gbc_btnRemoveSwimmer = new GridBagConstraints();
		gbc_btnRemoveSwimmer.gridwidth = 5;
		gbc_btnRemoveSwimmer.insets = new Insets(0, 0, 5, 5);
		gbc_btnRemoveSwimmer.gridx = 1;
		gbc_btnRemoveSwimmer.gridy = 7;
		contentPane.add(btnRemoveSwimmer, gbc_btnRemoveSwimmer);
		
		errorMessageLabel = new JLabel(" ");
		errorMessageLabel.setName("errorMessageLabel");
		errorMessageLabel.setForeground(Color.RED);
		GridBagConstraints gbc_errorMessageLabel = new GridBagConstraints();
		gbc_errorMessageLabel.gridwidth = 7;
		gbc_errorMessageLabel.insets = new Insets(0, 0, 0, 5);
		gbc_errorMessageLabel.gridx = 0;
		gbc_errorMessageLabel.gridy = 8;
		contentPane.add(errorMessageLabel, gbc_errorMessageLabel);
	}

	@Override
	public void showAllSwimmers(List<Swimmer> swimmers) {
		swimmers.forEach(listSwimmerModel::addElement);
	}

	@Override
	public void swimmerAdded(Swimmer swimmer) {
		listSwimmerModel.addElement(swimmer);
		errorMessageLabel.setText(" ");
	}

	@Override
	public void showError(String message, Swimmer swimmer) {
		
	}

	@Override
	public void swimmerRemoved(Swimmer swimmer) {
		
	}

}
