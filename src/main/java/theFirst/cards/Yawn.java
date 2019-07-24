package theFirst.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.FirstMod;
import theFirst.characters.TheFirst;
import theFirst.powers.AbstractCustomPower;
import theFirst.powers.WakingPower;

import static theFirst.FirstMod.makeCardPath;
import static theFirst.FirstMod.makeID;

public class Yawn extends AbstractCustomCard {

    public static final String ID = FirstMod.makeID(Yawn.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheFirst.Enums.COLOR_FIRST;

    private static final int COST = 1;

    private static final int BLOCK = 10;
    private static final int UPGRADE_BLOCK = 5;


    public Yawn() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        this.exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));

        String powerID = makeID("WakingPower");

        if(AbstractDungeon.player.hasPower(powerID)){
            AbstractCustomPower pow = (AbstractCustomPower) AbstractDungeon.player.getPower(powerID);
            if(pow.powerCheck) {
                pow.onSpecificTrigger();
            }
        }
        else{
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WakingPower(p, this)));
        }
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