//diskBoard[column][row] and it's top left to bottom right
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Main implements Runnable, ActionListener{

  // Class Variables 
  CardLayout screens;

  JPanel mainPanel;
  JPanel titlePanel;
  JPanel rulesPanel;
  JPanel gamePanel;
  JPanel boardPanel;
  JPanel textPanel;

  JLabel titleLabel;
  JLabel textBarGame;
  JLabel textBarRules;

  JTextArea rulesTextArea;

  JButton menuSingle;
  JButton menuMulti;
  JButton menuRules;
  JButton menuBack;

  // board
  JLabel[][] diskBoard;
  String[][] diskValues;

  JButton[] columnButtons;

  // images
  ImageIcon titleIcon;
  ImageIcon singleIcon;
  ImageIcon multiIcon;
  ImageIcon rulesIcon;
  ImageIcon menuIcon;
  ImageIcon wonOneText;
  ImageIcon wonTwoText;
  ImageIcon turnOneText;
  ImageIcon turnTwoText;
  ImageIcon drawText;
  ImageIcon rulesText;
  ImageIcon columnIcon;
  ImageIcon redDisk;
  ImageIcon yellowDisk;
  ImageIcon greenDisk;
  ImageIcon emptyDisk;

  //colours  
  Color backgroundTitle = new Color(151, 162, 199);
  Color backgroundGame = new Color(255, 255, 255);

  // variables
  boolean singlePlayer;
  boolean turnRed = true;
  boolean[] columnFull;
  boolean allColumnsFull = false;
  boolean win = false;
  
  // Method to assemble our GUI
  public void run(){
    // Creats a JFrame that is 700 pixels by 600 pixels, and closes when you click on the X
    JFrame frame = new JFrame("Connect Four");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(700, 600);
    frame.setVisible(true);
    

    // icons
    titleIcon = new ImageIcon("Title.png");
    singleIcon = new ImageIcon("SinglePlayer.png");
    multiIcon = new ImageIcon("MultiPlayer.png");
    rulesIcon = new ImageIcon("RulesButton.png");
    menuIcon = new ImageIcon("MenuButton.png");
    wonOneText = new ImageIcon("TextBarWonOne.png");
    wonTwoText = new ImageIcon("TextBarWonTwo.png");
    turnOneText = new ImageIcon("TextBarTurnOne.png");
    turnTwoText = new ImageIcon("TextBarTurnTwo.png");
    drawText = new ImageIcon("TextBarDraw.png");
    rulesText = new ImageIcon("TextBarRules.png");
    columnIcon = new ImageIcon("ColumnButton.png");
    redDisk = new ImageIcon("DiskRed.png");
    yellowDisk = new ImageIcon("DiskYellow.png");
    greenDisk = new ImageIcon("DiskGreen.png");
    emptyDisk = new ImageIcon("DiskEmpty.png");


    // title panel
    titlePanel = new JPanel();
    titlePanel.setLayout(null);
    titlePanel.setBackground(backgroundTitle);

    // title
    titleLabel = new JLabel(titleIcon);
    titleLabel.setBounds(50, 100, 600, 125);
    titlePanel.add(titleLabel);

    // menu buttons
    menuSingle = new JButton(singleIcon);
    menuMulti = new JButton(multiIcon);
    menuRules = new JButton(rulesIcon);

    menuSingle.setBounds(50, 325, 200, 125);
    menuMulti.setBounds(250, 325, 200, 125);
    menuRules.setBounds(450, 325, 200, 125);
    
    menuSingle.setActionCommand("single");
    menuMulti.setActionCommand("multi");
    menuRules.setActionCommand("rules");

    menuSingle.addActionListener(this);
    menuMulti.addActionListener(this);
    menuRules.addActionListener(this);

    titlePanel.add(menuSingle);
    titlePanel.add(menuMulti);
    titlePanel.add(menuRules);


    // rules panel
    rulesPanel = new JPanel();
    rulesPanel.setLayout(null);
    titlePanel.setBackground(backgroundTitle);

    // rules title
    textBarRules = new JLabel(rulesText);
    textBarRules.setBounds(100, 50, 600, 125);
    rulesPanel.add(textBarRules);

    // rules text area
    rulesTextArea = new JTextArea(" This is Connect 4, where the goal of the game \n is to connect 4 disks of your colour. You can \n connect them vertically, horizontally and \n diagonally. The player who connects 4 of their disks \n win. To play select either single player or two\n player. During your turn you can click one of \n the buttons above the board, which will place \n your disk at the bottom of that column. Then \n your opponent will go, and then back to you. \n If the whole board has been filled up with no \n winner, then the game ends declaring a draw.");

    // set font colour and size
    rulesTextArea.setFont(new Font("Serif", Font.PLAIN, 25));
    rulesTextArea.setForeground(new Color(0, 0, 0)); 

    rulesTextArea.setEnabled(false);
    rulesTextArea.setBounds(50, 200, 600, 350);
    rulesPanel.add(rulesTextArea);

    // menu button
    menuBack = new JButton(menuIcon);
    menuBack.setActionCommand("menu");
    menuBack.addActionListener(this);
    menuBack.setBounds(450, 50, 200, 125);
    rulesPanel.add(menuBack);


    // board panel
    boardPanel = new JPanel();
    boardPanel.setLayout(new GridLayout(6,7));
    boardPanel.setBackground(backgroundGame);
    boardPanel.setBounds(85, 110, 525, 450);

    // text panel
    textPanel = new JPanel();
    textPanel.setLayout(null);
    textPanel.setBackground(backgroundGame);
    textPanel.setBounds(0, 0, 800, 110);
    
    // game panel
    gamePanel = new JPanel();
    gamePanel.setLayout(null);
    gamePanel.setBackground(backgroundGame);

    gamePanel.add(boardPanel);
    gamePanel.add(textPanel);


    // game heading
    textBarGame = new JLabel(turnOneText);
    textBarGame.setBounds(50, 0, 600, 80);

    textPanel.add(textBarGame);

    // column buttons
    columnButtons = new JButton[7];
    columnFull = new boolean[7];

    // for loop to create and place the column buttons
    for (int i = 0; i < 7; i++) {
      columnButtons[i] = new JButton(columnIcon);
      columnButtons[i].setBounds((95 + (i * 75)), 90, 55, 10);
      columnButtons[i].setActionCommand("column" + (i + 1));
      columnButtons[i].addActionListener(this);

      textPanel.add(columnButtons[i]);

      // tell the game that the corresponding column isn't full
      columnFull[i] = false;
    }
    
    // game board and values of disks
    diskBoard = new JLabel[7][6];
    diskValues = new String[7][6];

    // for loop to create and place the labels
    // also tell diskValues what the disks are
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 7; j++) {
        diskBoard[j][i] = new JLabel(emptyDisk);
        diskValues[j][i] = "empty";
        boardPanel.add(diskBoard[j][i]);
      }
    }


    // card layout
    screens = new CardLayout();

    // main panel
    mainPanel = new JPanel();
    mainPanel.setLayout(screens);
    frame.add(mainPanel);

    mainPanel.add(titlePanel, "title");
    mainPanel.add(gamePanel, "game");
    mainPanel.add(rulesPanel, "rules");

    screens.show(mainPanel, "title");
  }

  // method called when a button is pressed
  public void actionPerformed(ActionEvent e){
    // get the command from the action
    String command = e.getActionCommand();
    // if someone has won or its a draw then allow no action to be performed
    if (win == false && allColumnsFull == false) {
      switch (command) {
        // menu naviagation buttons
        case "single":
          // swap screens and tell program it's 1 player
          screens.show(mainPanel, "game");
          singlePlayer = true;
          return;
        case "multi":
          // swap screens and tell program it's 2 player
          screens.show(mainPanel, "game");
          singlePlayer = false;
          return;
        case "rules":
          // swap screens to the rules panel
          screens.show(mainPanel, "rules");
          return;
        case "menu":
          // swap screens to the title panel
          screens.show(mainPanel, "title");
          return;
        // column buttons
        case "column1":
          // peforms the column action but inputs the column
          columnAction(0);
          return;
        case "column2":
          // peforms the column action but inputs the column
          columnAction(1);
          return;
        case "column3":
          // peforms the column action but inputs the column
          columnAction(2);
          return;
        case "column4":
          // peforms the column action but inputs the column
          columnAction(3);
          return;
        case "column5":
          // peforms the column action but inputs the column
          columnAction(4);
          return;
        case "column6":
          // peforms the column action but inputs the column
          columnAction(5);
          return;
        case "column7":
          // peforms the column action but inputs the column
          columnAction(6);
          return;
      }
    }
    // ends the method
    return;
  }

  // Main method to start our program
  public static void main(String[] args){
    // Creates an instance of our program
    Main gui = new Main();
    // Lets the computer know to start it in the event thread
    SwingUtilities.invokeLater(gui);
  }
  // method to drop disks
  public void dropDisks(ImageIcon colour, int column) {
    // if the column is full tell the program and skip dropping the disk
    if (diskValues[column][0] == "empty") {
      // checks every spot in the column if its empty
      for (int i = 0; i < 6; i++) {
        // checks if the last disk is empty, if so place the disk
        if (i == 5 && diskValues[column][5].equals("empty")) {
          diskBoard[column][5].setIcon(colour);
          // find out what colour the disk is, and give that colour to disk values
          if (colour == redDisk) {
            // disk is red
            diskValues[column][5] = "red";
          } else {
            // disk is yellow
            diskValues[column][5] = "yellow";
          }
        // if the disk we are checking is empty then go onto the next disk
        } else if (diskValues[column][i].equals("empty")) {
          // does nothing, repeats loop
        // the disk we are checking is filled so we drop the disk before it 
        } else {
          diskBoard[column][i-1].setIcon(colour);
          // tells the program what value the disk has
          if (colour == redDisk) {
            // disk is red
            diskValues[column][i-1] = "red";
          } else {
            // disk is yellow
            diskValues[column][i-1] = "yellow";
          } 
          // stop the for loop since a disk has benn placed
          i = 6;
        }
      
      }
      // performs dectect win method
      detectWin(colour);
    }
    // if the top disk is full then tell the program the column is full
    if (diskValues[column][0] != "empty")  { 
      columnFull[column] = true;
    }
    return;
    
  }
  // what happens if a column button is pressed
  public void columnAction(int column) {
    // if the column is full then end the loop without swapping turns
    if (columnFull[column] == true) {
      return; 
    } 

    // first player goes if its their turn
    // red disk
    if (turnRed == true) {
      // swap turns
      turnRed = false;
      textBarGame.setIcon(turnTwoText);

      // drop disk
      dropDisks(redDisk, column);
    // yellows turn
    } else {
      // swap turns
      turnRed = true;
      textBarGame.setIcon(turnOneText);

      // drop disk
      dropDisks(yellowDisk,column);  
    }
    // find out if the player won
    if (win == true) {
      // end the method
      return;
    }
    // if its 2 player yellow go if its 1 player CPU go
    // activates the computers turn after the players turn
    if (singlePlayer == true) {
      // get the spot the CPU wants to place
      int columnCPU = findSpotCPU();

      // swap turns
      turnRed = true;
      textBarGame.setIcon(turnOneText);

      // drop the disk and swap turns
      dropDisks(yellowDisk, columnCPU);
    }

    // find out what columns are full
    int columnsFull = 0;
    for (int i = 0; i < columnButtons.length; i++) {
      if (columnFull[i] == true) {
        // column is full add one to the columns full variables
        columnsFull = columnsFull + 1;
      } else {
        // column is not full, so stop the loop
        i = columnButtons.length;
      }
    }

    // check if all the columns are full
    if (columnsFull == 7) {
      // no disk can be placed so end game as a draw
      allColumnsFull = true;
      System.out.println("draw");
      textBarGame.setIcon(drawText);
    }
    return;
  }

  // checks if theres a win 
  public void detectWin(ImageIcon colour){
    // convert the image icon into a string
    String newColour;
    if (colour == redDisk) {
      newColour = "red";
    } else {
      newColour = "yellow";
    }
    
    // checks each way to win
    horizontalWin(newColour);
    verticalWin(newColour);
    diagonalWin1(newColour);
    diagonalWin2(newColour);

    // check if theres a win, if there is then display it
    if (win == true) {
      if (colour == redDisk) {
        textBarGame.setIcon(wonOneText);
      } else {
        textBarGame.setIcon(wonTwoText);
      }
    }
  }
  // checks if theres a win horizontally
  public void horizontalWin(String newColour){
    // repeats for every disk on the left side of the board (ones that can have a connect 4 horizontally)
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 4; j++) {
        // checks if there are 4 disks in a line
        if (diskValues[j][i].equals(newColour) && diskValues[j + 1][i].equals(newColour) && diskValues[j + 2][i].equals(newColour) && diskValues[j + 3][i].equals(newColour)) {
          // set the disks to green
          diskBoard[j][i].setIcon(greenDisk);
          diskBoard[j + 1][i].setIcon(greenDisk);
          diskBoard[j + 2][i].setIcon(greenDisk);
          diskBoard[j + 3][i].setIcon(greenDisk);

          // activate win event
          win = true;

          // end loop
          return;
        }
      }
    }
  }
  // checks if theres a win vertically
  public void verticalWin(String newColour){
    // repeats for every disk on the top side of the board (ones that can have a connect 4 downwards)
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 7; j++) {
        // checks if there are 4 disks going down with the same value
        if (diskValues[j][i].equals(newColour) && diskValues[j][i + 1].equals(newColour) && diskValues[j][i + 2].equals(newColour) && diskValues[j][i + 3].equals(newColour)) {
          // set the disks to green
          diskBoard[j][i].setIcon(greenDisk);
          diskBoard[j][i + 1].setIcon(greenDisk);
          diskBoard[j][i + 2].setIcon(greenDisk);
          diskBoard[j][i + 3].setIcon(greenDisk);

          // activate win event
          win = true;

          // end loop
          return;
        }
      }
    }
  }
  // checks if theres a win diagonally (going up)
  public void diagonalWin1(String newColour){
    // check all the disks in the bottom right corner
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        // checks 4 disks in a line diagonally
        if (diskValues[j][i].equals(newColour) && diskValues[j + 1][i + 1].equals(newColour) && diskValues[j + 2][i + 2].equals(newColour) && diskValues[j + 3][i + 3].equals(newColour)) {
          // set the disks to green
          diskBoard[j][i].setIcon(greenDisk);
          diskBoard[j + 1][i + 1].setIcon(greenDisk);
          diskBoard[j + 2][i + 2].setIcon(greenDisk);
          diskBoard[j + 3][i + 3].setIcon(greenDisk);

          // activate win event
          win = true;

          // end loop
          return;
        }
      }
    }
  }
  // check if theres a win diagonally (going down)
  public void diagonalWin2(String newColour){
    // checks all the disks in the top left corner
    for (int i = 3; i < 6; i++) {
      for (int j = 0; j < 4; j++) {
        // checks if 4 disks of the same value are in a line
        if (diskValues[j][i].equals(newColour) && diskValues[j + 1][i - 1].equals(newColour) && diskValues[j + 2][i - 2].equals(newColour) && diskValues[j + 3][i - 3].equals(newColour)) {
          // set the disks to green
          diskBoard[j][i].setIcon(greenDisk);
          diskBoard[j + 1][i - 1].setIcon(greenDisk);
          diskBoard[j + 2][i - 2].setIcon(greenDisk);
          diskBoard[j + 3][i - 3].setIcon(greenDisk);

          // activate win event
          win = true;

          // end loop
          return;
        }
      }
    }
  }

  // the computers AI finding the best spot
  public int findSpotCPU() {
    // create a random number
    Random rand = new Random();

    // create the number
    int randInt = rand.nextInt(7);
    
    // declare the variable used to tell what column to output
    int columnChosen;

    
    // find if there are 3 disks in a row, if so then output the column to place the disk
    columnChosen = horizontal3CheckCPU1("yellow");
    if (columnChosen != 100) {
      System.out.println("Found it!");
      return columnChosen;
    }
    columnChosen = horizontal3CheckCPU1("red");
    if (columnChosen != 100) {
      System.out.println("Found it!");
      return columnChosen;
    }

    columnChosen = vertical3CheckCPU1("yellow");
    if (columnChosen != 100) {
      System.out.println("Found it!");
      return columnChosen;
    }
    columnChosen = vertical3CheckCPU1("red");
    if (columnChosen != 100) {
      System.out.println("Found it!");
      return columnChosen;
    }

    columnChosen = diagonal3CheckCPU1("yellow");
    if (columnChosen != 100) {
      System.out.println("Found it!");
      return columnChosen;
    }
    columnChosen = diagonal3CheckCPU1("red");
    if (columnChosen != 100) {
      System.out.println("Found it!");
      return columnChosen;
    }

    columnChosen = diagonal3CheckCPU2("yellow");
    if (columnChosen != 100) {
      System.out.println("Found it!");
      return columnChosen;
    }
    columnChosen = diagonal3CheckCPU2("red");
    if (columnChosen != 100) {
      System.out.println("Found it!");
      return columnChosen;
    }

    // find if there are 2 disks in a row, if so output the column
    columnChosen = horizontal2CheckCPU1("red");
    if (columnChosen != 100) {
      System.out.println("Found a spot!");
      return columnChosen;
    }
    columnChosen = horizontal2CheckCPU1("yellow");
    if (columnChosen != 100) {
      System.out.println("Found a spot!");
      return columnChosen;
    }

    columnChosen = vertical2CheckCPU1("red");
    if (columnChosen != 100) {
      System.out.println("Found a spot!");
      return columnChosen;
    }
    columnChosen = vertical2CheckCPU1("yellow");
    if (columnChosen != 100) {
      System.out.println("Found a spot!");
      return columnChosen;
    }

    columnChosen = diagonal2CheckCPU1("red");
    if (columnChosen != 100) {
      System.out.println("Found a spot!");
      return columnChosen;
    }
    columnChosen = diagonal2CheckCPU1("yellow");
    if (columnChosen != 100) {
      System.out.println("Found a spot!");
      return columnChosen;
    }

    columnChosen = diagonal2CheckCPU2("red");
    if (columnChosen != 100) {
      System.out.println("Found a spot!");
      return columnChosen;
    }
    columnChosen = diagonal2CheckCPU2("yellow");
    if (columnChosen != 100) {
      System.out.println("Found a spot!");
      return columnChosen;
    }

    // randomly select a column
    columnChosen = randInt;
    System.out.println("I can't see a thing!");
    return columnChosen;
  }

  public int horizontal3CheckCPU1(String colour) {
    // checks if there are any 3 disks in a row with empty space to the right
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 4; j++) {
        // checks if there are 4 disks in a line (empty at the end of the line)
        if (diskValues[j][i].equals(colour) && diskValues[j + 1][i].equals(colour) && diskValues[j + 2][i].equals(colour) && diskValues[j + 3][i].equals("empty")) {
          if (diskValues[j + 3][i + 1].equals("empty")) {
            // doesn't do nothing
            return 100;
          } else {
            // output the column
            return j + 3;
          }
        // checks if there are 4 disks in a line (empty at the start of the line)
        } else if (diskValues[j][i].equals("empty") && diskValues[j + 1][i].equals(colour) && diskValues[j + 2][i].equals(colour) && diskValues[j + 3][i].equals(colour)) {
          if (diskValues[j][i + 1].equals("empty")) {
            // doesn't do nothing
            return 100;
          } else {
            // output the column
            return j;
          }
        }
      }
    }
    return 100;
  }
  public int vertical3CheckCPU1(String colour) {
    // checks if there are any 3 disks in a row with empty space on the top
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 7; j++) {
        // checks if there are 3 disks going down with the same value with an empty space
        if (diskValues[j][i].equals("empty") && diskValues[j][i + 1].equals(colour) && diskValues[j][i + 2].equals(colour) && diskValues[j][i + 3].equals(colour)) {
          // tell tell CPU method the column
          return j;
        }
      }
    }
    return 100;
  }
  public int diagonal3CheckCPU1(String colour) {
    // checks all the disks in the bot left corner
    for (int i = 3; i < 6; i++) {
      for (int j = 0; j < 4; j++) {
        // checks if 4 disks of the same value are in a line (empty at the beginning)
        if (diskValues[j][i].equals("empty") && diskValues[j + 1][i - 1].equals(colour) && diskValues[j + 2][i - 2].equals(colour) && diskValues[j + 3][i - 3].equals(colour)) {
          // output the column
          return j;
        // checks if 4 disks of the same value are in a line (empty at the end)
        } else if (diskValues[j][i].equals(colour) && diskValues[j + 1][i - 1].equals(colour) && diskValues[j + 2][i - 2].equals(colour) && diskValues[j + 3][i - 3].equals("empty")) {
          // checks if its giving the player a win
          if (diskValues[j + 3][i - 2].equals("empty")) {
            // doesn't do nothing
            return 100;
          } else {
            // output the column
            return j + 3;
          }
        }
      }
    }
    return 100;
  }
  public int diagonal3CheckCPU2(String colour) {
    // checks all the disks in the top left corner
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        // checks if 4 disks of the same value are in a line (empty at the begining)
        if (diskValues[j][i].equals("empty") && diskValues[j + 1][i + 1].equals(colour) && diskValues[j + 2][i + 2].equals(colour) && diskValues[j + 3][i + 3].equals(colour)) {
          // checks if its giving the player a win
          if (diskValues[j][i + 1].equals("empty")) {
            // doesn't do nothing
            return 100;
          } else {
            // output the column
            return j;
          }
        // checks if 4 disks of the same value are in a line (empty at the end)
        } else if (diskValues[j][i].equals(colour) && diskValues[j + 1][i + 1].equals(colour) && diskValues[j + 2][i + 2].equals(colour) && diskValues[j + 3][i + 3].equals("empty")) {
          // output the column
          return j + 3;
        }
      }
    }
    return 100;
  }

  public int horizontal2CheckCPU1(String colour) {
    // checks if there are any 2 disks in a row with empty space to the right
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 5; j++) {
        // checks if there are 3 disks in a line
        if (diskValues[j][i].equals(colour) && diskValues[j + 1][i].equals(colour) && diskValues[j + 2][i].equals("empty")) {
          // end loop and tell CPU spot the coloumn
          return j + 2;
        } else if (diskValues[j][i].equals("empty") && diskValues[j + 1][i].equals(colour) && diskValues[j + 2][i].equals(colour)) {
          // end loop and tell CPU spot the coloumn
          return j;
        }
      }
    }
    return 100;
  }
  public int vertical2CheckCPU1(String colour) {
    // checks if there are any 3 disks in a row with empty space on the top
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 6; j++) {
        // checks if there are 3 disks going down with the same value with an empty space
        if (diskValues[j][i].equals("empty") && diskValues[j][i + 1].equals(colour) && diskValues[j][i + 2].equals(colour)) {
          // tell tell CPU method the column
          return j;
        }
      }
    }
    return 100;
  }
  public int diagonal2CheckCPU1(String colour) {
    // checks all the disks in the bot left corner
    for (int i = 2; i < 6; i++) {
      for (int j = 0; j < 5; j++) {
        // checks if 3 disks of the same value are in a line
        if (diskValues[j][i].equals("empty") && diskValues[j + 1][i - 1].equals(colour) && diskValues[j + 2][i - 2].equals(colour)) {
          // output the column
          return j;
        } else if (diskValues[j][i].equals(colour) && diskValues[j + 1][i - 1].equals(colour) && diskValues[j + 2][i - 2].equals("empty")) {
          // output the column
          return j + 2;
        }
      }
    }
    return 100;
  }
  public int diagonal2CheckCPU2(String colour) {
    // checks all the disks in the top left corner
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 5; j++) {
        // checks if 3 disks of the same value are in a line
        if (diskValues[j][i].equals("empty") && diskValues[j + 1][i + 1].equals(colour) && diskValues[j + 2][i + 2].equals(colour)) {
          // output the column
          return j;
        } else if (diskValues[j][i].equals(colour) && diskValues[j + 1][i + 1].equals(colour) && diskValues[j + 2][i + 2].equals("empty")) {
          // output the column
          return j + 2;
        }
      }
    }
    return 100;
  }
}
