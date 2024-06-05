import backend.*;
import frontend.*;

public class Main {
    public static void main(String[] args) {
        DBManager dbManager = new DBManager();
        new MainWindow(dbManager);
    }
}