package q2;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

/**
 * Class DrawTriangle
 * <p>An equilateral triangle using a rubber banding technique.  
 * The triangle size and orientation are determined by a mouse drag.  
 * Use the original mouse press location as the center, and the dragged 
 * position of the mouse as one of the corners.
 * </p>
 *
 * @author Zhang Yuanyuan
 * @version 2017-11-30
 */
public class DrawTriangle extends Application {
        
    /** The contents of the application scene. */
    private Group root;
    /** center point. */
    private Point2D center;
    /** radius of the circle.*/
    private final int radius = 3;
    /** circle to move to first mouse click location. */
    private Circle atCenter = new Circle(0, 0, radius);
    /** polygon to draw the equilateral triangle.*/
    private Polygon p = new Polygon();

   
    /**
     * Displays an initially empty scene, waiting for the user to draw lines
     * with the mouse.
     * 
     * @param primaryStage
     *            a Stage
     */
    public void start(Stage primaryStage) {
       
        root = new Group(atCenter);
        atCenter.setFill(Color.CYAN);
        p.setStroke(Color.CYAN);
        p.setFill(null);
        root.getChildren().add(p);
        final int appWidth = 800;
        final int appHeight = 500;
        Scene scene = new Scene(root, appWidth, appHeight, Color.BLACK);
        
        
        scene.setOnMousePressed(this::processMousePress);
        scene.setOnMouseDragged(this::processMouseDrag);

        primaryStage.setTitle("Equilateral Triangle");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Use the original mouse press location as the center of 
     * the triangle.
     * @param event invoked this method
     */
    public void processMousePress(MouseEvent event) {
        center = new Point2D(event.getX(), event.getY());
        atCenter.setTranslateX(event.getX());
        atCenter.setTranslateY(event.getY());
        
    }
    
    /**
     * Drags the mouse to determine one of the vertice location to 
     * set the triangle size and orientation.
     * @param event invoked this method
     */
    public void processMouseDrag(MouseEvent event) {
        final int angleConstant = 3;
        double centerX = center.getX();
        double centerY = center.getY();
        double r = Math.sqrt(Math.pow(centerX - event.getX(), 2) 
            + Math.pow(centerY - event.getY(), 2));
        double x1 = event.getX();
        double y1 = event.getY();
        double angle1 = Math.atan2(y1 - centerY, centerX - x1);
        double x2 = r * Math.cos(angle1 - Math.PI / angleConstant) + centerX;
        double y2 = centerY - r * Math.sin(angle1 - Math.PI / angleConstant);
        double x3 = r * Math.cos(angle1 + Math.PI / angleConstant) + centerX;
        double y3 = centerY - r * Math.sin(angle1 + Math.PI / angleConstant);
        p.getPoints().clear();
        p.getPoints().addAll(new Double[]{x1, y1, x2, y2, x3, y3});
    }

    /**
     * Launches the JavaFX application.
     * 
     * @param args
     *            command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

