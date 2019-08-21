package theFirst.cards.Curses;

import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.SlumberingMod;
import theFirst.actions.SeedAction;
import theFirst.cards.AbstractCustomCard;

import static theFirst.SlumberingMod.makeCardPath;

public class Seed extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(Seed.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.CURSE;
    public static final CardColor COLOR = CardColor.CURSE;

    private static final int COST = -2;

    public Seed(){
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.misc = 0;
        this.baseMagicNumber = this.magicNumber = this.misc;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void triggerWhenDrawn() {
        AbstractDungeon.actionManager.addToBottom(new SetDontTriggerAction(this, false));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return true;
    }

    @Override
    public void triggerOnExhaust() {
        AbstractDungeon.actionManager.addToBottom(new SeedAction(this.uuid, 1));
    }

    @Override
    public void applyPowers() {
        this.baseMagicNumber = this.misc;
        super.applyPowers();
    }

    @Override
    public void upgrade() {
    }
}