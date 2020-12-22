import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;

public class WordGuessServer extends Application {

	HashMap<String, Scene> sceneMap;
	BorderPane opening,serverPane;
	Scene introScene,serverScene;
	VBox port;
	Text portInfo,hangManTitle;
	TextField portText;
	Image powerButton;
	ImageView pB;
	ListView<String> hangManList;
	Server serverConnection;
	

	public static void main(String[] args) {
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("(Server) Let's Play Hangman");
		sceneMap = new HashMap<>();
		sceneMap.put("intro",createIntroGui());
		sceneMap.put("server",createServerGui());
		pB.setOnMouseClicked(e->{
			if(!portText.getText().isEmpty()) {
				int portNum = Integer.parseInt(portText.getText());
				if (portNum >= 1024 && portNum <= 65535)
					primaryStage.setScene(sceneMap.get("server"));
				serverConnection = new Server(data -> {
					Platform.runLater(()->{
						hangManList.getItems().add(data.toString());
					});
				},portNum);

			}
		});
		



		primaryStage.setScene(sceneMap.get("intro"));
		primaryStage.show();


	}

	public Scene createIntroGui(){
		Insets textSpacing = new Insets(20,20,20,20);
		//Text to inform user
		portInfo = new Text("Enter Port Number (1024 to 65535) then press power button :");
		portInfo.setStyle("-fx-font: 20 arial;");
		//Text field to enter port
		portText = new TextField();
		portText.setStyle("-fx-font: 24 arial;");
		portText.setText("5555");
		//Power Button to turn on the server
		powerButton = new Image("powerButton.png");
		pB = new ImageView(powerButton);
		//setting button size
		pB.setPreserveRatio(true);
		pB.setFitHeight(100);
		pB.setBlendMode(BlendMode.DARKEN);
		//Vbox to organize port texts
		port = new VBox(5,portInfo,portText);

		//Pane that will be displayed to scene
		opening = new BorderPane();
		opening.setTop(port);
		opening.setCenter(pB);
		BorderPane.setMargin(port, textSpacing);
		//Creating the intro scene map
		introScene = new Scene(opening,600,600);

		return introScene;
	}

	public Scene createServerGui(){
		
		//Shows game info
		hangManList = new ListView();
		//The title
		hangManTitle = new Text("THE HANGMAN SERVER");
		hangManTitle.setStyle("-fx-font: 42 arial;");
		//The pane to be displayed
		serverPane = new BorderPane();
		serverPane.setCenter(hangManList);
		serverPane.setTop(hangManTitle);
		serverPane.setAlignment(hangManTitle, Pos.CENTER);
		//Creating the scene
		serverScene = new Scene(serverPane,600,600);
		
		return serverScene;
	}

}
