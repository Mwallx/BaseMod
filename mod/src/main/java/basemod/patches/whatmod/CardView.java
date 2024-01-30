package basemod.patches.whatmod;

import basemod.ReflectionHacks;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import java.util.ArrayList;
import java.util.List;

@SpirePatch(
    clz=SingleCardViewPopup.class,
    method="renderTips"
)
public class CardView
{
    public static void Postfix(SingleCardViewPopup __instance, SpriteBatch sb)
    {
        if (WhatMod.enabled) {
			AbstractCard card = ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
            Hitbox nextHb = ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "nextHb");

			List<Class<?>> clses = new ArrayList<>();
			clses.add(card.getClass());
			CardModifierManager.modifiers(card).stream()
					.map(AbstractCardModifier::getClass)
					.forEach(clses::add);
			Class<?>[] arr = clses.toArray(new Class<?>[0]);
			WhatMod.renderModTooltipBottomLeft(sb, Settings.WIDTH / 2f + 340f * Settings.scale, nextHb.cY + 160f * Settings.scale, arr);
		}
    }
}
