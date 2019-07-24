package theFirst.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theFirst.FirstMod;
import theFirst.util.TextureLoader;

import static theFirst.FirstMod.makePowerPath;

public class DrowsyPower  extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = FirstMod.makeID("DrowsyPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    private boolean justApplied = false;

    //TODO define power
    // doesnt drop per turn?
    // inc damage taken?
    // lowers energy/draw?

    public DrowsyPower(AbstractCreature owner, int amount, boolean isSourceMonster) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        if (isSourceMonster) {
            this.justApplied = true;
        }
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        this.priority = 99;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return !this.owner.isPlayer && AbstractDungeon.player.hasRelic("Paper Crane") ? damage * 0.6F : damage * 0.75F;
        } else {
            return damage;
        }
    }

    @Override
    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
        } else {
            if (this.amount == 0) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, ID, 1));
            }

        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            if (this.owner != null && !this.owner.isPlayer && AbstractDungeon.player.hasRelic("Paper Crane")) {
                this.description = DESCRIPTIONS[0] + 40 + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
            } else {
                this.description = DESCRIPTIONS[0] + 25 + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
            }
        } else if (this.owner != null && !this.owner.isPlayer && AbstractDungeon.player.hasRelic("Paper Crane")) {
            this.description = DESCRIPTIONS[0] + 40 + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3];
        } else {
            this.description = DESCRIPTIONS[0] + 25 + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3];
        }

    }

    @Override
    public AbstractPower makeCopy() {
        return new DrowsyPower(owner, amount, justApplied);
    }
}
