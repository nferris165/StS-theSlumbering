package theSlumbering.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;
import theSlumbering.patches.customTags;

import static theSlumbering.SlumberingMod.makeCardPath;

public class WokeAttack extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(WokeAttack.class.getSimpleName());
    public static final String IMG = makeCardPath("WokeAttack.png");

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int BLOCK = 3;
    private static final int UPGRADE_BLOCK = 2;

    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 3;

    public WokeAttack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        this.isMultiDamage = true;

        //tags
        this.tags.add(BaseModCardTags.BASIC_STRIKE);
        this.tags.add(customTags.Woke);

    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FocusPower(p, 30), 30));//
        /*AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL)); */
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));

    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBlock(UPGRADE_BLOCK);
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}