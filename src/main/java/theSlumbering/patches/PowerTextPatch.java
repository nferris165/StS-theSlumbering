package theSlumbering.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;
import theSlumbering.relics.SlumberingRelic;

import static theSlumbering.SlumberingMod.makeID;

public class PowerTextPatch {
    private static String[] TEXT = CardCrawlGame.languagePack.getPowerStrings(makeID("Patches")).DESCRIPTIONS;

    @SpirePatch(
            clz= BarricadePower.class,
            method="updateDescription"
    )
    public static class BarricadeDescPatch{
        public static SpireReturn<String> Prefix(BarricadePower __instance){
            if(ActionManagerPatch.hasBag.get(AbstractDungeon.actionManager)){
                if(AbstractDungeon.player.hasRelic(SlumberingRelic.ID)){
                    __instance.description = TEXT[0] + (AbstractDungeon.player.getRelic(SlumberingRelic.ID).counter * 2) + TEXT[1];
                    return SpireReturn.Return(TEXT[0] + (AbstractDungeon.player.getRelic(SlumberingRelic.ID).counter * 2) + TEXT[1]);
                } else {
                    __instance.description = TEXT[0] + 5 + TEXT[1];
                    return SpireReturn.Return(TEXT[0] + 5 + TEXT[1]);
                }
            }
            return SpireReturn.Continue();
        }
    }
}
