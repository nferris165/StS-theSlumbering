package theSlumbering.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.*;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;

import java.util.ArrayList;

import static theSlumbering.SlumberingMod.makeID;

@SuppressWarnings("unused")

public class RelicHealthPatch {
    private static String[] TEXT = CardCrawlGame.languagePack.getRelicStrings(makeID("Patches")).DESCRIPTIONS;
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


    @SpirePatch(
            clz = BlueCandle.class,
            method = "onUseCard"
    )
    public static class BlueCandlePatch{
        @SpireInsertPatch(
                locator = BlueLocator.class
        )
        public static SpireReturn Insert(BlueCandle __instance, AbstractCard card, UseCardAction action){
            if(card.color == AbstractCard.CardColor.CURSE){
                AbstractPlayer p = AbstractDungeon.player;
                if(p instanceof TheSlumbering) {
                    p.getRelic("Blue Candle").flash();
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                            new WeakPower(p, 1, false), 1));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                            new FrailPower(p, 1, false), 1));
                    card.exhaust = true;
                    action.exhaustCard = true;

                    return SpireReturn.Return(null);
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = BlueCandle.class,
            method = "getUpdatedDescription"
    )
    public static class BlueCandleDescPatch{
        public static SpireReturn<String> Prefix(){
            if(AbstractDungeon.player instanceof TheSlumbering){
                return SpireReturn.Return(TEXT[3]);
            }
            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = BloodyIdol.class,
            method = "onGainGold"
    )
    public static class BloodyIdolPatch{
        @SpireInsertPatch(
                locator = IdolLocator.class
        )
        public static SpireReturn Insert(BloodyIdol __instance){
            AbstractPlayer p = AbstractDungeon.player;
            if(p instanceof TheSlumbering) {
                if(AbstractDungeon.relicRng.random(0, 99) >= 40){
                    SlumberingMod.decHeartCollectorRelic(1);
                }

                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = BloodyIdol.class,
            method = "getUpdatedDescription"
    )
    public static class BloodyIdolDescPatch{
        public static SpireReturn<String> Prefix(){
            if(AbstractDungeon.player instanceof TheSlumbering){
                return SpireReturn.Return(TEXT[4]);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = TinyHouse.class,
            method = "onEquip"
    )
    public static class TinyHousePatch{
        public static void Prefix(TinyHouse __instance){
            AbstractPlayer p = AbstractDungeon.player;
            if(p instanceof TheSlumbering) {
                SlumberingMod.incSlumberingRelicFloat(5);
            }
        }
    }


    @SpirePatch(
            clz = FaceOfCleric.class,
            method = "onVictory"
    )
    public static class FaceOfClericPatch{
        public static void Prefix(FaceOfCleric __instance){
            AbstractPlayer p = AbstractDungeon.player;
            if(p instanceof TheSlumbering) {
                SlumberingMod.incSlumberingRelicFloat(1);
            }
        }
    }

    @SpirePatch(
            clz = FaceOfCleric.class,
            method = "getUpdatedDescription"
    )
    public static class FaceOfClericDescPatch{
        public static SpireReturn<String> Prefix(){
            if(AbstractDungeon.player instanceof TheSlumbering){
                return SpireReturn.Return(TEXT[6]);
            }
            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = SingingBowl.class,
            method = "getUpdatedDescription"
    )
    public static class SingingBowlDescPatch{
        public static SpireReturn<String> Prefix(){
            if(AbstractDungeon.player instanceof TheSlumbering){
                return SpireReturn.Return(TEXT[5]);
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

    public static class BlueLocator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "type");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }

    public static class IdolLocator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "player");
            return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[1]};
        }
    }
}
