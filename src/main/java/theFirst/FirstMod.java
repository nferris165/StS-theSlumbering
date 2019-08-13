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
import com.evacipated.cardcrawl.modthespire.Loader;
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
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import javassist.CtClass;
import javassist.NotFoundException;
import org.clapper.util.classutil.*;
import theFirst.cards.BasicDefend;
import theFirst.characters.TheFirst;
import theFirst.events.BasicEvent;
import theFirst.monsters.SimpleMonster;
import theFirst.patches.customTags;
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

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;


@SuppressWarnings("WeakerAccess")
@SpireInitializer
public class FirstMod implements
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
        BaseMod.addRelicToCustomPool(new StarMobile(), TheFirst.Enums.COLOR_FIRST);

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
        UnlockTracker.markRelicAsSeen(StarMobile.ID);

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
        BaseMod.loadCustomStringsFile(UIStrings.class,
                modID + "Resources/localization/eng/FirstMod-UI-Strings.json");
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

    private static void autoAddCards() throws URISyntaxException, IllegalAccessException, InstantiationException, NotFoundException, ClassNotFoundException
    {
        ClassFinder finder = new ClassFinder();
        URL url = FirstMod.class.getProtectionDomain().getCodeSource().getLocation();
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
        if(AbstractDungeon.player.hasRelic(FirstRelic.ID)){
            AbstractCustomRelic r = (AbstractCustomRelic) AbstractDungeon.player.getRelic(FirstRelic.ID);
            r.onTrigger(amt);
        }
    }

    public static void incFirstRelicFloat(int amt)
    {
        if(AbstractDungeon.player.hasRelic(FirstRelic.ID)){
            AbstractCustomRelic r = (AbstractCustomRelic) AbstractDungeon.player.getRelic(FirstRelic.ID);
            r.onTriggerFloat(amt);
        }
    }


    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {

        ArrayList<AbstractCard> removeList = new ArrayList<>();

        for(AbstractCard c: AbstractDungeon.player.drawPile.group){
            if(c.hasTag(customTags.Passive)){
                logger.info(AbstractDungeon.player.drawPile.group + "\n\n");
                ((AbstractCustomCard) c).passiveEffect();
                removeList.add(c);
            }
        }

        for (AbstractCard c: removeList) {
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c,
                    (float) Settings.WIDTH / 2.0F - 30.0F * Settings.scale - AbstractCard.IMG_WIDTH / 2.0F,(float) Settings.HEIGHT / 2.0F));
            AbstractDungeon.player.drawPile.removeCard(c);
            logger.info(AbstractDungeon.player.drawPile.group + "\n\n");
        }
    }

    @Override
    public void receiveStartAct() {
        if(AbstractDungeon.player.chosenClass == TheFirst.Enums.THE_FIRST){
            int a = AbstractDungeon.actNum;
            TheFirst.replaceCards(a);
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
