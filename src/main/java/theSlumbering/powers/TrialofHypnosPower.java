package theSlumbering.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import theSlumbering.SlumberingMod;
import theSlumbering.cards.*;
import theSlumbering.util.TextureLoader;

public class TrialofHypnosPower extends AbstractCustomPower implements CloneablePowerInterface {
    @SuppressWarnings("WeakerAccess")

    public static final String POWER_ID = SlumberingMod.makeID("TrialofHypnosPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theFirstResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theFirstResources/images/powers/placeholder_power32.png");

    private int trialType;
    private int gold;
    private boolean mightTrial, speedTrial, brainTrial, patienceTrial;
    private boolean upgraded;

    private int dThresh = 30;
    private int bThresh = 50;
    private int cThresh = 5;
    private int pThresh = 1;

    private int upMod = 0;

    public TrialofHypnosPower(final AbstractCreature owner, int trialType, int gold, boolean upgraded) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.trialType = trialType;
        this.gold = gold;
        this.upgraded = upgraded;

        this.mightTrial = false;
        this.speedTrial = false;
        this.brainTrial = false;
        this.patienceTrial = false;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        if(this.upgraded){
            upMod = 2;
            dThresh = 50;
            bThresh = 80;
            cThresh = 7;
            pThresh = 0;
        }

        updateDescription();
    }

    //TODO: Better victory effect
    private void mightTrial(boolean init){
        //Task: Play an attack with at least 30 damage
        if(init){
            this.mightTrial = true;
        }
        else{
            AbstractDungeon.player.gainGold(this.gold * (3 + upMod));
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
            AbstractDungeon.player.gainGold(this.gold * (3 + upMod));
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
            AbstractDungeon.player.gainGold(this.gold * (3 + upMod));
            this.flash();
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(new TrialsofHypnos()));
            AbstractDungeon.effectsQueue.add(new RainingGoldEffect(gold + 150));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    private void patienceTrial(boolean init){
        //Task: End two turns having played 1 or fewer cards
        if(init){
            this.patienceTrial = true;
        }
        else{
            AbstractDungeon.player.gainGold(this.gold * (3 + upMod));
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
            case 3:
                patienceTrial(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void updateDescription() {
        switch (trialType){
            case 0:
                description = DESCRIPTIONS[0] + dThresh + DESCRIPTIONS[1];
                break;
            case 1:
                description = DESCRIPTIONS[2] + bThresh + DESCRIPTIONS[3];
                break;
            case 2:
                description = DESCRIPTIONS[4] + cThresh + DESCRIPTIONS[5];
                break;
            case 3:
                description = DESCRIPTIONS[6] + pThresh + DESCRIPTIONS[7];
                break;
            default:
                description = "Error.";
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
    public void atEndOfTurn(boolean isPlayer) {
        if(!owner.isPlayer){
            return;
        }
        AbstractPlayer p = (AbstractPlayer) owner;
        if(this.patienceTrial && p.cardsPlayedThisTurn <= pThresh){
            patienceTrial(false);
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new TrialofHypnosPower(owner, trialType, gold, upgraded);
    }
}
