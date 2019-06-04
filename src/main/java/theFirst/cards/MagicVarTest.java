package theFirst.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theFirst.FirstMod;
import theFirst.characters.TheFirst;

import static theFirst.FirstMod.makeCardPath;

public class MagicVarTest extends AbstractCustomCard {


    public static final String ID = FirstMod.makeID(MagicVarTest.class.getSimpleName());
    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheFirst.Enums.COLOR_FIRST;

    private static final int COST = 1;
    private static final int VALUE = 4;
    private static final int UPGRADE_BONUS = 5;


    public MagicVarTest() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        baseDamage = VALUE;
        SecondMagicNumber = baseSecondMagicNumber = VALUE;

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeSecondMagicNumber(UPGRADE_BONUS);
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int calc = EnergyPanel.totalCount * damage;

            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, calc, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }
}