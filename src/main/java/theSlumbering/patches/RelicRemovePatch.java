package theSlumbering.patches;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;
import theSlumbering.characters.TheSlumbering;
import theSlumbering.relics.SlumberingRelic;

@SpirePatch(clz=AbstractDungeon.class,method="initializeCardPools")
public class RelicRemovePatch {

    public static void Prefix(AbstractDungeon dungeon_instance) {
        if (AbstractDungeon.player instanceof TheSlumbering) {
            BaseMod.removeRelic(new Strawberry());
            BaseMod.removeRelic(new Pear());
            BaseMod.removeRelic(new Mango());
            BaseMod.removeRelic(new Waffle());
            //BaseMod.removeRelic(new MarkOfTheBloom());
            //BaseMod.removeRelic(new FaceOfCleric());

        }

        if (AbstractDungeon.player.hasRelic(SlumberingRelic.ID)) {
            //dungeon_instance.eventList.remove(ScrapOoze.ID);
        }

        //SlumberingMod.logger.info("patch worked\n\n\n");

    }
}