package theSlumbering.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.UUID;

public class EntombedAction extends AbstractGameAction {

    private AbstractPlayer p;
    private int miscIncrease;
    private UUID uuid;
    private int limit;

    public EntombedAction(UUID targetUUID, int miscIncrease, int limit) {

        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.miscIncrease = miscIncrease;
        this.uuid = targetUUID;
        this.limit = limit;
    }

    @Override
    public void update() {
        AbstractCard deckCard = null;
        for(AbstractCard card: AbstractDungeon.player.masterDeck.group){
            if (card.uuid.equals(this.uuid)) {
                deckCard = card;
                card.misc += this.miscIncrease;
                card.applyPowers();
                card.baseMagicNumber = this.limit - card.misc;
                card.isMagicNumberModified = false;
                break;
            }
        }

        for(AbstractCard battleCard: GetAllInBattleInstances.get(this.uuid)){
            battleCard.misc += this.miscIncrease;
            battleCard.applyPowers();
            battleCard.baseMagicNumber = this.limit - battleCard.misc;
            if(battleCard.misc == this.limit){
                battleCard.upgrade();
                battleCard.superFlash();
                deckCard.upgrade();
                AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(deckCard.makeStatEquivalentCopy()));
            }
        }
        this.isDone = true;
    }
}