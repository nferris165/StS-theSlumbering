package theSlumbering.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import theSlumbering.SlumberingMod;
import theSlumbering.actions.StartDrawAction;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractRoom.class,
        method = "update"
)

public class BattleDrawBlockRenderPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )

    public static void Insert(AbstractRoom __instance) {

        SlumberingMod.logger.info(AbstractDungeon.player.hand.group + "\n\n");
        AbstractDungeon.actionManager.addToBottom(new StartDrawAction());

    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(ModHelper.class, "isModEnabled");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
