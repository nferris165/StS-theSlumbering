package theFirst.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseBlockPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theFirst.SlumberingMod;
import theFirst.util.TextureLoader;

public class NegatePower extends AbstractPower implements CloneablePowerInterface, OnLoseBlockPower {

    @SuppressWarnings("WeakerAccess")
    public AbstractCreature source;

    public static final String POWER_ID = SlumberingMod.makeID("NegatePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theFirstResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theFirstResources/images/powers/placeholder_power32.png");

    public NegatePower(final AbstractCreature owner, final int amount, boolean turnbased) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = turnbased;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);


        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (this.amount <= 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damage) {
        if (damage > 0) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }

        return 0;
    }

    @Override
    public AbstractPower makeCopy() {
        return new NegatePower(owner, amount, isTurnBased);
    }

    @Override
    public int onLoseBlock(DamageInfo damageInfo, int damage) {
        if (damage > 0) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }

        return 0;
    }
}
