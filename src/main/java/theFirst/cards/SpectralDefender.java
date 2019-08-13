package theFirst.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.FirstMod;
import theFirst.characters.TheFirst;
import theFirst.patches.customTags;
import theFirst.powers.DoubleBlockPower;

import static theFirst.FirstMod.makeCardPath;

public class SpectralDefender extends AbstractCustomCard {

    public static final String ID = FirstMod.makeID(SpectralDefender.class.getSimpleName());

    public static final String IMG = makeCardPath("P_temp.png");

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheFirst.Enums.COLOR_FIRST;

    private static final int COST = -2;

    public SpectralDefender() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        tags.add(customTags.Passive);

    }

    @Override
    public void passiveEffect() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DoubleBlockPower(p)));
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