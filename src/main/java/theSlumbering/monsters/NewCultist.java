package theSlumbering.monsters;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import theSlumbering.SlumberingMod;
import theSlumbering.powers.DreamRitualPower;

import static theSlumbering.SlumberingMod.makeMonsterPath;


public class NewCultist extends CustomMonster {
    public static final String ID = SlumberingMod.makeID("NewCultist");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    private static final String[] MOVES = monsterstrings.MOVES;
    private static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 38;
    private static final int HP_MAX = 44;
    private static final int A_7_HP_MIN = 42;
    private static final int A_7_HP_MAX = 48;
    private static final float HB_X = -8.0F;
    private static final float HB_Y = 10.0F;
    private static final float HB_W = 230.0F;
    private static final float HB_H = 240.0F;
    private static final int ATTACK_DMG = 1;
    private static final int ATTACK_BLOCK = 10;
    private static final int A_7_ATTACK_DMG = 2;
    private static final int A_7_ATTACK_BLOCK = 11;
    private static final int POWER_ATTACK_DMG = 25;
    private boolean firstMove;
    private boolean saidPower;
    private static final int RITUAL_AMT = 1;
    private static final int A_2_RITUAL_AMT = 2;
    private int ritualAmount;
    private int attackBlockDmg;
    public int attackBlockBlock;
    private static final byte DARK_STRIKE = 1;
    private static final byte INCANTATION = 3;
    private boolean talky;
    private boolean elder;

    public NewCultist(float x, float y, boolean talk) {
        super(NAME, ID, 44, HB_X, HB_Y, HB_W, HB_H, (String)null, x, y);

        this.elder = AbstractDungeon.aiRng.random(99) >= 95;

        this.firstMove = true;
        this.saidPower = false;
        this.ritualAmount = 0;
        this.talky = true;

        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(A_7_HP_MIN, A_7_HP_MAX);
            this.attackBlockDmg = A_7_ATTACK_DMG;
            this.attackBlockBlock = A_7_ATTACK_BLOCK;
        } else {
            this.setHp(HP_MIN, HP_MAX);
            this.attackBlockDmg = ATTACK_DMG;
            this.attackBlockBlock = ATTACK_BLOCK;
        }

        this.dialogX = -50.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 2) {
            this.ritualAmount = A_2_RITUAL_AMT;
        } else {
            this.ritualAmount = RITUAL_AMT;
        }

        this.damage.add(new DamageInfo(this, this.attackBlockDmg));
        this.damage.add(new DamageInfo(this, POWER_ATTACK_DMG));
        this.talky = talk;

        this.loadAnimation(makeMonsterPath("NewCultist/skeleton.atlas"), makeMonsterPath("NewCultist/skeleton.json"), 1.0F);
        TrackEntry e = this.state.setAnimation(0, "waving", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public NewCultist(float x, float y){
        this(x, y, true);
    }

    public void usePreBattleAction() {
        //start with buffs
        //AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.buffBlock));
        if(elder){
            //elders?
        }
    }

    public void takeTurn() {
        switch(this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.SLASH_HORIZONTAL));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.attackBlockBlock));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[2], 1.0F, 2.0F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AttackEffect.SLASH_HEAVY));
                break;
            case 3:
                if (this.talky) {
                    this.playSfx();
                    AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 1.0F, 2.0F));
                    this.saidPower = true;
                }

                if (AbstractDungeon.ascensionLevel >= 17) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new DreamRitualPower(this, this.ritualAmount + 1)));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new DreamRitualPower(this, this.ritualAmount)));
                }
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if (this.firstMove) {
            this.firstMove = false;
            this.setMove(MOVES[2], (byte)3, Intent.BUFF);
        } else if(GameActionManager.turn == 6){
            this.setMove((byte)2, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base);
        } else {
            this.setMove((byte)1, Intent.ATTACK_DEFEND, ((DamageInfo)this.damage.get(0)).base);
        }

    }

    private void playSfx() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1A"));
        } else if (roll == 1) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1B"));
        } else {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1C"));
        }

    }

    private void playDeathSfx() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_CULTIST_2A");
        } else if (roll == 1) {
            CardCrawlGame.sound.play("VO_CULTIST_2B");
        } else {
            CardCrawlGame.sound.play("VO_CULTIST_2C");
        }

    }

    public void die() {
        this.playDeathSfx();
        this.state.setTimeScale(0.1F);
        this.useShakeAnimation(5.0F);
        if (this.talky && this.saidPower) {
            AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX, this.hb.cY + this.dialogY, 2.5F, DIALOG[1], false));
            ++this.deathTimer;
        }
        super.die();
    }

}
