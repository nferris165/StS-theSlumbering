package theFirst.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import theFirst.SlumberingMod;

import java.util.ArrayList;

@SpirePatch(
        clz = CardRewardScreen.class,
        method = "discoveryOpen",
        paramtypez = {
                AbstractCard.CardType.class
        }
)

public class DiscoveryPatch {

    @SpireInsertPatch(
            localvars = {"derp"},
            locator = DiscoveryPatch.Locator.class
    )

    public static void Insert(CardRewardScreen __instance, AbstractCard.CardType cardType, @ByRef ArrayList[] derp){
        if(cardType.equals(customTypes.PASSIVE)){
            derp[0].clear();
            int[] list = {-1, -1, -1};
            boolean dup;
            int size = AbstractDungeon.player.drawPile.size();
            int index;

            if(size < 3){
                for(int j = 0; j < (3 - size); j++)
                derp[0].add(null);
            }

            while(derp[0].size() != 3) {
                dup = false;
                index = AbstractDungeon.miscRng.random(0, size - 1);
                SlumberingMod.logger.info(size + " : " + index + "\n\n");
                for (int i: list) {
                    if(index == i){
                        dup = true;
                        break;
                    }
                }
                for (int n = 0; n < 3; n++) {
                    if(list[n] == -1){
                        list[n] = index;
                        break;
                    }
                }
                if(!dup){
                    derp[0].add(AbstractDungeon.player.drawPile.group.get(index));
                }
            }
        }
    }

    @SpireInsertPatch(
            localvars = {"derp"},
            locator = DiscoveryPatch.Locator2.class
    )
    public static void Insert2(CardRewardScreen __instance, AbstractCard.CardType cardType, @ByRef ArrayList[] derp){
        ArrayList<Object> remove = new ArrayList<>();

        for (Object c: derp[0]) {
            if(c == null){
                remove.add(c);
            }
        }

        for (Object c: remove) {
            derp[0].remove(c);
        }
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "size");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }

    public static class Locator2 extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(CardRewardScreen.class, "rewardGroup");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
