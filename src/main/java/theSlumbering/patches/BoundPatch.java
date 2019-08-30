package theSlumbering.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import theSlumbering.cards.BindingBlow;
import theSlumbering.powers.BoundPower;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractCard.class,
        method = "canUse"
)

public class BoundPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )

    public static SpireReturn<Boolean> Insert(AbstractCard __instance, AbstractPlayer p, AbstractMonster m) {

        if (p.hasPower(BoundPower.POWER_ID )&& !__instance.cardID.equals(BindingBlow.ID)) {
            if(__instance.isGlowing){
                __instance.stopGlowing();
            }
            __instance.cantUseMessage = "I cannot play this while bound.";
            return SpireReturn.Return(false);

        }
        return SpireReturn.Continue();
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "type");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}


