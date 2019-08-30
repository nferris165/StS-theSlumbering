package theSlumbering.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSlumbering.cards.Stretch;
import theSlumbering.cards.Yawn;

public class DreamAction extends AbstractGameAction {
    private boolean upgraded;

    public DreamAction(boolean upgraded){
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        AbstractCard yawn = new Yawn();
        AbstractCard stretch = new Stretch();

        if(upgraded){
            yawn.upgrade();
            stretch.upgrade();
        }

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(yawn,1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(stretch,1));

        this.isDone = true;
    }
}
