package Code;

public class CharacterAttributes {
    // Core Stats
    public int level = 0; // value; the level of the character
    public int strength = 0; // 0.01% damage per 1 point
    public int intelligence = 0; // 0.01% to all elemental damage per 1 point;
    public int willpower = 0; // 0.01% resource generation per 1 point
    public int dexterity = 0; // 0.02% crit chance per 1 point

    // Armor & Resistances
    public int armor = 0; // value; how much armor do you have?
    public double physicalRes = 0.0; // percent; how much damage reduced from physical?
    public double fireRes = 0.0; // percent; how much damage reduced from fire?
    public double lightningRes = 0.0; // percent; how much damage reduced from lightning?
    public double coldRes = 0.0; // percent; how much damage reduced from cold?
    public double poisonRes = 0.0; // percent; how much damage reduced from poison?
    public double shadowRes = 0.0; // percent; how much damage reduced from shadow?

    // Offense
    public double baseWeaponDmg = 0.0; // value; extra damage from weapons
    public double weaponSpeed = 0.0; // percent; extra attack speed from weapons
    public double attackSpd = 0.625; // percent; x attacks per second
    public double critChance = 0.0; // percent; chance of critical hit?
    public double critDmg = 0.0; // percent; how much damage to critically hit enemies?
    public double vulnerableDmg = 0.0; // percent; how much damage to vulnerable enemies?
    public double damage = 0.0; // value; base damage amount (how much health taken away from the enemy?)
    public double physDmg = 0.0; // percent; how much extra damage using physical?
    public double fireDmg = 0.0; // percent; how much extra damage using fire?
    public double lightningDmg = 0.0; // percent; how much extra damage using lightning?
    public double coldDmg = 0.0; // percent; how much extra damage using cold?
    public double poisonDmg = 0.0; // percent; how much extra damage using poison?
    public double shadowDmg = 0.0; // percent; how much extra damage using shadow?
    public double bossDmg = 0.0; // percent; how much extra damage to bosses?
    public double thorns = 0; // percent; how much damage is reflected?

    // Defensive
    public double charMaxHP = 100; // value; character's max health value
    public double charCurrHP = 100; // value; character's current health value
    public double healBoost = 0.0; // percent; how much extra heal taken?
    public double blockChance = 0.0; // percent; chance of damage being ignored?
    public double dmgReduction = 0.0; // percent; how much damage is reduced?
    public double shieldStrength = 0.0; // percent; how strong is shield?
    public int maxPotionVal = 5; // value; max potion quantity
    
    // Utility
    public double charMaxMana = 100; // value; character's max mana value
    public double charCurrMana = 100; // value; character's current mana value
    public int manaRegenVal = 0; // value; x mana per y millisecond
    public double manaRegen = 0.0; // millisecond; x mana per y millisecond
    public double cdReduction = 0; // percent; cooldown reduction
    public double maxSpd = 10.0; // value; max speed applicable
    public double expBonus = 1.0; // percent; how much exp bonus?
    public int maxDashVal = 1; // value; max
    

    public CharacterAttributes() {

    }
}
