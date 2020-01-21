package theSlumbering.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import theSlumbering.SlumberingMod;
import theSlumbering.util.TextureLoader;

import static theSlumbering.SlumberingMod.makeRelicOutlinePath;
import static theSlumbering.SlumberingMod.makeRelicPath;

public class EnergyDrink extends AbstractCustomRelic {

    public static final String ID = SlumberingMod.makeID("EnergyDrink");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("temp.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("temp.png"));

    private boolean active;


    public EnergyDrink() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);

        this.counter = -1;
        this.floatCounter = 0;

        this.active = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onVictory() {
        if(AbstractDungeon.actNum == 1 && active){
            stopPulse();
            active = false;
            --AbstractDungeon.player.energy.energyMaster;
        }
    }

    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c) {

        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        if(room instanceof MonsterRoomBoss || room instanceof MonsterRoomElite){
            if(AbstractDungeon.actNum == 1) {
                beginPulse();
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                ++AbstractDungeon.player.energy.energyMaster;
                active = true;
            }
        }
    }
}
