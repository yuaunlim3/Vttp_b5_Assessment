package vttp.batch5.sdf.task02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		String filename = "";
		if (args.length > 0) {
			filename = args[0];
		}

		System.out.printf("Processing: %s",filename);
		File file = new File(filename);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		String[][] board = new String[3][3];

		String line = "A";

			while (line != null) {
				for(int x = 0 ; x < 3 ; x++){
				line = br.readLine();
				if (line == null) {
					break;
				}
				String[] inputs = line.split("");
				for(int y = 0; y<3;y++){
					board[x][y] = inputs[y];
				}
	
			}
		}

		System.out.println("Board:");
		//printBoard(board);

		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				if (board[x][y].equals(".")) {
					System.out.println(" ");
					board[x][y] = "X";
					int score = checkMove(board);
					board[x][y] = ".";
					System.out.printf("x = %d y = %d  score = %d\n",x,y,score);
				}
			}
		}


	}

	private static boolean isMovesLeft(String[][] board) {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				if (board[x][y].equals(".")) {
					return true;
				}
			}
		}
		return false;
	}

	private static String getWinner(String[][] board) {
		String winner = "";
		// check each row:
		for (int x = 0; x < board.length; x++) {
			if (board[x][0].equals(board[x][1]) && board[x][1].equals(board[x][2]) && !board[x][0].equals(".")) {
				winner = board[x][0];
			}
		}

		// Check columns:
		for (int x = 0; x < board[0].length; x++) {
			if (board[0][x].equals(board[1][x]) && board[1][x].equals(board[2][x]) && !board[0][x].equals(".")) {
				winner = board[0][x];
			}
		}

		// Check left to right diagonals:
		if (board[2][0].equals(board[1][1]) && board[0][2].equals(board[2][0]) && !board[2][0].equals(".")) {
			winner = board[2][0];
		}

		// Check right to left diagonals:
		if (board[0][0].equals(board[1][1]) && board[0][0].equals( board[2][2]) && !board[0][0].equals(".")) {
			winner = board[0][0];
		}

		if (!isMovesLeft(board)) {
			winner = "tie";
		}
		return winner;
	}

	private static void printBoard(String[][] board){
		for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                System.out.print(board[x][y]);
            }
            System.out.print("\n");
        }
	}

	public static int checkMove(String[][] board){
		//printBoard(board);
		if(getWinner(board).equals("X")){
			return 1;
		}
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				if (board[x][y].equals(".")) {
					board[x][y] = "O";
					if(getWinner(board).equals("O")){
						board[x][y] = ".";
						return -1;
					}
					board[x][y] = ".";
				}
			}
		}
		return 0;

	}

	

}
