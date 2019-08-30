package theSlumbering.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;

public abstract class AbstractCustomPlayer extends CustomPlayer {

    public int defaultSecondMagicNumber;
    public int defaultBaseSecondMagicNumber;

    //constructors
    public AbstractCustomPlayer(String name, PlayerClass playerClass, EnergyOrbInterface energyOrbInterface, String model, String animation) {
        super(name, playerClass, energyOrbInterface, model, animation);
    }
    public AbstractCustomPlayer(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath, float[] layerSpeeds, String model, String animation) {
        super(name, playerClass, orbTextures, orbVfxPath, layerSpeeds, model, animation);
    }
    public AbstractCustomPlayer(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath, float[] layerSpeeds, AbstractAnimation animation) {
        super(name, playerClass, orbTextures, orbVfxPath, layerSpeeds, animation);
    }
    public AbstractCustomPlayer(String name, PlayerClass playerClass, EnergyOrbInterface energyOrbInterface, AbstractAnimation animation) {
        super(name, playerClass, energyOrbInterface, animation);
    }
    public AbstractCustomPlayer(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath, AbstractAnimation animation) {
        super(name, playerClass, orbTextures, orbVfxPath, animation);
    }
    public AbstractCustomPlayer(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath, String model, String animation) {
        super(name, playerClass, orbTextures, orbVfxPath, model, animation);
    }//*/

    public void imcrementDefaultSecondMagicNumber() {
        defaultBaseSecondMagicNumber++;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber;
    }
    public void imcrementDefaultSecondMagicNumber(int amt) {
        defaultBaseSecondMagicNumber += amt;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber;
    }

}
