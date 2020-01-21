package theSlumbering.patches;


import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import theSlumbering.SlumberingMod;

import java.util.ArrayList;

@SpirePatch(
        clz = CardLibrary.class,
        method = "getCopy",
        paramtypez = {
                String.class,
                int.class,
                int.class
        }
)

public class CardLoadMiscFix {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"retVal"}
    )

    public static void Insert(String key, int upgradeTime, int misc, @ByRef AbstractCard[] retVal) {

        if (retVal[0].cardID.equals(SlumberingMod.makeID("EntombedWeapon"))) {
            retVal[0].applyPowers();
            if (retVal[0].misc >= retVal[0].magicNumber) {
                retVal[0].upgrade();
            }
            retVal[0].initializeDescription();
        }
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "cardID");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}