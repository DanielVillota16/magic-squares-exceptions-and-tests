package customExceptions;

@SuppressWarnings("serial")
public class UncompatibleValuesException extends Exception {

	private String startPoint;
	private String direction;
	
	public UncompatibleValuesException(String startPoint, String direction) {
		super("The values: <<" + startPoint + ">> and <<" + direction + ">> are not compatible.");
		this.startPoint = startPoint;
		this.direction = startPoint;
	}

	public String getStartPoint() {
		return startPoint;
	}

	public String getDirection() {
		return direction;
	}



}
