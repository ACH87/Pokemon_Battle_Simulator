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
	private int level = 1;
	private StatusConditions statusCondition = null;
	private Item item = null;
	private Ability ablility = null;
	private Map stats = new HashMap<Stats, Integer>();
	private int currentHealth = 0;
	private int maxHealth = 0;
	private Map evs = new HashMap<Stats, Integer>();
	private Map ivs = new HashMap<Stats, Integer>();
	private boolean inBattle = false;
	private int currentBoost = 0;
	private Nature nature = null;
	
	public Pokemon(String name, Attack[] learnset, Type primaryTyping, Type secondaryTyping,
			int level, HashMap<Stats, Integer> baseStats, Nature nature) {
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
		this.currentHealth = (int) stats.get(Stats.HP);
		this.maxHealth = this.currentHealth;
		this.nature = nature;
	}
	
	public void setAttacks(Attack[] attacks){
		if(attacks.length > 4){
			throw new IllegalArgumentException("Cannot set more than four attacks");
		}
		this.attacks = attacks;
	}
	
	public void setEvsIvs(HashMap<Stats, Integer> evs, HashMap<Stats, Integer> ivs){
		this.evs = evs;
		this.ivs = ivs;
		for(Object stat: stats.keySet()){
			Double finalStat = (2*((Integer)stats.get(stat) + (Integer)ivs.get(stat)) 
					+ ((Math.sqrt((Integer)evs.get(stat))/4) * level )/ 100) +5;
			stats.put(stat, Math.floor(finalStat));
		}
		
		Stats pos = this.nature.getPositive();
		
		if(pos != null){
			int stat = (int) ((Integer) stats.get(pos) * 1.1);
			stats.put(pos, stat);
			//a positive nature must have a negative stat
			Stats neg = this.nature.getNegative();
			stat = (int) ((Integer) stats.get(neg) * 0.9);
			stats.put(neg, stat);
		}
		
		stats.put(Stats.HP, (Integer)stats.get(Stats.HP) + 5 + level);
	}
	
	public int attack(int move, Pokemon target){
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
	
	public int getStat(Stats s){
		return (int) stats.get(s);
	}
	
	public StatusConditions getStatusCondition(){
		return statusCondition;
	}
	
	public void setStatusCondition(StatusConditions s){
		this.statusCondition = s;
		if(s == statusCondition.Burn){
			boostStat(Stats.ATTACK, -2);
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
	
	public void boostStat(Stats stat, int boost){
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
