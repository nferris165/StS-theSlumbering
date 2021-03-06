package theSlumbering.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.RefundAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSlumbering.cards.AbstractCustomCard;

import static theSlumbering.SlumberingMod.logger;

public class ImagineAction extends AbstractGameAction {
    private AbstractCard.CardType cardType;
    private AbstractCard card;

    public ImagineAction(AbstractCard.CardType type, int cost, AbstractCard card){
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
        this.cardType = type;
        this.card = card;
    }



    @Override
    public void update() {
        if (AbstractDungeon.player.drawPile.isEmpty()) {
            this.isDone = true;
        } else {
            logger.info("got here\n\n");
            AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
            if (card.type == this.cardType) {
                AbstractDungeon.actionManager.addToBottom(new RefundAction(this.card));
                ((AbstractCustomCard) this.card).check = true;
            }

            this.isDone = true;
        }
    }
}
