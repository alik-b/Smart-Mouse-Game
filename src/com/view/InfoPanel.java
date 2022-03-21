package com.view;

import javax.swing.*;
import java.awt.*;

/**
 * This class is the top Panel of the JFrame holding the start, stop, step and reset buttons.
 * It also holds the labels of the time score and lives.
 *
 * @author Alik Balika
 * @version 2020.12.19
 */
public class InfoPanel extends JPanel {


    JButton start;
    JButton stop;
    JButton step;
    JButton reset;

    JLabel time;
    JLabel lives;
    JLabel score;

    int numOfLives = 3;
    int numOfCheese = 3;
    int elapsedTime;

    Timer timer;

    Maze maze;

    /**
     * constructor adds all the labels and buttons to the top
     * It also adds all the action listeners.
     *
     * @param maze takes in the maze in order to interact with it
     */
    public InfoPanel(Maze maze) {
        this.maze = maze;
        setBackground(Color.BLACK);

        addTimeLabels();
        addButtons();
        addLives();
        addScore();
        addActionListeners();

    }

    /**
     * Decreases the number of lives down by one and updates the lives label
     */
    public void updateLives() {
        numOfLives--;
        lives.setText(numOfLives + "");
    }

    /**
     * Decreases the number of cheese by one and updates the score label
     */
    public void updateScore() {
        numOfCheese--;
        score.setText(numOfCheese + "");
    }

    /**
     * Adds all of the button action listeners.
     */
    private void addActionListeners() {
        stop.addActionListener(e -> {
            maze.getTimer().stop();
            timer.stop();
        });

        start.addActionListener(e -> {
            maze.getTimer().setRepeats(true);
            maze.getTimer().start();
            timer.start();
        });

        step.addActionListener(e -> {
            maze.getTimer().setRepeats(false);
            maze.getTimer().start();
        });

        reset.addActionListener(e -> {
            maze.clear();
            time.setText("Time: 00:00");
            numOfLives = 3;
            lives.setText(numOfLives + "");
            numOfCheese = 3;
            score.setText(numOfLives + "");
            elapsedTime = 0;
        });

    }

    /**
     * adds the score label with an icon
     */
    private void addScore() {
        score = new JLabel(new ImageIcon("src/resources/cheese.png"));
        score.setText(numOfCheese + "");
        score.setForeground(Color.WHITE);

        add(score);
    }

    /**
     * adds the lives label with an icon
     */
    private void addLives() {
        lives = new JLabel(new ImageIcon("src/resources/mouse.png"));
        numOfLives = 3;
        lives.setText(numOfLives + "");
        lives.setForeground(Color.WHITE);

        add(lives);
    }

    /**
     * Adds the time label and keeps updating it.
     */
    private void addTimeLabels() {
        time = new JLabel("Time: 00:00");
        time.setForeground(Color.WHITE);
        elapsedTime = 0;

        timer = new Timer(1000, e -> {
            elapsedTime++;

            int min = elapsedTime / 60;
            int seconds = elapsedTime % 60;

            time.setText(String.format("Time: %02d:%02d", min, seconds));
        });
        timer.start();

        add(time);
    }

    /**
     * adds all of the buttons to the panel
     */
    private void addButtons() {
        start = new JButton("Start");
        start.setFocusable(false);
        start.setBackground(Color.BLACK);
        start.setForeground(Color.WHITE);
        stop = new JButton("Stop");
        stop.setFocusable(false);
        stop.setBackground(Color.BLACK);
        stop.setForeground(Color.WHITE);
        step = new JButton("Step");
        step.setFocusable(false);
        step.setBackground(Color.BLACK);
        step.setForeground(Color.WHITE);
        reset = new JButton("Reset");
        reset.setFocusable(false);
        reset.setBackground(Color.BLACK);
        reset.setForeground(Color.WHITE);

        add(start);
        add(stop);
        add(step);
        add(reset);
    }

}
