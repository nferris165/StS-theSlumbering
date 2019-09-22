package theSlumbering.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.OfferingEffect;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;

import static theSlumbering.SlumberingMod.makeCardPath;

public class WakeUpSlap extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(WakeUpSlap.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 0;

    private static final int DAMAGE = 3;
    private static final int UP_DAM = -2;

    private static final int STRENGTH = 2;
    private static final int UPGRADE_STR = 1;

    private static final int DRAW = 1;

    public WakeUpSlap() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = STRENGTH;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagicNumber = this.secondMagicNumber = DAMAGE;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new VFXAction(new OfferingEffect(), 0.5F));
        //AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, secondMagicNumber));
        p.damage(new DamageInfo(p, secondMagicNumber, DamageInfo.DamageType.NORMAL));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, DRAW));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_STR);
            upgradeSecondMagicNumber(UP_DAM);
            initializeDescription();
        }
    }
}