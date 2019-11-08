package theSlumbering.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;
import theSlumbering.patches.customTags;
import theSlumbering.powers.ToughSkinPower;
import theSlumbering.relics.SlumberingRelic;

import static theSlumbering.SlumberingMod.makeCardPath;

public class ToughSkin extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(ToughSkin.class.getSimpleName());

    public static final String IMG = makeCardPath("P_temp.png");

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = -2;

    private static final int MAGIC = 5;

    public ToughSkin() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        updateMagic();

        tags.add(customTags.Passive);
    }

    private void updateMagic(){
        if(CardCrawlGame.dungeon != null && AbstractDungeon.player.hasRelic(SlumberingRelic.ID)){
            magicNumber = baseMagicNumber = AbstractDungeon.player.getRelic(SlumberingRelic.ID).counter;
        } else {
            baseMagicNumber = magicNumber = MAGIC;
        }
    }

    @Override
    public void passiveEffect() {
        AbstractPlayer p = AbstractDungeon.player;
        updateMagic();
        if(!p.hasPower(ToughSkinPower.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ToughSkinPower(p, this.magicNumber)));
        }
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