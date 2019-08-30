package theSlumbering.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.DiscardAtEndOfTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.Iterator;

@SpirePatch(
        clz = DiscardAtEndOfTurnAction.class,
        method = "update"
)

public class SnoozePatch {
    @SpireInsertPatch(
            localvars = {"c", "e"},
            locator = Locator.class
    )

    public static void Insert(DiscardAtEndOfTurnAction __instance, Iterator c, AbstractCard e){

        if(e.hasTag(customTags.Snooze)){
            //SlumberingMod.logger.info(c + " " + e + " \n\n");
            AbstractDungeon.player.limbo.addToTop(e);
            c.remove();
        }
    }

    @SpireInsertPatch(
            locator = L2.class
    )

    public static void Insert2(DiscardAtEndOfTurnAction __instance){
        Iterator c = AbstractDungeon.player.limbo.group.iterator();

        while(c.hasNext()) {
            AbstractCard e = (AbstractCard)c.next();
            if (e.hasTag(customTags.Snooze)) {
                AbstractDungeon.topLevelEffects.add(new ShowCardAndAddToDrawPileEffect(e, true, false));
                c.remove();
            }
        }
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "retain");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }

    public static class L2 extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "actionManager");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
