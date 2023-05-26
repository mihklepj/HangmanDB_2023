package controllers;

import models.Database;
import models.Model;
import views.View;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ComboBoxItemChanged implements ItemListener {
    private final Model model;
    private final View view;

    public ComboBoxItemChanged(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // JOptionPane.showMessageDialog(null, "Changed ComboBox");
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if(e.getItem().equals(model.getChooseCategory())) {
                view.getTxtNewCategory().setEnabled(true);
                new Database(model.getDatabaseFile(), model);
            } else {
                view.getTxtNewCategory().setEnabled(false);
                view.getTxtNewCategory().setToolTipText("");
                new Database(model.getDatabaseFile(), model).selectByCategory(view.getCmbCategory().getSelectedItem().toString());

            }
            view.updateTable();
            view.getLblWordTotal().setText("Total: "+ model.getDatabaseData().size());
        }
    }
}
