package theSlumbering.monsters;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
import com.megacrit.cardcrawl.actions.animations.SetAnimationAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import theSlumbering.SlumberingMod;

import static theSlumbering.SlumberingMod.makeMonsterPath;


public class SimpleMonster extends CustomMonster {
    public static final String ID = SlumberingMod.makeID("SimpleMonster");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] MOVES = monsterstrings.MOVES;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 40;
    private static final int HP_MAX = 44;
    private static final int A_7_HP_MIN = 42;
    private static final int A_7_HP_MAX = 46;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = -25.0F;
    private static final float HB_W = 260.0F;
    private static final float HB_H = 170.0F;
    private static final int ATTACK1_DMG = 11;
    private static final int A_2_ATTACK1_DMG = 12;
    private static final int ATTACKBLOCK_DMG = 7;
    private static final int ATTACKBLOCK_BLOCK = 5;
    private static final int BUFF_STR = 3;
    private static final int A_2_BUFF_STR = 4;
    private static final int A_17_BUFF_STR = 5;
    private static final int BUFF_BLOCK = 6;
    private static final int A_17_BUFF_BLOCK = 9;
    private boolean elder;
    private static final float eMod = 1.15F;
    private int buffBlock;
    private int attack1Dmg;
    private int attackBlockDmg;
    private int attackBlockBlock;
    private int buffStr;
    private static final byte ATTACK1 = 1; // These bytes are referred to for attacks.
    private static final byte BUFF = 2;
    private static final byte ATTACKBLOCK = 3;
    private boolean firstTurn = true;

    public SimpleMonster(float x, float y) {
        super(NAME, "JawWorm", 44, HB_X, HB_Y, HB_W, HB_H, (String)null, x, y);

        elder = AbstractDungeon.aiRng.random(99) >= 95;

        if (AbstractDungeon.ascensionLevel >= 7) {
            if(elder){
                this.setHp((int)(A_7_HP_MIN * eMod), (int)(A_7_HP_MAX * eMod));
            }
            else{
                this.setHp(A_7_HP_MIN, A_7_HP_MAX);
            }
        } else {
            if(elder){
                this.setHp((int)(HP_MIN * eMod), (int)(HP_MAX * eMod));
            }
            else{
                this.setHp(HP_MIN, HP_MAX);
            }
        }

        if (AbstractDungeon.ascensionLevel >= 17) {
            this.buffStr = A_17_BUFF_STR;
            this.buffBlock = A_17_BUFF_BLOCK;
            this.attack1Dmg = A_2_ATTACK1_DMG ;
            this.attackBlockDmg = ATTACKBLOCK_DMG;
            this.attackBlockBlock = ATTACKBLOCK_BLOCK;
        } else if (AbstractDungeon.ascensionLevel >= 2) {
            this.buffStr = A_2_BUFF_STR;
            this.buffBlock = BUFF_BLOCK;
            this.attack1Dmg = A_2_ATTACK1_DMG;
            this.attackBlockDmg = ATTACKBLOCK_DMG;
            this.attackBlockBlock = ATTACKBLOCK_BLOCK;
        } else {
            this.buffStr = BUFF_STR;
            this.buffBlock = BUFF_BLOCK;
            this.attack1Dmg = ATTACK1_DMG;
            this.attackBlockDmg = ATTACKBLOCK_DMG;
            this.attackBlockBlock = ATTACKBLOCK_BLOCK;
        }

        this.damage.add(new DamageInfo(this, this.attack1Dmg));
        this.damage.add(new DamageInfo(this, this.attackBlockDmg));
        this.loadAnimation(makeMonsterPath("SimpleMon/skeleton.atlas"), makeMonsterPath("SimpleMon/skeleton.json"), 1.0F);
        TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random()); // Randomizes the animation start point, so multiple identical enemies aren't in sync.
    }

    public void usePreBattleAction() {
        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, this.buffStr), this.buffStr));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.buffBlock));
     }

    public void takeTurn() {
        if (this.firstTurn) {
            //AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F));
            this.setMove((byte)3, Intent.ATTACK_DEFEND, ((DamageInfo)this.damage.get(1)).base);
            this.firstTurn = false;
        }

        switch(this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new SetAnimationAction(this, "chomp"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.3F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.NONE));
                break;
            case 2:
                this.state.setAnimation(0, "tailslam", false);
                this.state.addAnimation(0, "idle", true, 0.0F);
                AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_JAW_WORM_BELLOW"));
                AbstractDungeon.actionManager.addToBottom(new ShakeScreenAction(0.2F, ShakeDur.SHORT, ShakeIntensity.MED));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, this.buffStr), this.buffStr));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.buffBlock));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.attackBlockBlock));
                break;
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if (num < 25) {
            if (this.lastMove((byte)1)) {
                if (AbstractDungeon.aiRng.randomBoolean(0.5625F)) {
                    this.setMove(MOVES[0], (byte)2, Intent.DEFEND_BUFF);
                } else {
                    this.setMove((byte)3, Intent.ATTACK_DEFEND, ((DamageInfo)this.damage.get(1)).base);
                }
            } else {
                this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
            }
        } else if (num < 55) {
            if (this.lastTwoMoves((byte)3)) {
                if (AbstractDungeon.aiRng.randomBoolean(0.357F)) {
                    this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
                } else {
                    this.setMove(MOVES[0], (byte)2, Intent.DEFEND_BUFF);
                }
            } else {
                this.setMove((byte)3, Intent.ATTACK_DEFEND, ((DamageInfo)this.damage.get(1)).base);
            }
        } else if (this.lastMove((byte)2)) {
            if (AbstractDungeon.aiRng.randomBoolean(0.416F)) {
                this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
            } else {
                this.setMove((byte)3, Intent.ATTACK_DEFEND, ((DamageInfo)this.damage.get(1)).base);
            }
        } else {
            this.setMove(MOVES[0], (byte)2, Intent.DEFEND_BUFF);
        }

    }

    public void die() {
        super.die();
        CardCrawlGame.sound.play("JAW_WORM_DEATH");
    }

}
