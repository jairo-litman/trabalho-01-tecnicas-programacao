import backend.Manager;
import frontend.XilftenMenu;

public class Main {
    public static void main(String[] args) {
        try {
            Manager db = Manager.getInstance();
            new XilftenMenu(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}