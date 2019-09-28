package theSlumbering.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSlumbering.SlumberingMod;
import theSlumbering.cards.*;
import theSlumbering.util.TextureLoader;

import static theSlumbering.SlumberingMod.makeID;

public class AwakePower extends AbstractCustomPower implements CloneablePowerInterface {
    @SuppressWarnings("WeakerAccess")

    public static final String POWER_ID = SlumberingMod.makeID("AwakePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theSlumberingResources/images/powers/awake_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theSlumberingResources/images/powers/awake_power32.png");

    private int percent = 50;
    private int bonus = 15;
    private float percentF = ((float) percent) / 100.0f;
    private float buffF = ((float) bonus) / 100.0f;

    public AwakePower(final AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        float regBuf = damage * percentF;
        if (type == DamageInfo.DamageType.NORMAL) {
            return regBuf;
        } else {
            return damage;
        }
    }

    @Override
    public float modifyBlock(float blockAmount) {
        return (blockAmount * percentF);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + percent + DESCRIPTIONS[1] + percent + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new AwakePower(owner);
    }
}
