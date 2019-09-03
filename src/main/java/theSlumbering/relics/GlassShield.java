package theSlumbering.relics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseBlockRelic;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import theSlumbering.SlumberingMod;
import theSlumbering.util.TextureLoader;

import static theSlumbering.SlumberingMod.*;

public class GlassShield extends AbstractCustomRelic implements OnLoseBlockRelic {

    public static final String ID = SlumberingMod.makeID("GlassShield");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GlassShield.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GlassShield.png"));


    public GlassShield() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);

        this.counter = AbstractDungeon.actNum * 30;
        this.floatCounter = 0;
        updateDescription();
    }



    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + this.counter + DESCRIPTIONS[1];

    }

    @Override
    public void updateDescription() {

        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public int onLoseBlock(DamageInfo damageInfo, int i) {
        //logger.info(i + " got here\n\n");
        this.counter--;
        flash();
        return i - 3;
    }
}