package Nonogram;

import basicgraphics.*;
import basicgraphics.images.Picture;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GridBox extends Sprite {

    public final static String FILLED = "filled";
    public final static String CROSSED = "crossed";
    public final static String EMPTY = "empty";
    private final int colNum;
    private final int rowNum;
    private String status = EMPTY;
    private ArrayList<String> history = new ArrayList<String>();
    private int historyIndex = 0;

    public GridBox(SpriteComponent sc, int r, int c) {
        super(sc);
        this.colNum = c;
        this.rowNum = r;
        setPicture(makeBoxPicture(EMPTY));
        setDrawingPriority(10000000);
        int x = Grid.GRID_OFFSET + colNum * Grid.boxSize + 1;
        int y = Grid.GRID_OFFSET + rowNum * Grid.boxSize + 1;
        setX(x);
        setY(y);
        history.add(EMPTY);
    }

    /**
     * Changes the box to filled if empty, or back to empty if it was already filled
     */
    public void changeFilled() {
        String newStatus = status.equals(FILLED) ? EMPTY : FILLED;
        status = newStatus;
        history.add(newStatus);
        setPicture(makeBoxPicture(newStatus));
        historyIndex++;
    }

    /**
     * Changes the box to crossed if empty, or back to empty if it was already crossed
     */
    public void changeCrossed() {
        String newStatus = status.equals(CROSSED) ? EMPTY : CROSSED;
        status = newStatus;
        history.add(newStatus);
        setPicture(makeBoxPicture(newStatus));
        historyIndex++;
    }

    /**
     * Gets the correct picture based on the status of the box
     * @param boxType if it is filled, crossed, or empty
     * @return 
     */
    private Picture makeBoxPicture(String boxType) {
        Picture pic;
        if (boxType.equals(FILLED)) {
            pic = Grid.filled;
        } else if (boxType.equals(CROSSED)) {
            pic = Grid.crossed;
        } else {
            pic = Grid.empty;
        }
        return pic;
    }

    /**
     * Reverts the box status to the previous status when there is an undo
     */
    public void setToPreviousHistory() {
        historyIndex--;
        String lastStatus = history.get(historyIndex);
        switch (lastStatus) {
            case EMPTY:
                if (status.equals(CROSSED)) {
                    changeCrossed();
                } else if (status.equals(FILLED)) {
                    changeFilled();
                }
                break;
            case CROSSED:
                changeCrossed();
                break;
            case FILLED:
                changeFilled();
                break;
            default:
                break;
        }

    }

    /**
     * Get the column number of this box
     * @return 
     */
    public int getCol() {
        return colNum;
    }
    
    /** Get the row number of this box
     * 
     * @return 
     */
    public int getRow() {
        return rowNum;
    }

    /** Get the current status of this box
     * 
     * @return 
     */
    public String getStatus() {
        return status;
    }
}
