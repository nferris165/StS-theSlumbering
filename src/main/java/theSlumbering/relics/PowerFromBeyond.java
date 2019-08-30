package theSlumbering.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theSlumbering.SlumberingMod;
import theSlumbering.util.TextureLoader;

import static theSlumbering.SlumberingMod.makeRelicOutlinePath;
import static theSlumbering.SlumberingMod.makeRelicPath;

public class PowerFromBeyond extends AbstractCustomRelic {

    public static final String ID = SlumberingMod.makeID("PowerFromBeyond");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("zzz.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("zzz.png"));

    private static final int LIMIT = 2;


    public PowerFromBeyond() {
        super(ID, IMG, OUTLINE, AbstractRelic.RelicTier.BOSS, LandingSound.MAGICAL);

        this.counter = -1;
        this.floatCounter = 0;
    }

    @Override
    public void atTurnStart() {
        this.counter = 0;
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

    @Override
    public void onVictory() {
        this.counter = -1;
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
    public void onCardDraw(AbstractCard drawnCard) {
        drawnCard.freeToPlayOnce = true;
    }

    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (this.counter < LIMIT && card.type != AbstractCard.CardType.CURSE) {
            ++this.counter;
            if (this.counter >= LIMIT) {
                this.flash();
            }
        }
    }

    @Override
    public boolean canPlay(AbstractCard card) {
        if (this.counter >= LIMIT && card.type != AbstractCard.CardType.CURSE) {
            card.cantUseMessage = this.DESCRIPTIONS[1] + LIMIT + this.DESCRIPTIONS[2];
            return false;
        } else {
            return true;
        }
    }
}
