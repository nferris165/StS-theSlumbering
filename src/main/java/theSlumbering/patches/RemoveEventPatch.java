package theSlumbering.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.WindingHalls;
import com.megacrit.cardcrawl.events.city.CursedTome;
import com.megacrit.cardcrawl.events.city.Nest;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;
import theSlumbering.events.JustInTime;
import theSlumbering.events.MysteriousOrb;
import theSlumbering.events.PoweredUp;
import theSlumbering.relics.SlumberingRelic;

@SpirePatch(
        clz=AbstractDungeon.class,
        method="initializeCardPools"
)
public class RemoveEventPatch {

    public static void Prefix(AbstractDungeon dungeon_instance) {
        if (AbstractDungeon.player instanceof TheSlumbering) {

            dungeon_instance.eventList.remove(CursedTome.ID); // damage hardcoded

        } else {
            dungeon_instance.eventList.remove(MysteriousOrb.ID);

        }

        if (!SlumberingMod.eventSharing) {
            if (!(AbstractDungeon.player instanceof TheSlumbering)) {
                dungeon_instance.eventList.remove(PoweredUp.ID);
                dungeon_instance.eventList.remove(JustInTime.ID);
            }
        }
    }
}