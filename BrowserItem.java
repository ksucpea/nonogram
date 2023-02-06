package Nonogram;

import basicgraphics.Sprite;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;

public class BrowserItem extends Sprite {

    public BrowserItem(SpriteComponent sc, Puzzle puzzle) {
        super(sc);
        Picture pic = puzzle.getCompletedPicture();
        setDrawingPriority(1000);
        setPicture(pic);
    }
}
