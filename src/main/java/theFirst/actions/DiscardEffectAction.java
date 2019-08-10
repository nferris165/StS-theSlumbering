package theFirst.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AutoplayCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Buffer;
import com.megacrit.cardcrawl.cards.colorless.Panacea;
import com.megacrit.cardcrawl.cards.curses.Clumsy;
import com.megacrit.cardcrawl.cards.green.Blur;
import com.megacrit.cardcrawl.cards.red.Uppercut;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFirst.FirstMod;

import java.util.Iterator;

public class DiscardEffectAction extends AbstractGameAction {

    private AbstractPlayer p;
    private boolean endTurn;
    private boolean isRandom;
    private boolean upgraded;

    public static final String ACTION_ID = FirstMod.makeID("DiscardEffectAction");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
    private static final String[] TEXT = uiStrings.TEXT;

    public DiscardEffectAction(AbstractCreature target, AbstractCreature source, int amount, boolean upgraded, boolean isRandom) {
        this(target, source, amount, upgraded, isRandom,false);
    }


    public DiscardEffectAction(AbstractCreature target, AbstractCreature source, int amount, boolean upgraded, boolean isRandom, boolean endTurn) {

        this.p = AbstractDungeon.player;
        this.setValues(target, source, amount);
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.DISCARD;
        this.endTurn = endTurn;
        this.isRandom = isRandom;
        this.upgraded = upgraded;
    }

    private void discard(AbstractCard card){
        this.p.hand.moveToDiscardPile(card);
        card.triggerOnManualDiscard();
        GameActionManager.incrementDiscard(this.endTurn);
    }

    private AbstractCard getCard(AbstractCard.CardType type){
        AbstractCard card;

        switch (type)
        {
            case ATTACK:
                card = new Uppercut();
                break;
            case SKILL:
                card = new Blur();
                break;
            case POWER:
                card = new Buffer();
                break;
            case STATUS:
                card = new Dazed();
                this.isDone = true;
                break;
            case CURSE:
                card = new Clumsy();
                this.isDone = true;
                break;
            default:
                card = new Panacea();
                break;
        }

        if(this.upgraded){
            card.upgrade();
        }

        return card;
    }

    private void cardEffect(AbstractCard card){
        AbstractCard.CardType type = card.type;
        AbstractMonster monTarget = AbstractDungeon.getRandomMonster();

        AbstractCard c = getCard(type);

        if (c.cost > 0) {
            c.freeToPlayOnce = true;
        }
        c.exhaustOnUseOnce = true;
        c.purgeOnUse = true;


        if (!c.canUse(AbstractDungeon.player, monTarget)) {
            if (c.exhaustOnUseOnce) {
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.limbo));
            } else {
                AbstractDungeon.actionManager.addToTop(new UnlimboAction(c));
                AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(c, AbstractDungeon.player.limbo));
                AbstractDungeon.actionManager.addToTop(new WaitAction(0.4F));
            }
        } else {
            c.applyPowers();
            AbstractDungeon.actionManager.addToTop(new QueueCardAction(c, monTarget));
            AbstractDungeon.actionManager.addToTop(new UnlimboAction(c));
            if (!Settings.FAST_MODE) {
                AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
            } else {
                AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
            }
        }
        //AbstractDungeon.actionManager.addToBottom(new ShowCardAction(c));
        //AbstractDungeon.actionManager.addToBottom(new QueueCardAction(c, monTarget));
    }

    @Override
    public void update() {
        AbstractCard c;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }

            int i;
            if (this.p.hand.size() <= this.amount) {
                this.amount = this.p.hand.size();
                i = this.p.hand.size();

                for(int j = 0; j < i; ++j) {
                    AbstractCard c2 = this.p.hand.getTopCard();
                    cardEffect(c2);
                    this.p.hand.moveToDiscardPile(c2);
                    if (!this.endTurn) {
                        c2.triggerOnManualDiscard();
                    }

                    GameActionManager.incrementDiscard(this.endTurn);
                }

                AbstractDungeon.player.hand.applyPowers();
                this.tickDuration();
                return;
            }

            if (!this.isRandom) {
                if (this.amount < 0) {
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
                    AbstractDungeon.player.hand.applyPowers();
                    this.tickDuration();
                    return;
                }

                if (this.p.hand.size() > this.amount) {
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false);
                }

                AbstractDungeon.player.hand.applyPowers();
                this.tickDuration();
                return;
            }

            // if random
            for(i = 0; i < this.amount; ++i) {
                c = this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
                cardEffect(c);
                discard(c);
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {

            for (AbstractCard abstractCard : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                c = abstractCard;
                cardEffect(c);
                discard(c);
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
    }
}