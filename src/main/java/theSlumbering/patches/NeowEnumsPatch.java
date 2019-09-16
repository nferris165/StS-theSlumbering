package theSlumbering.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.neow.NeowReward;

public class NeowEnumsPatch {
    @SpireEnum
    public static NeowReward.NeowRewardType WAKE;

    @SpireEnum
    public static NeowReward.NeowRewardType WAKE_MORE;

    @SpireEnum
    public static NeowReward.NeowRewardType SLUMBER_BOSS_RELIC;

    @SpireEnum
    public static NeowReward.NeowRewardType BASIC_CARD;

    @SpireEnum
    public static NeowReward.NeowRewardType START_SLUMBER;
}
