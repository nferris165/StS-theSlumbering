package theSlumbering.powers;

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
import theSlumbering.SlumberingMod;
import theSlumbering.cards.*;
import theSlumbering.patches.ActionManagerPatch;
import theSlumbering.relics.SlumberingRelic;
import theSlumbering.util.TextureLoader;

public class BagOfShieldsPower extends AbstractCustomPower implements CloneablePowerInterface, InvisiblePower {
    @SuppressWarnings("WeakerAccess")

    public static final String POWER_ID = SlumberingMod.makeID("BagOfShieldsPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theSlumberingResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theSlumberingResources/images/powers/placeholder_power32.png");

    public BagOfShieldsPower(final AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        ActionManagerPatch.hasBag.set(AbstractDungeon.actionManager, true);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        if(AbstractDungeon.player.hasRelic(SlumberingRelic.ID)){
            description = DESCRIPTIONS[0] + (AbstractDungeon.player.getRelic(SlumberingRelic.ID).counter * 2) + DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0] + 5 + DESCRIPTIONS[1];
        }
    }

    @Override
    public void onVictory() {
        ActionManagerPatch.hasBag.set(AbstractDungeon.actionManager, false);
    }

    @Override
    public AbstractPower makeCopy() {
        return new BagOfShieldsPower(owner);
    }
}
