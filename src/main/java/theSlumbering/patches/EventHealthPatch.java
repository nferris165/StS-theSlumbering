package theSlumbering.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.Bonfire;
import com.megacrit.cardcrawl.events.shrines.Designer;
import com.megacrit.cardcrawl.events.shrines.FaceTrader;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;
import theSlumbering.relics.HeartCollector;

import java.util.ArrayList;

import static theSlumbering.SlumberingMod.makeID;

@SuppressWarnings("unused")

public class EventHealthPatch {
    private static String[] TEXT = CardCrawlGame.languagePack.getEventString(makeID("Patches")).DESCRIPTIONS;
    private static String[] OPTIONS = CardCrawlGame.languagePack.getEventString(makeID("Patches")).OPTIONS;

    // Bonfire
    @SpirePatch(
            clz = Bonfire.class,
            method = "setReward"
    )

    public static class BonfirePatch{
        @SpireInsertPatch(
                localvars = {"dialog", "DIALOG_3"},
                locator = BonfireLocator1.class
        )
        public static SpireReturn Insert(Bonfire __instance, CardRarity rarity, String dialog, String DIALOG_3){
            if(AbstractDungeon.player instanceof TheSlumbering){
                if(rarity == CardRarity.SPECIAL) {
                    SlumberingMod.incSlumberingRelicFloat(5);
                    __instance.imageEventText.updateBodyText(DIALOG_3 + TEXT[0]);
                    return SpireReturn.Return(null);
                }
                else if(rarity == CardRarity.UNCOMMON) {
                    SlumberingMod.incSlumberingRelic(1);
                    __instance.imageEventText.updateBodyText(DIALOG_3 + TEXT[1]);
                    return SpireReturn.Return(null);
                }
            }
            return SpireReturn.Continue();
        }

        @SpireInsertPatch(
                localvars = {"dialog", "DIALOG_3"},
                locator = BonfireLocator3.class
        )
        public static SpireReturn Insert3(Bonfire __instance, CardRarity rarity, String dialog, String DIALOG_3){
            if(AbstractDungeon.player instanceof TheSlumbering){
                SlumberingMod.incSlumberingRelic(2);
                __instance.imageEventText.updateBodyText(DIALOG_3 + TEXT[2]);
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    // Designer
    @SpirePatch(
            clz = Designer.class,
            method = "buttonEffect"
    )

    public static class DesignerPatch {
        @SpireInsertPatch(
                localvars = {"hpLoss"},
                locator = DesignerLocator.class
        )
        public static void Insert(Designer __instance, int buttonPressed, @ByRef int[] hpLoss) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                hpLoss[0] = 0;
            }
        }

        public static void Postfix(Designer __instance, int buttonPressed) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                if(__instance.imageEventText.optionList.size() == 4){
                    __instance.imageEventText.updateDialogOption(3, OPTIONS[0]);
                }
            }
        }
    }

    //Face Trader
    @SpirePatch(
            clz = FaceTrader.class,
            method = "buttonEffect"
    )

    public static class FaceTraderPatch {
        private static int goldReward = 75, value = 2;
        @SpireInsertPatch(
                localvars = {"damage"},
                locator = DesignerLocator.class //same spot
        )
        public static void Insert(FaceTrader __instance, int buttonPressed, @ByRef int[] damage) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                damage[0] = 0;
                SlumberingMod.decHeartCollectorRelic(value);
            }
        }

        public static void Postfix(FaceTrader __instance, int buttonPressed) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                if(__instance.imageEventText.optionList.size() == 3){
                    if (AbstractDungeon.ascensionLevel >= 15) {
                        goldReward = 50;
                    }

                    if(AbstractDungeon.player.hasRelic(HeartCollector.ID)){
                        if(AbstractDungeon.player.getRelic(HeartCollector.ID).counter < value){
                            __instance.imageEventText.updateDialogOption(0, OPTIONS[5], true);
                        }
                        else{
                            __instance.imageEventText.updateDialogOption(0, OPTIONS[2] + OPTIONS[1] + value + OPTIONS[3] + goldReward + OPTIONS[4]);
                        }
                    }
                }
            }
        }
    }




    // Locators
    public static class BonfireLocator1 extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "heal");
            return LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
        }
    }

    public static class BonfireLocator3 extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "increaseMaxHp");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }

    public static class DesignerLocator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "damage");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
