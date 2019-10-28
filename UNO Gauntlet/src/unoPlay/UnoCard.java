package unoPlay;

import javafx.scene.paint.Color;

public class UnoCard {
	
	private Color color;
	private String cardType;
	private String image;
	private int pointValue;
	
	public UnoCard(Color color, String cardType, String image, int pointValue) {
		this.color = color;
		this.cardType = cardType;
		this.image = image;
		this.pointValue = pointValue;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public int getPointValue() {
		return pointValue;
	}

	public void setPointValue(int pointValue) {
		this.pointValue = pointValue;
	}
	
}
