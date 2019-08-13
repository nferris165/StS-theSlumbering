package theFirst.cards.colorless;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.FirstMod;
import theFirst.cards.AbstractCustomCard;

import static theFirst.FirstMod.makeCardPath;

public class StoneHearth extends AbstractCustomCard {

    public static final String ID = FirstMod.makeID(StoneHearth.class.getSimpleName());

    public static final String IMG = makeCardPath("A_temp.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 1;

    private static final int DAMAGE = 2;
    private static final int UPGRADE_PLUS_DMG = 3;

    private static final int MAGIC = 7;
    private static final int UP_MAGIC = 5;

    public StoneHearth() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;

        baseMagicNumber = magicNumber = MAGIC;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int random = AbstractDungeon.miscRng.random(0, this.magicNumber);
        int val = damage + random;

        AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(
                new DamageInfo(p, val, damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, val));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UP_MAGIC);
            initializeDescription();
        }
    }
}