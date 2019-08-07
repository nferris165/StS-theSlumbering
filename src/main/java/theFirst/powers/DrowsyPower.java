package theFirst.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theFirst.FirstMod;
import theFirst.util.TextureLoader;

import static theFirst.FirstMod.makeID;
import static theFirst.FirstMod.makePowerPath;

public class DrowsyPower  extends AbstractCustomPower implements CloneablePowerInterface {
    public static final String POWER_ID = FirstMod.makeID("DrowsyPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    private boolean justApplied = false;
    private int percent = 20;
    private int bonus = 10;

    public DrowsyPower(AbstractCreature owner, int amount, boolean isSourceMonster) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.val = amount;
        if (isSourceMonster) {
            this.justApplied = true;
        }
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        this.priority = 99;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        float regBuf = damage * 0.2F * this.amount + damage;
        float incBuf = damage * 0.3F * this.amount + damage;
        if (type == DamageInfo.DamageType.NORMAL) {
            return AbstractDungeon.player.hasRelic(makeID("StarMobile")) ? incBuf : regBuf;
        } else {
            return damage;
        }
    }

    private void incEnergy(int amt){
        for(int i = 0; i < amt; i++) {
            ++AbstractDungeon.player.energy.energy;
        }
    }

    private void decEnergy(int amt){
        for(int i = 0; i < amt; i++) {
            --AbstractDungeon.player.energy.energy;
        }
    }

    @Override
    public void onInitialApplication() {
        decEnergy(this.amount);

        for(int i = 0; i < this.amount; i++) {
            --AbstractDungeon.player.energy.energyMaster;
        }
    }

    @Override
    public void onRemove() {
        incEnergy(this.amount);
        for(int i = 0; i < this.val; i++) {
            ++AbstractDungeon.player.energy.energyMaster;
        }    }

    @Override
    public void onVictory() {
        for(int i = 0; i < this.val; i++) {
            ++AbstractDungeon.player.energy.energyMaster;
        }    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if(this.amount > 999){
            this.amount = 999;
        }

        incEnergy(reduceAmount);
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if(this.amount > 999){
            this.amount = 999;
        }

        decEnergy(stackAmount);
    }

    @Override
    public void updateDescription() {
        if (this.owner.isPlayer && AbstractDungeon.player.hasRelic(makeID("StarMobile"))) {
            this.description = DESCRIPTIONS[0] + ((percent + bonus) * this.amount) + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + (percent * this.amount) + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }

    }

    @Override
    public AbstractPower makeCopy() {
        return new DrowsyPower(owner, amount, justApplied);
    }
}
