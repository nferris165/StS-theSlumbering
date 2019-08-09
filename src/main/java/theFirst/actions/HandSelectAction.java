package theFirst.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import theFirst.FirstMod;

import java.util.ArrayList;
import java.util.Iterator;

public class HandSelectAction extends AbstractGameAction {
    private AbstractPlayer p;
    public static final String ACTION_ID = FirstMod.makeID("HandSelectAction");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
    private static final String[] TEXT = uiStrings.TEXT;

    public HandSelectAction(){
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    private void draw(AbstractCard card){

        Iterator deck = AbstractDungeon.player.masterDeck.group.iterator();
        AbstractCard c;
        ArrayList<AbstractCard> cardsToMove = new ArrayList();

        while(deck.hasNext()) {
            c = (AbstractCard)deck.next();
            if (c.cardID.equals(card.cardID)) {
                cardsToMove.add(c);
            }
        }

        for (AbstractCard d: cardsToMove) {
            if (this.p.hand.size() == 10) {
                this.p.drawPile.moveToDiscardPile(d);
                this.p.createHandIsFullDialog();
            } else {
                //this.p.drawPile.moveToHand(d, this.p.drawPile);
            }
        }


    }

    @Override
    public void update(){
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            } else if (this.p.hand.size() == 1) {
                AbstractCard c = this.p.hand.getTopCard();

                draw(c);

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
                    draw(c);
                }

                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            this.tickDuration();
        }
    }
}
