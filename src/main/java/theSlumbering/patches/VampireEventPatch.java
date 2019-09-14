package theSlumbering.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import theSlumbering.characters.TheSlumbering;

import java.util.ArrayList;

@SuppressWarnings("unused")

public class VampireEventPatch {
    @SpirePatch(
            clz = Vampires.class,
            method = "buttonEffect"
    )

    public static class VampiresEventPatch {
        private static int value = 1;
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn Insert(Vampires __instance, int buttonPressed, @ByRef int[] ___screenNum) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                __instance.imageEventText.updateBodyText("The tall figure grabs your arm, pulls you forward, and sinks his fangs into your neck... " +
                        "But nothing happens. He tries again, biting deep into your neck, but it doesn't take." +
                        " NL ... NL \" You have strayed too far from the #pdarkness! \" NL You cannot accept their gift. " +
                        "They morph into a thick, black fog that flows away from you, and suddenly you are alone once more.");
                ___screenNum[0] = 1;
                __instance.imageEventText.updateDialogOption(0, "[Leave]");
                __instance.imageEventText.clearRemainingOptions();
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(Vampires.class, "imageEventText");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
