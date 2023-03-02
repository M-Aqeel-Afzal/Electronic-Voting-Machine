package application;


import java.io.File;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main.ConnectionBuilderMysql;
//import Accounts.ConnectionBuilderMysql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Controllar implements Initializable            //main controllar class
{
	// Adding new party and candidate
	@FXML
	private TextField cnic=new TextField();             //to get cnic from text field
	@FXML
	private TextField chairman=new TextField();             //to get chairman from text field
	@FXML
	private TextField name=new TextField();             //to get name from text field
	@FXML
	private TextField count=new TextField();             //to get count from text field
	@FXML
	private ComboBox<String> combobox1=new ComboBox<>();    //to pass values to combobox
String pr1="";
	// Adding new admin and signin
		@FXML
		private TextField passward=new TextField();             //to get passward from text field
		@FXML
		private TextField confirm_passward=new TextField();             //to get confirmed pasward from text field
        boolean flag=false,flag2=false;
        
     // vote casting
     		@FXML
     		private TextField party=new TextField();         //to add new party and candidate
     		@FXML
     		private TextField candidate1=new TextField();
     		
     		 // adding new candidate  
     		@FXML
     		private TextField city=new TextField();          //to add new candidate info
     		@FXML
     		private TextField area=new TextField();

	@FXML
	private Button closeButton;
	 @FXML
	    private Label winner=new Label();       //for winner
	

	@FXML
	private PieChart chart=new PieChart();              //to show presentage
	
	
	@FXML
	private ComboBox<String> combobox=new ComboBox<>();        //to show values in form of combobox
	ArrayList<String> list1=new ArrayList<>();
	ArrayList<String> list2=new ArrayList<>();
	{
		                      
			java.sql.Statement s2 = null;
			try {
				s2 = ConnectionBuilderMysql.buildConnection().createStatement();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	try {
		ResultSet str2 =s2.executeQuery("Select name_and_party from candidate");
		
		while(str2.next()) {
			list2.add(str2.getString("name_and_party"));
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	ObservableList<PieChart.Data> list3 = FXCollections.observableList(new ArrayList<PieChart.Data>());
	ObservableList<String> parties= FXCollections.observableArrayList(list1);
	ObservableList<String> candidate= FXCollections.observableArrayList(list2);
	

	public void SaveButtonCliked(ActionEvent event) {      //saving new party information 
		try {
			Media letterSound = new Media(new File("F:\\ecloipesWorkspace\\task4\\src\\application\\beep1.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
			mediaPlayer.play();
			Parent root;
			Stage primaryStage=new Stage();
			javafx.scene.image.Image image2 = new javafx.scene.image.Image(
					getClass().getResource("image5.jpg").toExternalForm());
			ImageView iv1 = new ImageView(image2);
			 java.sql.Statement s1 = null;
				try {
					s1 = ConnectionBuilderMysql.buildConnection().createStatement();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
Party party=new Party();
			party.setCnic(cnic.getText());
			party.setChairman(chairman.getText());
			party.setName(name.getText());
			String query="insert into party (name,chairman,cnic,t_count) values (?,?,?,?)";
			 try {
					PreparedStatement state=ConnectionBuilderMysql.buildConnection().prepareStatement(query);
					state.setString(1, party.getChairman());
					state.setString(2, party.getName());
					state.setString(3, party.getCnic());
					state.setString(4, "0");
					int inserted=state.executeUpdate();
					if(inserted>0)
					{
						System.out.println("added successfuliy");
						flag2=true;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 java.sql.Statement s2 = null;
				try {
					s2 = ConnectionBuilderMysql.buildConnection().createStatement();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		try {
			
			ResultSet str2 =s2.executeQuery("Select cnic from party");
			while(str2.next()) {
				System.out.println(str2.getString("cnic")+ "  "+cnic.getText());
				if((str2.getString("cnic").equals(cnic.getText())))
						{
					flag=true;
					if(flag2==true)
						flag=false;
					break;
						}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
				 primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
				 if(flag==true)
				 {
					 Alert mess = new Alert(AlertType.INFORMATION);
					 mess.setTitle("Error");
					 mess.setContentText("CNIC allready exist!");
					
					 mess.setHeaderText(null);
					
					 mess.showAndWait();
			           flag=false;
			           flag2=false;
				root = FXMLLoader.load(getClass().getResource("party.fxml"));
				 }
				 else
				 {
					 root = FXMLLoader.load(getClass().getResource("Admintasks.fxml"));
					 flag=false;
					 flag2=false;
				 }
			
		
			// Scene scene =new Scene(root);
			primaryStage.setScene(new Scene(root, 1800, 850));
			primaryStage.setMaximized(true);
			// primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Save1ButtonCliked(ActionEvent event) {   //saving new candidate info
		try {
			Media letterSound = new Media(new File("F:\\ecloipesWorkspace\\task4\\src\\application\\beep1.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
			mediaPlayer.play();
			Parent root;
			Stage primaryStage=new Stage();
			javafx.scene.image.Image image2 = new javafx.scene.image.Image(
					getClass().getResource("image5.jpg").toExternalForm());
			ImageView iv1 = new ImageView(image2);
			 java.sql.Statement s1 = null;
				try {
					s1 = ConnectionBuilderMysql.buildConnection().createStatement();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Candidate can=new Candidate();
			
			can.setName(name.getText());
		
			can.setCnic(cnic.getText());
		
			can.setCity(city.getText());
			
			can.setArea(area.getText());
			String query="insert into candidate (name_and_party,cnic,city,area,count) values (?,?,?,?,?)";
			 try {
					PreparedStatement state=ConnectionBuilderMysql.buildConnection().prepareStatement(query);
					state.setString(1, can.getName());
					state.setString(2, can.getCnic());
					state.setString(3, can.getCity());
					state.setString(4, can.getArea());
					state.setString(5, "0");
					int inserted=state.executeUpdate();
					if(inserted>0)
					{
						System.out.println("added successfuliy");
						flag2=true;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 java.sql.Statement s2 = null;
				try {
					s2 = ConnectionBuilderMysql.buildConnection().createStatement();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		try {
			
			ResultSet str2 =s2.executeQuery("Select cnic from candidate");
			while(str2.next()) {
				System.out.println(str2.getString("cnic")+ "  "+cnic.getText());
				if((str2.getString("cnic").equals(cnic.getText())))
						{
					flag=true;
					if(flag2==true)
						flag=false;
					break;
						}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
				 primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
				 if(flag==true)
				 {
					 Alert mess = new Alert(AlertType.INFORMATION);
					 mess.setTitle("Error");
					 mess.setContentText("CNIC allready exist!");
				
					 mess.setHeaderText(null);
					
					 mess.showAndWait();
			           flag=false;
			           flag2=false;
				root = FXMLLoader.load(getClass().getResource("candidate.fxml"));
				 }
				 else
				 {
					 root = FXMLLoader.load(getClass().getResource("Admintasks.fxml"));
					 flag=false;
					 flag2=false;
				 }
			
		
			// Scene scene =new Scene(root);
			primaryStage.setScene(new Scene(root, 1800, 850));
			primaryStage.setMaximized(true);
			// primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void SignupButtonCliked(ActionEvent event) {           //to show signup page
		try {
			Media letterSound = new Media(new File("F:\\ecloipesWorkspace\\task4\\src\\application\\beep1.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
			mediaPlayer.play();
			Stage primaryStage=new Stage();
			javafx.scene.image.Image image2 = new javafx.scene.image.Image(
					getClass().getResource("image5.jpg").toExternalForm());
			ImageView iv1 = new ImageView(image2);
			 primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("signup.fxml"));
			// Scene scene =new Scene(root);
			primaryStage.setScene(new Scene(root, 1800, 850));
			primaryStage.setMaximized(true);
			// primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void remove1ButtonCliked(ActionEvent event) {        // to remove candidate
		try {
			Media letterSound = new Media(new File("F:\\ecloipesWorkspace\\task4\\src\\application\\beep1.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
			mediaPlayer.play();
			Stage primaryStage=new Stage();
			javafx.scene.image.Image image2 = new javafx.scene.image.Image(
					getClass().getResource("image5.jpg").toExternalForm());
			ImageView iv1 = new ImageView(image2);
			 primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("deletecandidate.fxml"));
			// Scene scene =new Scene(root);
			primaryStage.setScene(new Scene(root, 1800, 850));
			primaryStage.setMaximized(true);
			// primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void remove2ButtonCliked(ActionEvent event) {   //to delete a party
		try {
			Media letterSound = new Media(new File("F:\\ecloipesWorkspace\\task4\\src\\application\\beep1.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
			mediaPlayer.play();
			Stage primaryStage=new Stage();
			javafx.scene.image.Image image2 = new javafx.scene.image.Image(
					getClass().getResource("image5.jpg").toExternalForm());
			ImageView iv1 = new ImageView(image2);
			 primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("deleteparty.fxml"));
			// Scene scene =new Scene(root);
			primaryStage.setScene(new Scene(root, 1800, 850));
			primaryStage.setMaximized(true);
			// primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removecandidateButtonCliked(ActionEvent event) {        //to renove candidate   
		try {
			Media letterSound = new Media(new File("F:\\ecloipesWorkspace\\task4\\src\\application\\beep1.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
			mediaPlayer.play();
			Stage primaryStage=new Stage();
			javafx.scene.image.Image image2 = new javafx.scene.image.Image(
					getClass().getResource("image5.jpg").toExternalForm());
			ImageView iv1 = new ImageView(image2);
			
			
			

			String c=cnic.getText();
			 java.sql.Statement s2 = null;
			String query="delete from candidate where cnic="+c;
			 try {
				 s2 = ConnectionBuilderMysql.buildConnection().createStatement();
					
					s2.executeUpdate(query);
					System.out.println("deleted");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      
			 primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("Admintasks.fxml"));
			// Scene scene =new Scene(root);
			primaryStage.setScene(new Scene(root, 1800, 850));
			primaryStage.setMaximized(true);
			// primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removepartyButtonCliked(ActionEvent event) {       //to remove party
		try {
			Media letterSound = new Media(new File("F:\\ecloipesWorkspace\\task4\\src\\application\\beep1.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
			mediaPlayer.play();
			Stage primaryStage=new Stage();
			javafx.scene.image.Image image2 = new javafx.scene.image.Image(
					getClass().getResource("image5.jpg").toExternalForm());
			ImageView iv1 = new ImageView(image2);
			
			
			

			String c=cnic.getText();
			 java.sql.Statement s2 = null;
			String query="delete from party where cnic="+c;
			 try {
				 s2 = ConnectionBuilderMysql.buildConnection().createStatement();
					
					s2.executeUpdate(query);
					System.out.println("deleted");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      
			 primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("Admintasks.fxml"));
			// Scene scene =new Scene(root);
			primaryStage.setScene(new Scene(root, 1800, 850));
			primaryStage.setMaximized(true);
			// primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void ResultButtonCliked(ActionEvent event) {        //to show results
		try {
			Media letterSound = new Media(new File("F:\\ecloipesWorkspace\\task4\\src\\application\\beep1.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
			mediaPlayer.play();
			int temp,max=0;
			String n="";
			Stage primaryStage=new Stage();
			javafx.scene.image.Image image2 = new javafx.scene.image.Image(
					getClass().getResource("image5.jpg").toExternalForm());
			ImageView iv1 = new ImageView(image2);
			java.sql.Statement s2 = null;
			try {
				s2 = ConnectionBuilderMysql.buildConnection().createStatement();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	try {
		ResultSet str2 =s2.executeQuery("Select name,t_count from party");
		
		while(str2.next()) {
			n=str2.getString("name");
			temp=Integer.parseInt(str2.getString("t_count"));
			if(temp>max)
				max=temp;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	winner.setText(pr1);
			 primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("results.fxml"));
			// Scene scene =new Scene(root);
			primaryStage.setScene(new Scene(root, 1800, 850));
			primaryStage.setMaximized(true);
			// primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void pichartButtonCliked(ActionEvent event) {     //to show percentage in form of piechart
		try {
			Media letterSound = new Media(new File("F:\\ecloipesWorkspace\\task4\\src\\application\\beep1.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
			mediaPlayer.play();
			Stage primaryStage=new Stage();
			javafx.scene.image.Image image2 = new javafx.scene.image.Image(
					getClass().getResource("image5.jpg").toExternalForm());
			ImageView iv1 = new ImageView(image2);
			
			
	
			 primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("precentage.fxml"));
			// Scene scene =new Scene(root);
			System.out.println(list3);
			
			//Group root1 =new Group(chart);
			primaryStage.setScene(new Scene(root, 1800, 850));
			primaryStage.setMaximized(true);
			// primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	


	public void GuestButtonCliked(ActionEvent event) {       //to show starting page
		try {
			Media letterSound = new Media(new File("F:\\ecloipesWorkspace\\task4\\src\\application\\beep1.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
			mediaPlayer.play();
			Stage primaryStage=new Stage();
			javafx.scene.image.Image image2 = new javafx.scene.image.Image(
					getClass().getResource("image1.jpg").toExternalForm());
			ImageView iv1 = new ImageView(image2);
			 primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("GuestEntry.fxml"));
			// Scene scene =new Scene(root);
			primaryStage.setScene(new Scene(root, 1800, 850));
			primaryStage.setMaximized(true);
			// primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ConfirmButtonCliked(ActionEvent event) {      //to confirm that vote has been successfully cast
		try {
			Media letterSound = new Media(new File("F:\\ecloipesWorkspace\\task4\\src\\application\\beep1.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
			mediaPlayer.play();
			int temp1=0;
			boolean flag2=false;
			Stage primaryStage=new Stage();
			javafx.scene.image.Image image2 = new javafx.scene.image.Image(
					getClass().getResource("image1.jpg").toExternalForm());
			ImageView iv1 = new ImageView(image2);
			java.sql.Statement s1 = null;
			try {
				s1 = ConnectionBuilderMysql.buildConnection().createStatement();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			java.sql.Statement s2 = null;
			try {
				s2 = ConnectionBuilderMysql.buildConnection().createStatement();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		
		String pc=combobox1.getValue();
		String c=cnic.getText();
		System.out.println("click "+" "+pc+" "+c);
		 String temp="",temp2="",temp3="";
		try {
			String q="Select cnic from vote";
			System.out.println("query is "+q);
			ResultSet str2 =s2.executeQuery(q);
			
			while(str2.next()) {
				temp=str2.getString("cnic");
				if(c.equalsIgnoreCase(temp))
				{
					 Alert mess = new Alert(AlertType.INFORMATION);
					 mess.setTitle("Error");
					 mess.setContentText("You have allready cast your vote!");
					
					 mess.setHeaderText(null);
				
					 mess.showAndWait();
			           flag2=true;
			           break;
				}
			}
				
				temp1++;
				temp=Integer.toString(temp1);
				System.out.println("count get  "+temp);
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String query="insert into vote (cnic,party_and_candidate) values (?,?)";
		if(flag2==false)
		{
		 try {
				PreparedStatement state=ConnectionBuilderMysql.buildConnection().prepareStatement(query);
				state.setString(1, c);
				state.setString(2, pc);
				
				int inserted=state.executeUpdate();
				if(inserted>0)
				{
					System.out.println("vote successfully cast");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 java.sql.Statement s3 = null;
			try {
				s2 = ConnectionBuilderMysql.buildConnection().createStatement();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(flag2==false)
			{
	try {
		String q="Select count from candidate where name_and_party='"+pc+"'";
		System.out.println("query is "+q);
		ResultSet str2 =s2.executeQuery(q);
		
		while(str2.next()) {
			temp=str2.getString("count");
			temp1=Integer.parseInt(temp); 
		}
			
			temp1++;
			temp=Integer.toString(temp1);
			System.out.println("count get  "+temp);
			
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		  query="UPDATE candidate SET count= ? WHERE name_and_party='"+pc+"'";
		 try {
				PreparedStatement state=ConnectionBuilderMysql.buildConnection().prepareStatement(query);
				state.setString(1, temp);
				
				
				int inserted=state.executeUpdate();
				if(inserted>0)
				{
					System.out.println("updated successfully cast");
					boolean flag1=false;
					for(int i=0;i<pc.length();i++)
					{
						if(pc.charAt(i)==')')
						{
							flag1=false;
							
						}
						if(flag1)
						{
							temp3=temp3+pc.charAt(i);
						}
						if(pc.charAt(i)=='(')
						{
							flag1=true;
							
						}
						
						
					}
				}
				System.out.println("party: "+temp3);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
			}
		 
		 
		 
		 
		 
		 
		 java.sql.Statement s4 = null;
			try {
				s4 = ConnectionBuilderMysql.buildConnection().createStatement();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(flag2==false)
			{
	try {
		String q="Select name,t_count from party";
		ResultSet str2 =s2.executeQuery(q);
		
		while(str2.next()) {
			temp=str2.getString("name");
			temp2=str2.getString("t_count");
			if(temp.equals(temp3))
			{temp1=Integer.parseInt(temp2); 
			break;
			}
		}
			
			temp1++;
			temp=Integer.toString(temp1);
			System.out.println("count get  "+temp);
			
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		  query="UPDATE party SET t_count= ? WHERE name='"+temp3+"'";
		 try {
				PreparedStatement state=ConnectionBuilderMysql.buildConnection().prepareStatement(query);
				state.setString(1, temp);
				
				
				int inserted=state.executeUpdate();
				if(inserted>0)
				{
					System.out.println("updated successfully cast");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
			}
		 
		 
		 
		 flag2=false;
			
			 primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
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
	@FXML

	public void ContinueButtonCliked(ActionEvent event) {     //to get votter cnic
		try {
			Media letterSound = new Media(new File("F:\\ecloipesWorkspace\\task4\\src\\application\\beep1.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
			mediaPlayer.play();
			Stage primaryStage=new Stage();
			Parent root;
			javafx.scene.image.Image image1 = new javafx.scene.image.Image(
					getClass().getResource("image6.jpg").toExternalForm());
			ImageView iv = new ImageView(image1);
			javafx.scene.image.Image image2 = new javafx.scene.image.Image(
					getClass().getResource("image5.jpg").toExternalForm());
			ImageView iv1 = new ImageView(image2);
			 System.out.println(cnic.getText()+" click");
			 primaryStage.hide();

			 if(cnic.getText().isEmpty())
			 {
				 Alert mess = new Alert(AlertType.INFORMATION);
				 mess.setTitle("Error");
				 mess.setContentText("CNIC field is empty!");
				
				 mess.setHeaderText(null);
				
				 mess.showAndWait();
				  root = FXMLLoader.load(getClass().getResource("GuestEntry.fxml"));
			 }
			 else
			 {	 root = FXMLLoader.load(getClass().getResource("GuestTasks.fxml"));

			 }
			// Scene scene =new Scene(root);
			 primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
			primaryStage.setScene(new Scene(root, 1800, 850));
			primaryStage.setMaximized(true);
			// primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void SigninButtonCliked(ActionEvent event) {       //to signin the admin   
		try {
			Media letterSound = new Media(new File("F:\\ecloipesWorkspace\\task4\\src\\application\\beep1.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
			mediaPlayer.play();
			Parent root;
			Stage primaryStage=new Stage();
			javafx.scene.image.Image image1 = new javafx.scene.image.Image(
					getClass().getResource("image6.jpg").toExternalForm());
			ImageView iv = new ImageView(image1);
			javafx.scene.image.Image image2 = new javafx.scene.image.Image(
					getClass().getResource("image5.jpg").toExternalForm());
			ImageView iv1 = new ImageView(image2);
			java.sql.Statement s2 = null;
			try {
				s2 = ConnectionBuilderMysql.buildConnection().createStatement();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	try {
		
		ResultSet str2 =s2.executeQuery("Select * from admin_info");
		while(str2.next()) {
			System.out.println(str2.getString("name")+ "  "+name.getText());
			System.out.println(str2.getString("passward")+ "  "+passward.getText());
			if((str2.getString("name").equals(name.getText()))&&(str2.getString("passward").equals(passward.getText())))
					{
				flag=true;
				break;
					}
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
			 primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
			 if(flag==false)
			 {
				 Alert mess = new Alert(AlertType.INFORMATION);
				 mess.setTitle("Error");
				 mess.setContentText("Username or Passward is incorrect!");
				
				 mess.setHeaderText(null);
				
				 mess.showAndWait();
			root = FXMLLoader.load(getClass().getResource("signin.fxml"));
			 }
			 else
			 {
				 root = FXMLLoader.load(getClass().getResource("Admintasks.fxml"));
				 flag=false;
			 }
			// Scene scene =new Scene(root);
			primaryStage.setScene(new Scene(root, 1800, 850));
			primaryStage.setMaximized(true);
			flag=false;
			// primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void PartyButtonCliked(ActionEvent event) {     //to add new party
		try {
			Media letterSound = new Media(new File("F:\\ecloipesWorkspace\\task4\\src\\application\\beep1.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
			mediaPlayer.play();
			Parent root;
			Stage primaryStage=new Stage();
			javafx.scene.image.Image image1 = new javafx.scene.image.Image(
					getClass().getResource("image6.jpg").toExternalForm());
			ImageView iv = new ImageView(image1);
			javafx.scene.image.Image image2 = new javafx.scene.image.Image(
					getClass().getResource("image5.jpg").toExternalForm());
			ImageView iv1 = new ImageView(image2);
			
		 root = FXMLLoader.load(getClass().getResource("party.fxml"));
			// Scene scene =new Scene(root);
			primaryStage.setScene(new Scene(root, 1800, 850));
			primaryStage.setMaximized(true);
			// primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void CandidateButtonCliked(ActionEvent event) {    //to add new candidate
		try {
			Media letterSound = new Media(new File("F:\\ecloipesWorkspace\\task4\\src\\application\\beep1.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
			mediaPlayer.play();
			Stage primaryStage=new Stage();
			javafx.scene.image.Image image1 = new javafx.scene.image.Image(
					getClass().getResource("image6.jpg").toExternalForm());
			ImageView iv = new ImageView(image1);
			javafx.scene.image.Image image2 = new javafx.scene.image.Image(
					getClass().getResource("image5.jpg").toExternalForm());
			ImageView iv1 = new ImageView(image2);

			 primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("candidate.fxml"));
			// Scene scene =new Scene(root);
			primaryStage.setScene(new Scene(root, 1800, 850));
			primaryStage.setMaximized(true);
			// primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void percentageButtonCliked(ActionEvent event) {        //to show percentage
		try {
			Media letterSound = new Media(new File("F:\\ecloipesWorkspace\\task4\\src\\application\\beep1.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
			mediaPlayer.play();
			Stage primaryStage=new Stage();
			javafx.scene.image.Image image1 = new javafx.scene.image.Image(
					getClass().getResource("image6.jpg").toExternalForm());
			ImageView iv = new ImageView(image1);
			javafx.scene.image.Image image2 = new javafx.scene.image.Image(
					getClass().getResource("image5.jpg").toExternalForm());
			ImageView iv1 = new ImageView(image2);

			 primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("precentage.fxml"));
			// Scene scene =new Scene(root);
			primaryStage.setScene(new Scene(root, 1800, 850));
			primaryStage.setMaximized(true);
			// primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void resultsButtonCliked(ActionEvent event) {         //to show results
		try {
			Media letterSound = new Media(new File("F:\\ecloipesWorkspace\\task4\\src\\application\\beep1.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
			mediaPlayer.play();
			Stage primaryStage=new Stage();
			javafx.scene.image.Image image1 = new javafx.scene.image.Image(
					getClass().getResource("image6.jpg").toExternalForm());
			ImageView iv = new ImageView(image1);
			javafx.scene.image.Image image2 = new javafx.scene.image.Image(
					getClass().getResource("image5.jpg").toExternalForm());
			ImageView iv1 = new ImageView(image2);

			 primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("results.fxml"));
			// Scene scene =new Scene(root);
			primaryStage.setScene(new Scene(root, 1800, 850));
			primaryStage.setMaximized(true);
			// primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void DiscriptionButtonCliked(ActionEvent event) {    //to show how to cast vote
		try {
			Media letterSound = new Media(new File("F:\\ecloipesWorkspace\\task4\\src\\application\\beep1.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
			mediaPlayer.play();
			Stage primaryStage=new Stage();
			javafx.scene.image.Image image1 = new javafx.scene.image.Image(
					getClass().getResource("image6.jpg").toExternalForm());
			ImageView iv = new ImageView(image1);
			javafx.scene.image.Image image2 = new javafx.scene.image.Image(
					getClass().getResource("image5.jpg").toExternalForm());
			ImageView iv1 = new ImageView(image2);

			 primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("Description.fxml"));
			// Scene scene =new Scene(root);
			primaryStage.setScene(new Scene(root, 1800, 850));
			primaryStage.setMaximized(true);
			// primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void CastButtonCliked(ActionEvent event) {     //to cast vote
		try {
			Media letterSound = new Media(new File("F:\\ecloipesWorkspace\\task4\\src\\application\\beep1.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
			mediaPlayer.play();
			Stage primaryStage=new Stage();
			javafx.scene.image.Image image1 = new javafx.scene.image.Image(
					getClass().getResource("image6.jpg").toExternalForm());
			ImageView iv = new ImageView(image1);
			javafx.scene.image.Image image2 = new javafx.scene.image.Image(
					getClass().getResource("image5.jpg").toExternalForm());
			ImageView iv1 = new ImageView(image2);

			 primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("Vote.fxml"));
			// Scene scene =new Scene(root);
			primaryStage.setScene(new Scene(root, 1800, 850));
			primaryStage.setMaximized(true);
			// primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void BackButtonCliked(ActionEvent event) {    //to go to previous page
		try {  
			Media letterSound = new Media(new File("F:\\ecloipesWorkspace\\task4\\src\\application\\beep1.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
			mediaPlayer.play();
			Stage primaryStage=new Stage();
			javafx.scene.image.Image image1 = new javafx.scene.image.Image(
					getClass().getResource("image6.jpg").toExternalForm());
			ImageView iv = new ImageView(image1);
			javafx.scene.image.Image image2 = new javafx.scene.image.Image(
					getClass().getResource("image5.jpg").toExternalForm());
			ImageView iv1 = new ImageView(image2);

			 primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("Admintasks.fxml"));
			// Scene scene =new Scene(root);
			primaryStage.setScene(new Scene(root, 1800, 850));
			primaryStage.setMaximized(true);
			// primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void AdminButtonCliked(ActionEvent event) { //to go to admin page
		try {
			Media letterSound = new Media(new File("F:\\ecloipesWorkspace\\task4\\src\\application\\beep1.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
			mediaPlayer.play();
			Stage primaryStage=new Stage();
			javafx.scene.image.Image image1 = new javafx.scene.image.Image(
					getClass().getResource("image6.jpg").toExternalForm());
			ImageView iv = new ImageView(image1);
			javafx.scene.image.Image image2 = new javafx.scene.image.Image(
					getClass().getResource("image5.jpg").toExternalForm());
			ImageView iv1 = new ImageView(image2);

			 primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
			// Scene scene =new Scene(root);
			primaryStage.setScene(new Scene(root, 1800, 850));
			primaryStage.setMaximized(true);
			// primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@FXML
	public void SignupSuccessfull(ActionEvent event) {    //to sign up a new admin

		Media letterSound = new Media(new File("F:\\ecloipesWorkspace\\task4\\src\\application\\beep1.wav").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
		Parent	  root;
		mediaPlayer.play();
		try {
			Stage primaryStage=new Stage();
			javafx.scene.image.Image image1 = new javafx.scene.image.Image(
					getClass().getResource("image4.jpg").toExternalForm());
			ImageView iv = new ImageView(image1);
			javafx.scene.image.Image image2 = new javafx.scene.image.Image(
					getClass().getResource("image5.jpg").toExternalForm());
			ImageView iv1 = new ImageView(image2);
			java.sql.Statement s1 = null;
			try {
				s1 = ConnectionBuilderMysql.buildConnection().createStatement();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String n=name.getText();
			String c=cnic.getText();
			String p=passward.getText();
			String cp=confirm_passward.getText();
		System.out.println("click"+n+" "+c+" "+p+" "+cp);
		if(!passward.getText().equals(confirm_passward.getText()))
		 {
			 Alert mess = new Alert(AlertType.INFORMATION);
			 mess.setTitle("passward not matched");
			 mess.setContentText("Please enter same as passward field!");
			
			 mess.setHeaderText(null);
			
			 mess.showAndWait();
	           root= FXMLLoader.load(getClass().getResource("signup.fxml"));
		 }
		else {
		String query="insert into admin_info (name,cnic,passward) values (?,?,?)";
		 try {
				PreparedStatement state=ConnectionBuilderMysql.buildConnection().prepareStatement(query);
				state.setString(1, n);
				state.setString(2, c);
				state.setString(3, p);
				int inserted=state.executeUpdate();
				if(inserted>0)
				{
					System.out.println("signup successfully done");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("Admintasks.fxml"));
			flag=false;
			// Scene scene =new Scene(root);
		}
			primaryStage.setScene(new Scene(root, 1800, 850));
			primaryStage.setMaximized(true);
			// primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		combobox.setItems(parties);
		combobox1.setItems(candidate);
		java.sql.Statement s2 = null;
		try {
			s2 = ConnectionBuilderMysql.buildConnection().createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
try {
	ResultSet str2 =s2.executeQuery("Select name,t_count from party");
	
	

	PieChart.Data d = null;
	while(str2.next()) {
		int i=Integer.parseInt(str2.getString("t_count"));
		d=new PieChart.Data(str2.getString("name"),i);
			list3.add(d);
	}
	
	
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

		chart.setData(list3);

		String pr="";
		int max=0;
		java.sql.Statement s3 = null;
		try {
			s2 = ConnectionBuilderMysql.buildConnection().createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
try {
	ResultSet str2 =s2.executeQuery("Select name,t_count from party");
	
	while(str2.next()) {
		String n=str2.getString("name");
		int temp=Integer.parseInt(str2.getString("t_count"));
		if(temp>max)
			{max=temp;
			pr=n;
			pr1=n;
			}
			
	}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
winner.setText(pr);
System.out.println("--->"+pr);
	}

}
