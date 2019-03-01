package ui;

import model.*;

import customExceptions.IllegalSizeForMagicSquareException;
import customExceptions.UncompatibleValuesException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * This is the controller class for MagicSquare.fxml file, which provides us the user interface. 
 * It allows us to modify what values we want to set in order to get a customized magic square.
 * @author Jesus Daniel Villota
 * Date: 12/02/2019
 */

public class MagicSquareController {
	
	//attributes
	
	private MagicSquare magicSquare;
	
	private Label resultH;
	
	private Label resultV;
	
	//fxml parts
	
    @FXML
    private VBox vBox;
	
	@FXML
    private MenuButton menuButtonStartPoint;
	
	@FXML
	private MenuButton menuButtonDirection;
    
	@FXML
    private TextField sizeTextField;
	
    @FXML
    private GridPane matrix;

    @FXML
    private Label errorMessage;
    
    @FXML
    private MenuItem nO;

    @FXML
    private MenuItem nE;

    @FXML
    private MenuItem sO;

    @FXML
    private MenuItem sE;
    
    @FXML
    private MenuItem menuItemFirstRow;

    @FXML
    private MenuItem menuItemFirstColumn;

    @FXML
    private MenuItem menuItemLastRow;

    @FXML
    private MenuItem menuItemLastColumn;
    
    //fxml methods - user interface interactions
    
    /**
     * This method defines the initial values for some of the attributes.
     * post: it's going to be executed every time we start the program.
     */
    
    @FXML
    public void initialize() {
		matrix.setVisible(false);
		resultH = new Label();
		resultV = new Label();
		resultH.setStyle("-fx-background-color:#ceff47;");
	    resultV.setStyle("-fx-background-color:#ceff47;");
    }
    
    /**
     * This method is called when we click on the <<Clear>> button, it just set the convenient values 
     * and availability for every menu button and text field or label. It also clears the GridPane (matrix for the magic square).
     * post: it's going to set the default values for every part of the program.
     */
    
    @FXML
    void clearItems(ActionEvent event) {
    	this.nO.setDisable(false);
    	this.sE.setDisable(false);
    	this.sO.setDisable(false);
    	this.nE.setDisable(false);
    	
    	this.sizeTextField.setText("");
    	this.errorMessage.setText("");

    	this.menuItemFirstRow.setDisable(false);
    	this.menuItemFirstColumn.setDisable(false);
    	this.menuItemLastRow.setDisable(false);
    	this.menuItemLastColumn.setDisable(false);
    	
    	this.menuButtonStartPoint.setText("None");
    	this.menuButtonDirection.setText("None");
    	
    	this.matrix.getChildren().clear();
    }
    
    /**
     * This method is called when the <<Generate>> button in the user interface gets clicked on.
     * Makes the GridPane matrix visible, then clears it in case of have been used before.
     * Then proceeds to try to initialize a MagicSquare object with the correspondent values gotten from
     * TextField and MenuItem controllers, if all the values are correct, every cell in the GridPane gets
     * filled up with the correspondent MagicSquare's matrix integers.
     * pre: every needed value has to be selected in the user interface before trying to generate the matrix,
     * if not, clicking on Generate button will show an error message Label.
     * post: The GridPane will be filled up with the correspondent integers.
     * @param event it's the value that we pass as argument when we click in the <<Generate>> button.
     * @see #matrix
     * @see #magicSquare
     */
    @FXML
    void generate(ActionEvent event) {
    	matrix.setVisible(true);
    	matrix.getChildren().clear();
    	errorMessage.setText("");
    	try{
	    	int size = Integer.parseInt(sizeTextField.getText());
	    	String direction = menuButtonDirection.getText() , startPoint = menuButtonStartPoint.getText();
	    	this.magicSquare = new MagicSquare(startPoint, direction, size);
        	this.magicSquare.fillMagicSquare();
        	for(int i = 0; i < size; i++) {
        		final int row = i;
        		for(int j = 0; j < size; j++) {
        			final int column = j;
        			Label number = new Label(magicSquare.getMagicSquare()[i][j] + "");
        			number.setPrefSize(30, 40);
        			number.setStyle("-fx-background-color:#ffff;");
        			number.setAlignment(Pos.CENTER);
        			number.setOnMouseClicked(new EventHandler<Event>() {
        				@Override
        				public void handle(Event e) {
        					clickGrid(row,column);
        				}
        			});
        			matrix.add(number, j, i);
        		}
        	}
    	} catch(IllegalSizeForMagicSquareException eSize) {
    		errorMessage.setText(eSize.getMessage());
    	} catch(UncompatibleValuesException eU) {
    		errorMessage.setText(eU.getMessage());
    	} catch(NumberFormatException ex){
	    	errorMessage.setText("Error, please enter the required values");
    	}
    }
    
    //The next 8 methods do essentially the same thing, they set the input values for 
    //direction and startPoint. Those values are going to be used to build a magic square.
    //(there is one method for every button)
    
    @FXML
    void selectDirectionNE(ActionEvent event) {
    	
    	this.menuButtonDirection.setText(MagicSquare.DIR_NE);
    	this.nO.setDisable(true);
    	this.sE.setDisable(true);
    	this.sO.setDisable(true);
    	
    	this.menuItemLastRow.setDisable(true);
    	this.menuItemFirstColumn.setDisable(true);
    }

    @FXML
    void selectDirectionNO(ActionEvent event) {
    	
    	this.menuButtonDirection.setText(MagicSquare.DIR_NO);
    	this.nE.setDisable(true);
    	this.sE.setDisable(true);
    	this.sO.setDisable(true);

    	this.menuItemLastColumn.setDisable(true);
    	this.menuItemLastRow.setDisable(true);
    }

    @FXML
    void selectDirectionSE(ActionEvent event) {
    	
    	this.menuButtonDirection.setText(MagicSquare.DIR_SE);
    	this.nO.setDisable(true);
    	this.nE.setDisable(true);
    	this.sO.setDisable(true);

    	this.menuItemFirstRow.setDisable(true);
    	this.menuItemFirstColumn.setDisable(true);
    }

    @FXML
    void selectDirectionSO(ActionEvent event) {
    	
    	this.menuButtonDirection.setText(MagicSquare.DIR_SO);
    	this.nE.setDisable(true);
    	this.nO.setDisable(true);
    	this.sE.setDisable(true);

    	this.menuItemFirstRow.setDisable(true);
    	this.menuItemLastColumn.setDisable(true);
    
    }

    @FXML
    void selectStartPointFirstColumn(ActionEvent event) {
    	
    	this.menuButtonStartPoint.setText(MagicSquare.START_POINT_FC);
    	this.menuItemFirstRow.setDisable(true);
    	this.menuItemLastRow.setDisable(true);
    	this.menuItemLastColumn.setDisable(true);
    	
    	this.nE.setDisable(true);
    	this.sE.setDisable(true);
    }

    @FXML
    void selectStartPointFirstRow(ActionEvent event) {
    	
    	this.menuButtonStartPoint.setText(MagicSquare.START_POINT_FR);
    	this.menuItemLastColumn.setDisable(true);
    	this.menuItemLastRow.setDisable(true);
    	this.menuItemFirstColumn.setDisable(true);
    	
    	this.sO.setDisable(true);
    	this.sE.setDisable(true);
    }
    
    @FXML
    void selectStartPointLastColumn(ActionEvent event) {
    	
    	this.menuButtonStartPoint.setText(MagicSquare.START_POINT_LC);
    	this.menuItemFirstRow.setDisable(true);
    	this.menuItemLastRow.setDisable(true);
    	this.menuItemFirstColumn.setDisable(true);
    	
    	this.nO.setDisable(true);
    	this.sO.setDisable(true);
    }

    @FXML
    void selectStartPointLastRow(ActionEvent event) {
    	
    	this.menuButtonStartPoint.setText(MagicSquare.START_POINT_LR);
    	this.menuItemFirstRow.setDisable(true);
    	this.menuItemLastColumn.setDisable(true);
    	this.menuItemFirstColumn.setDisable(true);
    	
    	this.nE.setDisable(true);
    	this.nO.setDisable(true);
    }
    
    //method for a visual aspect, but it does not depend of the fxml file
    
    /**
     * When this method is called (we can call it from the user interface clicking on an already generated magic square)
     * it will "paint" every cell located in the same row and the same column as the clicked cell.
     * Every time we click on a cell, the magic square will be re-painted 
     * in order to always get the correct row and column with a different color.
     * post: paint the correspondent row and column.
     * @param rowIndex integer value which stands for the correspondent row value of the cell where we have clicked on.
     * @param colIndex integer value which stands for the correspondent column value of the cell where we have clicked on.
     */
    public void clickGrid(int rowIndex, int colIndex) {
    	matrix.getChildren().remove(resultV); 
    	matrix.getChildren().remove(resultH);
	    if(colIndex < magicSquare.getSize() && rowIndex < magicSquare.getSize()) {
	    	for(int i = 0; i < magicSquare.getSize(); i++ ) {
	    		for(int j = 0; j < magicSquare.getSize(); j++) {
	    			if(j == colIndex) {
	    				matrix.getChildren().get(i*magicSquare.getSize()+j).
	        	    	setStyle("-fx-background-color:#41d9f4;");
	    			}else {
	    				if(i != rowIndex) {
	    					matrix.getChildren().get(i*magicSquare.getSize()+j).
    	        	    	setStyle("-fx-background-color:#ffffff");
	    				}
	    			}
	    			if(i == rowIndex) {
	    				matrix.getChildren().get(i*magicSquare.getSize()+j).
	        	    	setStyle("-fx-background-color:#41d9f4;");
	    			}else {
	    				if(j != colIndex) {
    	    				matrix.getChildren().get(i*magicSquare.getSize()+j).
    	        	    	setStyle("-fx-background-color:#ffffff;");    	    					
	    				}
	    			}
	    		}
    	    }
	    	resultH.setText(" = " + magicSquare.getMagicConstant());
   	    	resultV.setText(" = " + magicSquare.getMagicConstant());
    	    matrix.add(resultH, magicSquare.getMagicSquare().length, rowIndex);
    	    matrix.add(resultV, colIndex, magicSquare.getMagicSquare().length);
	    }
    }
}