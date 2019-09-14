package theSlumbering.patches;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.RestOption;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static theSlumbering.SlumberingMod.makeID;

@SuppressWarnings("unused")

public class CampfireRestPatch {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("CampfirePatch"));
    private static final String[] TEXT = uiStrings.TEXT;
    private static int hearts = 5;
    @SpirePatch(
            clz = RestOption.class,
            method = "useOption"
    )

    public static class RestPatch{
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(RestOption __instance){
            if (AbstractDungeon.player.hasRelic("Regal Pillow")){
                hearts += 2;
            }
            SlumberingMod.decHeartCollectorRelic(-hearts);
            SlumberingMod.incSlumberingRelic(-1);
        }
    }

    @SpirePatch(
            clz = RestOption.class,
            method = "updateUsability"
    )
    public static class TexturePatch{

        public static void Postfix(RestOption __instance, boolean canUse, @ByRef Texture[] ___img, @ByRef String[] ___description){
            if(AbstractDungeon.player instanceof TheSlumbering){
                if (!canUse) {
                    ___img[0] = loadImage(SlumberingMod.makeUIPath("campfire/Slumbering_Disabled.png"));
                } else {
                    ___img[0] = loadImage(SlumberingMod.makeUIPath("campfire/test.png"));
                }
                ___description[0] = TEXT[0] + hearts + TEXT[1];
            }
        }
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "effectList");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
