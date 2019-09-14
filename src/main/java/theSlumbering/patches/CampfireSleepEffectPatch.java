package theSlumbering.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;

import java.util.ArrayList;
@SuppressWarnings("unused")

public class CampfireSleepEffectPatch {
    @SpirePatch(
            clz = CampfireSleepEffect.class,
            method = "update"
    )

    public static class HealPatch{
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn Insert(CampfireSleepEffect __instance){
            if(AbstractDungeon.player instanceof TheSlumbering){
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "heal");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
