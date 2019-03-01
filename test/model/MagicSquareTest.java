package model;

import static org.junit.Assert.*;

import org.junit.Test;

import customExceptions.*;

public class MagicSquareTest {
	
	private MagicSquare ms;
	
	private void setupScenary1() {
		
	}
	
	private void setupScenary2() {
		ms = new MagicSquare(MagicSquare.START_POINT_FR, MagicSquare.DIR_NE, 5);
	}
	
	private void setupScenary3() {
		ms = new MagicSquare(MagicSquare.START_POINT_FR, MagicSquare.DIR_SO, 5);
	}
	
	@Test
	public void testMagicSquare1() {
		setupScenary1();
		String startPoint = MagicSquare.START_POINT_FC;
		String direction = MagicSquare.DIR_NE;
		int size = 5;
		MagicSquare magicSquare = null;
		try {
			magicSquare = new MagicSquare(startPoint, direction, size);
		} catch(IllegalArgumentException eIA) {
			fail();
		}
		assertTrue("The startpoint value failed", magicSquare.getStartPoint().equals(startPoint));
		assertTrue("The direction value failed", magicSquare.getDirection().equals(direction));
		assertTrue("The size value failed", magicSquare.getSize() == size);
	}
	
	@Test
	public void testMagicSquare2() {
		setupScenary1();
		String startPoint = MagicSquare.START_POINT_FR;
		String direction = MagicSquare.DIR_NE;
		int size = 0;
		try {
			@SuppressWarnings("unused")
			MagicSquare magicSquare = new MagicSquare(startPoint, direction, size);
			fail();
		}catch (IllegalSizeForMagicSquareException eSize) {
			System.out.println(eSize.getMessage());
		}catch(IllegalArgumentException e){
			fail();
		}
		
	}
	
	@Test
	public void testMagicSquare3() {
		setupScenary1();
		String startPoint = MagicSquare.START_POINT_FR;
		String direction = MagicSquare.DIR_NE;
		int size = -1;
		try {
			@SuppressWarnings("unused")
			MagicSquare magicSquare = new MagicSquare(startPoint, direction, size);
			fail();
		}catch (IllegalSizeForMagicSquareException e) {
			System.out.println(e.getMessage());
		}catch(IllegalArgumentException e){
			fail();
		} 
	}
	
	@Test
	public void testMagicSquare4() {
		setupScenary1();
		String startPoint = MagicSquare.START_POINT_FR;
		String direction = MagicSquare.DIR_NE;
		int size = 2;
		try {
			@SuppressWarnings("unused")
			MagicSquare magicSquare = new MagicSquare(startPoint, direction, size);
			fail();
		}catch (IllegalSizeForMagicSquareException e) {
			System.out.println(e.getMessage());
		}catch(IllegalArgumentException e){
			fail();
		}
	}
	
	@Test
	public void testMagicSquare5() {
		setupScenary1();
		String startPoint = MagicSquare.START_POINT_FR;
		String direction = "RandomString";
		int size = 5;
		try {
			@SuppressWarnings("unused")
			MagicSquare magicSquare = new MagicSquare(startPoint, direction, size);
			fail();
		}catch (IllegalSizeForMagicSquareException e) {
			fail();
		}catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testMagicSquare6() {
		setupScenary1();
		String startPoint = "RandomString";
		String direction = MagicSquare.DIR_NE;
		int size = 5;
		try {
			@SuppressWarnings("unused")
			MagicSquare magicSquare = new MagicSquare(startPoint, direction, size);
			fail();
		}catch (IllegalSizeForMagicSquareException e) {
			fail();
		}catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testFillMagicSquare1() {
		setupScenary2();
		try {
			ms.fillMagicSquare();
		} catch(UncompatibleValuesException e) {
			fail();
		}
		boolean noZeros = true;
		for(int i = 0; i < ms.getSize() && noZeros; i++) {
			for(int j = 0; j < ms.getSize() && noZeros; j++) {
				if(ms.getMagicSquare()[i][j] == 0) {
					noZeros = false;
				}
			}
		}
		assertTrue("The resultant magic square contains at least a zero in some cell", noZeros);
	}
	
	@Test
	public void testFillMagicSquare2() {
		setupScenary3();
		try{
			ms.fillMagicSquare();
			fail();
		}catch(UncompatibleValuesException e) {
			System.out.println(e.getMessage());
		}
	}
	@Test
	public void testFillMagicSquare3() {
		setupScenary2();
		try {
			ms.fillMagicSquare();
		} catch(UncompatibleValuesException e) {
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOverloadedFillMagicSquare1() {
		setupScenary2();
		int[] movement = {-1,1}; // MagicSquare.NE
		int[] correction = {1,0}; // correction: down
		int row = 0; // first row
		int column = ms.getSize()/2; // middle column
		ms.fillMagicSquare(movement, correction, row, column);
		int[] rowSum = new int[ms.getSize()], colSum = new int[ms.getSize()], diagSum = new int[2]; 
		for(int i = 0; i < ms.getSize(); i++) {
			int sum = 0;
			for(int j = 0; j < ms.getSize(); j++) {
				sum += ms.getMagicSquare()[i][j];
			}
			rowSum[i] = sum;
		}
		for(int i = 0; i < ms.getSize(); i++) {
			int sum = 0;
			for(int j = 0; j < ms.getSize(); j++) {
				sum += ms.getMagicSquare()[j][i];
			}
			colSum[i] = sum;
		}
		for(int i = 0; i < ms.getSize(); i++) {
			diagSum[0] += ms.getMagicSquare()[i][i];
			diagSum[1] += ms.getMagicSquare()[ms.getSize()-i-1][ms.getSize()-i-1];
		}
		boolean allSame = true;
		int sum = rowSum[0];
		for(int i = 0; i < ms.getSize() && allSame; i++) {
			if(i < 2) {
				if(diagSum[i] != sum) {
					allSame = false;
				}
			}
			if(rowSum[i] != sum || colSum[i] != sum) {
				allSame = false;
			}
		}
		assertTrue("The resultant magic square is not valid", allSame);
	}
	
	@Test
	public void testOverloadedFillMagicSquare2() {
		setupScenary2();
		int[] movement = {-1,0}; // incorrect movement
		int[] correction = {0,0}; // incorrect direction
		int row = 1; // incorrect row in start point
		int column = ms.getSize()-1; // incorrect column in start point
		ms.fillMagicSquare(movement, correction, row, column);
		int[] rowSum = new int[ms.getSize()], colSum = new int[ms.getSize()], diagSum = new int[2]; 
		for(int i = 0; i < ms.getSize(); i++) {
			int sum = 0;
			for(int j = 0; j < ms.getSize(); j++) {
				sum += ms.getMagicSquare()[i][j];
			}
			rowSum[i] += sum;
		}
		for(int i = 0; i < ms.getSize(); i++) {
			int sum = 0;
			for(int j = 0; j < ms.getSize(); j++) {
				sum += ms.getMagicSquare()[j][i];
			}
			colSum[i] += sum;
		}
		for(int i = 0; i < ms.getSize(); i++) {
			diagSum[0] += ms.getMagicSquare()[i][i];
			diagSum[1] += ms.getMagicSquare()[ms.getSize()-i-1][ms.getSize()-i-1];
		}
		boolean allSame = true;
		int sum = rowSum[0];
		for(int i = 0; i < ms.getSize() && allSame; i++) {
			if(i < 2) {
				if(diagSum[i] != sum) {
					allSame = false;
				}
			}
			if(rowSum[i] != sum || colSum[i] != sum) {
				allSame = false;
			}
		}
		assertFalse("The resultant magic square is correct", allSame);
	}
	
	@Test
	public void testOverloadedFillMagicSquare3() {
		setupScenary2();
		int[] movement = {-1,1}; // MagicSquare.NE
		int[] correction = {1,0}; // correction: down
		int row = -1; // incorrect row in start point, it's going to cause an IndexOutOfBoundsException
		int column = ms.getSize()-1; // last column position
		try{
			ms.fillMagicSquare(movement, correction, row, column);
			fail();
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testOverloadedFillMagicSquare4() {
		setupScenary2();
		int[] movement = {-5,7}; // Invalid movement vector
		int[] correction = {4,5}; // Invalid correction vector
		int row = 0; // first row
		int column = ms.getSize()/2; // middle column
		try{
			ms.fillMagicSquare(movement, correction, row, column);
			fail();
		} catch(OutOfRangeException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testCorrectPos1() {
		setupScenary2();
		int row = -1, column = ms.getSize();
		try {
			int[] corrected = ms.correctPos(row, column);
			assertTrue("", corrected[0] == ms.getSize()-1 && corrected[1] == 0);
		}catch(OutOfRangeException e) {
			fail();
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testCorrectPos2() {
		setupScenary2();
		int row = -2, column = ms.getSize();
		try {
			ms.correctPos(row, column);
			fail();
		}catch(OutOfRangeException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetMagicConstant() {
		setupScenary2();
		int[] movement = {-1,1}; // MagicSquare.NE
		int[] correction = {1,0}; // correction: down
		int row = 0; // first row
		int column = ms.getSize()/2; // middle column
		ms.fillMagicSquare(movement, correction, row, column);
		int[] rowSum = new int[ms.getSize()], colSum = new int[ms.getSize()], diagSum = new int[2]; 
		for(int i = 0; i < ms.getSize(); i++) {
			int sum = 0;
			for(int j = 0; j < ms.getSize(); j++) {
				sum += ms.getMagicSquare()[i][j];
			}
			rowSum[i] = sum;
		}
		for(int i = 0; i < ms.getSize(); i++) {
			int sum = 0;
			for(int j = 0; j < ms.getSize(); j++) {
				sum += ms.getMagicSquare()[j][i];
			}
			colSum[i] = sum;
		}
		for(int i = 0; i < ms.getSize(); i++) {
			diagSum[0] += ms.getMagicSquare()[i][i];
			diagSum[1] += ms.getMagicSquare()[ms.getSize()-i-1][ms.getSize()-i-1];
		}
		boolean allSame = true;
		int sum = rowSum[0];
		for(int i = 0; i < ms.getSize() && allSame; i++) {
			if(i < 2) {
				if(diagSum[i] != sum) {
					allSame = false;
				}
			}
			if(rowSum[i] != sum || colSum[i] != sum) {
				allSame = false;
			}
		}
		assertTrue("The magic constant doesn't coincide with the rows/columns/diagonal sums", allSame && sum == ms.getMagicConstant());
	}

	@Test
	public void testGetters() {
		setupScenary1();
		String startPoint = MagicSquare.START_POINT_FR;
		String direction = MagicSquare.DIR_NE;
		int size = 5;
		MagicSquare ms = new MagicSquare(startPoint, direction, size);
		assertTrue("The getter for startPoint is wrong", ms.getStartPoint().equals(startPoint));
		assertTrue("The getter for direction is wrong", ms.getDirection().equals(direction));
		assertTrue("The getter for size is wrong", ms.getSize() == size);
	}
	
	@Test
	public void testSetters() {
		setupScenary2();
		String startPoint = MagicSquare.START_POINT_LC;
		String direction = MagicSquare.DIR_SE;
		int size = 3;
		ms.setStartPoint(startPoint);
		ms.setDirection(direction);
		ms.setSize(size);
		assertTrue("The setter for startPoint is wrong", ms.getStartPoint().equals(startPoint));
		assertTrue("The setter for direction is wrong", ms.getDirection().equals(direction));
		assertTrue("The setter for size is wrong", ms.getSize() == size);
	}
	
}
