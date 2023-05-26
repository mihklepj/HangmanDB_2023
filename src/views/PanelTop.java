package views;

import models.Model;

import javax.swing.*;
import java.awt.*;

public class PanelTop extends JPanel {
    private Model model;
    private JPanel pnlComponents = new JPanel(new GridBagLayout());
    private GridBagConstraints gbc = new GridBagConstraints();
    private JLabel lblWordTotal, lblCategoryTotal; // Total labels
    private JTextField txtWord, txtNewCategory;
    private  JButton btnAdd, btnEdit, btnUpdate;
    private JComboBox<String> cmbCategory;

    public PanelTop(Model model) {
        this.model = model;
        setupPanel(); // setup PanelTop
        setupConstraints();
        setupLineFirst(); // First line on pnlComponents
        setupLineSecond(); // Second line on pnlComponents
        setupLineThird();
        setupLineFourth();

        this.add(pnlComponents);
    }

    private void setupPanel() {
        this.setBackground(new Color(184, 168, 104, 183));
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

    }
    private void setupConstraints() {
        gbc.anchor = GridBagConstraints.WEST; // align cell contents left
        gbc.insets = new Insets(2,2,2,2); // 2px space
        gbc.fill = GridBagConstraints.BOTH; // Stretches the contents of the cell
    }

    private void setupLineFirst() {
        JLabel lblWord = new JLabel("Word");
        gbc.gridx = 0; // Column
        gbc.gridy = 0; // Row
        pnlComponents.add(lblWord, gbc); // on pnlComponents

        txtWord = new JTextField("", 14);
        gbc.gridx = 1; // Column
        gbc.gridy = 0; // Row
        pnlComponents.add(txtWord, gbc); // on pnlComponents

        lblWordTotal = new JLabel("Total: " + model.getDatabaseData().size());
        gbc.gridx = 2; // Column
        gbc.gridy = 0; // Row
        pnlComponents.add(lblWordTotal, gbc); // on pnlComponents
    }
    private void setupLineSecond() {
        JLabel lblCategory = new JLabel("Category");
        gbc.gridx = 0; // Column
        gbc.gridy = 1; // Row
        pnlComponents.add(lblCategory, gbc); // on pnlComponents

        cmbCategory = new JComboBox<>(model.getCmbNames()); // combobox
        gbc.gridx = 1; // Column
        gbc.gridy = 1; // Row
        pnlComponents.add(cmbCategory, gbc); // on pnlComponents

        lblCategoryTotal = new JLabel("Total: " + (model.getCmbNames().length-1));
        gbc.gridx = 2; // Column
        gbc.gridy = 1; // Row
        pnlComponents.add(lblCategoryTotal, gbc); // on pnlComponents
    }

    private void setupLineThird() {
        JLabel lblNewCategory = new JLabel("New category");
        gbc.gridx = 0; // Column
        gbc.gridy = 2; // Row
        pnlComponents.add(lblNewCategory, gbc); // on pnlComponents

        txtNewCategory = new JTextField("", 14);
        gbc.gridx = 1; // Column
        gbc.gridy = 2; // Row
        pnlComponents.add(txtNewCategory, gbc); // on pnlComponents
    }

    private void setupLineFourth() {
        btnEdit = new JButton("Edit record");
        btnEdit.setEnabled(false);
        gbc.gridx = 0; // Column
        gbc.gridy = 3; // Row
        pnlComponents.add(btnEdit, gbc); // on pnlComponents

        btnAdd = new JButton("Add new word");
        gbc.gridx = 1; // Column
        gbc.gridy = 3; // Row
        pnlComponents.add(btnAdd, gbc); // on pnlComponents

        btnUpdate = new JButton("Update record");
        btnUpdate.setEnabled(false);
        gbc.gridx = 2; // Column
        gbc.gridy = 3; // Row
        pnlComponents.add(btnUpdate, gbc); // on pnlComponents
    }

    public JLabel getLblWordTotal() {
        return lblWordTotal;
    }

    public JLabel getLblCategoryTotal() {
        return lblCategoryTotal;
    }

    public JTextField getTxtWord() {
        return txtWord;
    }

    public JTextField getTxtNewCategory() {
        return txtNewCategory;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnEdit() {
        return btnEdit;
    }

    public JButton getBtnUpdate() {
        return btnUpdate;
    }

    public JComboBox<String> getCmbCategory() {
        return cmbCategory;
    }
}
