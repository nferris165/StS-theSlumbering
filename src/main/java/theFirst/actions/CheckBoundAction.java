package theFirst.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theFirst.cards.BindingBlow;
import theFirst.powers.BoundPower;

public class CheckBoundAction extends AbstractGameAction {

    private AbstractPlayer p;
    private AbstractCard card;

    public CheckBoundAction(AbstractCard card) {

        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;

        this.card = card;
    }

    @Override
    public void update() {
        if(!p.hasPower(BoundPower.POWER_ID)){
            BindingBlow.multipleBound = false;
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new BoundPower(p, this.card)));
        }
        else {
            BindingBlow.multipleBound = true;
        }
        this.isDone = true;
    }
}