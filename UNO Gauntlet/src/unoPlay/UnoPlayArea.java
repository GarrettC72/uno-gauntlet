package unoPlay;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javafx.scene.paint.Color;

public class UnoPlayArea {
	
	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;
	private int currentPlayer;
	private Stack<UnoCard> deck;
	private Stack<UnoCard> pile;
	private Map<String, Integer> cardPoints = new HashMap<String, Integer>();
	
	public UnoPlayArea() {
		player1 = new Player();
		player2 = new Player();
		player3 = new Player();
		player4 = new Player();
		createDeck();
		pile = new Stack<UnoCard>();
		currentPlayer = (int)(Math.random() * 4);
	}
	
	public void createDeck() {
		deck = new Stack<UnoCard>();
		deck.push(new UnoCard(Color.BLUE,"zero","blue_zero_card.png",0));
		Collections.shuffle(deck);
	}
	
	public void shuffleDeck() {
		deck.addAll(pile);
		Collections.shuffle(deck);
	}

}
