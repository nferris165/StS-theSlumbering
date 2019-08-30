package theSlumbering.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import theSlumbering.SlumberingMod;
import theSlumbering.cards.Curses.Seed;
import theSlumbering.characters.TheSlumbering;

import static theSlumbering.SlumberingMod.makeCardPath;

public class TheSeed extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(TheSeed.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;


    public TheSeed() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.purgeOnUse = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Seed(), MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH, MathUtils.random(0.2F, 0.8F) * (float) Settings.HEIGHT));
        //AbstractDungeon.player.masterDeck.addToRandomSpot(new Seed());
        AbstractDungeon.player.masterDeck.removeCard(this.cardID);
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