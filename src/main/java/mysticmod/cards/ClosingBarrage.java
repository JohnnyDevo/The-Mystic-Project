package mysticmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import mysticmod.actions.ClosingBarrageAction;
import mysticmod.patches.AbstractCardEnum;
import mysticmod.powers.ArtesPlayed;
import mysticmod.powers.SpellsPlayed;

public class ClosingBarrage extends AbstractMysticCard {
    public static final String ID = "mysticmod:ClosingBarrage";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
    public static final String IMG_PATH = "mysticmod/images/cards/closingbarrage.png";
    private static final int COST = -1;
    private static final int MULTIPLIER = 3;

    public ClosingBarrage() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK, AbstractCardEnum.MYSTIC_PURPLE,
                AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
            magicNumber = baseMagicNumber = MULTIPLIER;
            damage = baseDamage = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (energyOnUse < EnergyPanel.totalCount) {
            energyOnUse = EnergyPanel.totalCount;
        }
        AbstractDungeon.actionManager.addToBottom(new ClosingBarrageAction(p, m, damage, damageTypeForTurn, freeToPlayOnce, energyOnUse, upgraded));
        rawDescription = DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void applyPowers() {
        int baseDamagePlaceholder = baseDamage;
        int damageX = 0;
        int damageY = 0;
        if (AbstractDungeon.player.hasPower(ArtesPlayed.POWER_ID)) {
            damageX = AbstractDungeon.player.getPower(ArtesPlayed.POWER_ID).amount;
        }
        if (AbstractDungeon.player.hasPower(SpellsPlayed.POWER_ID)) {
            damageY = AbstractDungeon.player.getPower(SpellsPlayed.POWER_ID).amount;
        }
        baseDamage = magicNumber * (damageX + damageY);
        super.applyPowers();
        baseDamage = baseDamagePlaceholder;
        if (damage != baseDamage) {
            isDamageModified = true;
        }
        rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION;
        initializeDescription();
    }

    public void calculateCardDamage(final AbstractMonster mo) {
        int baseDamagePlaceholder = baseDamage;
        int damageX = 0;
        int damageY = 0;
        if (AbstractDungeon.player.hasPower(ArtesPlayed.POWER_ID)) {
            damageX = AbstractDungeon.player.getPower(ArtesPlayed.POWER_ID).amount;
        }
        if (AbstractDungeon.player.hasPower(SpellsPlayed.POWER_ID)) {
            damageY = AbstractDungeon.player.getPower(SpellsPlayed.POWER_ID).amount;
        }
        baseDamage = magicNumber * (damageX + damageY);
        super.calculateCardDamage(mo);
        baseDamage = baseDamagePlaceholder;
        isDamageModified = damage != baseDamage;
        rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        rawDescription = DESCRIPTION;
        initializeDescription();
    }


    @Override
    public AbstractCard makeCopy() {
        return new ClosingBarrage();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
