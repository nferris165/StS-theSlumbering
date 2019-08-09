package theFirst.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.FirstMod;
import theFirst.actions.HandSelectAction;
import theFirst.characters.TheFirst;

import static theFirst.FirstMod.makeCardPath;

public class RecurringDream extends AbstractCustomCard {

    public static final String ID = FirstMod.makeID(RecurringDream.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheFirst.Enums.COLOR_FIRST;

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