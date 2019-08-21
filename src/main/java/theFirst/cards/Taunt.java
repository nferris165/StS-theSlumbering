package theFirst.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theFirst.SlumberingMod;
import theFirst.characters.TheSlumbering;

import static theFirst.SlumberingMod.makeCardPath;

public class Taunt extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(Taunt.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 2;

    private static final int BLOCK = 20;
    private static final int UPGRADE_BLOCK = 10;

    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = -1;

    public Taunt() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();

            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (!monster.isDead && !monster.isDying) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new StrengthPower(monster, this.magicNumber), this.magicNumber));
                }
            }
        }

        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}