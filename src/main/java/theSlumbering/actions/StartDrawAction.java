package theSlumbering.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSlumbering.SlumberingMod;
import theSlumbering.cards.AbstractCustomCard;
import theSlumbering.patches.customTags;

public class StartDrawAction extends AbstractGameAction {

    private AbstractPlayer p;

    public StartDrawAction() {
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            SlumberingMod.logger.info(AbstractDungeon.player.hand.group + "\n\n");
            for(AbstractCard c: AbstractDungeon.player.hand.group){
                if(c.hasTag(customTags.StartDraw)){
                    ((AbstractCustomCard)c).passiveEffect();
                }
            }
        }
        this.tickDuration();
    }
}