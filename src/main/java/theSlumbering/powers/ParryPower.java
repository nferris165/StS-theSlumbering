package theSlumbering.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseBlockPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSlumbering.SlumberingMod;
import theSlumbering.patches.customTags;
import theSlumbering.util.TextureLoader;

import static theSlumbering.SlumberingMod.makePowerPath;

public class ParryPower extends AbstractPower implements CloneablePowerInterface, OnLoseBlockPower {
    @SuppressWarnings("WeakerAccess")
    public static final String POWER_ID = SlumberingMod.makeID("ParryPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    private boolean blocked = false;
    private AbstractCard card;

    public ParryPower(final AbstractCreature owner, final int amount, final AbstractCard card) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.card = card;

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
        renew();
    }

    @Override
    public AbstractPower makeCopy() {
        return new ParryPower(owner, amount, card);
    }

    private void decrement(){
        if (this.amount <= 1) {
            renew();
        } else {
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, ID, 1));
        }
    }

    @Override
    public int onAttacked(DamageInfo info, int i) {
        if (info.type == DamageInfo.DamageType.NORMAL && info.owner != null
                && info.owner != this.owner && !this.blocked){
            decrement();
            i *= 2;
        }
        this.blocked = false;
        return i;
    }

    private void renew(){
        if(card.hasTag(customTags.Renewable)){
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(this.card, 1));
        }
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ID));

    }

    @Override
    public int onLoseBlock(DamageInfo info, int i) {
        //SlumberingMod.logger.info(info.output + " block " + i + " " + this.owner.currentBlock + "\n\n");
        this.blocked = true;
        if (info.type == DamageInfo.DamageType.NORMAL && info.owner != null
                && info.owner != this.owner) {

            if(this.owner.currentBlock >= info.output){
                this.flash();
                DamageInfo parryInfo = new DamageInfo(this.owner, info.output, DamageInfo.DamageType.THORNS);
                AbstractDungeon.actionManager.addToTop(new DamageAction(info.owner, parryInfo, AbstractGameAction.AttackEffect.SLASH_HEAVY, true));

                decrement();
                return 0;
            }

            this.flash();
            decrement();
            return i * 2;
        }
        return i;
    }
}
