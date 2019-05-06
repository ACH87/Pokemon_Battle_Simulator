package pokemon.SecondaryEffects;

import pokemon.AttackEffects;
import pokemon.Pokemon;
import pokemon.StatusConditions;

public class BurnTarget implements AttackEffects{

	public BurnTarget() {
		super();
	}

	@Override
	public int applyEffect(Pokemon user, Pokemon target, int damage) {
		target.setStatusCondition(StatusConditions.Burn);		
		return damage;
	}
}
