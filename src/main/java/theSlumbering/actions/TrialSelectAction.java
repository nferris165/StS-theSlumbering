package theSlumbering.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSlumbering.powers.TrialofHypnosPower;

public class TrialSelectAction extends AbstractGameAction {

    private AbstractPlayer p;
    private int gold;
    private boolean upgraded;

    public TrialSelectAction(AbstractPlayer p, int price, boolean upgraded) {

        this.p = p;
        this.gold = price;
        this.upgraded = upgraded;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        AbstractDungeon.player.loseGold(this.gold);
        int rng;

        float roll = AbstractDungeon.miscRng.random(0.0f, 100.0f);
        if(roll <= 20.0f){
            rng = 0;
        }
        else if(roll <= 50.0f){
            rng = 1;
        }
        else if(roll <= 80.0f){
            rng = 2;
        }
        else{
            rng = 3;
        }
        //rng = 0;

        if(!p.hasPower(TrialofHypnosPower.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TrialofHypnosPower(p, rng, gold, upgraded)));
        }
        this.isDone = true;
    }
}