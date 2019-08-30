package theSlumbering.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.Iterator;
import java.util.UUID;

public class SeedAction extends AbstractGameAction {
    private int miscIncrease;
    private UUID uuid;

    public SeedAction(UUID targetUUID, int miscIncrease)
    {
        this.miscIncrease = miscIncrease;
        this.uuid = targetUUID;
    }

    @Override
    public void update() {
        Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();

        AbstractCard c = null;
        AbstractCard d = null;
        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (c.uuid.equals(this.uuid)) {
                d = c;
                c.misc += this.miscIncrease;
                c.applyPowers();
                c.baseMagicNumber = c.misc;
                c.isMagicNumberModified = false;
            }
        }

        for(var1 = GetAllInBattleInstances.get(this.uuid).iterator(); var1.hasNext(); c.baseMagicNumber = c.misc) {
            c = (AbstractCard)var1.next();
            c.misc += this.miscIncrease;
            c.applyPowers();
            if(c.misc == 5){
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c,
                        (float) Settings.WIDTH / 2.0F - 30.0F * Settings.scale - AbstractCard.IMG_WIDTH / 2.0F,(float) Settings.HEIGHT / 2.0F));
                AbstractDungeon.player.increaseMaxHp(100,true);
                AbstractDungeon.player.masterDeck.removeCard(d);
            }
        }

        this.isDone = true;
    }
}
