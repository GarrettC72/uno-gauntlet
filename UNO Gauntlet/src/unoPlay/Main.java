package unoPlay;

import java.util.Stack;

public class Main {
	
	private static UnoPlayArea table;

	public static void main(String[] args) {
		table = new UnoPlayArea();
		Stack<UnoCard> deck = table.getDeck();
		
	}

}
