package theSlumbering.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;
import theSlumbering.powers.DrowsyPower;

import static theSlumbering.SlumberingMod.makeCardPath;

public class NocturnalMyoclonus extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(NocturnalMyoclonus.class.getSimpleName());

    public static final String IMG = makeCardPath("A_temp.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 2;

    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 4;

    public NocturnalMyoclonus() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        if(p.hasPower(DrowsyPower.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.damage));
        }

        if(this.upgraded){
            AbstractDungeon.actionManager.addToBottom(new PlayTopCardAction(m, false));
        }
    }



    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            this.rawDescription = this.updated_desc;
            initializeDescription();
        }
    }
}