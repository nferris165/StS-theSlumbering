package theFirst.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theFirst.SlumberingMod;
import theFirst.cards.*;
import theFirst.util.TextureLoader;

public class WakingPower extends AbstractCustomPower implements CloneablePowerInterface {
    @SuppressWarnings("WeakerAccess")
    public AbstractCreature source;
    public AbstractCard card;
    public static boolean needYawn;

    public static final String POWER_ID = SlumberingMod.makeID("WakingPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theFirstResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theFirstResources/images/powers/placeholder_power32.png");

    public WakingPower(final AbstractCreature owner, AbstractCard card) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.card = card;

        type = PowerType.BUFF;
        isTurnBased = false;

        needYawn = this.card.cardID.equals(new Stretch().cardID);

        this.powerCheck = needYawn;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);


        updateDescription();
    }


    @Override
    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
    }

    @Override
    public void updateDescription() {
        if(needYawn){
            description = DESCRIPTIONS[0];
        }
        else {
            description = DESCRIPTIONS[1];
        }

    }


    @Override
    public AbstractPower makeCopy() {
        return new WakingPower(owner, card);
    }

    @Override
    public void onSpecificTrigger() {
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
        SlumberingMod.incSlumberingRelic(1);

    }

}
