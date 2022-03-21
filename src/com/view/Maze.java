package com.view;

import com.controller.Direction;
import com.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class contains the bulk of the code. It adds everything to the screen and contains collision detectors
 * and moves everything
 */
public class Maze extends JPanel implements ActionListener {

    private int M_WIDTH = 0;
    private int M_HEIGHT = 0;
    private final int SPACE = 25;
    public static final int LEFT_COLLISION = 1;
    public static final int RIGHT_COLLISION = 2;
    public static final int TOP_COLLISION = 3;
    public static final int BOTTOM_COLLISION = 4;
    public static int DELAY = 250;

    private boolean winner = false;
    private boolean loser = false;

    TAdapter adapter;


    private Timer timer;
    private Mouse mouse;
    private final File file;

    private ArrayList<Wall> walls;
    private ArrayList<Bush> bushes;
    private ArrayList<Hole> holes;
    private ArrayList<Path> paths;
    private ArrayList<Cheese> cheeses;
    private ArrayList<Cat> cats;

    private InfoPanel info;

    /**
     * Passes in the file and initializes the maze
     * @param file that user passes in
     */
    public Maze(File file) {
        this.file = file;
        initMaze();
    }

    /**
     * Adds a new key listener and add all the entities and starts the timer
     */
    private void initMaze() {

        adapter = new TAdapter();
        addKeyListener(adapter);
        setBackground(Color.BLACK);
        setFocusable(true);
        addEntities();
        setPreferredSize(new Dimension(M_WIDTH * 25, M_HEIGHT * 25));
        timer = new Timer(DELAY, this);
        timer.start();
    }

    /**
     * Takes all of the information from the file and adds all of them into their respective ArrayLists.
     */
    private void addEntities() {
        walls = new ArrayList<>();
        bushes = new ArrayList<>();
        paths = new ArrayList<>();
        holes = new ArrayList<>();
        cheeses = new ArrayList<>();
        cats = new ArrayList<>();

        //int numOfCols = 0;
//        int numOfRows = 0;
        char[][] coords = new char[0][];
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            String s = scanner.nextLine();
            Scanner sScan = new Scanner(s);
            while (sScan.hasNext()) {
                M_WIDTH = sScan.nextInt();
                M_HEIGHT = sScan.nextInt();
            }
            coords = new char[M_HEIGHT][M_WIDTH];
            for (int i = 0; i < M_HEIGHT; i++) {
                String next = scanner.nextLine();
                coords[i] = next.toCharArray();
                //System.out.println(next);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int x;
        int y;

        for (int i = 0; i < coords.length; i++) {
            for (int j = 0; j < coords[i].length; j++) {

                char c = coords[i][j];
                x = j * SPACE;
                y = i * SPACE;

                switch (c) {
                    case 'B':
                        walls.add(new Wall(x, y));
                        break;
                    case 'G':
                        bushes.add(new Bush(x, y));
                        break;
                    case 'P':
                        paths.add(new Path(x, y));
                        break;
                    case 'H':
                        holes.add(new Hole(x, y));
                        break;
                    default:
                        break;
                }
            }
        }

        while (true) {
            assert scanner != null;
            if (!scanner.hasNextLine()) break;
            String s = scanner.nextLine();
            Scanner sScan = new Scanner(s);
            while (sScan.hasNext()) {
                char c = sScan.next().charAt(0);
                int xx = sScan.nextInt() * 25;
                int yy = sScan.nextInt() * 25;

                switch (c) {
                    case 'M' -> mouse = new Mouse(xx, yy);
                    case 'C' -> cheeses.add(new Cheese(xx, yy));
                    case '1' -> cats.add(new Cat1(xx, yy));
                    case '2' -> cats.add(new Cat2(xx, yy));
                    case '3' -> cats.add(new Cat3(xx, yy));
                    case '4' -> cats.add(new Cat4(xx, yy));
                    case '5' -> cats.add(new Cat5(xx, yy));
                }

            }
        }
    }

    /**
     * Paints everything and prints a winner screen, loser screen, or draws the entities
     * @param g graphics object
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (winner) {
            winnerScreen(g);
        } else if (loser) {
            loserScreen(g);
        } else {
            drawEntities(g);
        }
    }

    /**
     * Draws everything onto the panel
     * @param g graphics object
     */
    private void drawEntities(Graphics g) {
        ArrayList<Entity> everything = new ArrayList<>();

        everything.addAll(walls);
        everything.addAll(bushes);
        everything.addAll(holes);
        everything.addAll(paths);
        everything.addAll(cheeses);
        everything.addAll(cats);
        everything.add(mouse);

        for (Entity item : everything) {
            g.drawImage(item.getImage(), item.getX(), item.getY(), this);
        }
    }

    /**
     * This class handles the moving of the mouse.
     */
    public class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            keySwitch(key);
        }

        public void keyPressed(int key) {
            keySwitch(key);
        }

        private void keySwitch(int key) {
            switch (key) {

                case KeyEvent.VK_LEFT:
                    //System.out.println("LEFT: " + key);
                    if (checkWall_BushCollision(mouse, LEFT_COLLISION)) {
                        return;
                    }
                    mouse.move(-SPACE, 0);
                    break;

                case KeyEvent.VK_RIGHT:
                    //System.out.println("RIGHT: " + key);
                    if (checkWall_BushCollision(mouse, RIGHT_COLLISION)) {
                        return;
                    }
                    mouse.move(SPACE, 0);
                    break;

                case KeyEvent.VK_UP:
                    //System.out.println("UP: " + key);
                    if (checkWall_BushCollision(mouse, TOP_COLLISION)) {
                        return;
                    }
                    mouse.move(0, -SPACE);
                    break;

                case KeyEvent.VK_DOWN:
                    //System.out.println("DOWN: " + key);
                    if (checkWall_BushCollision(mouse, BOTTOM_COLLISION)) {
                        return;
                    }
                    mouse.move(0, SPACE);
                    break;

                default:
                    break;
            }
            if (checkCheeseCollision(mouse)) {
                info.updateScore();
            }
            if (checkMouseCatCollision(mouse)) {
                info.updateLives();
                int rand = (int) (Math.random() * holes.size());
                Hole hole = holes.get(rand);
                mouse.setX(hole.getX());
                mouse.setY(hole.getY());
            }
            System.out.println("(" + mouse.getX() + "," + mouse.getY() + ")");
            repaint();
        }
    }

    /**
     * The method that the timer calls every DELAY ms.
     * It moves the cats and checks if the user has collected all of the cheese
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        moveCats();
        repaint();
        if (info.numOfCheese <= 0) {
            winner = true;
            timer.stop();
        }
        if (info.numOfLives <= 0) {
            loser = true;
            timer.stop();
        }
    }

    /**
     * Paints over the screen and outputs a loser message
     * @param g graphics object
     */
    private void loserScreen(Graphics g) {
        //System.out.println("LOSER");
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 10000, 10000);
        g.setColor(Color.WHITE);
        g.drawString("YOU LOST! (Press Reset to play again)", 200, M_HEIGHT * 25 / 2);
    }

    /**
     * Paints over the screen and outputs a winner message
     * @param g graphics object
     */
    private void winnerScreen(Graphics g) {
        //System.out.println("WINNER");
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 10000, 10000);
        g.setColor(Color.WHITE);
        g.drawString("YOU WON! (Press Reset to play again)", 200, M_HEIGHT * 25 / 2);
    }

    /**
     * Moves the cats depending on what type of cat it is
     */
    private void moveCats() {
        for (Cat cat : cats) {
            if (cat instanceof Cat1) {
                moveCat1(cat);
            }
            if (cat instanceof Cat2) {
                moveCat2(cat);
            }
            if (cat instanceof Cat3) {
                moveCat3(cat);
            }
            if (cat instanceof Cat4) {
                moveCat4(cat);
            }
            if (cat instanceof Cat5) {
                moveCat5(cat);
            }
            if (checkMouseCatCollision(cat)) {
                info.updateLives();
                int rand = (int) (Math.random() * holes.size());
                Hole hole = holes.get(rand);
                mouse.setX(hole.getX());
                mouse.setY(hole.getY());
            }
        }
    }

    /**
     * This moves cat5 which is the smart cat
     * @param cat Cat5
     */
    private void moveCat5(Cat cat) {
        if (cat.isCatStopped()) {
            cat.setCurrentX(-SPACE);
            cat.setCurrentY(0);
            cat.move(cat.getCurrentX(), cat.getCurrentY());
        }
        boolean onSameRow = mouse.getY() == cat.getY();
        boolean onSameColumn = mouse.getX() == cat.getX();
        if (!onSameRow) {
            Direction vDirection = findVerticalDirectionOfMouse(cat);
            cat.setCurrentX(vDirection.getX());
            cat.setCurrentY(vDirection.getY());
            if (!checkWall_Bush_HoleCollision(cat, cat.getCurrentX(), cat.getCurrentY())) {
                cat.move(cat.getCurrentX(), cat.getCurrentY());
            }
        } else {
            cat.move(0, 0);
        }
        if (!onSameColumn) {
            Direction hDirection = findHorizontalDirectionOfMouse(cat);
            cat.setCurrentX(hDirection.getX());
            cat.setCurrentY(hDirection.getY());
            if (!checkWall_Bush_HoleCollision(cat, cat.getCurrentX(), cat.getCurrentY())) {
                cat.move(cat.getCurrentX(), cat.getCurrentY());
            }
        } else {
            cat.move(0, 0);
        }
    }

    /**
     * Helper method for moving Cat5
     * @param cat Cat5
     * @return whether the cat is moving left or right
     */
    private Direction findHorizontalDirectionOfMouse(Cat cat) {
        if (mouse.getX() < cat.getX()) {
            return Direction.LEFT;
        }
        if (mouse.getX() > cat.getX()) {
            return Direction.RIGHT;
        }
        return null;
    }

    /**
     * Helper method for moving Cat5
     * @param cat Cat5
     * @return whether or not the cat is moving up or down
     */
    private Direction findVerticalDirectionOfMouse(Cat cat) {
        if (mouse.getY() < cat.getY()) {
            return Direction.UP;
        }
        if (mouse.getY() > cat.getY()) {
            return Direction.DOWN;
        }
        return null;
    }

    /**
     * This moves Cat4 which is the cat that goes left and right
     * @param cat Cat4
     */
    private void moveCat4(Cat cat) {
        if (cat.isCatStopped()) {
            cat.setCurrentX(SPACE);
            cat.setCurrentY(0);
            cat.move(cat.getCurrentX(), cat.getCurrentY());
        } else if ((cat.isMovingLeft() && checkWall_Bush_HoleCollision(cat, LEFT_COLLISION))) {
            cat.setCurrentX(SPACE);
            cat.setCurrentY(0);
            cat.move(cat.getCurrentX(), cat.getCurrentY());
        } else if ((cat.isMovingRight() && checkWall_Bush_HoleCollision(cat, RIGHT_COLLISION))) {
            cat.setCurrentX(-SPACE);
            cat.setCurrentY(0);
            cat.move(cat.getCurrentX(), cat.getCurrentY());
        } else {
            cat.move(cat.getCurrentX(), cat.getCurrentY());
        }
    }

    /**
     * This moves Cat3 which only moves up and down
     * @param cat Cat3
     */
    private void moveCat3(Cat cat) {
        if (cat.isCatStopped()) {
            cat.setCurrentX(0);
            cat.setCurrentY(-SPACE);
            cat.move(cat.getCurrentX(), cat.getCurrentY());
        } else if ((cat.isMovingUp() && checkWall_Bush_HoleCollision(cat, TOP_COLLISION))) {
            cat.setCurrentX(0);
            cat.setCurrentY(SPACE);
            cat.move(cat.getCurrentX(), cat.getCurrentY());
        } else if ((cat.isMovingDown() && checkWall_Bush_HoleCollision(cat, BOTTOM_COLLISION))) {
            cat.setCurrentX(0);
            cat.setCurrentY(-SPACE);
            cat.move(cat.getCurrentX(), cat.getCurrentY());
        } else {
            cat.move(cat.getCurrentX(), cat.getCurrentY());
        }
    }

    /**
     * This moves Cat2 which turns a clockwise direction at every intersection
     * @param cat Cat2
     */
    private void moveCat2(Cat cat) {
        if (cat.isCatStopped()) {
            cat.setCurrentX(SPACE);
            cat.setCurrentY(0);
            cat.move(cat.getCurrentX(), cat.getCurrentY());
        } else if ((cat.isMovingLeft() && checkWall_Bush_HoleCollision(cat, LEFT_COLLISION))) {
            cat.setCurrentX(0);
            cat.setCurrentY(-SPACE);
            if (!checkWall_Bush_HoleCollision(cat, cat.getCurrentX(), cat.getCurrentY())) {
                cat.move(cat.getCurrentX(), cat.getCurrentY());
            }
        } else if ((cat.isMovingRight() && checkWall_Bush_HoleCollision(cat, RIGHT_COLLISION))) {
            cat.setCurrentX(0);
            cat.setCurrentY(SPACE);
            if (!checkWall_Bush_HoleCollision(cat, cat.getCurrentX(), cat.getCurrentY())) {
                cat.move(cat.getCurrentX(), cat.getCurrentY());
            }
        } else if ((cat.isMovingUp() && checkWall_Bush_HoleCollision(cat, TOP_COLLISION))) {
            cat.setCurrentX(SPACE);
            cat.setCurrentY(0);
            if (!checkWall_Bush_HoleCollision(cat, cat.getCurrentX(), cat.getCurrentY())) {
                cat.move(cat.getCurrentX(), cat.getCurrentY());
            }
        } else if ((cat.isMovingDown() && checkWall_Bush_HoleCollision(cat, BOTTOM_COLLISION))) {
            cat.setCurrentX(-SPACE);
            cat.setCurrentY(0);
            if (!checkWall_Bush_HoleCollision(cat, cat.getCurrentX(), cat.getCurrentY())) {
                cat.move(cat.getCurrentX(), cat.getCurrentY());
            }
        } else {
            cat.move(cat.getCurrentX(), cat.getCurrentY());
        }
    }

    /**
     * This cat moves at a random direction when it hits an intersection
     * @param cat Cat1
     */
    private void moveCat1(Cat cat) {
        if (cat.isCatStopped()) {
            cat.setCurrentX(SPACE);
            cat.setCurrentY(0);
            cat.move(cat.getCurrentX(), cat.getCurrentY());

        } else if ((cat.isMovingLeft() && checkWall_Bush_HoleCollision(cat, LEFT_COLLISION)) ||
                (cat.isMovingRight() && checkWall_Bush_HoleCollision(cat, RIGHT_COLLISION)) ||
                (cat.isMovingUp() && checkWall_Bush_HoleCollision(cat, TOP_COLLISION)) ||
                (cat.isMovingDown() && checkWall_Bush_HoleCollision(cat, BOTTOM_COLLISION))) {

            Direction rand = Direction.getRandDirection();

            while (checkWall_Bush_HoleCollision(cat, rand.getX(), rand.getY())) {
                rand = Direction.getRandDirection();
            }
            cat.move(rand.getX(), rand.getY());
            cat.setCurrentX(rand.getX());
            cat.setCurrentY(rand.getY());

        } else {
            cat.move(cat.getCurrentX(), cat.getCurrentY());
        }
    }


    /**
     * The following methods are collision detector methods for the cats and the mouse
     *
     */
    // COLLISION CHECKER METHODS
    // ----------------------------------------------------------------------------------------------------------------

    private boolean checkMouseCatCollision(Entity entity) {
        if (entity instanceof Mouse) {
            for (Cat c : cats) {
                if (c.getX() == entity.getX() && c.getY() == entity.getY()) {
                    return true;
                }
            }
        } else {
            if (entity.getX() == mouse.getX() && entity.getY() == mouse.getY()) {
                return true;
            }
        }
        return false;
    }

    // FOR MOUSE
    private boolean checkCheeseCollision(Entity entity) {
        for (Cheese cheese : cheeses) {
            if (cheese.getX() == entity.getX() && cheese.getY() == entity.getY()) {
                cheeses.remove(cheese);
                return true;
            }
        }
        return false;
    }

    // FOR MOUSE
    private boolean checkWall_BushCollision(Entity entity, int type) {
        switch (type) {
            case LEFT_COLLISION:
                for (Wall wall : walls) {
                    if (entity.isLeftCollision(wall)) {
                        return true;
                    }
                }
                for (Bush bush : bushes) {
                    if (entity.isLeftCollision(bush)) {
                        return true;
                    }
                }
                return false;

            case RIGHT_COLLISION:
                for (Wall wall : walls) {
                    if (entity.isRightCollision(wall)) {
                        return true;
                    }
                }
                for (Bush bush : bushes) {
                    if (entity.isRightCollision(bush)) {
                        return true;
                    }
                }
                return false;

            case TOP_COLLISION:
                for (Wall wall : walls) {
                    if (entity.isTopCollision(wall)) {
                        return true;
                    }
                }
                for (Bush bush : bushes) {
                    if (entity.isTopCollision(bush)) {
                        return true;
                    }
                }
                return false;

            case BOTTOM_COLLISION:
                for (Wall wall : walls) {
                    if (entity.isBottomCollision(wall)) {
                        return true;
                    }
                }
                for (Bush bush : bushes) {
                    if (entity.isBottomCollision(bush)) {
                        return true;
                    }
                }
                return false;

            default:
                break;
        }
        return false;
    }

    // FOR CATS
    private boolean checkWall_Bush_HoleCollision(Entity entity, int type) {
        switch (type) {
            case LEFT_COLLISION:
                for (Wall wall : walls) {
                    if (entity.isLeftCollision(wall)) {
                        return true;
                    }
                }
                for (Bush bush : bushes) {
                    if (entity.isLeftCollision(bush)) {
                        return true;
                    }
                }
                for (Hole hole : holes) {
                    if (entity.isLeftCollision(hole)) {
                        return true;
                    }
                }
                return false;

            case RIGHT_COLLISION:
                for (Wall wall : walls) {
                    if (entity.isRightCollision(wall)) {
                        return true;
                    }
                }
                for (Bush bush : bushes) {
                    if (entity.isRightCollision(bush)) {
                        return true;
                    }
                }
                for (Hole hole : holes) {
                    if (entity.isRightCollision(hole)) {
                        return true;
                    }
                }
                return false;

            case TOP_COLLISION:
                for (Wall wall : walls) {
                    if (entity.isTopCollision(wall)) {
                        return true;
                    }
                }
                for (Bush bush : bushes) {
                    if (entity.isTopCollision(bush)) {
                        return true;
                    }
                }
                for (Hole hole : holes) {
                    if (entity.isTopCollision(hole)) {
                        return true;
                    }
                }
                return false;

            case BOTTOM_COLLISION:
                for (Wall wall : walls) {
                    if (entity.isBottomCollision(wall)) {
                        return true;
                    }
                }
                for (Bush bush : bushes) {
                    if (entity.isBottomCollision(bush)) {
                        return true;
                    }
                }
                for (Hole hole : holes) {
                    if (entity.isBottomCollision(hole)) {
                        return true;
                    }
                }
                return false;

            default:
                break;
        }
        return false;
    }

    // FOR CATS
    private boolean checkWall_Bush_HoleCollision(Entity entity, int x, int y) {
        if (x == -SPACE && y == 0) {
            if (checkWall_Bush_HoleCollision(entity, LEFT_COLLISION)) {
                return true;
            }
        }
        if (x == SPACE && y == 0) {
            if (checkWall_Bush_HoleCollision(entity, RIGHT_COLLISION)) {
                return true;
            }
        }
        if (x == 0 && y == -SPACE) {
            if (checkWall_Bush_HoleCollision(entity, TOP_COLLISION)) {
                return true;
            }
        }
        if (x == 0 && y == SPACE) {
            if (checkWall_Bush_HoleCollision(entity, BOTTOM_COLLISION)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Clears the ArrayLists and resets the game
     */
    public void clear() {
        walls.clear();
        bushes.clear();
        paths.clear();
        holes.clear();
        cheeses.clear();
        cats.clear();

        winner = false;
        loser = false;

        addEntities();
        timer.start();
    }

    public Timer getTimer() {
        return timer;
    }

    public TAdapter getAdapter() {
        return adapter;
    }

    public void setInfo(InfoPanel info) {
        this.info = info;
    }
}
