package gui.generic;

import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 * Représente une flèche javafx.
 */
public class Flèche extends Path
{

    private static final double defaultArrowHeadSize = 10.0;



    /**
     * Construit une flèche javafx.
     * @param startX position X de départ de la flèche.
     * @param startY position Y de départ de la flèche.
     * @param endX
     * @param endY
     * @param c
     * @param arrowHeadSize
     */
    public Flèche(double startX, double startY, double endX, double endY, Color c, double arrowHeadSize)
    {
        super();
        strokeProperty().bind(fillProperty());
        this.setFill(c);

        //Line
        this.getElements().add(new MoveTo(startX, startY));
        this.getElements().add(new LineTo(endX, endY));

        //ArrowHead
        double angle = Math.atan2((endY - startY), (endX - startX)) - Math.PI / 2.0;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        //point1
        double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
        double y1 = (- 1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;
        //point2
        double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
        double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;

        this.getElements().add(new LineTo(x1, y1));
        this.getElements().add(new LineTo(x2, y2));
        this.getElements().add(new LineTo(endX, endY));
    }

    public Flèche(double startX, double startY, double endX, double endY, Color c){
        this(startX, startY, endX, endY, c, defaultArrowHeadSize);
    }

    public Flèche(double startX, double startY, double endX, double endY){
        this(startX, startY, endX, endY, Color.BLACK, defaultArrowHeadSize);
    }
}
