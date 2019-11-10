package theSlumbering.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;

import static theSlumbering.SlumberingMod.makeCardPath;

public class FullGuard extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(FullGuard.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 2;

    private static final int BLOCK = 6;
    private static final int UPGRADE_BLOCK = 2;

    public FullGuard() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = BLOCK;
        baseSecondMagicNumber = secondMagicNumber = 0;
    }

    @Override
    public void atTurnStart() {
        this.secondMagicNumber = 0;
        this.applyPowers();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.secondMagicNumber = magicNumber * AbstractDungeon.player.cardsPlayedThisTurn;
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.secondMagicNumber));
    }

    @Override
    public void triggerOnCardPlayed(AbstractCard cardPlayed) {
        this.secondMagicNumber = magicNumber * AbstractDungeon.player.cardsPlayedThisTurn;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            upgradeMagicNumber(UPGRADE_BLOCK);
            initializeDescription();
        }
    }
}