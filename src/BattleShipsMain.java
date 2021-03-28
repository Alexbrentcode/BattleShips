import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BattleShipsMain {

    public static void main(String[] args) {
       gameBoard.gameState();
    }

    public static class gameBoard {

        /*
        * The gameBoard class contains all of the methods and parameters of the BattleShips board. A 10x10 2d arr is
        * created as the game board is always fixed.
        * */
        String[][] shipCoordinates = new String[3][5];
        char[][] arrayGrid = new char[10][10];

        /*Reset method assumes the board is incorrectly populated so it marks every tile as unvisited.*/

        public void reset() {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    arrayGrid[i][j] = '~';
                }
            }
            plotShips();
            System.out.println("New Game - Good Luck!");
            printGrid();
        }

        /*Prints content of the gameGrid to console*/

        public void printGrid() {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    System.out.print(arrayGrid[i][j] + " ");
                }
                System.out.println();
            }
        }

        /*
        * Generates the starting coords of a ship and runs a series of checks to determine how to position the other
        * ship coords. By comparing the 2 values and getting the smaller number, it allows for ships to be placed both
        * horizontally and vertically at random. This loops is iterated through for the 3 required ships. Once a ship
        * has been added, it's list of coords in checked against the current coordinates to check that there is no
        * overlap. The resulting iterations taken is then input to the console. Finally the first and last values of
        * the coords array are replaced by values outside of the gameBoard to fit the spec of 1 BattleShip & 2 Destroyers
        * */

        public void plotShips() {
            ArrayList<String> purityCheck = new ArrayList<>();
            int counter = 0;
            int xVal, yVal;
            int iterations = 0;
            while (counter < 3) {
                xVal = (int) (Math.random() * 9);
                yVal = (int) (Math.random() * 9);

                if (xVal <= yVal) {
                    if (xVal < 5) {
                        for (int i = 0; i < 5; i++) {
                            String temp = xVal + i + "" + yVal;
                            shipCoordinates[counter][i] = temp;
                            purityCheck.add(temp);
                        }
                    } else {
                        for (int i = 0; i < 5; i++) {
                            String temp = yVal + "" + (xVal - i);
                            shipCoordinates[counter][i] = temp;
                            purityCheck.add(temp);
                        }
                    }
                }
                if (xVal > yVal) {
                    if (yVal < 5) {
                        for (int i = 0; i < 5; i++) {
                            String temp = yVal + i + "" + xVal;
                            shipCoordinates[counter][i] = temp;
                            purityCheck.add(temp);
                        }
                    } else {
                        for (int i = 0; i < 5; i++) {
                            String temp = xVal + "" + (yVal - i);
                            shipCoordinates[counter][i] = temp;
                            purityCheck.add(temp);
                        }
                    }
                }

                outerloop:
                for (int k = 0; k < purityCheck.size(); k++) {
                    for (int j = k + 1; j < purityCheck.size(); j++) {
                        if (purityCheck.get(k).equals(purityCheck.get(j))) {
                            iterations++;
                            counter--;
                            purityCheck.subList(purityCheck.size() - 5, purityCheck.size()).clear();
                            break outerloop;
                        }

                    }

                }
                counter++;
            }
            shipCoordinates[0][0] = "KK";
            shipCoordinates[2][4] = "KK";
            System.out.println("Spaces uniquely populated after " + iterations + " iteration(s)");

/*            for (int q = 0; q < 3; q++) {
                for (int r = 0; r < 5; r++) {
                    System.out.print(shipCoordinates[q][r] + " ");
                }
            }*/
        }

        /*
        * The below method takes the user input and parses it for validity using regEx. If the pattern doesn't match
        * the regEx, then the method loops until correct input has been submitted.
        * */

        public static String parseInput() {
            Scanner inputReader = new Scanner(System.in);
            String input = inputReader.nextLine().toUpperCase();
            Pattern pattern = Pattern.compile("[A-J{1}][0-9{1}]");
            Matcher patternMatcher = pattern.matcher(input);
            System.out.println(input);
            boolean correctInput = patternMatcher.find();
            while (!correctInput) {
                if (!patternMatcher.matches()) {
                    System.out.println("Invalid input, try again.");
                    input = inputReader.nextLine();
                    patternMatcher = pattern.matcher(input);
                } else {
                    correctInput = true;
                }
            }
            return input;
        }

        /*
        * Using a hashmap to represent the values of the A-J inputs, the method splits the input and checks if the
        * coordiantes represented as numbers is in the array of shipCoordinates.
        * */

        public boolean playerShot(String input) {
            String hitMsg = "Good shot!";
            String missMsg = "Enter another coordinate...";
            HashMap<String, Integer> inputKey = new HashMap<String, Integer>() {{
                put("A", 0); put("B", 1); put("C", 2); put("D", 3); put("E", 4); put("F", 5); put("G", 6); put("H", 7); put("I", 8); put("J", 9);
            }};

            String[] inputChars = input.split("", 0);
            String numericInput = inputKey.get(inputChars[0]) + "" + inputChars[1];

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 5; j++) {
                    if (shipCoordinates[i][j].contains(numericInput)) {
                        arrayGrid[Integer.parseInt(inputChars[1])][inputKey.get(inputChars[0])] = 'O';
                        System.out.println(hitMsg);
                        return true;
                    } else {
                        arrayGrid[Integer.parseInt(inputChars[1])][inputKey.get(inputChars[0])] = 'X';
                    }
                }
            }
            System.out.println(missMsg);
            return false;
        }

        /*
        * Method below controls the running of the game, and initializes play through the reset() method.
        * If the player wishes to play again, there is a final loop clause at the end to reset.
        * */

        public static void gameState() {
            int hitCounter = 0;
            int moveCounter = 0;
            boolean isGame = false;
            gameBoard test = new gameBoard();
            test.reset();
            while (!isGame) {
                moveCounter++;
                if(test.playerShot(parseInput())){
                    hitCounter++;
                }
                test.printGrid();
                if(hitCounter == 13){
                    System.out.println("You won in " + moveCounter + " moves!" );
                    System.out.println("Type YES to play again.");
                    Scanner inputReader = new Scanner(System.in);
                    String input = inputReader.nextLine().toUpperCase();

                    if(input.equals("YES")) {
                        System.out.println("reset");
                        test.reset();
                        hitCounter = 0;
                        break;
                } else {
                        isGame = true;
                    }
                }
            }
        }

    }
}

