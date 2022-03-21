package com.view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Scanner;

/**
 * the main driver class of the program.
 */
public class SmartMouseEx extends JFrame {

    private final File file;

    public SmartMouseEx(File file) {
        this.file = file;

        initUI();
    }

    private void initUI() {

        Maze maze = new Maze(file);
        InfoPanel info = new InfoPanel(maze);
        maze.setInfo(info);

        add(info, BorderLayout.NORTH);
        add(maze, BorderLayout.CENTER);
        add(new ControlPanel(maze), BorderLayout.SOUTH);

        setResizable(false);
        pack();

        setTitle("Smart Mouse Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        String fileName = "src/resources/default_level.txt";
        File file = new File("");

        Scanner input = new Scanner(System.in);
        System.out.print("Would you like to play the default level? (Enter 'y'): ");
        String yesOrNo = input.nextLine();
        if (yesOrNo.equalsIgnoreCase("y")) {
            file = new File(fileName);
        } else {
            while (!file.exists()) {
                System.out.print("Please enter the name of your file: ");
                fileName = input.nextLine();
                file = new File(fileName);
            }
        }
        File finalFile = file;
        EventQueue.invokeLater(() -> {
            SmartMouseEx ex = new SmartMouseEx(finalFile);
            ex.setVisible(true);
        });
    }
}