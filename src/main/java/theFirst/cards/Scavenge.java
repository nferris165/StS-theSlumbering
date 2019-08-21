package theFirst.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.SlumberingMod;
import theFirst.actions.CheckDiscardAction;
import theFirst.characters.TheSlumbering;

import static theFirst.SlumberingMod.makeCardPath;

public class Scavenge extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(Scavenge.class.getSimpleName());

    public static final String IMG = makeCardPath("A_temp.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 1;

    private static final int BLOCK = 10;

    private static final int DAMAGE = 10;

    private static final int MAGIC = 5;
    private static final int UPGRADE_MAGIC = -1;

    public Scavenge() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!this.upgraded){
            AbstractDungeon.actionManager.addToBottom(new CheckDiscardAction(p, m, magicNumber, magicNumber, block, damage, false, true));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new CheckDiscardAction(p, m, magicNumber, magicNumber, block, damage, true, true));
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