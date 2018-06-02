package connectfour;

public class App {
    public static void main(String[] args) {
        System.out.print("\n\nConnect Four, The Best PC Experience!\n\n");

        try {
            GameBoard gameBoard = new GameBoard();
            gameBoard.runGameLoop();
        } catch(Exception ex){
            ex.printStackTrace();
            // TBD .. !
        }
    }
}
