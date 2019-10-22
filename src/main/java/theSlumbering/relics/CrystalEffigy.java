package theSlumbering.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;
import theSlumbering.util.TextureLoader;

import static theSlumbering.SlumberingMod.makeRelicOutlinePath;
import static theSlumbering.SlumberingMod.makeRelicPath;

public class CrystalEffigy extends AbstractCustomRelic {

    public static final String ID = SlumberingMod.makeID("CrystalEffigy");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("temp.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("temp.png"));

    private boolean damaged;


    public CrystalEffigy() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);

        this.counter = -1;
        this.floatCounter = 0;
        this.damaged = false;
    }

    @Override
    public String getUpdatedDescription() {
        if(AbstractDungeon.player instanceof TheSlumbering){
            return DESCRIPTIONS[1];
        } else{
            return DESCRIPTIONS[0];
        }

    }

    @Override
    public void atBattleStart() {
        this.damaged = false;
    }

    @Override
    public void onLoseHp(int damageAmount) {
        this.damaged = true;
        super.onLoseHp(damageAmount);
    }

    @Override
    public void onVictory() {
        if(!this.damaged){
            this.flash();
            if(AbstractDungeon.player instanceof TheSlumbering){
                AbstractDungeon.player.increaseMaxHp(5, true);

            } else{
                AbstractDungeon.player.increaseMaxHp(10, true);
            }
        }
    }

    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c) {

        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }
}
