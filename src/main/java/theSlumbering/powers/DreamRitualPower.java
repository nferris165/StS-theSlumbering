package theSlumbering.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theSlumbering.SlumberingMod;
import theSlumbering.cards.*;
import theSlumbering.monsters.NewCultist;
import theSlumbering.util.TextureLoader;

public class DreamRitualPower extends AbstractCustomPower implements CloneablePowerInterface {
    @SuppressWarnings("WeakerAccess")

    public static final String POWER_ID = SlumberingMod.makeID("DreamRitualPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theSlumberingResources/images/powers/dream_ritual84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theSlumberingResources/images/powers/dream_ritual32.png");

    private boolean skipFirst = true;
    private int dexAmt = 2;

    public DreamRitualPower(final AbstractCreature owner, int strAmt) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = strAmt;
        //this.dexAmt = dexAmt * 2;

        type = PowerType.BUFF;
        isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atEndOfRound() {
        if (!this.skipFirst) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new DexterityPower(owner, -dexAmt), -dexAmt));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));
            if(owner instanceof NewCultist){
                ((NewCultist) owner).attackBlockBlock -= dexAmt;
            }
        } else {
            this.skipFirst = false;
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + dexAmt + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new DreamRitualPower(owner, amount);
    }
}
