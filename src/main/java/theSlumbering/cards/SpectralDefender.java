package theSlumbering.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;
import theSlumbering.patches.customTags;
import theSlumbering.powers.DoubleBlockPower;
import theSlumbering.relics.SlumberingRelic;

import static theSlumbering.SlumberingMod.makeCardPath;

public class SpectralDefender extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(SpectralDefender.class.getSimpleName());

    public static final String IMG = makeCardPath("P_temp.png");

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = -2;

    private static final int MAGIC = 50;

    public SpectralDefender() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        tags.add(customTags.Passive);

        updateMagic();

    }

    private void updateMagic(){
        if(CardCrawlGame.dungeon != null
                && AbstractDungeon.player != null
                && AbstractDungeon.player.hasRelic(SlumberingRelic.ID)){
            magicNumber = baseMagicNumber = AbstractDungeon.player.getRelic(SlumberingRelic.ID).counter * 3;
        } else {
            baseMagicNumber = magicNumber = MAGIC;
        }
        initializeDescription();
    }

    @Override
    public void passiveEffect() {
        AbstractPlayer p = AbstractDungeon.player;
        updateMagic();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DoubleBlockPower(p, this.magicNumber), this.magicNumber));
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