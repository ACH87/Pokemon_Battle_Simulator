package jsons;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import factories.FactoryCreator;
import pokemon.Attack;
import pokemon.AttackEffects;
import pokemon.AttackType;
import pokemon.Type;

public class MoveReader extends JsonReader {
	
	private Attack[] attackList = null;
	private FactoryCreator factory = null;

	public MoveReader(String path, FactoryCreator factory) {
		super();
		this.factory = factory;	
		createList(path);
	}
	
	private Attack initialiseAttack(JSONObject obj){
		Type type = factory.getType(obj.get("type").toString());
		AttackEffects secondaryEffect = factory.getEffect((Integer)obj.get("secondaryEffect"));
		Attack newAttack = new Attack(obj.get("name").toString(), (int) obj.get("basePower"), type, 
				findAttackType(obj.get("attackType").toString()), secondaryEffect, 
				(int) obj.get("secondaryEffectChance"), (int) obj.get("targets"));
		return newAttack;
	}
	
	private AttackType findAttackType(String attackType){
		if(attackType == AttackType.PHYSICAL.toString()){
			return AttackType.PHYSICAL;
		}
		else if(attackType == AttackType.SPECIAL.toString()){
			return AttackType.SPECIAL;
		}
		else{
			return AttackType.STATUS;
		}
	}
	
	private void createList(String path){
		JSONArray moveList = readJsonArray(path);
		attackList = new Attack[moveList.size()];
		for(int i = 0; i < moveList.size(); i++){
			attackList[i] = initialiseAttack( (JSONObject) moveList.get(i));
		}
	}
	
	public Attack[] getAttacks(){
		return this.attackList;
	}
	
}
