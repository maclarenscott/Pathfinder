// package ICS.Pathfinder;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        //Options window
        Options options = new Options(300, 300);
        options.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        options.setVisible(true);

        //Main screen
        Screen appWindow = new Screen(options);
        options.setScreen(appWindow);
        appWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appWindow.setVisible(true);

    }
}
