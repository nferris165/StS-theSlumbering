package theSlumbering.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;
import theSlumbering.powers.SideSleeperPower;

import static theSlumbering.SlumberingMod.makeCardPath;

public class SideSleeper extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(SideSleeper.class.getSimpleName());

    public static final String IMG = makeCardPath("P_temp.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = -1;

    public SideSleeper() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        baseMagicNumber = magicNumber = MAGIC;

    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!p.hasPower(SideSleeperPower.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SideSleeperPower(p, this.magicNumber)));
        }
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            //upgradeMagicNumber(UPG_MAGIC);
            initializeDescription();
        }
    }
}