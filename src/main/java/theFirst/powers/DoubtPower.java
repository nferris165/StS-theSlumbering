package theFirst.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theFirst.FirstMod;
import theFirst.cards.*;
import theFirst.characters.TheFirst;
import theFirst.util.TextureLoader;

public class DoubtPower extends AbstractCustomPower implements CloneablePowerInterface, InvisiblePower {
    @SuppressWarnings("WeakerAccess")

    private int val;

    public static final String POWER_ID = FirstMod.makeID("DoubtPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theFirstResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theFirstResources/images/powers/placeholder_power32.png");

    public DoubtPower(final AbstractCreature owner, int val) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.val = val;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.val + DESCRIPTIONS[1];
    }

    @Override
    public void onVictory() {
        if(AbstractDungeon.player.chosenClass == TheFirst.Enums.THE_FIRST){
            AbstractDungeon.player.decreaseMaxHealth(10);

        }else{
            AbstractDungeon.player.decreaseMaxHealth(this.val);
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new DoubtPower(owner, val);
    }
}
