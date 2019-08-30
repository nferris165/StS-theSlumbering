package theSlumbering.cards.Curses;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSlumbering.SlumberingMod;
import theSlumbering.cards.AbstractCustomCard;
import theSlumbering.patches.customTags;
import theSlumbering.powers.DoubtPower;

import static theSlumbering.SlumberingMod.makeCardPath;

public class LingeringDoubt extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(LingeringDoubt.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.CURSE;
    public static final CardColor COLOR = CardColor.CURSE;

    private static final int COST = 1;

    private static final int HP = 2;

    public LingeringDoubt(){
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.baseSecondMagicNumber = this.secondMagicNumber = HP;
        this.tags.add(customTags.ActionCurse);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, DoubtPower.POWER_ID));
    }

    @Override
    public void upgrade() {
    }

    @Override
    public void passiveEffect() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DoubtPower(p, this.secondMagicNumber)));
    }
}