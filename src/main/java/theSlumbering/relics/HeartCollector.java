package theSlumbering.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theSlumbering.SlumberingMod;
import theSlumbering.util.TextureLoader;

import static theSlumbering.SlumberingMod.makeRelicOutlinePath;
import static theSlumbering.SlumberingMod.makeRelicPath;

public class HeartCollector extends AbstractCustomRelic implements ClickableRelic {

    public static final String ID = SlumberingMod.makeID("HeartCollector");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("HeartCollector.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("HeartCollector.png"));

    public HeartCollector() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);

        if(AbstractDungeon.ascensionLevel >= 6){
            this.counter = 5;
        }
        else{
            this.counter = 6;
        }
    }

    @Override
    public void onRightClick() {
        if (!isObtained || this.counter == 0) {
            return;
        }
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            flash();
            this.counter--;
            if(this.counter <= 0) {
                stopPulse();
                this.counter = 0;
            }

            AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_COLLECTOR_DEBUFF"));

            //gain block
            int woke = 0;
            if(AbstractDungeon.player.hasRelic(SlumberingRelic.ID)){
                AbstractRelic r = AbstractDungeon.player.getRelic(SlumberingRelic.ID);
                woke = r.counter;
            }

            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, woke));

        }
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        if (m.currentHealth <= 0 && !m.hasPower(MinionPower.POWER_ID)) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(m, this));
            if(m.type == AbstractMonster.EnemyType.BOSS){
                if(AbstractDungeon.ascensionLevel >= 14){
                    this.counter += 2;
                }
                else{
                    this.counter += 3;
                }
            }
            this.counter++;
        }
    }

    public void atTurnStart() {
        if(this.counter > 0) {
            beginLongPulse();
        }
    }

    @Override
    public void atPreBattle() {
        if(this.counter > 0) {
            beginLongPulse();
        }
    }

    @Override
    public void onTrigger(int amt) {
        this.flash();
        this.counter -= amt;
        if(this.counter < 0){
            this.counter = 0;
        }
    }

    @Override
    public void onVictory() {
        stopPulse();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[1] + DESCRIPTIONS[2];
    }
}
