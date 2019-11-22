package unoPlay;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.stage.Stage;

public class Main extends Application{
	
	private static UnoPlayArea table;
	private TextArea info;

	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage primaryStage) {
		table = new UnoPlayArea(false);
		BorderPane pane = new BorderPane();
		
		Label title = new Label("UNO Gauntlet");
		title.setFont(new Font("Lancer",20.0));
		StackPane titlePane = new StackPane();
		titlePane.getChildren().add(title);
		pane.setTop(titlePane);
		
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
			if(group1.getSelectedToggle() != null && group2.getSelectedToggle() != null)
			startGame(primaryStage,((group2.getSelectedToggle().equals(gentle)) ? false : true));
		});
		pane.setBottom(startPane);
		
		Scene scene = new Scene(pane, 400, 300);
		primaryStage.setTitle("UNO Gauntlet");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void startGame(Stage primaryStage, boolean hard) {
		table = new UnoPlayArea(hard);
		BorderPane pane = new BorderPane();
		
		Image backImage = new Image("resources/back_uno_card.png",85,130,false,false);
		Image backImage2 = new Image("resources/side_back_uno_card.png",130,85,false,false);
		
		BorderPane centerPane = new BorderPane();
		HBox centerPiles = new HBox();
		ImageView view4 = new ImageView(backImage);
		ImageView view5 = new ImageView(new Image(table.getPile().peek().getImage(),85,130,false,false));
		centerPiles.getChildren().addAll(view4, view5);
		centerPiles.setAlignment(Pos.CENTER);
		info = new TextArea();
		info.setEditable(false);
		info.setText("Game Start");
		HBox colorButtons = new HBox();
		Button blueButton = new Button("Blue");
		blueButton.setStyle("-fx-background-color: #0000ff; ");
		Button greenButton = new Button("Green");
		greenButton.setStyle("-fx-background-color: #00ff00; ");
		Button redButton = new Button("Red");
		redButton.setStyle("-fx-background-color: #ff0000; ");
		Button yellowButton = new Button("Yellow");
		yellowButton.setStyle("-fx-background-color: #ffff00; ");
		blueButton.setOnAction(e -> {
			UnoCard topCard = table.getPile().peek();
			if(topCard.getCardType().equals("wild_draw_4")) {
				table.getPile().peek().setColor("blue");
				view5.setImage(new Image("resources/blue_wild_draw_4_card.png",85,130,false,false));
			}else if(topCard.getCardType().equals("wild")) {
				table.getPile().peek().setColor("blue");
				view5.setImage(new Image("resources/blue_wild_card.png",85,130,false,false));
			}
			blueButton.setDisable(true);
			greenButton.setDisable(true);
			redButton.setDisable(true);
			yellowButton.setDisable(true);
		});
		greenButton.setOnAction(e -> {
			UnoCard topCard = table.getPile().peek();
			if(topCard.getCardType().equals("wild_draw_4")) {
				table.getPile().peek().setColor("green");
				view5.setImage(new Image("resources/green_wild_draw_4_card.png",85,130,false,false));
			}else if(topCard.getCardType().equals("wild")) {
				table.getPile().peek().setColor("green");
				view5.setImage(new Image("resources/green_wild_card.png",85,130,false,false));
			}
			blueButton.setDisable(true);
			greenButton.setDisable(true);
			redButton.setDisable(true);
			yellowButton.setDisable(true);
		});
		redButton.setOnAction(e -> {
			UnoCard topCard = table.getPile().peek();
			if(topCard.getCardType().equals("wild_draw_4")) {
				table.getPile().peek().setColor("red");
				view5.setImage(new Image("resources/red_wild_draw_4_card.png",85,130,false,false));
			}else if(topCard.getCardType().equals("wild")) {
				table.getPile().peek().setColor("red");
				view5.setImage(new Image("resources/red_wild_card.png",85,130,false,false));
			}
			blueButton.setDisable(true);
			greenButton.setDisable(true);
			redButton.setDisable(true);
			yellowButton.setDisable(true);
		});
		yellowButton.setOnAction(e -> {
			UnoCard topCard = table.getPile().peek();
			if(topCard.getCardType().equals("wild_draw_4")) {
				table.getPile().peek().setColor("yellow");
				view5.setImage(new Image("resources/yellow_wild_draw_4_card.png",85,130,false,false));
			}else if(topCard.getCardType().equals("wild")) {
				table.getPile().peek().setColor("yellow");
				view5.setImage(new Image("resources/yellow_wild_card.png",85,130,false,false));
			}
			blueButton.setDisable(true);
			greenButton.setDisable(true);
			redButton.setDisable(true);
			yellowButton.setDisable(true);
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
		pane1.getChildren().add(new Label("Player 1"));
		HBox cardDisplay = new HBox();
		ArrayList<UnoCard> hand = table.getPlayer(0).getHand();
		for(UnoCard card: hand) {
			Image image = new Image(card.getImage(),85,130,false,false);
			ImageView cardImage = new ImageView(image);
			cardDisplay.getChildren().add(cardImage);
			cardImage.setOnMouseClicked(e -> {
				if(table.canPlayCard(card)) {
					info.appendText("\nPlayer 1 plays " + card.toString());
					table.getPile().push(card);
					table.getPlayer(0).getHand().remove(card);
					view5.setImage(new Image(card.getImage(),85,130,false,false));
					cardDisplay.getChildren().remove(cardImage);
					if(card.getColor().equals("black")) {
						blueButton.setDisable(false);
						greenButton.setDisable(false);
						redButton.setDisable(false);
						yellowButton.setDisable(false);
						info.appendText("\nChoose a color");
					}
				}
			});
		}
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
				player.getHand().add(newCard);
				if(table.getDeck().size() == 0) {
					table.shuffleDeck();
				}
				Image newImage = new Image(newCard.getImage(),85,130,false,false);
				ImageView newCardImage = new ImageView(newImage);
				cardDisplay.getChildren().add(newCardImage);
				newCardImage.setOnMouseClicked(f -> {
					if(table.canPlayCard(newCard)) {
						info.appendText("\nPlayer 1 plays " + newCard.toString());
						table.getPile().push(newCard);
						player.getHand().remove(newCard);
						view5.setImage(newImage);
						cardDisplay.getChildren().remove(newCardImage);
						if(newCard.getColor().equals("black")) {
							blueButton.setDisable(false);
							greenButton.setDisable(false);
							redButton.setDisable(false);
							yellowButton.setDisable(false);
							info.appendText("\nChoose a color");
						}
					}
				});
			}
		});
		
		ImageView view1 = new ImageView(backImage2);
		ImageView view2 = new ImageView(backImage);
		view2.setRotate(180.0);
		ImageView view3 = new ImageView(backImage2);
		view3.setRotate(180.0);
		
		VBox pane2 = new VBox();
		pane2.getChildren().addAll(new Label("Player 2"), view1);
		pane2.setStyle("-fx-background-color: red");
		pane2.setAlignment(Pos.CENTER);
		
		HBox pane3 = new HBox();
		pane3.getChildren().addAll(new Label("Player 3"), view2);
		pane3.setStyle("-fx-background-color: green");
		pane3.setAlignment(Pos.CENTER);
		
		VBox pane4 = new VBox();
		pane4.getChildren().addAll(new Label("Player 4"), view3);
		pane4.setStyle("-fx-background-color: yellow");
		pane4.setAlignment(Pos.CENTER);
		
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

}
