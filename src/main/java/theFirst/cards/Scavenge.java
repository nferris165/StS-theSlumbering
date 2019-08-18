package theFirst.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.FirstMod;
import theFirst.actions.CheckDiscardAction;
import theFirst.characters.TheFirst;

import static theFirst.FirstMod.makeCardPath;

public class Scavenge extends AbstractCustomCard {

    public static final String ID = FirstMod.makeID(Scavenge.class.getSimpleName());

    public static final String IMG = makeCardPath("A_temp.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheFirst.Enums.COLOR_FIRST;

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