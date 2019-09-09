package theSlumbering.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;

import java.util.ArrayList;
@SuppressWarnings("unused")

public class RelicHealthPatch {
    private static String[] TEXT = {
            "Wake up a little...",
            "Wake up a lot...",
            "Whenever you obtain a #rCurse, wake up a litte..."
    };
    @SpirePatch(
            clz= Strawberry.class,
            method="onEquip"
    )

    public static class StrawberryPatch{
        @SpireInsertPatch(
                locator = StrawLocator.class
        )
        public static SpireReturn Insert(Strawberry __instance){
            if(AbstractDungeon.player instanceof TheSlumbering){
                SlumberingMod.incSlumberingRelic(1);
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz= Pear.class,
            method="getUpdatedDescription"
    )
    public static class StrawberryDescPatch{
        public static SpireReturn<String> Prefix(){
            if(AbstractDungeon.player instanceof TheSlumbering){
                return SpireReturn.Return(TEXT[0]);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Pear.class,
            method="onEquip"
    )
    public static class PearPatch{
        @SpireInsertPatch(
                locator = StrawLocator.class
        )
        public static SpireReturn Insert(Pear __instance){
            if(AbstractDungeon.player instanceof TheSlumbering){
                SlumberingMod.incSlumberingRelic(1);
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Pear.class,
            method="getUpdatedDescription"
    )
    public static class PearDescPatch{
        public static SpireReturn<String> Prefix(){
            if(AbstractDungeon.player instanceof TheSlumbering){
                return SpireReturn.Return(TEXT[0]);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Mango.class,
            method="onEquip"
    )
    public static class MangoPatch{
        @SpireInsertPatch(
                locator = StrawLocator.class
        )
        public static SpireReturn Insert(Mango __instance){
            if(AbstractDungeon.player instanceof TheSlumbering){
                SlumberingMod.incSlumberingRelic(1);
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Mango.class,
            method="getUpdatedDescription"
    )
    public static class MangoDescPatch{
        public static SpireReturn<String> Prefix(){
            if(AbstractDungeon.player instanceof TheSlumbering){
                return SpireReturn.Return(TEXT[0]);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Waffle.class,
            method="onEquip"
    )
    public static class WafflePatch{
        @SpireInsertPatch(
                locator = StrawLocator.class
        )
        public static SpireReturn Insert(Waffle __instance){
            if(AbstractDungeon.player instanceof TheSlumbering){
                SlumberingMod.incSlumberingRelic(2);
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Waffle.class,
            method="getUpdatedDescription"
    )
    public static class WaffleDescPatch{
        public static SpireReturn<String> Prefix(){
            if(AbstractDungeon.player instanceof TheSlumbering){
                return SpireReturn.Return(TEXT[1]);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= DarkstonePeriapt.class,
            method="onObtainCard"
    )
    public static class DarkstonePatch{
        @SpireInsertPatch(
                locator = DarkstoneLocator.class
        )
        public static SpireReturn Insert(DarkstonePeriapt __instance, AbstractCard card){
            if(card.color == AbstractCard.CardColor.CURSE){
                if(AbstractDungeon.player instanceof TheSlumbering){
                    SlumberingMod.incSlumberingRelic(1);
                    return SpireReturn.Return(null);
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= DarkstonePeriapt.class,
            method="getUpdatedDescription"
    )
    public static class DarkstoneDescPatch{
        public static SpireReturn<String> Prefix(){
            if(AbstractDungeon.player instanceof TheSlumbering){
                return SpireReturn.Return(TEXT[2]);
            }
            return SpireReturn.Continue();
        }
    }


    // Locators
    public static class StrawLocator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "player");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }

    public static class DarkstoneLocator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "color");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
