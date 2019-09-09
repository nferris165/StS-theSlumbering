package theSlumbering.patches;

import basemod.BaseMod;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;
import theSlumbering.characters.TheSlumbering;
import theSlumbering.relics.SlumberingRelic;

@SpirePatch(
        clz=AbstractDungeon.class,
        method="initializeCardPools"

)
public class RelicRemovePatch {

    public static void Prefix(AbstractDungeon dungeon_instance) {
        if (AbstractDungeon.player instanceof TheSlumbering) {

            //healing
            BaseMod.removeRelic(new BloodVial());  //Ironclad
            BaseMod.removeRelic(new BurningBlood());  //Ironclad
            BaseMod.removeRelic(new BlackBlood());  //Ironclad
            BaseMod.removeRelic(new RegalPillow()); //tweak
            BaseMod.removeRelic(new MeatOnTheBone());
            BaseMod.removeRelic(new Pantograph());
            BaseMod.removeRelic(new BirdFacedUrn()); //tweak
            BaseMod.removeRelic(new EternalFeather()); //tweak
            BaseMod.removeRelic(new BloodyIdol()); //tweak
            BaseMod.removeRelic(new MealTicket()); //tweak
            BaseMod.removeRelic(new ToyOrnithopter()); //tweak

            //TODO: tweak relics
            //hp interaction
            BaseMod.removeRelic(new CentennialPuzzle());
            BaseMod.removeRelic(new RunicCube());  //Ironclad
            BaseMod.removeRelic(new SelfFormingClay());  //Ironclad
            BaseMod.removeRelic(new RedSkull()); //Ironclad    - tweak
            BaseMod.removeRelic(new BlueCandle()); // tweak
            //BaseMod.removeRelic(new MarkOfTheBloom()); //tweak

            //resting
            BaseMod.removeRelic(new DreamCatcher()); //look at
            BaseMod.removeRelic(new CoffeeDripper()); //look at

        }

        if (AbstractDungeon.player.hasRelic(SlumberingRelic.ID)) {
            //dungeon_instance.eventList.remove(ScrapOoze.ID);
        }
    }
}