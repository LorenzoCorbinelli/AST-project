package com.corbinelli.lorenzo.swimming.view.swing;

import java.awt.EventQueue;
import java.util.List;

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

public class SwimmerSwingView extends JFrame implements SwimmerView {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtId;
	private JLabel lblName;
	private JTextField txtName;

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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblId = new JLabel("id");
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.fill = GridBagConstraints.VERTICAL;
		gbc_lblId.insets = new Insets(0, 0, 5, 5);
		gbc_lblId.gridx = 2;
		gbc_lblId.gridy = 0;
		contentPane.add(lblId, gbc_lblId);
		
		txtId = new JTextField();
		txtId.setName("idTextBox");
		GridBagConstraints gbc_txtId = new GridBagConstraints();
		gbc_txtId.insets = new Insets(0, 0, 5, 0);
		gbc_txtId.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtId.gridx = 3;
		gbc_txtId.gridy = 0;
		contentPane.add(txtId, gbc_txtId);
		txtId.setColumns(10);
		
		lblName = new JLabel("name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 2;
		gbc_lblName.gridy = 1;
		contentPane.add(lblName, gbc_lblName);
		
		txtName = new JTextField();
		txtName.setName("nameTextBox");
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.insets = new Insets(0, 0, 5, 0);
		gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtName.gridx = 3;
		gbc_txtName.gridy = 1;
		contentPane.add(txtName, gbc_txtName);
		txtName.setColumns(10);
	}

	@Override
	public void showAllSwimmers(List<Swimmer> swimmers) {
		
	}

	@Override
	public void swimmerAdded(Swimmer swimmer) {
		
	}

	@Override
	public void showError(String message, Swimmer swimmer) {
		
	}

	@Override
	public void swimmerRemoved(Swimmer swimmer) {
		
	}

}
