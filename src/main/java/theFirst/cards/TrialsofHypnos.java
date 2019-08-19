package theFirst.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theFirst.FirstMod;
import theFirst.actions.TrialSelectAction;
import theFirst.characters.TheFirst;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theFirst.FirstMod.makeCardPath;

public class TrialsofHypnos extends AbstractCustomCard {

    public static final String ID = FirstMod.makeID(TrialsofHypnos.class.getSimpleName());

    public static final String IMG = makeCardPath("P_temp.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheFirst.Enums.COLOR_FIRST;

    private String baseDesc = languagePack.getCardStrings(ID).DESCRIPTION;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

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
        AbstractDungeon.actionManager.addToBottom(new TrialSelectAction(p, (int)(p.gold*0.1)));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            if(CardCrawlGame.dungeon == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT){
                this.rawDescription = this.updated_desc;
            }
            initializeDescription();
        }
    }
}