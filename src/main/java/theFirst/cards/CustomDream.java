package theFirst.cards;

import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.FirstMod;
import theFirst.characters.TheFirst;

import java.util.List;

import static theFirst.FirstMod.makeCardPath;

public class CustomDream extends AbstractCustomCard implements ModalChoice.Callback {

    public static final String ID = FirstMod.makeID(CustomDream.class.getSimpleName());

    public static final String IMG = makeCardPath("A_temp.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheFirst.Enums.COLOR_FIRST;

    private static final int COST = 1;
    private static final int UP_COST = 0;

    private static final int BLOCK = 8;

    private static final int DAMAGE = 9;
    private static final int ALL = 4;


    private ModalChoice modal;

    public CustomDream() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;

        modal = new ModalChoiceBuilder()
                .setCallback(this)
                .setColor(CardColor.BLUE)
                .addOption(ext_desc[0] + BLOCK + ext_desc[1], CardTarget.SELF)
                .setColor(CardColor.RED)
                .addOption(ext_desc[2] + DAMAGE + ext_desc[3], CardTarget.ENEMY)
                .setColor(CardColor.GREEN)
                .addOption(ext_desc[2] + ALL + ext_desc[4], CardTarget.ALL_ENEMY)
                .create();
    }

    @Override
    public List<TooltipInfo> getCustomTooltips()
    {
        return modal.generateTooltips();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        modal.open();
    }

    @Override
    public void optionSelected(AbstractPlayer p, AbstractMonster m, int i) {
        CardColor color;
        switch (i) {
            case 0:
                color = CardColor.BLUE;
                break;
            case 1:
                color = CardColor.RED;
                break;
            case 2:
                color = CardColor.GREEN;
                break;
            default:
                return;
        }

        AbstractCard c;
        if (color == CardColor.BLUE) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        } else if(color == CardColor.RED){
            this.isMultiDamage = false;
            this.baseDamage = DAMAGE;
            this.applyPowers();
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        } else{
            this.isMultiDamage = true;
            this.baseDamage = ALL;
            this.applyPowers();
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UP_COST);
            initializeDescription();
        }
    }
}