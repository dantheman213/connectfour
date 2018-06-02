package connectfour;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameBoard {
    private final int GRID_X = 4;
    private final int GRID_Y = 4;

    private static Random random;
    private List<List<Integer>> boardData;
    private GameStatusEnum gameStatus;


    public enum GameStatusEnum {
        PLAYER_1,
        PLAYER_2,
        GAME_OVER
    };

    public GameBoard() {
        if(random == null) {
            random = new Random();
        }

        gameStatus = GameStatusEnum.PLAYER_1;
        boardData = new ArrayList<List<Integer>>();

        for(int y = 0; y < GRID_Y; y++) {
            List<Integer> row = new ArrayList<Integer>();

            for(int x = 0; x < GRID_X; x++) {
                row.add(null);
            }

            boardData.add(row);
        }
    }

    private Integer getGameMove(int x, int y) throws Exception {
        if(x < 0 || x > GRID_X-1 || y < 0 || y > GRID_Y-1)
            throw new Exception("Out of bounds.");

        return boardData.get(y).get(x);
    }

    private String convertTokenToDisplay(Integer token) throws Exception {
        String ret;

        if(token == null)
            return "-";

        switch(token) {
            case 1:
                ret = "X";
                break;
            case 2:
                ret = "Y";
                break;
            default:
                ret = "-";
                break;
        }

        return ret;
    }

    private void setGameMove(int x, int player) throws Exception {
        if(x < 0 || x > GRID_X-1)
            throw new Exception("Out of bounds.");

        for(int y = GRID_Y-1; y > -1; y--) {
            if(boardData.get(y).get(x) == null) {
                boardData.get(y).set(x, player);
                return;
            }
        }

        throw new Exception("Overflow.");
    }

    private void drawBoard() throws Exception {
        String columnLine = "";

        for(int y = 0; y < GRID_Y; y++) {
            String line = "";

            for(int x = 0; x < GRID_X; x++) {
                line += String.format("%s | ", convertTokenToDisplay(getGameMove(x, y)));
            }

            columnLine += String.format("%d   ", y);
            System.out.println(line);
        }

        System.out.print(columnLine + "\n\n");
    }

    public void runGameLoop() throws Exception {
        boolean bShouldRun = true;

        while(bShouldRun) {
            drawBoard();

            if(gameStatus == GameStatusEnum.PLAYER_1) {
                // human
                System.out.print("Player 1, what is your move? ");
                String input = System.console().readLine().trim().toLowerCase();
                System.out.print("\n\n");

                // TBD: CHECK TO SEE IF THE USER PROVIDED LEGAL INPUT

                setGameMove(Integer.parseInt(input), 1);
                gameStatus = GameStatusEnum.PLAYER_2;
            } else if(gameStatus == GameStatusEnum.PLAYER_2) {
                // AI
                System.out.print("Player 2 (the computer) is thinking...\n\n");
                TimeUnit.SECONDS.sleep(2);
                setGameMove(generateEnemyMove(), 2);

                gameStatus = GameStatusEnum.PLAYER_1;
            } else if(gameStatus == GameStatusEnum.GAME_OVER) {
                // TBD
            }
        }
    }

    private int generateEnemyMove() throws Exception {
        boolean isComplete = false;

        while(!isComplete) {
            int x = generateRandomNumber(0, GRID_X-1);
            if(boardData.get(0).get(x) == null) {
                // legal move
                return x;
            }
        }

        return -1; // never should get here
    }

    private int generateRandomNumber(int min, int max) {
        return (random.nextInt(max + 1 - min) + min);
    }
}
