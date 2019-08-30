package theSlumbering.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theSlumbering.SlumberingMod;
import theSlumbering.powers.DrowsyPower;
import theSlumbering.util.TextureLoader;

import static theSlumbering.SlumberingMod.*;

public class StarMobile extends AbstractCustomRelic {

    public static final String ID = SlumberingMod.makeID("StarMobile");

    //TODO: make art
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("zzz.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("zzz.png"));


    public StarMobile() {
        super(ID, IMG, OUTLINE, AbstractRelic.RelicTier.UNCOMMON, LandingSound.MAGICAL);

        this.counter = -1;
        this.floatCounter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        if(AbstractDungeon.player.hasPower(DrowsyPower.POWER_ID)){
            AbstractDungeon.player.getPower(DrowsyPower.POWER_ID).updateDescription();
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
