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
import theFirst.util.TextureLoader;

public class BoundPower extends AbstractCustomPower implements CloneablePowerInterface {
    @SuppressWarnings("WeakerAccess")

    public static final String POWER_ID = SlumberingMod.makeID("BoundPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theFirstResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theFirstResources/images/powers/placeholder_power32.png");

    private AbstractCard card;

    public BoundPower(final AbstractCreature owner, AbstractCard card) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.card = card;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public void onInitialApplication() {
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if(c.isGlowing && !c.cardID.equals(card.cardID)){
                c.stopGlowing();
            }
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public AbstractPower makeCopy() {
        return new BoundPower(owner, card);
    }
}
