package controllers;

import models.Database;
import models.Model;
import views.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListenerUpdate implements ActionListener {
    private  Model model;
    private View view;
    public ButtonListenerUpdate(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String word = view.getTxtWord().getText();
        String category = view.getCmbCategory().getSelectedItem().toString();
        String newCategory = view.getTxtNewCategory().getText();
        if (!word.isEmpty() && view.getCmbCategory().getSelectedItem().toString().equals(model.getChooseCategory()) && !newCategory.isEmpty()) {
                // new category
            Database db = new Database(model.getDatabaseFile(), model);
            db.update(model.getChangeableId(), word, newCategory);
        } else if (!word.isEmpty() && !view.getCmbCategory().getSelectedItem().toString().equals(model.getChooseCategory()) && newCategory.isEmpty()) {
                 // old category
            Database db = new Database(model.getDatabaseFile(), model);
            db.update(model.getChangeableId(), word, category);
        } else {
            JOptionPane.showMessageDialog(view, "Something went wrong!");
        }

        view.updateTable();
        view.updateComboBox();
        view.getBtnEdit().setEnabled(false);
        view.getBtnUpdate().setEnabled(false);
        view.getBtnAdd().setEnabled(true);
        view.getTxtWord().setText("");
        view.getTxtNewCategory().setText("");
    }
}
