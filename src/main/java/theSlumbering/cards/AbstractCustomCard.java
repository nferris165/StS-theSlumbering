package theSlumbering.cards;

import basemod.abstracts.CustomCard;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;


public abstract class AbstractCustomCard extends CustomCard {

    public int secondMagicNumber;
    public int baseSecondMagicNumber;
    public boolean upgradedSecondMagicNumber;
    public boolean isSecondMagicNumberModified;
    public String updated_desc;
    public String[] ext_desc;
    public static String BG_SMALL_PASSIVE_FIELD = "theSlumberingResources/images/512/bg_passive_field.png";
    public static String BG_LARGE_PASSIVE_FIELD = "theSlumberingResources/images/1024/bg_passive_field.png";

    public AbstractCustomCard(final String id,
                              final String img,
                              final int cost,
                              final CardType type,
                              final CardColor color,
                              final CardRarity rarity,
                              final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);
        this.updated_desc = languagePack.getCardStrings(id).UPGRADE_DESCRIPTION;
        this.ext_desc = languagePack.getCardStrings(id).EXTENDED_DESCRIPTION;
    }

    @Override
    public void displayUpgrades() {

        super.displayUpgrades();

        if (upgradedSecondMagicNumber) {
            isSecondMagicNumberModified = true;
            secondMagicNumber = baseSecondMagicNumber;
        }
    }

    public void passiveEffect()
    {
        //AbstractDungeon.actionManager.addToBottom(new ShowCardAction(this));
    }

    public void upgradeSecondMagicNumber(int upgradeAmount) {
        upgradedSecondMagicNumber = true;
        baseSecondMagicNumber += upgradeAmount;
        secondMagicNumber = baseSecondMagicNumber;
    }
}