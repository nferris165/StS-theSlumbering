package theSlumbering.monsters;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.esotericsoftware.spine.Bone;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
import com.megacrit.cardcrawl.actions.animations.SetAnimationAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.vfx.NemesisFireParticle;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import theSlumbering.SlumberingMod;

import static theSlumbering.SlumberingMod.makeMonsterPath;


public class NewElite extends CustomMonster {
    public static final String ID = SlumberingMod.makeID("Adrasteia");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] MOVES = monsterstrings.MOVES;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP = 185;
    private static final int A_8_HP = 200;
    private static final int SCYTHE_COOLDOWN_TURNS = 2;
    private static final float HB_X = 5.0F;
    private static final float HB_Y = -10.0F;
    private static final float HB_W = 350.0F;
    private static final float HB_H = 440.0F;
    private static final int SCYTHE_DMG = 45;
    private static final int FIRE_DMG = 6;
    private static final int FIRE_TIMES = 3;
    private static final int A_3_FIRE_DMG = 7;
    private static final int BURN_AMT = 3;
    private static final int A_18_BURN_AMT = 5;
    private int fireDmg;
    private int scytheCooldown = 0;
    private int burnAmt;
    private static final byte TRI_ATTACK = 2;
    private static final byte SCYTHE = 3;
    private static final byte TRI_BURN = 4;
    private float fireTimer = 0.0F;
    private static final float FIRE_TIME = 0.05F;
    private Bone eye1;
    private Bone eye2;
    private Bone eye3;
    private boolean firstMove = true;
    private boolean firstTurn = true;

    public NewElite(float x, float y) {
        super(NAME, ID, HP, HB_X, HB_Y, HB_W, HB_H, (String) null, x, y);

        this.type = EnemyType.ELITE;

        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(A_8_HP);
        } else {
            this.setHp(HP);
        }

        if (AbstractDungeon.ascensionLevel >= 3) {
            this.fireDmg = A_3_FIRE_DMG;
        } else {
            this.fireDmg = FIRE_DMG;
        }

        if (AbstractDungeon.ascensionLevel >= 18) {
            this.burnAmt = A_18_BURN_AMT;
        } else {
            this.burnAmt = BURN_AMT;
        }

        this.damage.add(new DamageInfo(this, SCYTHE_DMG));
        this.damage.add(new DamageInfo(this, this.fireDmg));

        this.loadAnimation(makeMonsterPath("Adrasteia/skeleton.atlas"), makeMonsterPath("Adrasteia/skeleton.json"), 1.0F);
        TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.8F);
        this.eye1 = this.skeleton.findBone("eye0");
        this.eye2 = this.skeleton.findBone("eye1");
        this.eye3 = this.skeleton.findBone("eye2");
    }

    public void usePreBattleAction() {
        //start with buffs

    }

    public void takeTurn() {
        if (this.firstTurn) {
            //AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F));
            this.firstTurn = false;
        }

        switch(this.nextMove) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                this.playSfx();
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.SLASH_HEAVY));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_NEMESIS_1C"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect(
                        this.hb.cX, this.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 1.5F));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Burn(), this.burnAmt));
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        --this.scytheCooldown;
        if (this.firstMove) {
            this.firstMove = false;
            if (num < 50) {
                this.setMove((byte)2, Intent.ATTACK, this.fireDmg, FIRE_TIMES, true);
            } else {
                this.setMove((byte)4, Intent.DEBUFF);
            }

        } else {
            if (num < 30) {
                if (!this.lastMove((byte)3) && this.scytheCooldown <= 0) {
                    this.setMove((byte)3, Intent.ATTACK, SCYTHE_DMG);
                    this.scytheCooldown = SCYTHE_COOLDOWN_TURNS;
                } else if (AbstractDungeon.aiRng.randomBoolean()) {
                    if (!this.lastTwoMoves((byte)2)) {
                        this.setMove((byte)2, Intent.ATTACK, this.fireDmg, FIRE_TIMES, true);
                    } else {
                        this.setMove((byte)4, Intent.DEBUFF);
                    }
                } else if (!this.lastMove((byte)4)) {
                    this.setMove((byte)4, Intent.DEBUFF);
                } else {
                    this.setMove((byte)2, Intent.ATTACK, this.fireDmg, FIRE_TIMES, true);
                }
            } else if (num < 65) {
                if (!this.lastTwoMoves((byte)2)) {
                    this.setMove((byte)2, Intent.ATTACK, this.fireDmg, FIRE_TIMES, true);
                } else if (AbstractDungeon.aiRng.randomBoolean()) {
                    if (this.scytheCooldown > 0) {
                        this.setMove((byte)4, Intent.DEBUFF);
                    } else {
                        this.setMove((byte)3, Intent.ATTACK, SCYTHE_DMG);
                        this.scytheCooldown = SCYTHE_COOLDOWN_TURNS;
                    }
                } else {
                    this.setMove((byte)4, Intent.DEBUFF);
                }
            } else if (!this.lastMove((byte)4)) {
                this.setMove((byte)4, Intent.DEBUFF);
            } else if (AbstractDungeon.aiRng.randomBoolean() && this.scytheCooldown <= 0) {
                this.setMove((byte)3, Intent.ATTACK, SCYTHE_DMG);
                this.scytheCooldown = SCYTHE_COOLDOWN_TURNS;
            } else {
                this.setMove((byte)2, Intent.ATTACK, this.fireDmg, 3, true);
            }

        }
    }

    private void playSfx() {
        int roll = MathUtils.random(1);
        if (roll == 0) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_NEMESIS_1A"));
        } else {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_NEMESIS_1B"));
        }

    }

    private void playDeathSfx() {
        int roll = MathUtils.random(1);
        if (roll == 0) {
            CardCrawlGame.sound.play("Pop");
        } else {
            //alternate?
            CardCrawlGame.sound.play("Pop");
        }

    }

    public void damage(DamageInfo info) {
        if (info.owner != null && info.type == DamageInfo.DamageType.NORMAL && info.output > 0) {
            TrackEntry e = this.state.setAnimation(0, "Hit", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
            e.setTimeScale(0.8F);
        }
        super.damage(info);
    }

    public void changeState(String key) {
        byte var3 = -1;
        switch(key.hashCode()) {
            case 1941037640:
                if (key.equals("ATTACK")) {
                    var3 = 0;
                }
            default:
                switch(var3) {
                    case 0:
                        TrackEntry e = this.state.setAnimation(0, "Attack", false);
                        this.state.addAnimation(0, "Idle", true, 0.0F);
                        e.setTimeScale(0.8F);
                    default:
                }
        }
    }

    public void die() {
        super.die();
        playDeathSfx();
    }

    public void update() {
        super.update();
        if (!this.isDying) {
            this.fireTimer -= Gdx.graphics.getDeltaTime();
            if (this.fireTimer < 0.0F) {
                this.fireTimer = 0.05F;
                AbstractDungeon.effectList.add(new NemesisFireParticle(this.skeleton.getX() + this.eye1.getWorldX(), this.skeleton.getY() + this.eye1.getWorldY()));
                AbstractDungeon.effectList.add(new NemesisFireParticle(this.skeleton.getX() + this.eye2.getWorldX(), this.skeleton.getY() + this.eye2.getWorldY()));
                AbstractDungeon.effectList.add(new NemesisFireParticle(this.skeleton.getX() + this.eye3.getWorldX(), this.skeleton.getY() + this.eye3.getWorldY()));
            }
        }

    }

}
