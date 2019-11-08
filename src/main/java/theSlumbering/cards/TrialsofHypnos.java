package theSlumbering.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theSlumbering.SlumberingMod;
import theSlumbering.actions.TrialSelectAction;
import theSlumbering.characters.TheSlumbering;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theSlumbering.SlumberingMod.makeCardPath;

public class TrialsofHypnos extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(TrialsofHypnos.class.getSimpleName());

    public static final String IMG = makeCardPath("P_temp.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private String baseDesc = languagePack.getCardStrings(ID).DESCRIPTION;

    private static final int COST = 1;
    //private static final int UPGRADED_COST = 1;

    public TrialsofHypnos() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.exhaust = true;
    }

    @Override
    public void triggerWhenDrawn() {
        this.rawDescription = baseDesc;
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        AbstractCard c = super.makeCopy();
        c.rawDescription = baseDesc;
        c.initializeDescription();
        return c;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard c = super.makeStatEquivalentCopy();
        c.rawDescription = baseDesc;
        c.initializeDescription();
        return c;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new TrialSelectAction(p, (int)(p.gold*0.1), this.upgraded));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            //upgradeBaseCost(UPGRADED_COST);
            if(CardCrawlGame.dungeon == null || AbstractDungeon.currMapNode == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT){
                this.rawDescription = this.updated_desc;
            }
            initializeDescription();
        }
    }
}