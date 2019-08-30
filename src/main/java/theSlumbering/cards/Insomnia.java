package theSlumbering.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;
import theSlumbering.powers.DrowsyPower;

import static theSlumbering.SlumberingMod.makeCardPath;

public class Insomnia extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(Insomnia.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 3;

    private static final int BLOCK = 4;

    private static final int MAGIC = 4;
    private static final int UPGRADE_MAGIC = 3;

    private static final int MAGIC2 = 2;
    private static final int MAGIC2_UPGRADE = 1;

    public Insomnia() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        baseSecondMagicNumber = secondMagicNumber = MAGIC2;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));

        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, DrowsyPower.POWER_ID, secondMagicNumber));
    }




    @Override
    public void applyPowers() {
        this.baseBlock = BLOCK + this.magicNumber * AbstractDungeon.player.getPower(DrowsyPower.POWER_ID).amount;
        super.applyPowers();
        this.isBlockModified = this.block != BLOCK;
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeSecondMagicNumber(MAGIC2_UPGRADE);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}