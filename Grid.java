package Nonogram;

import basicgraphics.Sprite;
import basicgraphics.SpriteComponent;
import basicgraphics.*;
import basicgraphics.images.Picture;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Grid extends Sprite {

    public static Dimension puzzleDimensions;
    public static int boxSize;
    public static final int GRID_OFFSET = 200;
    public static Picture filled;
    public static Picture crossed;
    public static Picture empty;

    public GridBox[][] boxes;
    public Puzzle puzzle;
    public static Picture[] numberHints = new Picture[69];
    private List<GridBox> history = new ArrayList();
    private int historyIndex = 0;

    public Grid(SpriteComponent sc, String difficulty, Dimension customBoard) {
        super(sc);
        if (!difficulty.equals("custom")) {
            PuzzleLevels allLevels = new PuzzleLevels();
            ArrayList<Puzzle> puzzles = allLevels.levels.get(difficulty);
            int randNum = new Random().nextInt(puzzles.size());
            puzzle = puzzles.get(randNum);
        } else {
            puzzle = new Puzzle("blank", "custom", new int[customBoard.width][customBoard.height]);
        }
        puzzleDimensions = puzzle.getSize();
        boxSize = (Nonogram.GAME_DIMENSIONS - Grid.GRID_OFFSET) / Math.max(puzzleDimensions.width, puzzleDimensions.height);
        super.setPicture(drawGrid());
        super.setDrawingPriority(9);
        super.setX(0);
        super.setY(0);
        loadPictures();
        // creating the empty boxes
        boxes = new GridBox[puzzleDimensions.height][puzzleDimensions.width];
        for (int i = 0; i < puzzleDimensions.height; i++) {
            for (int j = 0; j < puzzleDimensions.width; j++) {
                GridBox box = new GridBox(sc, i, j);
                boxes[i][j] = box;
            }
        }
        drawHints(sc);
    }

    /**
     * Loads all images so that new images aren't loaded every time a box is
     * changed
     */
    private void loadPictures() {
        filled = new Picture("filled.png").resize((float) boxSize / 44);
        crossed = new Picture("crossed.png").resize((float) boxSize / 44);
        Image im = BasicFrame.createImage(boxSize, boxSize);
        Graphics g = im.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, boxSize - 1, boxSize - 1);
        empty = new Picture(im);
        for (int i = 0; i < numberHints.length; i++) {
            numberHints[i] = new Picture((i + 1) + ".png").resize((float) boxSize / 44);
        }

    }

    /**
     * Draws the grid outline based on the puzzles dimensions and game dimensions
     *
     * @return
     */
    private Picture drawGrid() {
        Image im = BasicFrame.createImage(Nonogram.GAME_DIMENSIONS, Nonogram.GAME_DIMENSIONS);
        Graphics g = im.getGraphics();
        for (int i = 0; i <= puzzleDimensions.width; i++) {
            g.setColor(i % 5 == 0 ? Color.BLACK : Color.LIGHT_GRAY);
            g.drawLine(i * boxSize + GRID_OFFSET, 0, i * boxSize + GRID_OFFSET, puzzleDimensions.height * boxSize + GRID_OFFSET);
        }

        for (int i = 0; i <= puzzleDimensions.height; i++) {
            g.setColor(i % 5 == 0 ? Color.BLACK : Color.LIGHT_GRAY);
            g.drawLine(0, i * boxSize + GRID_OFFSET, puzzleDimensions.width * boxSize + GRID_OFFSET, i * boxSize + GRID_OFFSET);
        }

        Picture p = new Picture(im);
        return p;
    }

    /**
     * Draws the hints and neatly positions them to the respective row/column
     *
     * @param sc
     */
    private void drawHints(SpriteComponent sc) {
        ArrayList<Integer>[] rowHints = puzzle.getRowHints();
        ArrayList<Integer>[] colHints = puzzle.getColumnHints();
        
        // row hints
        for (int i = 0; i < puzzleDimensions.height; i++) {
            ArrayList<Integer> rowHint = rowHints[i];
            for (int j = 0; j < rowHint.size(); j++) {
                NumberHint hint = new NumberHint(sc, rowHint.get(j), boxSize);
                int baseX = 0;
                int baseY = GRID_OFFSET + i * boxSize + 10;
                hint.setY(baseY);
                hint.setX(baseX + (j * 30));

            }
        }
        // column hints
        for (int i = 0; i < puzzleDimensions.width; i++) {
            ArrayList<Integer> colHint = colHints[i];
            for (int j = 0; j < colHint.size(); j++) {
                NumberHint hint = new NumberHint(sc, colHint.get(j), boxSize);
                int baseX = GRID_OFFSET + i * boxSize + 1;
                int baseY = 0;
                hint.setY(baseY + (j * 30));
                hint.setX(baseX);
            }
        }
    }

    /**
     * Loops through all boxes and changes them to the correct solution
     */
    public void autoSolve() {
        int[][] correct = puzzle.getCorrect();
        for (int i = 0; i < puzzleDimensions.height; i++) {
            for (int j = 0; j < puzzleDimensions.width; j++) {
                if (correct[i][j] == 1 && !boxes[i][j].getStatus().equals("filled")) {
                    boxes[i][j].changeFilled();
                } else {
                    boxes[i][j].changeCrossed();
                }
            }
        }
    }

    /**
     * Generates the data when making a custom puzzle. Instead of manually
     * typing the puzzle data, you can draw it on the creation screen and
     * generate the data this way
     *
     * @return
     */
    public String generateCustomPuzzle() {
        String output = "";
        for (int i = 0; i < puzzleDimensions.height; i++) {
            output += "{";
            for (int j = 0; j < puzzleDimensions.width; j++) {
                if (boxes[i][j].getStatus().equals("filled")) {
                    output += "1";
                } else {
                    output += "0";
                }
                if (j < puzzleDimensions.width - 1) {
                    output += ", ";
                }
            }
            output += "},\n";
        }
        return output;
    }

    /**
     * Reveals the correct status for a given box
     *
     * @param col
     * @param row
     */
    public void showHint(int col, int row) {
        if (puzzle.getCorrect()[col][row] == 1) {
            if (boxes[col][row].getStatus().equals(GridBox.FILLED)) {
                return;
            }
            boxes[col][row].changeFilled();
        } else {
            if (boxes[col][row].getStatus().equals(GridBox.CROSSED)) {
                return;
            }
            boxes[col][row].changeCrossed();
        }
    }

    /**
     * Resets the board to all empty boxes
     */
    public void restart() {
        for (int i = 0; i < puzzleDimensions.width; i++) {
            for (int j = 0; j < puzzleDimensions.height; j++) {
                GridBox current = boxes[i][j];
                if (!current.getStatus().equals(GridBox.EMPTY)) {
                    if (current.getStatus().equals(GridBox.FILLED)) {
                        current.changeFilled();
                    } else {
                        current.changeCrossed();
                    }
                }
            }
        }
    }

    /**
     * Saves the action the user made so that it can be undone
     *
     * @param pressed The box that was pressed
     */
    public void recordAction(GridBox pressed) {
        if (historyIndex < history.size() - 1) {
            history = history.subList(0, historyIndex + 1);
        }
        history.add(pressed);
        historyIndex = history.size() - 1;
    }

    /**
     * Undo the the move made
     *
     */
    public void undo() {
        if (historyIndex == -1) {
            return;
        }
        GridBox current = history.get(historyIndex);
        current.setToPreviousHistory();
        historyIndex--;
    }

    /**
     * Checks if the current row and column matches the given hints for that row
     * or column. If it matches, empty boxes in that row or column are crossed
     * out
     *
     * @param row
     * @param col
     */
    public void checkAutoFill(int row, int col) {
        ArrayList<Integer> rowHints = puzzle.getRowHints()[row];
        ArrayList<Integer> colHints = puzzle.getColumnHints()[col];
        ArrayList<Integer> userRow = new ArrayList<>();
        ArrayList<Integer> userCol = new ArrayList<>();

        int count = 0;
        // gets the user's row data
        for (int i = 0; i <= puzzleDimensions.height; i++) {
            if (i != puzzleDimensions.height && boxes[i][col].getStatus().equals(GridBox.FILLED)) {
                count++;
            } else if (count > 0) {
                userCol.add(count);
                count = 0;
            }
        }
        // gets the user's column data
        for (int i = 0; i <= puzzleDimensions.width; i++) {
            if (i != puzzleDimensions.width && boxes[row][i].getStatus().equals(GridBox.FILLED)) {
                count++;
            } else if (count > 0) {
                userRow.add(count);
                count = 0;
            }
        }

        // compare the user row data to the correct row data
        // it doesn't have to be in correct order, it just has to be the correct number of filled boxes
        if (userRow.equals(rowHints)) {
            for (int i = 0; i < puzzleDimensions.width; i++) {
                if (boxes[row][i].getStatus().equals(GridBox.EMPTY)) {
                    boxes[row][i].changeCrossed();
                }
            }
        }
        //compare the user column data to the correct column data
        if (userCol.equals(colHints)) {
            for (int i = 0; i < puzzleDimensions.height; i++) {
                if (boxes[i][col].getStatus().equals(GridBox.EMPTY)) {
                    boxes[i][col].changeCrossed();
                }
            }
        }
    }
    
    /**
     * Sets all the box sprites active to false in preparation for a new board
     */
    public void reset() {
        for(int i = 0; i < boxes.length; i++) {
            for(int j = 0; j < boxes[0].length; j++) {
                boxes[i][j].setActive(false);
            }
        }
    }

}
