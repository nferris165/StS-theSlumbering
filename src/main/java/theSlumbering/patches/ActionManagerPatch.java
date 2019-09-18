package theSlumbering.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;

@SuppressWarnings("unused")

@SpirePatch(
        clz= GameActionManager.class,
        method= SpirePatch.CLASS
)
public class ActionManagerPatch {

    public static SpireField<Integer> snoozeCount = new SpireField<>(() -> 0);

}
