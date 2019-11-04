package unoPlay;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class UnoPlayArea {
	
	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;
	private Player currentPlayer;
	private int currentPosition;
	private boolean backwards;
	private Stack<UnoCard> deck;
	private Stack<UnoCard> pile;
	private Map<String, Integer> cardPoints;
	
	public UnoPlayArea() {
		player1 = new Player();
		player2 = new Player();
		player3 = new Player();
		player4 = new Player();
		createDeck();
		pile = new Stack<UnoCard>();
		currentPosition = (int)(Math.random() * 4);
	}
	
	public void createDeck() {
		cardPoints = new HashMap<String, Integer>();
		cardPoints.put("zero", 0);
		cardPoints.put("one", 1);
		cardPoints.put("two", 2);
		cardPoints.put("three", 3);
		cardPoints.put("four", 4);
		cardPoints.put("five", 5);
		cardPoints.put("six", 6);
		cardPoints.put("seven", 7);
		cardPoints.put("eight", 8);
		cardPoints.put("nine", 9);
		cardPoints.put("draw_2", 20);
		cardPoints.put("skip", 20);
		cardPoints.put("reverse", 20);
		cardPoints.put("wild", 50);
		cardPoints.put("wild_draw_4", 50);
		deck = new Stack<UnoCard>();
		String[] types = {"zero","one","two","three","four","five","six","seven","eight","nine",
				"draw_2","skip","reverse","wild","wild_draw_4"};
		String[] colors = {"blue", "green", "red", "yellow", "black"};
		for(int i = 0; i < colors.length - 1; i++) {
			deck.push(new UnoCard(colors[i],types[0],colors[i] + "_" + types[0] + "_card.png",cardPoints.get(types[0])));
			for(int j = 1; j < types.length - 2; j++) {
				deck.push(new UnoCard(colors[i],types[j],colors[i] + "_" + types[j] + "_card.png",cardPoints.get(types[j])));
				deck.push(new UnoCard(colors[i],types[j],colors[i] + "_" + types[j] + "_card.png",cardPoints.get(types[j])));
			}
		}
		for(int i = 0; i < 4; i++) {
			deck.push(new UnoCard(colors[colors.length - 1],types[types.length - 2],types[types.length - 2] + "_card.png",cardPoints.get(types[types.length - 2])));
			deck.push(new UnoCard(colors[colors.length - 1],types[types.length - 1],types[types.length - 1] + "_card.png",cardPoints.get(types[types.length - 1])));
		}
		Collections.shuffle(deck);
	}
	
	public void shuffleDeck() {
		deck.addAll(pile);
		Collections.shuffle(deck);
	}
	
	public Stack<UnoCard> getDeck() {
		return deck;
	}

	public Stack<UnoCard> getPile() {
		return pile;
	}

	public void changeCurrentPlayer(int change) {
		if(currentPosition + change < 0) {
			currentPosition = currentPosition + change + 4;
		}else {
			currentPosition = (currentPosition + change) % 4;
		}
	}

	public boolean isBackwards() {
		return backwards;
	}

	public void setBackwards(boolean backwards) {
		this.backwards = backwards;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

}
