package theSlumbering.events;

import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import theSlumbering.SlumberingMod;
import theSlumbering.cards.Curses.LingeringDoubt;
import theSlumbering.cards.Curses.Sloth;
import theSlumbering.characters.TheSlumbering;

import java.util.*;

import static theSlumbering.SlumberingMod.makeEventPath;

public class JustInTime extends AbstractImageEvent {

    public static final String ID = SlumberingMod.makeID("JustInTime");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("JustInTime.png");

    private static int goldSpent;
    private static int option;
    private static int count;
    private static int chambers;
    private static AbstractCard card;
    private static AbstractRelic relic;
    private Map<String, Integer> map = new HashMap<>();

    public JustInTime() {
        super(NAME, DESCRIPTIONS[0], IMG);

        CharSelectInfo info = AbstractDungeon.player.getLoadout();
        goldSpent = (info.gold + CardCrawlGame.goldGained) - AbstractDungeon.player.gold;

        setMap();

        if (AbstractDungeon.ascensionLevel >= 15) {
            //bad effect
        } else {
            //normal effect
        }

        //dialog choices
        imageEventText.setDialogOption(OPTIONS[1]);
        imageEventText.setDialogOption(OPTIONS[0]);
    }

    private void setMap(){
        map.put("Gold", 3);
        map.put("Health", 4);
        map.put("Max", 5);
        map.put("Card", 6);
        map.put("Curse", 7);
        map.put("Relic", 8);
    }

    private void getReward(){
        switch (option){
            case 3:
                AbstractDungeon.player.loseGold(AbstractDungeon.player.gold);
                break;
            case 4:
                AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, 10));
                break;
            case 5:
                AbstractDungeon.player.decreaseMaxHealth(10);
                break;
            case 6:
                AbstractDungeon.effectList.add(new PurgeCardEffect(card.makeStatEquivalentCopy()));
                AbstractDungeon.player.masterDeck.removeCard(card);
                break;
            case 7:
                AbstractDungeon.actionManager.addToBottom(new AddCardToDeckAction(card));
                // AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(card, ));
                //AbstractDungeon.player.masterDeck.addToTop(card);
                break;
            case 8:
                AbstractDungeon.player.loseRelic(relic.relicId);
                break;
            default:
                break;
        }
    }

    private void getNext(){
        if(count < chambers) {
            int val;

            String[] keys = map.keySet().toArray(new String[0]);
            int i = AbstractDungeon.eventRng.random(0, keys.length - 1);

            val = map.get(keys[i]);
            map.remove(keys[i]);

            option = val;

            this.imageEventText.updateBodyText(DESCRIPTIONS[option]);

            if(option == 6){
                card = AbstractDungeon.player.masterDeck.getRandomCard(AbstractDungeon.eventRng);
                this.imageEventText.updateDialogOption(0, OPTIONS[option], card);
            } else if (option == 7){
                if(AbstractDungeon.player instanceof TheSlumbering){
                    card = new Sloth();
                } else{
                    card = new LingeringDoubt();
                }
                this.imageEventText.updateDialogOption(0, OPTIONS[option], card);
            } else if (option == 8){
                ArrayList<AbstractRelic> relics = new ArrayList<>();
                for(AbstractRelic r: AbstractDungeon.player.relics){
                    if(r.tier != AbstractRelic.RelicTier.STARTER){
                        relics.add(r);
                    }
                }
                Collections.shuffle(relics, new Random(AbstractDungeon.miscRng.randomLong()));
                relic = relics.get(0);
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[option], relic);
                this.imageEventText.setDialogOption(OPTIONS[0]);
            } else {
                this.imageEventText.updateDialogOption(0, OPTIONS[option]);
            }
        } else{
            this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
            this.imageEventText.updateDialogOption(0, OPTIONS[2]);
            this.imageEventText.clearRemainingOptions();
            option = -1;
            screenNum = 2;
        }
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0: // button = 0
                        if(count > 0){
                            getReward();
                        }
                        getNext();
                        count++;
                        break;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[0]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;

                        break;
                }
                break;
            case 1: //screenNum = 1;
                switch (i) {
                    case 0:
                        openMap();
                        break;
                }
                break;
            case 2:
                switch (i) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                }
                break;
        }
    }

    static{
        count = 0;
        option = 1;
        chambers = 2;
    }
}