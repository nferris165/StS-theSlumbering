package theFirst.relics;

import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnLoseHpRelic;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseBlockRelic;
import theFirst.FirstMod;
import theFirst.util.TextureLoader;

import static theFirst.FirstMod.*;

public class SlimeShield extends AbstractCustomRelic implements BetterOnLoseHpRelic, OnLoseBlockRelic, CustomSavable<Integer> {

    public static final String ID = FirstMod.makeID("SlimeShield");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SlimeShield.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GlassShield.png"));

    public boolean active;
    private int powerLevel = 15;

    public SlimeShield() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);

        this.counter = powerLevel;
        this.floatCounter = 0;
        this.active = true;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];

    }

    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c) {

        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public void atBattleStart(){
        this.counter = powerLevel;
        this.active = true;
        flash();
    }

    @Override
    public int onLoseBlock(DamageInfo damageInfo, int i) {
        /*if(this.active){
            if(i >= this.counter){
                flash();
                int j = i - this.counter;
                this.counter = -1;
                this.active = false;
                this.powerLevel--;
                return j;
            }
            else{
                flash();
                this.counter -= i;
                return 0;
            }
        }
        */
        return i;
    }

    @Override
    public int betterOnLoseHp(DamageInfo damageInfo, int i) {
        if(this.active){
            if(i >= this.counter){
                flash();
                int j = i - this.counter;
                this.counter = -1;
                this.active = false;
                this.powerLevel--;
                return j;
            }
            else{
                flash();
                this.counter -= i;
                return 0;
            }
        }
        return i;
    }

    @Override
    public Integer onSave() {
        return this.powerLevel;
    }

    @Override
    public void onLoad(Integer pLevel) {
        this.powerLevel = pLevel;
    }
}
