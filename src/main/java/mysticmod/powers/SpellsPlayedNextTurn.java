package mysticmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SpellsPlayedNextTurn extends AbstractPower {
    public static final String POWER_ID = "mysticmod:SpellsPlayedNextTurnPower";
    public static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTIONS = cardStrings.DESCRIPTIONS;

    public SpellsPlayedNextTurn(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = new Texture("mysticmod/images/powers/spells played next turn.png");
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();

    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount;
        if (amount == 1) {
            description += DESCRIPTIONS[1];
        } else {
            description += DESCRIPTIONS[2];
        }
    }

    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SpellsPlayed(AbstractDungeon.player, this.amount), this.amount));
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, SpellsPlayedNextTurn.POWER_ID, AbstractDungeon.player.getPower(SpellsPlayedNextTurn.POWER_ID).amount));
    }
}