import javax.swing.SwingUtilities;

import backend.*;
import frontend.*;

public class Main {
    public static void main(String[] args) {
        // Set up the DBManager instance
        DBManager dbManager = new DBManager();

        // Create and show the XilftenMenu
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new XilftenMenu(dbManager);
            }
        });
    }
}