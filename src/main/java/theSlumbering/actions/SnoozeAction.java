package theSlumbering.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import theSlumbering.SlumberingMod;
import theSlumbering.patches.customTags;

import java.util.ArrayList;
import java.util.Iterator;

public class SnoozeAction extends AbstractGameAction {

    private AbstractPlayer p;
    public static final String ACTION_ID = SlumberingMod.makeID("SnoozeAction");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
    private static final String[] TEXT = uiStrings.TEXT;

    private ArrayList<AbstractCard> cannotUpgrade = new ArrayList<>();

    public SnoozeAction() {

        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for(AbstractCard card: p.hand.group){
                if(card.hasTag(customTags.Snooze)){
                    cannotUpgrade.add(card);
                }
            }

            if (this.p.hand.size() == cannotUpgrade.size()) {
                this.isDone = true;
                return;
            } else if (this.p.hand.group.size() == 1) {
                AbstractCard c = this.p.hand.getTopCard();

                c.tags.add(customTags.Snooze);
                c.rawDescription = c.rawDescription + " NL theslumbering:Snooze.";
                c.initializeDescription();

                AbstractDungeon.player.hand.refreshHandLayout();
                this.isDone = true;
            } else {
                this.p.hand.group.removeAll(this.cannotUpgrade);
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false);

                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                Iterator selected = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();
                AbstractCard c;

                while(selected.hasNext()) {
                    c = (AbstractCard)selected.next();
                    c.tags.add(customTags.Snooze);
                    c.rawDescription = c.rawDescription + " NL theslumbering:Snooze.";
                    c.initializeDescription();
                    p.hand.addToTop(c);
                }

                this.returnCards();
                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            }
            this.tickDuration();
        }
    }

    private void returnCards(){
        for(AbstractCard c: cannotUpgrade){
            this.p.hand.addToTop(c);
        }
    }
}