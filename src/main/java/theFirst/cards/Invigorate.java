package theFirst.cards;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.SlumberingMod;
import theFirst.characters.TheSlumbering;
import theFirst.powers.DrowsyPower;

import static theFirst.SlumberingMod.makeCardPath;

public class Invigorate extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(Invigorate.class.getSimpleName());

    public static final String IMG = makeCardPath("P_temp.png");

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;


    public Invigorate() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, DrowsyPower.POWER_ID, 1));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.isInnate = true;
            upgradeBaseCost(UPGRADED_COST);
            this.rawDescription = this.updated_desc;
            initializeDescription();
        }
    }
}