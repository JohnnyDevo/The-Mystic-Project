package mysticmod.modifiers;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.daily.mods.AbstractDailyMod;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.RunModStrings;

public class CrystalClear extends AbstractDailyMod {
    public static final String ID = "mysticmod:CrystalClear";
    private static final RunModStrings modStrings = CardCrawlGame.languagePack.getRunModString(ID);
    public static final String NAME = modStrings.NAME;
    public static final String DESC = modStrings.DESCRIPTION;

    public CrystalClear() {
        super(ID, NAME, DESC, null, true);
        img = ImageMaster.loadImage("mysticmod/images/modifiers/crystalclear.png");
    }
}
