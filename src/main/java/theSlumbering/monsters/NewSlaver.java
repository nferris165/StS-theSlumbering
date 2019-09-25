package theSlumbering.monsters;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.FrailPower;
import theSlumbering.SlumberingMod;
import theSlumbering.powers.DrowsyPower;

import static theSlumbering.SlumberingMod.makeMonsterPath;


public class NewSlaver extends CustomMonster {
    public static final String ID = SlumberingMod.makeID("NewSlaver");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] MOVES = monsterstrings.MOVES;
    private static final int HP_MIN = 45;
    private static final int HP_MAX = 49;
    private static final int A_7_HP_MIN = 48;
    private static final int A_7_HP_MAX = 52;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 170.0F;
    private static final float HB_H = 230.0F;
    private static final int STAB_DMG = 10;
    private static final int A_2_STAB_DMG = 12;
    private static final int SHRED_DMG = 6;
    private static final int A_2_SHRED_DMG = 7;
    private boolean elder;
    private static final float eMod = 1.15F;
    private int stabDmg;
    private int shredDmg;
    private int FRAIL_AMT = 1;
    private int delay = 3;
    private static final byte STAB = 1;
    private static final byte DROWSE = 2;
    private static final byte SHRED = 3;
    private boolean usedDrowse = false;
    private boolean firstTurn = true;

    public NewSlaver(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, (String)null, x, y);

        this.elder = AbstractDungeon.aiRng.random(99) >= 95;

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
            this.FRAIL_AMT++;
            this.delay--;
        } else if (AbstractDungeon.ascensionLevel >= 2) {
            this.stabDmg = A_2_STAB_DMG;
            this.shredDmg = A_2_SHRED_DMG;
        } else {
            this.stabDmg = STAB_DMG;
            this.shredDmg = SHRED_DMG;
        }

        this.damage.add(new DamageInfo(this, this.stabDmg));
        this.damage.add(new DamageInfo(this, this.shredDmg));
        this.loadAnimation(makeMonsterPath("NewSlaver/skeleton.atlas"), makeMonsterPath("NewSlaver/skeleton.json"), 1.0F);
        TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void usePreBattleAction() {

     }

    public void takeTurn() {
        switch(this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.SLASH_HORIZONTAL));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new DrowsyPower(AbstractDungeon.player, 1, true), 1));
                this.usedDrowse = true;
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AttackEffect.SLASH_DIAGONAL));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, this.FRAIL_AMT, true), this.FRAIL_AMT));
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        SlumberingMod.logger.info(num + "\n\n");
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove(MOVES[0], (byte)3, Intent.ATTACK_DEBUFF, this.shredDmg);
        } else if(!this.usedDrowse && GameActionManager.turn > this.delay && num < 25){
            this.setMove(MOVES[1], (byte)2, Intent.STRONG_DEBUFF);
        } else if(num < 60){
            this.setMove((byte)1, Intent.ATTACK, this.stabDmg);
        } else{
            if((AbstractDungeon.ascensionLevel >= 17 && this.lastMove((byte)3)) || this.lastTwoMoves((byte)3)){
                this.setMove((byte)1, Intent.ATTACK, this.stabDmg);
            }
            else{
                this.setMove(MOVES[0], (byte)3, Intent.ATTACK_DEBUFF, this.shredDmg);
            }
        }
    }

    private void playDeathSfx() {
        int roll = MathUtils.random(1);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_SLAVERRED_2A");
        } else {
            CardCrawlGame.sound.play("VO_SLAVERRED_2B");
        }

    }

    public void die() {
        super.die();
        playDeathSfx();
    }

}
