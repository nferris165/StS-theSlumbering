package theFirst.relics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnSmithRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import theFirst.FirstMod;
import theFirst.util.TextureLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import static theFirst.FirstMod.makeRelicOutlinePath;
import static theFirst.FirstMod.makeRelicPath;

public class EnchantedHammer extends AbstractCustomRelic implements BetterOnSmithRelic {

    public static final String ID = FirstMod.makeID("EnchantedHammer");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("EnchantedHammer.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("EnchantedHammer.png"));


    public EnchantedHammer() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);

        this.counter = -1;
        this.floatCounter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];

    }

    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c) {

        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public void betterOnSmith(AbstractCard abstractCard) {
        if (AbstractDungeon.player.masterDeck.hasUpgradableCards()){
            ArrayList<AbstractCard> upgradableCards = new ArrayList<>();
            Iterator card = AbstractDungeon.player.masterDeck.group.iterator();

            while(card.hasNext()) {
                AbstractCard c = (AbstractCard)card.next();
                if (c.canUpgrade()) {
                    upgradableCards.add(c);
                }
            }

            Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));

            if(!upgradableCards.isEmpty()){
                flash();
                ((AbstractCard)upgradableCards.get(0)).upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)upgradableCards.get(0));
                AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(((AbstractCard)upgradableCards.get(0)).makeStatEquivalentCopy(),
                        (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH + 60.0F * Settings.scale,(float)Settings.HEIGHT / 2.0F));
                AbstractDungeon.topLevelEffectsQueue.add(new UpgradeShineEffect(
                        (float) Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH + 20.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
            }


        }
    }
}
