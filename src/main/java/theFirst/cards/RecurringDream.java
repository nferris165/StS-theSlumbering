package theFirst.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.SlumberingMod;
import theFirst.actions.HandSelectAction;
import theFirst.characters.TheSlumbering;

import static theFirst.SlumberingMod.makeCardPath;

public class RecurringDream extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(RecurringDream.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    public RecurringDream() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new HandSelectAction(this.upgraded));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            this.rawDescription = this.updated_desc;
            initializeDescription();
        }
    }
}