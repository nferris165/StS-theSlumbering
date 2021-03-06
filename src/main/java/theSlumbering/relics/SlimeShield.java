package theSlumbering.relics;

import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnLoseHpRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseBlockRelic;
import com.megacrit.cardcrawl.vfx.RelicAboveCreatureEffect;
import theSlumbering.SlumberingMod;
import theSlumbering.characters.TheSlumbering;
import theSlumbering.util.TextureLoader;

import static theSlumbering.SlumberingMod.*;

public class SlimeShield extends AbstractCustomRelic implements BetterOnLoseHpRelic, CustomSavable<Integer> {

    public static final String ID = SlumberingMod.makeID("SlimeShield");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SlimeShield.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GlassShield.png"));

    public boolean active;
    private int powerLevel = 15;

    public SlimeShield() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);

        this.counter = powerLevel;
        this.floatCounter = 0;
        this.active = true;

        updateDescription(TheSlumbering.Enums.THE_SLUMBERING);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + this.powerLevel + DESCRIPTIONS[1];

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
        //this.counter = powerLevel;
        if(this.powerLevel > 0){
            this.active = true;
            flash();
        }
    }

    @Override
    public int betterOnLoseHp(DamageInfo damageInfo, int i) {
        if(this.active){
            if(i >= this.counter){
                flash();
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                int j = i - this.counter;
                this.counter = -1;
                this.active = false;
                this.powerLevel--;
                return j;
            }
            else{
                flash();
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.counter -= i;
                return 0;
            }
        }
        return i;
    }

    @Override
    public void onVictory() {
        if(this.counter != this.powerLevel){
            this.powerLevel--;
            this.counter = this.powerLevel;
        }
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
