package Nonogram;

import basicgraphics.*;
import basicgraphics.images.Picture;
import java.awt.Dimension;

public class MiniPicture extends Sprite {
    
    public static int height = 400;

    public MiniPicture(SpriteComponent sc, Picture pic) {
        super(sc);
        setDrawingPriority(10000000);
        setPicture(pic);
        setX(0);
        setY(0);
    }
}
