package theSlumbering.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Pain;
import com.megacrit.cardcrawl.cards.curses.Regret;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.FrailPower;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;

import java.util.ArrayList;

import static theSlumbering.SlumberingMod.makeID;

public class CurseHealthPatch {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("CursePatch"));
    private static final String[] TEXT = uiStrings.TEXT;

    //Pain
    @SpirePatch(
            clz = Pain.class,
            method = "triggerOnOtherCardPlayed"
    )

    public static class PainPatch{
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn Insert(Pain __instance, AbstractCard c){
            if(AbstractDungeon.player instanceof TheSlumbering){
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                                new FrailPower(AbstractDungeon.player, 1, false), 1));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = Pain.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class PainDescPatch{
        public static void Prefix(Pain __instance, @ByRef String[] ___DESCRIPTION){
            if(AbstractDungeon.player instanceof TheSlumbering){
                ___DESCRIPTION[0] = TEXT[0];
            }
        }
    }

    //Regret
    @SpirePatch(
            clz = Regret.class,
            method = "use"
    )

    public static class RegretPatch{
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn Insert(Regret __instance){
            if(AbstractDungeon.player instanceof TheSlumbering){
                SlumberingMod.decHeartCollectorRelic(1);
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = Regret.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class RegretDescPatch{
        public static void Prefix(Regret __instance, CardStrings ___cardStrings){
            if(AbstractDungeon.player instanceof TheSlumbering){
                ___cardStrings.DESCRIPTION = TEXT[1];
            }
        }
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "actionManager");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
