package theSlumbering.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.buttons.SingingBowlButton;
import com.megacrit.cardcrawl.ui.buttons.SkipCardButton;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import theSlumbering.characters.TheSlumbering;

import java.util.ArrayList;

import static theSlumbering.SlumberingMod.makeID;

public class BowlButtonPatch {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("BowlPatch"));
    private static final String[] TEXT = uiStrings.TEXT;
    @SpirePatch(
            clz = SingingBowlButton.class,
            method = "render"
    )

    public static class SingingBowlButtonPatch{
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn Insert(SingingBowlButton __instance, SpriteBatch sb, float ___current_x, Color ___textColor){
            if(AbstractDungeon.player instanceof TheSlumbering){
                FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, TEXT[0], ___current_x, SkipCardButton.TAKE_Y, ___textColor);
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderFontCentered");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
