package trainer;

import java.util.ArrayList;
import java.util.List;
import pokemon.Pokemon;

public class Team {
	
	private List<Pokemon> team = new ArrayList<Pokemon>();
	
	public void addNewPokemon(Pokemon pokemon){
		if(team.size() > 6){
			throw new IllegalArgumentException("Can't have more than 6 pokemon");
		}
		
		team.add(pokemon);
	}

}
