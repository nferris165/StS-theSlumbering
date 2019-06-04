package theFirst.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static theFirst.FirstMod.makeID;

public class EnergyVariable extends DynamicVariable
{
    @Override
    public String key()
    {
        return makeID("ED");
    }

    @Override
    public boolean isModified(AbstractCard card)
    {
        return card.isDamageModified;
    }

    @Override
    public int value(AbstractCard card)
    {
        return card.damage * EnergyPanel.getCurrentEnergy();
    }
    
    @Override
    public int baseValue(AbstractCard card)
    {   
        return card.baseDamage * EnergyPanel.getCurrentEnergy();
    }

    @Override
    public boolean upgraded(AbstractCard card)
    {               
       return card.upgradedDamage;
    }
}