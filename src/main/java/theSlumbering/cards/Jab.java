package theSlumbering.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;

import static theSlumbering.SlumberingMod.makeCardPath;

public class Jab extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(Jab.class.getSimpleName());

    public static final String IMG = makeCardPath("A_temp.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 0;
    private static final int U_COST = 0;

    private static final int DAMAGE = 3;
    private static final int UPGRADE_PLUS_DMG = 2;

    public Jab() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        this.isInnate = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            //upgradeBaseCost(U_COST);
            //this.rawDescription = updated_desc;
            initializeDescription();
        }
    }
}