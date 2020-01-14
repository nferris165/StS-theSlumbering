package theSlumbering.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;

import static theSlumbering.SlumberingMod.makeCardPath;

import theSlumbering.patches.customTags;
import theSlumbering.powers.BagOfShieldsPower;
import theSlumbering.powers.RedemptionPower;

public class Redemption extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(Redemption.class.getSimpleName());

    public static final String IMG = makeCardPath("P_temp.png");

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = -2;

    public Redemption() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        tags.add(customTags.Passive);
    }

    @Override
    public void passiveEffect() {
        AbstractPlayer p = AbstractDungeon.player;
        if(!p.hasPower(RedemptionPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RedemptionPower(p, this)));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void upgrade() {
    }
}