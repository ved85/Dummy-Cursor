/**
*@author Vedant Varma
*/
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class Vcursor {
    public static void main(String[] args) {
        // Create a JFrame to listen for key events
        JFrame frame = new JFrame("Press Esc to Exit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        try {
            Robot robot = new Robot();
            Timer timer = new Timer();

            // Define the size of the square
            final int squareSize = 50;
            final int[] step = {0}; // Step counter to determine the current direction

            // Schedule the cursor movement task
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    try {
                        // Get the current mouse position
                        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
                        Point currentPoint = pointerInfo.getLocation();
                        int currentX = (int) currentPoint.getX();
                        int currentY = (int) currentPoint.getY();

                        // Calculate new position based on the current step
                        int newX = currentX;
                        int newY = currentY;

                        switch (step[0] % 4) {
                            case 0: // Move right
                                newX += squareSize;
                                break;
                            case 1: // Move down
                                newY += squareSize;
                                break;
                            case 2: // Move left
                                newX -= squareSize;
                                break;
                            case 3: // Move up
                                newY -= squareSize;
                                break;
                        }

                        // Move the mouse to the new position
                        robot.mouseMove(newX, newY);
                        System.out.println("Cursor moved to: (" + newX + ", " + newY + ") at: " + System.currentTimeMillis());

                        // Increment step to change direction in the next iteration
                        step[0]++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            timer.schedule(task, 0, 1000); // Schedule task every 1 second (1000 ms)

            // Add a KeyListener to the JFrame to detect Esc key
            frame.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        timer.cancel(); // Stop the timer
                        System.out.println("Exiting program...");
                        frame.dispose(); // Close the GUI
                        System.exit(0); // Exit the program
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {}
            });
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
