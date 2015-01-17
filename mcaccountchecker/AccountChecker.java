package mcaccountchecker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AccountChecker extends Application
{
	
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		Parent parent = FXMLLoader.load(getClass().getResource("/res/fxml/main.fxml"));
		Scene scene = new Scene(parent);
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Account Checker");
		
		stage.show();
	}

}
