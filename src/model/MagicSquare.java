package model;
import customExceptions.*;
/**
 * This class is in charge of make it possible to build a magic square matrix, it requires a few variables 
 * which are going to be provided from the user.
 * @author Jesus Daniel Villota
 * Date: 12/02/2019
 */
public class MagicSquare {

	//attributes
	
	private int[][] magicSquare;
	private String startPoint;
	private String direction;
	private int size;
	
	//constants
	
	public final static String DIR_NE = "NE";
	public final static String DIR_NO = "NO";
	public final static String DIR_SE = "SE";
	public final static String DIR_SO = "SO";
	
	public final static String START_POINT_FR = "First Row";
	public final static String START_POINT_FC = "First Column";
	public final static String START_POINT_LR = "Last Row";
	public final static String START_POINT_LC = "Last Column";

	//constructor

	/**
	 * Constructor for MagicSquare class.
	 * It allows you to create a MagicSquare object.
	 * pre: the integer for size has to be an odd number. Every parameter has its
	 * correspondent type of data.
	 * post: a magic square object can be created.
	 * @param startPoint indicates which point the magic square should start to be filled in.
	 * @param direction indicates the diagonal direction that the filling process should follow.
	 * @param size indicates the order for the magic square, it has to be an odd number.
	 * @throws AttributesWithIllegalValuesException 
	 */
	public MagicSquare(String startPoint, String direction, int size) throws IllegalArgumentException, IllegalSizeForMagicSquareException{
		if(size > 0 && size%2 != 0) {
			if((startPoint.equals(START_POINT_FR) || startPoint.equals(START_POINT_FC) 
					|| startPoint.equals(START_POINT_LR) || startPoint.equals(START_POINT_LC))
					&& (direction.equals(DIR_NE) || direction.equals(DIR_NO) 
					|| direction.equals(DIR_SE) || direction.equals(DIR_SO))) {
				this.startPoint = startPoint;
				this.direction = direction;
				this.size = size;
				this.magicSquare = new int[size][size];
			}else {
				throw new IllegalArgumentException(startPoint + " and " + direction + " are not valid arguments to create a MagicSquare");
			}
		}else {
			throw new IllegalSizeForMagicSquareException(size);
		}
	}

	//methods
	
	/**
	 * This method allows a MagicSquare object to fill the magicSquare integer matrix when it's created.
	 * pre: all the attributes have to be initialized and contain the correct values.
	 * post: every position in the magicSquare matrix will have its correspondent value.
	 * @see #fillMagicSquare()
	 */

	public void fillMagicSquare(int[] movement, int[] correction, int row, int column) {
		int ini = 0;
		while(ini < size*size) {
			if(ini == 0) {
				ini++;
				magicSquare[row][column] = ini;
			}else {
				int initRow = row;
				int initColumn = column;
				row += movement[0];
				column += movement[1];
				row = correctPos(row, column)[0];
				column = correctPos(row, column)[1];
				ini++;
				if(magicSquare[row][column] == 0) {
					magicSquare[row][column] = ini;
				}else {
					row = initRow + correction[0];
					column = initColumn + correction[1];
					row = correctPos(row, column)[0];
					column = correctPos(row, column)[1];
					magicSquare[row][column] = ini;
				}
			}
		}
	}
	
	/**
	 * This method gets the information from the MagicSquare object attributes and then decides 
	 * the value for each needed data (as the direction and correction movement) to fill the magic 
	 * square correctly. Then the gotten data is used in the overloaded method 
	 * fillMagicSquare(int[], int[], int) and there is where the matrix gets filled up.
	 * post: the matrix for the requested magic square will be filled up with the correspondent values.
	 * @see #fillMagicSquare(int[], int[], int, int)
	 */
	
	public void fillMagicSquare() throws UncompatibleValuesException{
		if((this.startPoint.equals(START_POINT_FR) && (direction.equals(DIR_NE) || direction.equals(DIR_NO)))
				|| (this.startPoint.equals(START_POINT_FC) && (this.direction.equals(DIR_NO) || this.direction.equals(DIR_SO)))
				|| (this.startPoint.equals(START_POINT_LR) && (this.direction.equals(DIR_SE) || this.direction.equals(DIR_SO)))
				|| (this.startPoint.equals(START_POINT_LC) && (this.direction.equals(DIR_NE) || this.direction.equals(DIR_SE))) ) {
			int[] up = {-1,0}, down = {1,0}, right = {0,1}, left = {0,-1};
			int[] nO = {-1,-1}, nE = {-1,1}, sO = {1,-1}, sE = {1,1};
			int[] movement = direction.equals(DIR_NE)?nE:direction.equals(DIR_NO)?nO
					:direction.equals(DIR_SE)?sE:sO;
			int[] correction = startPoint.equals(START_POINT_FR) ? down:
				startPoint.equals(START_POINT_FC) ? right :
				startPoint.equals(START_POINT_LR) ? up : left;
			int row = startPoint.equals(START_POINT_FC) || startPoint.equals(START_POINT_LC) 
					? magicSquare.length/2
					: startPoint.equals(START_POINT_FR) ? 0: magicSquare.length-1;
			int column = startPoint.equals(START_POINT_FR) || startPoint.equals(START_POINT_LR) 
					? magicSquare.length/2
					: startPoint.equals(START_POINT_FC) ? 0: magicSquare.length-1;
			fillMagicSquare(movement, correction, row, column);
		}else {
			throw new UncompatibleValuesException(startPoint, direction);
		}
	}
	
	/**
	 * This method evaluates 2 possible cases (-1 and magicSquare.length) for each parameter (integers) and then changes their values if it's necessary.
	 * @param row an integer for row value in magicSquare matrix.
	 * @param column an integer for column value in magicSquare matrix.
	 * @return location an integer array whit length = 2, the first position has the corrected value for row,
	 * the second position has the corrected value for column.
	 */
	
	public int[] correctPos(int row, int column) throws OutOfRangeException {
		int[] location = {row, column};
		if(row < -1 || row > size) {
			throw new OutOfRangeException(-1, size, row);
		}else if(column < -1 || column > size) {
			throw new OutOfRangeException(-1, size, column);
		}
		if(row == -1) {
			location[0] = size-1;
		}else if(row == size){
			location[0] = 0;
		}
		if(column == -1) {
			location[1] = size-1;
		}else if(column == size){
			location[1] = 0;
		}
		return location;
	}
	
	/**
	 * This method calculates the magic constant (result of the sum for each row, column and diagonal).
	 * post: the sum will be calculated.
	 * @return the result
	 */
	public int getMagicConstant() {
		return (size*(size*size + 1))/2;
	}
	
	//getters and setters
	
	public int[][] getMagicSquare() {
		return this.magicSquare;
	}

	/**
	 * 
	 * @param magicSquare
	 */
	public void setMagicSquare(int[][] magicSquare) {
		this.magicSquare = magicSquare;
	}

	public String getStartPoint() {
		return this.startPoint;
	}

	/**
	 * 
	 * @param startPoint
	 */
	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public String getDirection() {
		return this.direction;
	}

	/**
	 * 
	 * @param direction
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	public int getSize() {
		return this.size;
	}

	/**
	 * 
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}

}