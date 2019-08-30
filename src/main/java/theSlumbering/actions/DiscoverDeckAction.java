package theSlumbering.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import theSlumbering.SlumberingMod;
import theSlumbering.patches.customTypes;

public class DiscoverDeckAction extends AbstractGameAction {

    private AbstractPlayer p;
    private boolean retrieveCard = false;
    private boolean draw;

    public static final String ACTION_ID = SlumberingMod.makeID("DiscoverDeckAction");

    public DiscoverDeckAction() {
        this(true);
    }

    public DiscoverDeckAction(boolean draw) {
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.draw = draw;
    }

    private void draw(){
        AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard;
        //disCard.current_x = -1000.0F * Settings.scale;
        if (this.p.hand.size() < BaseMod.MAX_HAND_SIZE) {
            p.drawPile.moveToHand(disCard, AbstractDungeon.player.drawPile);
        } else {
            p.drawPile.moveToDiscardPile(disCard);
            this.p.createHandIsFullDialog();
        }
    }

    private void copy(){
        AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
        disCard.current_x = -1000.0F * Settings.scale;
        if (p.hand.size() < BaseMod.MAX_HAND_SIZE) {
            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        } else {
            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        }
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.discoveryOpen(customTypes.PASSIVE);
            this.tickDuration();
        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    if(this.draw){
                        draw();
                    }
                    else{
                        copy();
                    }

                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }

                this.retrieveCard = true;
            }

            this.tickDuration();
        }
    }
}