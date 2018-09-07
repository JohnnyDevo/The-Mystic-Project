package mysticmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mysticmod.actions.GeminiFormAction;
import mysticmod.cards.AbstractMysticCard;
import mysticmod.relics.CrystalBall;


public class GeminiFormPower extends AbstractPower {
    public static final String POWER_ID = "mysticmod:GeminiFormPower";
    public static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTIONS = cardStrings.DESCRIPTIONS;
    private int spellsPlayedThisTurn = 0;
    private int artesPlayedThisTurn = 0;
    public static boolean isActive = true;

    public GeminiFormPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = new Texture("mysticmod/images/powers/gemini form power.png");
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();

    }

    @Override
    public void updateDescription() {
        if (this.amount > 1) {
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + DESCRIPTIONS[3];
        } else {
            description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + DESCRIPTIONS[3];
        }
    }

    @Override
    public void onUseCard(AbstractCard c, UseCardAction action) {
        if (isActive) {
            if ((c instanceof AbstractMysticCard && ((AbstractMysticCard) c).isSpell()) || (AbstractDungeon.player.hasRelic(CrystalBall.ID) && c.type == AbstractCard.CardType.SKILL)) {
                if (spellsPlayedThisTurn < this.amount) {
                    AbstractDungeon.actionManager.addToBottom(new GeminiFormAction((AbstractMonster) action.target, true, c));
                }
                this.spellsPlayedThisTurn++;
            }
            if ((c instanceof AbstractMysticCard && ((AbstractMysticCard) c).isArte()) || (AbstractDungeon.player.hasRelic(CrystalBall.ID) && c.type == AbstractCard.CardType.ATTACK)) {
                if (artesPlayedThisTurn < this.amount) {
                    AbstractDungeon.actionManager.addToBottom(new GeminiFormAction((AbstractMonster) action.target, false, c));
                }
                this.artesPlayedThisTurn++;
            }
        } else {
            System.out.println("cardQueue.size() = " + AbstractDungeon.actionManager.cardQueue.size());
            if (AbstractDungeon.actionManager.cardQueue.size() == 1) {
                isActive = true;
            }
        }
        System.out.println("isActive = " + isActive);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.artesPlayedThisTurn = 0;
            this.spellsPlayedThisTurn = 0;
        }
    }

    @Override
    public void onInitialApplication() {
        for (AbstractCard card : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if ((card instanceof AbstractMysticCard && ((AbstractMysticCard)card).isSpell()) || (AbstractDungeon.player.hasRelic(CrystalBall.ID) && card.type == AbstractCard.CardType.SKILL)) {
                this.spellsPlayedThisTurn++;
            }
            if ((card instanceof AbstractMysticCard && ((AbstractMysticCard)card).isArte()) || (AbstractDungeon.player.hasRelic(CrystalBall.ID) && card.type == AbstractCard.CardType.ATTACK)) {
                this.artesPlayedThisTurn++;
            }
        }
    }
}
