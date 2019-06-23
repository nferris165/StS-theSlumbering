package theFirst.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theFirst.FirstMod;

import theFirst.util.TextureLoader;

public class ReflectPower extends AbstractPower implements CloneablePowerInterface {
    @SuppressWarnings("WeakerAccess")
    public AbstractCreature source;

    public static final String POWER_ID = FirstMod.makeID("ReflectPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theFirstResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theFirstResources/images/powers/placeholder_power32.png");

    private DamageInfo reflectInfo;

    public ReflectPower(final AbstractCreature owner, final AbstractCreature source, final int percent) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = percent;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);


        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {

        //info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS
        if (info.type == DamageInfo.DamageType.NORMAL && info.owner != null && info.owner != this.owner) {
            this.flash();
            int percentDamage = (int) (((float) this.amount / 100) * (float) damageAmount);

            //FirstMod.logger.info(percentDamage + " " + this.amount + " " + damageAmount + "\n\n");

            this.reflectInfo = new DamageInfo(this.owner, percentDamage, DamageInfo.DamageType.THORNS);
            AbstractDungeon.actionManager.addToTop(new DamageAction(info.owner, this.reflectInfo, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, true));
        }

        return damageAmount;
    }

    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, ID));
    }

    @Override
    public AbstractPower makeCopy() {
        return new ReflectPower(owner, source, amount);
    }
}
