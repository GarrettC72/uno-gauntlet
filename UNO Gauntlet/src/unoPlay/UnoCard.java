package unoPlay;

public class UnoCard implements Comparable<UnoCard>{
	
	private String color;
	private String cardType;
	private String image;
	private int pointValue;
	
	public UnoCard(String color, String cardType, String image, int pointValue) {
		this.color = color;
		this.cardType = cardType;
		this.image = image;
		this.pointValue = pointValue;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
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
	
	public String toString() {
		return color + " " + cardType;
	}

	@Override
	public int compareTo(UnoCard o) {
		if(this.pointValue < o.getPointValue()) {
			return -1;
		}else if(this.pointValue == o.getPointValue()) {
			return 0;
		}else {
			return 0;
		}
	}
	
}
