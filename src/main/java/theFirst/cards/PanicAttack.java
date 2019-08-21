package theFirst.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.SlumberingMod;
import theFirst.characters.TheSlumbering;

import static theFirst.SlumberingMod.makeCardPath;

public class PanicAttack extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(PanicAttack.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    public PanicAttack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractMonster monster: AbstractDungeon.getMonsters().monsters){
            AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(monster, p));
        }

        AbstractDungeon.actionManager.addToBottom(new AddCardToDeckAction(CardLibrary.getCurse().makeCopy()));
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