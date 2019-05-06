package pokemon;

import java.util.Random;

public class Attack {
	
	private String name = null;
	private int basePower = 0;
	private Type type = null;
	private AttackEffects effect = null;
	private int effectChance = 0;
	private AttackType attackType = null;
	private int targets = 1;
	private double otherModifier = 1.0;
	
	/**
	 * 
	 * @param name
	 *   name of attack
	 * @param basePower
	 *   base power of attack
	 * @param type
	 *   type of attack
	 * @param attackType
	 *   special or physical attack
	 * @param effect
	 *   attack effect (i.e burn)
	 * @param effectChance
	 *   chance of effect e.g 30% chance to burn
	 * @param targets
	 *   number of targets (i.e swift hits 2 targets)
	 */
	public Attack(String name, int basePower, Type type, AttackType attackType, AttackEffects effect,
			int effectChance, int targets){
		this.name = name;
		this.basePower = basePower;
		this.type =  type;
		this.attackType = attackType;
		this.effect = effect;
		this.effectChance = effectChance;
		if(targets != 0 ){
			this.targets = targets;
		}
	}

	public String getName() {
		return name;
	}

	public int getBasePower() {
		return basePower;
	}

	public Type getType() {
		return type;
	}
	
	public void setModifier(double mod ){
		this.otherModifier = mod;
	}
	
	/**
	 * 
	 * @param user
	 *   pokemon attacking
	 * @param target
	 *   target user 
	 * @return
	 *   damage
	 */
	public int attack(Pokemon user, Pokemon target){
		Double multiplier = 1.0;
		
		//apply same type attack bonus
		if(stab(user)){
			multiplier = 1.5;
		}
		
		//multiple by resistances / weaknesses
		multiplier = multiplier * target.getTypeEffectiveness(type);	
		
		// if burnt pyshical attack is halved (attack stat is not altered)
		if(user.getStatusCondition() == StatusConditions.Burn &&
				attackType == AttackType.PHYSICAL){
			multiplier = 0.5;
		}
		
		int modifier = calculateModifier(multiplier);
		
		int damage = modifier * calculatePreModDamage(user, target);
		
		//apply rng to attack
		Random rng = new Random();
		if(rng.nextInt(100) + 1 <= effectChance){
			effect.applyEffect(user, target, damage);
		}
		
		return damage;
	}
	
	/**
	 * @param user
	 *   attacking pokemon
	 * @return
	 *   if user is same type has attacl
	 */
	private Boolean stab(Pokemon user){
		Boolean result = false;
		if(user.getPrimaryTyping() == type || user.getSecondaryTyping() == type){
			result = true;
		}
		return result;
	}
	
	/**
	 * formula to apply multiplier (found on www.bulbapeidia)
	 * 
	 * @param multiplier
	 *   initial multipler 
	 * @return
	 *   final multiplier
	 */
	private int calculateModifier(Double multiplier){
		Random rng = new Random();
		double damage = (rng.nextInt(16) + 85) * multiplier;
		if(rng.nextInt(24) == 0){
			damage *= 0.5;
		}
		if(targets > 1){
			damage *= 0.75;
		}
		return (int) (Math.ceil(damage) * otherModifier);
	}
	
	/**
	 * calculate attack damage
	 * @param user
	 *   attacking pokemon
	 * @param target
	 *   target pokemon
	 * @return
	 *   return damage
	 */
	private int calculatePreModDamage(Pokemon user, Pokemon target){
		int preModDamage = 0;
		if(attackType == AttackType.PHYSICAL){
			preModDamage = ((2*user.getLvl())/5 + 2) * basePower * 
					(user.getStat(Stats.ATTACK)/target.getStat(Stats.DEFENSE));
			preModDamage = (int) Math.ceil((preModDamage/50) + 2);
		}
		else if(attackType == AttackType.SPECIAL){
			preModDamage = ((2*user.getLvl())/5 + 2) * basePower * 
					(user.getStat(Stats.SPECIAL_ATTACK)/target.getStat(Stats.SPECIAL_DEFENSE));
			preModDamage = (int) Math.ceil((preModDamage/50) + 2);
		}
		return preModDamage;
	}

}
