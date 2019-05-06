package jsons;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.json.simple.JSONObject;

import pokemon.AttackEffects;
import pokemon.SecondaryEffects.BurnTarget;
import pokemon.SecondaryEffects.GainHealth;

public class SecondaryEffectReader extends JsonReader{
	
	private Map<Integer, AttackEffects> effects = new HashMap<Integer, AttackEffects>();
	
	public SecondaryEffectReader(String path) {
		super();
		effects = setEffects(path);
	}
	
	/**
	 * 
	 * @param path
	 *   json file path for secondary effects
	 * @return
	 *   organised map
	 */
	private Map<Integer, AttackEffects> setEffects(String path){
		Map<Integer, JSONObject> map = readJsonObject(path);
		Map<Integer, AttackEffects> unsortedMap = new HashMap<>();
		for(Entry<Integer, JSONObject> entry : map.entrySet()){
			AttackEffects ae = findAe(entry.getValue());
			unsortedMap.put(entry.getKey(), ae);
		}
		
		//organise map 
		Map<Integer, AttackEffects> sorted = new TreeMap<Integer, AttackEffects>(effects);
		
		return sorted;
	}
	
	private AttackEffects findAe(JSONObject ae){
		AttackEffects attackEffect = null;
		String name = (String) ae.get("name");
		if(name == "Burn"){
			attackEffect = new BurnTarget();
		}
		else if(name == "GainHealth50"){
			attackEffect = new GainHealth(50);
		}
		
		return attackEffect;
	}
	
	public Map<Integer, AttackEffects> getList(){
		return this.effects;
	}

}
