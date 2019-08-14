package theFirst.cards.Curses;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.FirstMod;
import theFirst.cards.AbstractCustomCard;
import theFirst.patches.customTags;
import theFirst.powers.DrowsyPower;

import static theFirst.FirstMod.makeCardPath;

public class Sloth extends AbstractCustomCard {

    public static final String ID = FirstMod.makeID(Sloth.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.CURSE;
    public static final CardColor COLOR = CardColor.CURSE;

    private static final int COST = 1;

    private static final int DROWSY = 1;

    public Sloth(){
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.baseSecondMagicNumber = this.secondMagicNumber = DROWSY;
        this.tags.add(customTags.ActionCurse);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, DrowsyPower.POWER_ID, this.secondMagicNumber));
    }

    @Override
    public void upgrade() {
    }

    @Override
    public void passiveEffect() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new DrowsyPower(p, this.secondMagicNumber, false)));
    }
}