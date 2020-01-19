package theSlumbering.relics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;
import theSlumbering.powers.DrowsyPower;
import theSlumbering.util.TextureLoader;

import static theSlumbering.SlumberingMod.*;

public class SlumberingRelic extends AbstractCustomRelic implements ClickableRelic {

    public static final String ID = SlumberingMod.makeID("SlumberingRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("zzz.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("zzz.png"));

    private static final float max_hp_ratio = 0.1F;
    private boolean firstElite;


    public SlumberingRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);

        if(difficultyNormal){
            this.counter = 5;
        } else{
            this.counter = 6;
        }
        if(AbstractDungeon.ascensionLevel >= 14){
            this.counter--;
        }

        if(CardCrawlGame.dungeon != null){
            checkWake();
        }
        this.floatCounter = 0;
    }

    @Override
    public void setCounter(int counter) {
        checkWake();
        super.setCounter(counter);
    }

    @Override
    public int getState(){
        if(this.counter < 0){
            return 1;
        }
        else if(this.counter == 0){
            return 2;
        }
        else if(this.counter < 8){
            return 3;
        }
        else if(this.counter < 14){
            return 4;
        }
        else if(this.counter < 25){
            return 5;
        }
        else if (this.counter < 30){
            return 6;
        }
        else //if(this.counter >= 20)
        {
            return 6;
        }
    }

    @Override
    public String getUpdatedDescription() {
        int s = getState();
        return DESCRIPTIONS[s] + DESCRIPTIONS[0];

    }

    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c) {

        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    public void checkWake(){
        int s = getState();
        updateDescription(TheSlumbering.Enums.THE_SLUMBERING);
        switch(s)
        {
            case 1 :
                //effect 1
                break;
            case 2 :
                this.img = ImageMaster.loadImage(makeRelicPath("zzz.png"));
                break;
            case 3 :
                this.img = ImageMaster.loadImage(makeRelicPath("zzz2.png"));
                break;
            case 4 :
                this.img = ImageMaster.loadImage(makeRelicPath("zzz3.png"));
                break;
            case 5 :
                this.img = ImageMaster.loadImage(makeRelicPath("zzz4.png"));
                break;
            case 6:
                this.img = ImageMaster.loadImage(makeRelicPath("zzz5.png"));
                break;
            default :
                break;
        }

    }

    public void checkFloat() {
        while(this.floatCounter >= 1.0F){
            this.floatCounter -= 1.0F;
            onTrigger();
        }

        while(this.floatCounter <= -1.0F){
            this.floatCounter += 1.0F;
            onTrigger(-1);
        }
    }

    @Override
    public void floatCount(){

    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        if (m.currentHealth <= 0 && firstElite && (m.type == AbstractMonster.EnemyType.ELITE || m.type == AbstractMonster.EnemyType.BOSS)) {
            this.flash();
            this.firstElite = false;
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(m, this));
            onTrigger(1);
        }
    }

    @Override
    public void atBattleStartPreDraw() {
        this.firstElite = true;
    }

    @Override
    public void atBattleStart() {
        if(getState() == 6){
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 5));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new DexterityPower(AbstractDungeon.player, 1), 1));
        }
    }

    @Override
    public void onTrigger(){
        onTrigger(1);
    }

    @Override
    public void onTrigger(int amt){
        this.flash();
        this.counter += amt;
        if(this.counter < 0) {
            this.counter = 0;
            AbstractDungeon.overlayMenu.endTurnButton.disable(true);
        }
        checkWake();
    }

    @Override
    public void onTriggerFloat(int amt)
    {
        this.floatCounter += ((float)amt * max_hp_ratio);
        checkFloat();
    }

    @Override
    public void onRightClick() {

        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT
                && AbstractDungeon.player.hasPower(DrowsyPower.POWER_ID)) {
            AbstractCreature p = AbstractDungeon.player;
            int amt = AbstractDungeon.player.getPower(DrowsyPower.POWER_ID).amount;
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, DrowsyPower.POWER_ID));
            //onTrigger(-amt);
            onTrigger(-1);
        }
    }
}
