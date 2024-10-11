import java.util.Scanner;  // Importerar Scanner-klassen för att kunna läsa in användarens input

// Huvudklassen för spelet
class TicTacToe {
    private Board board;            // Skapar en instans av klassen Board som ärspelplanen
    private Player player1;         // Skapar en instans för spelare 1
    private Player player2;         // Skapar en instans för spelare 2
    private Player currentPlayer;   // Håller reda på vilken spelare som har turen

    // Konstruktor som initialiserar spelet
    public TicTacToe() {
        board = new Board();            // Initierar en ny spelplan
        player1 = new Player('X');      // Spelare 1 använder 'X'
        player2 = new Player('O');      // Spelare 2 använder 'O'
        currentPlayer = player1;        // Sätter spelare 1 som den som börjar
    }

    // Metoden som startar och driver spelet
    public void play() {
        boolean gameIsRunning = true;   // Håller spelet igång tills någon vinner eller det blir oavgjort
        while (gameIsRunning) {         // loop som körs sålänge gameIsRunning är true
            board.displayBoard();       // Visar spelplanen på skärmen
            System.out.println("It is player " + currentPlayer.getSymbol() + "'s time to make a move.");
            boolean validMove = false;  // Variabel för att kontrollera om ett drag är giltigt

            // Loopa tills ett giltigt drag görs
            while (!validMove) {
                int[] move = currentPlayer.getMove();            // Hämtar spelarens inmatade rad och kolumn
                validMove = board.makeMove(move[0], move[1], currentPlayer.getSymbol());  // Försöker göra ett drag
                if (!validMove) {  // Om draget är ogiltigt sker detta
                    System.out.println("You can't make this move. Try again. Make sure to enter a row and column (0, 1, or 2) separated by a space"); // Meddelar att draget var ogiltigt
                }
            }

            // Kolla om det aktuella draget leder till en vinst
            if (board.checkWin(currentPlayer.getSymbol())) {
                board.displayBoard();    // Visar den uppdaterade spelplanen
                System.out.println("Player " + currentPlayer.getSymbol() + " wins the game!");  // Meddelar vinnaren
                gameIsRunning = false;   // Avslutar spelet
            }
            // Kolla om spelplanen är full (oavgjort)
            else if (board.isFull()) {
                board.displayBoard();    // Visar den spelplanen
                System.out.println("DRAW");  // Meddelar att spelet är oavgjort
                gameIsRunning = false;   // Avslutar spelet
            }
            // Om ingen har vunnit och spelet inte är oavgjort
            else {
                switchPlayer();          // Växla till nästa spelare
            }

            // Om spelet har avslutats, starta ett nytt spel
            if (!gameIsRunning) {
                System.out.println("Game over. Starting a new game");
                board.clearBoard();      // Rensar spelplanen för ett nytt spel
                gameIsRunning = true;    // Återställer spelet till att köra igen
            }
        }
    }

    // Metod för att växla mellan spelarna
    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;  // Växlar till nästa spelare
    }

    // Huvudmetoden som startar programmet
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();  // Skapar en ny instans av TicTacToe-spelet
        game.play();  // Startar spelet
    }
}

// Klass som representerar en spelare
class Player {
    private char symbol;      // Variabel som håller spelarens symbol (X eller O) - använder char då vi lagrar 1 tecken.
    private Scanner scanner;  // Scanner-objekt för att läsa in användarens input

    // Konstruktor som initierar spelaren med en given symbol
    public Player(char symbol) {
        this.symbol = symbol;             // Sätter spelarens symbol
        this.scanner = new Scanner(System.in);  // Initierar Scanner för att läsa användarens input
    }

    // Metod som returnerar spelarens symbol
    public char getSymbol() {
        return symbol;  // Returnerar spelarens symbol (X eller O)
    }

    // Metod som hämtar spelarens drag (rad och kolumn)
    public int[] getMove() {
        System.out.print("Enter the row and column numbers (0, 1, or 2), separated by a space: ");
        int row = scanner.nextInt();  // Läser in raden från användaren
        int col = scanner.nextInt();  // Läser in kolumnen från användaren
        return new int[]{row, col};   // Returnerar ett array med rad och kolumn
    }
}

// Klass som representerar spelplanen
class Board {
    private char[][] board;  // En 2D-array som representerar 3x3 spelplanen

    // Konstruktor som skapar en tom spelplan
    public Board() {
        board = new char[3][3];  // Initierar spelplanen som en 3x3 matris
        clearBoard();  // Rensar spelplanen till att vara tom
    }

    // Metod som rensar spelplanen (fyller med *)
    public void clearBoard() {
        for (int i = 0; i < 3; i++) {  // Loopar igenom varje rad
            for (int j = 0; j < 3; j++) {  // Loopar igenom varje kolumn
                board[i][j] = '*';  // Sätter varje position till *
            }
        }
    }

    // Metod som visar spelplanen
    public void displayBoard() {
        for (int i = 0; i < 3; i++) {  // Loopar igenom raderna
            for (int j = 0; j < 3; j++) {  // Loopar igenom kolumnerna
                System.out.print(board[i][j] + " ");  // Skriver ut varje position följt av ett mellanrum
            }
            System.out.println();  // Ny rad efter varje rad i spelplanen
        }
    }

    // Metod som försöker göra ett drag
    public boolean makeMove(int row, int col, char symbol) {
        // Kontrollerar om raden och kolumnen är giltiga och om rutan är tom
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == '*') {
            board[row][col] = symbol;  // Sätter symbolen på den angivna positionen
            return true;  // Returnerar true om draget är giltigt
        }
        return false;  // Returnerar false om draget är ogiltigt
    }

    // Metod som kontrollerar om en spelare har vunnit
    public boolean checkWin(char symbol) {
        // Kollar om någon rad eller kolumn har samma symbol
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) ||
                    (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol)) {
                return true;  // Returnerar true om det finns en vinnande rad eller kolumn
            }
        }

        // Kollar diagonalerna
        if ((board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
                (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol)) {
            return true;  // Returnerar true om någon diagonal är vinnande
        }

        return false;  // Returnerar false om ingen har vunnit
    }

    // Metod som kontrollerar om spelplanen är full
    public boolean isFull() {
        for (int i = 0; i < 3; i++) {  // Loopar igenom varje rad
            for (int j = 0; j < 3; j++) {  // Loopar igenom varje kolumn
                if (board[i][j] == '*') {  // Om det finns en tom ruta, är spelplanen inte full
                    return false;
                }
            }
        }
        return true;  // Returnerar true om alla rutor är fyllda
    }
}
