package com.lunazstudios.virtualloot.mixin.cobblebase;

import com.cobblemon.mod.common.Cobblemon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import notlown.cobblebase.core.SpeciesSkillRegistry;
import notlown.cobblebase.core.SpeciesSkills;
import notlown.cobblebase.core.SkillEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Pseudo
@Mixin(targets = {
    "notlown.cobblebase.fabric.client.gui.AdminJobsPanel",
    "notlown.cobblebase.neoforge.client.gui.AdminJobsPanel"
}, priority = 2000, remap = false)
public abstract class CobblebaseAdminStatsMixin {

    @Unique private static int virtualloot$starFilter = 0; // 0=All, 1..5 = stars
    @Unique private static int virtualloot$sourceFilter = 0; // 0=All, 1=Cobblemon, 2=Custom
    @Unique private static int virtualloot$statsScroll = 0;
    @Unique private static final Map<Integer, int[]> virtualloot$starHitboxes = new HashMap<>();
    @Unique private static int[] virtualloot$sourceHitbox = null;

    @Unique
    private static class SpeciesStatEntry {
        final String species;
        final int prof;
        final boolean isOfficial;

        SpeciesStatEntry(String species, int prof, boolean isOfficial) {
            this.species = species;
            this.prof = prof;
            this.isOfficial = isOfficial;
        }
    }

    @Unique
    private static boolean virtualloot$isOfficialCobblemon(String species) {
        try {
            return Cobblemon.INSTANCE.getSpeciesRegistry().get(species) != null;
        } catch (Throwable ignored) {
            return true;
        }
    }

    @Unique
    private static void virtualloot$renderPokemonSprite(GuiGraphics context, Font font, String species, int x, int y) {
        try {
            Class<?> helperClass = Class.forName("notlown.cobblebase.fabric.client.gui.PokemonSpriteHelper", false, Thread.currentThread().getContextClassLoader());
            Object instance = helperClass.getField("INSTANCE").get(null);
            for (java.lang.reflect.Method m : helperClass.getMethods()) {
                if (m.getName().equals("renderSmallIconByName") && m.getParameterCount() == 6) {
                    m.invoke(instance, context, font, species, x, y, 0f);
                    return;
                }
            }
        } catch (Throwable ignored) {
        }
        try {
            Class<?> helperClass = Class.forName("notlown.cobblebase.neoforge.client.gui.PokemonSpriteHelper", false, Thread.currentThread().getContextClassLoader());
            Object instance = helperClass.getField("INSTANCE").get(null);
            for (java.lang.reflect.Method m : helperClass.getMethods()) {
                if (m.getName().equals("renderSmallIconByName") && m.getParameterCount() == 6) {
                    m.invoke(instance, context, font, species, x, y, 0f);
                    return;
                }
            }
        } catch (Throwable ignored) {
        }
    }

    @Inject(method = "renderStats", at = @At("HEAD"), cancellable = true, remap = false)
    private void virtualloot$customRenderStats(
        Object contextObj, Object jobObj,
        int rightX, int rightW, int contentTop, int contentBottom,
        CallbackInfo ci
    ) {
        if (!(contextObj instanceof GuiGraphics context)) {
            return;
        }

        Font font = Minecraft.getInstance().font;
        String skillId = "";
        try {
            java.lang.reflect.Method getSkillId = jobObj.getClass().getMethod("getSkillId");
            skillId = (String) getSkillId.invoke(jobObj);
        } catch (Throwable ignored) {
        }

        Map<String, SpeciesSkills> allSpecies = SpeciesSkillRegistry.INSTANCE.getAllAssigned();
        List<SpeciesStatEntry> matching = new ArrayList<>();

        for (Map.Entry<String, SpeciesSkills> entry : allSpecies.entrySet()) {
            String sp = entry.getKey();
            SpeciesSkills data = entry.getValue();
            for (SkillEntry se : data.getSkills()) {
                if (se.getSkillId().equals(skillId)) {
                    matching.add(new SpeciesStatEntry(sp, se.getProficiency(), virtualloot$isOfficialCobblemon(sp)));
                    break;
                }
            }
        }

        int padding = 8;
        if (matching.isEmpty()) {
            context.drawString(font, "§8No species can use this skill yet.", rightX + padding + 4, contentTop + 4, 0x888888, false);
            ci.cancel();
            return;
        }

        int total = matching.size();
        double avgProf = matching.stream().mapToInt(e -> e.prof).average().orElse(0.0);
        int maxProf = matching.stream().mapToInt(e -> e.prof).max().orElse(0);

        Map<Integer, Integer> byProf = new HashMap<>();
        for (SpeciesStatEntry e : matching) {
            byProf.put(e.prof, byProf.getOrDefault(e.prof, 0) + 1);
        }

        // 1. Overview Header
        context.drawString(font, "§e§lOverview", rightX + padding + 4, contentTop + 4, 0xFFD700, false);
        String summary = String.format("§7Total species: §f%d   §7Avg prof: §f%.1f   §7Max prof: §f%d", total, avgProf, maxProf);
        context.drawString(font, summary, rightX + padding + 4, contentTop + 16, 0xCCCCCC, false);

        // 2. Interactive Star Filters Bar
        int distY = contentTop + 28;
        context.drawString(font, "§7Filter:", rightX + padding + 4, distY + 3, 0xAAAAAA, false);
        virtualloot$starHitboxes.clear();

        int bx = rightX + padding + 44;
        // [All] filter button
        int allW = 38;
        int btnH = 12;
        boolean allHov = false;
        int allBg = (virtualloot$starFilter == 0) ? 0xFF2A3A4E : 0xFF181C24;
        int allBorder = (virtualloot$starFilter == 0) ? 0xFF58A6FF : 0xFF333344;
        context.fill(bx - 1, distY - 1, bx + allW + 1, distY + btnH + 1, allBorder);
        context.fill(bx, distY, bx + allW, distY + btnH, allBg);
        String allLabel = (virtualloot$starFilter == 0) ? "§b§lAll (" + total + ")" : "§7All (" + total + ")";
        context.drawString(font, allLabel, bx + 3, distY + 2, 0xFFFFFF, false);
        virtualloot$starHitboxes.put(0, new int[]{bx, distY, allW, btnH});
        bx += allW + 4;

        // [5★] down to [1★] buttons
        for (int prof = 5; prof >= 1; prof--) {
            int count = byProf.getOrDefault(prof, 0);
            int starW = 46;
            int starBg = (virtualloot$starFilter == prof) ? 0xFF3E3A20 : 0xFF181C24;
            int starBorder = (virtualloot$starFilter == prof) ? 0xFFFFD700 : 0xFF333344;
            context.fill(bx - 1, distY - 1, bx + starW + 1, distY + btnH + 1, starBorder);
            context.fill(bx, distY, bx + starW, distY + btnH, starBg);
            String starLabel = (virtualloot$starFilter == prof) ? "§6§l" + prof + "★ §e(" + count + ")" : "§e" + prof + "★ §8(" + count + ")";
            context.drawString(font, starLabel, bx + 3, distY + 2, 0xFFFFFF, false);
            virtualloot$starHitboxes.put(prof, new int[]{bx, distY, starW, btnH});
            bx += starW + 4;
        }

        // 3. Source Filter Toggle Button (Right side)
        int srcW = 86;
        int srcX = rightX + rightW - padding - srcW;
        int srcBg = 0xFF222233;
        int srcBorder = 0xFF445566;
        context.fill(srcX - 1, distY - 1, srcX + srcW + 1, distY + btnH + 1, srcBorder);
        context.fill(srcX, distY, srcX + srcW, distY + btnH, srcBg);
        String srcText = switch (virtualloot$sourceFilter) {
            case 1 -> "§aCobblemon Only";
            case 2 -> "§dCustom Only";
            default -> "§fAll Species";
        };
        context.drawString(font, srcText, srcX + 4, distY + 2, 0xFFFFFF, false);
        virtualloot$sourceHitbox = new int[]{srcX, distY, srcW, btnH};

        // 4. Filter and Sort Species List
        List<SpeciesStatEntry> filtered = new ArrayList<>();
        for (SpeciesStatEntry e : matching) {
            if (virtualloot$starFilter > 0 && e.prof != virtualloot$starFilter) continue;
            if (virtualloot$sourceFilter == 1 && !e.isOfficial) continue;
            if (virtualloot$sourceFilter == 2 && e.isOfficial) continue;
            filtered.add(e);
        }

        filtered.sort((a, b) -> {
            if (b.prof != a.prof) return Integer.compare(b.prof, a.prof);
            return a.species.compareToIgnoreCase(b.species);
        });

        // 5. Scrollable 2-Column Species Grid
        int topY = distY + 16;
        context.fill(rightX + padding, topY, rightX + rightW - padding, topY + 1, 0xFF333344);

        String gridTitle = "§e§lSpecies Ranked by Proficiency §7(" + filtered.size() + " matches)";
        context.drawString(font, gridTitle, rightX + padding + 4, topY + 4, 0xFFD700, false);

        int gridTop = topY + 16;
        int listH = contentBottom - gridTop;
        int rowHeight = 22;
        int cols = 2;
        int colGap = 6;
        int innerW = rightW - padding * 2 - 8;
        int colW = (innerW - colGap * (cols - 1)) / cols;

        int totalRows = (filtered.size() + cols - 1) / cols;
        int contentHeight = totalRows * rowHeight;
        int maxScroll = Math.max(0, contentHeight - listH);
        virtualloot$statsScroll = Math.max(0, Math.min(virtualloot$statsScroll, maxScroll));

        context.enableScissor(rightX + padding, gridTop, rightX + rightW - padding, contentBottom);

        for (int i = 0; i < filtered.size(); i++) {
            SpeciesStatEntry entry = filtered.get(i);
            int col = i % cols;
            int rowI = i / cols;
            int cx = rightX + padding + col * (colW + colGap);
            int ry = gridTop + rowI * rowHeight - virtualloot$statsScroll;

            if (ry + rowHeight < gridTop || ry > contentBottom) continue;

            int rowBg = (rowI % 2 == 0) ? 0xFF1E1E2C : 0xFF171724;
            context.fill(cx, ry, cx + colW, ry + rowHeight - 2, rowBg);

            // Left badge / Sprite
            virtualloot$renderPokemonSprite(context, font, entry.species, cx + 2, ry + 1);

            // Name, Rank and Stars
            String rank = "§7#" + (i + 1);
            String rawName = entry.species.substring(0, 1).toUpperCase() + entry.species.substring(1);
            String nameTag = entry.isOfficial ? "§f§l" + rawName : "§d§l" + rawName + " §8(custom)";

            context.pose().pushPose();
            context.pose().translate(cx + 22, ry + 2, 0);
            context.pose().scale(0.85f, 0.85f, 1f);
            context.drawString(font, rank + " " + nameTag, 0, 0, 0xFFFFFF, false);
            context.pose().popPose();

            StringBuilder stars = new StringBuilder();
            for (int s = 1; s <= 5; s++) {
                stars.append(s <= entry.prof ? "★" : "☆");
            }

            context.pose().pushPose();
            context.pose().translate(cx + 22, ry + 11, 0);
            context.pose().scale(0.7f, 0.7f, 1f);
            context.drawString(font, "§6" + stars.toString() + " §7(Prof " + entry.prof + ")", 0, 0, 0xFFD700, false);
            context.pose().popPose();
        }

        context.disableScissor();

        // 6. Scrollbar track & thumb
        if (contentHeight > listH) {
            int trackX = rightX + rightW - padding - 4;
            context.fill(trackX, gridTop, trackX + 4, contentBottom, 0xFF14141E);
            int thumbH = Math.max(16, (int) ((float) listH / contentHeight * listH));
            int thumbY = gridTop + (int) ((float) virtualloot$statsScroll / maxScroll * (listH - thumbH));
            context.fill(trackX, thumbY, trackX + 4, thumbY + thumbH, 0xFF58A6FF);
        }

        ci.cancel();
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true, remap = false)
    private void virtualloot$onAdminStatsClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (button != 0) return;

        // Check star filter button hits
        for (Map.Entry<Integer, int[]> entry : virtualloot$starHitboxes.entrySet()) {
            int[] b = entry.getValue();
            if (mouseX >= b[0] && mouseX <= b[0] + b[2] && mouseY >= b[1] && mouseY <= b[1] + b[3]) {
                virtualloot$starFilter = entry.getKey();
                virtualloot$statsScroll = 0;
                cir.setReturnValue(true);
                return;
            }
        }

        // Check source toggle button hit
        if (virtualloot$sourceHitbox != null) {
            int[] b = virtualloot$sourceHitbox;
            if (mouseX >= b[0] && mouseX <= b[0] + b[2] && mouseY >= b[1] && mouseY <= b[1] + b[3]) {
                virtualloot$sourceFilter = (virtualloot$sourceFilter + 1) % 3;
                virtualloot$statsScroll = 0;
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "mouseScrolled", at = @At("HEAD"), cancellable = true, remap = false)
    private void virtualloot$onAdminStatsScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount, CallbackInfoReturnable<Boolean> cir) {
        virtualloot$statsScroll = (int) Math.max(0, virtualloot$statsScroll - (verticalAmount * 18));
        cir.setReturnValue(true);
    }
}
