package automatic;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * @author noname
 */
public class FindImage {

    public Robot robot;
    private BufferedImage FullScreenImage = this.getFullScreenImage();
    private BufferedImage FindImage;
    public int FSIWidth;
    public int FSIHeight;
    public int FIWidth;
    public int FIHeight;
    private int[][][] FSIRGBData;
    private int[][][] FIRGBData;
    public int FMinX;
    public int FMinY;
    public int FMaxX;
    public int FMaxY;
    public int FMidX;
    public int FMidY;
    public boolean notFindImage;

    public FindImage() {
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(FindImage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ClickLeftMouse(int wait) {
        Log.write("ClickLeftMouse(wait=" + wait + ")", true);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(wait <= 0 ? 10 : wait);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void ClickLeftMouse(int x, int y) {
        ClickLeftMouse(x, y, 10);
    }

    public void ClickLeftMouse(int x, int y, int wait) {
        robot.mouseMove(x, y);
        Log.write("mouseMove(x=" + x + ",y=" + y + ")", true);
        ClickLeftMouse(wait);
    }

    public void ClickImage(String path, int a, int b, int c, int d, int ERGBMax, boolean isPercentage, int RGBPositiveDeviation, int RGBNegativeDeviation, int waitms, int loopT) throws IOException {
        for (int i = 0; i < (loopT == 0 ? 9999 : loopT); i++) {
            findImage(path, a, b, c, d, ERGBMax, isPercentage, RGBPositiveDeviation, RGBNegativeDeviation);
            if (notFindImage) {
                robot.delay(waitms);
            } else {
                ClickLeftMouse(FMidX, FMidY);
                notFindImage = true;
                break;
            }
        }
    }

    public void ClickImage(String path, int a, int b, int c, int d, int ERGBMax, boolean isPercentage, int waitms, int loopT) throws IOException {
        for (int i = 0; i < (loopT == 0 ? 9999 : loopT); i++) {
            findImage(path, a, b, c, d, ERGBMax, isPercentage);
            if (notFindImage) {
                robot.delay(waitms);
            } else {
                ClickLeftMouse(FMidX, FMidY);
                notFindImage = true;
                break;
            }
        }
    }

    /*public void ClickImage(String path, int a, int b, int c, int d, int ERGBMax, boolean isPercentage,int RGBPositiveDeviation,int RGBNegativeDeviation) throws IOException {
        findImage(path, a, b, c, d, ERGBMax, isPercentage,RGBPositiveDeviation,RGBNegativeDeviation);
        ClickLeftMouse(FMidX, FMidY);
        notFindImage = true;
    }*/
    public void findImage(String path, int a, int b, int c, int d, int ERGBMax, boolean isPercentage) throws IOException {
        FindImage = ImageIO.read(new File(path));
        FullScreenImage = getFullScreenImage();
        FSIRGBData = getImageGRB(FullScreenImage, 0);
        FIRGBData = getImageGRB(FindImage, 0);
        FSIWidth = FullScreenImage.getWidth();
        FSIHeight = FullScreenImage.getHeight();
        FIWidth = FindImage.getWidth();
        FIHeight = FindImage.getHeight();
        ERGBMax = isPercentage ? (int) (FIWidth * FIHeight * (ERGBMax / 100d)) : ERGBMax;
        int ErrorsRGB = 0;
        int Y;
        int X;
        int maxx = Math.min(FSIWidth - FIWidth, c);
        int maxy = Math.min(FSIHeight - FIHeight, d);
        boolean notF = false;
        notFindImage = true;
        Find1:
        for (int y = b; y < maxy; y++) {
            for (int x = a; x < maxx; x++) {
                //System.out.println((FIRGBData[0][0] ^ FSIRGBData[y][x])+"|"+ (FIRGBData[0][FIWidth - 1] ^ FSIRGBData[y][x + FIWidth - 1])+"|"+ (FIRGBData[FIHeight - 1][FIWidth - 1] ^ FSIRGBData[y + FIHeight - 1][x + FIWidth - 1])+"|"+ (FIRGBData[FIHeight - 1][0] ^ FSIRGBData[y + FIHeight - 1][x]));
                if ((FIRGBData[0][0][0] ^ FSIRGBData[y][x][0]) == 0 && (FIRGBData[0][FIWidth - 1][0] ^ FSIRGBData[y][x + FIWidth - 1][0]) == 0 && (FIRGBData[FIHeight - 1][FIWidth - 1][0] ^ FSIRGBData[y + FIHeight - 1][x + FIWidth - 1][0]) == 0 && (FIRGBData[FIHeight - 1][0][0] ^ FSIRGBData[y + FIHeight - 1][x][0]) == 0) {
                    ErrorsRGB = 0;
                    Find2:
                    for (int YY = 0; YY < FIHeight; YY++) {
                        Y = y + YY;
                        for (int XX = 0; XX < FIWidth; XX++) {
                            X = x + XX;
                            if (Y >= FSIHeight || X >= FSIWidth) {
                                notF = true;
                                break Find2;
                            }
                            if ((FIRGBData[YY][XX][0] ^ FSIRGBData[Y][X][0]) != 0) {
                                ErrorsRGB++;
                                if (ErrorsRGB > ERGBMax) {
                                    notF = true;
                                    break Find2;
                                }
                            }
                        }
                    }
                    System.out.println("x=" + x + "|y=" + y + "|" + ErrorsRGB + "/" + ERGBMax);
                    if (!notF) {
                        FMinY = y;
                        FMaxY = y + FIHeight;
                        FMidY = ((FMinY + FMaxY) / 2);
                        FMinX = x;
                        FMaxX = x + FIWidth;
                        FMidX = ((FMinX + FMaxX) / 2);
                        notFindImage = false;
                        break Find1;
                    }
                }
            }
        }
        Log.write("Find \"" + path + "\". result=" + (notFindImage ? "false" : ("true x=" + FMinX + "|y=" + FMinY + "|" + ErrorsRGB + "/" + ERGBMax)), true);
    }

    private int[][][] getImageGRB(BufferedImage bfImage, int a) {
        int width = bfImage.getWidth();
        int height = bfImage.getHeight();
        int[][][] result = new int[height][width][1];
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                result[h][w][0] = bfImage.getRGB(w, h) & 0xFFFFFF;
            }
        }
        return result;
    }

    public void findImage(String path, int a, int b, int c, int d, int ERGBMax, boolean isPercentage, int RGBPositiveDeviation, int RGBNegativeDeviation) throws IOException {
        RGBPositiveDeviation = Math.abs(RGBPositiveDeviation);
        //assert RGBPositiveDeviation>=0;
        RGBNegativeDeviation = Math.abs(RGBNegativeDeviation);
        //assert RGBNegativeDeviation>=0;
        FindImage = ImageIO.read(new File(path));
        FullScreenImage = getFullScreenImage();
        FSIRGBData = getImageGRB(FullScreenImage);
        FIRGBData = getImageGRB(FindImage, RGBPositiveDeviation, RGBNegativeDeviation);
        FSIWidth = FullScreenImage.getWidth();
        FSIHeight = FullScreenImage.getHeight();
        FIWidth = FindImage.getWidth();
        FIHeight = FindImage.getHeight();
        ERGBMax = isPercentage ? (int) (FIWidth * FIHeight * (ERGBMax / 100d)) : ERGBMax;
        int ErrorsRGB;
        int Y;
        int X;
        int maxx = Math.min(FSIWidth - FIWidth, c);
        int maxy = Math.min(FSIHeight - FIHeight, d);
        boolean notF = false;
        notFindImage = true;
        System.out.print(path);
        //System.out.println(FIRGBData[0][0][0]+"|"+FIRGBData[0][0][3]);
        Find1:
        for (int y = b; y < maxy; y++) {
            for (int x = a; x < maxx; x++) {
                //System.out.println((FIRGBData[0][0] ^ FSIRGBData[y][x])+"|"+ (FIRGBData[0][FIWidth - 1] ^ FSIRGBData[y][x + FIWidth - 1])+"|"+ (FIRGBData[FIHeight - 1][FIWidth - 1] ^ FSIRGBData[y + FIHeight - 1][x + FIWidth - 1])+"|"+ (FIRGBData[FIHeight - 1][0] ^ FSIRGBData[y + FIHeight - 1][x]));
                System.out.println(FSIRGBData[y][x][0] + "|" + FIRGBData[0][0][0] + "|" + FIRGBData[0][0][3]);
                if (FSIRGBData[y][x][0] >= FIRGBData[0][0][0]
                        && FSIRGBData[y][x][0] <= FIRGBData[0][0][3]
                        && FSIRGBData[y][x][1] >= FIRGBData[0][0][1]
                        && FSIRGBData[y][x][1] <= FIRGBData[0][0][4]
                        && FSIRGBData[y][x][2] >= FIRGBData[0][0][2]
                        && FSIRGBData[y][x][2] <= FIRGBData[0][0][5]
                        && FSIRGBData[y][x + FIWidth - 1][0] >= FIRGBData[0][FIWidth - 1][0]
                        && FSIRGBData[y][x + FIWidth - 1][0] <= FIRGBData[0][FIWidth - 1][3]
                        && FSIRGBData[y][x + FIWidth - 1][1] >= FIRGBData[0][FIWidth - 1][1]
                        && FSIRGBData[y][x + FIWidth - 1][1] <= FIRGBData[0][FIWidth - 1][4]
                        && FSIRGBData[y][x + FIWidth - 1][2] >= FIRGBData[0][FIWidth - 1][2]
                        && FSIRGBData[y][x + FIWidth - 1][2] <= FIRGBData[0][FIWidth - 1][5]
                        && FSIRGBData[y + FIHeight - 1][x + FIWidth - 1][0] >= FIRGBData[FIHeight - 1][FIWidth - 1][0]
                        && FSIRGBData[y + FIHeight - 1][x + FIWidth - 1][0] <= FIRGBData[FIHeight - 1][FIWidth - 1][3]
                        && FSIRGBData[y + FIHeight - 1][x + FIWidth - 1][1] >= FIRGBData[FIHeight - 1][FIWidth - 1][1]
                        && FSIRGBData[y + FIHeight - 1][x + FIWidth - 1][1] <= FIRGBData[FIHeight - 1][FIWidth - 1][4]
                        && FSIRGBData[y + FIHeight - 1][x + FIWidth - 1][2] >= FIRGBData[FIHeight - 1][FIWidth - 1][2]
                        && FSIRGBData[y + FIHeight - 1][x + FIWidth - 1][2] <= FIRGBData[FIHeight - 1][FIWidth - 1][5]
                        && FSIRGBData[y + FIHeight - 1][x][0] >= FIRGBData[FIHeight - 1][0][0]
                        && FSIRGBData[y + FIHeight - 1][x][0] <= FIRGBData[FIHeight - 1][0][3]
                        && FSIRGBData[y + FIHeight - 1][x][1] >= FIRGBData[FIHeight - 1][0][1]
                        && FSIRGBData[y + FIHeight - 1][x][1] <= FIRGBData[FIHeight - 1][0][4]
                        && FSIRGBData[y + FIHeight - 1][x][2] >= FIRGBData[FIHeight - 1][0][2]
                        && FSIRGBData[y + FIHeight - 1][x][2] <= FIRGBData[FIHeight - 1][0][5]) {
                    ErrorsRGB = 0;
                    Find2:
                    for (int YY = 0; YY < FIHeight; YY++) {
                        Y = y + YY;
                        for (int XX = 0; XX < FIWidth; XX++) {
                            X = x + XX;
                            if (Y >= FSIHeight || X >= FSIWidth) {
                                notF = true;
                                break Find2;
                            }
                            if (FSIRGBData[Y][X][0] >= FIRGBData[YY][XX][0]
                                    && FSIRGBData[Y][X][0] <= FIRGBData[YY][XX][3]
                                    && FSIRGBData[Y][X][1] >= FIRGBData[YY][XX][1]
                                    && FSIRGBData[Y][X][1] <= FIRGBData[YY][XX][4]
                                    && FSIRGBData[Y][X][2] >= FIRGBData[YY][XX][2]
                                    && FSIRGBData[Y][X][2] <= FIRGBData[YY][XX][5]) {
                                ErrorsRGB++;
                                if (ErrorsRGB > ERGBMax) {
                                    notF = true;
                                    break Find2;
                                }
                            }
                        }
                    }
                    System.out.println("x=" + x + "|y=" + y + "|" + ErrorsRGB + "/" + ERGBMax);
                    if (!notF) {
                        FMinY = y;
                        FMaxY = y + FIHeight;
                        FMidY = ((FMinY + FMaxY) / 2);
                        FMinX = x;
                        FMaxX = x + FIWidth;
                        FMidX = ((FMinX + FMaxX) / 2);
                        notFindImage = false;
                        break Find1;
                    }
                }
            }
        }
        System.out.println(notFindImage);
    }

    public int[][][] getImageGRB(BufferedImage bfImage) {
        int width = bfImage.getWidth();
        int height = bfImage.getHeight();
        int[][][] result = new int[height][width][3];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color c = new Color(bfImage.getRGB(x, y));
                result[y][x][0] = c.getRed();
                result[y][x][1] = c.getGreen();
                result[y][x][2] = c.getBlue();
            }
        }
        return result;
    }

    public int[][][] getImageGRB(BufferedImage bfImage, int RGBPositiveDeviation, int RGBNegativeDeviation) {
        int width = bfImage.getWidth();
        int height = bfImage.getHeight();
        int[][][] result = new int[height][width][6];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color c = new Color(bfImage.getRGB(x, y));
                result[y][x][0] = c.getRed() - RGBNegativeDeviation;
                result[y][x][1] = c.getGreen() - RGBNegativeDeviation;
                result[y][x][2] = c.getBlue() - RGBNegativeDeviation;
                result[y][x][3] = c.getRed() + RGBPositiveDeviation;
                result[y][x][4] = c.getGreen() + RGBPositiveDeviation;
                result[y][x][5] = c.getBlue() + RGBPositiveDeviation;
            }
        }
        return result;
    }

    public BufferedImage getFullScreenImage() {
        BufferedImage bfImage = null;
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        try {
            Robot robot1 = new Robot();
            bfImage = robot1.createScreenCapture(new Rectangle(0, 0, width, height));
        } catch (AWTException e) {
        }
        return bfImage;
    }
}
