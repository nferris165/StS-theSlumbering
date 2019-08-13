package theFirst.patches;


import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

@SpirePatch(
        clz= SingleCardViewPopup.class,
        method="allowUpgradePreview"
)

public class PassiveUpgradeRenderPatch {

    @SpireInsertPatch(
            localvars={"card"},
            rloc = 0
    )
    public static SpireReturn<Boolean> Insert(SingleCardViewPopup __instance, AbstractCard card){

        boolean bool = card.hasTag(customTags.Passive);

        if(bool){
            return SpireReturn.Return(false);
        }


            return SpireReturn.Continue();
    }
}
