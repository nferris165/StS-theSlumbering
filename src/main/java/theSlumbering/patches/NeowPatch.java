package theSlumbering.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.neow.NeowReward;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;

import java.util.ArrayList;

import static theSlumbering.SlumberingMod.logger;
import static theSlumbering.SlumberingMod.makeID;

public class NeowPatch {

    @SpirePatch(
            clz= NeowReward.class,
            method=SpirePatch.CONSTRUCTOR,
            paramtypez={
                    boolean.class
            }
    )

    public static class npPatch{
        public static void Postfix(NeowReward __instance, boolean firstMini){

            if (AbstractDungeon.player instanceof TheSlumbering) {
                if(firstMini) {
                    __instance.optionLabel = "hello";
                    __instance.type = NeowReward.NeowRewardType.THREE_ENEMY_KILL;
                }
                else{
                    __instance.optionLabel = "hello";
                    __instance.type = NeowReward.NeowRewardType.THREE_ENEMY_KILL;
                }
            }
        }
    }

    /*
    @SpirePatch(
            clz= NeowReward.class,
            method=SpirePatch.CONSTRUCTOR,
            paramtypez={
                    boolean.class
            }
    )

    public static class NeowMiniPatch{
        private static UIStrings uiStrings;
        private static String[] TEXT;
        @SpireInsertPatch(

                locator = Locator.class
        )
        public static void Insert(NeowReward __instance, boolean firstMini) {
            /*if (uiStrings == null) {
                String UI_ID = makeID("Neow");
                uiStrings = CardCrawlGame.languagePack.getUIString(UI_ID);
                TEXT = uiStrings.TEXT;
            }

            if (AbstractDungeon.player instanceof TheSlumbering) {
                if(firstMini) {
                    reward[0] = new NeowReward.NeowRewardDef(NeowReward.NeowRewardType.THREE_ENEMY_KILL, TEXT[28]);
                }
                else{
                    reward[0] = new NeowReward.NeowRewardDef(NeowReward.NeowRewardType.THREE_ENEMY_KILL, TEXT[28]);
                }
            }

            if (AbstractDungeon.player instanceof TheSlumbering) {
                if(firstMini) {
                    __instance.optionLabel = "hello";
                    __instance.type = NeowReward.NeowRewardType.THREE_ENEMY_KILL;
                }
                else{
                    __instance.optionLabel = "hello";
                    __instance.type = NeowReward.NeowRewardType.THREE_ENEMY_KILL;
                }
            }
            logger.info(__instance.optionLabel + "got here \n\n");
        }

        public static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(NeowReward.class, "optionLabel");

                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
            }
        }
    }
/*
    @SpirePatch(
            clz= NeowReward.class,
            method="getRewardOptions"
    )

    public static class NeowBlessPatch{
        @SpireInsertPatch(
                localvars={"reward"},
                locator = NeowMiniPatch.Locator.class
        )

        public static void Insert(){

        }
    }

    @SpirePatch(
            clz = NeowReward.class,
            method = "activate"
    )
    public static class ActivatePatch {
        public static void Prefix(NeowReward __instance) {

        }
    }*/
}
