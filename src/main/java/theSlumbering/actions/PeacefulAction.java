package theSlumbering.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PeacefulAction extends AbstractGameAction {

    private AbstractPlayer p;
    private AbstractMonster m;
    private float val;

    public PeacefulAction(AbstractMonster m, int percent) {

        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.BLOCK;

        this.m = m;
        this.val = percent / 100.0f;
    }

    @Override
    public void update() {
        if (this.m != null && this.m.currentHealth < this.m.maxHealth) {
            this.val *= this.m.maxHealth;
            this.val = Math.min(this.val, this.m.maxHealth - this.m.currentHealth);
            this.m.heal(Math.round(this.val), true);

            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, Math.round(this.val)));
            if(m.type != AbstractMonster.EnemyType.ELITE && m.type != AbstractMonster.EnemyType.BOSS){
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, Math.round(this.val)));
            }
        }

        this.isDone = true;
    }
}