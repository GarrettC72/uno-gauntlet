package unoPlay;

import java.util.ArrayList;

public class Player {
	
	private int pointTotal;
	private ArrayList<UnoCard> hand;
	
	public Player() {
		pointTotal = 0;
		hand = new ArrayList<UnoCard>();
	}
	
	public int getPointTotal() {
		return pointTotal;
	}

	public void setPointTotal(int pointTotal) {
		this.pointTotal = pointTotal;
	}

	public ArrayList<UnoCard> getHand() {
		return hand;
	}

	public void addCard(UnoCard card) {
		hand.add(card);
	}

}
