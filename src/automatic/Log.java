package automatic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.logging.Level;
//import java.util.logging.Logger;

/**
 * @author noname
 */
public abstract class Log {

    public static volatile String log = new File("").getAbsolutePath() + System.getProperty("file.separator") + "log" + System.getProperty("file.separator");

    private Log() {
        assert false;
    }

    public static void create() {
        File file = new File(log + get_time()[0] + ".txt");
        System.out.println(file.exists());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void write(String a, boolean isWT) {
        create();
        File file = new File(log + get_time()[0] + ".txt");
        try (java.io.FileWriter fw = new java.io.FileWriter(file, true)) {
            java.io.PrintWriter x = new java.io.PrintWriter(fw);
            x.println((isWT ? ("[" + get_time()[1] + "]") : "") + a);
            x.flush();
            fw.flush();
            x.close();
            fw.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String[] get_time() {
        return new String[]{new SimpleDateFormat("yyyy-MM-dd").format(new Date()), new SimpleDateFormat("HH:mm:ss").format(new Date())};
    }
}
