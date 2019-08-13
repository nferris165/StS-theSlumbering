package theFirst.cards;

import com.megacrit.cardcrawl.actions.unique.DoubleYourBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.FirstMod;
import theFirst.characters.TheFirst;

import static com.megacrit.cardcrawl.cards.green.Reflex.EXTENDED_DESCRIPTION;
import static theFirst.FirstMod.makeCardPath;

public class StrongFooting extends AbstractCustomCard {

    public static final String ID = FirstMod.makeID(StrongFooting.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheFirst.Enums.COLOR_FIRST;

    private static final int COST = 1;
    private static final int UP_COST = 0;

    private static final int MAGIC = 8;
    private static final int UPGRADE_MAGIC = 5;

    public StrongFooting() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        baseMagicNumber = magicNumber = MAGIC;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DoubleYourBlockAction(p));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(p.currentBlock > this.magicNumber){
            this.cantUseMessage = EXTENDED_DESCRIPTION[0];
            return false;
        }
        return super.canUse(p, m);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UP_COST);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}