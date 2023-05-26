package views;

import models.DatabaseData;
import models.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class View extends JFrame {
    private Model model;
    private PanelTop panelTop;
    private PanelBottom panelBottom;

    public View(Model model) {
        this.model = model;
        setupFrame();
        setupPanels();
    }
    private void setupFrame() {
        this.setTitle("Hangman Database Manager"); // Jframe
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sulge aken
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(520,400)); // akna suurus
    }

    public PanelBottom getPanelBottom() {
        return panelBottom;
    }

    private void setupPanels() {
        panelTop = new PanelTop(model);
        panelBottom = new PanelBottom();

        this.add(panelTop, BorderLayout.NORTH);
        this.add(panelBottom, BorderLayout.CENTER);

    }
    public JLabel getLblWordTotal() {
        return panelTop.getLblWordTotal();
    }

    public JLabel getLblCategoryTotal() {
        return panelTop.getLblCategoryTotal();
    }

    public JTextField getTxtWord() {
        return panelTop.getTxtWord();
    }

    public JTextField getTxtNewCategory() {
        return panelTop.getTxtNewCategory();
    }

    public JButton getBtnAdd() {
        return panelTop.getBtnAdd();
    }

    public JButton getBtnEdit() {
        return panelTop.getBtnEdit();
    }

    public JButton getBtnUpdate() {
        return panelTop.getBtnUpdate();
    }

    public JComboBox<String> getCmbCategory() {
        return panelTop.getCmbCategory();
    }

    public void updateTable() {
        model.getDtm().setRowCount(0); // Clear table
        int x = 1;
        for(DatabaseData dd : model.getDatabaseData()) {
            String word = dd.getWord();
            String category = dd.getCategory();
            model.getDtm().addRow(new Object[]{x, word, category, dd.getId()});
            x++;
        }
    }
    public void updateComboBox() {
        getCmbCategory().removeAllItems(); // Clear combobox
        for(String category : model.getCmbNames()) {
            getCmbCategory().addItem(category);
        }
    }
    public void registerItemStateChanged(ItemListener e) {panelTop.getCmbCategory().addItemListener(e);}
    public void registerButtonAdd(ActionListener al) {panelTop.getBtnAdd().addActionListener(al);}
    public void registerButtonEdit(ActionListener al) {panelTop.getBtnEdit().addActionListener(al);}
    public void registerButtonUpdate(ActionListener al) {panelTop.getBtnUpdate().addActionListener(al);}
}
