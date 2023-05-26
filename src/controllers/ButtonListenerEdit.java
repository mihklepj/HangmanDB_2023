package controllers;

import models.Database;
import models.DatabaseData;
import models.Model;
import views.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ButtonListenerEdit implements ActionListener {
    private Model model;
    private View view;
    public ButtonListenerEdit(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.getBtnAdd().setEnabled(false);
        int id = (int) model.getTable().getModel().getValueAt(model.getTable().getSelectedRow(), 3);
        List<DatabaseData> dd = new Database(model.getDatabaseFile(), model).selectById(id);
        view.getTxtWord().setText(dd.get(0).getWord());
        // view.getTxtNewCategory().setText(dd.get(0).getCategory());
        model.setChangeableId(id); // dd.get(0).getId()
        view.getCmbCategory().setSelectedItem(dd.get(0).getCategory());
    }

}
