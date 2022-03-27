package automatic;

import java.awt.AWTException;
import java.awt.Robot;
//import java.awt.event.InputEvent;
//import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.image.BufferedImage;
//import javax.swing.ImageIcon;
//import java.awt.Toolkit;
import java.io.File;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
//import net.sourceforge.tess4j.util.LoadLibs;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;
//import javax.imageio.ImageIO;
import java.awt.Rectangle;
//import java.util.Date;
//import java.text.SimpleDateFormat;

/**
 * @author noname
 */
public class Automatic {

    private Robot robot;
    private FindImage FI1 = new FindImage();
    private FindImage FI2 = new FindImage();
    private FindImage FI3 = new FindImage();
    private String path = new File("").getAbsolutePath();
    private String graphics = path + System.getProperty("file.separator") + "Graphics" + System.getProperty("file.separator");
    private String log = path + System.getProperty("file.separator") + "log" + System.getProperty("file.separator");

    public static void main(String[] args) {
        Automatic automatic = new Automatic(0);
    }

    public Automatic(int a) {
        try {
            while (true) {
                robot = new Robot();
                FI1.findImage(graphics + "明日方舟/采购中心.png", 0, 0, 999999, 999999, 5, true);
                System.out.println(FI1.notFindImage);
                robot.delay(1000);
                closeMenu();
            }
        } catch (IOException | AWTException ex) {
            Logger.getLogger(Automatic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("deprecation")
    public Automatic() {
        int[] a;
        boolean[] b;
        try {
            robot = new Robot();
            Process p = Runtime.getRuntime().exec("E:\\emulator\\nemu\\EmulatorShell\\NemuPlayer.exe");
            //FI.ClickImage(graphics + "明日方舟.png", 0, 0, 999999, 999999, 5, true, 1000, 0);
            //FI.ClickImage(graphics + "明日方舟/start.png", 0, 0, 999999, 999999, 20, true, 1000, 0);
            //Thread.sleep(30000);
            while (true) {
                robot.delay(2000);
                System.out.println("a");
                FI1.findImage(graphics + "明日方舟/作战.png", 0, 0, 999999, 999999, 5, true);
                b = new boolean[]{FI1.notFindImage};
                FI2.findImage(graphics + "明日方舟/关闭公告.png", 0, 0, 999999, 999999, 1, true);
                if (FI1.notFindImage || b[0]) {
                    break;
                }
            }
            if (b[0] && !FI1.notFindImage) {
                FI1.ClickLeftMouse(10);
                robot.delay(5000);
                FI1.ClickImage(graphics + "明日方舟/关闭公告.png", 0, 0, 999999, 999999, 5, true, 1000, 5);
                robot.delay(5000);
                FI1.ClickLeftMouse(10);
                robot.delay(5000);
                FI1.ClickImage(graphics + "明日方舟/关闭公告.png", 0, 0, 999999, 999999, 5, true, 1000, 5);
                robot.delay(5000);
                FI1.ClickLeftMouse(10);
                robot.delay(5000);
                FI1.ClickImage(graphics + "明日方舟/关闭公告.png", 0, 0, 999999, 999999, 5, true, 1000, 5);
                robot.delay(5000);
                FI1.ClickLeftMouse(10);
                robot.delay(5000);
                FI1.ClickImage(graphics + "明日方舟/关闭公告.png", 0, 0, 999999, 999999, 5, true, 1000, 5);
                robot.delay(5000);
                FI1.ClickLeftMouse(10);
                robot.delay(5000);
                while (true) {
                    FI1.ClickImage(graphics + "明日方舟/关闭公告.png", 0, 0, 999999, 999999, 5, true, 1, 1);
                    FI1.findImage(graphics + "明日方舟/采购中心.png", 0, 0, 999999, 999999, 5, true);
                    if (FI1.notFindImage) {
                        FI1.ClickLeftMouse(10);
                    } else {
                        break;
                    }
                }
                /*
                while (true) {
                    System.out.println("c");
                    Thread.sleep(5000);
                    findrobot = new ScreenFindImage(graphics + "明日方舟/活动领取.png", null, 0, 0);
                    if (findrobot.FI1.FMidY != 0) {
                        robot.mouseMove(findrobot.FI1.FMidX, findrobot.FI1.FMidY);
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        robot.delay(10);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    } else {
                        findrobot = new ScreenFindImage(graphics + "明日方舟/关闭公告.png", null, 720, 0);
                        if (findrobot.FI1.FMidY != 0) {
                            robot.mouseMove(findrobot.FI1.FMidX, findrobot.FI1.FMidY);
                            robot.mousePress(InputEvent.BUTTON1_MASK);
                            robot.delay(10);
                            robot.mouseRelease(InputEvent.BUTTON1_MASK);
                        } else {
                            findrobot = new ScreenFindImage(graphics + "明日方舟/采购中心.png", null, 720, 0);
                            if (findrobot.FI1.FMidY != 0) {
                                break;
                            } else {
                                robot.mousePress(InputEvent.BUTTON1_MASK);
                                robot.delay(10);
                                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                            }
                        }
                    }
                }*/
            }
            System.out.println("d");
            //detect_new_mail();
            System.out.println("e");
            to_LS_5();
            robot.delay(1000);
            loopDo(graphics + "明日方舟/通关.png", 4);
        } catch (AWTException | IOException | TesseractException ex) {
            Logger.getLogger(Automatic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void detect_new_mail() throws IOException {
        int[] a = {0, 0};
        while (true) {
            robot.delay(1000);
            FI1.findImage(graphics + "明日方舟/新邮件.png", 0, 0, 999999, 999999, 5, true);
            if (!FI1.notFindImage) {
                FI1.ClickLeftMouse(FI1.FMidX, FI1.FMidY);
                while (true) {
                    robot.delay(1000);
                    FI1.findImage(graphics + "明日方舟/收邮件.png", 0, 0, 999999, 999999, 5, true);
                    if (!FI1.notFindImage) {
                        FI1.ClickLeftMouse(FI1.FMidX, FI1.FMidY);
                        while (true) {
                            robot.delay(1000);
                            FI1.findImage(graphics + "明日方舟/返回.png", 0, 0, 999999, 999999, 5, true);
                            if (!FI1.notFindImage) {
                                FI1.ClickLeftMouse(FI1.FMidX, FI1.FMidY);
                            } else {
                                FI1.ClickLeftMouse(10);
                            }
                        }
                    } else if (a[1] > 5) {
                        FI1.findImage(graphics + "明日方舟/返回.png", 0, 0, 999999, 999999, 5, true);
                        if (!FI1.notFindImage) {
                            FI1.ClickLeftMouse(FI1.FMidX, FI1.FMidY);
                        } else {
                            FI1.ClickLeftMouse(10);
                        }
                        break;
                    }
                    a[1]++;
                }
                break;
            } else if (a[0] > 5) {
                break;
            }
            a[0]++;
        }
    }

    private void combat_page(int buttomID) throws IOException {
        to_combat();
        robot.delay(2000);
        FI1.findImage(graphics + "坐标原点.png", 0, 0, 999999, 999999, 10, true);
        //System.out.println(FI1.FMaxY+650+"|"+(FI1.FMinX+148*buttomID)+"|"+buttomID+"|"+FI1.FMinX+"|"+FI1.FMinY);
        FI1.ClickLeftMouse(FI1.FMidX + 148 * buttomID, FI1.FMaxY + 650);
    }

    private void materials_page(int buttomID) throws IOException {
        combat_page(1);
        robot.delay(1000);
        FI1.ClickLeftMouse(FI1.FMaxX + 262 * buttomID, FI1.FMaxY + 360);
    }

    private void materials_level(int buttomID) throws IOException {
        FI1.findImage(graphics + "坐标原点.png", 0, 0, 999999, 999999, 10, true);
        FI1.ClickLeftMouse(FI1.FMaxX + new int[]{150, 420, 610, 770, 880}[buttomID], FI1.FMaxY + new int[]{570, 520, 410, 300, 190}[buttomID]);
    }

    private void back_to_the_main_menu() throws IOException {
        while (true) {
            FI1.findImage(graphics + "明日方舟/采购中心.png", 0, 0, 999999, 999999, 5, true);
            if (detectionLocation_menu()) {
                break;
            } else {
                FI1.ClickImage(graphics + "明日方舟/菜单.png", 0, 0, 999999, 999999, 5, true, 1000, 2);
                FI1.ClickImage(graphics + "明日方舟/菜单-首页.png", 0, 0, 999999, 999999, 5, true, 1000, 3);
            }
        }
    }

    private boolean detectionLocation_menu() throws IOException {
        FI1.findImage(graphics + "明日方舟/作战.png", 0, 0, 999999, 999999, 10, true);
        System.out.println("detectionLocation_menu=" + FI1.notFindImage);
        return !FI1.notFindImage;
    }

    private void to_combat() throws IOException {
        if (detectionLocation_menu()) {
            FI1.ClickImage(graphics + "明日方舟/作战.png", 0, 0, 999999, 999999, 5, true, 1000, 0);
        } else {
            FI1.ClickImage(graphics + "明日方舟/菜单.png", 0, 0, 999999, 999999, 5, true, 1000, 0);
            FI1.ClickImage(graphics + "明日方舟/菜单-作战.png", 0, 0, 999999, 999999, 5, true, 1000, 0);
        }
    }

    private void to_LS_5() throws IOException {
        //to_combat();
        //FI.ClickImage(graphics + "明日方舟/物资筹备.png", 0, 0, 999999, 999999, 30, true, 1000, 0);
        materials_page(0);
        robot.delay(1000);
        //FI.ClickImage(graphics + "明日方舟/狗粮.png", 0, 0, 999999, 999999, 5, true, 1000, 0);
        //FI.ClickImage(graphics + "明日方舟/LS-5.png", 0, 0, 999999, 999999, 5, true, 1000, 0);
        materials_level(4);
    }

    private int[] getIntellect(boolean isWriteLog) throws IOException, TesseractException {
        FI1.findImage(graphics + "坐标原点.png", 0, 0, 999999, 999999, 10, true);
        if (detectionLocation_menu()) {
            FI1.findImage(graphics + "明日方舟/理智1.png", 0, 0, 999999, 999999, 5, true);
            BufferedImage bi = robot.createScreenCapture(new Rectangle(FI1.FMaxX, FI1.FMinY, FI1.FMaxX + 130, FI1.FMaxY + FI1.FIWidth));
            ITesseract instance = new Tesseract();
            String s = instance.doOCR(bi);
            FI1.findImage(graphics + "明日方舟/理智3.png", 0, 0, 999999, 999999, 5, true);
            bi = robot.createScreenCapture(new Rectangle(FI1.FMaxX, FI1.FMinY, FI1.FMaxX + 55, FI1.FMaxY + FI1.FIWidth));
            String s1 = instance.doOCR(bi);
            if (isWriteLog) {
                Log.write(s + s1, true);
            }
            return new int[]{Integer.parseInt(s), Integer.parseInt(s1.substring(1))};
        } else {
            //FI.findImage(graphics + "明日方舟/理智2.png", 0, 0, 999999, 999999, 5, true);
            BufferedImage bi = robot.createScreenCapture(new Rectangle(FI1.FMaxX + 1140, FI1.FMaxY + 35, FI1.FMaxX + 1270, FI1.FMaxY + 70));
            ITesseract instance = new Tesseract();
            String s = instance.doOCR(bi);
            if (isWriteLog) {
                Log.write(s, true);
            }
            String[] s1 = s.split("/");
            System.out.println(s);
            return new int[]{toInt(s1[0]), toInt(s1[1])};
        }
    }

    private void loopDo(String path, int time) throws TesseractException, IOException {
        for (int i = 0; i < time; i++) {
            robot.delay(1000);
            FI1.ClickImage(graphics + "明日方舟/代理关.png", 0, 0, 999999, 999999, 5, true, 1, 1);
            if (getIntellect(true)[0] < 30) {
                break;
            }
            FI1.ClickImage(graphics + "明日方舟/开始行动1.png", 0, 0, 999999, 999999, 5, true, 1000, 0);
            FI1.ClickImage(graphics + "明日方舟/开始行动2.png", 0, 0, 999999, 999999, 5, true, 1000, 0);
            robot.delay(30000);
            while (true) {
                robot.delay(2000);
                FI1.findImage(path, 0, 0, 999999, 999999, 5, true);
                if (!FI1.notFindImage) {
                    FI1.ClickImage(path, 0, 0, 999999, 999999, 5, true, 1000, 999999);
                    break;
                }
            }
            robot.delay(10000);
        }
    }

    private void closeMenu() throws IOException {
        while (true) {
            FI1.findImage(graphics + "坐标原点.png", 0, 0, 999999, 999999, 10, true);
            if (!FI1.notFindImage) {
                FI1.ClickLeftMouse(FI1.FMaxX + 1259, FI1.FMaxY - 8);
                robot.delay(2000);
                FI1.ClickLeftMouse(FI1.FMaxX + 660, FI1.FMaxY + 460);
                break;
            }
            robot.delay(1000);
        }
    }

    private int toInt(String s) {
        String result = "";
        boolean a;
        for (int i = 0; i < s.length(); i++) {
            a = false;
            for (int j = 0; j < 10; j++) {
                if (s.substring(i, i + 1).equals(new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}[j])) {
                    a = true;
                }
            }
            if (a) {
                result += s.substring(i, i + 1);
            }
        }
        return Integer.parseInt(result);
    }
}
