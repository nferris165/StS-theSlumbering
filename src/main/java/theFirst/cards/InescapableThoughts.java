package theFirst.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.SlumberingMod;
import theFirst.characters.TheSlumbering;
import theFirst.powers.RepeatPower;

import static theFirst.SlumberingMod.makeCardPath;

public class InescapableThoughts extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(InescapableThoughts.class.getSimpleName());

    public static final String IMG = makeCardPath("P_temp.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    public InescapableThoughts() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!p.hasPower(RepeatPower.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RepeatPower(p)));
        }
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}