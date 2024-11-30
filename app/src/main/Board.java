package app.src.main;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Board {
    public static final int SQUARE_SIZE = 90;
    public static final int NUM_FILES = 8;
    public static final int NUM_RANKS = 8;
    public static final int WHITE = 1;
    public static final int BLACK = 0;

    public static void setUp() {
        createFrame();
    }

    private static void createFrame() {
        JFrame frame = new JFrame("Abir's chess engine");
        // shut down program when the window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // make board appear on screen
        frame.setVisible(true);
        frame.setResizable(false);

        Panel panel = new Panel();
        panel.setPreferredSize(new Dimension(720, 720));

        frame.add(panel);
        // adjust the frame's size to the panel's size to the panel
        frame.pack();

    }

}
