import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class tic_tac_toe {
	
	static List<Integer> playerPositions = new ArrayList<>();
	static List<Integer> cpuPositions = new ArrayList<>();
	static List<Integer> player1Positions = new ArrayList<>();
	static List<Integer> player2Positions = new ArrayList<>();
	
	public static void main(String[] args) {
		char [][] gameBoard = {
				{' ','|',' ','|',' '},
				{'-','+','-','+','-'},
				{' ','|',' ','|',' '},
				{'-','+','-','+','-'},
				{' ','|',' ','|',' '}};
		Scanner scan = new Scanner(System.in);
		option(gameBoard, scan);
		scan.close();
	}
	

	private static void chart(char[][] gameBoard) {
		for(char[] row:gameBoard) {
			for(char c:row) {
				System.out.print(c);
			}System.out.println();
		}
	}

	private static void option(char[][] gameBoard, Scanner scan) {
		System.out.println("""
				Option 1: vs CPU
				or
				Option 2: vs Player2
				""");
		int option = scan.nextInt();
		if (option == 1) {
			vsCpuMatch(gameBoard, scan);
		}else if(option == 2) {
			vsPlayerMatch(gameBoard, scan);
		}else {
			System.out.println("Wrong Input");
			option(gameBoard, scan);
		}
	}

	private static void vsPlayerMatch(char[][] gameBoard, Scanner scan) {
		while(true) {
			chart(gameBoard);
			System.out.println("Player 1: Placement (1-9)");
			int player1Pos = scan.nextInt();
			while (player1Positions.contains(player1Pos)||player2Positions.contains(player1Pos)) {
				System.out.println("Duplicate input! Placement (1-9)");
				player1Pos = scan.nextInt();
			}
			vsPlayerMatchPosition(gameBoard, player1Pos, "user1");
			if(playerMatchWinner().length() > 0) {
				chart(gameBoard);
				System.out.println(playerMatchWinner());
				break;
			}
			chart(gameBoard);
			System.out.println("Player 2: Placement (1-9)");
			int player2Pos = scan.nextInt();
			while (player1Positions.contains(player2Pos)||player2Positions.contains(player2Pos)) {
				System.out.println("Duplicate input! Placement (1-9)");
				player2Pos = scan.nextInt();
			}
			vsPlayerMatchPosition(gameBoard, player2Pos, "user2");
			if(playerMatchWinner().length() > 0) {
				chart(gameBoard);
				System.out.println(playerMatchWinner());
				break;
			}
		}
	}

	private static void vsPlayerMatchPosition(char[][] gameBoard, int pos, String player) {
		char symbol = ' ';
		if(player.equals("user1")) {
			symbol = 'X';
			player1Positions.add(pos);
		}else if (player.equals("user2")){
			symbol = 'O';
			player2Positions.add(pos);
		}
		insertionForPosition(gameBoard, pos, symbol);
	}

	private static String playerMatchWinner() {
		List<List<Integer>> win = winnableSituations();
		for (List<Integer> row:win) {
			if (player1Positions.containsAll(row)) {
				return "Player1 won";
			} else if(player2Positions.containsAll(row)) {
				return "Player2 won";
			} else if(player1Positions.size()+player2Positions.size() == 9) {
				return "Draw";
			}
		}
		return "";
	}
	
	private static void vsCpuMatch(char[][] gameBoard, Scanner scan) {
		while(true) {
			chart(gameBoard);
			System.out.println("Placement (1-9)");
			int playerPos = scan.nextInt();
			while (playerPositions.contains(playerPos)||cpuPositions.contains(playerPos)) {
				System.out.println("Duplicate input! Placement (1-9)");
				playerPos = scan.nextInt();
			}
			vsCpuMatchPosition(gameBoard, playerPos, "user1");
			if(cpuMatchWinner().length() > 0) {
				chart(gameBoard);
				System.out.println(cpuMatchWinner());
				break;
			}
			
			Random random = new Random();
			int cpuPos = random.nextInt(9)+1;
			while (playerPositions.contains(cpuPos)||cpuPositions.contains(cpuPos)) {
				cpuPos = random.nextInt(9)+1;
			}
			vsCpuMatchPosition(gameBoard, cpuPos, "user2");
			if(cpuMatchWinner().length() > 0) {
				chart(gameBoard);
				System.out.println(cpuMatchWinner());
				break;
			}
		}
	}
	
	private static void vsCpuMatchPosition(char[][] gameBoard, int pos, String player) {
		char symbol = ' ';
		if(player.equals("user1")) {
			symbol = 'X';
			playerPositions.add(pos);
		}else if (player.equals("user2")){
			symbol = 'O';
			cpuPositions.add(pos);
		}
		insertionForPosition(gameBoard, pos, symbol);
	}
	
	private static String cpuMatchWinner() {
		List<List<Integer>> win = winnableSituations();
		for (List<Integer> row:win) {
			if (playerPositions.containsAll(row)) {
				return "You won";
			} else if(cpuPositions.containsAll(row)) {
				return "cpu won";
			} else if(playerPositions.size()+cpuPositions.size() == 9) {
				return "Draw";
			}
		}
		return "";
	}
	
	private static void insertionForPosition(char[][] gameBoard, int pos, char symbol) {
		switch(pos){
			case 1: gameBoard[0][0] = symbol;break;
			case 2: gameBoard[0][2] = symbol;break;
			case 3: gameBoard[0][4] = symbol;break;
			case 4: gameBoard[2][0] = symbol;break;
			case 5: gameBoard[2][2] = symbol;break;
			case 6: gameBoard[2][4] = symbol;break;
			case 7: gameBoard[4][0] = symbol;break;
			case 8: gameBoard[4][2] = symbol;break;
			case 9: gameBoard[4][4] = symbol;break;
			default: break;
		}
	}
	
	private static List<List<Integer>> winnableSituations() {
		List<Integer> topRow = List.of(1,2,3);
		List<Integer> midRow = List.of(4,5,6);
		List<Integer> botRow = List.of(7,8,9);
		List<Integer> leftCol = List.of(1,4,7);
		List<Integer> midCol = List.of(2,5,8);
		List<Integer> rightCol = List.of(3,6,9);
		List<Integer> cross1 = List.of(1,5,9);
		List<Integer> cross2 = List.of(3,5,7);	
		List<List<Integer>> win = new ArrayList<>();
		win.add(topRow);
		win.add(midRow);
		win.add(botRow);
		win.add(leftCol);
		win.add(midCol);
		win.add(rightCol);
		win.add(cross1);
		win.add(cross2);
		return win;
	}

}
