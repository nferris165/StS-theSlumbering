package theSlumbering.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import theSlumbering.SlumberingMod;

import java.util.ArrayList;

import static theSlumbering.SlumberingMod.makeID;

@SpirePatch(
        clz=AbstractCard.class,
        method="renderType"
)
public class LibraryRenderPatch {
    private static UIStrings uiStrings;
    private static String[] TEXT;
    @SpireInsertPatch(
            localvars={"text"},
            locator = Locator.class
    )
    public static void Insert(AbstractCard __instance, SpriteBatch sb, @ByRef String[] text) {
        if (uiStrings == null) {
            String UI_ID = makeID("RenderType");
            uiStrings = CardCrawlGame.languagePack.getUIString(UI_ID);
            TEXT = uiStrings.TEXT;
        }

        if (SlumberingMod.passiveCheck(__instance)) {
            text[0] = TEXT[0];
        }
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderRotatedText");

            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}