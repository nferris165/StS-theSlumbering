package theSlumbering.relics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import theSlumbering.SlumberingMod;
import theSlumbering.util.TextureLoader;

import java.util.*;

import static theSlumbering.SlumberingMod.*;

public class CursedAnvil extends AbstractCustomRelic {

    public static final String ID = SlumberingMod.makeID("CursedAnvil");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("CursedAnvil2.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("CursedAnvil.png"));

    private static final int cardsDowngraded = 5;


    public CursedAnvil() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);

        this.counter = -1;
        this.floatCounter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + cardsDowngraded + DESCRIPTIONS[1];

    }

    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c) {

        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public void onEquip()
    {
        flash();
        //upgrade deck
        int effectCount = 0;

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.canUpgrade()) {
                ++effectCount;
                c.upgrade();
                if (effectCount <= 20) {
                    float x = MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH;
                    float y = MathUtils.random(0.2F, 0.8F) * (float) Settings.HEIGHT;
                    AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), x, y));
                    AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(x, y));
                }
            }
        }

        //downgrade
        ArrayList<AbstractCard> upgradedCards = new ArrayList<>();

        for (AbstractCard c2 : AbstractDungeon.player.masterDeck.group) {
            if (c2.upgraded) {
                upgradedCards.add(c2);
            }
        }

        int size = upgradedCards.size();
        Collections.shuffle(upgradedCards, new Random(AbstractDungeon.miscRng.randomLong()));

        if(size != 0) {
            if (size > cardsDowngraded) {
                size = 5;
            }


            //TODO waiting

            for(int ranCard = 0; ranCard < size; ranCard++){
                //logger.info("waiting?\n");
                //AbstractRoom.waitTimer = 2.0F;
                //AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect((AbstractCard)upgradedCards.get(ranCard).makeCopy()));
                //AbstractDungeon.actionManager.addToBottom(new VFXAction(new ShowCardBrieflyEffect((AbstractCard)upgradedCards.get(ranCard).makeCopy()),1.0F)); //only exists during combat
                AbstractDungeon.player.masterDeck.removeCard((AbstractCard)upgradedCards.get(ranCard));
                AbstractDungeon.player.masterDeck.addToRandomSpot((AbstractCard)upgradedCards.get(ranCard).makeCopy());
            }
        }
    }
}
