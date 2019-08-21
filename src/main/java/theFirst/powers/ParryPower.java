package theFirst.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
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

public class ParryPower extends AbstractPower implements CloneablePowerInterface {
    @SuppressWarnings("WeakerAccess")
    public AbstractCreature source;

    public static final String POWER_ID = SlumberingMod.makeID("ParryPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theFirstResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theFirstResources/images/powers/placeholder_power32.png");

    public ParryPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);


        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
    }

    @Override
    public AbstractPower makeCopy() {
        return new ParryPower(owner, amount);
    }

    public void decrement(){
        if (this.amount <= 1) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
        } else {
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, ID, 1));
        }
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int i) {

        if (info.type == DamageInfo.DamageType.NORMAL && info.owner != null
                && info.owner != this.owner && this.owner.currentBlock >= info.output) {

            this.flash();
            DamageInfo parryInfo = new DamageInfo(this.owner, info.output, DamageInfo.DamageType.THORNS);
            AbstractDungeon.actionManager.addToTop(new DamageAction(info.owner, parryInfo, AbstractGameAction.AttackEffect.SLASH_HEAVY, true));

            decrement();

            return 0;
        }

        decrement();

        return i * 2;
    }
}
