package Code;
import java.util.Hashtable;
import java.util.Map;

public class CharacterAttributes {
    // Core Stats
    public int level = 0; // value; the level of the character
    public double exp = 0.0; // value; how much experience does the character have?
    public double maxExp = 100.0; // value; how much experience does the character need to level up?
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
    public Map<String, Double> elementalDamage = new Hashtable<>(); // collection of all elemental damages
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
    public double manaRegenVal = 0.0; // value; x mana per 5000 millisecond
    public int manaRegenTime = 5000; // value; 5000 millisecond for each regen
    public double cdReduction = 0; // percent; cooldown reduction
    public double maxSpd = 10.0; // value; max speed applicable
    public double expBonus = 1.0; // percent; how much exp bonus?
    public int maxDashVal = 1; // value; max
    
    // Boolean
    public boolean isDead = false;
    public boolean isInSafeZone = true;

    public CharacterAttributes() {
    
    }

    public void updateValues() {

        calculate();

        // Add elemental damage values into Map
        elementalDamage.put("fireDmg", fireDmg);
        elementalDamage.put("lightningDmg", lightningDmg);
        elementalDamage.put("coldDmg", coldDmg);
        elementalDamage.put("poisonDmg", poisonDmg);
        elementalDamage.put("shadowDmg", shadowDmg);

        // every value in strength is a +0.01% to damage
        damage = damage * (1.0 + (strength * 0.0001));
        // every value in intelligence is a +0.01% to all elemental damage
        for (Map.Entry<String, Double> entry : elementalDamage.entrySet()) {
            double updatedValue = entry.getValue() * (1.0 + (intelligence * 0.0001));
            entry.setValue(updatedValue);
        }
        // every value in willpower is 0.01% of resource regen per 5000ms
        manaRegenVal = manaRegenVal * (1.0 * (willpower * 0.0001));
        // every value in dexterity is 0.02% added to critical strike chance
        critChance = critChance * (1.0 * (dexterity * 0.0002));
    }

    private void calculate() {

        // when exp fills maxExp, upgrade level, reset exp to exp-maxExp, and reset maxExp to a higher value
         if (exp >= maxExp) {
            level += 1;
            exp -= maxExp;
            maxExp *= 1.1;
         }
         // when character's current health value hits 0, tick on isDead
         if (charCurrHP <= 0) {
            isDead = true;
         }
         
    }
}
