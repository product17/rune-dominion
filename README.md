# Rune Dominion

### Adds 2 runes into the game that can be added to a Trinket inventory space above the offhand.

---

Primal Runes: can add additional slots for Trait Runes

Trait Runes: adds traits, which are stat boosts or effects

---

## Adding Traits to the runes

Step 1) add trait file to `/traits/traits/`

Step 2) use one or more of the Trait interfaces `[StatTrait, InventoryCheckTrait, DamageTrait]`

> **StatTrait**: for flat stats such as hp, added and removed when the rune is equipped and unequipped.

> **InventoryCheckTrait**: will check inventory state on tick, useful for things like requiring another piece of gear such as requiring a shield.

> **DamageTrait**: for adding damage to attacks (it just does the damage calculation just before armor takes effect).

Step 3) Add your trait to `/traits/Traits.java`

Step 4) Add your trait to `/traits/TraitMap.java`

Step 5) do a quick test... ;)

### Yay! It should be part of the trait pool now...

### ToDo:

There is still a decent amount of cleanup that can probably happen...