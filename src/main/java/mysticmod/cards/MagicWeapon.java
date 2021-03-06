package mysticmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import mysticmod.actions.ReplaceCardAction;
import mysticmod.patches.AbstractCardEnum;
import mysticmod.patches.MysticTags;

public class MagicWeapon extends AbstractMysticCard {
    public static final String ID = "mysticmod:MagicWeapon";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "mysticmod/images/cards/magicweapon.png";
    private static final int COST = 1;
    private static final int UPGRADED_STR_GAIN = 1;
    private static final int STRENGTH_GAIN = 2;

    public MagicWeapon() {
        this(true);
    }

    public MagicWeapon(boolean makePreview) {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.MYSTIC_PURPLE,
                AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        magicNumber = baseMagicNumber = STRENGTH_GAIN;
        exhaust = true;
        tags.add(MysticTags.IS_SPELL);
        if (makePreview) {
            cardsToPreview = new BladeBurst(false);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
        AbstractCard newBladeBurst = new BladeBurst();
        if (upgraded) {
            newBladeBurst.upgrade();
        }
        UnlockTracker.markCardAsSeen(newBladeBurst.cardID);
        AbstractDungeon.actionManager.addToBottom(new ReplaceCardAction(this, newBladeBurst));
    }

    @Override
    public AbstractCard makeCopy() {
        return new MagicWeapon();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_STR_GAIN);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
