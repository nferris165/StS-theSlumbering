package theSlumbering.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import theSlumbering.SlumberingMod;

import java.util.ArrayList;
import java.util.Iterator;

public class PurgeAction extends AbstractGameAction {

    private AbstractPlayer p;
    private AbstractCard card;
    private boolean purgeSource;

    public static final String ACTION_ID = SlumberingMod.makeID("PurgeAction");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
    private static final String[] TEXT = uiStrings.TEXT;

    public PurgeAction(AbstractCard c, boolean purgeSource) {

        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.card = c;
        this.purgeSource = purgeSource;
    }

    public PurgeAction(){
        this(null, false);
    }

    private void remove(AbstractCard card, CardGroup pile){

        Iterator deck = pile.group.iterator();
        AbstractCard c;
        ArrayList<AbstractCard> cardsToMove = new ArrayList<>();

        if(this.purgeSource){
            cardsToMove.add(this.card);
        }

        while(deck.hasNext()) {
            c = (AbstractCard)deck.next();
            if (c.uuid.equals(card.uuid)) {
                cardsToMove.add(c);
                break;
            }
        }

        for (AbstractCard d: cardsToMove) {
            if(pile.contains(d)){
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(d));
                pile.removeCard(d);
            }
        }
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            } else if (this.p.hand.size() == 1) {
                AbstractCard c = this.p.hand.getTopCard();

                p.hand.removeCard(c);
                remove(c, this.p.masterDeck);

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
                    remove(c, this.p.masterDeck);
                }

                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            this.tickDuration();
        }
    }
}