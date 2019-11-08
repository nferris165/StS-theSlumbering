package theSlumbering.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import theSlumbering.powers.BagOfShieldsPower;
import theSlumbering.relics.SlumberingRelic;

import java.util.ArrayList;

@SpirePatch(
        clz= GameActionManager.class,
        method= "getNextAction"
)
public class ActionManagerBlockPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )

    public static void Insert(GameActionManager __instance) {

        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasPower(BagOfShieldsPower.POWER_ID)) {
            int loss = p.currentBlock;
            if(AbstractDungeon.player.hasRelic(SlumberingRelic.ID)){
                loss -= AbstractDungeon.player.getRelic(SlumberingRelic.ID).counter * 2;
            }

            if(loss < 0){
                loss = 0;
            }
            p.loseBlock(loss);

        }
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasPower");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
