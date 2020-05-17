package theSlumbering.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSlumbering.SlumberingMod;
import theSlumbering.actions.ImagineAction;
import theSlumbering.characters.TheSlumbering;

import static theSlumbering.SlumberingMod.makeCardPath;

public class ImagineSkill extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(ImagineSkill.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 2;

    public ImagineSkill() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.check = false;
    }

    public void drawAndCheck(AbstractPlayer p, boolean refund){
        if (AbstractDungeon.player.drawPile.isEmpty()) {
            AbstractDungeon.actionManager.addToBottom(new EmptyDeckShuffleAction());
        }
        if(refund){
            AbstractDungeon.actionManager.addToBottom(new ImagineAction(CardType.SKILL, this.cost, this));
        }
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        drawAndCheck(p, true);
        if(this.upgraded || !this.check){
            drawAndCheck(p,true);
        } else{
            drawAndCheck(p, false);
        }
        this.check = false;
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