package theSlumbering.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSlumbering.SlumberingMod;
import theSlumbering.cards.BasicAttack;
import theSlumbering.cards.DrowsyAttack;
import theSlumbering.cards.WokeAttack;
import theSlumbering.util.TextureLoader;

import static theSlumbering.SlumberingMod.makePowerPath;

public class AutoAttackPower extends AbstractPower implements CloneablePowerInterface {
    @SuppressWarnings("WeakerAccess")
    public AbstractCreature source;

    public static final String POWER_ID = SlumberingMod.makeID("AutoAttackPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("autoattack84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("autoattack32.png"));

    private final int val;
    private AbstractCard atk;

    public AutoAttackPower(final AbstractCreature owner, final AbstractCreature source, final int amount, final int value) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.val = value;
        this.atk = genAtk();

        type = PowerType.DEBUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        AbstractMonster targetMonster = AbstractDungeon.getRandomMonster();

        atk.freeToPlayOnce = true;

        if (atk.type != AbstractCard.CardType.POWER) {
            atk.purgeOnUse = true;
        }

        AbstractDungeon.actionManager.addToBottom(new QueueCardAction(atk, targetMonster));
    }

    private AbstractCard genAtk(){
        AbstractCard randAtt;
        switch(val)
        {
            case 1:
                randAtt = new BasicAttack();
                break;
            case 2:
                randAtt = new DrowsyAttack();
                break;
            case 3:
                randAtt = new WokeAttack();
                break;
            default:
                randAtt = new WokeAttack();
                break;
        }
        return randAtt;
    }

    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0] + atk.name + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new AutoAttackPower(owner, source, amount, val);
    }
}
