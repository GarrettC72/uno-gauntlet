package unoPlay;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Main extends Application{

	private static UnoPlayArea table;
	private TextArea info;
	private Label play1;
	private Label play2;
	private Label play3;
	private Label play4;
	private HBox cardDisplay;
	private ImageView showPile;
	private Button blueButton;
	private Button greenButton;
	private Button redButton;
	private Button yellowButton;
	private int currentRound;
	private int totalRounds;
	private boolean stop;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		table = new UnoPlayArea(false);
		BorderPane pane = new BorderPane();

		Label title = new Label("UNO Gauntlet");
		title.setFont(new Font("Lancer",28.0));
		StackPane titlePane = new StackPane();
		titlePane.getChildren().add(title);
		pane.setTop(titlePane);

		Label rules = new Label("End the game with the least amount of points to win!\n"
				+ "Wild and Draw 4 Cards - 50 pts\n"
				+ "Draw 2, Skip and Reverse Cards - 20 pts\n"
				+ "Number Cards - (Number on the card) pts");
		rules.setTextAlignment(TextAlignment.CENTER);
		StackPane rulePane = new StackPane();
		rulePane.getChildren().add(rules);
		pane.setBottom(rulePane);

		VBox rounds = new VBox();
		rounds.setPadding(new Insets(5, 5, 5, 5));
		Label lOne = new Label("Choose the number of rounds");
		RadioButton three = new RadioButton("3");
		RadioButton four = new RadioButton("4");
		RadioButton five = new RadioButton("5");
		rounds.getChildren().addAll(lOne, three, four, five);
		rounds.setAlignment(Pos.CENTER_LEFT);
		pane.setLeft(rounds);
		ToggleGroup group1 = new ToggleGroup();
		three.setToggleGroup(group1);
		four.setToggleGroup(group1);
		five.setToggleGroup(group1);

		VBox difficulty = new VBox();
		difficulty.setPadding(new Insets(5, 5, 5, 5));
		Label lTwo = new Label("Choose the difficulty");
		RadioButton gentle = new RadioButton("Gentle");
		RadioButton mean = new RadioButton("Mean");
		difficulty.getChildren().addAll(lTwo, gentle, mean);
		difficulty.setAlignment(Pos.CENTER_LEFT);
		pane.setRight(difficulty);
		ToggleGroup group2 = new ToggleGroup();
		gentle.setToggleGroup(group2);
		mean.setToggleGroup(group2);

		StackPane startPane = new StackPane();
		Button start = new Button("Start Game");
		startPane.getChildren().add(start);
		start.setOnAction(e -> {
			if(group1.getSelectedToggle() != null && group2.getSelectedToggle() != null) {
				startGame(primaryStage,((group2.getSelectedToggle().equals(gentle)) ? false : true),((group1.getSelectedToggle().equals(three)) ? 3 : ((group1.getSelectedToggle().equals(four)) ? 4 : 5)));
			}
		});
		pane.setCenter(startPane);

		Scene scene = new Scene(pane, 600, 500);
		primaryStage.setTitle("UNO Gauntlet");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void startGame(Stage primaryStage, boolean hard, int rounds) {
		totalRounds = rounds - 1;
		currentRound = 0;
		table = new UnoPlayArea(hard);
		BorderPane pane = new BorderPane();

		Image backImage = new Image("resources/back_uno_card.png",85,130,false,false);
		Image backImage2 = new Image("resources/side_back_uno_card.png",130,85,false,false);

		BorderPane centerPane = new BorderPane();
		HBox centerPiles = new HBox();
		ImageView view4 = new ImageView(backImage);
		showPile = new ImageView(new Image(table.getPile().peek().getImage(),85,130,false,false));
		centerPiles.getChildren().addAll(view4, showPile);
		centerPiles.setAlignment(Pos.CENTER);
		info = new TextArea();
		info.setEditable(false);
		info.setText("Game " + (currentRound + 1) + " Start");
		HBox colorButtons = new HBox();
		blueButton = new Button("Blue");
		blueButton.setStyle("-fx-background-color: #0000ff; ");
		greenButton = new Button("Green");
		greenButton.setStyle("-fx-background-color: #00ff00; ");
		redButton = new Button("Red");
		redButton.setStyle("-fx-background-color: #ff0000; ");
		yellowButton = new Button("Yellow");
		yellowButton.setStyle("-fx-background-color: #ffff00; ");
		blueButton.setOnAction(e -> {
			UnoCard topCard = table.getPile().peek();
			if(topCard.getCardType().equals("wild_draw_4")) {
				table.getPile().peek().setColor("blue");
				showPile.setImage(new Image("resources/blue_wild_draw_4_card.png",85,130,false,false));
			}else if(topCard.getCardType().equals("wild")) {
				table.getPile().peek().setColor("blue");
				showPile.setImage(new Image("resources/blue_wild_card.png",85,130,false,false));
			}
			blueButton.setDisable(true);
			greenButton.setDisable(true);
			redButton.setDisable(true);
			yellowButton.setDisable(true);
			computerTurns();
		});
		greenButton.setOnAction(e -> {
			UnoCard topCard = table.getPile().peek();
			if(topCard.getCardType().equals("wild_draw_4")) {
				table.getPile().peek().setColor("green");
				showPile.setImage(new Image("resources/green_wild_draw_4_card.png",85,130,false,false));
			}else if(topCard.getCardType().equals("wild")) {
				table.getPile().peek().setColor("green");
				showPile.setImage(new Image("resources/green_wild_card.png",85,130,false,false));
			}
			blueButton.setDisable(true);
			greenButton.setDisable(true);
			redButton.setDisable(true);
			yellowButton.setDisable(true);
			computerTurns();
		});
		redButton.setOnAction(e -> {
			UnoCard topCard = table.getPile().peek();
			if(topCard.getCardType().equals("wild_draw_4")) {
				table.getPile().peek().setColor("red");
				showPile.setImage(new Image("resources/red_wild_draw_4_card.png",85,130,false,false));
			}else if(topCard.getCardType().equals("wild")) {
				table.getPile().peek().setColor("red");
				showPile.setImage(new Image("resources/red_wild_card.png",85,130,false,false));
			}
			blueButton.setDisable(true);
			greenButton.setDisable(true);
			redButton.setDisable(true);
			yellowButton.setDisable(true);
			computerTurns();
		});
		yellowButton.setOnAction(e -> {
			UnoCard topCard = table.getPile().peek();
			if(topCard.getCardType().equals("wild_draw_4")) {
				table.getPile().peek().setColor("yellow");
				showPile.setImage(new Image("resources/yellow_wild_draw_4_card.png",85,130,false,false));
			}else if(topCard.getCardType().equals("wild")) {
				table.getPile().peek().setColor("yellow");
				showPile.setImage(new Image("resources/yellow_wild_card.png",85,130,false,false));
			}
			blueButton.setDisable(true);
			greenButton.setDisable(true);
			redButton.setDisable(true);
			yellowButton.setDisable(true);
			computerTurns();
		});
		blueButton.setDisable(true);
		greenButton.setDisable(true);
		redButton.setDisable(true);
		yellowButton.setDisable(true);
		colorButtons.getChildren().addAll(blueButton, greenButton, redButton, yellowButton);
		centerPane.setCenter(centerPiles);
		centerPane.setBottom(info);
		centerPane.setTop(colorButtons);

		HBox pane1 = new HBox();
		play1 = new Label("Player 1\nCards left: " + table.getPlayer(0).getHand().size() +
				"\nPoints: " + table.getPlayer(0).getPointTotal());
		pane1.getChildren().add(play1);
		cardDisplay = new HBox();
		updatePlayerHand();
		pane1.setStyle("-fx-background-color: blue");
		pane1.setAlignment(Pos.CENTER);
		pane1.getChildren().add(cardDisplay);
		ScrollPane s1 = new ScrollPane();
		s1.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		s1.setVbarPolicy(ScrollBarPolicy.NEVER);
		s1.setContent(pane1);
		s1.setStyle("-fx-background: blue; -fx-background-color: blue");

		view4.setOnMouseClicked(e -> {
			Player player = table.getPlayer(0);
			if(table.getCurrentPosition() == 0 && !table.canPlayCard(player)) {
				UnoCard newCard = table.getDeck().pop();
				Image newImage = new Image(newCard.getImage(),85,130,false,false);
				if(table.getDeck().size() == 0) {
					table.shuffleDeck();
				}
				if(table.canPlayCard(newCard)) {
					info.appendText("\nPlayer 1 plays " + newCard.toString());
					if(table.getPlayer(0).getHand().size() == 1) {
						info.appendText(". UNO!");
					}
					table.getPile().push(newCard);
					showPile.setImage(newImage);
					table.applyEffect(newCard);
					updateText();
					if(table.checkVictorious(table.getPlayer(0))) {
						if(currentRound < totalRounds) {
							restartGame();
						}else {
							end();
						}
						updateText();
					}else {
						if(newCard.getColor().equals("black")) {
							blueButton.setDisable(false);
							greenButton.setDisable(false);
							redButton.setDisable(false);
							yellowButton.setDisable(false);
							info.appendText("\nChoose a color");
						}else {
							computerTurns();
						}
					}
				}else {
					info.appendText("\nPlayer 1 draws a card");
					player.addCard(newCard);
					ImageView newCardImage = new ImageView(newImage);
					cardDisplay.getChildren().add(newCardImage);
					newCardImage.setOnMouseClicked(f -> addEventToCard(newCard, newImage, newCardImage)
					);
					table.applyEffect(null);
					computerTurns();
				}
			}
		});

		ImageView view1 = new ImageView(backImage2);
		ImageView view2 = new ImageView(backImage);
		view2.setRotate(180.0);
		ImageView view3 = new ImageView(backImage2);
		view3.setRotate(180.0);

		VBox pane2 = new VBox();
		play2 = new Label("Player 2\nCards left: " + table.getPlayer(1).getHand().size() + 
				"\nPoints: " + table.getPlayer(1).getPointTotal());
		pane2.getChildren().addAll(play2, view1);
		pane2.setStyle("-fx-background-color: red");
		pane2.setAlignment(Pos.CENTER);

		HBox pane3 = new HBox();
		play3 = new Label("Player 3\nCards left: " + table.getPlayer(2).getHand().size() + 
				"\nPoints: " + table.getPlayer(2).getPointTotal());
		pane3.getChildren().addAll(play3, view2);
		pane3.setStyle("-fx-background-color: green");
		pane3.setAlignment(Pos.CENTER);

		VBox pane4 = new VBox();
		play4 = new Label("Player 4\nCards left: " + table.getPlayer(3).getHand().size() + 
				"\nPoints: " + table.getPlayer(3).getPointTotal());
		pane4.getChildren().addAll(play4, view3);
		pane4.setStyle("-fx-background-color: yellow");
		pane4.setAlignment(Pos.CENTER);

		play1.setFont(new Font(14.0));
		play2.setFont(new Font(14.0));
		play3.setFont(new Font(14.0));
		play4.setFont(new Font(14.0));

		pane.setTop(pane3);
		pane.setRight(pane2);
		pane.setBottom(s1);
		pane.setLeft(pane4);
		pane.setCenter(centerPane);
		Scene scene = new Scene(pane, 800, 700);
		primaryStage.setTitle("UNO Gauntlet");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/** Updates player info **/
	public synchronized void updateText() {
		play1.setText("Player 1\nCards left: " + table.getPlayer(0).getHand().size() +
				"\nPoints: " + table.getPlayer(0).getPointTotal());
		play2.setText("Player 2\nCards left: " + table.getPlayer(1).getHand().size() + 
				"\nPoints: " + table.getPlayer(1).getPointTotal());
		play3.setText("Player 3\nCards left: " + table.getPlayer(2).getHand().size() + 
				"\nPoints: " + table.getPlayer(2).getPointTotal());
		play4.setText("Player 4\nCards left: " + table.getPlayer(3).getHand().size() + 
				"\nPoints: " + table.getPlayer(3).getPointTotal());
	}

	public void computerTurns() {
		stop = false;
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					while(table.getCurrentPosition() != 0 && !stop) {
						Thread.sleep(1500);
						int i = table.getCurrentPosition();
						Player computer = table.getPlayer(table.getCurrentPosition());
						if(table.computerTurn()) {
							info.appendText("\nPlayer " + (i + 1) + " plays " + table.getPile().peek().toString());
							if(computer.getHand().size() == 1) {
								info.appendText(". UNO!");
							}
						}else {
							info.appendText("\nPlayer " + (i + 1) + " draws a card");
						}
						showPile.setImage(new Image(table.getPile().peek().getImage(),85,130,false,false));
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								updateGUI();
							}

						});
						if(table.checkVictorious(computer)) {
							stop = true;
							if(currentRound < totalRounds) {
								restartGame();
								break;
							}else {
								end();
							}
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		thread.start();
	}

	public synchronized void updatePlayerHand() {
		cardDisplay.getChildren().clear();
		ArrayList<UnoCard> hand = table.getPlayer(0).getHand();
		Iterator<UnoCard> iterator = hand.iterator();
		while(iterator.hasNext()) {
			UnoCard card = iterator.next();
			Image image = new Image(card.getImage(),85,130,false,false);
			ImageView cardImage = new ImageView(image);
			cardDisplay.getChildren().add(cardImage);
			cardImage.setOnMouseClicked(e -> addEventToCard(card, image, cardImage)
					);
		}
	}

	public void restartGame() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1500);
					currentRound++;
					table.restart();
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							info.appendText("\nGame " + (currentRound + 1) + " Start");
							updateGUI();
							blueButton.setDisable(true);
							greenButton.setDisable(true);
							redButton.setDisable(true);
							yellowButton.setDisable(true);
						}

					});
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		thread.start();
	}
	
	public void addEventToCard(UnoCard card, Image image, ImageView cardImage) {
		if(table.canPlayCard(card) && table.getCurrentPosition() == 0) {
			info.appendText("\nPlayer 1 plays " + card.toString());
			table.getPile().push(card);
			table.getPlayer(0).getHand().remove(card);
			if(table.getPlayer(0).getHand().size() == 1) {
				info.appendText(". UNO!");
			}
			showPile.setImage(image);
			table.applyEffect(card);
			updateText();
			cardDisplay.getChildren().remove(cardImage);
			if(table.checkVictorious(table.getPlayer(0))) {
				if(currentRound < totalRounds) {
					restartGame();
					return;
				}else {
					end();
				}
				updateText();
			}else {
				if(card.getColor().equals("black")) {
					blueButton.setDisable(false);
					greenButton.setDisable(false);
					redButton.setDisable(false);
					yellowButton.setDisable(false);
					info.appendText("\nChoose a color");
				}else {
					computerTurns();
				}
			}
		}
	}
	
	public void updateGUI() {
		showPile.setImage(new Image(table.getPile().peek().getImage(),85,130,false,false));
		updateText();
		updatePlayerHand();
	}

	public void end() {
		table.setCurrentPosition(0);
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(Node view: cardDisplay.getChildren()) {
					view.setOnMouseClicked(null);
				}
				info.appendText("\n" + table.getWinningPlayers());
				updateGUI();
			}
			
		});
	}

}
