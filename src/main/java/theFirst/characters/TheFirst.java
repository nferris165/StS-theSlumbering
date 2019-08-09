package theFirst.characters;

import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import theFirst.FirstMod;
import theFirst.cards.*;
import theFirst.cards.BasicDefend;
import theFirst.powers.DrowsyPower;
import theFirst.relics.*;

import java.util.ArrayList;
import java.util.Iterator;

import static theFirst.FirstMod.*;
import static theFirst.characters.TheFirst.Enums.COLOR_FIRST;

public class TheFirst extends AbstractCustomPlayer {

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_FIRST;
        @SpireEnum(name = "FIRST_TEAL_COLOR")
        public static AbstractCard.CardColor COLOR_FIRST;
        @SpireEnum(name = "FIRST_TEAL_COLOR") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    //custom variables
    private static boolean calledTransform = true;

    //base stats
    private static final int ENERGY_PER_TURN = 4;
    private static final int STARTING_HP = 800;
    private static final int MAX_HP = 800;
    private static final int STARTING_GOLD = 10000;
    private static final int CARD_DRAW = 6;
    private static final int ORB_SLOTS = 0;

    //init settings
    private static final String ID = makeID("FirstCharacter");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;



    private static final String[] orbTextures = {
            "theFirstResources/images/char/firstCharacter/orb/layer1.png",
            "theFirstResources/images/char/firstCharacter/orb/layer2.png",
            "theFirstResources/images/char/firstCharacter/orb/layer3.png",
            "theFirstResources/images/char/firstCharacter/orb/layer4.png",
            "theFirstResources/images/char/firstCharacter/orb/layer5.png",
            "theFirstResources/images/char/firstCharacter/orb/layer6.png",
            "theFirstResources/images/char/firstCharacter/orb/layer1d.png",
            "theFirstResources/images/char/firstCharacter/orb/layer2d.png",
            "theFirstResources/images/char/firstCharacter/orb/layer3d.png",
            "theFirstResources/images/char/firstCharacter/orb/layer4d.png",
            "theFirstResources/images/char/firstCharacter/orb/layer5d.png",};

    public TheFirst(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "theFirstResources/images/char/firstCharacter/orb/vfx.png", null,
                new SpriterAnimation(
                        "theFirstResources/images/char/firstCharacter/Spriter/theDefaultAnimation.scml"));

        //energy manager
        initializeClass(null,
                THE_FIRST_SHOULDER_1,
                THE_FIRST_SHOULDER_2,
                THE_FIRST_CORPSE,
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));

        //animations
        loadAnimation(
                THE_FIRST_SKELETON_ATLAS,
                THE_FIRST_SKELETON_JSON,
                1.0f);
        AnimationState.TrackEntry e = state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        //dialog box
        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 220.0F * Settings.scale);
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(BasicAttack.ID);
        retVal.add(BasicAttack.ID);
        retVal.add(BasicAttack.ID);

        retVal.add(BasicDefend.ID);
        retVal.add(BasicDefend.ID);
        retVal.add(BasicDefend.ID);

        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(FirstRelic.ID);
        retVal.add(HeartCollector.ID);

        UnlockTracker.markRelicAsSeen(FirstRelic.ID);
        UnlockTracker.markRelicAsSeen(HeartCollector.ID);

        return retVal;
    }

    //char select effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false); // Screen Effect
    }

    //char select noise
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    //TODO finalize hp values
    @Override
    public int getAscensionMaxHPLoss() {
        return 0;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_FIRST;
    }

    @Override
    public Color getCardTrailColor() {
        return FirstMod.FIRST_TEAL;
    }

    //BitmapFont object for energy orb
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    //Match and Keep event card
    @Override
    public AbstractCard getStartCardForEvent() {
        return new BasicAttack();
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheFirst(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return FirstMod.FIRST_TEAL;
    }

   //heart attack tint
    @Override
    public Color getSlashAttackColor() {
        return FirstMod.FIRST_TEAL;
    }

    //heart attack combo
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    @Override
    public void preBattlePrep()
    {
        super.preBattlePrep();

        if(AbstractDungeon.player.hasRelic("theFirst:FirstRelic")){
            AbstractCustomRelic r = (AbstractCustomRelic) AbstractDungeon.player.getRelic("theFirst:FirstRelic");
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player,
                    AbstractDungeon.player, new DrowsyPower(AbstractDungeon.player, 6 - r.getState(), false), 1));
        }
        else{
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player,
                    AbstractDungeon.player, new DrowsyPower(AbstractDungeon.player, 99, false), 1));
        }
    }

    private static void replaceBasic() {

        calledTransform = false;
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
        Iterator i = AbstractDungeon.player.masterDeck.group.iterator();

        //CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        AbstractCard e;
        while(i.hasNext()){
            e = (AbstractCard)i.next();
            //logger.info(e.tags + "\n");

            float x = MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH;
            float y = MathUtils.random(0.2F, 0.8F) * (float) Settings.HEIGHT;

            if(e instanceof BasicAttack){

                AbstractCard c = new DrowsyAttack();
                if(e.upgraded){
                    c.upgrade();
                }
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, x, y));
                //group.addToBottom(c);
                i.remove();
            }
            else if(e instanceof BasicDefend){
                AbstractCard c = new DrowsyDefend();
                if(e.upgraded){
                    c.upgrade();
                }
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, x, y));
                //group.addToBottom(c);
                i.remove();
            }
        }

        //AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, "testing");

    }

    private static void replaceDrowsy() {

        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
        Iterator i = AbstractDungeon.player.masterDeck.group.iterator();

        AbstractCard e;
        while(i.hasNext()){
            e = (AbstractCard)i.next();
            //logger.info(e.tags + "\n");

            float x = MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH;
            float y = MathUtils.random(0.2F, 0.8F) * (float) Settings.HEIGHT;

            if(e instanceof DrowsyAttack){

                AbstractCard c = new WokeAttack();
                if(e.upgraded){
                    c.upgrade();
                }
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, x, y));
                i.remove();
            }
            else if(e instanceof DrowsyDefend){
                AbstractCard c = new WokeDefend();
                if(e.upgraded){
                    c.upgrade();
                }
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, x, y));
                i.remove();
            }

        }

    }

    public static void replaceCards(int act) {

        switch (act)
        {
            case 0:
                break;
            case 1:
                break;
            case 2:
                replaceBasic();
                incFirstRelic(4);
                break;
            case 3:
                replaceDrowsy();
                //replaceBasic(); //maybe not?
                incFirstRelic(5);
                break;
            case 4:
                incFirstRelic(6);
                break;
            default:
                replaceDrowsy();
                replaceBasic(); //maybe not?
                incFirstRelic(4);
                break;
        }


    }
}
