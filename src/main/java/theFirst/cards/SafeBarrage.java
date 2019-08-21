package theFirst.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.SlumberingMod;
import theFirst.characters.TheSlumbering;

import static theFirst.SlumberingMod.makeCardPath;

public class SafeBarrage extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(SafeBarrage.class.getSimpleName());

    public static final String IMG = makeCardPath("A_temp.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 1;

    private static final int DAMAGE = 3;

    private static final int MAGIC = 7;
    private static final int UPGRADE_PLUS_MAG = 3;

    public SafeBarrage() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < magicNumber; i++){
            AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(p.currentBlock < 50){
            return false;
        }
        return super.canUse(p, m);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAG);
            initializeDescription();
        }
    }
}