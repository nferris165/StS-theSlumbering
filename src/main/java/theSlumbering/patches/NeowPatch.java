package theSlumbering.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.neow.NeowReward.NeowRewardDef;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;

import java.util.ArrayList;
@SuppressWarnings("unused")


public class NeowPatch {
    private static final String[] text = {"[ #gWake #gup #ga #glittle... ]",
            "[ #gWake #gup #ga #glot... ]",
            "[ #gObtain #ga #grandom #gBasic #gCard ]"};

    @SpireEnum
    private static NeowReward.NeowRewardType WAKE;

    @SpireEnum
    private static NeowReward.NeowRewardType WAKE_MORE;

    @SpireEnum
    private static NeowReward.NeowRewardType SLUMBER_BOSS_RELIC;

    @SpireEnum
    private static NeowReward.NeowRewardType BASIC_CARD;

    @SpirePatch(
            clz= NeowReward.class,
            method=SpirePatch.CONSTRUCTOR,
            paramtypez={
                    boolean.class
            }
    )

    public static class NeowMiniPatch{
        public static void Postfix(NeowReward __instance, boolean firstMini){

            if (AbstractDungeon.player instanceof TheSlumbering) {
                if(firstMini) {
                    //do usual
                }
                else{
                    __instance.optionLabel = text[0];
                    __instance.type = WAKE;
                }
            }
        }
    }

    @SpirePatch(
            clz= NeowReward.class,
            method="getRewardOptions"
    )

    public static class NeowBlessPatch{

        public static ArrayList<NeowRewardDef> Postfix(ArrayList<NeowRewardDef> __result, NeowReward __instance, final int category){
            if (AbstractDungeon.player instanceof TheSlumbering) {
                if(category == 0){
                    __result.add(new NeowRewardDef(BASIC_CARD, text[2]));
                }
                else if(category == 1){
                    __result.add(new NeowRewardDef(WAKE, text[0]));

                    NeowRewardDef hp = null;
                    for(NeowRewardDef n: __result){
                        if(n.type.equals(NeowReward.NeowRewardType.TEN_PERCENT_HP_BONUS)){
                            hp = n;
                            break;
                        }
                    }

                    __result.remove(hp);
                }
                else if(category == 2){
                    __result.add(new NeowRewardDef(WAKE_MORE, text[1]));
                    NeowRewardDef hp = null;
                    for(NeowRewardDef n: __result){
                        if(n.type.equals(NeowReward.NeowRewardType.TWENTY_PERCENT_HP_BONUS)){
                            hp = n;
                            break;
                        }
                    }
                    __result.remove(hp);
                }
                else if(category == 3){
                    NeowRewardDef hp = null;
                    for(NeowRewardDef n: __result){
                        if(n.type.equals(NeowReward.NeowRewardType.BOSS_RELIC)){
                            hp = n;
                            break;
                        }
                    }
                    __result.remove(hp);
                    __result.add(new NeowRewardDef(SLUMBER_BOSS_RELIC, "[ #rLose #ryour #rstarting #rRelic #gObtain #ga #grandom #gboss #gRelic ]"));
                }

            }
            return __result;
        }
    }

    @SpirePatch(
            clz = NeowReward.class,
            method = "getRewardDrawbackOptions"
    )
    public static class DrawbackPatch{
        public static ArrayList<NeowReward.NeowRewardDrawbackDef> Postfix(ArrayList<NeowReward.NeowRewardDrawbackDef> __result, NeowReward __instance){
            if(AbstractDungeon.player instanceof TheSlumbering) {
                NeowReward.NeowRewardDrawbackDef hp = null;
                NeowReward.NeowRewardDrawbackDef hp2 = null;
                for (NeowReward.NeowRewardDrawbackDef n : __result) {
                    if (n.type.equals(NeowReward.NeowRewardDrawback.TEN_PERCENT_HP_LOSS)) {
                        hp = n;
                    }
                    if (n.type.equals(NeowReward.NeowRewardDrawback.PERCENT_DAMAGE)) {
                        hp2 = n;
                    }
                }

                __result.remove(hp);
                __result.remove(hp2);
            }
            return __result;
        }
    }

    @SpirePatch(
            clz = NeowReward.class,
            method = "activate"
    )
    public static class ActivatePatch {
        public static void Prefix(NeowReward __instance) {
            if (__instance.type == WAKE) {
                SlumberingMod.incSlumberingRelic(1);
            }
            else if(__instance.type == SLUMBER_BOSS_RELIC){
                AbstractDungeon.player.loseRelic(((AbstractRelic)AbstractDungeon.player.relics.get(1)).relicId);
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.BOSS));
            }
            else if (__instance.type == WAKE_MORE) {
                SlumberingMod.incSlumberingRelic(2);
            }
            else if (__instance.type == BASIC_CARD) {
                ArrayList<AbstractCard> list = SlumberingMod.generateByTag(0);
                AbstractCard c = list.get((NeowEvent.rng.random(0, list.size() - 1)));
                AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            }
        }
    }
}
