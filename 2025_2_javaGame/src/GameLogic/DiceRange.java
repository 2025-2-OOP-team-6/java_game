package GameLogic;

public class DiceRange {
	int start;
	int end;
	
	public DiceRange(int start, int end) {
		this.start = start;
		this.end = end;
		errRange();
	}
	
	public void startChange(int num) {
		start += num;
		errRange();
	}
	
	public void endChange(int num) {
		end += num;
		errRange();
	}
	
	private void errRange() {
		if (start >= end) {
			throw new RuntimeException("end보다 start가 크거나 같음");
		}
	}
}
