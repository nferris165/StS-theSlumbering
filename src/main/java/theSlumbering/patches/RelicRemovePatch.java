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
            BaseMod.removeRelic(new BurningBlood());  //Ironclad
            BaseMod.removeRelic(new BlackBlood());  //Ironclad
            BaseMod.removeRelic(new MagicFlower());  //Ironclad

            BaseMod.removeRelic(new BloodVial());  //better out
            //BaseMod.removeRelic(new MeatOnTheBone()); //better out
            BaseMod.removeRelic(new Pantograph());  //better out
            BaseMod.removeRelic(new BirdFacedUrn()); //better out
            BaseMod.removeRelic(new EternalFeather()); //better out
            BaseMod.removeRelic(new MealTicket()); //better out
            BaseMod.removeRelic(new ToyOrnithopter()); //better out

            //hp interaction
            BaseMod.removeRelic(new RunicCube());  //Ironclad
            BaseMod.removeRelic(new SelfFormingClay());  //Ironclad
            BaseMod.removeRelic(new RedSkull()); //Ironclad

            BaseMod.removeRelic(new CentennialPuzzle()); // better out

            // special
            //BaseMod.removeRelic(new LizardTail()); //already has proper error checking
            //BaseMod.removeRelic(new SingingBowl()); //text adjusted
            //BaseMod.removeRelic(new TinyHouse());  //HP value adjusted
            //BaseMod.removeRelic(new FaceOfCleric()); //HP value adjusted

            //TODO: tweak resting relics
            //resting
            BaseMod.removeRelic(new RegalPillow()); //tweak
            BaseMod.removeRelic(new DreamCatcher()); //look at
            BaseMod.removeRelic(new CoffeeDripper()); //look at

        }

        if (AbstractDungeon.player.hasRelic(SlumberingRelic.ID)) {
            //dungeon_instance.eventList.remove(ScrapOoze.ID);
        }
    }
}