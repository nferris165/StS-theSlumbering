package theFirst.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.WindingHalls;
import theFirst.FirstMod;
import theFirst.characters.TheFirst;
import theFirst.events.BasicEvent;
import theFirst.relics.FirstRelic;

@SpirePatch(clz=AbstractDungeon.class,method="initializeCardPools")
public class RemoveEventPatch {

    public static void Prefix(AbstractDungeon dungeon_instance) {
        if (AbstractDungeon.player instanceof TheFirst) {
            //dungeon_instance.eventList.remove(GoopPuddle.ID);
            dungeon_instance.eventList.remove(WindingHalls.ID);

            /*dungeon_instance.eventList.remove(BigFish.ID);
            dungeon_instance.eventList.remove(Cleric.ID);
            dungeon_instance.eventList.remove(DeadAdventurer.ID);
            dungeon_instance.eventList.remove(LivingWall.ID);
            dungeon_instance.eventList.remove(Mushrooms.ID);
            dungeon_instance.eventList.remove(GoldenIdolEvent.ID);
            dungeon_instance.eventList.remove(GoldenWing.ID);
            dungeon_instance.eventList.remove(Sssserpent.ID);
            dungeon_instance.eventList.remove(ScrapOoze.ID);
            */


        } else {
            dungeon_instance.eventList.remove(BasicEvent.ID);

        }

        if (AbstractDungeon.player.hasRelic(FirstRelic.ID)) {
            //dungeon_instance.eventList.remove(ScrapOoze.ID);
        }

        /*
        if (AbstractDungeon.player instanceof TheFirst) {
            TheFirst sc = (TheFirst) AbstractDungeon.player;
            if (sc.foughtSlimeBoss || sc.hasRelic(StudyCardRelic.ID)) {
                dungeon_instance.eventList.remove(Hunted.ID);
            }
        } else {
            dungeon_instance.eventList.remove(Hunted.ID);
        }
        */

        if (!FirstMod.eventSharing) {
            if (!(AbstractDungeon.player instanceof TheFirst)) {
                //dungeon_instance.eventList.remove(ArtOfSlimeWar.ID);
            }
        }

        //FirstMod.logger.info("patch worked\n\n\n");

    }
}