package unoPlay;

import java.util.ArrayList;
import java.util.Stack;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{
	
	private static UnoPlayArea table;

	public static void main(String[] args) {
		//table = new UnoPlayArea();
		//Stack<UnoCard> deck = table.getDeck();
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
		HBox pane1 = new HBox();
		pane1.getChildren().add(new Label("Player1"));
		ArrayList<UnoCard> hand = table.getPlayer(0).getHand();
		for(UnoCard card: hand) {
			Image image = new Image(card.getImage(),85,130,false,false);
			pane1.getChildren().add(new ImageView(image));
		}
		pane1.setStyle("-fx-background-color: blue");
		pane1.setAlignment(Pos.CENTER);
		Image backImage = new Image("resources/back_uno_card.png",85,130,false,false);
		Image backImage2 = new Image("resources/side_back_uno_card.png",130,85,false,false);
		ImageView view1 = new ImageView(backImage2);
		ImageView view2 = new ImageView(backImage);
		view2.setRotate(180.0);
		ImageView view3 = new ImageView(backImage2);
		view3.setRotate(180.0);
		VBox pane2 = new VBox();
		pane2.getChildren().addAll(new Label("Player2"), view1);
		pane2.setStyle("-fx-background-color: red");
		HBox pane3 = new HBox();
		pane3.getChildren().addAll(new Label("Player3"), view2);
		pane3.setStyle("-fx-background-color: green");
		VBox pane4 = new VBox();
		pane4.getChildren().addAll(new Label("Player4"), view3);
		pane4.setStyle("-fx-background-color: yellow");
		Pane centerPane = new Pane();
		pane.setTop(pane3);
		pane.setRight(pane2);
		pane.setBottom(pane1);
		pane.setLeft(pane4);
		pane.setCenter(centerPane);
		Scene scene = new Scene(pane, 800, 700);
		primaryStage.setTitle("UNO Gauntlet");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
