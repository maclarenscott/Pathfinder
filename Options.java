// package Pathfinder;
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
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.lang.*;
class Options extends JFrame {
    private Grid grid;
    private Screen screen;

    private JPanel buttonJPanel;
    private JButton reset;
    private JButton setStart;
    private JButton setEnd;
    private JButton go;
    private JSlider frameRate;
    private JLabel frameRateLabel;

    private boolean startStatus = false;
    private boolean endStatus = false;

    public Options(int width, int height) {
        super("Pathfinding");
        setSize(width, height);
        setResizable(false);
        buttonJPanel = new JPanel();
        buttonJPanel.setLayout(new GridLayout(4, 2));
        reset = new JButton("Reset");
        buttonJPanel.add(reset);
        ActionListener resetEvent = new ButtonEventListener();
        reset.addActionListener(resetEvent);
        setStart = new JButton("Set Start");
        buttonJPanel.add(setStart);
        ActionListener setStartEvent = new ButtonEventListener();
        setStart.addActionListener(setStartEvent);
        setEnd = new JButton("Set End");
        buttonJPanel.add(setEnd);
        ActionListener setEndEvent = new ButtonEventListener();
        setEnd.addActionListener(setEndEvent);
        go = new JButton("Go");
        buttonJPanel.add(go);
        ActionListener goEvent = new ButtonEventListener();
        go.addActionListener(goEvent);
        frameRate = new JSlider(JSlider.HORIZONTAL);
        buttonJPanel.add(frameRate);
        frameRateLabel = new JLabel("Frame Rate");
        buttonJPanel.add(frameRateLabel);

        add(buttonJPanel, BorderLayout.NORTH);
    }

    public void setStartStatus(boolean b) {
        startStatus = b;
    }

    public void setEndStatus(boolean b) {
        endStatus = b;
    }

    public boolean getStartStatus() {
        return startStatus;
    }

    public boolean getEndStatus() {
        return endStatus;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
        screen.repaint();
    }


    public int getFrameRate() {
        return this.frameRate.getValue();
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    // events
    class ButtonEventListener implements ActionListener {
        // The ActionListener interface requires that we override the actionPerformed()
        // method.
        // This method will be called automatically whenever a button event occurs.
        @Override
        public void actionPerformed(ActionEvent e) {
            // The ActionEvent getSource() method returns a reference to the button widget
            // that was clicked
            // This allows us to use one event listener for more than one JButton, if
            // desired.
            if (e.getSource() == reset) {
                screen.getContentPane().removeAll();
                screen.revalidate();
                grid.reset();
                screen.validate();
                screen.repaint();
            }
            if (e.getSource() == setStart) {
                setStartStatus(true);
            }
            if (e.getSource() == setEnd) {
                setEndStatus(true);
            }
            if (e.getSource() == go) {
                int[] start = grid.getStart();
                int[] end = grid.getEnd();
                if (start[0] != -1 && end[0] != -1) { // if start and end exist
                    System.out.println(
                            String.format("Going from (%s,%s) to (%s,%s)", start[0], start[1], end[0], end[1]));
                    screen.bFSearch(start, start, end, new ArrayList<int[]>());
                    System.out.println(grid);
                    repaint();
                }
            }
        }
    }
}
