package theFirst.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;

public class AbstractCustomRelic extends CustomRelic {

    public float floatCounter;

    public AbstractCustomRelic(String id, Texture texture, RelicTier tier, LandingSound sfx) {
        super(id, texture, tier, sfx);
    }

    public AbstractCustomRelic(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx) {
        super(id, texture, outline, tier, sfx);
    }

    public AbstractCustomRelic(String id, String imgName, RelicTier tier, LandingSound sfx) {
        super(id, imgName, tier, sfx);
    }

    public void onTrigger(int value){

    }

    public int getState(){
        return 0;
    }

    public void floatCount()
    {

    }

    public void onTriggerFloat(int amt)
    {

    }

    public int intTrigger(int val)
    {
        return val;
    }

    public void updateDescription(){

    }

}
