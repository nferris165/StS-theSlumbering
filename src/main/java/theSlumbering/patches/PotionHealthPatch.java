package theSlumbering.patches;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.potions.FairyPotion;
import com.megacrit.cardcrawl.potions.FruitJuice;
import com.megacrit.cardcrawl.potions.RegenPotion;
import theSlumbering.characters.TheSlumbering;

@SpirePatch(
        clz = PotionHelper.class,
        method = "initialize"
)

public class PotionHealthPatch {

    public static void Postfix(PlayerClass chosenClass){
        if(chosenClass == TheSlumbering.Enums.THE_SLUMBERING){
            //BaseMod.removePotion(FairyPotion.POTION_ID);
            BaseMod.removePotion(RegenPotion.POTION_ID);
            BaseMod.removePotion(FruitJuice.POTION_ID);
        }
    }
}
