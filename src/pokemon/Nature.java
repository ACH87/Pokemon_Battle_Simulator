package pokemon;

public class Nature {
	
	private Attribute positiveStat = null;
	private Attribute negativeStat = null;
	
	public Nature(Attribute positive, Attribute negative){
		this.positiveStat = positive;
		this.negativeStat = negative;
	}
	
	public Attribute getPositive(){
		return this.positiveStat;
	}
	
	public Attribute getNegative(){
		return this.negativeStat;
	}

}
