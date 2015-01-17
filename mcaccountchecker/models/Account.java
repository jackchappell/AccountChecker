package mcaccountchecker.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Account
{
	
	private final StringProperty username;
	private final StringProperty password;
	
	public Account(String username, String password)
	{
		this.password = new SimpleStringProperty(password);
		this.username = new SimpleStringProperty(username);
	}
	
	public StringProperty getUsername()
	{
		return username;
	}
	
	public StringProperty getPassword()
	{
		return password;
	}
	
}
