package theSlumbering.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;
import theSlumbering.patches.customTags;

import static theSlumbering.SlumberingMod.makeCardPath;

public class WokeDefend extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(WokeDefend.class.getSimpleName());
    public static final String IMG = makeCardPath("WokeDefend.png");

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int BLOCK = 8;
    private static final int UPGRADE_BLOCK = 4;

    public WokeDefend() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = BLOCK;

        //tags
        this.tags.add(BaseModCardTags.BASIC_DEFEND);
        this.tags.add(customTags.Woke);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, block)));

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}