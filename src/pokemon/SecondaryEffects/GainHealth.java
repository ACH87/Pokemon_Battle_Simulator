package pokemon.SecondaryEffects;

import pokemon.AttackEffects;
import pokemon.Pokemon;

public class GainHealth implements AttackEffects {
	
	private int percentage = 0;

	public GainHealth(int percentage){
		this.percentage = percentage;
	}
	
	@Override
	public int applyEffect(Pokemon user, Pokemon target, int damage) {
		user.regainHealth((int) Math.ceil(damage*percentage));
		return damage;
	}

}
