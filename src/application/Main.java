package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			javafx.scene.image.Image image1 = new javafx.scene.image.Image(
					getClass().getResource("image6.jpg").toExternalForm());
			ImageView iv = new ImageView(image1);
			javafx.scene.image.Image image2 = new javafx.scene.image.Image(
					getClass().getResource("image5.jpg").toExternalForm());
			ImageView iv1 = new ImageView(image2);

			Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
			// Scene scene =new Scene(root);
			primaryStage.setScene(new Scene(root, 1800, 850));
			primaryStage.setMaximized(true);
			// primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static class ConnectionBuilderMysql {
	    static String user_name="root";
	    static String Password="aq4427";

	    public static  Connection buildConnection () {
	        try{
	           Class.forName("com.mysql.jdbc.Driver");
	           try {
	               return DriverManager.getConnection("jdbc:mysql://localhost:3306/Electronic_Votting_System",user_name,Password);
	           } catch (SQLException e) {
	               System.err.println("connection not established");
	               e.printStackTrace();
	                return null;
	           }
	        } catch(ClassNotFoundException ex) {
	            System.err.println("Driver is not loaded");
	            return null;
	        }
	    }
	}
	public static void main(String[] args) {
		//Mysql connection
		try {
			 //DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("MySQL Driver successfully loaded");

			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","aq4427");
			System.out.println("MySQL Connection Established");
		}
		catch(ClassNotFoundException e) {
			//e.printStackTrace();
			System.out.println("MySQL Driver not loaded");
		}

		catch(SQLException e) {
			System.out.println("MySQL Connection Failed");
		}

		launch(args);
	}
}