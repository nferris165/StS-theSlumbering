package theFirst.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.FirstMod;
import theFirst.characters.TheFirst;
import theFirst.patches.customTags;

import static theFirst.FirstMod.makeCardPath;

public class RestedPunch extends AbstractCustomCard {

    public static final String ID = FirstMod.makeID(RestedPunch.class.getSimpleName());

    public static final String IMG = makeCardPath("A_temp.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheFirst.Enums.COLOR_FIRST;

    private static final int COST = 2;

    private static final int DAMAGE = 5;
    private static final int UPGRADE_PLUS_DMG = 5;

    private static final int MAGIC = 4;


    public RestedPunch() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;

        this.tags.add(customTags.Snooze);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        if(!this.upgraded){
            this.baseDamage = DAMAGE;
            this.isDamageModified = false;
        }
        else{
            this.baseDamage = DAMAGE + UPGRADE_PLUS_DMG;
        }
    }

    @Override
    public void triggerWhenDrawn() {
        this.baseDamage += magicNumber;
        this.isDamageModified = true;
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