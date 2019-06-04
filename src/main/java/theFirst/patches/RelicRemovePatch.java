package theFirst.patches;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;
import theFirst.characters.TheFirst;
import theFirst.relics.FirstRelic;

@SpirePatch(clz=AbstractDungeon.class,method="initializeCardPools")
public class RelicRemovePatch {

    public static void Prefix(AbstractDungeon dungeon_instance) {
        if (AbstractDungeon.player instanceof TheFirst) {
            BaseMod.removeRelic(new Strawberry());
            BaseMod.removeRelic(new Pear());
            BaseMod.removeRelic(new Mango());
            BaseMod.removeRelic(new Waffle());
            //BaseMod.removeRelic(new MarkOfTheBloom());
            //BaseMod.removeRelic(new FaceOfCleric());

        }

        if (AbstractDungeon.player.hasRelic(FirstRelic.ID)) {
            //dungeon_instance.eventList.remove(ScrapOoze.ID);
        }

        //FirstMod.logger.info("patch worked\n\n\n");

    }
}