package unoPlay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class UnoPlayArea {
	
	private Player[] players = {new Player(),new Player(),new Player(),new Player()};
	private int currentPosition;
	private boolean hard;
	private boolean backwards;
	private Stack<UnoCard> deck;
	private Stack<UnoCard> pile;
	private Map<String, Integer> cardPoints;
	
	public UnoPlayArea(boolean hard) {
		createDeck();
		pile = new Stack<UnoCard>();
		currentPosition = 0;
		backwards = false;
		this.hard = hard;
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < players.length; j++) {
				players[j].addCard(deck.pop());
			}
		}
		for(int i = 1; i < players.length; i++) {
			organizePlayerHand(players[i]);
		}
		while(deck.peek().getColor().equals("black") || deck.peek().getCardType().equals("draw_2") 
				|| deck.peek().getCardType().equals("skip") || deck.peek().getCardType().equals("reverse")) {
			Collections.shuffle(deck);
		}
		pile.push(deck.pop());
	}
	
	public void restart() {
		for(int i = 0; i < players.length; i++) {
			players[i].getHand().clear();
		}
		createDeck();
		pile = new Stack<UnoCard>();
		currentPosition = 0;
		backwards = false;
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < players.length; j++) {
				players[j].addCard(deck.pop());
			}
		}
		for(int i = 1; i < players.length; i++) {
			organizePlayerHand(players[i]);
		}
		while(deck.peek().getColor().equals("black") || deck.peek().getCardType().equals("draw_2") 
				|| deck.peek().getCardType().equals("skip") || deck.peek().getCardType().equals("reverse")) {
			Collections.shuffle(deck);
		}
		pile.push(deck.pop());
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
			deck.push(new UnoCard(colors[i],types[0],"resources/" + colors[i] + "_" + types[0] + "_card.png",cardPoints.get(types[0])));
			for(int j = 1; j < types.length - 2; j++) {
				deck.push(new UnoCard(colors[i],types[j],"resources/" + colors[i] + "_" + types[j] + "_card.png",cardPoints.get(types[j])));
				deck.push(new UnoCard(colors[i],types[j],"resources/" + colors[i] + "_" + types[j] + "_card.png",cardPoints.get(types[j])));
			}
		}
		for(int i = 0; i < 4; i++) {
			deck.push(new UnoCard(colors[colors.length - 1],types[types.length - 2],"resources/" + types[types.length - 2] + "_card.png",cardPoints.get(types[types.length - 2])));
			deck.push(new UnoCard(colors[colors.length - 1],types[types.length - 1],"resources/" + types[types.length - 1] + "_card.png",cardPoints.get(types[types.length - 1])));
		}
		Collections.shuffle(deck);
	}
	
	public void shuffleDeck() {
		UnoCard top = pile.pop();
		deck.addAll(pile);
		Collections.shuffle(deck);
		pile.clear();
		pile.push(top);
	}

	public void changeCurrentPosition(int change) {
		if(currentPosition + change < 0) {
			currentPosition = currentPosition + change + 4;
		}else {
			currentPosition = (currentPosition + change) % 4;
		}
	}
	
	public void organizePlayerHand(Player player) {
		if(hard) {
			player.organizeHand(new Comparator<UnoCard>() {

				@Override
				public int compare(UnoCard o1, UnoCard o2) {
					if(o1.getPointValue() < o2.getPointValue()) {
						return 1;
					}else if(o1.getPointValue() == o2.getPointValue()) {
						return 0;
					}else {
						return -1;
					}
				}
				
			});
		}else {
			player.organizeHand(new Comparator<UnoCard>() {

				@Override
				public int compare(UnoCard o1, UnoCard o2) {
					if(o1.getPointValue() < o2.getPointValue()) {
						return -1;
					}else if(o1.getPointValue() == o2.getPointValue()) {
						return 0;
					}else {
						return 1;
					}
				}
				
			});
		}
	}
	
	public boolean checkVictorious(Player player) {
		if(player.getHand().size() == 0) {
			for(int i = 0; i < players.length; i++) {
				if(!players[i].equals(player)) {
					Player loser = players[i];
					int points = loser.getHand().stream()
							.mapToInt(UnoCard::getPointValue)
							.sum();
					loser.setPointTotal(loser.getPointTotal() + points);
				}
			}
			return true;
		}
		return false;
	}
	
	public boolean computerTurn() {
		Player currentPlayer = players[currentPosition];
		ArrayList<UnoCard> hand = currentPlayer.getHand();
		if(!canPlayCard(currentPlayer)) {
			UnoCard topCard = deck.pop();
			if(canPlayCard(topCard)) {
				pile.push(topCard);
				applyEffect(topCard);
				if(topCard.getColor().equals("black")) {
					String[] colors = {"blue", "green", "red", "yellow"};
					int c = (int)(Math.random() * 4);
					pile.peek().setColor(colors[c]);
					pile.peek().setImage("resources/" + colors[c] + "_" + pile.peek().getCardType() + "_card.png");
				}
				if(deck.size() == 0) {
					shuffleDeck();
				}
				return true;
			}else {
				currentPlayer.addCard(topCard);
				organizePlayerHand(currentPlayer);
				applyEffect(null);
				if(deck.size() == 0) {
					shuffleDeck();
				}
				return false;
			}
		}else {
			for(int i = 0; i < hand.size(); i++) {
				if(canPlayCard(hand.get(i))) {
					UnoCard card = hand.get(i);
					pile.push(hand.remove(i));
					applyEffect(card);
					if(card.getColor().equals("black")) {
						String[] colors = {"blue", "green", "red", "yellow"};
						int c = (int)(Math.random() * 4);
						pile.peek().setColor(colors[c]);
						pile.peek().setImage("resources/" + colors[c] + "_" + pile.peek().getCardType() + "_card.png");
					}
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean canPlayCard(Player player) {
		UnoCard topCard = pile.peek();
		String topColor = topCard.getColor();
		String topCardType = topCard.getCardType();
		ArrayList<UnoCard> hand = player.getHand();
		for(UnoCard card: hand) {
			if(card.getColor().equals(topColor) || card.getCardType().contentEquals(topCardType)
					|| card.getColor().equals("black")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean canPlayCard(UnoCard card) {
		UnoCard topCard = pile.peek();
		String topColor = topCard.getColor();
		String topCardType = topCard.getCardType();
		if(card.getColor().equals(topColor) || card.getCardType().equals(topCardType)
				|| card.getColor().equals("black")) {
			return true;
		}
		return false;
	}
	
	public void applyEffect(UnoCard card) {
		String type = (card != null) ? card.getCardType() : "";
		int shift = 0;
		switch(type) {
		case "draw_2":
			if(backwards) {
				Player target = getPlayer((currentPosition == 0) ? 3 : (currentPosition - 1));
				if(deck.size() < 2) {
					int leftover = 2 - deck.size();
					while(deck.size() > 0) {
						target.addCard(deck.pop());
					}
					shuffleDeck();
					for(int i = 0; i < leftover; i++) {
						target.addCard(deck.pop());
					}
				}else {
					for(int i = 0; i < 2; i++) {
						target.addCard(deck.pop());
					}
				}
				if(!target.equals(getPlayer(0))) {
					organizePlayerHand(target);
				}
				shift = -2;
			}else {
				Player target = getPlayer((currentPosition + 1) % players.length);
				if(deck.size() < 2) {
					int leftover = 2 - deck.size();
					while(deck.size() > 0) {
						target.addCard(deck.pop());
					}
					shuffleDeck();
					for(int i = 0; i < leftover; i++) {
						target.addCard(deck.pop());
					}
				}else {
					for(int i = 0; i < 2; i++) {
						target.addCard(deck.pop());
					}
				}
				if(!target.equals(getPlayer(0))) {
					organizePlayerHand(target);
				}
				shift = 2;
			}
			break;
		case "skip":
			if(backwards) {
				shift = -2;
			}else {
				shift = 2;
			}
			break;
		case "reverse":
			backwards = !backwards;
			if(backwards) {
				shift = -1;
			}else {
				shift = 1;
			}
			break;
		case "wild_draw_4":
			if(backwards) {
				Player target = getPlayer((currentPosition == 0) ? 3 : (currentPosition - 1));
				if(deck.size() < 4) {
					int leftover = 4 - deck.size();
					while(deck.size() > 0) {
						target.addCard(deck.pop());
					}
					shuffleDeck();
					for(int i = 0; i < leftover; i++) {
						target.addCard(deck.pop());
					}
				}else {
					for(int i = 0; i < 4; i++) {
						target.addCard(deck.pop());
					}
				}
				if(!target.equals(getPlayer(0))) {
					organizePlayerHand(target);
				}
				shift = -2;
			}else {
				Player target = getPlayer((currentPosition + 1) % 4);
				if(deck.size() < 4) {
					int leftover = 4 - deck.size();
					while(deck.size() > 0) {
						target.addCard(deck.pop());
					}
					shuffleDeck();
					for(int i = 0; i < leftover; i++) {
						target.addCard(deck.pop());
					}
				}else {
					for(int i = 0; i < 4; i++) {
						target.addCard(deck.pop());
					}
				}
				if(!target.equals(getPlayer(0))) {
					organizePlayerHand(target);
				}
				shift = 2;
			}
			break;
		default:
			if(backwards) {
				shift = -1;
			}else {
				shift = 1;
			}
			break;
		}
		changeCurrentPosition(shift);
	}
	
	public String getWinningPlayers() {
		int win = players[0].getPointTotal();
		for(int i = 1; i < players.length; i++) {
			if(players[i].getPointTotal() < win) {
				win = players[i].getPointTotal();
			}
		}
		ArrayList<Integer> winners = new ArrayList<Integer>();
		for(int i = 0; i < players.length; i++) {
			if(players[i].getPointTotal() == win) {
				winners.add((i + 1));
			}
		}
		String s = "The winners are Player(s) ";
		for(int i: winners) {
			s += i + ", ";
		}
		return s.substring(0, s.length() - 2);
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
	
	public Player getPlayer(int i) {
		return players[i];
	}
	
	public boolean isHard() {
		return hard;
	}

	public void setHard(boolean hard) {
		this.hard = hard;
	}
	
	public Stack<UnoCard> getDeck() {
		return deck;
	}

	public Stack<UnoCard> getPile() {
		return pile;
	}

}
