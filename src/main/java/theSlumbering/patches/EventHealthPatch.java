package theSlumbering.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.Bonfire;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;

import java.util.ArrayList;

import static theSlumbering.SlumberingMod.makeID;

@SuppressWarnings("unused")

public class EventHealthPatch {
    private static String[] TEXT = CardCrawlGame.languagePack.getEventString(makeID("Patches")).DESCRIPTIONS;
    
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
}
