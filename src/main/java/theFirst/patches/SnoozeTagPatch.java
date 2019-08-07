package theFirst.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class SnoozeTagPatch
{
    @SpireEnum
    public static AbstractCard.CardTags FIRST_SNOOZE;

    @SpirePatch(
            clz=AbstractCard.class,
            method="makeStatEquivalentCopy"
    )
    public static class MakeStatEquivalentCopy
    {
        public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance)
        {
            if (__instance.hasTag(FIRST_SNOOZE) != __result.hasTag(FIRST_SNOOZE)) {
                if (__instance.hasTag(FIRST_SNOOZE)) {
                    __result.tags.add(FIRST_SNOOZE);
                } else {
                    __instance.tags.remove(FIRST_SNOOZE);
                }
            }
            return __result;
        }
    }
}