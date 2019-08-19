package theFirst.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import theFirst.FirstMod;
import theFirst.cards.*;
import theFirst.util.TextureLoader;

public class TrialofHypnosPower extends AbstractCustomPower implements CloneablePowerInterface {
    @SuppressWarnings("WeakerAccess")

    public static final String POWER_ID = FirstMod.makeID("TrialofHypnosPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theFirstResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theFirstResources/images/powers/placeholder_power32.png");

    private int trialType;
    private int gold;
    private boolean mightTrial, speedTrial, brainTrial;

    private int dThresh = 30;
    private int bThresh = 50;
    private int cThresh = 5;

    public TrialofHypnosPower(final AbstractCreature owner, int trialType, int gold) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.trialType = trialType;
        this.gold = gold;

        this.mightTrial = false;
        this.speedTrial = false;
        this.brainTrial = false;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    //TODO: Better victory effect
    private void mightTrial(boolean init){
        //Task: Play an attack with at least 30 damage
        if(init){
            this.mightTrial = true;
        }
        else{
            AbstractDungeon.player.gainGold(this.gold * 3);
            this.flash();
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(new TrialsofHypnos()));
            AbstractDungeon.effectsQueue.add(new RainingGoldEffect(gold + 150));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    private void brainTrial(boolean init){
        //Task: Have 50 or more Block
        if(init){
            this.brainTrial = true;
        }
        else{
            AbstractDungeon.player.gainGold(this.gold * 3);
            this.flash();
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(new TrialsofHypnos()));
            AbstractDungeon.effectsQueue.add(new RainingGoldEffect(gold + 150));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    private void speedTrial(boolean init){
        //Task: Play 5 cards in a turn
        if(init){
            this.speedTrial = true;
        }
        else{
            AbstractDungeon.player.gainGold(this.gold * 3);
            this.flash();
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(new TrialsofHypnos()));
            AbstractDungeon.effectsQueue.add(new RainingGoldEffect(gold + 150));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
        }

    }

    @Override
    public void onInitialApplication() {
        switch (trialType){
            case 0:
                mightTrial(true);
                break;
            case 1:
                brainTrial(true);
                break;
            case 2:
                speedTrial(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void updateDescription() {
        switch (trialType){
            case 0:
                description = DESCRIPTIONS[0];
                break;
            case 1:
                description = DESCRIPTIONS[1];
                break;
            case 2:
                description = DESCRIPTIONS[2];
                break;
            default:
                description = DESCRIPTIONS[0];
                break;
        }
    }

    @Override
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        if(this.mightTrial && damageAmount >= dThresh){
            mightTrial(false);
        }
        return damageAmount;
    }

    @Override
    public void onGainedBlock(float blockAmount) {
        if(this.brainTrial && (owner.currentBlock + blockAmount) >= bThresh){
            brainTrial(false);
        }
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        if(!owner.isPlayer){
            return;
        }
        AbstractPlayer p = (AbstractPlayer) owner;
        if(this.speedTrial && p.cardsPlayedThisTurn >= cThresh){
            speedTrial(false);
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new TrialofHypnosPower(owner, trialType, gold);
    }
}
