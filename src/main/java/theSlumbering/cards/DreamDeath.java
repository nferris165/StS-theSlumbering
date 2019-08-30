package theSlumbering.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.DarkSmokePuffEffect;
import theSlumbering.SlumberingMod;
import theSlumbering.actions.DeathAction;
import theSlumbering.characters.TheSlumbering;

import static theSlumbering.SlumberingMod.makeCardPath;

public class DreamDeath extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(DreamDeath.class.getSimpleName());

    public static final String IMG = makeCardPath("A_temp.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 2;

    private static final int DAMAGE = 15;
    private static final int UPGRADE_PLUS_DMG = 5;

    public DreamDeath() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new DarkSmokePuffEffect(m.hb.cX, m.hb.cY - 40.0F * Settings.scale)));
        }

        AbstractDungeon.actionManager.addToBottom(new DeathAction(m, new DamageInfo(p, damage, damageTypeForTurn), this.upgraded));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            this.rawDescription = updated_desc;
            initializeDescription();
        }
    }
}