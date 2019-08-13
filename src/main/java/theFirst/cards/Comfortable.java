package theFirst.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import theFirst.FirstMod;
import theFirst.characters.TheFirst;

import static com.megacrit.cardcrawl.cards.green.Reflex.EXTENDED_DESCRIPTION;
import static theFirst.FirstMod.makeCardPath;

public class Comfortable extends AbstractCustomCard {

    public static final String ID = FirstMod.makeID(Comfortable.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheFirst.Enums.COLOR_FIRST;

    private static final int COST = -2;

    public Comfortable() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void triggerWhenDrawn() {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));

        if(upgraded){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new ArtifactPower(AbstractDungeon.player, 1), 1));
        }
    }

    @Override
    public void triggerOnManualDiscard() {
        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(
                this, AbstractDungeon.player.discardPile));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = this.updated_desc;
            initializeDescription();
        }
    }
}