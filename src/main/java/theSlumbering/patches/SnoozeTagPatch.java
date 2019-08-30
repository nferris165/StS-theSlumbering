package theSlumbering.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class SnoozeTagPatch
{
    @SpirePatch(
            clz=AbstractCard.class,
            method="makeStatEquivalentCopy"
    )
    public static class MakeStatEquivalentCopy
    {
        public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance)
        {
            if (__instance.hasTag(customTags.Snooze) != __result.hasTag(customTags.Snooze)) {
                if (__instance.hasTag(customTags.Snooze)) {
                    __result.tags.add(customTags.Snooze);
                } else {
                    __instance.tags.remove(customTags.Snooze);
                }
            }
            return __result;
        }
    }
}