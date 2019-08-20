package theFirst.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theFirst.FirstMod;
import theFirst.characters.TheFirst;
import theFirst.powers.DrowsyPower;

import static theFirst.FirstMod.makeCardPath;

public class NocturnalGifts extends AbstractCustomCard {

    public static final String ID = FirstMod.makeID(NocturnalGifts.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheFirst.Enums.COLOR_FIRST;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    public NocturnalGifts() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p.hasPower(DrowsyPower.POWER_ID)){
            AbstractPower pow = p.getPower(DrowsyPower.POWER_ID);
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, pow.amount));
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