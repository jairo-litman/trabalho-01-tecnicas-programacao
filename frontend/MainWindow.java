package frontend;

import javax.swing.*;

import backend.DBManager;

public class MainWindow extends JFrame {
    DBManager dbManager;

    public MainWindow(DBManager dbManager) {
        super("Main Window");

        this.dbManager = dbManager;

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
