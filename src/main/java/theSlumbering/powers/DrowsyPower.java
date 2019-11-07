package theSlumbering.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSlumbering.SlumberingMod;
import theSlumbering.util.TextureLoader;

import static theSlumbering.SlumberingMod.makeID;
import static theSlumbering.SlumberingMod.makePowerPath;

public class DrowsyPower  extends AbstractCustomPower implements CloneablePowerInterface {
    public static final String POWER_ID = SlumberingMod.makeID("DrowsyPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("drowsy_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("drowsy_power32.png"));

    private boolean justApplied = false;
    private int percent = 25;
    private int bonus = 15;
    private float percentF = ((float) percent) / 100.0f;
    private float buffF = ((float) bonus) / 100.0f;

    public DrowsyPower(AbstractCreature owner, int amount, boolean isSourceMonster) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.val = amount;
        if (isSourceMonster) {
            this.justApplied = true;
        }
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.priority = 99;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        float regBuf = damage * percentF * this.amount + damage;
        float incBuf = damage * (percentF + buffF) * this.amount + damage;
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
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(
                AbstractDungeon.player, AbstractDungeon.player, AwakePower.POWER_ID));
    }

    @Override
    public void onRemove() {
        incEnergy(this.amount);
        for(int i = 0; i < this.val; i++) {
            ++AbstractDungeon.player.energy.energyMaster;
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,
                AbstractDungeon.player, new AwakePower(AbstractDungeon.player)));
    }

    @Override
    public void onVictory() {
        for(int i = 0; i < this.val; i++) {
            ++AbstractDungeon.player.energy.energyMaster;
        }
    }

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
        if(stackAmount < 0){
            if(-stackAmount > this.amount){
                stackAmount = -this.amount;
            }
            reducePower(-stackAmount);
            return;
        }
        super.stackPower(stackAmount);
        if(this.amount > 999){
            this.amount = 999;
        }

        decEnergy(stackAmount);
    }

    @Override
    public void updateDescription() {
        if (this.owner.isPlayer && AbstractDungeon.player.hasRelic(makeID("StarMobile"))) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + ((percent + bonus) * this.amount) + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + (percent * this.amount) + DESCRIPTIONS[2];
        }

    }

    @Override
    public AbstractPower makeCopy() {
        return new DrowsyPower(owner, amount, justApplied);
    }
}
