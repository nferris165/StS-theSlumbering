package theFirst.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.SlumberingMod;
import theFirst.characters.TheSlumbering;
import theFirst.patches.customTags;

import static theFirst.SlumberingMod.makeCardPath;

public class BasicDefend extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(BasicDefend.class.getSimpleName());

    public static final String IMG = makeCardPath("BasicDefend.png");


    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 1;

    private static final int BLOCK = 3;
    private static final int UPGRADE_BLOCK = 3;

    public BasicDefend() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = BLOCK;

        //tags
        this.tags.add(BaseModCardTags.BASIC_DEFEND);
        this.tags.add(customTags.Basic);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));

    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            initializeDescription();
        }
    }
}