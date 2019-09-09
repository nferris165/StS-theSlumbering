package theSlumbering.potions;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.WeMeetAgain;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import theSlumbering.SlumberingMod;
import theSlumbering.relics.SlumberingRelic;

public class NewPotion extends AbstractPotion {


    public static final String POTION_ID = SlumberingMod.makeID("NewPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public NewPotion() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.HEART, PotionColor.POWER);

        potency = getPotency();
        description = DESCRIPTIONS[0];
        isThrown = false;
        tips.add(new PowerTip(name, description));

    }

    @Override
    public void use(AbstractCreature target) {

        SlumberingMod.incSlumberingRelic(potency);
    }

    //TODO use on map? ui.panels.potion
    @Override
    public boolean canUse() {
        if (AbstractDungeon.actionManager.turnHasEnded && AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) {
            //logger.info(AbstractDungeon.actionManager.turnHasEnded + "&&" + AbstractDungeon.getCurrRoom().phase);
            return false;
        } else {
            return AbstractDungeon.getCurrRoom().event == null || !(AbstractDungeon.getCurrRoom().event instanceof WeMeetAgain);
        }
    }


    @Override
    public AbstractPotion makeCopy() {
        return new NewPotion();
    }

    @Override
    public int getPotency(final int potency) {
        return 1;
    }

    public void upgradePotion()
    {
        potency += 1;
        tips.clear();
        tips.add(new PowerTip(name, description));
    }
}
