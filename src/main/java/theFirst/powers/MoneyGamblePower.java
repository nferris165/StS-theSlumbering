package theFirst.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theFirst.FirstMod;
import theFirst.util.TextureLoader;

public class MoneyGamblePower extends AbstractCustomPower implements CloneablePowerInterface {
    @SuppressWarnings("WeakerAccess")

    public static final String POWER_ID = FirstMod.makeID("MoneyGamblePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theFirstResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theFirstResources/images/powers/placeholder_power32.png");

    private int gold;

    public MoneyGamblePower(final AbstractCreature owner, int gold) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.gold = gold;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        AbstractDungeon.player.loseGold(this.gold);
    }

    @Override
    public void onVictory() {
        float roll = AbstractDungeon.miscRng.random(0.0f, 100.0f);
        FirstMod.logger.info(roll + "\n\n");

        if(roll <= 5.0f){
            AbstractDungeon.player.gainGold(this.gold * 10);
        }
        else if(roll <= 90.0f){
            AbstractDungeon.player.gainGold(this.gold);
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + gold + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new MoneyGamblePower(owner, gold);
    }
}
