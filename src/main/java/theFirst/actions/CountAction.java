package theFirst.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

public class CountAction extends AbstractGameAction {

    private AbstractPlayer p;
    private int[] multiDamage;
    private DamageInfo.DamageType damageTypeForTurn;
    private boolean damageDealt;
    private AbstractCard.CardType type;

    public CountAction(int[] multiDamage, DamageInfo.DamageType damageTypeForTurn, AbstractCard.CardType type) {

        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.USE;
        this.multiDamage = multiDamage;
        this.damageTypeForTurn = damageTypeForTurn;
        this.damageDealt = true;
        this.type = type;
    }

    public CountAction(int[] multiDamage, DamageInfo.DamageType damageTypeForTurn, AbstractCard.CardType type, boolean damageDealt){
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.USE;
        this.multiDamage = multiDamage;
        this.damageTypeForTurn = damageTypeForTurn;
        this.damageDealt = damageDealt;
        this.type = type;
    }

    public CountAction(boolean damageDealt, AbstractCard.CardType type){
        this(null, null, type, damageDealt);
    }

    @Override
    public void update() {
        if(p.drawPile.isEmpty()){
            this.isDone = true;
            return;
        }

        AbstractCard c;
        c = p.drawPile.getTopCard();
        while(c.type != this.type && p.drawPile.size() > 1){
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
            p.drawPile.moveToDiscardPile(c);
            if(this.damageDealt){
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
            }
            c = p.drawPile.getTopCard();
        }

        if(this.damageDealt){
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        }
        p.drawPile.moveToHand(c, p.drawPile);
        p.hand.refreshHandLayout();

        this.isDone = true;
    }
}