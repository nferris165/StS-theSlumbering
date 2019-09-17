package theSlumbering.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;

public class PassivePoolPatch {
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "returnTrulyRandomCardInCombat",
            paramtypez = {
                    AbstractCard.CardType.class
            }
    )

    public static class PassivePool{
        @SpireInsertPatch(
                locator  = Locator.class,
                localvars = {"list"}
        )
        public static void Insert(ArrayList<AbstractCard> list){
            for(AbstractCard c: srcCommonCardPool.group){
                if(c.hasTag(customTags.Passive)){
                    list.remove(c);
                }
            }
            for(AbstractCard c: srcUncommonCardPool.group){
                if(c.hasTag(customTags.Passive)){
                    list.remove(c);
                }
            }
            for(AbstractCard c: srcRareCardPool.group){
                if(c.hasTag(customTags.Passive)){
                    list.remove(c);
                }
            }
        }
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "returnTrulyRandomCardInCombat",
            paramtypez = {

            }
    )

    public static class PassivePoolTypeless{
        @SpireInsertPatch(
                locator  = Locator.class,
                localvars = {"list"}
        )
        public static void Insert(ArrayList<AbstractCard> list){
            for(AbstractCard c: srcCommonCardPool.group){
                if(c.hasTag(customTags.Passive)){
                    list.remove(c);
                }
            }
            for(AbstractCard c: srcUncommonCardPool.group){
                if(c.hasTag(customTags.Passive)){
                    list.remove(c);
                }
            }
            for(AbstractCard c: srcRareCardPool.group){
                if(c.hasTag(customTags.Passive)){
                    list.remove(c);
                }
            }
        }
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "get");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
