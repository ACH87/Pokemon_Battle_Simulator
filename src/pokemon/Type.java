package pokemon;

import java.util.Arrays;

public class Type {

	private String name = null;
	private String[] resistances = null;
	private String[] weaknesses = null;
	private String[] immunities = null;
	
	public Type(String name, String[] resistances, String[] weaknesses, String[] immunities) {
		super();
		this.name = name;
		this.resistances = resistances;
		this.weaknesses = weaknesses;
		this.immunities = immunities;
	}
	
	public double findMultiplier(Type t){
		double multiplier = 1;
		String typeName = t.getName();
		if(Arrays.stream(resistances).anyMatch(typeName::equals)){
			multiplier = 0.5;
		}
		else if(Arrays.stream(weaknesses).anyMatch(typeName::equals)){
			multiplier = 2;
		}
		else if(Arrays.stream(immunities).anyMatch(typeName::equals)){
			multiplier = 0;
		}
		return multiplier;
	}
	
	public String getName(){
		return this.name;
	}
	
}
