package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;
import org.omg.CORBA.Object;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.*;
import java.util.Arrays;
import java.util.Random;


public class Game implements Serializable {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        startMenu();
        TETile[][] world;
        String s0 = "";
        while (s0.length() < 1) {           //loop is necessary
            if (StdDraw.hasNextKeyTyped()) {
                s0 = s0 + String.valueOf(StdDraw.nextKeyTyped());
                if (s0.equals("Q")) {        //quit
                    System.exit(0);
                } else if (s0.equals("N")) {
                    drawString("Please enter a random number followed by letter S");
                    while (s0.charAt(s0.length() - 1) != 'S') {  //the last char in the string is not S. Start a loop that never ends until S is typed.
                        if (StdDraw.hasNextKeyTyped()) {
                            s0 = s0 + String.valueOf(StdDraw.nextKeyTyped());
                            drawString(s0);
                        }
                    }
                    int value = Integer.parseInt(s0.replaceAll("[^0-9]", ""));
                    long s = value;
                    worldGenerator wg = new worldGenerator(s);
                    world = wg.worldGeneration();
                    char c5;
                    while (s0.charAt(s0.length() - 1) != 'Q' || s0.charAt(s0.length() - 2) != ':') {
                        if (StdDraw.hasNextKeyTyped()) {
                            c5 = StdDraw.nextKeyTyped();
                            s0 = s0 + String.valueOf(c5);
                            int[] t = worldGenerator.movePlayer(c5, world);
                            ter.renderFrame(world);
                            if (worldGenerator.win) {
                                StdDraw.pause(500);
                                drawString("YOU WIN THE GAME!!");
                            }
//                              System.out.println(t[0] + " " + t[1]);     //test
                        }
                    }
                    //serialization
                    serial(s0);
                    return;
                } else if (s0.equals("L")) {    //deserialization
                    if (!checkSaved()) {
                        System.exit(0);
                    }
                    Game g1 = new Game();
                    try {
                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("C:\\Users\\YJC\\Desktop\\CS\\CS61B\\homework\\skeleton-sp18\\proj2\\game.txt"));
                        String input1 = (String) ois.readObject();  //get the original String
                        if (input1.length() == 0) {
                            System.exit(0);
                        }
                        String inputLoad = input1.substring(0, input1.length() - 2);
                        ois.close();
                        world = g1.resumeWorld(inputLoad);
                        String sx = inputLoad;
                        char c6;
                        while (sx.charAt(sx.length() - 1) != 'Q' || sx.charAt(sx.length() - 2) != ':') {
                            if (StdDraw.hasNextKeyTyped()) {
                                c6 = StdDraw.nextKeyTyped();
                                sx = sx + String.valueOf(c6);
//                                System.out.println(sx);         //test
                                int[] t = worldGenerator.movePlayer(c6, world);
                                ter.renderFrame(world);
                                if (worldGenerator.win) {
                                    StdDraw.pause(500);
                                    drawString("YOU WIN THE GAME!!");
                                }
                            }
                        }
                        serial(sx);
                        return;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                } else {
                    drawString("Wrong letter entered. Please restart the game.");
                    StdDraw.pause(1500);
                    System.exit(0);
                }
            }
        }

        }


    public void startMenu() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenRadius(0.003);
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        String s1 = "CS61B: THE GAME";
        StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3, s1);

        Font f1 = new Font("Monaco", Font.ITALIC, 15);
        StdDraw.setFont(f1);
        StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3 - 2, "by J. YIN");

        String s2 = "New Game (N)";
        String s3 = "Load Game (L)";
        String s4 = "Quit (Q)";
        Font f2 = new Font("Monaco", Font.PLAIN, 15);
        StdDraw.setFont(f2);
        StdDraw.text(WIDTH / 2, HEIGHT / 3 + 2, s2);
        StdDraw.text(WIDTH / 2, HEIGHT / 3, s3);
        StdDraw.text(WIDTH / 2, HEIGHT / 3 - 2, s4);
        StdDraw.show();
    }

    public static void drawString(String s) {
        //TODO: Take the string and display it in the center of the screen

        StdDraw.clear(Color.BLACK);
        Font f = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(f);
        StdDraw.setPenRadius(0.003);
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, s);

        StdDraw.show();
        StdDraw.enableDoubleBuffering();
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */

    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        char[] c = input.toCharArray();
        TETile[][] world;
                if (c[0] == 'Q') {        //quit
                    System.exit(0);
                } else if (c[0] == 'N') {
                    int value = Integer.parseInt(input.replaceAll("[^0-9]", ""));
                    long s = value;
                    int j;
                    for (j = 0; j < c.length && c[j] != 'S'; j++) ;  //after the loop c[j] is S
                    worldGenerator wg = new worldGenerator(s);
                    world = wg.worldGeneration();
                    j++;                    //c[j-1] is S
                    while (c[j - 1] != ':' || c[j] != 'Q') {
                        int[] t = worldGenerator.movePlayer(c[j], world);
                        ter.renderFrame(world);
                        if (worldGenerator.win) {
                            StdDraw.pause(500);
                            drawString("YOU WIN THE GAME!!");
                        }
                        if (j < c.length - 1) {
                            j++;
                        }
                    }
                    serial(input);
                    return world;
                } else if (c[0] == 'L') {
                    if (!checkSaved()) {
                        System.exit(0);
                    }
                    Game g1 = new Game();
                    try {
                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("C:\\Users\\YJC\\Desktop\\CS\\CS61B\\homework\\skeleton-sp18\\proj2\\game.txt"));
                        String input1 = (String) ois.readObject();
                        String inputLoad = input1.substring(0, input1.length() - 2);
                        ois.close();
                        g1.playWithInputString(inputLoad);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                } else {
                    drawString("Wrong letter entered. Please restart the game.");
                    StdDraw.pause(1500);
                    System.exit(0);
                }
        return null;
    }

    public static void serial (String s) {
        File game = new File("C:\\Users\\YJC\\Desktop\\CS\\CS61B\\homework\\skeleton-sp18\\proj2\\game.txt");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(game));
            oos.writeObject(s);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public TETile[][] resumeWorld(String s) {               //resume the tiles in the world to load
        TETile[][] world;
        char[] c = s.toCharArray();
        if (c[0] == 'N') {
            int value = Integer.parseInt(s.replaceAll("[^0-9]", ""));
            long v = value;
            int j;
            for (j = 0; j < c.length && c[j] != 'S'; j++) ;  //after the loop c[j] is S
            worldGenerator wg = new worldGenerator(v);
            world = wg.worldGeneration();
            j++;                    //c[j-1] is S
            while ((c[j - 1] != ':' || c[j] != 'Q') && j < c.length ) {
                int[] t = worldGenerator.movePlayer(c[j], world);
                ter.renderFrame(world);
                if (worldGenerator.win) {
                    StdDraw.pause(500);
                    drawString("YOU WIN THE GAME!!");
                }
                j++;
            }
            return world;
        }
        return null;
    }
    public boolean checkSaved() {
        File file = new File("C:\\Users\\YJC\\Desktop\\CS\\CS61B\\homework\\skeleton-sp18\\proj2\\game.txt");
        boolean empty = file.exists() && file.length() == 0;
        return !empty;
    }

    public static void main(String[] args) throws IOException {
        Game g = new Game();
//        g.playWithInputString("N123SSSSSD");
//        g.playWithInputString("N123SSSSSD:Q");
//        g.playWithInputString("L");
        g.playWithKeyboard();

    }

}

