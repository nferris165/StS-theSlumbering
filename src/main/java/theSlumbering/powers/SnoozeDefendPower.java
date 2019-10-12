package theSlumbering.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSlumbering.SlumberingMod;
import theSlumbering.util.TextureLoader;

public class SnoozeDefendPower extends AbstractCustomPower implements CloneablePowerInterface {
    @SuppressWarnings("WeakerAccess")

    public static final String POWER_ID = SlumberingMod.makeID("SnoozeDefendPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theSlumberingResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theSlumberingResources/images/powers/placeholder_power32.png");

    public SnoozeDefendPower(final AbstractCreature owner, int amt) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.val = amt;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + val + DESCRIPTIONS[1];
    }

    @Override
    public void stackPower(int stackAmount) {
        val += stackAmount;
        updateDescription();
    }

    @Override
    public void onSnooze() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, val));
    }

    @Override
    public AbstractPower makeCopy() {
        return new SnoozeDefendPower(owner, val);
    }
}
