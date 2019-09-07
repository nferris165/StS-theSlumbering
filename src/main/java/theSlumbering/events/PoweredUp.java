package theSlumbering.events;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.combat.RoomTintEffect;
import theSlumbering.SlumberingMod;
import theSlumbering.cards.BagOfShields;
import theSlumbering.cards.SpectralDefender;
import theSlumbering.cards.ToughSkin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static theSlumbering.SlumberingMod.makeEventPath;

public class PoweredUp extends AbstractImageEvent {

    public static final String ID = SlumberingMod.makeID("PoweredUp");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("PoweredUp.png");

    private int cost;
    private AbstractCard reward = null;
    private Map<Integer, Integer> map = new HashMap<>();
    private  List<Integer> list = new ArrayList<>();
    private  List<Integer> newList = new ArrayList<>();
    private final int SIZE = 10;

    public PoweredUp() {
        super(NAME, DESCRIPTIONS[0], IMG);

        if (AbstractDungeon.ascensionLevel >= 15) {
            cost = -2;
        } else {
            cost = -1;
        }

        genScene();

        map.put(1,0);
        map.put(2,0);
        map.put(3,0);

        imageEventText.setDialogOption(OPTIONS[0]);
        imageEventText.setDialogOption(OPTIONS[1]);
    }

    private void genScene(){
        for(int i = 0; i < SIZE; i++){
            list.add(i);
        }

        for (int i = 0; i < 4; i++) {
            int randomIndex = AbstractDungeon.eventRng.random.nextInt(list.size());
            newList.add(list.get(randomIndex));
            list.remove(randomIndex);
        }
    }

    private void checkScore(){
        int a = map.get(1);
        int b = map.get(2);
        int c = map.get(3);

        if(a == b && b == c){
            return;
        }

        int max = (a >= b) ? a : b;
        max = (max >= c) ? max : c;

        for(Map.Entry entry : map.entrySet()){
            if(((int)entry.getValue()) == max){
                max = (int) entry.getKey();
            }
        }

        switch (max) {
            case 1:
                this.reward = new SpectralDefender();
                break;
            case 2:
                this.reward = new BagOfShields();
                break;
            case 3:
                this.reward = new ToughSkin();
                break;
        }
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0: // button = 0
                        SlumberingMod.incSlumberingRelic(cost);
                        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
                        CardCrawlGame.sound.play("EVENT_OOZE", true);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[2]);
                        screenNum = 6;
                        break;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;

                        break;
                }
                break;
            case 1: // screenNum = 1;
                switch (i) {
                    case 0:
                        openMap();
                        break;
                }
                break;
            case 2:
                switch (i) {
                    case 0:
                        AbstractDungeon.topLevelEffects.add(new RoomTintEffect(Color.TEAL, 10.0f));
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2] + DESCRIPTIONS[4 + newList.get(1)]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        map.replace(1, map.get(1) + 1);
                        screenNum = 3;
                        break;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2] + DESCRIPTIONS[4 + newList.get(1)]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        map.replace(2, map.get(2) + 1);
                        screenNum = 3;
                        break;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2] + DESCRIPTIONS[4 + newList.get(1)]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        map.replace(3, map.get(3) + 1);
                        screenNum = 3;
                        break;
                }
                break;
            case 3:
                switch (i) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2] + DESCRIPTIONS[4 + newList.get(2)]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        map.replace(1, map.get(1) + 1);
                        screenNum = 4;
                        break;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2] + DESCRIPTIONS[4 + newList.get(2)]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        map.replace(2, map.get(2) + 1);
                        screenNum = 4;
                        break;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2] + DESCRIPTIONS[4 + newList.get(2)]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        map.replace(3, map.get(3) + 1);
                        screenNum = 4;
                        break;
                }
                break;
            case 4:
                switch (i) {
                    case 0:
                        map.replace(1, map.get(1) + 1);
                        checkScore();
                        if(reward == null){
                            this.imageEventText.updateBodyText(DESCRIPTIONS[2] + DESCRIPTIONS[4 + newList.get(3)]);
                            this.imageEventText.clearAllDialogs();
                            this.imageEventText.setDialogOption(OPTIONS[3]);
                            this.imageEventText.setDialogOption(OPTIONS[4]);
                            this.imageEventText.setDialogOption(OPTIONS[5]);
                            screenNum = 5;
                        }
                        else{
                            this.imageEventText.updateBodyText(DESCRIPTIONS[SIZE + 4]);
                            this.imageEventText.updateDialogOption(0, OPTIONS[2], this.reward);
                            this.imageEventText.clearRemainingOptions();
                            screenNum = 7;
                        }
                        break;
                    case 1:
                        map.replace(2, map.get(2) + 1);
                        checkScore();
                        if(reward == null){
                            this.imageEventText.updateBodyText(DESCRIPTIONS[2] + DESCRIPTIONS[4 + newList.get(3)]);
                            this.imageEventText.clearAllDialogs();
                            this.imageEventText.setDialogOption(OPTIONS[3]);
                            this.imageEventText.setDialogOption(OPTIONS[4]);
                            this.imageEventText.setDialogOption(OPTIONS[5]);
                            screenNum = 5;
                        }
                        else{
                            this.imageEventText.updateBodyText(DESCRIPTIONS[SIZE + 4]);
                            this.imageEventText.updateDialogOption(0, OPTIONS[2], this.reward);
                            this.imageEventText.clearRemainingOptions();
                            screenNum = 7;
                        }
                        break;
                    case 2:
                        map.replace(3, map.get(3) + 1);
                        checkScore();
                        if(reward == null){
                            this.imageEventText.updateBodyText(DESCRIPTIONS[2] + DESCRIPTIONS[4 + newList.get(3)]);
                            this.imageEventText.clearAllDialogs();
                            this.imageEventText.setDialogOption(OPTIONS[3]);
                            this.imageEventText.setDialogOption(OPTIONS[4]);
                            this.imageEventText.setDialogOption(OPTIONS[5]);
                            screenNum = 5;
                        }
                        else{
                            this.imageEventText.updateBodyText(DESCRIPTIONS[SIZE + 4]);
                            this.imageEventText.updateDialogOption(0, OPTIONS[2], this.reward);
                            this.imageEventText.clearRemainingOptions();
                            screenNum = 7;
                        }
                        break;
                }
                break;
            case 5:
                switch (i) {
                    case 0:
                        this.reward = new SpectralDefender();
                        this.imageEventText.updateBodyText(DESCRIPTIONS[SIZE + 4]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[2], this.reward);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 7;
                        break;
                    case 1:
                        this.reward = new BagOfShields();
                        this.imageEventText.updateBodyText(DESCRIPTIONS[SIZE + 4]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[2], this.reward);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 7;
                        break;
                    case 2:
                        this.reward = new ToughSkin();
                        this.imageEventText.updateBodyText(DESCRIPTIONS[SIZE + 4]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[2], this.reward);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 7;
                        break;
                }
                break;
            case 6:
                switch (i) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2] + DESCRIPTIONS[4 + newList.get(0)]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        screenNum = 2;
                        break;
                }
                break;
            case 7:
                switch (i) {
                    case 0:
                        if(this.reward != null){
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(
                                    this.reward.makeStatEquivalentCopy(),
                                    (float) Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 20.0F * Settings.scale,
                                    (float)Settings.HEIGHT / 2.0F));
                        }
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4 + SIZE + 1]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[1]);
                        screenNum = 1;
                        break;
                }
                break;
        }
    }
}
