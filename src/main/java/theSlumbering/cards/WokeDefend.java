package theSlumbering.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;
import theSlumbering.patches.ActionManagerPatch;
import theSlumbering.patches.customTags;
import theSlumbering.powers.DrowsyPower;

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

    private static final int BLOCK = 7;
    private static final int UPGRADE_BLOCK = 3;

    private static final int MAGIC = 1;
    private static final int UP_MAGIC = 1;

    public WokeDefend() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = BLOCK;
        magicNumber = baseMagicNumber = MAGIC;

        //tags
        this.tags.add(BaseModCardTags.BASIC_DEFEND);
        this.tags.add(customTags.Woke);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, DrowsyPower.POWER_ID, 1));
    }

    @Override
    public void applyPowers() {
        int bl;
        if(!this.upgraded){
            bl = BLOCK;
        } else{
            bl = BLOCK + UPGRADE_BLOCK;
        }
        this.baseBlock = bl + (this.magicNumber * ActionManagerPatch.snoozeCount.get(AbstractDungeon.actionManager));
        super.applyPowers();
        this.isBlockModified = this.block != bl;
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