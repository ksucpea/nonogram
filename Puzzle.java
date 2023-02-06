package Nonogram;

import basicgraphics.BasicFrame;
import basicgraphics.images.Picture;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

public class Puzzle {

    private final Dimension gridDimensions;
    private String difficulty;
    private int[][] correct;
    private String name;
    private ArrayList[] rowHints;
    private ArrayList[] colHints;

    public Puzzle(String name, String difficulty, int[][] data) {
        this.difficulty = difficulty;
        int width = data[0].length;
        int height = data.length;
        gridDimensions = new Dimension(width, height);
        System.out.println(gridDimensions.toString());
        this.name = name;
        correct = data;
        rowHints = new ArrayList[gridDimensions.height];
        colHints = new ArrayList[gridDimensions.width];

        
        // creates the column hints
        for (int i = 0; i < gridDimensions.width; i++) {
            int count = 0;
            ArrayList<Integer> hints = new ArrayList();
            for (int j = 0; j <= gridDimensions.height; j++) {
                if (j != gridDimensions.height && correct[j][i] == 1) {
                    count++;
                } else if (count > 0) {
                    hints.add(count);
                    count = 0;
                }
            }
            colHints[i] = hints;
        }

        // creates the row hints
        for (int i = 0; i < gridDimensions.height; i++) {
            int count = 0;
            ArrayList<Integer> hints = new ArrayList();
            for (int j = 0; j <= gridDimensions.width; j++) {
                if (j != gridDimensions.width && correct[i][j] == 1) {
                    count++;
                } else if (count > 0) {
                    hints.add(count);
                    count = 0;
                }
            }
            rowHints[i] = hints;
        }
        
    }

    /**
     * Checks if the player's board is equal to the solution
     *
     * @param boxes the players boxes
     * @return if the board is correct
     */
    public Boolean isEqual(GridBox[][] boxes) {
        for (int i = 0; i < gridDimensions.height; i++) {
            for (int j = 0; j < gridDimensions.width; j++) {
                int correctStatus = correct[i][j];
                int userBoxStatus = boxes[i][j].getStatus().equals(GridBox.FILLED) ? 1 : 0;
                if (correctStatus != userBoxStatus) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Get the correct solution
     *
     * @return
     */
    public int[][] getCorrect() {
        return correct;
    }

    /**
     * Get the size of the puzzle (10, 15, 20)
     *
     * @return
     */
    public Dimension getSize() {
        return gridDimensions;
    }

    /**
     * Get the name of the puzzle
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Get the difficulty of the puzzle
     *
     * @return
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Get the hints for all rows
     *
     * @return An array of ArrayLists, the index contains the hints for that
     * row's index
     */
    public ArrayList[] getRowHints() {
        return rowHints;
    }

    /**
     * Get the hints for all columns
     *
     * @return An array of ArrayLists, the index contains the hints for that
     * column's index
     */
    public ArrayList[] getColumnHints() {
        return colHints;
    }

    /**
     * Generates a small and neat version of the correct puzzle for better
     * viewing
     *
     * @return Picture of the completed puzzle
     */
    public Picture getCompletedPicture() {
        Picture pic;
        final int miniboxHeight = MiniPicture.height / (gridDimensions.width > gridDimensions.height ? gridDimensions.width : gridDimensions.height);
        Image im = BasicFrame.createImage(MiniPicture.height, MiniPicture.height);
        Graphics g = im.getGraphics();
        g.setColor(Color.RED);
        pic = new Picture(im);
        for (int i = 0; i < gridDimensions.width; i++) {
            for (int j = 0; j < gridDimensions.height; j++) {
                if (correct[j][i] == 1) {
                    g.setColor(Color.BLACK);
                    g.fillRect(i * miniboxHeight, j * miniboxHeight, miniboxHeight, miniboxHeight);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(i * miniboxHeight, j * miniboxHeight, miniboxHeight, miniboxHeight);
                }
            }
        }
        return pic;
    }

    public String toString() {
        String visualization = "";
        for (int i = 0; i < gridDimensions.height; i++) {
            for (int j = 0; j < gridDimensions.width; j++) {
                visualization += (correct[i][j] == 1 ? "X" : "O");
            }
            visualization += "\n";
        }
        return "Puzzle \"" + name + "\", " + difficulty + " difficulty\n" + visualization;
    }

}
