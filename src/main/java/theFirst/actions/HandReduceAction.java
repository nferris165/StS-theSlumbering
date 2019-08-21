package theFirst.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import theFirst.SlumberingMod;

import java.util.Iterator;

public class HandReduceAction extends AbstractGameAction{

    private AbstractPlayer p;
    private int redVal;
    public static final String ACTION_ID = SlumberingMod.makeID("HandReduceAction");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
    private static final String[] TEXT = uiStrings.TEXT;

    public HandReduceAction(int amount){
        this.p = AbstractDungeon.player;
        this.redVal = -amount;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    }

    private void reduce(AbstractCard card) {
        card.modifyCostForTurn(this.redVal);
        this.p.drawPile.moveToHand(card, this.p.hand);
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            } else if (this.p.hand.size() == 1) {
                AbstractCard c = this.p.hand.getTopCard();

                reduce(c);

                AbstractDungeon.player.hand.refreshHandLayout();
                this.isDone = true;
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false);

                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                Iterator selected = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();
                AbstractCard c;

                while(selected.hasNext()) {
                    c = (AbstractCard)selected.next();
                    reduce(c);
                }

                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            this.tickDuration();
        }
    }
}
