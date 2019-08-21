package theFirst.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import theFirst.SlumberingMod;
import theFirst.cards.AbstractCustomCard;

import java.lang.reflect.InvocationTargetException;
        import java.lang.reflect.Method;

public class RenderBgPatch {
    private static Method cardRenderHelperMethod;
    private static Method screenRenderHelperMethod;
    private static boolean renderReflectFailureNotified = false;
    private static boolean renderActualFailureNotified = false;

    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderCardBg"
    )
    public static class AbstractCardRenderCardBgPatch {
        private static TextureAtlas.AtlasRegion region = new TextureAtlas.AtlasRegion(
                ImageMaster.loadImage(AbstractCustomCard.BG_SMALL_PASSIVE_FIELD), 0, 0, 512, 512);

        public static void Postfix(AbstractCard __card_instance, SpriteBatch sb, float x, float y) {
            if (cardRenderHelperMethod == null) {
                try {
                    cardRenderHelperMethod = AbstractCard.class.getDeclaredMethod(
                            "renderHelper", SpriteBatch.class, Color.class,
                            TextureAtlas.AtlasRegion.class, float.class, float.class);
                } catch (NoSuchMethodException e) {
                    if (!renderReflectFailureNotified) {
                        renderReflectFailureNotified = true;
                        System.out.println("ALERT: Failed to reflect AbstractCard method renderHelper");
                        e.printStackTrace();
                    }
                }
            }

            boolean isPassive = SlumberingMod.passiveCheck(__card_instance);

            if (isPassive) {
                try {
                    Color reflectedColor = (Color) ReflectionHacks.getPrivate(
                            __card_instance, AbstractCard.class, "renderColor");
                    cardRenderHelperMethod.setAccessible(true);
                    cardRenderHelperMethod.invoke(__card_instance, sb, reflectedColor, region, x, y);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    if (!renderActualFailureNotified) {
                        renderActualFailureNotified = true;
                        System.out.println("ALERT: Failed to invoke AbstractCard method renderHelper");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "renderCardBack"
    )
    public static class SingleCardViewPopupRenderCardBackPatch {
        private static TextureAtlas.AtlasRegion region = new TextureAtlas.AtlasRegion(
                ImageMaster.loadImage(AbstractCustomCard.BG_LARGE_PASSIVE_FIELD), 0, 0, 1024, 1024);

        public static void Postfix(SingleCardViewPopup __instance, SpriteBatch sb) {
            AbstractCard reflectedCard = (AbstractCard) ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
            if (screenRenderHelperMethod == null) {
                try {
                    screenRenderHelperMethod = SingleCardViewPopup.class.getDeclaredMethod(
                            "renderHelper", SpriteBatch.class, float.class, float.class, TextureAtlas.AtlasRegion.class);
                } catch (NoSuchMethodException e) {
                    if (!renderReflectFailureNotified) {
                        renderReflectFailureNotified = true;
                        System.out.println("ALERT: Failed to reflect SingleCardViewPopup method renderHelper");
                        e.printStackTrace();
                    }
                }
            }

            boolean isPassive = SlumberingMod.passiveCheck(reflectedCard);

            if (isPassive) {
                try {
                    screenRenderHelperMethod.setAccessible(true);
                    screenRenderHelperMethod.invoke(__instance, sb,
                            Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, region);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    if (!renderActualFailureNotified) {
                        renderActualFailureNotified = true;
                        System.out.println("ALERT: Failed to invoke SingleCardViewPopup method renderHelper");
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}