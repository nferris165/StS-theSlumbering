package theFirst.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theFirst.FirstMod;
import theFirst.characters.TheFirst;
import theFirst.powers.AbstractCustomPower;
import theFirst.powers.WakingPower;

import static theFirst.FirstMod.makeCardPath;
import static theFirst.FirstMod.makeID;

public class Stretch extends AbstractCustomCard {

    public static final String ID = FirstMod.makeID(Stretch.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheFirst.Enums.COLOR_FIRST;

    private static final int COST = 1;

    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DMG = 5;

    public Stretch() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(
                m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));


        String powerID = makeID("WakingPower");

        if(AbstractDungeon.player.hasPower(powerID)){
            AbstractCustomPower pow = (AbstractCustomPower) AbstractDungeon.player.getPower(powerID);
            if(!pow.powerCheck) {
                pow.onSpecificTrigger();
                //TODO: wake up
            }
        }
        else{
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WakingPower(p, this)));
        }
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