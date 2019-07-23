package theFirst.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theFirst.cards.Stretch;
import theFirst.cards.Yawn;

public class DreamAction extends AbstractGameAction {

    public DreamAction(){

    }

    @Override
    public void update() {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Yawn(),1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Stretch(),1));

        this.isDone = true;
    }
}
