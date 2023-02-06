package Nonogram;

import static Nonogram.Nonogram.GAME_DIMENSIONS;
import basicgraphics.SpriteComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Browser {

    private List<Puzzle> allItems = new ArrayList();
    private List<Puzzle> visibleItems = new ArrayList();
    private int index = 0;
    private final int numItemsVisible = 3;

    public Browser() {
        PuzzleLevels puzzleLevels = new PuzzleLevels();
        this.allItems = puzzleLevels.getAllPuzzles();
    }

    /**
     * Scroll through the available puzzles
     * @param direction The direction to scroll (up/down)
     * @return a new SpriteComponent (to clear previously rendered items)
     */
    public SpriteComponent scroll(String direction) {
        if (direction.equals("up")) {
            if (index > 0) {
                index--;
            }
        } else if (direction.equals("down")) {
            if (index < allItems.size() - numItemsVisible) {
                index++;
            }
        }
        visibleItems = allItems.subList(index, index + numItemsVisible);
        SpriteComponent sc = createNewSC();
        drawVisibleItems(sc);
        return sc;
    }

    /**
     * Gets the name of the puzzle that is in the middle of the screen
     * @return Name of puzzle
     */
    public String getHighlightedPuzzleName() {
        Puzzle puz = visibleItems.get(numItemsVisible / 2);
        return puz.getName();
    }
    
    public String getHighlightedPuzzleDimensions() {
        Puzzle puz = visibleItems.get(numItemsVisible / 2);
        return "("+puz.getSize().width + "x" + puz.getSize().height + ")";
    }

    /**
     * Creates a fresh SpriteComponent
     * @return SpriteComponent
     */
    private SpriteComponent createNewSC() {
        SpriteComponent sc = new SpriteComponent() {
            @Override
            public void paintBackground(Graphics g) {
                Dimension d = getSize();
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, d.width, d.height);
            }
        };
        sc.setPreferredSize(new Dimension(GAME_DIMENSIONS, MiniPicture.height));
        return sc;
    }
    
    /**
     * Draws the items that are visible in the browser given the current index
     * @param sc 
     */
    private void drawVisibleItems(SpriteComponent sc) {
        for (int i = 0; i < numItemsVisible; i++) {
            BrowserItem item = new BrowserItem(sc, visibleItems.get(i));
            int posX;
            int middlePos = numItemsVisible / 2;
            if (i < middlePos) {
                posX = -MiniPicture.height / 2;
            } else if (i > middlePos) {
                posX = GAME_DIMENSIONS - (MiniPicture.height / 2);
            } else {
                posX = (GAME_DIMENSIONS / 2) - (MiniPicture.height / 2);
            }
            item.setX(posX);
        }
    }

}
