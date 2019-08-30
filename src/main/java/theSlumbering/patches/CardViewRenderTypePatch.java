package theSlumbering.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import theSlumbering.SlumberingMod;

import java.util.ArrayList;

import static theSlumbering.SlumberingMod.makeID;

@SpirePatch(
        clz = SingleCardViewPopup.class,
        method = "renderCardTypeText"
)
public class CardViewRenderTypePatch {
    private static UIStrings uiStrings;
    private static String[] TEXT;
    @SpireInsertPatch(
            localvars = {"label", "card"},
            locator = Locator.class
    )
    public static void Insert(SingleCardViewPopup __instance, SpriteBatch sb, @ByRef String[] label, AbstractCard card) {
        if (uiStrings == null) {
            String UI_ID = makeID("RenderType");
            uiStrings = CardCrawlGame.languagePack.getUIString(UI_ID);
            TEXT = uiStrings.TEXT;
        }

        if (SlumberingMod.passiveCheck(card)) {
            label[0] = TEXT[0];
        }
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderFontCentered");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}