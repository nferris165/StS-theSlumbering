package theFirst.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.FirstMod;
import theFirst.characters.TheFirst;
import theFirst.powers.SideSleeperPower;

import static theFirst.FirstMod.makeCardPath;
import static theFirst.FirstMod.makeID;

public class SideSleeper extends AbstractCustomCard {

    public static final String ID = FirstMod.makeID(SideSleeper.class.getSimpleName());

    public static final String IMG = makeCardPath("P_temp.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheFirst.Enums.COLOR_FIRST;

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
        if(!p.hasPower(makeID("SideSleeperPower"))){
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