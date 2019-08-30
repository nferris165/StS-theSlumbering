package theSlumbering.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSlumbering.SlumberingMod;
import theSlumbering.util.TextureLoader;

public class SideSleeperPower extends AbstractCustomPower implements CloneablePowerInterface {
    @SuppressWarnings("WeakerAccess")

    private int reduce;

    public static final String POWER_ID = SlumberingMod.makeID("SideSleeperPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theFirstResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theFirstResources/images/powers/placeholder_power32.png");

    public SideSleeperPower(final AbstractCreature owner, int cost) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = -1;
        this.reduce = cost;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    private void reduceCard(){
        int count = AbstractDungeon.player.hand.size();

        if(count > 0) {
            AbstractCard card = AbstractDungeon.player.hand.getBottomCard();
            card.setCostForTurn(this.reduce);
        }
    }

    @Override
    public void onInitialApplication() {
        reduceCard();
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        reduceCard();
    }

    @Override
    public void onDrawOrDiscard() {
        reduceCard();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.reduce + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SideSleeperPower(owner, reduce);
    }
}
