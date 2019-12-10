package theSlumbering.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSlumbering.actions.AddSnoozeAction;
import theSlumbering.actions.SnoozeAction;
import theSlumbering.characters.TheSlumbering;

import static theSlumbering.SlumberingMod.makeCardPath;
import static theSlumbering.SlumberingMod.makeID;

public class Snooze extends AbstractCustomCard {

    public static final String ID = makeID(Snooze.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    private static final int MAGIC = 3;
    private static final int UPGRADED_MAGIC = 1;

    private static final int SEC_MAGIC = 4;

    public Snooze() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = baseMagicNumber = MAGIC;
        this.secondMagicNumber = baseSecondMagicNumber = SEC_MAGIC;

        this.exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //p.limbo.addToTop(this);
        p.hand.removeCard(this);
        for(AbstractCard c: p.hand.group){
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, secondMagicNumber));
        }
        AbstractDungeon.actionManager.addToBottom(new SnoozeAction(p.hand));
        AbstractDungeon.actionManager.addToBottom(new ShuffleAction(p.drawPile, false));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            //upgradeBaseCost(UPGRADED_COST);
            upgradeMagicNumber(UPGRADED_MAGIC);
            initializeDescription();
        }
    }
}