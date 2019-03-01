package customExceptions;

@SuppressWarnings("serial")
public class IllegalSizeForMagicSquareException extends IllegalArgumentException{

	public static final String LESS_THAN_ONE = "less than 1";
	public static final String NOT_ODD = "not an odd integer";
	
	private String type;
	private String customMessage;
	private int size;
	
	public IllegalSizeForMagicSquareException(int size) {
		super("The size has not a correct value: " + size + ". ");
		this.size = size;
		calculateType(size);
		customMessage = "The value is " + type;
	}

	public void calculateType(int size) {
		if(size < 1) {
			type = LESS_THAN_ONE;
		}else if(size%2 == 0) {
			type = NOT_ODD;
		}
	}
	
	@Override
	public String getMessage() {
		String msg;
		msg = super.getMessage() + customMessage;
		return msg;
	}
	
	public String getType() {
		return type;
	}
	
	public int getSize() {
		return this.size;
	}
}