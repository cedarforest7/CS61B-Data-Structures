package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private static Random rand;
    private static Random rand1;
    private static int seed;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bucks!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        seed = Integer.parseInt(args[0]);
        rand = new Random(seed);
        MemoryGame game = new MemoryGame(40, 40);
        game.startGame();
    }

    public MemoryGame(int width, int height) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        char[] c = new char[n];
        int ind;
        for (int i = 0; i < n; i++) {
            ind = rand.nextInt(26);
            c[i] = CHARACTERS[ind];
        }
        String s = String.valueOf(c);
        return s;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
//        first clears the canvas, draws everything necessary for the next frame, and then shows the canvas.
//        char[] c1 = s.toCharArray();

        StdDraw.clear(Color.BLACK);
        Font f = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(f);
        StdDraw.setPenRadius(0.003);
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        StdDraw.text(this.width/2, this.height/2, s);

        if (!gameOver) {
            StdDraw.line(0, this.height - 2, 40, this.height - 2);
            Font f2 = new Font("Monaco",Font.PLAIN, 20);
            StdDraw.setFont(f2);
            String s0 = "Round:" + String.valueOf(round);
            StdDraw.text(3, this.height - 1, s0);
            String st;
            if (playerTurn) {
                st = "Type!";
            } else {
                st = "Watch!";
            }
            StdDraw.text(this.width/2,this.height - 1, st);
            rand1 = new Random(seed + round);
            int a = rand1.nextInt(7);
            StdDraw.text(this.width - 6,this.height - 1, ENCOURAGEMENT[a]);
        }

        StdDraw.show();
        StdDraw.enableDoubleBuffering();
        //TODO: If game is not over, display relevant game information at the top of the screen
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        String[] s1 = letters.split("");
        for (int i = 0; i < s1.length; i++) {
            drawFrame(s1[i]);
            StdDraw.pause(1000);
            drawFrame("");
            StdDraw.show();
            StdDraw.pause(500);
        }

    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        playerTurn = true;
        String s2 = "";
        drawFrame("Please Type");
        while (s2.length() != n) {
            if (StdDraw.hasNextKeyTyped()) {
                s2 = s2 + String.valueOf(StdDraw.nextKeyTyped());
                drawFrame(s2);
            }
        }
        playerTurn = false;
        return s2;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        String msg1, msg2, typed;
        round = 1;
        playerTurn = false;
        gameOver = false;
        //TODO: Establish Game loop
        while (true) {
            msg1 = "Rounds:" + String.valueOf(round);
            drawFrame(msg1);
            msg2 = generateRandomString(round);
            flashSequence(msg2);
            typed = solicitNCharsInput(msg2.length());
            if (typed.equals(msg2)) {
                round++;
                StdDraw.pause(1000);
            } else {
                gameOver = true;
                String endMsg = "Game Over! You made it to round:" + String.valueOf(round);
                drawFrame(endMsg);
                return;
            }
        }

    }

}
