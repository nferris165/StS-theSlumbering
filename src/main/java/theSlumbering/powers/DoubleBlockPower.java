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

import static theSlumbering.SlumberingMod.makePowerPath;

public class DoubleBlockPower extends AbstractCustomPower implements CloneablePowerInterface {
    @SuppressWarnings("WeakerAccess")

    public static final String POWER_ID = SlumberingMod.makeID("DoubleBlockPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("double_block84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("double_block32.png"));

    private int max;

    public DoubleBlockPower(final AbstractCreature owner, int max) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.max = max;

        type = PowerType.BUFF;
        isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        int block = this.owner.currentBlock;

        if( block > 0){
            block = Math.min(max, block);
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, block));
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        if(stackAmount > max){
            this.max = stackAmount;
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + max + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new DoubleBlockPower(owner, max);
    }
}
