package theSlumbering.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSlumbering.SlumberingMod;

import java.util.ArrayList;

public class DrawTagAction extends AbstractGameAction {

    private AbstractPlayer p;
    private int amount;
    private CardTags tag;

    public DrawTagAction(int amt, CardTags tag) {
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.amount = amt;
        this.tag = tag;


    }

    @Override
    public void update() {
        if(this.duration == Settings.ACTION_DUR_FAST){
            if(p.drawPile.isEmpty()){
                this.isDone = true;
                return;
            }

            ArrayList<AbstractCard> removeList = new ArrayList<>();

            for(AbstractCard c: p.drawPile.group){
                if(c.hasTag(this.tag)){
                   removeList.add(c);
                }
            }

            int count = 0;
            for(AbstractCard card: removeList){
                count++;
                if(count > amount){
                    this.isDone = true;
                    return;
                }
                if (this.p.hand.size() == BaseMod.MAX_HAND_SIZE) {
                    this.p.drawPile.moveToDiscardPile(card);
                    this.p.createHandIsFullDialog();
                } else{
                    card.unhover();
                    card.lighten(true);
                    card.setAngle(0.0F);
                    card.drawScale = 0.12F;
                    card.targetDrawScale = 0.75F;
                    card.current_x = CardGroup.DRAW_PILE_X;
                    card.current_y = CardGroup.DRAW_PILE_Y;
                    this.p.drawPile.removeCard(card);
                    card.triggerWhenDrawn();
                    AbstractDungeon.player.hand.addToTop(card);
                    AbstractDungeon.player.hand.refreshHandLayout();
                    AbstractDungeon.player.hand.applyPowers();
                }
            }
        }
        this.tickDuration();
    }
}