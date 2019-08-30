package theSlumbering.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theSlumbering.cards.AbstractCustomCard;

import static theSlumbering.SlumberingMod.makeID;

public class DynamicMagicVariable extends DynamicVariable {

    @Override
    public String key() {
        return makeID("Magic");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractCustomCard) card).isSecondMagicNumberModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractCustomCard) card).secondMagicNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractCustomCard) card).baseSecondMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractCustomCard) card).upgradedSecondMagicNumber;
    }
}