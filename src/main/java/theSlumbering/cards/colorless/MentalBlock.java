package theSlumbering.cards.colorless;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSlumbering.SlumberingMod;
import theSlumbering.cards.AbstractCustomCard;
import theSlumbering.characters.TheSlumbering;

import static theSlumbering.SlumberingMod.makeCardPath;

public class MentalBlock extends AbstractCustomCard {


    public static final String ID = SlumberingMod.makeID(Detonator.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.STATUS;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    public MentalBlock() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.purgeOnUse = true;
        this.dontTriggerOnUseCard = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}