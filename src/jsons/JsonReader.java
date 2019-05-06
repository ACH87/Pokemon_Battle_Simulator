package jsons;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonReader {
	
	public JsonReader(){}
	
	protected JSONArray readJsonArray(String path){
		JSONArray arr = null;
		try {
			arr = (JSONArray)new JSONParser().parse(path);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
		return arr;
	}
	
	protected JSONObject readJsonObject(String path){
		JSONObject obj = null;
		try{
			obj = (JSONObject) new JSONParser().parse(path);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return obj;
	}
	
}
