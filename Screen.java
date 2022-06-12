
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import java.lang.StackWalker.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JSlider;

// import ICS.Pathfinder;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.lang.*;
class Screen extends JFrame {
    private Grid grid;
    private int width = 700;
    private int height = 700;
    private int frameRate = 30;
    private Options options;

    public void paint(Graphics g) {

        super.paint(g);
        setVisible(true);
        Pixel[][] board = grid.getBoard();
        for (int row = 0; row < grid.getHeight(); row++) {
            for (int col = 0; col < grid.getWidth(); col++) {
                g.setColor(Color.BLACK);
                int xPosition = (int) (col * width / grid.getWidth());
                int yPosition = (int) (row * height / grid.getHeight());
                int pixelWidth = (int) (width / grid.getWidth());
                int pixelHeight = (int) (height / grid.getHeight());
                g.drawRect(xPosition, yPosition, pixelWidth, pixelHeight);

                // So after this, if you draw anything, all of it's result will be the color
                if (board[row][col].getIsStartPoint()) {
                    g.setColor(Color.GREEN);
                } else if (board[row][col].getIsEndPoint()) {
                    g.setColor(Color.RED);
                }  else if (board[row][col].getSignificant()) {
                    // System.out.println("("+row+","+col+")");
                    g.setColor(Color.ORANGE);
                } else if (board[row][col].getActive()) {
                    if (board[row][col].getState()) {
                        g.setColor(Color.LIGHT_GRAY);
                    } else {
                        g.setColor(Color.GRAY);
                    }
                } else if (board[row][col].getState()) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(xPosition, yPosition, pixelWidth, pixelHeight);
            }
        }

    }

    public Screen(Options options) {
        // Call the parent JFrame constructor to set the title, and switch to FlowLayout
        super("Grid");
        this.options = options;
        setSize(width, height);
        setResizable(true);

        grid = new Grid(8, 8);
        options.setGrid(grid);
        // Create and register listener for mouse and mouse motion events
        MouseEventListener mousePanelListener = new MouseEventListener();
        addMouseListener(mousePanelListener);
        addMouseMotionListener(mousePanelListener);
        validate();
        repaint();
    }

    public boolean intArrayInArrayList(int[] arr, ArrayList<int[]> list) {
        System.out.print(String.format("checking if (%s,%s) in travelled...", arr[0], arr[1]));
        for (int i = 0; i < list.size(); i++) {
            int[] temp = list.get(i);
            if (temp[0] == arr[0] && temp[1] == arr[1]) {
                System.out.println(String.format("found (%s,%s) in travelled", arr[0], arr[1]));
                return true;
            }
        }
        System.out.println(String.format("didn't find (%s,%s) in travelled", arr[0], arr[1]));
        return false;
    }

    public ArrayList<int[]> bFSearch(int[] current, int[] start, int[] end, ArrayList<int[]> travelled) {
        
        repaint();
        travelled.add(current);
        Pixel[][] board = grid.getBoard();
        if (current[0] == end[0] & current[1] == end[1]) {
            board[current[1]][current[0]].setSignificant(true);
            System.out.println("got to " + end[0] + "," + end[1] + " it took " + travelled.size() + " steps");
            System.out.println("Here is the path...");
            for (int m = 0; m < travelled.size(); m++) {
                System.out.println("x: " + travelled.get(m)[0]);
                System.out.println("y: " + travelled.get(m)[1]);
                System.out.println();
            }
            return travelled;
        }
        System.out.println(String.format("Starting bfsearch on (%s,%s)...", current[0], current[1]));
        //  get neighboring nodes
        ArrayList<int[]> neighbors = grid.getNeighborPixels(current, end);

        System.err.println("---travelled to---");
        for (int m = 0; m < travelled.size(); m++) {
            System.out.print("(" + travelled.get(m)[0] + ",");
            System.out.println(+travelled.get(m)[1] + ")");
        }
        System.out.println("---");

        //Checks neighbors
        for (int i = 0; i < neighbors.size(); i++) {
            int[] n = neighbors.get(i);
            System.out.println(String.format("Checking neighbor (%s,%s)...", n[0], n[1]));
            if (end[0] == n[0] && end[1] == n[1]) {
                board[current[1]][current[0]].setSignificant(true);
                board[n[1]][n[0]].setSignificant(true);
                travelled.add(n);
                System.out.println("got to " + end[0] + "," + end[1] + " it took " +
                        travelled.size() + " steps");
                System.out.println("Here is the path...");
                for (int m = 0; m < travelled.size(); m++) {
                    System.out.println("x: " + travelled.get(m)[0]);
                    System.out.println("y: " + travelled.get(m)[1]);
                    System.out.println();
                }
                return travelled;
            }
            //Checks if already visited neighbor n
            if (!intArrayInArrayList(n, travelled) ) {
                
                if (travelled.size() < 1000) {
                    board[current[1]][current[0]].setSignificant(true);
                    repaint();
                    System.out.println(
                            "stepping to (" + n[0] + "," + n[1] + ") from (" + current[0] + "," + current[1] + ")");
                    return bFSearch(n, start, end, travelled);
                } //Kills execution after 1000 steps
                else {
                    System.err.println("too many steps to handle..." + travelled.size());
                    return new ArrayList<int[]>();
                }

            } else {
                System.out.println("already travelled to (" + n[0] + "," + n[1] + ")");
                System.err.println("---travelled to---");
                for (int m = 0; m < travelled.size(); m++) {

                    System.out.print("(" + travelled.get(m)[0] + ",");
                    System.out.println(+travelled.get(m)[1] + ")");

                }
                System.out.println("---");
            }
        }
        System.out.println("Couldn't find a path");
        return travelled;
    }

    

    class MouseEventListener implements MouseListener, MouseMotionListener {
        // These 5 methods override those in MouseListener interface
        // handle event when mouse released immediately after press
        int renderDelay = 0;

        @Override
        public void mouseClicked(MouseEvent event) {
            System.out.println(String.format("Clicked at [%d, %d]",
                    event.getX(), event.getY()));
        }

        // handle event when mouse pressed
        @Override
        public void mousePressed(MouseEvent event) {
            grid.mouseActive(event.getX(), event.getY(), width, height, options);
            grid.setStart(event.getX(), event.getY(), width, height, options);
            grid.setEnd(event.getX(), event.getY(), width, height, options);
            repaint();
        }

        // handle event when mouse released after dragging
        @Override
        public void mouseReleased(MouseEvent event) {
            grid.mouseUp(options);
            repaint();
            // System.out.println( String.format( "Released at [%d, %d]",
            // event.getX(), event.getY() ) );
        }



        // handle event when user drags mouse with button pressed
        @Override
        public void mouseDragged(MouseEvent event) {
            grid.mouseActive(event.getX(), event.getY(), width, height, options);
            // System.out.println(String.format("Dragged at [%d, %d]",
            // event.getX(), event.getY()));
            if (renderDelay % (101 - options.getFrameRate()) == 0) {
                repaint();
            }
            renderDelay++;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        // handle event when user moves mouse
    }

}
