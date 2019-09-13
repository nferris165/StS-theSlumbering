package theSlumbering.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.exordium.*;
import com.megacrit.cardcrawl.events.shrines.*;
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
                locator = MaxHPLocator.class
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
                locator = DamageLocator.class
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
                locator = DamageLocator.class
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

    //Woman in Blue
    @SpirePatch(
            clz = WomanInBlue.class,
            method = "buttonEffect"
    )

    public static class WomanInBluePatch {
        public static void Prefix(WomanInBlue __instance, int buttonPressed) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                AbstractDungeon.player.gainGold(20);
            }
        }
    }

    //Gremlin Wheel Game
    @SpirePatch(
            clz = GremlinWheelGame.class,
            method = "applyResult"
    )

    public static class GremlinWheelGamePatch {
        @SpireInsertPatch(
                localvars = {"damageAmount"},
                locator = DamageLocator.class
        )
        public static SpireReturn Insert(GremlinWheelGame __instance, @ByRef int[] damageAmount) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                damageAmount[0] = 0;
                SlumberingMod.decHeartCollectorRelic(2);
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }

        @SpireInsertPatch(
                locator = HealLocator.class
        )
        public static void Insert2(GremlinWheelGame __instance) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                if(AbstractDungeon.player.hasRelic(HeartCollector.ID)) {
                    SlumberingMod.decHeartCollectorRelic(-(AbstractDungeon.player.getRelic(HeartCollector.ID).counter));
                }
            }
        }
    }

    @SpirePatch(
            clz = GremlinWheelGame.class,
            method = "preApplyResult"
    )

    public static class GremlinWheelGameDescPatch {
        public static void Postfix(GremlinWheelGame __instance) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                int result = (int) ReflectionHacks.getPrivate(
                        __instance, GremlinWheelGame.class, "result");
                if(result == 5){
                    __instance.imageEventText.updateDialogOption(0, OPTIONS[8]);
                }
                else if(result == 2){
                    __instance.imageEventText.updateBodyText(TEXT[3]);
                    __instance.imageEventText.updateDialogOption(0, OPTIONS[7]);
                }
            }
        }
    }

    //Big Fish
    @SpirePatch(
            clz = BigFish.class,
            method = "buttonEffect"
    )

    public static class BigFishPatch {
        @SpireInsertPatch(
                locator = HealLocator.class
        )
        public static void Insert(BigFish __instance, int buttonPressed) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                SlumberingMod.decHeartCollectorRelic(-3);
            }
        }

        public static void Postfix(BigFish __instance, int buttonPressed) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                if(buttonPressed == 0){
                    __instance.imageEventText.updateBodyText(TEXT[4]);
                }
                else if(buttonPressed == 1){
                    __instance.imageEventText.updateBodyText(TEXT[5]);
                }
            }
        }
    }

    @SpirePatch(
            clz = BigFish.class,
            method = SpirePatch.CONSTRUCTOR
    )

    public static class BigFishDescPatch {
        public static void Postfix(BigFish __instance) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                __instance.imageEventText.updateDialogOption(0, OPTIONS[9]);
                __instance.imageEventText.updateDialogOption(1, OPTIONS[10]);

            }
        }
    }

    //Golden Idol
    @SpirePatch(
            clz = GoldenIdolEvent.class,
            method = "buttonEffect"
    )

    public static class GoldenIdolEventPatch {
        private static int value = 2;
        @SpireInsertPatch(
                localvars = {"damage"},
                locator = DamageLocator.class
        )
        public static void Insert(GoldenIdolEvent __instance, int buttonPressed, @ByRef int[] damage) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                damage[0] = 0;
                if (AbstractDungeon.ascensionLevel >= 15) {
                    value = 3;
                }
                SlumberingMod.decHeartCollectorRelic(value);
            }
        }

        @SpireInsertPatch(
                localvars = {"maxHpLoss"},
                locator = DecMaxHPLocator.class
        )
        public static void Insert2(GoldenIdolEvent __instance, int buttonPressed, @ByRef int[] maxHpLoss) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                maxHpLoss[0] = 0;
                SlumberingMod.incSlumberingRelic(-1);
            }
        }

        public static void Postfix(GoldenIdolEvent __instance, int buttonPressed) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                if(__instance.imageEventText.optionList.size() == 3){
                    if (AbstractDungeon.ascensionLevel >= 15) {
                        value = 3;
                    }

                    if(AbstractDungeon.player.hasRelic(HeartCollector.ID)){
                        if(AbstractDungeon.player.getRelic(HeartCollector.ID).counter < value){
                            __instance.imageEventText.updateDialogOption(1, OPTIONS[5], true);
                        }
                        else{
                            __instance.imageEventText.updateDialogOption(1, OPTIONS[11] + OPTIONS[1] + value + " #rCollected #rHearts.");
                        }
                    }
                    __instance.imageEventText.updateDialogOption(2, OPTIONS[12] + OPTIONS[13]);
                }
            }
        }
    }

    //Mushrooms
    @SpirePatch(
            clz = Mushrooms.class,
            method = "buttonEffect"
    )

    public static class MushroomsEventPatch {
        private static int value = -4;
        @SpireInsertPatch(
                localvars = {"healAmt"},
                locator = HealLocator.class
        )
        public static void Insert(Mushrooms __instance, int buttonPressed, @ByRef int[] healAmt) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                healAmt[0] = 0;
                SlumberingMod.decHeartCollectorRelic(value);
            }
        }

        public static void Postfix(Mushrooms __instance, int buttonPressed) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                if(buttonPressed == 1){
                    __instance.roomEventText.updateBodyText(TEXT[6]);
                }
            }
        }
    }

    @SpirePatch(
            clz = Mushrooms.class,
            method = SpirePatch.CONSTRUCTOR
    )

    public static class MushroomsDescPatch {
        public static void Postfix(Mushrooms __instance) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                if(__instance.roomEventText.optionList.size() == 4){
                    __instance.roomEventText.removeDialogOption(3);
                    __instance.roomEventText.removeDialogOption(2);
                }
                if(__instance.roomEventText.optionList.size() != 1){
                    __instance.roomEventText.updateDialogOption(1, OPTIONS[14]);
                }
            }
        }
    }

    //Scrap Ooze
    @SpirePatch(
            clz = ScrapOoze.class,
            method = "buttonEffect"
    )

    public static class ScrapOozeEventPatch {
        private static int value = 1;
        @SpireInsertPatch(
                localvars = {"dmg"},
                locator = DamageLocator.class
        )
        public static void Insert(ScrapOoze __instance, int buttonPressed, @ByRef int[] dmg) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                dmg[0] = 0;
                SlumberingMod.decHeartCollectorRelic(value);
            }
        }

        public static void Postfix(ScrapOoze __instance, int buttonPressed) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                if(__instance.imageEventText.optionList.size() == 2){
                    if(AbstractDungeon.player.hasRelic(HeartCollector.ID)){
                        if(AbstractDungeon.player.getRelic(HeartCollector.ID).counter < value){
                            __instance.imageEventText.updateDialogOption(0, OPTIONS[5], true);
                        }
                        else{
                            int relicObtainChance = (int) ReflectionHacks.getPrivate(
                                    __instance, ScrapOoze.class, "relicObtainChance");
                            __instance.imageEventText.updateDialogOption(0, OPTIONS[15] + relicObtainChance + OPTIONS[16]);
                        }
                    }
                }
            }
        }
    }

    @SpirePatch(
            clz = ScrapOoze.class,
            method = SpirePatch.CONSTRUCTOR
    )

    public static class ScrapOozeDescPatch {
        public static void Postfix(ScrapOoze __instance) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                if(AbstractDungeon.player.getRelic(HeartCollector.ID).counter < 1){
                    __instance.imageEventText.updateDialogOption(0, OPTIONS[5], true);
                }
                else {
                    int relicObtainChance = (int) ReflectionHacks.getPrivate(
                            __instance, ScrapOoze.class, "relicObtainChance");
                    __instance.imageEventText.updateDialogOption(0, OPTIONS[15] + relicObtainChance + OPTIONS[16]);
                }
            }
        }
    }

    //Shining Light
    @SpirePatch(
            clz = ShiningLight.class,
            method = "buttonEffect"
    )

    public static class ShiningLightEventPatch {
        private static int value = 3;
        @SpireInsertPatch(
                localvars = {"damage"},
                locator = DamageLocator.class
        )
        public static void Insert(ShiningLight __instance, int buttonPressed, @ByRef int[] damage) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                damage[0] = 0;
                SlumberingMod.decHeartCollectorRelic(value);
            }
        }
    }

    @SpirePatch(
            clz = ShiningLight.class,
            method = SpirePatch.CONSTRUCTOR
    )

    public static class ShiningLightDescPatch {
        private static int value = 3;
        public static void Postfix(ShiningLight __instance) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                if(AbstractDungeon.player.getRelic(HeartCollector.ID).counter < value){
                    __instance.imageEventText.updateDialogOption(0, OPTIONS[5], true);
                }
                else {
                    __instance.imageEventText.updateDialogOption(0, OPTIONS[17] + value + " #rCollected #rHearts.");
                }
            }
        }
    }

    //Cleric
    @SpirePatch(
            clz = Cleric.class,
            method = "buttonEffect"
    )

    public static class ClericEventPatch {
        private static int value = -2;
        @SpireInsertPatch(
                localvars = {"healAmt"},
                locator = HealLocator.class
        )
        public static void Insert(Cleric __instance, int buttonPressed, @ByRef int[] healAmt) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                healAmt[0] = 0;
                SlumberingMod.decHeartCollectorRelic(value);
            }
        }
    }

    @SpirePatch(
            clz = Cleric.class,
            method = SpirePatch.CONSTRUCTOR
    )

    public static class ClericDescPatch {
        public static void Postfix(Cleric __instance) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                if(AbstractDungeon.player.gold > 35){
                    __instance.imageEventText.updateDialogOption(0, OPTIONS[18] + 2 + " #gCollected #gHearts.");
                }
            }
        }
    }

    //World of Goop
    @SpirePatch(
            clz = GoopPuddle.class,
            method = "buttonEffect"
    )

    public static class GoopPuddleEventPatch {
        private static int value = 1;
        @SpireInsertPatch(
                localvars = {"damage"},
                locator = DamageLocator.class
        )
        public static void Insert(GoopPuddle __instance, int buttonPressed, @ByRef int[] damage) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                damage[0] = 0;
                SlumberingMod.decHeartCollectorRelic(value);
            }
        }

        public static void Postfix(GoopPuddle __instance, int buttonPressed) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                if(buttonPressed == 0){
                    __instance.imageEventText.updateBodyText(TEXT[7]);
                }
            }
        }
    }

    @SpirePatch(
            clz = GoopPuddle.class,
            method = SpirePatch.CONSTRUCTOR
    )

    public static class GoopPuddleDescPatch {
        private static int value = 1;
        public static void Postfix(GoopPuddle __instance) {
            if (AbstractDungeon.player instanceof TheSlumbering) {
                if(AbstractDungeon.player.getRelic(HeartCollector.ID).counter < value){
                    __instance.imageEventText.updateDialogOption(0, OPTIONS[5], true);
                }
                else {
                    __instance.imageEventText.updateDialogOption(0, OPTIONS[19] + value + " #rCollected #rHeart.");
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

    public static class MaxHPLocator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "increaseMaxHp");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }

    public static class DamageLocator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "damage");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }

    public static class HealLocator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "heal");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }

    public static class DecMaxHPLocator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "decreaseMaxHealth");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
