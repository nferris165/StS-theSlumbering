package theSlumbering.cards;

import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSlumbering.SlumberingMod;
import theSlumbering.actions.CountAction;
import theSlumbering.characters.TheSlumbering;

import static theSlumbering.SlumberingMod.makeCardPath;

public class ForcedThought extends AbstractCustomCard  implements ModalChoice.Callback{

    public static final String ID = SlumberingMod.makeID(ForcedThought.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private ModalChoice modal;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

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
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}