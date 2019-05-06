package pokemon;

public class Nature {
	
	private Stats positiveStat = null;
	private Stats negativeStat = null;
	
	public Nature(Stats positive, Stats negative){
		this.positiveStat = positive;
		this.negativeStat = negative;
	}
	
	public Stats getPositive(){
		return this.positiveStat;
	}
	
	public Stats getNegative(){
		return this.negativeStat;
	}

}
