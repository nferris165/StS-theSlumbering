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
import theSlumbering.relics.HeartCollector;

import java.util.ArrayList;

import static theSlumbering.SlumberingMod.makeID;

@SuppressWarnings("unused")

public class RelicHealthPatch {
    private static String[] TEXT = CardCrawlGame.languagePack.getRelicStrings(makeID("Patches")).DESCRIPTIONS;

    //Strawberry
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
            clz= Strawberry.class,
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

    //Pear
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

    //Mango
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

    //Waffle
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

    //Darkstone Periapt
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

    //Blue Candle
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

    //Bloody Idol
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

    //TinyHouse
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

    //FaceOfCleric
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

    //SingingBowl
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

    //MarkOfTheBloom
    @SpirePatch(
            clz = MarkOfTheBloom.class,
            method = "getUpdatedDescription"
    )
    public static class MarkOfTheBloomDescPatch{
        public static SpireReturn<String> Prefix(){
            if(AbstractDungeon.player instanceof TheSlumbering){
                return SpireReturn.Return(TEXT[7]);
            }
            return SpireReturn.Continue();
        }
    }

    //Meat on the Bone
    @SpirePatch(
            clz = MeatOnTheBone.class,
            method = "onTrigger"
    )
    public static class MeatOnTheBonePatch{
        public static SpireReturn Prefix(MeatOnTheBone __instance){
            AbstractPlayer p = AbstractDungeon.player;
            if(p instanceof TheSlumbering) {
                if(p.hasRelic(HeartCollector.ID) && p.getRelic(HeartCollector.ID).counter < 5){
                    SlumberingMod.decHeartCollectorRelic(-1);
                }
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = MeatOnTheBone.class,
            method = "getUpdatedDescription"
    )
    public static class MeatOnTheBoneDescPatch{
        public static SpireReturn<String> Prefix(){
            if(AbstractDungeon.player instanceof TheSlumbering){
                return SpireReturn.Return(TEXT[8]);
            }
            return SpireReturn.Continue();
        }
    }

    //Pantograph
    @SpirePatch(
            clz = Pantograph.class,
            method = "atBattleStart"
    )
    public static class PantographPatch{
        @SpireInsertPatch(
                locator = HealLocator.class
        )
        public static SpireReturn Insert(Pantograph __instance){
            if(AbstractDungeon.player instanceof TheSlumbering){
                SlumberingMod.decHeartCollectorRelic(-2);
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = Pantograph.class,
            method = "getUpdatedDescription"
    )
    public static class PantographDescPatch{
        public static SpireReturn<String> Prefix(){
            if(AbstractDungeon.player instanceof TheSlumbering){
                return SpireReturn.Return(TEXT[9]);
            }
            return SpireReturn.Continue();
        }
    }

    //Eternal Feather
    @SpirePatch(
            clz = EternalFeather.class,
            method = "onEnterRoom"
    )
    public static class EternalFeatherPatch{
        @SpireInsertPatch(
                locator = HealLocator.class
        )
        public static SpireReturn Insert(EternalFeather __instance){
            if(AbstractDungeon.player instanceof TheSlumbering){
                int amountToGain = AbstractDungeon.player.masterDeck.size() / 5;
                SlumberingMod.decHeartCollectorRelic(-amountToGain);
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = EternalFeather.class,
            method = "getUpdatedDescription"
    )
    public static class EternalFeatherDescPatch{
        public static SpireReturn<String> Prefix(){
            if(AbstractDungeon.player instanceof TheSlumbering){
                return SpireReturn.Return(TEXT[10]);
            }
            return SpireReturn.Continue();
        }
    }

    //Meal Ticket
    @SpirePatch(
            clz = MealTicket.class,
            method = "justEnteredRoom"
    )
    public static class MealTicketPatch{
        @SpireInsertPatch(
                locator = HealLocator.class
        )
        public static SpireReturn Insert(MealTicket __instance){
            if(AbstractDungeon.player instanceof TheSlumbering){
                SlumberingMod.decHeartCollectorRelic(-2);
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = MealTicket.class,
            method = "getUpdatedDescription"
    )
    public static class MealTicketDescPatch{
        public static SpireReturn<String> Prefix(){
            if(AbstractDungeon.player instanceof TheSlumbering){
                return SpireReturn.Return(TEXT[11]);
            }
            return SpireReturn.Continue();
        }
    }

    //Toy Ornithopter
    @SpirePatch(
            clz = ToyOrnithopter.class,
            method = "onUsePotion"
    )
    public static class ToyOrnithopterPatch{
        public static SpireReturn Prefix(ToyOrnithopter __instance){
            __instance.flash();
            if(AbstractDungeon.player instanceof TheSlumbering){
                SlumberingMod.decHeartCollectorRelic(-1);
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = ToyOrnithopter.class,
            method = "getUpdatedDescription"
    )
    public static class ToyOrnithopterDescPatch{
        public static SpireReturn<String> Prefix(){
            if(AbstractDungeon.player instanceof TheSlumbering){
                return SpireReturn.Return(TEXT[12]);
            }
            return SpireReturn.Continue();
        }
    }

    //Regal Pillow
    @SpirePatch(
            clz = RegalPillow.class,
            method = "getUpdatedDescription"
    )
    public static class RegalPillowDescPatch{
        public static SpireReturn<String> Prefix(){
            if(AbstractDungeon.player instanceof TheSlumbering){
                return SpireReturn.Return(TEXT[13]);
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

    public static class HealLocator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "heal");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
