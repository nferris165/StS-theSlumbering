package theSlumbering.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSlumbering.SlumberingMod;
import theSlumbering.cards.FocusedStrike;

public class FocusedAction extends AbstractGameAction {

    private AbstractPlayer p;
    private AbstractCard card;
    private AbstractMonster m;

    public FocusedAction(AbstractCard card, AbstractMonster m) {
        this.p = AbstractDungeon.player;
        this.card = card;
        this.m = m;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.DAMAGE;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            //card.applyPowers();
            ((FocusedStrike) card).strikes(p, m);
        }
        this.tickDuration();
    }
}