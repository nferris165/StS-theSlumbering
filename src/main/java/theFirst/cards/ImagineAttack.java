package theFirst.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.SlumberingMod;
import theFirst.actions.ImagineAction;
import theFirst.characters.TheSlumbering;

import static theFirst.SlumberingMod.makeCardPath;

public class ImagineAttack extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(ImagineAttack.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 2;

    public ImagineAttack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    public void drawAndCheck(AbstractPlayer p){
        if (AbstractDungeon.player.drawPile.isEmpty()) {
            AbstractDungeon.actionManager.addToBottom(new EmptyDeckShuffleAction());
        }
        AbstractDungeon.actionManager.addToBottom(new ImagineAction(CardType.ATTACK, this.cost, this));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        drawAndCheck(p);
        if(this.upgraded){
            drawAndCheck(p);
        }
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