package theFirst.cards.colorless;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.FirstMod;
import theFirst.actions.BombTossAction;
import theFirst.cards.AbstractCustomCard;

import static theFirst.FirstMod.makeCardPath;

public class BombToss extends AbstractCustomCard {

    public static final String ID = FirstMod.makeID(BombToss.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -1;

    public BombToss() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new BombTossAction(p, this.energyOnUse, this.upgraded, this.freeToPlayOnce));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}