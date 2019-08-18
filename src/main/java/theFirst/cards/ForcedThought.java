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
import theFirst.actions.CountAction;
import theFirst.characters.TheFirst;

import java.util.List;

import static theFirst.FirstMod.makeCardPath;

public class ForcedThought extends AbstractCustomCard  implements ModalChoice.Callback{

    public static final String ID = FirstMod.makeID(ForcedThought.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheFirst.Enums.COLOR_FIRST;

    private ModalChoice modal;

    private static final int COST = 1;

    public ForcedThought() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        modal = new ModalChoiceBuilder()
                .setCallback(this)
                .setColor(CardColor.BLUE)
                .addOption(ext_desc[0], CardTarget.SELF)
                .setColor(CardColor.RED)
                .addOption(ext_desc[1], CardTarget.SELF)
                .setColor(CardColor.GREEN)
                .addOption(ext_desc[2], CardTarget.SELF)
                .create();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        modal.open();
    }

    @Override
    public void optionSelected(AbstractPlayer p, AbstractMonster m, int i) {
        switch (i) {
            case 0:
                AbstractDungeon.actionManager.addToBottom(new CountAction(false, CardType.POWER));
                break;
            case 1:
                AbstractDungeon.actionManager.addToBottom(new CountAction(false, CardType.ATTACK));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new CountAction(false, CardType.SKILL));
                break;
            default:
                return;
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}