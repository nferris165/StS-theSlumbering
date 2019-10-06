package theSlumbering.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import theSlumbering.SlumberingMod;
import theSlumbering.patches.ActionManagerPatch;

import java.util.ArrayList;

public class SnoozeAction extends AbstractGameAction {

    private AbstractPlayer p;
    private ArrayList<AbstractCard> list = new ArrayList<>();
    private CardGroup group;

    public SnoozeAction(CardGroup cardGroup, AbstractCard specificCard) {
        this.group = cardGroup;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;

        list.add(specificCard);
    }

    public SnoozeAction(CardGroup cardGroup) {
        this.group = cardGroup;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;

        list.addAll(cardGroup.group);
    }

    @Override
    public void update() {
        if(this.duration == Settings.ACTION_DUR_FAST){
            ArrayList<AbstractCard> removeList = new ArrayList<>();
            for(AbstractCard c: list){
                p.drawPile.addToRandomSpot(c.makeSameInstanceOf());
                group.removeCard(c);

                int old = ActionManagerPatch.snoozeCount.get(AbstractDungeon.actionManager);
                ActionManagerPatch.snoozeCount.set(AbstractDungeon.actionManager, old + 1);
            }
            this.isDone = true;
        }
        this.tickDuration();
    }
}