package theSlumbering.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;
import theSlumbering.patches.customTags;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theSlumbering.SlumberingMod.makeCardPath;

public class Hallucinations extends AbstractCustomCard {

    public static final String ID = SlumberingMod.makeID(Hallucinations.class.getSimpleName());

    public static final String IMG = makeCardPath("A_temp.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheSlumbering.Enums.COLOR_SLUMBERING;

    private static final int COST = 2;

    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DMG = 3;

    private boolean first;

    public Hallucinations() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseSecondMagicNumber = UPGRADE_PLUS_DMG;
        this.first  = false;
        //this.upgraded = true;
        this.tags.add(customTags.Snooze);
    }

    @Override
    public void triggerOnGlowCheck() {
        if(!this.first && isGlowing){
            glowColor = Color.GOLD;
        }
        else{
            glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
        }
        super.triggerOnGlowCheck();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!this.first){
            this.first = true;
            this.upgrade();

            for(AbstractCard card: p.masterDeck.group){
                if(card.uuid == this.uuid){
                    card.upgrade();
                }
            }
        }
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.POISON));
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {
        upgradeDamage(UPGRADE_PLUS_DMG);
        ++this.timesUpgraded;
        this.name = languagePack.getCardStrings(ID).NAME + " + " + this.timesUpgraded;
        this.initializeTitle();
        initializeDescription();
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard card = super.makeStatEquivalentCopy();

        ((Hallucinations) card).first = this.first;

        return card;
    }
}
