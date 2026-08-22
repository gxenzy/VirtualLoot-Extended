package com.lunazstudios.virtualloot.client.cobblebase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import notlown.cobblebase.core.SkillEntry;
import notlown.cobblebase.core.SpeciesSkillRegistry;
import notlown.cobblebase.core.SpeciesSkills;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class AdminStatsOverlay {

    public static int starFilter = 0; // 0=All, 1..5 = stars
    public static int sourceFilter = 0; // 0=All, 1=Cobblemon Only, 2=Custom Only
    public static int statsScroll = 0;

    private static final Map<Integer, int[]> starHitboxes = new HashMap<>();
    private static int[] sourceHitbox = null;

    public static class SpeciesStatEntry {
        public final String species;
        public final int prof;
        public final boolean isOfficial;

        public SpeciesStatEntry(String species, int prof, boolean isOfficial) {
            this.species = species;
            this.prof = prof;
            this.isOfficial = isOfficial;
        }
    }

    public static boolean isOfficialCobblemon(String species) {
        try {
            Class<?> psClass = Class.forName("com.cobblemon.mod.common.api.pokemon.PokemonSpecies", false, Thread.currentThread().getContextClassLoader());
            Object inst = psClass.getField("INSTANCE").get(null);
            Method m = psClass.getMethod("getByName", String.class);
            return m.invoke(inst, species.toLowerCase()) != null;
        } catch (Throwable ignored) {
            return true;
        }
    }

    public static void renderPokemonSprite(GuiGraphics context, Font font, String species, int x, int y) {
        try {
            Class<?> helperClass = Class.forName("notlown.cobblebase.fabric.client.gui.PokemonSpriteHelper", false, Thread.currentThread().getContextClassLoader());
            Object instance = helperClass.getField("INSTANCE").get(null);
            for (Method m : helperClass.getMethods()) {
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
            for (Method m : helperClass.getMethods()) {
                if (m.getName().equals("renderSmallIconByName") && m.getParameterCount() == 6) {
                    m.invoke(instance, context, font, species, x, y, 0f);
                    return;
                }
            }
        } catch (Throwable ignored) {
        }
    }

    public static boolean isStatsTabActive(Object adminScreen) {
        try {
            Field atField = adminScreen.getClass().getDeclaredField("activeTab");
            atField.setAccessible(true);
            String tab = (String) atField.get(adminScreen);
            if (!"jobs".equals(tab)) return false;

            Field jpField = adminScreen.getClass().getDeclaredField("jobsPanel");
            jpField.setAccessible(true);
            Object jobsPanel = jpField.get(adminScreen);
            if (jobsPanel == null) return false;

            Field vmField = jobsPanel.getClass().getDeclaredField("viewMode");
            vmField.setAccessible(true);
            Object vm = vmField.get(jobsPanel);
            if (vm == null || !vm.toString().equals("DETAIL")) return false;

            Field dtField = jobsPanel.getClass().getDeclaredField("detailTab");
            dtField.setAccessible(true);
            Object dt = dtField.get(jobsPanel);
            if (dt == null || !dt.toString().equals("STATS")) return false;

            Field djiField = jobsPanel.getClass().getDeclaredField("detailJobIdx");
            djiField.setAccessible(true);
            int idx = djiField.getInt(jobsPanel);
            return idx >= 0;
        } catch (Throwable ignored) {
            return false;
        }
    }

    public static String getActiveSkillId(Object adminScreen) {
        try {
            Field jpField = adminScreen.getClass().getDeclaredField("jobsPanel");
            jpField.setAccessible(true);
            Object jobsPanel = jpField.get(adminScreen);
            if (jobsPanel == null) return "";

            Field djiField = jobsPanel.getClass().getDeclaredField("detailJobIdx");
            djiField.setAccessible(true);
            int idx = djiField.getInt(jobsPanel);

            Field jeField = jobsPanel.getClass().getDeclaredField("jobEdits");
            jeField.setAccessible(true);
            List<?> edits = (List<?>) jeField.get(jobsPanel);
            if (edits != null && idx >= 0 && idx < edits.size()) {
                Object job = edits.get(idx);
                Method m = job.getClass().getMethod("getSkillId");
                return (String) m.invoke(job);
            }
        } catch (Throwable ignored) {
        }
        return "";
    }

    public static void render(GuiGraphics context, Object adminScreen, int mouseX, int mouseY, float delta) {
        if (!isStatsTabActive(adminScreen)) {
            return;
        }

        try {
            Field pxField = adminScreen.getClass().getDeclaredField("panelX");
            pxField.setAccessible(true);
            int panelX = pxField.getInt(adminScreen);

            Field pyField = adminScreen.getClass().getDeclaredField("panelY");
            pyField.setAccessible(true);
            int panelY = pyField.getInt(adminScreen);

            Field pwField = adminScreen.getClass().getDeclaredField("panelW");
            pwField.setAccessible(true);
            int panelW = pwField.getInt(adminScreen);

            Field phField = adminScreen.getClass().getDeclaredField("panelH");
            phField.setAccessible(true);
            int panelH = phField.getInt(adminScreen);

            int sidebarW = 110;
            int padding = 4;
            int rightX = panelX + sidebarW;
            int rightW = panelW - sidebarW;
            int contentTop = panelY + 22 + 16 + padding + 14 + 2 + 12 + 4;
            int contentBottom = panelY + panelH - 18 - 2;

            // Clear right pane content area with sleek dark background
            context.fill(rightX + padding, contentTop, rightX + rightW - padding, contentBottom, 0xFF141422);

            String skillId = getActiveSkillId(adminScreen);
            Font font = Minecraft.getInstance().font;

            Map<String, SpeciesSkills> allSpecies = SpeciesSkillRegistry.INSTANCE.getAllAssigned();
            List<SpeciesStatEntry> matching = new ArrayList<>();

            for (Map.Entry<String, SpeciesSkills> entry : allSpecies.entrySet()) {
                String sp = entry.getKey();
                SpeciesSkills data = entry.getValue();
                for (SkillEntry se : data.getSkills()) {
                    if (se.getSkillId().equals(skillId)) {
                        matching.add(new SpeciesStatEntry(sp, se.getProficiency(), isOfficialCobblemon(sp)));
                        break;
                    }
                }
            }

            if (matching.isEmpty()) {
                context.drawString(font, "§8No species can use this skill yet.", rightX + padding + 4, contentTop + 4, 0x888888, false);
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
            context.drawString(font, "§e§lOverview", rightX + padding + 4, contentTop + 2, 0xFFD700, false);
            String summary = String.format("§7Total species: §f%d   §7Avg prof: §f%.1f   §7Max prof: §f%d", total, avgProf, maxProf);
            context.drawString(font, summary, rightX + padding + 4, contentTop + 14, 0xCCCCCC, false);

            // 2. Interactive Star Filters Bar
            int distY = contentTop + 26;
            context.drawString(font, "§7Filter:", rightX + padding + 4, distY + 2, 0xAAAAAA, false);
            starHitboxes.clear();

            int bx = rightX + padding + 44;
            // [All] filter button
            int allW = 38;
            int btnH = 12;
            int allBg = (starFilter == 0) ? 0xFF2A3A4E : 0xFF181C24;
            int allBorder = (starFilter == 0) ? 0xFF58A6FF : 0xFF333344;
            context.fill(bx - 1, distY - 1, bx + allW + 1, distY + btnH + 1, allBorder);
            context.fill(bx, distY, bx + allW, distY + btnH, allBg);
            String allLabel = (starFilter == 0) ? "§b§lAll (" + total + ")" : "§7All (" + total + ")";
            context.drawString(font, allLabel, bx + 3, distY + 2, 0xFFFFFF, false);
            starHitboxes.put(0, new int[]{bx, distY, allW, btnH});
            bx += allW + 4;

            // [5★] down to [1★] buttons
            for (int prof = 5; prof >= 1; prof--) {
                int count = byProf.getOrDefault(prof, 0);
                int starW = 46;
                int starBg = (starFilter == prof) ? 0xFF3E3A20 : 0xFF181C24;
                int starBorder = (starFilter == prof) ? 0xFFFFD700 : 0xFF333344;
                context.fill(bx - 1, distY - 1, bx + starW + 1, distY + btnH + 1, starBorder);
                context.fill(bx, distY, bx + starW, distY + btnH, starBg);
                String starLabel = (starFilter == prof) ? "§6§l" + prof + "★ §e(" + count + ")" : "§e" + prof + "★ §8(" + count + ")";
                context.drawString(font, starLabel, bx + 3, distY + 2, 0xFFFFFF, false);
                starHitboxes.put(prof, new int[]{bx, distY, starW, btnH});
                bx += starW + 4;
            }

            // 3. Source Filter Toggle Button
            int srcW = 86;
            int srcX = rightX + rightW - padding - srcW;
            int srcBg = 0xFF222233;
            int srcBorder = 0xFF445566;
            context.fill(srcX - 1, distY - 1, srcX + srcW + 1, distY + btnH + 1, srcBorder);
            context.fill(srcX, distY, srcX + srcW, distY + btnH, srcBg);
            String srcText = (sourceFilter == 1) ? "§aCobblemon Only" : (sourceFilter == 2) ? "§dCustom Only" : "§fAll Species";
            context.drawString(font, srcText, srcX + 4, distY + 2, 0xFFFFFF, false);
            sourceHitbox = new int[]{srcX, distY, srcW, btnH};

            // 4. Filter and Sort Species List
            List<SpeciesStatEntry> filtered = new ArrayList<>();
            for (SpeciesStatEntry e : matching) {
                if (starFilter > 0 && e.prof != starFilter) continue;
                if (sourceFilter == 1 && !e.isOfficial) continue;
                if (sourceFilter == 2 && e.isOfficial) continue;
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
            statsScroll = Math.max(0, Math.min(statsScroll, maxScroll));

            context.enableScissor(rightX + padding, gridTop, rightX + rightW - padding, contentBottom);

            for (int i = 0; i < filtered.size(); i++) {
                SpeciesStatEntry entry = filtered.get(i);
                int col = i % cols;
                int rowI = i / cols;
                int cx = rightX + padding + col * (colW + colGap);
                int ry = gridTop + rowI * rowHeight - statsScroll;

                if (ry + rowHeight < gridTop || ry > contentBottom) continue;

                int rowBg = (rowI % 2 == 0) ? 0xFF1E1E2C : 0xFF171724;
                context.fill(cx, ry, cx + colW, ry + rowHeight - 2, rowBg);

                // Left Sprite
                renderPokemonSprite(context, font, entry.species, cx + 2, ry + 1);

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
                int thumbY = gridTop + (int) ((float) statsScroll / maxScroll * (listH - thumbH));
                context.fill(trackX, thumbY, trackX + 4, thumbY + thumbH, 0xFF58A6FF);
            }
        } catch (Throwable ignored) {
        }
    }

    public static boolean mouseClicked(Object adminScreen, double mouseX, double mouseY, int button) {
        if (button != 0 || !isStatsTabActive(adminScreen)) return false;

        // Check star filter button hits
        for (Map.Entry<Integer, int[]> entry : starHitboxes.entrySet()) {
            int[] b = entry.getValue();
            if (mouseX >= b[0] && mouseX <= b[0] + b[2] && mouseY >= b[1] && mouseY <= b[1] + b[3]) {
                starFilter = entry.getKey();
                statsScroll = 0;
                return true;
            }
        }

        // Check source toggle button hit
        if (sourceHitbox != null) {
            int[] b = sourceHitbox;
            if (mouseX >= b[0] && mouseX <= b[0] + b[2] && mouseY >= b[1] && mouseY <= b[1] + b[3]) {
                sourceFilter = (sourceFilter + 1) % 3;
                statsScroll = 0;
                return true;
            }
        }

        return false;
    }

    public static boolean mouseScrolled(Object adminScreen, double mouseX, double mouseY, double verticalAmount) {
        if (!isStatsTabActive(adminScreen)) return false;
        statsScroll = (int) Math.max(0, statsScroll - (verticalAmount * 18));
        return true;
    }
}
