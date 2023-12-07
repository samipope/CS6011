package assignment09;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 *
 * Demo app which draws the segments in the order specified by the BSP Tree's
 * far to near traversal
 *
 * The bottom left corner of the screen is (0,0) and the top right is (1,1)
 *
 * Click to place the eye and redraw the segments from far to near, from that point
 *
 * Shift click to search for a collision between the eye and the clicked point
 * The app will draw one colliding edge if any.  The algorithm we discussed doesn't
 * specify WHICH collision be returned, just that one will if there are any.
 */
public class Renderer2D extends JPanel {
    private final BSPTree tree;
    private final Point2D.Double eye = new Point2D.Double(0.5, 0.5f);

    private ArrayList<Segment> orderedSegments;
    private int currentSegment = 0;
    private Segment collidingSegment = null; //for doing collision checks
    private Timer timer;

    final static int windowSize = 800;

    public Renderer2D(BSPTree tree){
        this.tree = tree;


        JFrame f = new JFrame("2D BSP Renderer");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(windowSize, windowSize);
        f.add(this);
        f.pack();
        f.setVisible(true);

        this.addMouseListener(new MouseAdapter() {
            //pressed instead of clicked because if you move the mouse at all before releasing
            //you don't get a click event
            @Override
            public void mousePressed(MouseEvent e) {
                handleClick(e);

            }
        });
        f.setLocation(windowSize,0);
        startAnimation();
    }


    //redraw everything
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.clearRect(0,0,windowSize,windowSize);
        g2.setColor(Color.BLACK);


        for(int i = 0; i < currentSegment; i++){
            var s = orderedSegments.get(i);

            drawSegmentAsLine(g2, s);
        }


        g2.setColor(Color.RED);
        //1 - y because the drawing coordinate system has 1 pointing down
        g2.fillOval((int)(eye.x*windowSize), (int)((1 - eye.y)*windowSize),
                10,10
        );

        if(collidingSegment != null){
            drawSegmentAsLine(g2, collidingSegment);
        }

    }

    private static void drawSegmentAsLine(Graphics2D g2, Segment s) {
        //1 - y because the drawing coordinate system has 1 pointing down
        g2.drawLine((int)(s.x1()*windowSize),
                (int)((1 - s.y1())*windowSize),
                (int)(s.x2()*windowSize),
                (int)((1 - s.y2())*windowSize));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(windowSize,windowSize);
    }

    private void handleClick(MouseEvent e){

        if(e.isShiftDown()) {

            collidingSegment = tree.collision(new Segment(eye.x, eye.y,
                    (double)(e.getX())/windowSize, 1.0 - (double)(e.getY())/windowSize));
            repaint();

        } else {
            System.out.println(e.getX() + " " + e.getY());
            eye.x = (double) (e.getX()) / windowSize;
            eye.y = 1.0 - (double) (e.getY()) / windowSize;
            collidingSegment = null;
            startAnimation();
        }


    }

    private void startAnimation() {
        orderedSegments = new ArrayList<>();
        tree.traverseFarToNear(eye.x, eye.y, orderedSegments::add);

        //animate
        if(timer != null){
            timer.stop();
        }
        currentSegment = 0;
        //each frame we'll draw one more line so we can visualize the traversal
        timer = new Timer(16, (ActionEvent ae) ->{
            currentSegment++;
            if(currentSegment >= orderedSegments.size()){
                timer.stop();
            }
            repaint();

        });
        timer.start();
    }

    public static void main(String[] args) {

        ArrayList<Segment> as = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            as.add(new Segment(Math.random(), Math.random(), Math.random(), Math.random()));
        }

        BSPTree bt = new BSPTree(as);

        bt.insert(new Segment(0, 0.5, 1, 0.5));
        bt.insert(new Segment(0.3, 0, 0.3, 1));

        SwingUtilities.invokeLater(() -> {
            new Renderer2D(bt);
        });
    }
}