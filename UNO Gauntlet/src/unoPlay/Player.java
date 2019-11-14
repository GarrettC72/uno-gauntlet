package unoPlay;

import java.util.ArrayList;
import java.util.Comparator;

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
	
	public void organizeHand(Comparator<UnoCard> comparator) {
		for(int i = 0; i < hand.size() - 1; i++) {
			int minIndex = i;
			for(int j = i + 1; j < hand.size(); j++) {
				if(comparator.compare(hand.get(j),hand.get(minIndex)) < 0) {
					minIndex = j;
				}
			}
			if(minIndex != i) {
				UnoCard temp = hand.get(minIndex);
				hand.set(minIndex, hand.get(i));
				hand.set(i, temp);
			}
		}
	}

}
