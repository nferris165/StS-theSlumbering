package theFirst.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theFirst.SlumberingMod;
import theFirst.util.TextureLoader;

import static theFirst.SlumberingMod.*;

public class GamblerFolly extends AbstractCustomRelic {

    public static final String ID = SlumberingMod.makeID("GamblerFolly");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GamblerFolly.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GamblerFolly.png"));
    private final int curseVal = 5;
    private final int boonVal = 15;
    private boolean gaining = false;


    public GamblerFolly() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);

        this.counter = -1;
        this.floatCounter = 0;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        if(this.counter == -2) { //boon
            return DESCRIPTIONS[1];
        }
        else if(this.counter == -3){ //curse
            //return DESCRIPTIONS[2] + "#b" + curseVal + DESCRIPTIONS[4];
            return DESCRIPTIONS[2];
        }
        else if(this.counter == -4){ //jackpot
            return DESCRIPTIONS[3];
        }
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        int curse = AbstractDungeon.miscRng.random(0,99);
        if(curse < 50){
            this.counter = -3;
            //logger.info("cursed\n\n");
            this.img = ImageMaster.loadImage(makeRelicPath("GamblerCURSE.png"));
            updateDescription(AbstractDungeon.player.chosenClass);
        }
        else if(curse == 99){
            this.img = ImageMaster.loadImage(makeRelicPath("GamblerJACKPOT.png"));
            this.counter = -4;
            updateDescription(AbstractDungeon.player.chosenClass);
            AbstractDungeon.player.gainGold(100 + AbstractDungeon.miscRng.random(-50,50));
        }
        else{
            //logger.info("not cursed\n\n");
            this.counter = -2;
            this.img = ImageMaster.loadImage(makeRelicPath("GamblerBOON.png"));
            updateDescription(AbstractDungeon.player.chosenClass);
        }
    }

    @Override
    public void setCounter(int newCounter){
        this.counter = newCounter;
        //logger.info(this.counter + "\n\n");
        if (this.counter == -2) {
            this.img = ImageMaster.loadImage(makeRelicPath("GamblerBOON.png"));
            this.counter = -2;
        }
        else if(this.counter == -3){
            this.img = ImageMaster.loadImage(makeRelicPath("GamblerCURSE.png"));
            this.counter = -3;
        }
        else if(this.counter == -4){
            this.img = ImageMaster.loadImage(makeRelicPath("GamblerJACKPOT.png"));
            this.counter = -4;
        }
    }

    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c) {

        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    public void gamble(){
        if(this.counter == -3) {
            flash();
            AbstractDungeon.player.loseGold(curseVal + AbstractDungeon.miscRng.random(-3, 4));
        }
        else{
            //gaining = true;
            //AbstractDungeon.player.gainGold(boonVal + AbstractDungeon.miscRng.random(-1, 4));
            //gaining = false;
        }
    }

    @Override
    public void onGainGold() {
        if(!gaining) {
            gamble();
        }
    }

    @Override
    public void onChestOpenAfter(boolean bossChest) {
        int odds = AbstractDungeon.miscRng.random(1, 1000);

        if(this.counter == -2) {
            flash();
            AbstractDungeon.getCurrRoom().addGoldToRewards(boonVal + AbstractDungeon.miscRng.random(0, 8));
            if(odds <= 200) {
                AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.COMMON);
            } else if(odds < 265){
                AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.UNCOMMON);
            } else if(odds < 300){
                AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.RARE);
            } else if(odds < 310){
                AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.BOSS);
            } else if(odds < 315){
                AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.SHOP);
                AbstractDungeon.player.gainGold(50);
            } else if(odds < 316){
                AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.RARE);
                AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.BOSS);
            }
        } else if(this.counter == -4) {
            flash();
            AbstractDungeon.getCurrRoom().addGoldToRewards(boonVal + AbstractDungeon.miscRng.random(7, 28));
            if(odds <= 250) {
                AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.COMMON);
            } else if(odds < 400){
                AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.UNCOMMON);
            } else if(odds < 500){
                AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.RARE);
            } else if(odds < 550){
                AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.BOSS);
            } else if(odds < 600){
                AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.SHOP);
                AbstractDungeon.player.gainGold(50);
            } else if(odds < 610){
                AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.RARE);
                AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.BOSS);
            }
        }
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        if(room.phase.equals(AbstractRoom.RoomPhase.COMBAT)) {
            if (this.counter == -2) {
                room.addGoldToRewards(boonVal + AbstractDungeon.miscRng.random(-2, 7));
            }
            else if(this.counter == -4){
                room.addGoldToRewards(boonVal + AbstractDungeon.miscRng.random(3, 12));
            }
        }
    }

    @Override
    public void onVictory() {
        flash();
    }
}
