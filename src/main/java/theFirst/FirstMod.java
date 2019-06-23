package theFirst;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.neow.NeowEvent;
import theFirst.characters.TheFirst;
import theFirst.events.BasicEvent;
import theFirst.monsters.SimpleMonster;
import theFirst.relics.AbstractCustomRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theFirst.cards.*;
import theFirst.potions.NewPotion;
import theFirst.relics.*;
import theFirst.util.TextureLoader;
import theFirst.variables.EnergyVariable;
import theFirst.variables.DynamicMagicVariable;
import theFirst.relics.FirstRelic;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;


@SuppressWarnings("WeakerAccess")
@SpireInitializer
public class FirstMod implements
        StartActSubscriber,
        MaxHPChangeSubscriber,
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostExhaustSubscriber,
        PostInitializeSubscriber {

    public static final Logger logger = LogManager.getLogger(FirstMod.class.getName());

    //mod settings
    public static Properties defaultSettings = new Properties();
    public static final String event_sharing_settings = "eventSharing";
    public static boolean eventSharing = false;

    private static final String MODNAME = "First Mod";
    private static final String AUTHOR = "Nichilas";
    private static final String DESCRIPTION = "My first mod, ft. the First.";



    //char main color
    public static final Color FIRST_TEAL = CardHelper.getColor(59.0f, 125.0f, 99.0f);

    public static final Color FIRST_POTION_RUST = CardHelper.getColor(211.0f, 40.0f, 2.0f);

    private static final String ATTACK_CARD_FIRST = "theFirstResources/images/512/bg_attack_first_color.png";
    private static final String SKILL_CARD_FIRST = "theFirstResources/images/512/bg_skill_first_color.png";
    private static final String POWER_CARD_FIRST = "theFirstResources/images/512/bg_power_first_color.png";

    private static final String ENERGY_ORB_FIRST = "theFirstResources/images/512/card_first_color_orb.png";
    private static final String CARD_ENERGY_ORB = "theFirstResources/images/512/card_small_orb.png";

    private static final String ATTACK_FIRST_PORTRAIT = "theFirstResources/images/1024/bg_attack_first_color.png";
    private static final String SKILL_FIRST_PORTRAIT = "theFirstResources/images/1024/bg_skill_first_color.png";
    private static final String POWER_FIRST_PORTRAIT = "theFirstResources/images/1024/bg_power_first_color.png";
    private static final String ENERGY_ORB_FIRST_PORTRAIT = "theFirstResources/images/1024/card_first_color_orb.png";

    private static final String THE_FIRST_BUTTON = "theFirstResources/images/charSelect/firstButton.png";
    private static final String THE_FIRST_PORTRAIT = "theFirstResources/images/charSelect/FirstPortraitBG.png";
    public static final String THE_FIRST_SHOULDER_1 = "theFirstResources/images/char/defaultCharacter/shoulder.png";
    public static final String THE_FIRST_SHOULDER_2 = "theFirstResources/images/char/defaultCharacter/shoulder2.png";
    public static final String THE_FIRST_CORPSE = "theFirstResources/images/char/defaultCharacter/corpse.png";

    public static final String BADGE_IMAGE = "theFirstResources/images/Badge.png";

    public static final String THE_FIRST_SKELETON_ATLAS = "theFirstResources/images/char/defaultCharacter/skeleton.atlas";
    public static final String THE_FIRST_SKELETON_JSON = "theFirstResources/images/char/defaultCharacter/skeleton.json";

    private static final String modID = "theFirst";


    //Image Directories
    public static String makeCardPath(String resourcePath) {
        return modID + "Resources/images/cards/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return modID + "Resources/images/events/" + resourcePath;
    }

    public static String makeMonsterPath(String resourcePath) {
        return modID + "Resources/images/monsters/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return modID + "Resources/images/orbs/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return modID + "Resources/images/powers/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return modID + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return modID + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeVfxPath(String resourcePath) {
        return modID + "Resources/images/vfx/" + resourcePath;
    }

    public FirstMod() {
        BaseMod.subscribe(this);

        BaseMod.addColor(TheFirst.Enums.COLOR_FIRST, FIRST_TEAL, FIRST_TEAL, FIRST_TEAL,
                FIRST_TEAL, FIRST_TEAL, FIRST_TEAL, FIRST_TEAL,
                ATTACK_CARD_FIRST, SKILL_CARD_FIRST, POWER_CARD_FIRST, ENERGY_ORB_FIRST,
                ATTACK_FIRST_PORTRAIT, SKILL_FIRST_PORTRAIT, POWER_FIRST_PORTRAIT,
                ENERGY_ORB_FIRST_PORTRAIT, CARD_ENERGY_ORB);

        //TODO finalize mod settings
        logger.info("Adding mod settings");
        defaultSettings.setProperty(event_sharing_settings, "FALSE");
        try {
            SpireConfig config = new SpireConfig("firstMod", "theFirstConfig", defaultSettings);
            config.load();
            eventSharing = config.getBool(event_sharing_settings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unused")
    public static void initialize() {
        FirstMod firstmod = new FirstMod();
    }

    @Override
    public void receiveEditCharacters() {

        BaseMod.addCharacter(new TheFirst("the First", TheFirst.Enums.THE_FIRST),
                THE_FIRST_BUTTON, THE_FIRST_PORTRAIT, TheFirst.Enums.THE_FIRST);

        receiveEditPotions();
    }


    @Override
    public void receivePostInitialize() {
        //mod badge and setting panel
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        ModPanel settingsPanel = new ModPanel();

        //TODO mod panel
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("Toggles event sharing.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                eventSharing,
                settingsPanel,
                (label) -> {},
                (button) -> {

                    eventSharing = button.enabled;
                    try {
                        SpireConfig config = new SpireConfig("firstMod", "theFirstConfig", defaultSettings);
                        config.setBool(event_sharing_settings, eventSharing);
                        config.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        settingsPanel.addUIElement(enableNormalsButton);
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        //events
        BaseMod.addEvent(BasicEvent.ID, BasicEvent.class, Exordium.ID);

        //monsters
        BaseMod.addMonster(SimpleMonster.ID, "SimpleMon", () -> new SimpleMonster(0.0F, 25.0F));

        //encounters
        BaseMod.addMonsterEncounter(Exordium.ID, new MonsterInfo(SimpleMonster.ID, 5));

    }

    public void receiveEditPotions() {
        BaseMod.addPotion(NewPotion.class, FIRST_POTION_RUST, FIRST_TEAL, FIRST_POTION_RUST, NewPotion.POTION_ID, TheFirst.Enums.THE_FIRST);
        //BaseMod.addPotion(NewPotion.class, FIRST_POTION_RUST, FIRST_TEAL, FIRST_POTION_RUST, NewPotion.POTION_ID);

    }


    @Override
    public void receiveEditRelics() {
        //character only
        BaseMod.addRelicToCustomPool(new HeartCollector(), TheFirst.Enums.COLOR_FIRST);
        BaseMod.addRelicToCustomPool(new FirstRelic(), TheFirst.Enums.COLOR_FIRST);

        //shared
        BaseMod.addRelic(new GlassShield(), RelicType.SHARED);
        BaseMod.addRelic(new SlimeShield(), RelicType.SHARED);
        BaseMod.addRelic(new CursedAnvil(), RelicType.SHARED);
        BaseMod.addRelic(new EnchantedHammer(), RelicType.SHARED);
        BaseMod.addRelic(new GamblerFolly(), RelicType.SHARED);

        //unlock?
        UnlockTracker.markRelicAsSeen(GlassShield.ID);
        UnlockTracker.markRelicAsSeen(SlimeShield.ID);
        UnlockTracker.markRelicAsSeen(CursedAnvil.ID);
        UnlockTracker.markRelicAsSeen(EnchantedHammer.ID);
        UnlockTracker.markRelicAsSeen(GamblerFolly.ID);
    }


    @Override
    public void receiveEditCards() {
        //variables
        BaseMod.addDynamicVariable(new EnergyVariable());
        BaseMod.addDynamicVariable(new DynamicMagicVariable());

        //special cards
        BaseMod.addCard(new BasicDefend());
        BaseMod.addCard(new BasicAttack());
        BaseMod.addCard(new DrowsyDefend());
        BaseMod.addCard(new DrowsyAttack());
        BaseMod.addCard(new WokeDefend());
        BaseMod.addCard(new WokeAttack());

        //standard cards
        BaseMod.addCard(new Jab());
        BaseMod.addCard(new StumblingBlow());
        BaseMod.addCard(new RareAttack());
        BaseMod.addCard(new WakeUpSlap());
        BaseMod.addCard(new ThornCloak());
        BaseMod.addCard(new Parry());
        BaseMod.addCard(new CommonPower());
        BaseMod.addCard(new ExtraArm());
        BaseMod.addCard(new Reflect());

        BaseMod.addCard(new ChannelSkill());
        BaseMod.addCard(new MagicVarTest());

        //colorless cards
        BaseMod.addCard(new TheSeed());
        BaseMod.addCard(new Seed());
        BaseMod.addCard(new Bomb());

        //unlock?
        UnlockTracker.unlockCard(BasicDefend.ID);
        UnlockTracker.unlockCard(BasicAttack.ID);
        UnlockTracker.unlockCard(DrowsyAttack.ID);
        UnlockTracker.unlockCard(DrowsyDefend.ID);
        UnlockTracker.unlockCard(WokeAttack.ID);
        UnlockTracker.unlockCard(WokeDefend.ID);
        UnlockTracker.unlockCard(Jab.ID);
        UnlockTracker.unlockCard(StumblingBlow.ID);
        UnlockTracker.unlockCard(RareAttack.ID);
        UnlockTracker.unlockCard(WakeUpSlap.ID);
        UnlockTracker.unlockCard(ThornCloak.ID);
        UnlockTracker.unlockCard(Parry.ID);
        UnlockTracker.unlockCard(CommonPower.ID);
        UnlockTracker.unlockCard(ExtraArm.ID);
        UnlockTracker.unlockCard(Reflect.ID);
        UnlockTracker.unlockCard(ChannelSkill.ID);
        UnlockTracker.unlockCard(MagicVarTest.ID);
        UnlockTracker.unlockCard(TheSeed.ID);
        UnlockTracker.unlockCard(Seed.ID);
        UnlockTracker.unlockCard(Bomb.ID);


    }

    //TODO: autogenerate


    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class,
                modID + "Resources/localization/eng/FirstMod-Card-Strings.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                modID + "Resources/localization/eng/FirstMod-Character-Strings.json");
        BaseMod.loadCustomStringsFile(EventStrings.class,
                modID + "Resources/localization/eng/FirstMod-Event-Strings.json");
        BaseMod.loadCustomStringsFile(MonsterStrings.class,
                modID + "Resources/localization/eng/FirstMod-Monster-Strings.json");
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                modID + "Resources/localization/eng/FirstMod-Orb-Strings.json");
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                modID + "Resources/localization/eng/FirstMod-Potion-Strings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                modID + "Resources/localization/eng/FirstMod-Power-Strings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                modID + "Resources/localization/eng/FirstMod-Relic-Strings.json");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(modID + "Resources/localization/eng/FirstMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID.toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }


    @Override
    public void receivePostExhaust(AbstractCard c) {
        /*
        int val = 1;
        if (c.rarity == AbstractCard.CardRarity.UNCOMMON)
        {
            val = 2;
        }else if (c.rarity == AbstractCard.CardRarity.RARE)
        {
            val = 3;
        }
        AbstractDungeon.player.increaseMaxHp(val, true);
        */
    }

    @Override
    public int receiveMaxHPChange(int amt){

        incFirstRelicFloat(amt);

        if(AbstractDungeon.player.chosenClass == TheFirst.Enums.THE_FIRST){
            return 0;
        }

        return amt;
    }

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }



    public static void incFirstRelic(int amt)
    {
        if(AbstractDungeon.player.hasRelic("theFirst:FirstRelic")){
            AbstractCustomRelic r = (AbstractCustomRelic) AbstractDungeon.player.getRelic("theFirst:FirstRelic");
            r.onTrigger(amt);
        }
    }

    public static void incFirstRelicFloat(int amt)
    {
        if(AbstractDungeon.player.hasRelic("theFirst:FirstRelic")){
            AbstractCustomRelic r = (AbstractCustomRelic) AbstractDungeon.player.getRelic("theFirst:FirstRelic");
            r.onTriggerFloat(amt);
        }
    }



    @Override
    public void receiveStartAct() {
        if(AbstractDungeon.player.chosenClass == TheFirst.Enums.THE_FIRST){

            int a = AbstractDungeon.actNum;

            TheFirst.replaceCards(a);

        }
    }

    public static ArrayList<AbstractCard> generateByTag(int tag){

        ArrayList<AbstractCard> cardList = new ArrayList<>();

        NeowEvent neowEvent = new NeowEvent();
        try {
            Method method = neowEvent.getClass().getDeclaredMethod("blessing");
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }

        switch (tag)
        {
            case 0: //Basic
                cardList.add(new BasicAttack());
                cardList.add(new BasicDefend());
                break;
            case 1: //Drowsy
                cardList.add(new DrowsyAttack());
                cardList.add(new DrowsyDefend());
                break;
            case 2: //Woke
                cardList.add(new WokeAttack());
                cardList.add(new WokeDefend());
                break;
            default:
                logger.info("How did this happen?");
                break;
        }

        return cardList;
    }
}
