package theFirst.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.SlumberingMod;
import theFirst.actions.CheckBoundAction;
import theFirst.characters.TheSlumbering;
import theFirst.powers.BoundPower;

import static theFirst.SlumberingMod.makeCardPath;

public class BindingBlow extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(BindingBlow.class.getSimpleName());

    public static final String IMG = makeCardPath("A_temp.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 1;

    private static final int DAMAGE = 15;
    private static final int UPGRADE_PLUS_DMG = 10;

    public static boolean multipleBound = false;

    public BindingBlow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }

    @Override
    public void triggerWhenDrawn() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new CheckBoundAction(this));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));

        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, BoundPower.POWER_ID));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(multipleBound){
            if(this.isGlowing){
                this.stopGlowing();
            }
            this.cantUseMessage = ext_desc[0];
            return false;
        }
        return super.canUse(p, m);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}