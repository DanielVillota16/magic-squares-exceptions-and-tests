package customExceptions;

@SuppressWarnings("serial")
public class OutOfRangeException extends IllegalArgumentException{
	
	private int min;
	private int max;
	private int value;
	

	public OutOfRangeException(int min, int max, int value) {
		super(value+"");
		this.min = min;
		this.max = max;
		this.value = value;
	}
	
	public String valueIsOutOff() {
		String out="";
		if(value < min) {
			out = " is less than the minimum possible value.";
		}else if(value > max) {
			out = " is more than the maximum possible value.";
		}
		return out;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() + valueIsOutOff();
	}
	
	public int getMinimum() {
		return min;
	}
	public int getMaximum(){
		return max;
	}
	public int getValue() {
		return value;
	}
	
}
