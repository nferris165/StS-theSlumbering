package theSlumbering.patches;

import basemod.helpers.ModalChoice;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.VelvetChoker;

@SpirePatch(
        clz = VelvetChoker.class,
        method = "onPlayCard"
)

public class ModalChokerFixPatch {

    public static void Postfix(AbstractRelic __instance, AbstractCard card, AbstractMonster m) {

        if(ModalChoice.Callback.class.isAssignableFrom(card.getClass())){
            if(__instance.counter > 0){
                __instance.counter--;
            }
        }

    }
}
