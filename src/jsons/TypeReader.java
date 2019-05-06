package jsons;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pokemon.Type;

public class TypeReader extends JsonReader{
	
	private Type[] types = null;
	
	public TypeReader(String path){
		types = setTypes(path);
	}
	
	private Type initialiseType(JSONObject type){
		Type t = null;
		
		JSONArray w = (JSONArray) type.get("weaknesses");
		String weaknesses[] = new String[w.size()];
		JSONArray r = (JSONArray) type.get("resistances");
		String resistances[] = new String[r.size()];
		JSONArray i = (JSONArray) type.get("immunities");
		String immunities[] = new String[i.size()];
		
		for(int l = 0; l < w.size(); l++){
			weaknesses[l] = (String) w.get(l);
		}
		
		for(int l = 0; l < r.size(); l++){
			resistances[l] = (String) r.get(l);
		}
		
		for(int l =0; l < i.size(); l++){
			immunities[l] = (String) i.get(l);
		}
		
		t = new Type(type.get("name").toString(), resistances, weaknesses, immunities);
		
		return t;
	}
	
	private Type[] setTypes(String path){
		JSONArray types = readJsonArray(path);
		Type[] t = new Type[types.size()];
		for(int l = 0; l < types.size(); l++){
			t[l] = initialiseType((JSONObject) types.get(l));
		}
		return t;
	}
	
	public Type[] getTypes(){
		return types;
	}

}
