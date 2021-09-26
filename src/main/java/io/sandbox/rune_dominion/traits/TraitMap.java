package io.sandbox.rune_dominion.traits;

import java.util.HashMap;

import io.sandbox.rune_dominion.traits.traits.Barbarian;
import io.sandbox.rune_dominion.traits.traits.Guardian;
import io.sandbox.rune_dominion.traits.traits.Strength;
import io.sandbox.rune_dominion.traits.traits.Vitality;

public class TraitMap {
  public static HashMap<Traits, ? extends RuneTrait> traitList = new HashMap<>() {{
    put(Traits.BARBARIAN, new Barbarian());
    put(Traits.GUARDIAN, new Guardian());
    put(Traits.STRENGTH, new Strength());
    put(Traits.VITALITY, new Vitality());
  }};

  public static RuneTrait getTrait(Traits trait) {
    return traitList.get(trait);
  }
}
