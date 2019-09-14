package theSlumbering.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import theSlumbering.SlumberingMod;

import static com.megacrit.cardcrawl.helpers.ImageMaster.*;

public class ImageMasterPatch {
    @SpirePatch(
            clz = ImageMaster.class,
            method = "initialize"
    )

    public static class CampfirePatch{
        public static void Postfix(){
            //CAMPFIRE_REST_BUTTON = loadImage(SlumberingMod.makeUIPath("campfire/test.png"));
            //CAMPFIRE_REST_DISABLE_BUTTON = loadImage(SlumberingMod.makeUIPath("campfire/test.png"));
        }
    }
}
