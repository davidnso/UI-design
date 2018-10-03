package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorInput;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class InterfaceController {
	@FXML
	private Label oneUserAns;
	double p=0.10;@FXML
    private TextField sBar;
	@FXML 
	ProgressIndicator progressInd; 
	@FXML
	AnchorPane quizPane; 
	@FXML
	ListView<String> QuesArea = new ListView<String>(); 
	@FXML
	RadioButton trueAns,falseAns;
	@FXML
    private Button letsGo;
	@FXML 
	private TextArea corAns; 
	@FXML
    private Label ques,q1Label;
	@FXML
	private Label Qnum; 

	@FXML
    private Button GenKnowledge;
	@FXML
	Label stuName; 
	@FXML
	Label warning; 
	@FXML
	TextField uName;
	@FXML 
	PasswordField pWord;
	@FXML
	Button nextButton,beginButton;
	Connection conn = null; 
	String check,Ques;
	String checkP; int counter = 0;
	String conIn;String conInp; 
	public final String SQL=null;
	ArrayList userAns = new ArrayList();
	ArrayList CorrectAns = new ArrayList();String lastN;
	ArrayList<String> storedQ = new ArrayList<String>(); 
	DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
	Date now = new Date();
	storedData ishere = new storedData();
	@FXML
	ListView<String> finishedTestList = new ListView<String>();
	@FXML 
 
	static String storedDate;
	public void loginButtonClicked(ActionEvent evt) throws IOException {
		storedDate = dateFormat.format(now); 
		try {
			conIn = uName.getText();
			conInp = pWord.getText(); 
			conn = connector.DBconnection();
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery( "SELECT FirstName FROM studentinfo WHERE Username LIKE '%"+conIn+"%'");
			if(result.next()) {
			check = result.getString(1);
			
			}
			result = statement.executeQuery("SELECT FirstName FROM studentinfo WHERE Password LIKE '%" +conInp+"%'");
			if(result.next()) {
				checkP = result.getString(1); 
				
			}
			System.out.print("it worked "+ check+ " " + checkP);
			result = statement.executeQuery("SELECT LastName FROM studentinfo WHERE FirstName LIKE '%" +check+"%'");
			if(result.next()) {
				lastN = result.getString(1);
				
			}
			statement.execute("CREATE TABLE "+ check + "_"+lastN+ "_"+ storedDate+"(Questions text,CorrectAnswer text,StudentAnswer text)");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("it didn't work");
		}
		if(check.equals(checkP)) {
		System.out.print("button clicked");
		System.out.println(check + " "+ lastN);
		ishere.setFirst(check);
		ishere.setLast(lastN);
		
		Parent newpage = FXMLLoader.load(getClass().getResource("UI.fxml"));
		Scene newPageScene = new Scene(newpage); 
		
		Stage window = (Stage) ((Node)evt.getSource()).getScene().getWindow(); 
		window.setScene(newPageScene);
		
		window.show();
		}else {warning.setVisible(true);}
		File F = new File("TableName.txt"); 
		FileWriter P =new FileWriter(F,true); 
		PrintWriter OF = new PrintWriter(P); 
		OF.println(check + "_"+lastN+ "_"+ storedDate);
		OF.close();
		
	}
	 

	    @FXML
	    void GenKnowledgeClicked(ActionEvent event) throws IOException {
	    	
	    	Parent quizP = FXMLLoader.load(getClass().getResource("mainQ.fxml"));  
	    	Scene newP = new Scene(quizP); 
	    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow(); 
	    	
	    	window.setScene(newP);
	    	window.show(); 

	    }
	    @FXML
	    void nextButtonClicked(ActionEvent evt) throws IOException, SQLException{
	    	System.out.print(ishere.getFirst());
	    	String sw,sw2;
	    	int qnum;
	    	double Op =0.0;
	    	Random rand = new Random(); 
	    	int num = rand.nextInt(130)+1;
	    	conn = connector.DBconnection();
	    	Statement statement = conn.createStatement();
	    	ResultSet result = statement.executeQuery("SELECT Questions from TestBank WHERE ID ="+ num);
	    	Statement otherStat = conn.createStatement();
	    	ResultSet rs = otherStat.executeQuery("SELECT Answers from TestBank WHERE ID ="+ num);
	    	if(result.next()) {
	    		Ques = result.getString(1); 
	    	}
	    	if(rs.next()) {
	    		String storedAns = rs.getString(1);
	    		CorrectAns.add(storedAns);
	    	}
	    	System.out.println(Ques +" "+ num);
	    	ques.setText(Ques);
	    	storedQ.add(Ques);
	    	qnum = Integer.parseInt(Qnum.getText());
	    	qnum+=1;
	    	sw = Integer.toString(qnum);
	    	Qnum.setText(sw);
	    	if(trueAns.isSelected()) {
	    		System.out.print("TRUE");
	    		userAns.add(trueAns.getText().toUpperCase());
	    	oneUserAns.setVisible(true);	
	    		
	    	}
	    	if(falseAns.isSelected()) {
	    		System.out.print("FALSE");
	    		userAns.add(falseAns.getText().toUpperCase());
	    		 
	    	}
	    	progressInd.setProgress(p);
	    	p+=.10;
	    	System.out.print(progressInd.getProgress());
	    	if(progressInd.getProgress() == .9999999999999999) {
	    		for(int i=0;i<userAns.size();i++) {
		    		if(userAns.get(i).equals(CorrectAns.get(i))) {
		    			counter++;
		    			System.out.print(counter);
		    		}
		    	}	
	    		Parent doneP = FXMLLoader.load(getClass().getResource("done.fxml"));
	    		Scene finito = new Scene(doneP); 
	    		Stage window = (Stage)((Node)evt.getSource()).getScene().getWindow(); 
	    		window.setScene(finito);
	    		window.show(); 
	    		System.out.println(userAns + " " +CorrectAns);
	    		System.out.println(counter+ "/10");
	    		System.out.println(dateFormat.format(now) + "\n"+check + " " + lastN);
	    		System.out.println(storedQ.get(1));
	    		for(int i=0;i<storedQ.size()-1;i++) {
	    		statement.execute("INSERT INTO "+ishere.getFirst()+"_"+ishere.getLast()+"_"+storedDate+"(Questions,CorrectAnswer,StudentAnswer) VALUES("+"'" + storedQ.get(i)+"','" +CorrectAns.get(i)+"','"+ userAns.get(i)+"')");
	    		}
	    		}
	    	
	    	trueAns.setSelected(false);
	    	falseAns.setSelected(false);
	    }
	   public void beginButtonClicked(ActionEvent evt) throws SQLException {
	    	Random rand = new Random(); 
	    	int num = rand.nextInt(130)+1;
	    	conn = connector.DBconnection();
	    	Statement statement = conn.createStatement();
	    	ResultSet result = statement.executeQuery("SELECT Questions from TestBank WHERE ID ="+ num); 
	    	if(result.next()) {
	    		Ques = result.getString(1); 
	    	}
	    	ques.setText(Ques); 
	    	quizPane.setVisible(true);
	    	beginButton.setVisible(false);    	
	    }
	   @FXML
	    void goButtonClicked(ActionEvent event) throws FileNotFoundException {
		File F = new File("TableName.txt"); 
		Scanner K = new Scanner(F); 
		while(K.hasNextLine()){
			
			String foundLines = K.nextLine();
			((List) finishedTestList).add(foundLines);
			}
		
	    }
	   
}
