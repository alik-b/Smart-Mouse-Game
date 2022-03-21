package com.view;

import javax.swing.*;
import java.awt.*;

/**
 * This class is the bottom Panel of the JFrame holding the JSlider and the arrow buttons
 *
 * @author Alik Balika
 * @version 2020.12.19
 */
public class ControlPanel extends JPanel {

    public static final int FPS_MIN = 0;
    public static final int FPS_MAX = 500;
    public static final int FPS_INIT = 250;

    JLabel fps;
    JSlider slider;

    JButton right;
    JButton down;
    JButton up;
    JButton left;

    Maze maze;

    /**
     * adds all of the components and action listeners
     * @param maze the maze that this panel will interact with
     */
    public ControlPanel(Maze maze) {
        this.maze = maze;
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        setBackground(Color.BLACK);

        fps = new JLabel("FPS:");
        fps.setForeground(Color.WHITE);

        add(fps);
        addSlider();
        addButtons();
        addActionListeners();
    }

    /**
     * Adds the action listeners to each component
     */
    private void addActionListeners() {
        slider.addChangeListener(e -> maze.getTimer().setDelay(slider.getValue()));

        left.addActionListener(e -> maze.getAdapter().keyPressed(37));
        up.addActionListener(e -> maze.getAdapter().keyPressed(38));
        right.addActionListener(e -> maze.getAdapter().keyPressed(39));
        down.addActionListener(e -> maze.getAdapter().keyPressed(40));
    }

    /**
     * adds the buttons with arrow icons
     */
    private void addButtons() {
        right = new JButton(new ImageIcon("src/resources/right.png"));
        right.setPreferredSize(new Dimension(25, 25));
        right.setFocusable(false);
        down = new JButton(new ImageIcon("src/resources/down.png"));
        down.setPreferredSize(new Dimension(25, 25));
        down.setFocusable(false);
        up = new JButton(new ImageIcon("src/resources/up.png"));
        up.setPreferredSize(new Dimension(25, 25));
        up.setFocusable(false);
        left = new JButton(new ImageIcon("src/resources/left.png"));
        left.setPreferredSize(new Dimension(25, 25));
        left.setFocusable(false);

        add(right);
        add(down);
        add(up);
        add(left);
    }

    /**
     * adds the slider with all of the necessary details
     */
    private void addSlider() {
        slider = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, FPS_INIT);

        slider.setMajorTickSpacing(100);
        slider.setMinorTickSpacing(50);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setForeground(Color.WHITE);
        slider.setBackground(Color.BLACK);
        slider.setFocusable(false);

        add(slider);
    }

}
