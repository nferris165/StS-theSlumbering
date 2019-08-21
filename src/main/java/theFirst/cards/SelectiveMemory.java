package theFirst.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.SlumberingMod;
import theFirst.actions.PurgeAction;
import theFirst.characters.TheSlumbering;

import static theFirst.SlumberingMod.makeCardPath;

public class SelectiveMemory extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(SelectiveMemory.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 0;

    public SelectiveMemory() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.purgeOnUse = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new PurgeAction(p.masterDeck.findCardById(this.cardID), true));
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(p.hand.isEmpty()){
            this.cantUseMessage = ext_desc[0];
            return false;
        }
        return super.canUse(p, m);
    }

    @Override
    public void upgrade() {
    }
}