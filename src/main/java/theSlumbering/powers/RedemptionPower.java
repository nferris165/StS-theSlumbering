package theSlumbering.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnPlayerDeathPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSlumbering.SlumberingMod;
import theSlumbering.cards.*;
import theSlumbering.util.TextureLoader;

public class RedemptionPower extends AbstractCustomPower implements CloneablePowerInterface, InvisiblePower, OnPlayerDeathPower {
    @SuppressWarnings("WeakerAccess")

    public static final String POWER_ID = SlumberingMod.makeID("RedemptionPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theSlumberingResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theSlumberingResources/images/powers/placeholder_power32.png");

    private AbstractCard card;

    public RedemptionPower(final AbstractCreature owner, AbstractCard card) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.card = card;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public void onRemove() {
        //AbstractDungeon.actionManager.addToBottom(new ShowCardAndPoofAction(card.makeStatEquivalentCopy()));
    }

    @Override
    public AbstractPower makeCopy() {
        return new RedemptionPower(owner, card);
    }

    @Override
    public boolean onPlayerDeath(AbstractPlayer abstractPlayer, DamageInfo damageInfo) {
        abstractPlayer.heal(1);
        AbstractCard remCard = null;

        for(AbstractCard c: abstractPlayer.masterDeck.group){
            if(c.uuid == card.uuid){
                remCard = c;
            }
        }
        if(remCard != null) {
            abstractPlayer.masterDeck.removeCard(remCard);
        }


        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(abstractPlayer, abstractPlayer, this));

        return false;
    }
}
