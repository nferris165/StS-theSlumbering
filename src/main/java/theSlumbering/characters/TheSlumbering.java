package theSlumbering.characters;

import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
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
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import theSlumbering.SlumberingMod;
import theSlumbering.cards.*;
import theSlumbering.cards.BasicDefend;
import theSlumbering.patches.ActionManagerPatch;
import theSlumbering.powers.DrowsyPower;
import theSlumbering.relics.*;

import java.util.ArrayList;
import java.util.Iterator;

import static theSlumbering.SlumberingMod.*;
import static theSlumbering.characters.TheSlumbering.Enums.COLOR_SLUMBERING;

public class TheSlumbering extends AbstractCustomPlayer {

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_SLUMBERING;
        @SpireEnum(name = "FIRST_TEAL_COLOR")
        public static AbstractCard.CardColor COLOR_SLUMBERING;
        @SpireEnum(name = "FIRST_TEAL_COLOR") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    //custom variables
    private static boolean calledTransform = true;

    //base stats
    private static final int ENERGY_PER_TURN = 5;
    private static final int STARTING_HP = 1;
    private static final int MAX_HP = 1;
    private static final int STARTING_GOLD = 100;
    private static final int CARD_DRAW = 6;
    private static final int ORB_SLOTS = 0;

    //init settings
    private static final String ID = makeID("SlumberingCharacter");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;



    private static final String[] orbTextures = {
            "theSlumberingResources/images/char/slumberingCharacter/orb/layer1.png",
            "theSlumberingResources/images/char/slumberingCharacter/orb/layer2.png",
            "theSlumberingResources/images/char/slumberingCharacter/orb/layer3.png",
            "theSlumberingResources/images/char/slumberingCharacter/orb/layer4.png",
            "theSlumberingResources/images/char/slumberingCharacter/orb/layer5.png",
            "theSlumberingResources/images/char/slumberingCharacter/orb/layer6.png",
            "theSlumberingResources/images/char/slumberingCharacter/orb/layer1d.png",
            "theSlumberingResources/images/char/slumberingCharacter/orb/layer2d.png",
            "theSlumberingResources/images/char/slumberingCharacter/orb/layer3d.png",
            "theSlumberingResources/images/char/slumberingCharacter/orb/layer4d.png",
            "theSlumberingResources/images/char/slumberingCharacter/orb/layer5d.png",};

    public TheSlumbering(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "theSlumberingResources/images/char/slumberingCharacter/orb/vfx.png", null,
                new SpriterAnimation(
                        "theSlumberingResources/images/char/slumberingCharacter/Spriter/theDefaultAnimation.scml"));

        //energy manager
        initializeClass(null,
                THE_SLUMBERING_SHOULDER_1,
                THE_SLUMBERING_SHOULDER_2,
                THE_SLUMBERING_CORPSE,
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));

        //animations
        loadAnimation(
                THE_SLUMBERING_SKELETON_ATLAS,
                THE_SLUMBERING_SKELETON_JSON,
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
        retVal.add(Strike_Slumbering.ID);
        retVal.add(Strike_Slumbering.ID);

        retVal.add(BasicDefend.ID);
        retVal.add(BasicDefend.ID);
        retVal.add(Defend_Slumbering.ID);
        retVal.add(Defend_Slumbering.ID);

        retVal.add(Invigorate.ID);
        retVal.add(DreamEater.ID);

        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(SlumberingRelic.ID);
        retVal.add(HeartCollector.ID);

        UnlockTracker.markRelicAsSeen(SlumberingRelic.ID);
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

    @Override
    public int getAscensionMaxHPLoss() {
        return 0;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_SLUMBERING;
    }

    @Override
    public Color getCardTrailColor() {
        return SlumberingMod.SLUMBERING_TEAL;
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
        return new TheSlumbering(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return SlumberingMod.SLUMBERING_TEAL;
    }

   //heart attack tint
    @Override
    public Color getSlashAttackColor() {
        return SlumberingMod.SLUMBERING_TEAL;
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

        if(AbstractDungeon.player.hasRelic(SlumberingRelic.ID)){
            AbstractCustomRelic r = (AbstractCustomRelic) AbstractDungeon.player.getRelic(SlumberingRelic.ID);
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player,
                    AbstractDungeon.player, new DrowsyPower(AbstractDungeon.player, 6 - r.getState(), false), 1));
        }
        else{
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player,
                    AbstractDungeon.player, new DrowsyPower(AbstractDungeon.player, 99, false), 1));
        }
    }

    private static void applyState(AbstractCard Old, AbstractCard New){
        if(Old.upgraded){
            New.upgrade();
        }
        New.inBottleFlame = Old.inBottleFlame;
        New.inBottleTornado = Old.inBottleTornado;
        New.inBottleLightning = Old.inBottleLightning;
    }

    private static void replaceBasic() {

        calledTransform = false;
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);

        ArrayList<AbstractCard> list = new ArrayList<>();
        int effCount = 0;
        boolean fast = Settings.FAST_MODE;
        Settings.FAST_MODE = false;

        for(AbstractCard e: AbstractDungeon.player.masterDeck.group){
            float x, y;

            if(e instanceof BasicAttack){
                effCount++;

                if(effCount > 4) {
                    x = MathUtils.random(0.25F, 0.75F) * (float) Settings.WIDTH;
                    y = MathUtils.random(0.25F, 0.75F) * (float) Settings.HEIGHT;
                } else{
                    x = (float)Settings.WIDTH * (0.2F * effCount);
                    y = (float)Settings.HEIGHT * 0.5F;
                }

                logger.info(x + " " + y + " " + "\n\n");
                logger.info(e.uuid + " " + e.name + " " + "\n\n");
                AbstractCard c = new DrowsyAttack();
                applyState(e, c);
                AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(c, x, y, false));
                list.add(e);
            }
            else if(e instanceof BasicDefend){
                effCount++;

                if(effCount > 4) {
                    x = MathUtils.random(0.25F, 0.75F) * (float) Settings.WIDTH;
                    y = MathUtils.random(0.25F, 0.75F) * (float) Settings.HEIGHT;
                } else{
                    x = (float)Settings.WIDTH * (0.2F * effCount);
                    y = (float)Settings.HEIGHT * 0.5F;
                }
                logger.info(x + " " + y + " " + "\n\n");
                logger.info(e.uuid + " " + e.name + " " + "\n\n");
                AbstractCard c = new DrowsyDefend();
                applyState(e, c);
                AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(c, x, y, false));
                list.add(e);
            }
        }
        for(AbstractCard r: list){
            AbstractDungeon.player.masterDeck.removeCard(r);
        }
        Settings.FAST_MODE = fast;
    }

    private static void replaceDrowsy() {
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);

        ArrayList<AbstractCard> list = new ArrayList<>();
        int effCount = 0;
        boolean fast = Settings.FAST_MODE;
        Settings.FAST_MODE = false;

        for(AbstractCard e: AbstractDungeon.player.masterDeck.group){
            float x, y;

            if(e instanceof DrowsyAttack){
                effCount++;

                if(effCount > 4) {
                    x = MathUtils.random(0.25F, 0.75F) * (float) Settings.WIDTH;
                    y = MathUtils.random(0.25F, 0.75F) * (float) Settings.HEIGHT;
                } else{
                    x = (float)Settings.WIDTH * (0.2F * effCount);
                    y = (float)Settings.HEIGHT * 0.5F;
                }
                AbstractCard c = new WokeAttack();
                applyState(e, c);
                AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(c, x, y, false));
                list.add(e);
            }
            else if(e instanceof DrowsyDefend){
                effCount++;

                if(effCount > 4) {
                    x = MathUtils.random(0.25F, 0.75F) * (float) Settings.WIDTH;
                    y = MathUtils.random(0.25F, 0.75F) * (float) Settings.HEIGHT;
                } else{
                    x = (float)Settings.WIDTH * (0.2F * effCount);
                    y = (float)Settings.HEIGHT * 0.5F;
                }
                AbstractCard c = new WokeDefend();
                applyState(e, c);
                AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(c, x, y, false));
                list.add(e);
            }
        }
        for(AbstractCard r: list){
            AbstractDungeon.player.masterDeck.removeCard(r);
        }
        Settings.FAST_MODE = fast;
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
                incSlumberingRelic(2);
                break;
            case 3:
                replaceDrowsy();
                replaceBasic(); //maybe not?
                incSlumberingRelic(3);
                break;
            case 4:
                incSlumberingRelic(4);
                break;
            default:
                replaceDrowsy();
                replaceBasic(); //maybe not?
                incSlumberingRelic(3);
                break;
        }


    }
}
