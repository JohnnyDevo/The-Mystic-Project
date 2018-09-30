package mysticmod.cards;

import basemod.helpers.CardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mysticmod.mystictags.MysticTags;
import mysticmod.patches.AbstractCardEnum;
import mysticmod.relics.CrystalBall;

public class ComponentsPouch
        extends AbstractMysticCard {
    public static final String ID = "mysticmod:ComponentsPouch";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "mysticmod/images/cards/componentspouch.png";
    private static final int COST = 1;
    public static final int ATTACK_DMG = 7;
    private static final int UPGRADE_PLUS_DMG = 2;

    public ComponentsPouch() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK, AbstractCardEnum.MYSTIC_PURPLE,
                AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY);
        this.damage=this.baseDamage = ATTACK_DMG;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int spellsCount = 0;
        for (final AbstractCard card : p.hand.group) {
            if (card instanceof AbstractMysticCard && ((AbstractMysticCard)card).isSpell() || CardTags.hasTag(card, MysticTags.IS_SPELL)
                    || (AbstractDungeon.player.hasRelic(CrystalBall.ID) && card.type == AbstractCard.CardType.SKILL
                    && !(card instanceof AbstractMysticCard && ((AbstractMysticCard)card).isArte() || CardTags.hasTag(card, MysticTags.IS_ARTE)))) {
                spellsCount++;
            }
        }
        if (spellsCount > 0) {
            for (int i = 0; i < spellsCount; i++){
                AbstractDungeon.actionManager.addToBottom(
                        new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
            }
        }
        this.rawDescription = DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void applyPowers() {
        int spellsCount = 0;
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            if (card instanceof AbstractMysticCard && ((AbstractMysticCard)card).isSpell() || CardTags.hasTag(card, MysticTags.IS_SPELL)
                    || (AbstractDungeon.player.hasRelic(CrystalBall.ID) && card.type == AbstractCard.CardType.SKILL
                    && !(card instanceof AbstractMysticCard && ((AbstractMysticCard)card).isArte() || CardTags.hasTag(card, MysticTags.IS_ARTE)))) {
                spellsCount++;
            }
        }
        this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0] + spellsCount;
        if (spellsCount == 1) {
            this.rawDescription += EXTENDED_DESCRIPTION[1];
        } else {
            this.rawDescription += EXTENDED_DESCRIPTION[2];
        }
        this.initializeDescription();
        super.applyPowers();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new ComponentsPouch();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}