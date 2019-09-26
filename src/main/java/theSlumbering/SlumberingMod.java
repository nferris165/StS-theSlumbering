package theSlumbering;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.ReflectionHacks;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.relics.MarkOfTheBloom;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import javassist.CtClass;
import javassist.NotFoundException;
import org.clapper.util.classutil.*;
import theSlumbering.cards.BasicDefend;
import theSlumbering.characters.TheSlumbering;
import theSlumbering.events.MysteriousOrb;
import theSlumbering.events.PoweredUp;
import theSlumbering.monsters.Adrasteia;
import theSlumbering.monsters.NewSlaver;
import theSlumbering.monsters.NewCultist;
import theSlumbering.patches.customTags;
import theSlumbering.relics.AbstractCustomRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSlumbering.cards.*;
import theSlumbering.potions.NewPotion;
import theSlumbering.relics.*;
import theSlumbering.util.TextureLoader;
import theSlumbering.variables.EnergyVariable;
import theSlumbering.variables.DynamicMagicVariable;
import theSlumbering.relics.SlumberingRelic;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Properties;


@SuppressWarnings("WeakerAccess")
@SpireInitializer
public class SlumberingMod implements
        StartActSubscriber,
        OnStartBattleSubscriber,
        MaxHPChangeSubscriber,
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostExhaustSubscriber,
        PostInitializeSubscriber {

    public static final Logger logger = LogManager.getLogger(SlumberingMod.class.getName());

    //mod settings
    public static Properties defaultSettings = new Properties();
    public static final String event_sharing_settings = "eventSharing";
    public static boolean eventSharing = false;

    private static final String MODNAME = "Slumbering Mod";
    private static final String AUTHOR = "Nichilas";
    private static final String DESCRIPTION = "My first mod, ft. the Slumbering.";



    //char main color
    public static final Color SLUMBERING_TEAL = CardHelper.getColor(59.0f, 125.0f, 99.0f);

    public static final Color SLUMBERING_POTION_RUST = CardHelper.getColor(211.0f, 40.0f, 2.0f);

    private static final String ATTACK_CARD_SLUMBERING = "theSlumberingResources/images/512/bg_attack_first_color.png";
    private static final String SKILL_CARD_SLUMBERING = "theSlumberingResources/images/512/bg_skill_first_color.png";
    private static final String POWER_CARD_SLUMBERING = "theSlumberingResources/images/512/bg_power_first_color.png";

    private static final String ENERGY_ORB_SLUMBERING = "theSlumberingResources/images/512/card_first_color_orb.png";
    private static final String CARD_ENERGY_ORB = "theSlumberingResources/images/512/card_small_orb.png";

    private static final String ATTACK_SLUMBERING_PORTRAIT = "theSlumberingResources/images/1024/bg_attack_first_color.png";
    private static final String SKILL_SLUMBERING_PORTRAIT = "theSlumberingResources/images/1024/bg_skill_first_color.png";
    private static final String POWER_SLUMBERING_PORTRAIT = "theSlumberingResources/images/1024/bg_power_first_color.png";
    private static final String ENERGY_ORB_SLUMBERING_PORTRAIT = "theSlumberingResources/images/1024/card_first_color_orb.png";

    private static final String THE_SLUMBERING_BUTTON = "theSlumberingResources/images/charSelect/firstButton.png";
    private static final String THE_SLUMBERING_PORTRAIT = "theSlumberingResources/images/charSelect/FirstPortraitBG.png";
    public static final String THE_SLUMBERING_SHOULDER_1 = "theSlumberingResources/images/char/slumberingCharacter/shoulder.png";
    public static final String THE_SLUMBERING_SHOULDER_2 = "theSlumberingResources/images/char/slumberingCharacter/shoulder2.png";
    public static final String THE_SLUMBERING_CORPSE = "theSlumberingResources/images/char/slumberingCharacter/corpse.png";

    public static final String BADGE_IMAGE = "theSlumberingResources/images/Badge.png";

    public static final String THE_SLUMBERING_SKELETON_ATLAS = "theSlumberingResources/images/char/slumberingCharacter/skeleton.atlas";
    public static final String THE_SLUMBERING_SKELETON_JSON = "theSlumberingResources/images/char/slumberingCharacter/skeleton.json";

    private static final String AUDIO_PATH = "theSlumberingResources/audio/";

    private static final String modID = "theSlumbering";


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

    public static String makeUIPath(String resourcePath) {
        return modID + "Resources/images/ui/" + resourcePath;
    }

    public static String makeVfxPath(String resourcePath) {
        return modID + "Resources/images/vfx/" + resourcePath;
    }

    public SlumberingMod() {
        BaseMod.subscribe(this);

        BaseMod.addColor(TheSlumbering.Enums.COLOR_SLUMBERING, SLUMBERING_TEAL, SLUMBERING_TEAL, SLUMBERING_TEAL,
                SLUMBERING_TEAL, SLUMBERING_TEAL, SLUMBERING_TEAL, SLUMBERING_TEAL,
                ATTACK_CARD_SLUMBERING, SKILL_CARD_SLUMBERING, POWER_CARD_SLUMBERING, ENERGY_ORB_SLUMBERING,
                ATTACK_SLUMBERING_PORTRAIT, SKILL_SLUMBERING_PORTRAIT, POWER_SLUMBERING_PORTRAIT,
                ENERGY_ORB_SLUMBERING_PORTRAIT, CARD_ENERGY_ORB);

        //TODO finalize mod settings
        logger.info("Adding mod settings");
        defaultSettings.setProperty(event_sharing_settings, "FALSE");
        try {
            SpireConfig config = new SpireConfig("slumberingMod", "theSlumberingConfig", defaultSettings);
            config.load();
            eventSharing = config.getBool(event_sharing_settings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unused")
    public static void initialize() {
        SlumberingMod slumberingmod = new SlumberingMod();
    }

    @Override
    public void receiveEditCharacters() {

        BaseMod.addCharacter(new TheSlumbering("the Slumbering", TheSlumbering.Enums.THE_SLUMBERING),
                THE_SLUMBERING_BUTTON, THE_SLUMBERING_PORTRAIT, TheSlumbering.Enums.THE_SLUMBERING);

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
                        SpireConfig config = new SpireConfig("slumberingMod", "theSlumberingConfig", defaultSettings);
                        config.setBool(event_sharing_settings, eventSharing);
                        config.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        settingsPanel.addUIElement(enableNormalsButton);
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        //events
        BaseMod.addEvent(MysteriousOrb.ID, MysteriousOrb.class, Exordium.ID);
        BaseMod.addEvent(PoweredUp.ID, PoweredUp.class, TheCity.ID);

        //monsters
        //BaseMod.addMonster(SimpleMonster.ID, "Simple Monster", () -> new SimpleMonster(0.0F, 25.0F));
        BaseMod.addMonster(NewSlaver.ID, "NewSlaver", () -> new NewSlaver(0.0F, 0.0F));
        BaseMod.addMonster(NewCultist.ID, "NewCultist", () -> new NewCultist(0.0F, -10.0F));

        BaseMod.addMonster(Adrasteia.ID, "Adrasteia", () -> new Adrasteia(0.0F, 0.0F));

        //encounters
        //BaseMod.addMonsterEncounter(Exordium.ID, new MonsterInfo(SimpleMonster.ID, 2));
        BaseMod.addMonsterEncounter(Exordium.ID, new MonsterInfo(NewCultist.ID, 3.0F));  //normal weight 2
        BaseMod.addStrongMonsterEncounter(Exordium.ID, new MonsterInfo(NewSlaver.ID, 1.5F));

        BaseMod.addEliteEncounter(Exordium.ID, new MonsterInfo(Adrasteia.ID, 1.5F)); //normal weight 1

        //audio
        loadAudio();

    }

    public void receiveEditPotions() {
        BaseMod.addPotion(NewPotion.class, SLUMBERING_POTION_RUST, SLUMBERING_TEAL, SLUMBERING_POTION_RUST, NewPotion.POTION_ID, TheSlumbering.Enums.THE_SLUMBERING);
        //BaseMod.addPotion(NewPotion.class, SLUMBERING_POTION_RUST, SLUMBERING_TEAL, SLUMBERING_POTION_RUST, NewPotion.POTION_ID);

    }


    @Override
    public void receiveEditRelics() {
        //character only
        BaseMod.addRelicToCustomPool(new HeartCollector(), TheSlumbering.Enums.COLOR_SLUMBERING);
        BaseMod.addRelicToCustomPool(new SlumberingRelic(), TheSlumbering.Enums.COLOR_SLUMBERING);
        BaseMod.addRelicToCustomPool(new StarMobile(), TheSlumbering.Enums.COLOR_SLUMBERING);
        BaseMod.addRelicToCustomPool(new PowerFromBeyond(), TheSlumbering.Enums.COLOR_SLUMBERING);

        //shared
        BaseMod.addRelic(new GlassShield(), RelicType.SHARED);
        BaseMod.addRelic(new SlimeShield(), RelicType.SHARED);
        BaseMod.addRelic(new CursedAnvil(), RelicType.SHARED);
        BaseMod.addRelic(new EnchantedHammer(), RelicType.SHARED);
        BaseMod.addRelic(new GamblerFolly(), RelicType.SHARED);
        BaseMod.addRelic(new CrystalEffigy(), RelicType.SHARED);

        //unlock?
        UnlockTracker.markRelicAsSeen(GlassShield.ID);
        UnlockTracker.markRelicAsSeen(SlimeShield.ID);
        UnlockTracker.markRelicAsSeen(CursedAnvil.ID);
        UnlockTracker.markRelicAsSeen(EnchantedHammer.ID);
        UnlockTracker.markRelicAsSeen(GamblerFolly.ID);
        UnlockTracker.markRelicAsSeen(StarMobile.ID);
        UnlockTracker.markRelicAsSeen(PowerFromBeyond.ID);
        UnlockTracker.markRelicAsSeen(CrystalEffigy.ID);

    }


    @Override
    public void receiveEditCards() {

        try {
            autoAddCards();
        } catch (URISyntaxException | IllegalAccessException | InstantiationException | NotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //variables
        BaseMod.addDynamicVariable(new EnergyVariable());
        BaseMod.addDynamicVariable(new DynamicMagicVariable());

    }



    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class,
                modID + "Resources/localization/eng/SlumberingMod-Card-Strings.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                modID + "Resources/localization/eng/SlumberingMod-Character-Strings.json");
        BaseMod.loadCustomStringsFile(EventStrings.class,
                modID + "Resources/localization/eng/SlumberingMod-Event-Strings.json");
        BaseMod.loadCustomStringsFile(MonsterStrings.class,
                modID + "Resources/localization/eng/SlumberingMod-Monster-Strings.json");
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                modID + "Resources/localization/eng/SlumberingMod-Orb-Strings.json");
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                modID + "Resources/localization/eng/SlumberingMod-Potion-Strings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                modID + "Resources/localization/eng/SlumberingMod-Power-Strings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                modID + "Resources/localization/eng/SlumberingMod-Relic-Strings.json");
        BaseMod.loadCustomStringsFile(UIStrings.class,
                modID + "Resources/localization/eng/SlumberingMod-UI-Strings.json");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(modID + "Resources/localization/eng/SlumberingMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID.toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    public void loadAudio() {
        HashMap<String, Sfx> map = (HashMap<String, Sfx>) ReflectionHacks.getPrivate(CardCrawlGame.sound, SoundMaster.class, "map");
        map.put("Pop", new Sfx(AUDIO_PATH + "pop.ogg", false));
    }

    private static void autoAddCards() throws URISyntaxException, IllegalAccessException, InstantiationException, NotFoundException, ClassNotFoundException
    {
        ClassFinder finder = new ClassFinder();
        URL url = SlumberingMod.class.getProtectionDomain().getCodeSource().getLocation();
        finder.add(new File(url.toURI()));

        ClassFilter filter =
                new AndClassFilter(
                        new NotClassFilter(new InterfaceOnlyClassFilter()),
                        new NotClassFilter(new AbstractClassFilter()),
                        new ClassModifiersClassFilter(Modifier.PUBLIC),
                        new CardFilter()
                );
        Collection<ClassInfo> foundClasses = new ArrayList<>();
        finder.findClasses(foundClasses, filter);

        for (ClassInfo classInfo : foundClasses) {
            CtClass cls = Loader.getClassPool().get(classInfo.getClassName());
            if (cls.hasAnnotation(CardIgnore.class)) {
                continue;
            }
            boolean isCard = false;
            CtClass superCls = cls;
            while (superCls != null) {
                superCls = superCls.getSuperclass();
                if (superCls == null) {
                    break;
                }
                if (superCls.getName().equals(AbstractCard.class.getName())) {
                    isCard = true;
                    break;
                }
            }
            if (!isCard) {
                continue;
            }

            System.out.println(classInfo.getClassName());
            AbstractCard card = (AbstractCard) Loader.getClassPool().getClassLoader().loadClass(cls.getName()).newInstance();
            BaseMod.addCard(card);
            if (cls.hasAnnotation(CardNoSeen.class)) {
                UnlockTracker.hardUnlockOverride(card.cardID);
            } else {
                UnlockTracker.unlockCard(card.cardID);
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

        incSlumberingRelicFloat(amt);

        if(AbstractDungeon.player.chosenClass == TheSlumbering.Enums.THE_SLUMBERING){
            return 0;
        }

        return amt;
    }

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }



    public static void incSlumberingRelic(int amt)
    {
        if(AbstractDungeon.player.hasRelic(SlumberingRelic.ID)){
            AbstractCustomRelic r = (AbstractCustomRelic) AbstractDungeon.player.getRelic(SlumberingRelic.ID);
            r.onTrigger(amt);
        }
    }

    public static void incSlumberingRelicFloat(int amt)
    {
        if(AbstractDungeon.player.hasRelic(SlumberingRelic.ID)){
            AbstractCustomRelic r = (AbstractCustomRelic) AbstractDungeon.player.getRelic(SlumberingRelic.ID);
            r.onTriggerFloat(amt);
        }
    }

    public static void decHeartCollectorRelic(int amt){
        if(AbstractDungeon.player.hasRelic(HeartCollector.ID)){
            AbstractCustomRelic r = (AbstractCustomRelic) AbstractDungeon.player.getRelic(HeartCollector.ID);
            if(amt < 0 && AbstractDungeon.player.hasRelic(MarkOfTheBloom.ID)){
                AbstractDungeon.player.getRelic(MarkOfTheBloom.ID).flash();
            }
            else{
                r.onTrigger(amt);
            }
        }
    }


    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {

        ArrayList<AbstractCard> removeList = new ArrayList<>();

        // Check for Tags
        for(AbstractCard c: AbstractDungeon.player.drawPile.group){
            if(c.hasTag(customTags.Passive)){
                ((AbstractCustomCard) c).passiveEffect();
                removeList.add(c);
            }
            if(c.hasTag(customTags.ActionCurse)){
                ((AbstractCustomCard) c).passiveEffect();
            }
        }

        for (AbstractCard c: removeList) {
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c,
                    (float) Settings.WIDTH / 2.0F - 30.0F * Settings.scale - AbstractCard.IMG_WIDTH / 2.0F,(float) Settings.HEIGHT / 2.0F));
            AbstractDungeon.player.drawPile.removeCard(c);
        }

    }

    @Override
    public void receiveStartAct() {
        if(AbstractDungeon.player instanceof TheSlumbering){
            int a = AbstractDungeon.actNum;
            TheSlumbering.replaceCards(a);
        }
    }

    public static boolean passiveCheck(AbstractCard card){
        boolean val = false;

        if(card.hasTag(customTags.Passive)){
            val = true;
        }

        return val;
    }

    public static ArrayList<AbstractCard> generateByTag(int tag){

        ArrayList<AbstractCard> cardList = new ArrayList<>();

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
