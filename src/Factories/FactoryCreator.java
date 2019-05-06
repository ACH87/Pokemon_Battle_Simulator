package Factories;

import java.util.Map;

import jsons.MoveReader;
import jsons.SecondaryEffectReader;
import jsons.TypeReader;
import pokemon.Attack;
import pokemon.AttackEffects;
import pokemon.Type;

public class FactoryCreator {

	private Type[] types = null;
	private Map<Integer, AttackEffects> attackEffects = null;
	private Attack[] attacks = null;
	
	public FactoryCreator(String typePath, String effectPath, String AttackPath){
		this.types = new TypeReader(typePath).getTypes(); 
		this.attackEffects = new SecondaryEffectReader(effectPath).getList();
		this.attacks = new MoveReader(AttackPath, this).getAttacks();
	}
	
	public Type getType(String name){
		for(Type type: types){
			if(type.getName() == name){
				return type;
			}
		}
		throw new IllegalArgumentException("Type name " + name + " does not exist");
	}

	public AttackEffects getEffect(int i){
		try{
			return attackEffects.get(i);
		}
		catch(Exception e){
			throw new IllegalArgumentException("Effect int " + i + " does not exist");
		}
	}
	
	public Attack getAttack(String name){
		for(Attack a: attacks){
			if(a.getName() == name){
				return a;
			}
		}
		throw new IllegalArgumentException("Attack " + name + " does not exist");
	}
	
}
