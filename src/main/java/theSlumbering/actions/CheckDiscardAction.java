package theSlumbering.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CheckDiscardAction extends AbstractGameAction {

    private AbstractPlayer p;
    private AbstractMonster m;
    private int skill;
    private int attack;
    private int aCount;
    private int sCount;
    private int block;
    private int damage;
    private boolean sCheck;
    private boolean aCheck;

    public CheckDiscardAction(AbstractPlayer player, AbstractMonster m, int SkillCount, int AttackCount, int block, int damage, boolean sCheck, boolean aCheck) {

        this.p = player;
        this.m = m;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.DAMAGE;
        this.skill = SkillCount;
        this.attack = AttackCount;
        this.aCount = 0;
        this.sCount = 0;
        this.block = block;
        this.damage = damage;
        this.sCheck = sCheck;
        this.aCheck = aCheck;
    }

    @Override
    public void update() {

        for(AbstractCard c: p.discardPile.group){
            if(c.type == AbstractCard.CardType.ATTACK){
                aCount++;
            }
            else if(c.type == AbstractCard.CardType.SKILL){
                sCount++;
            }
        }

        if((sCount > skill) && sCheck){
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        }

        if((aCount > attack) && aCheck){
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage), AttackEffect.SLASH_HEAVY));
        }
        this.isDone = true;
    }
}