import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.System.exit;

public class WordGuessClient extends Application {
	String ip = "";
	String wordSoFar;
	int port = 5555;
	Client clientConnection;

	int categoryIndex = 0;
	int guessLeft = 6;
	int c1WordsLeft = 3;
	int c2WordsLeft = 3;
	int c3WordsLeft = 3;

	boolean currentlyC1 = false;
	boolean currentlyC2 = false;
	boolean currentlyC3 = false;

	boolean c1done = false;
	boolean c2done = false;
	boolean c3done = false;


	ListView<String> hangmanlist = new ListView<String>();
	HashMap<String,Scene> sceneMap;

	Button get_port = new Button("Submit!");

	Button get_ip = new Button("Submit!");

	Button begin_game = new Button("Begin!");
	Label lbl_port,lbl_ip;
	TextField tf_port, tf_ip;


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("(Client) Word Guess!!!");


		sceneMap = new HashMap<String,Scene>();

		sceneMap.put("client",  createClientGui());
		sceneMap.put("intro", createIntroGui());



		



		get_port.setOnAction(event -> {
			String portstr = tf_port.getText();
			port = Integer.parseInt(portstr);

			lbl_port.setDisable(true);
			tf_port.setDisable(true);
			get_port.setDisable(true);

			lbl_ip.setDisable(false);
			tf_ip.setDisable(false);
			get_ip.setDisable(false);
			System.out.println(port);
		});


		get_ip.setOnAction(event -> {
			ip = tf_ip.getText();
			System.out.println(ip);

			lbl_ip.setDisable(true);
			tf_ip.setDisable(true);
			get_ip.setDisable(true);

			begin_game.setDisable(false);
			
		});

		this.begin_game.setOnAction(e-> {primaryStage.setScene(sceneMap.get("client"));
			clientConnection = new Client(
					data -> {Platform.runLater(()->{hangmanlist.getItems().add(data.toString());});

					}, port, ip);
			clientConnection.start();
		});

		primaryStage.setScene(sceneMap.get("intro"));
		primaryStage.show();





	}

	private Scene createIntroGui() {
		lbl_port = new Label("Enter a port number: ");
		lbl_port.setTextFill(Color.WHITE);
		tf_port = new TextField();
		
		lbl_ip = new Label("Enter a IP address: ");
		lbl_ip.setTextFill(Color.WHITE);
		tf_ip = new TextField();



		Image image1 = new Image("wordguess3.png");
		ImageView imageview1 = new ImageView(image1);
		
		//Image image2 = new Image("begin.png");
		//ImageView imageview2 = new ImageView(image2);

		Pane startStage = new Pane();

		startStage.getChildren().addAll(imageview1);
		startStage.getChildren().addAll(lbl_port);
		startStage.getChildren().addAll(tf_port);
		startStage.getChildren().addAll(get_port);
		startStage.getChildren().addAll(lbl_ip);
		startStage.getChildren().addAll(tf_ip);
		startStage.getChildren().addAll(get_ip);
		startStage.getChildren().addAll(begin_game);
		


		lbl_port.relocate(200,250);
		tf_port.relocate(325,250);
		get_port.relocate(200,280);
		
		imageview1.relocate(200, 130);
		

		lbl_ip.relocate(200,330);
		tf_ip.relocate(325,330);
		get_ip.relocate(200,360);

		begin_game.relocate(360,430);

		lbl_ip.setDisable(true);
		tf_ip.setDisable(true);
		get_ip.setDisable(true);
		begin_game.setDisable(true);

		startStage.setStyle("-fx-background-repeat: no-repeat;-fx-background-image: url('background.png');-fx-background-size: cover, auto;");
		return new Scene(startStage,800,600);
	}

	public String generateString(int len)
	{
		String rs = "";
		for(int i = 0;i < len;i++) {
			rs = rs + '_';
		}

		return rs;

	}

	public String updateString(ArrayList<Integer> arr,String str)
	{
		//int arrIndex = arr.get(0);
		for (Integer integer : arr) {
			str = str.substring(0, integer)
					+ clientConnection.hm.getLetterPick()
					+ str.substring(integer + 1);
		}
		return str;
	}

	public boolean checkDone(String str)
	{

		return !str.contains("_");
	}

	public Scene createClientGui() {
		Pane selectCategory = new Pane();


		Image image4 = new Image("category.png");
		ImageView categoryIMG = new ImageView(image4);
		categoryIMG.relocate(150, 0);

		Label pick_category = new Label("");
		pick_category.setTextFill(Color.WHITE);
		Label lbl_pokemon = new Label("Pokemon");
		lbl_pokemon.setTextFill(Color.WHITE);
		Label lbl_movie = new Label("Movies");
		lbl_movie.setTextFill(Color.WHITE);
		Label lbl_food = new Label("Food");
		lbl_food.setTextFill(Color.WHITE);
		Label lbl_category = new Label("*Category Selected*");
		lbl_category.setTextFill(Color.WHITE);
		Label lbl_selection = new Label("Enter a letter to guess: ");
		lbl_selection.setTextFill(Color.WHITE);

		TextArea output = new TextArea("Word received from server.");
		output.setPrefWidth(200);
		output.setPrefHeight(400);

		lbl_category.setOpacity(0);
		lbl_selection.setOpacity(0);

		output.setOpacity(0);


		TextField tf_selection = new TextField();

		tf_selection.setPrefWidth(50);

		tf_selection.setOpacity(0);


		Button btn_guess = new Button ("Submit!");

		Button btn_playAgain = new Button("Play again?");
		Button btn_quit = new Button("Quit?");
		btn_playAgain.setVisible(false);

		btn_guess.setOpacity(0);

		selectCategory.setStyle("-fx-background-repeat: no-repeat;-fx-background-image: url('background.png');-fx-background-size: cover, auto;");

		Image img_food = new Image("food.png");
		Image img_movie = new Image("movie.jpg");
		Image img_pokemon = new Image("pokemon.jpg");
		ImageView iv_food = new ImageView(img_food);
		ImageView iv_movie = new ImageView(img_movie);
		ImageView iv_pokemon = new ImageView(img_pokemon);

		iv_pokemon.setFitWidth(150);
		iv_pokemon.setFitHeight(150);
		iv_movie.setFitWidth(150);
		iv_movie.setFitHeight(150);
		iv_food.setFitWidth(150);
		iv_food.setFitHeight(150);

		iv_movie.setOnMouseClicked(e->{
			System.out.println("Movie category selected");
			output.clear();
			lbl_category.setText("Movie Selected");
			lbl_category.setOpacity(1);
			lbl_selection.setOpacity(1);
			tf_selection.setOpacity(1);
			btn_guess.setOpacity(1);



			output.setOpacity(1);





			categoryIndex = 2;
			clientConnection.hm.setCategory(2);
			clientConnection.hm.setPickedCategory(true);
			clientConnection.hm.setPickingLetter(false);
			clientConnection.send(clientConnection.hm);
			try {
				clientConnection.updateInfo();
			} catch (IOException | ClassNotFoundException ex) {
				ex.printStackTrace();
			}

			output.setText(output.getText() + "\nLength of String: " + clientConnection.hm.getLetterCount());
			wordSoFar = generateString(clientConnection.hm.getLetterCount());
			output.setText(output.getText() + "\nWord: " + wordSoFar);




			iv_movie.setVisible(true);
			iv_food.setVisible(false);
			iv_pokemon.setVisible(false);

			currentlyC1 = true;
			currentlyC2 = false;
			currentlyC3 = false;
		});

		iv_food.setOnMouseClicked(e->{
			System.out.println("Food category selected");
			output.clear();
			lbl_category.setText("Food Selected");
			lbl_category.setOpacity(1);
			lbl_selection.setOpacity(1);
			tf_selection.setOpacity(1);
			btn_guess.setOpacity(1);



			output.setOpacity(1);



			categoryIndex = 1;
			clientConnection.hm.setCategory(1);
			clientConnection.hm.setPickedCategory(true);
			clientConnection.hm.setPickingLetter(false);
			clientConnection.send(clientConnection.hm);


			try {
				clientConnection.updateInfo();
			} catch (IOException | ClassNotFoundException ex) {
				ex.printStackTrace();
			}
			output.setText(output.getText() + "\nLength of String: " + clientConnection.hm.getLetterCount());
			wordSoFar = generateString(clientConnection.hm.getLetterCount());
			output.setText(output.getText() + "\nWord: " + wordSoFar);

			iv_movie.setVisible(false);
			iv_food.setVisible(true);
			iv_pokemon.setVisible(false);
			currentlyC1 = false;
			currentlyC2 = true;
			currentlyC3 = false;
		});

		iv_pokemon.setOnMouseClicked(e->{
			System.out.println("Pokemon category selected");
			output.clear();
			lbl_category.setText("Pokemon Selected");
			lbl_category.setOpacity(1);
			lbl_selection.setOpacity(1);
			tf_selection.setOpacity(1);
			btn_guess.setOpacity(1);



			output.setOpacity(1);



			categoryIndex = 0;
			clientConnection.hm.setCategory(0);
			clientConnection.hm.setPickedCategory(true);
			clientConnection.hm.setPickingLetter(false);
			clientConnection.send(clientConnection.hm);




			try {
				clientConnection.updateInfo();
			} catch (IOException | ClassNotFoundException ex) {
				ex.printStackTrace();
			}
			output.setText(output.getText() + "\nLength of String: " + clientConnection.hm.getLetterCount());
			wordSoFar = generateString(clientConnection.hm.getLetterCount());
			output.setText(output.getText() + "\nWord: " + wordSoFar);


			iv_movie.setVisible(false);
			iv_food.setVisible(false);
			iv_pokemon.setVisible(true);
			currentlyC1 = false;
			currentlyC2 = false;
			currentlyC3 = true;

		});




		btn_guess.setOnAction(event -> {
			String input = tf_selection.getText();
			input.toLowerCase();
			char guessChar = input.charAt(0);
			clientConnection.hm.setPickedCategory(false);
			clientConnection.hm.setPickingLetter(true);
			clientConnection.hm.setLetterPick(guessChar);
			clientConnection.send(clientConnection.hm);
			//update the client with the new packet.
			try {
				clientConnection.updateInfo();
			} catch (IOException | ClassNotFoundException ex) {
				ex.printStackTrace();
			}
			//update the string to get the information
			ArrayList<Integer> positions = clientConnection.hm.getLetterPositions();
			wordSoFar = updateString(positions,wordSoFar);

			//update the output field
			output.setText(output.getText() + "\nWord: " + wordSoFar);
			tf_selection.clear();

			//checking to see if we actually updated anything
			if(clientConnection.hm.getLetterPositions().isEmpty())
			{
				guessLeft--;
				output.setText(output.getText() + "\nGuesses left: " + guessLeft);
				if(guessLeft == 0)
				{
					if(currentlyC1)
					{

						c1WordsLeft--;
						if(c1WordsLeft != 0)
						{
							output.setText(output.getText() + "\nNo more guesses. Pick a different category.\nWord left in this category: " + c1WordsLeft);
							if(c1done && c2done)
							{
								iv_movie.setVisible(false);
								iv_pokemon.setVisible(true);
								iv_food.setVisible(false);
							}
							else if (c1done && c3done)
							{
								iv_movie.setVisible(false);
								iv_pokemon.setVisible(false);
								iv_food.setVisible(true);
							}
							else if(c2done && c3done)
							{
								iv_movie.setVisible(true);
								iv_pokemon.setVisible(false);
								iv_food.setVisible(false);
							}
							else if(c1done)
							{
								iv_movie.setVisible(false);
								iv_pokemon.setVisible(true);
								iv_food.setVisible(true);
							}
							else if(c2done)
							{
								iv_movie.setVisible(false);
								iv_pokemon.setVisible(true);
								iv_food.setVisible(false);
							}
							else if(c3done)
							{
								iv_movie.setVisible(false);
								iv_pokemon.setVisible(false);
								iv_food.setVisible(true);
							}
							else
							{
								iv_movie.setVisible(false);
								iv_pokemon.setVisible(true);
								iv_food.setVisible(true);
							}
							guessLeft = 6;
							lbl_selection.setOpacity(0);
							tf_selection.setOpacity(0);
							btn_guess.setOpacity(0);
						}
						else
						{
							output.setText(output.getText() + "\nNo more words in this category. \nGame over!");
							iv_movie.setVisible(false);
							iv_food.setVisible(false);
							iv_pokemon.setVisible(false);

							lbl_selection.setOpacity(0);
							tf_selection.setOpacity(0);
							btn_guess.setOpacity(0);

							btn_playAgain.setVisible(true);
							btn_quit.setVisible(true);
						}
					}
					else if (currentlyC2)
					{

						c2WordsLeft--;
						if(c2WordsLeft != 0)
						{
							output.setText(output.getText() + "\nNo more guesses. Pick a different category.\nWord left in this category: " + c2WordsLeft);
							if(c1done && c2done)
							{
								iv_movie.setVisible(false);
								iv_pokemon.setVisible(true);
								iv_food.setVisible(false);
							}
							else if (c1done && c3done)
							{
								iv_movie.setVisible(false);
								iv_pokemon.setVisible(false);
								iv_food.setVisible(true);
							}
							else if(c2done && c3done)
							{
								iv_movie.setVisible(true);
								iv_pokemon.setVisible(false);
								iv_food.setVisible(false);
							}
							else if(c1done)
							{
								iv_movie.setVisible(false);
								iv_pokemon.setVisible(true);
								iv_food.setVisible(false);
							}
							else if(c2done)
							{
								iv_movie.setVisible(true);
								iv_pokemon.setVisible(true);
								iv_food.setVisible(false);
							}
							else if(c3done)
							{
								iv_movie.setVisible(true);
								iv_pokemon.setVisible(false);
								iv_food.setVisible(false);
							}
							else
							{
								iv_movie.setVisible(true);
								iv_pokemon.setVisible(true);
								iv_food.setVisible(false);
							}
							guessLeft = 6;
							lbl_selection.setOpacity(0);
							tf_selection.setOpacity(0);
							btn_guess.setOpacity(0);
						}
						else
						{
							output.setText(output.getText() + "\nNo more words in this category. \nGame over!");
							iv_movie.setVisible(false);
							iv_food.setVisible(false);
							iv_pokemon.setVisible(false);

							lbl_selection.setOpacity(0);
							tf_selection.setOpacity(0);
							btn_guess.setOpacity(0);

							btn_playAgain.setVisible(true);
							btn_quit.setVisible(true);
						}
					}
					else if(currentlyC3)
					{

						c3WordsLeft--;
						if(c3WordsLeft != 0)
						{
							output.setText(output.getText() + "\nNo more guesses. Pick a different category.\nWord left in this category: " + c3WordsLeft);
							if(c1done && c2done)
							{
								iv_movie.setVisible(false);
								iv_pokemon.setVisible(true);
								iv_food.setVisible(false);
							}
							else if (c1done && c3done)
							{
								iv_movie.setVisible(false);
								iv_pokemon.setVisible(false);
								iv_food.setVisible(true);
							}
							else if(c2done && c3done)
							{
								iv_movie.setVisible(true);
								iv_pokemon.setVisible(false);
								iv_food.setVisible(false);
							}
							else if(c1done)
							{
								iv_movie.setVisible(false);
								iv_pokemon.setVisible(false);
								iv_food.setVisible(true);
							}
							else if(c2done)
							{
								iv_movie.setVisible(true);
								iv_pokemon.setVisible(false);
								iv_food.setVisible(false);
							}
							else if(c3done)
							{
								iv_movie.setVisible(true);
								iv_pokemon.setVisible(false);
								iv_food.setVisible(true);
							}
							else
							{
								iv_movie.setVisible(true);
								iv_pokemon.setVisible(false);
								iv_food.setVisible(true);
							}
							guessLeft = 6;
							lbl_selection.setOpacity(0);
							tf_selection.setOpacity(0);
							btn_guess.setOpacity(0);
						}
						else
						{
							output.setText(output.getText() + "\nNo more words in this category. \nGame over!");
							iv_movie.setVisible(false);
							iv_food.setVisible(false);
							iv_pokemon.setVisible(false);

							lbl_selection.setOpacity(0);
							tf_selection.setOpacity(0);
							btn_guess.setOpacity(0);

							btn_playAgain.setVisible(true);
							btn_quit.setVisible(true);



						}
					}




				}

			}

			//check if we have completed the word
			boolean wordDone = checkDone(wordSoFar);

			if(wordDone)
			{
				output.setText(output.getText() + "\nWord guessed correctly!");
				//re-enable everything but the category that was completed.
				if(currentlyC1)
				{
					c1done = true;
				}
				else if(currentlyC2)
				{
					c2done = true;
				}
				else if(currentlyC3)
				{
					c3done = true;
				}
				iv_movie.setVisible(true);
				iv_pokemon.setVisible(true);
				iv_food.setVisible(true);
				guessLeft = 6;
				lbl_selection.setOpacity(0);
				tf_selection.setOpacity(0);
				btn_guess.setOpacity(0);

				//if the category is done hide them.

				if(c1done && c2done)
				{
					iv_movie.setVisible(false);
					iv_pokemon.setVisible(true);
					iv_food.setVisible(false);
				}
				else if (c1done && c3done)
				{
					iv_movie.setVisible(false);
					iv_pokemon.setVisible(false);
					iv_food.setVisible(true);
				}
				else if(c2done && c3done)
				{
					iv_movie.setVisible(true);
					iv_pokemon.setVisible(false);
					iv_food.setVisible(false);
				}
				else if(c1done)
				{
					iv_movie.setVisible(false);
					iv_pokemon.setVisible(false);
					iv_food.setVisible(true);
				}
				else if(c2done)
				{
					iv_movie.setVisible(true);
					iv_pokemon.setVisible(false);
					iv_food.setVisible(false);
				}
				else if(c3done)
				{
					iv_movie.setVisible(true);
					iv_pokemon.setVisible(false);
					iv_food.setVisible(true);
				}
				else
				{
					iv_movie.setVisible(true);
					iv_pokemon.setVisible(true);
					iv_food.setVisible(true);
				}

				//now check if we reached an endgame
				if(c1done && c2done && c3done)
				{
					output.setText(output.getText() + "\nCongratulations! You win! \nPlay again?");
					lbl_selection.setOpacity(0);
					tf_selection.setOpacity(0);
					btn_guess.setOpacity(0);

					iv_movie.setVisible(false);
					iv_pokemon.setVisible(false);
					iv_food.setVisible(false);

					btn_playAgain.setVisible(true);
					btn_quit.setVisible(true);
				}

			}

		});


		btn_playAgain.setOnAction(event -> {
			iv_food.setVisible(true);
			iv_movie.setVisible(true);
			iv_pokemon.setVisible(true);

			guessLeft = 6;
			c1WordsLeft = 3;
			c2WordsLeft = 3;
			c3WordsLeft = 3;
			currentlyC2 = false;
			currentlyC3 = false;
			currentlyC1 = false;
			c1done = false;
			c2done = false;
			c3done = false;

			btn_playAgain.setVisible(false);


			clientConnection.hm.setPlayAgain(true);
			clientConnection.hm.setPickingLetter(false);
			clientConnection.send(clientConnection.hm);

			try {
				clientConnection.updateInfo();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			output.clear();

		});
        //if the player quit, notify the server. then exit.
        btn_quit.setOnAction(event -> {
			clientConnection.hm.setPickingLetter(false);
			clientConnection.hm.setPlayerQuit(true);
			clientConnection.send(clientConnection.hm);

			//now that we informed the server, we can leave.
			exit(1);
		});









		selectCategory.getChildren().addAll(pick_category);
		selectCategory.getChildren().addAll(lbl_food);
		selectCategory.getChildren().addAll(lbl_movie);
		selectCategory.getChildren().addAll(lbl_pokemon);
		selectCategory.getChildren().addAll(iv_food);
		selectCategory.getChildren().addAll(iv_movie);
		selectCategory.getChildren().addAll(iv_pokemon);
		selectCategory.getChildren().addAll(lbl_category);
		selectCategory.getChildren().addAll(lbl_selection);
		selectCategory.getChildren().addAll(tf_selection);
		selectCategory.getChildren().addAll(btn_guess);
		selectCategory.getChildren().addAll(btn_playAgain);
        selectCategory.getChildren().addAll(btn_quit);
        selectCategory.getChildren().addAll(categoryIMG);



		selectCategory.getChildren().addAll(output);

		pick_category.relocate(330,25);

		lbl_food.relocate(100,100);
		lbl_movie.relocate(350,100);
		lbl_pokemon.relocate(600,100);

		iv_food.relocate(50,133);
		iv_movie.relocate(300,133);
		iv_pokemon.relocate(550,133);

		lbl_category.relocate(300,300);
		lbl_category.setStyle("-fx-font-size: 20px; -fx-font-color: black;-fx-font-weight: bold");

		lbl_selection.relocate(300,350);
		tf_selection.relocate(425,350);
		btn_guess.relocate(300,400);
		btn_playAgain.relocate(300,450);
		btn_quit.relocate(300,500);




		output.relocate(50,350);


		//selectCategory.setStyle("-fx-background-repeat: no-repeat;-fx-background-image: url('bg.jpg');-fx-background-size: cover, auto;");
		return new Scene(selectCategory,800,800);
	}

}
