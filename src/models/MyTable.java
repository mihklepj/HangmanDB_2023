package models;

import views.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyTable {
    private Model model;
    private View view;
    private String[] header = new String[] {"#", "Word", "Category", ""};
    private DefaultTableModel dtm = new DefaultTableModel(header,0);
    private JTable table = new JTable(dtm);

    public MyTable(Model model, View view) {
        this.model = model;
        this.view = view;
        model.setDtm(dtm);
        createTable();

        view.updateTable();
    }

    private void createTable() {
        JScrollPane jsp = new JScrollPane(table);

        view.getPanelBottom().revalidate();
        view.getPanelBottom().add(jsp);
        view.getPanelBottom().setVisible(false);
        view.getPanelBottom().setVisible(true);

        // https://stackoverflow.com/questions/1990817/how-to-make-a-jtable-non-editable
        table.setDefaultEditor(Object.class,null);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    int id = (int) table.getModel().getValueAt(table.getSelectedRow(), 3);
                    int result = JOptionPane.showConfirmDialog(view, "Delete record?");
                    if (result == 0) { // Clicked yes
                        new Database(model.getDatabaseFile(), model).delete(id);
                        view.updateTable();
                        view.updateComboBox();
                        view.getLblWordTotal().setText("Total: "+ model.getDatabaseData().size());
                        view.getLblCategoryTotal().setText("Total: " + (model.getCmbNames().length-1));
                    }
                }
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
                    if(table.getSelectedRow() != -1) {
                        view.getBtnEdit().setEnabled(true);
                        view.getBtnUpdate().setEnabled(true);
                    } else {
                        view.getBtnEdit().setEnabled(false);
                        view.getBtnUpdate().setEnabled(false);
                    }
                }
            }
        });

        // Last column is hidden
        // https://stackhowto.com/how-to-hide-a-column-in-jtable/
        table.getColumnModel().getColumn(3).setMinWidth(0);
        table.getColumnModel().getColumn(3).setMaxWidth(0);
        table.getColumnModel().getColumn(3).setWidth(0);

        table.getColumnModel().getColumn(0).setMinWidth(50);
        table.getColumnModel().getColumn(0).setMaxWidth(50);

        model.setTable(table); // set table to model
    }
}
