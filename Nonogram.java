package Nonogram;

import basicgraphics.BasicContainer;
import basicgraphics.BasicFrame;
import basicgraphics.Clock;
import basicgraphics.SpriteComponent;
import basicgraphics.Task;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Nonogram {

    final BasicFrame bf = new BasicFrame("Nonogram");
    final Container content = bf.getContentPane();
    final CardLayout cards = new CardLayout();
    final static int GAME_DIMENSIONS = 900;
    BasicContainer gameContainer = new BasicContainer();

    public SpriteComponent sc;
    public static Grid grid;
    public static Point mousePos;
    public static boolean mouseIsDown = false;

    private int startX, startY, pressType;
    private static boolean autoSolved = false;
    private static int hints = 3;
    private static int timer = 0;
    private static int startTime;
    private static int endTime;

    /**
     * Loads the start screen
     */
    public void run() {
        content.setLayout(cards);
        BasicContainer start = new BasicContainer();
        start.setStringLayout(new String[][]{{"nonogram", "nonogram", "nonogram"}, {"easy", "medium", "hard"}, {"browse", "create", "tut"}});
        JButton tutBtn = new JButton("How to play");
        JButton easyBtn = new JButton("Random easy puzzle");
        JButton hardBtn = new JButton("Random hard puzzle");
        JButton mediumBtn = new JButton("Random medium puzzle");
        JButton createBtn = new JButton("Create custom puzzle");
        JButton browseBtn = new JButton("Browse all puzzles");
        easyBtn.setBackground(Color.BLACK);
        easyBtn.setForeground(Color.WHITE);
        easyBtn.setFocusPainted(false);
        easyBtn.setBorderPainted(false);
        tutBtn.setBackground(Color.BLACK);
        tutBtn.setForeground(Color.WHITE);
        tutBtn.setFocusPainted(false);
        tutBtn.setBorderPainted(false);
        hardBtn.setBackground(Color.BLACK);
        hardBtn.setForeground(Color.WHITE);
        hardBtn.setFocusPainted(false);
        hardBtn.setBorderPainted(false);
        mediumBtn.setBackground(Color.BLACK);
        mediumBtn.setForeground(Color.WHITE);
        mediumBtn.setFocusPainted(false);
        mediumBtn.setBorderPainted(false);
        createBtn.setBackground(Color.BLACK);
        createBtn.setForeground(Color.WHITE);
        createBtn.setFocusPainted(false);
        createBtn.setBorderPainted(false);
        browseBtn.setBackground(Color.BLACK);
        browseBtn.setForeground(Color.WHITE);
        browseBtn.setFocusPainted(false);
        browseBtn.setBorderPainted(false);
        start.add("hard", hardBtn);
        start.add("tut", tutBtn);
        start.add("easy", easyBtn);
        start.add("medium", mediumBtn);
        start.add("create", createBtn);
        start.add("browse", browseBtn);
        content.add(start, "Splash");
        content.add(gameContainer, "Game");

        SpriteComponent nonoSplash = new SpriteComponent() {
            @Override
            public void paintBackground(Graphics g) {
                Color[] colors = new Color[]{Color.BLACK, new Color(20, 20, 20), new Color(40, 40, 40), new Color(60, 60, 60), new Color(80, 80, 80), new Color(100, 100, 100)};
                Dimension d = getSize();
                //g.setColor(Color.white);
                //g.fillRect(0, 0, d.width, d.height);
                Random rand = new Random();

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(0, 100, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(0, 120, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(0, 140, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(0, 160, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(0, 180, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(20, 120, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(40, 140, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(60, 100, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(60, 120, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(60, 140, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(60, 160, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(60, 180, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(100, 120, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(100, 140, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(100, 160, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(120, 100, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(120, 180, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(140, 100, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(140, 180, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(160, 120, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(160, 140, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(160, 160, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(200, 100, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(200, 120, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(200, 140, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(200, 160, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(200, 180, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(220, 120, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(240, 140, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(260, 100, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(260, 120, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(260, 140, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(260, 160, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(260, 180, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(300, 120, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(300, 140, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(300, 160, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(320, 100, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(320, 180, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(340, 100, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(340, 180, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(360, 120, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(360, 140, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(360, 160, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(400, 120, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(400, 140, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(400, 160, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(420, 100, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(420, 180, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(440, 100, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(440, 140, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(440, 180, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(460, 100, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(460, 140, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(460, 160, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(460, 180, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(500, 100, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(500, 120, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(500, 140, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(500, 160, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(500, 180, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(520, 100, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(520, 140, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(540, 100, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(540, 140, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(540, 160, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(560, 120, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(560, 180, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(600, 120, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(600, 140, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(600, 160, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(600, 180, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(620, 100, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(620, 140, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(640, 100, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(640, 140, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(660, 120, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(660, 140, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(660, 160, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(660, 180, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(700, 100, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(700, 120, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(700, 140, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(700, 160, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(700, 180, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(720, 120, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(740, 140, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(760, 120, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(780, 100, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(780, 120, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(780, 140, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(780, 160, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(780, 180, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(820, 120, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(820, 180, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(840, 100, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(840, 140, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(840, 180, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(860, 100, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(860, 140, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(860, 180, 20, 20);

                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(880, 100, 20, 20);
                g.setColor(colors[rand.nextInt(colors.length)]);
                g.fillRect(880, 160, 20, 20);

            }
        };
        nonoSplash.setPreferredSize(new Dimension(900, 300));
        start.add("nonogram", nonoSplash);
        easyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame("easy", null);
            }
        });
        hardBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame("hard", null);
            }
        });
        mediumBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame("medium", null);
            }
        });
        tutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTutorial();
            }
        });

        browseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startBrowser();
            }
        });
        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField width = new JTextField();
                JTextField height = new JTextField();
                String message = "Enter a board size";
                int pane = JOptionPane.showOptionDialog(sc, new Object[]{message, "width", width, "height", height}, "Create custom puzzle", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (pane == JOptionPane.OK_OPTION) {
                    try {
                        int puzzleWidth = Integer.parseInt(width.getText());
                        int puzzleHeight = Integer.parseInt(height.getText());
                        startGame("custom", new Dimension(puzzleHeight, puzzleWidth));
                    } catch (Exception ex) {
                    }
                }
            }
        });

        Clock.addTask(new Task() {
            @Override
            public void run() {
                timer++;
            }
        });

        bf.show();

    }

    /**
     * Sets up the tutorial screen
     */
    private void showTutorial() {
        BasicContainer tutorial = new BasicContainer();
        content.add(tutorial, "tutorial");
        cards.show(content, "tutorial");
        String[][] layout = {{"text"}, {"back"}};
        tutorial.setStringLayout(layout);
        JTextArea text = new JTextArea("Objective: Cover the grid in filled/crossed squares"
                + "\n\n- Left click to fill and left click again to set back to empty, Right click to cross and right click again to set back to empty."
                + "\n- You can drag the mouse to fill in multiple squares at once"
                + "\n- Look at the key binds listed at the top of the game for extra features"
                + "\n\nHow to play: Hints will appear to the left of rows and at the top of columns indicating the number of filled squares present in that row/column.\n"
                + "- Hints that appear sequentially have to be separated by at least one crossed square from other black squares in that row/column\n"
                + "- For example, if the row hint says '10 2' there cannot be 12 filled squares in a row, there has to be a separate set of 10 filled and 2 filled squares in that row");
        tutorial.add("text", text);
        bf.show();
    }

    /**
     * Starts the browser
     */
    public void startBrowser() {
        Browser browser = new Browser();
        sc = browser.scroll("up");
        showBrowser(browser);
    }

    /**
     * Sets up the browser
     *
     * @param browser
     */
    private void showBrowser(Browser browser) {
        content.remove(gameContainer);
        gameContainer = new BasicContainer();
        content.add(gameContainer, "Game");
        cards.show(content, "Game");
        String[][] layout = {{"title", "title"}, {"browser", "browser"}, {"back", "scroll"}};
        gameContainer.setStringLayout(layout);
        gameContainer.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                    sc = browser.scroll("up");
                    showBrowser(browser);
                } else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                    sc = browser.scroll("down");
                    showBrowser(browser);
                }
            }
        });
        gameContainer.add("browser", sc);
        JLabel highlightedPuzzle = new JLabel(browser.getHighlightedPuzzleName() + " " + browser.getHighlightedPuzzleDimensions());
        gameContainer.add("title", highlightedPuzzle);
        JButton back = new JButton("Back to start screen");
        gameContainer.add("back", back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cards.show(content, "Splash");
            }
        });
        JLabel scroll = new JLabel("Use arrow keys to scroll < left and right >");
        gameContainer.add("scroll", scroll);
        bf.show();
        gameContainer.requestFocus();
    }

    /**
     * Sets up the game given a difficulty. If you're creating a custom board
     * the dimension has to be specified
     *
     * @param difficulty
     * @param customDimension
     */
    public void startGame(String difficulty, Dimension customDimension) {
        boolean customBoard = difficulty.equals("custom");
        startTime = timer;
        content.remove(gameContainer);
        gameContainer = new BasicContainer();
        content.add(gameContainer, "Game");
        cards.show(content, "Game");
        gameContainer.removeAll();
        sc = new SpriteComponent() {
            @Override
            public void paintBackground(Graphics g) {
                Dimension d = getSize();
                g.setColor(Color.white);
                g.fillRect(0, 0, d.width, d.height);
                final int NUM_STARS = 30;
                Random rand = new Random();
                rand.setSeed(0);
                g.setColor(Color.white);
                for (int i = 0; i < NUM_STARS; i++) {
                    int diameter = rand.nextInt(5) + 1;
                    int xpos = rand.nextInt(d.width);
                    int ypos = rand.nextInt(d.height);
                    g.fillOval(xpos, ypos, diameter, diameter);
                }
            }
        };
        sc.setPreferredSize(new Dimension(GAME_DIMENSIONS, GAME_DIMENSIONS));
        String[][] layout = {{"keybinds"}, {"hint"}, {"solve"}, {"create"}, {"undo"}, {"restart"}, {"board"}};
        gameContainer.setStringLayout(layout);
        gameContainer.add("board", sc);
        JLabel keybinds = new JLabel("Keybinds");
        JLabel hint = new JLabel("H - Reveal a hint");
        JLabel solve = new JLabel("S - Automatically solve the puzzle, press S again to end the game");
        JLabel create = new JLabel("C -  Create puzzle data from current board");
        JLabel undo = new JLabel("Z - Undo");
        JLabel restart = new JLabel("R - Restart");
        gameContainer.add("hint", hint);
        gameContainer.add("solve", solve);
        gameContainer.add("create", create);
        gameContainer.add("undo", undo);
        gameContainer.add("restart", restart);
        gameContainer.add("keybinds", keybinds);

        gameContainer.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                switch (ke.getKeyCode()) {
                    case KeyEvent.VK_H:
                        if (customBoard == true) {
                            return;
                        }
                        if (hints == 0) {
                            JLabel message = new JLabel("You have no more hints left");
                            JOptionPane.showMessageDialog(sc, message);
                            return;
                        }
                        JTextField row = new JTextField();
                        JTextField col = new JTextField();
                        String message = "Type the row and column of the box you want a hint for,\nor leave the fields blank to get a random hint.";
                        String rowText = "Row #";
                        String colText = "Column #";
                        int pane = JOptionPane.showOptionDialog(sc, new Object[]{message, rowText, row, colText, col}, "Hint", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                        if (pane == JOptionPane.OK_OPTION) {
                            Random rand = new Random();
                            int rowNum = !row.getText().equals("") ? Integer.parseInt(row.getText()) : rand.nextInt(grid.puzzle.getSize().height);
                            int colNum = !row.getText().equals("") ? Integer.parseInt(col.getText()) : rand.nextInt(grid.puzzle.getSize().width);
                            grid.showHint(colNum, rowNum);
                            hints--;
                        }
                        break;
                    case KeyEvent.VK_S:
                        if (customBoard == true) {
                            return;
                        }
                        if (autoSolved == false) {
                            grid.autoSolve();
                            autoSolved = true;
                        } else if (autoSolved == true) {
                            endGame();
                        }
                        break;
                    case KeyEvent.VK_C:
                        JOptionPane.showMessageDialog(sc, new JTextArea(grid.generateCustomPuzzle()));
                        break;
                    case KeyEvent.VK_R:
                        grid.restart();
                        break;
                    case KeyEvent.VK_Z:
                        grid.undo();
                        break;
                    default:
                        break;
                }
            }
        });
        sc.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseIsDown = true;
                startX = 0;
                startY = 0;
                pressType = e.getButton();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseIsDown = false;
                if (grid.puzzle.isEqual(grid.boxes)) {
                    endGame();
                }
            }
        });
        Task dragCheck = new Task() {
            @Override
            public void run() {
                if (!mouseIsDown) { // if the mouse isn't being dragged don't do anything
                    return;
                }
                try {
                    if (Math.abs(mousePos.x - startX) > (Grid.boxSize / 2)) { // checks if the total mouse movement goes outside the current box X position
                        startX = mousePos.x - ((mousePos.x - Grid.GRID_OFFSET) % Grid.boxSize) + (Grid.boxSize / 2); // update the current box the mouse is inside
                        startY = mousePos.y - ((mousePos.y - Grid.GRID_OFFSET) % Grid.boxSize) + (Grid.boxSize / 2);
                        int boxRow = (startY - Grid.GRID_OFFSET) / Grid.boxSize;
                        int boxCol = (mousePos.x - Grid.GRID_OFFSET) / Grid.boxSize;
                        GridBox pressed = grid.boxes[boxRow][boxCol];
                        if (pressType == MouseEvent.BUTTON1) {
                            pressed.changeFilled();
                            grid.recordAction(pressed);
                            if (customBoard == false) {
                                grid.checkAutoFill(boxRow, boxCol);
                            }
                        } else if (pressType == MouseEvent.BUTTON3) {
                            pressed.changeCrossed();
                            grid.recordAction(pressed);
                        }
                    } else if (Math.abs(mousePos.y - startY) > (Grid.boxSize / 2)) { // checks if the total mouse movement goes outside the current box Y position
                        startY = mousePos.y - ((mousePos.y - Grid.GRID_OFFSET) % Grid.boxSize) + (Grid.boxSize / 2); // update the current box the mouse is inside
                        startX = mousePos.x - ((mousePos.x - Grid.GRID_OFFSET) % Grid.boxSize) + (Grid.boxSize / 2);
                        int boxRow = (mousePos.y - Grid.GRID_OFFSET) / Grid.boxSize;
                        int boxCol = (startX - Grid.GRID_OFFSET) / Grid.boxSize;
                        GridBox pressed = grid.boxes[boxRow][boxCol];
                        if (pressType == MouseEvent.BUTTON1) { // left mouse fill
                            pressed.changeFilled();
                            grid.recordAction(pressed);
                            if (customBoard == false) {
                                grid.checkAutoFill(boxRow, boxCol);
                            }
                        } else if (pressType == MouseEvent.BUTTON3) { // right mouse cross
                            pressed.changeCrossed();
                            grid.recordAction(pressed);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("mouse is off the screen");
                }

            }

        };
        Task updateMousePos = new Task() {
            @Override
            public void run() {
                mousePos = sc.getMousePosition();
            }
        };
        Clock.addTask(sc.moveSprites());
        Clock.addTask(updateMousePos);
        Clock.addTask(dragCheck);
        Clock.start(1);
        grid = new Grid(sc, difficulty, customDimension);
        autoSolved = false;
        hints = 3;
        bf.show();
        gameContainer.requestFocus();
    }

    /**
     * Sets up the end screen
     */
    public void endGame() {
        grid.reset();
        endTime = timer;
        cards.show(content, "End");
        final BasicContainer game = new BasicContainer();
        content.add(game, "thing");
        final SpriteComponent ec = new SpriteComponent() {
            @Override
            public void paintBackground(Graphics g) {
                Dimension d = getSize();
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, d.width, d.height);
            }
        };
        ec.setPreferredSize(new Dimension(MiniPicture.height, MiniPicture.height));
        String[][] layout = {{"title", "title"}, {"minipicture", "minipicture", "minipicture"}, {"tryagain", "start"}};
        game.setStringLayout(layout);
        JButton startBtn = new JButton("Back to start screen");
        JButton tryagainBtn = new JButton("Try another random " + grid.puzzle.getDifficulty() + " puzzle");
        game.add("minipicture", ec);
        game.add("tryagain", tryagainBtn);
        game.add("start", startBtn);
        game.add("title", new JLabel("Congratulations, you completed this puzzle! (" + grid.puzzle.getName() + ") in " + ((endTime - startTime) / 50) + " seconds"));
        tryagainBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cards.show(content, "Game");
                startGame(grid.puzzle.getDifficulty(), null);
            }
        });
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cards.show(content, "Splash");
            }
        });
        MiniPicture completed = new MiniPicture(ec, grid.puzzle.getCompletedPicture());
        Clock.addTask(ec.moveSprites());
        cards.show(content, "thing");
        game.requestFocus();
    }

    public static void main(String[] args) {
        Nonogram game = new Nonogram();
        game.run();
    }
}
