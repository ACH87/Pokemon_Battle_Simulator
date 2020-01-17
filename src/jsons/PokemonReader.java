package jsons;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Factories.FactoryCreator;
import pokemon.Attack;
import pokemon.AttackType;
import pokemon.Pokemon;

public class PokemonReader extends JsonReader {

	private Attack[] attackList = null;
	private FactoryCreator factory = null;

	public PokemonReader(String path, FactoryCreator factory) {
		super();
		this.factory = factory;
		createList(path);
	}

	private Attack[] initialiseAttacks(JSONArray learnset) {
		Attack[] attacks = new Attack[learnset.size()];
		for (int i = 0; i < learnset.size(); i++) {
			attacks[i] = factory.getAttack(learnset.get(i).toString());
		}
		return attacks;
	}

	private Pokemon initialisePokemon(JSONObject obj) {
		// Attack[] attacks, Type primaryTyping, Type secondaryTyping,
		// int level, HashMap<Stats, Integer> baseStats, Nature nature

		Pokemon pokemon = new Pokemon(obj.get("name").toString(), initialiseAttacks((JSONArray) obj.get("learnset")),
				factory.getType(obj.get("primaryTyping").toString()),
				factory.getType(obj.get("secondaryTyping").toString()), 0, null, null);

		return pokemon;
	}

	private AttackType findAttackType(String attackType) {
		if (attackType == AttackType.PHYSICAL.toString()) {
			return AttackType.PHYSICAL;
		} else if (attackType == AttackType.SPECIAL.toString()) {
			return AttackType.SPECIAL;
		} else {
			return AttackType.STATUS;
		}
	}

	private void createList(String path) {
		JSONArray moveList = readJsonArray(path);
		attackList = new Attack[moveList.size()];
		for (int i = 0; i < moveList.size(); i++) {
			attackList[i] = factory.getAttack(moveList.get(i).toString());
		}
	}

	public Attack[] getAttacks() {
		return this.attackList;
	}

}