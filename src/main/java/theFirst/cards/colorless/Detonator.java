package theFirst.cards.colorless;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.SlumberingMod;
import theFirst.cards.AbstractCustomCard;

import static theFirst.SlumberingMod.makeCardPath;

public class Detonator extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(Detonator.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.STATUS;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;
    private final int explodeDamage = 2;
    private final int upgradeDamage = 1;

    public Detonator() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.purgeOnUse = true;
        this.dontTriggerOnUseCard = true;
        this.baseDamage = damage = explodeDamage;

        this.isMultiDamage = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        //TODO: bomb effect?
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SMASH));
    }

    @Override
    public void triggerWhenDrawn() {
        /* DEPRECATED
        if (AbstractDungeon.player.hasPower("Evolve") && !AbstractDungeon.player.hasPower("No Draw")) {
            AbstractDungeon.player.getPower("Evolve").flash();
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, AbstractDungeon.player.getPower("Evolve").amount));
        }
        */

        this.applyPowers();
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, false));
    }


    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(upgradeDamage);
            initializeDescription();
        }
        else{
            upgradeDamage(upgradeDamage);
        }
    }
}