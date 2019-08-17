package theFirst.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import theFirst.FirstMod;

public class CountAction extends AbstractGameAction {

    private AbstractPlayer p;
    private int[] multiDamage;
    private DamageInfo.DamageType damageTypeForTurn;

    public CountAction(int[] multiDamage, DamageInfo.DamageType damageTypeForTurn) {

        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.USE;
        this.multiDamage = multiDamage;
        this.damageTypeForTurn = damageTypeForTurn;
    }

    @Override
    public void update() {
        if(p.drawPile.isEmpty()){
            this.isDone = true;
            return;
        }

        AbstractCard c;
        c = p.drawPile.getTopCard();
        while(c.type != AbstractCard.CardType.ATTACK){
            FirstMod.logger.info(c + "\n\n");
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
            p.drawPile.moveToDiscardPile(c);
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
            if(p.drawPile.isEmpty()){
                break;
            }
            c = p.drawPile.getTopCard();
        }

        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        p.drawPile.moveToHand(c, p.drawPile);

        this.isDone = true;
    }
}