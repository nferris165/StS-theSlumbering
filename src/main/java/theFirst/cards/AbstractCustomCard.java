package theFirst.cards;

import basemod.abstracts.CustomCard;
import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;


public abstract class AbstractCustomCard extends CustomCard {

    public int SecondMagicNumber;
    public int baseSecondMagicNumber;
    public boolean upgradedSecondMagicNumber;
    public boolean isSecondMagicNumberModified;

    public AbstractCustomCard(final String id,
                              final String img,
                              final int cost,
                              final CardType type,
                              final CardColor color,
                              final CardRarity rarity,
                              final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);
    }

    @Override
    public void displayUpgrades() {

        super.displayUpgrades();

        if (upgradedSecondMagicNumber) {
            isSecondMagicNumberModified = true;
            SecondMagicNumber = baseSecondMagicNumber;
        }
    }

    public void upgradeSecondMagicNumber(int upgradeAmount) {
        upgradedSecondMagicNumber = true;
        baseSecondMagicNumber += upgradeAmount;
        SecondMagicNumber = baseSecondMagicNumber;
    }
}