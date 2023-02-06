package Nonogram;

import basicgraphics.*;
import basicgraphics.images.Picture;

public class NumberHint extends Sprite {
    
    public NumberHint(SpriteComponent sc, int num, int size) {
        super(sc);
        Picture pic = Grid.numberHints[num - 1];
        pic.transparentWhite();
        setDrawingPriority(1000);
        setPicture(pic);
        setX(0);
        setY(0);
    }
}
