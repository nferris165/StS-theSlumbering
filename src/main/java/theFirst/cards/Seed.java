package theFirst.cards;

import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import theFirst.FirstMod;
import theFirst.actions.SeedAction;

import java.util.Iterator;

import static theFirst.FirstMod.makeCardPath;

public class Seed extends AbstractCustomCard {

    public static final String ID = FirstMod.makeID(Seed.class.getSimpleName());

    public static final String IMG = makeCardPath("S_temp.png");

    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.CURSE;
    public static final CardColor COLOR = CardColor.CURSE;

    private static final int COST = -2;

    public Seed(){
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.misc = 0;
        this.baseMagicNumber = this.magicNumber = this.misc;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasRelic("Blue Candle")) {
            this.useBlueCandle(p);
        }
    }

    @Override
    public void triggerWhenDrawn() {
        AbstractDungeon.actionManager.addToBottom(new SetDontTriggerAction(this, false));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return true;
    }

    @Override
    public void triggerOnExhaust() {
        AbstractDungeon.actionManager.addToBottom(new SeedAction(this.uuid, 1));
    }

    @Override
    public void applyPowers() {
        this.baseMagicNumber = this.misc;
        super.applyPowers();
    }

    @Override
    public void upgrade() {
    }
}