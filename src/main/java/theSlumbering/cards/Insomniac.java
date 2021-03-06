package theSlumbering.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;
import theSlumbering.patches.customTags;

import static theSlumbering.SlumberingMod.makeCardPath;

public class Insomniac extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(Insomniac.class.getSimpleName());

    public static final String IMG = makeCardPath("P_temp.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = -2;

    private static final int MAGIC = 2;

    public Insomniac() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.cardsToPreview = new Sleepwalk();

        tags.add(customTags.Passive);
    }

    @Override
    public void passiveEffect() {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(
                new Sleepwalk(), this.magicNumber, true, true));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void upgrade() {
    }
}