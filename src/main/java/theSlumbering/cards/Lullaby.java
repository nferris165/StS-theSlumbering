package theSlumbering.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;


import static theSlumbering.SlumberingMod.makeCardPath;

public class Lullaby extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(Lullaby.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 1;

    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;

    private static final int MAGIC2 = 1;


    public Lullaby() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.exhaust = true;

        baseSecondMagicNumber = secondMagicNumber = MAGIC2;
        baseMagicNumber = magicNumber = MAGIC;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();

            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (!monster.isDead && !monster.isDying) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new StrengthPower(monster, -this.magicNumber), -this.magicNumber));
                    if(this.upgraded) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new WeakPower(monster, this.secondMagicNumber, false), this.secondMagicNumber));
                    }
                }
            }
        }
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            this.rawDescription = this.updated_desc;
            initializeDescription();
        }
    }
}