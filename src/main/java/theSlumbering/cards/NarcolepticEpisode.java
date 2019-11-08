package theSlumbering.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theSlumbering.characters.TheSlumbering;
import theSlumbering.powers.DrowsyPower;
import theSlumbering.powers.LoseDrowsyPower;

import static com.megacrit.cardcrawl.cards.green.Reflex.EXTENDED_DESCRIPTION;
import static theSlumbering.SlumberingMod.makeCardPath;
import static theSlumbering.SlumberingMod.makeID;

public class NarcolepticEpisode extends AbstractCustomCard {

    public static final String ID = makeID(NarcolepticEpisode.class.getSimpleName());

    public static final String IMG = makeCardPath("P_temp.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = -2;

    private static final int MAGIC = 1;
    private static final int UP_MAGIC = 1;

    private static final int SEC_MAGIC = 2;

    public NarcolepticEpisode() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.baseMagicNumber = magicNumber = MAGIC;
        this.baseSecondMagicNumber = secondMagicNumber = SEC_MAGIC;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void triggerWhenDrawn() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new DrowsyPower(p, this.magicNumber, false), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new LoseDrowsyPower(p, magicNumber), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new StrengthPower(p, this.secondMagicNumber), this.secondMagicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new LoseStrengthPower(p, this.secondMagicNumber), this.secondMagicNumber));
    }

    @Override
    public void triggerOnManualDiscard() {
        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, AbstractDungeon.player.discardPile));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UP_MAGIC);
            initializeDescription();
        }
    }
}