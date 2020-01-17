package pokemon;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Pokemon {

	private String name = null;
	private Attack[] learnset = null;
	private Attack[] attacks = null;
	private Type primaryTyping = null;
	private Type secondaryTyping = null;
	private int level;
	private StatusConditions statusCondition;
	private Item item;
	private Ability ablility;
	private HashMap<Attribute, Integer> stats = new HashMap<Attribute, Integer>();
	private int currentHealth;
	private int maxHealth;
	private HashMap<Attribute, Integer> evs = new HashMap<Attribute, Integer>();
	private HashMap<Attribute, Integer> ivs = new HashMap<Attribute, Integer>();
	private boolean inBattle = false;
	private int currentBoost;
	private Nature nature;
	
	public Pokemon(String name, Attack[] learnset, Type primaryTyping, Type secondaryTyping,
			int level, HashMap<Attribute, Integer> baseStats, Nature nature) {
		super();
		if(attacks.length > 4){
			throw new IllegalArgumentException("Can't have more than 4 attacks");
		}
		this.name = name;
		this.learnset = learnset;
		this.primaryTyping = primaryTyping;
		this.secondaryTyping = secondaryTyping;
		this.level = level;
		this.stats = baseStats;
		this.currentHealth = (int) stats.get(Attribute.HP);
		this.maxHealth = this.currentHealth;
		this.nature = nature;
	}
	
	public void setAttacks(Attack[] attacks){
		if(attacks.length > 4){
			throw new IllegalArgumentException("Cannot set more than four attacks");
		}
		this.attacks = attacks;
	}
	
	public void setEvsIvs(HashMap<Attribute, Integer> evs, HashMap<Attribute, Integer> ivs){
		this.evs = evs;
		this.ivs = ivs;
		for(Attribute stat: stats.keySet()){
			int finalStat = (int) ((2*(stats.get(stat) + ivs.get(stat)) 
					+ ((Math.sqrt(evs.get(stat))/4) * level )/ 100) +5);
			stats.put(stat, finalStat);
		}
		
		Attribute pos = this.nature.getPositive();
		
		if(pos != null){
			int stat = (int) ((Integer) stats.get(pos) * 1.1);
			stats.put(pos, stat);
			//a positive nature must have a negative stat
			Attribute neg = this.nature.getNegative();
			stat = (int) ((Integer) stats.get(neg) * 0.9);
			stats.put(neg, stat);
		}
		
		stats.put(Attribute.HP, (Integer)stats.get(Attribute.HP) + 5 + level);
	}
	
	public int attack(int move, Pokemon target){
		if(move<1 || move>3) throw new IllegalArgumentException("inccorect move selected");
		int damage = attacks[move].attack(this, target);
		target.damage(damage);
		return damage;
	}
	
	public Type getPrimaryTyping(){
		return this.primaryTyping;
	}
	
	public Type getSecondaryTyping(){
		return this.secondaryTyping;
	}
	
	public Double getTypeEffectiveness(Type t){
		return 1.0 * primaryTyping.findMultiplier(t) * secondaryTyping.findMultiplier(t);
	}
	
	public int getLvl(){
		return level;
	}
	
	public int getStat(Attribute s){
		return (int) stats.get(s);
	}
	
	public StatusConditions getStatusCondition(){
		return statusCondition;
	}
	
	public void setStatusCondition(StatusConditions s){
		this.statusCondition = s;
		if(s == statusCondition.Burn){
			boostStat(Attribute.ATTACK, -2);
		}
	}
	
	public void setItem(Item i){
		this.item = i;
	}
	
	public void damage(int i){
		currentHealth -= i;
	}
	
	public void regainHealth(int i){
		currentHealth = Math.min(currentHealth + i, maxHealth);
	}
	
	public void switchIn(){
		inBattle = true;
	}
	
	public void switchOut(Pokemon newPokemon){
		currentBoost = 0;
		if(statusCondition == StatusConditions.Burn){
			currentBoost = -2;
		}
		newPokemon.switchIn();
	}
	
	public void boostStat(Attribute stat, int boost){
		currentBoost += boost;
		if(currentBoost > 0){
			int newStat = (Integer) stats.get(stat) * ((currentBoost + 2) / 2);
			stats.put(stat, newStat);
		}
		else{
			int newStat = (Integer) stats.get(stat) * (2 / (currentBoost + 2)); 
			stats.put(stat, newStat);
		}
	}
	
}
