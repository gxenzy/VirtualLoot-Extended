<div align="center">

# Virtual Loot: Extended
### The Complete Virtual Base Automation, 3D Projection & Multi-Mod Integration Engine for Cobblemon

<p align="center">
  <a href="https://minecraft.net"><img src="https://img.shields.io/badge/Minecraft-1.21.1-62B47A?style=for-the-badge&logo=minecraft&logoColor=white" alt="Minecraft 1.21.1"></a>
  <a href="https://cobblemon.com"><img src="https://img.shields.io/badge/Cobblemon-1.7.3+-E8532E?style=for-the-badge" alt="Cobblemon 1.7.3+"></a>
  <a href="https://fabricmc.net"><img src="https://img.shields.io/badge/Fabric-0.19.3+-3A6EA5?style=for-the-badge&logo=fabric&logoColor=white" alt="Fabric Loader"></a>
  <a href="https://neoforged.net"><img src="https://img.shields.io/badge/NeoForge-21.1.234+-E65C00?style=for-the-badge" alt="NeoForge"></a>
  <a href="https://adoptium.net"><img src="https://img.shields.io/badge/Java-21%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21"></a>
  <a href="LICENSE.txt"><img src="https://img.shields.io/badge/License-MIT%20%2F%20MPL--2.0-588157?style=for-the-badge" alt="License"></a>
</p>

<p align="center">
  <a href="#overview">Overview</a> •
  <a href="#key-features">Key Features</a> •
  <a href="#3d-visual-projection-engine">3D Projections</a> •
  <a href="#all-43-cobblebase-jobs-catalog">All 43 Base Jobs</a> •
  <a href="#species-database--job-catalog">1,000+ Species Catalog</a> •
  <a href="#proficiency-scaling-system">Proficiency Math</a> •
  <a href="#controls--automation-setup">Controls & Automation</a> •
  <a href="#configuration-reference">Configuration</a> •
  <a href="#compatibility-matrix">Compatibility</a> •
  <a href="#credits--attributions">Credits</a>
</p>

</div>

---

<a id="overview"></a>
## <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/town-map.png" width="24" height="24" align="absmiddle"> Overview

In standard Cobblemon pastures, placing multiple Pokémon in pasture blocks causes physical mob entities to wander the surrounding terrain. On multiplayer servers and expansive player bases, this causes continuous entity ticking, AI navigation loops, collision physics overhead, and pathfinding spikes that rapidly degrade server TPS and client framerates.

**Virtual Loot: Extended** overhauls the Pasture Block into an automated **Virtual Base Automation & 3D Projection System**:

* **Zero Server Entity Ticking**: Pokémon exist virtually inside the pasture block entity. They work, harvest crops, mine ores, forage for rare items, breed eggs, and provide party buffs in the background with **0 physical entities spawned** on the server.
* **Full Multi-Mod Ecosystem**: Native compatibility bridge for **Cobblebase 2.0+** (all 43 Palworld-style jobs across 1,367+ Pokémon species across Gens 1–9), **Cobbreeding** (virtual egg breeding & Mirror Herb move transfers), and **Cobbleworkers** (field worker tasks).
* **Storage & Automation**: Every generated item, mineral, crop, and egg is collected in a built-in **27-slot internal inventory** that can be extracted automatically using **hoppers underneath**.
* **Client-Side 3D Projections**: View tethered Pokémon in the world using client-rendered **Cyber Wireframe**, **Sci-Fi Hologram**, or **Ethereal Ghost** visual modes with **zero server performance cost**.

---

<a id="key-features"></a>
## <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="24" height="24" align="absmiddle"> Key Features

<table width="100%">
  <tr>
    <td width="50%" valign="top">
      <h4><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/thunder-stone.png" width="20" height="20" align="absmiddle"> Server Performance & Storage</h4>
      <ul>
        <li><b>0 Entity Server Overhead</b>: Supports 16+ Pokémon per block without server tick lag.</li>
        <li><b>27-Slot Storage</b>: Built-in container for all drops. Access via <b>Shift + Right Click</b>.</li>
        <li><b>Hopper Automation</b>: Place hoppers below the pasture for automatic item piping into chests.</li>
        <li><b>Activity Log Sync</b>: Completed jobs log directly to Cobblebase's in-game GUI.</li>
      </ul>
    </td>
    <td width="50%" valign="top">
      <h4><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/devon-scope.png" width="20" height="20" align="absmiddle"> 3D Visual Projections & Polish</h4>
      <ul>
        <li><b>3 Visual Modes</b>: Wireframe, Hologram, and Ghost projections toggleable in-game.</li>
        <li><b>Zero-Push Physics</b>: Players walk freely through visual models with no collision bumping.</li>
        <li><b>Terrain Snapping</b>: Aligns feet with native world heightmaps.</li>
        <li><b>Spawn Animation</b>: Particle beam pillar, sound cues, and scale-in vortex.</li>
      </ul>
    </td>
  </tr>
  <tr>
    <td width="50%" valign="top">
      <h4><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/miracle-seed.png" width="20" height="20" align="absmiddle"> Cobblebase 2.0+ Automation</h4>
      <ul>
        <li><b>All 43 Base Jobs</b>: Complete coverage for all 1,367+ species across Gens 1–9.</li>
        <li><b>12 Finder Subtypes</b>: Targeted foraging for stones, candies, held items, relics, and more.</li>
        <li><b>Proficiency Scaling</b>: Dynamic cooldowns scaled by 1–5 star ratings.</li>
        <li><b>Team Aura Buffs</b>: Passive speed, strength, resistance, healing, and shiny luck buffs.</li>
      </ul>
    </td>
    <td width="50%" valign="top">
      <h4><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/lucky-egg.png" width="20" height="20" align="absmiddle"> Ecosystem Compatibility</h4>
      <ul>
        <li><b>Cobbreeding Bridge</b>: Virtual egg breeding, timers, and Mirror Herb move transfers.</li>
        <li><b>PC GUI Safety Shield</b>: Fixes crashes when querying properties on non-pasture blocks.</li>
        <li><b>Cobbleworkers Tasks</b>: Automated Pickup, Fishing, Diving, and Archaeology loot.</li>
        <li><b>Draggable HUD</b>: Click <code>[⚙]</code> to customize GUI overlay positions.</li>
      </ul>
    </td>
  </tr>
</table>

---

<a id="3d-visual-projection-engine"></a>
## <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/devon-scope.png" width="24" height="24" align="absmiddle"> 3D Visual Projection Engine

The visual projection engine renders client-side holograms and energy lattices of tethered Pokémon without spawning server mobs. Toggle modes directly in the Pasture interface or PC menu:

<table width="100%">
  <thead>
    <tr>
      <th width="15%" align="center">Visual Mode</th>
      <th width="22%" align="left">Aesthetic Style</th>
      <th width="38%" align="left">Rendering Pipeline & Shader Specs</th>
      <th width="25%" align="left">World Physics</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td align="center"><b>Mode 1</b></td>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/devon-scope.png" width="18" height="18" align="absmiddle"> <b>Cyber Wireframe</b></td>
      <td>• Vector polygon line lattice (<code>RenderType.lines()</code>)<br>• Electric Cyan outline (<code>#00F5FF</code>) with empty interiors<br>• Dynamic geometry support (Charizard tail flames, wing membranes)<br>• High-performance batch buffering at <b>60–80+ FPS</b></td>
      <td>• Heightmap clamped<br>• Zero-push collision bypass</td>
    </tr>
    <tr>
      <td align="center"><b>Mode 2</b></td>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/silph-scope.png" width="18" height="18" align="absmiddle"> <b>Sci-Fi Hologram</b></td>
      <td>• Emissive energy projection (<code>RenderType.entityTranslucentEmissive</code>)<br>• Cyan base tint with real-time animated sine scanlines<br>• Fullbright unshaded lighting (<code>0x00F000F0</code>)</td>
      <td>• Heightmap clamped<br>• Zero-push collision bypass</td>
    </tr>
    <tr>
      <td align="center"><b>Mode 3</b></td>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/spell-tag.png" width="18" height="18" align="absmiddle"> <b>Ethereal Ghost</b></td>
      <td>• Translucent spirit model (<code>RenderType.itemEntityTranslucentCull</code>)<br>• 40% Spectator opacity (<code>alpha = 105</code>)<br>• Soul fire particle mist radiating from the base</td>
      <td>• Smooth hover (+0.75 blocks)<br>• Zero-push collision bypass</td>
    </tr>
    <tr>
      <td align="center"><b>Mode 0</b></td>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/smoke-ball.png" width="18" height="18" align="absmiddle"> <b>Disabled</b></td>
      <td>• Visual models disabled entirely for maximum client performance</td>
      <td>• 100% background ticking</td>
    </tr>
  </tbody>
</table>

---

<a id="all-43-cobblebase-jobs-catalog"></a>
## <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/poke-ball.png" width="24" height="24" align="absmiddle"> All 43 Cobblebase Base Automation Jobs

All 43 Cobblebase automation jobs are organized into **7 functional categories**, matching the in-game Cobblebase Admin GUI:

<table width="100%">
  <thead>
    <tr>
      <th width="20%" align="left">Category</th>
      <th width="10%" align="center">Count</th>
      <th width="70%" align="left">Registered Jobs (With Official In-Game Icons)</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><b>Combat</b></td>
      <td align="center"><code>1</code></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/muscle-band.png" width="16" height="16" align="absmiddle"> <b>Guard</b> (Base border defense & combat loot)
      </td>
    </tr>
    <tr>
      <td><b>Gathering</b></td>
      <td align="center"><code>17</code></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/fire-stone.png" width="16" height="16" align="absmiddle"> <b>Alchemist</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/hard-stone.png" width="16" height="16" align="absmiddle"> <b>Architect</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/metal-coat.png" width="16" height="16" align="absmiddle"> <b>Armorer</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/miracle-seed.png" width="16" height="16" align="absmiddle"> <b>Botanist</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/poke-ball.png" width="16" height="16" align="absmiddle"> <b>Collector</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/dive-ball.png" width="16" height="16" align="absmiddle"> <b>Diving</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/iron.png" width="16" height="16" align="absmiddle"> <b>Excavator</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/super-rod.png" width="16" height="16" align="absmiddle"> <b>Fishing</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/lava-cookie.png" width="16" height="16" align="absmiddle"> <b>Forager</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/lum-berry.png" width="16" height="16" align="absmiddle"> <b>Harvester</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/everstone.png" width="16" height="16" align="absmiddle"> <b>Mining</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/max-potion.png" width="16" height="16" align="absmiddle"> <b>Pharmacist</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/lucky-egg.png" width="16" height="16" align="absmiddle"> <b>Producer</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/relic-gold.png" width="16" height="16" align="absmiddle"> <b>Prospector</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/rare-candy.png" width="16" height="16" align="absmiddle"> <b>Scholar</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/protector.png" width="16" height="16" align="absmiddle"> <b>Smith</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/protein.png" width="16" height="16" align="absmiddle"> <b>Trainer</b>
      </td>
    </tr>
    <tr>
      <td><b>Generation</b></td>
      <td align="center"><code>5</code></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/magmarizer.png" width="16" height="16" align="absmiddle"> <b>Brewing Fuel</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/charcoal.png" width="16" height="16" align="absmiddle"> <b>Furnace Fuel</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/flame-orb.png" width="16" height="16" align="absmiddle"> <b>Lava Fill</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/never-melt-ice.png" width="16" height="16" align="absmiddle"> <b>Snow Fill</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/mystic-water.png" width="16" height="16" align="absmiddle"> <b>Water Fill</b>
      </td>
    </tr>
    <tr>
      <td><b>Legendary</b></td>
      <td align="center"><code>3</code></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/light-clay.png" width="16" height="16" align="absmiddle"> <b>Aura Boost</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/energy-powder.png" width="16" height="16" align="absmiddle"> <b>Growth Aura</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/shiny-charm.png" width="16" height="16" align="absmiddle"> <b>Lucky Charm</b> (Shiny Multiplier)
      </td>
    </tr>
    <tr>
      <td><b>Social</b></td>
      <td align="center"><code>1</code></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/soothe-bell.png" width="16" height="16" align="absmiddle"> <b>Friend Recruiter</b> (Pasture friendship & affinity generation)
      </td>
    </tr>
    <tr>
      <td><b>Support</b></td>
      <td align="center"><code>11</code></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/oval-stone.png" width="16" height="16" align="absmiddle"> <b>Egg Hatcher</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/quick-claw.png" width="16" height="16" align="absmiddle"> <b>Haste Boost</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/full-restore.png" width="16" height="16" align="absmiddle"> <b>Healer</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/sticky-barb.png" width="16" height="16" align="absmiddle"> <b>Jump Boost</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/exp-share.png" width="16" height="16" align="absmiddle"> <b>Mentor</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/black-glasses.png" width="16" height="16" align="absmiddle"> <b>Night Vision</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/iron.png" width="16" height="16" align="absmiddle"> <b>Resistance Boost</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/leftovers.png" width="16" height="16" align="absmiddle"> <b>Saturation Boost</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/carbos.png" width="16" height="16" align="absmiddle"> <b>Speed Boost</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/protein.png" width="16" height="16" align="absmiddle"> <b>Strength Boost</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/prism-scale.png" width="16" height="16" align="absmiddle"> <b>Water Breathing</b>
      </td>
    </tr>
    <tr>
      <td><b>Utility</b></td>
      <td align="center"><code>5</code></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/up-grade.png" width="16" height="16" align="absmiddle"> <b>Craftsman</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/damp-rock.png" width="16" height="16" align="absmiddle"> <b>Extinguisher</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/squirt-bottle.png" width="16" height="16" align="absmiddle"> <b>Irrigator</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/amulet-coin.png" width="16" height="16" align="absmiddle"> <b>Item Gatherer</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/scope-lens.png" width="16" height="16" align="absmiddle"> <b>Scout</b>
      </td>
    </tr>
  </tbody>
</table>

---

<a id="species-database--job-catalog"></a>
## <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/miracle-seed.png" width="24" height="24" align="absmiddle"> Universal 1,000+ Species Database & Job Catalog

Virtual Loot: Extended integrates with Cobblebase's `SpeciesSkillRegistry` to map all **1,025 National Pokédex species plus regional and form variants (1,367+ total)** across Generations 1 through 9.

Every Pokémon automatically receives native work skills based on its **Primary & Secondary Elemental Types**, **Biological Anatomy**, **Abilities**, and **Evolutionary Stage**.

```
                        [1,367+ Pokémon Species Database (Gens 1–9)]
                                             │
      ┌──────────────────────┬───────────────┴───────────────┬──────────────────────┐
      ▼                      ▼                               ▼                      ▼
[Direct Producers]   [12 Finder Subtypes]        [Gathering & Field Tasks]    [Passive Team Auras]
• Wool, Milk & Eggs  • Alchemist (Evo Items)     • Harvester (Crops & Mints)  • Speed II Aura
• Honey & Comb       • Pharmacist (Medicine)     • Mining (Ores & Minerals)   • Strength I Aura
• Precious Metals    • Excavator (Deep Minerals) • Fishing (Marine Loot)      • Resistance I Aura
• Crystals & Gems    • Botanist (Seeds & Mulch)  • Archeology (Fossils/Relic) • Shiny Luck (1.4–3x)
• Slime & Magma      • Collector, Scholar, Smith • Healers, Mentors & Guards
```

---

### 1. The 18 Elemental Type Work Aptitude Matrix
Every single Pokémon in the game maps to work aptitudes through its elemental typing:

<table width="100%">
  <thead>
    <tr>
      <th width="18%" align="left">Elemental Type</th>
      <th width="37%" align="left">Primary Work Aptitudes (With Icons)</th>
      <th width="45%" align="left">Compatible Specialized Finder & Support Roles</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/types/generation-viii/sword-shield/12.png" width="36" height="16" align="absmiddle">
        <b>Grass</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/lum-berry.png" width="16" height="16" align="absmiddle"> <b>Harvester</b> (Crops, Apricorns, Berries, Mints)
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/miracle-seed.png" width="16" height="16" align="absmiddle"> <b>Botanist</b> (<code>finder_see</code>)<br>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/max-potion.png" width="16" height="16" align="absmiddle"> <b>Pharmacist</b> (<code>finder_hea</code>)
      </td>
    </tr>
    <tr>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/types/generation-viii/sword-shield/7.png" width="36" height="16" align="absmiddle">
        <b>Bug</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/lum-berry.png" width="16" height="16" align="absmiddle"> <b>Harvester</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/silver-powder.png" width="16" height="16" align="absmiddle"> <b>Silk Producer</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/miracle-seed.png" width="16" height="16" align="absmiddle"> <b>Botanist</b> (<code>finder_see</code>)<br>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/poke-ball.png" width="16" height="16" align="absmiddle"> <b>Collector</b> (<code>finder_bal</code>)
      </td>
    </tr>
    <tr>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/types/generation-viii/sword-shield/6.png" width="36" height="16" align="absmiddle">
        <b>Rock</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/everstone.png" width="16" height="16" align="absmiddle"> <b>Mining</b> (Ores, Stone, Amethyst, Quartz)
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/iron.png" width="16" height="16" align="absmiddle"> <b>Excavator</b> (<code>finder_ore</code>)<br>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/hard-stone.png" width="16" height="16" align="absmiddle"> <b>Architect</b> (<code>finder_bui</code>)
      </td>
    </tr>
    <tr>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/types/generation-viii/sword-shield/5.png" width="36" height="16" align="absmiddle">
        <b>Ground</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/everstone.png" width="16" height="16" align="absmiddle"> <b>Mining</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/helix-fossil.png" width="16" height="16" align="absmiddle"> <b>Archeologist</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/iron.png" width="16" height="16" align="absmiddle"> <b>Excavator</b> (<code>finder_ore</code>)<br>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/relic-gold.png" width="16" height="16" align="absmiddle"> <b>Prospector</b> (<code>finder_treasure</code>)
      </td>
    </tr>
    <tr>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/types/generation-viii/sword-shield/9.png" width="36" height="16" align="absmiddle">
        <b>Steel</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/everstone.png" width="16" height="16" align="absmiddle"> <b>Mining</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/protector.png" width="16" height="16" align="absmiddle"> <b>Smith</b> (Templates & Trims)
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/hard-stone.png" width="16" height="16" align="absmiddle"> <b>Architect</b> (<code>finder_bui</code>)<br>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/metal-coat.png" width="16" height="16" align="absmiddle"> <b>Armorer</b> (<code>finder_held</code>)
      </td>
    </tr>
    <tr>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/types/generation-viii/sword-shield/11.png" width="36" height="16" align="absmiddle">
        <b>Water</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/super-rod.png" width="16" height="16" align="absmiddle"> <b>Fishing</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/dive-ball.png" width="16" height="16" align="absmiddle"> <b>Dive Looter</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/max-potion.png" width="16" height="16" align="absmiddle"> <b>Pharmacist</b> (<code>finder_hea</code>)<br>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/poke-ball.png" width="16" height="16" align="absmiddle"> <b>Collector</b> (<code>finder_bal</code>)
      </td>
    </tr>
    <tr>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/types/generation-viii/sword-shield/10.png" width="36" height="16" align="absmiddle">
        <b>Fire</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/charcoal.png" width="16" height="16" align="absmiddle"> <b>Thermal Producer</b> (Charcoal, Blaze, Magma)
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/fire-stone.png" width="16" height="16" align="absmiddle"> <b>Alchemist</b> (<code>finder_evo</code>)<br>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/protector.png" width="16" height="16" align="absmiddle"> <b>Smith</b> (<code>finder_smith</code>)
      </td>
    </tr>
    <tr>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/types/generation-viii/sword-shield/13.png" width="36" height="16" align="absmiddle">
        <b>Electric</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/magnet.png" width="16" height="16" align="absmiddle"> <b>Power Generation</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/carbos.png" width="16" height="16" align="absmiddle"> <b>Speed II Aura</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/poke-ball.png" width="16" height="16" align="absmiddle"> <b>Collector</b> (<code>finder_bal</code>)<br>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/hard-stone.png" width="16" height="16" align="absmiddle"> <b>Architect</b> (<code>finder_bui</code>)
      </td>
    </tr>
    <tr>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/types/generation-viii/sword-shield/14.png" width="36" height="16" align="absmiddle">
        <b>Psychic</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/exp-share.png" width="16" height="16" align="absmiddle"> <b>Mentor</b> (Pasture XP) &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/fire-stone.png" width="16" height="16" align="absmiddle"> <b>Alchemist</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/rare-candy.png" width="16" height="16" align="absmiddle"> <b>Scholar</b> (<code>finder_exp</code>)<br>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/full-restore.png" width="16" height="16" align="absmiddle"> <b>Healer</b> (<code>healer</code>)
      </td>
    </tr>
    <tr>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/types/generation-viii/sword-shield/18.png" width="36" height="16" align="absmiddle">
        <b>Fairy</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/full-restore.png" width="16" height="16" align="absmiddle"> <b>Healer</b> (Party HP & Status Cures)
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/max-potion.png" width="16" height="16" align="absmiddle"> <b>Pharmacist</b> (<code>finder_hea</code>)<br>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/lava-cookie.png" width="16" height="16" align="absmiddle"> <b>Chef / Forager</b> (<code>finder_food</code>)
      </td>
    </tr>
    <tr>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/types/generation-viii/sword-shield/2.png" width="36" height="16" align="absmiddle">
        <b>Fighting</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/muscle-band.png" width="16" height="16" align="absmiddle"> <b>Guard</b> (Base Defense) &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/protein.png" width="16" height="16" align="absmiddle"> <b>Strength I Aura</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/protein.png" width="16" height="16" align="absmiddle"> <b>Trainer</b> (<code>finder_stat</code>)<br>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/metal-coat.png" width="16" height="16" align="absmiddle"> <b>Armorer</b> (<code>finder_held</code>)
      </td>
    </tr>
    <tr>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/types/generation-viii/sword-shield/17.png" width="36" height="16" align="absmiddle">
        <b>Dark</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/muscle-band.png" width="16" height="16" align="absmiddle"> <b>Guard</b> (Night Patrol) &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/relic-gold.png" width="16" height="16" align="absmiddle"> <b>Prospector</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/metal-coat.png" width="16" height="16" align="absmiddle"> <b>Armorer</b> (<code>finder_held</code>)<br>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/relic-gold.png" width="16" height="16" align="absmiddle"> <b>Prospector</b> (<code>finder_treasure</code>)
      </td>
    </tr>
    <tr>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/types/generation-viii/sword-shield/8.png" width="36" height="16" align="absmiddle">
        <b>Ghost</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/helix-fossil.png" width="16" height="16" align="absmiddle"> <b>Archeologist</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/relic-gold.png" width="16" height="16" align="absmiddle"> <b>Prospector</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/rare-candy.png" width="16" height="16" align="absmiddle"> <b>Scholar</b> (<code>finder_exp</code>)<br>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/fire-stone.png" width="16" height="16" align="absmiddle"> <b>Alchemist</b> (<code>finder_evo</code>)
      </td>
    </tr>
    <tr>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/types/generation-viii/sword-shield/1.png" width="36" height="16" align="absmiddle">
        <b>Normal</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/lucky-egg.png" width="16" height="16" align="absmiddle"> <b>Direct Producer</b> (Wool, Milk, Eggs) &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/poke-ball.png" width="16" height="16" align="absmiddle"> <b>Collector</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/lava-cookie.png" width="16" height="16" align="absmiddle"> <b>Chef / Forager</b> (<code>finder_food</code>)<br>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/poke-ball.png" width="16" height="16" align="absmiddle"> <b>Collector</b> (<code>finder_bal</code>)
      </td>
    </tr>
    <tr>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/types/generation-viii/sword-shield/3.png" width="36" height="16" align="absmiddle">
        <b>Flying</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/lucky-egg.png" width="16" height="16" align="absmiddle"> <b>Poultry Producer</b> (Eggs) &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/scope-lens.png" width="16" height="16" align="absmiddle"> <b>Pickup Scout</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/poke-ball.png" width="16" height="16" align="absmiddle"> <b>Collector</b> (<code>finder_bal</code>)<br>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/carbos.png" width="16" height="16" align="absmiddle"> <b>Speed Aura</b>
      </td>
    </tr>
    <tr>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/types/generation-viii/sword-shield/16.png" width="36" height="16" align="absmiddle">
        <b>Dragon</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/muscle-band.png" width="16" height="16" align="absmiddle"> <b>Apex Guard</b> (Base Defense) &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/protein.png" width="16" height="16" align="absmiddle"> <b>Strength Aura</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/rare-candy.png" width="16" height="16" align="absmiddle"> <b>Scholar</b> (<code>finder_exp</code>)<br>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/metal-coat.png" width="16" height="16" align="absmiddle"> <b>Armorer</b> (<code>finder_held</code>)
      </td>
    </tr>
    <tr>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/types/generation-viii/sword-shield/15.png" width="36" height="16" align="absmiddle">
        <b>Ice</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/never-melt-ice.png" width="16" height="16" align="absmiddle"> <b>Preservation</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/iron.png" width="16" height="16" align="absmiddle"> <b>Resistance I Aura</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/hard-stone.png" width="16" height="16" align="absmiddle"> <b>Architect</b> (<code>finder_bui</code>)<br>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/iron.png" width="16" height="16" align="absmiddle"> <b>Excavator</b> (<code>finder_ore</code>)
      </td>
    </tr>
    <tr>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/types/generation-viii/sword-shield/4.png" width="36" height="16" align="absmiddle">
        <b>Poison</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/sticky-barb.png" width="16" height="16" align="absmiddle"> <b>Slime & Viscosity Producer</b> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/fire-stone.png" width="16" height="16" align="absmiddle"> <b>Alchemist</b>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/max-potion.png" width="16" height="16" align="absmiddle"> <b>Pharmacist</b> (<code>finder_hea</code>)<br>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/relic-gold.png" width="16" height="16" align="absmiddle"> <b>Prospector</b> (<code>finder_treasure</code>)
      </td>
    </tr>
  </tbody>
</table>

---

### 2. Tiered Drop Probabilities by Skill Proficiency
All Finder, Mining, Harvester, Fishing, and Archaeology tasks roll across **4 loot tiers** based on proficiency:

$$\text{Loot Tiers: } \text{Common (Tier 0)} \longrightarrow \text{Uncommon (Tier 1)} \longrightarrow \text{Rare (Tier 2)} \longrightarrow \text{Ultra Rare (Tier 3)}$$

<table width="100%">
  <thead>
    <tr>
      <th width="32%" align="center">Proficiency Rating</th>
      <th width="17%" align="center">Tier 0 (Common)</th>
      <th width="17%" align="center">Tier 1 (Uncommon)</th>
      <th width="17%" align="center">Tier 2 (Rare)</th>
      <th width="17%" align="center">Tier 3 (Ultra Rare)</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td align="center"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"> <b>1 Star (Novice)</b></td>
      <td align="center"><b>80%</b></td>
      <td align="center"><b>20%</b></td>
      <td align="center">0%</td>
      <td align="center">0%</td>
    </tr>
    <tr>
      <td align="center"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"> <b>2 Stars (Apprentice)</b></td>
      <td align="center"><b>80%</b></td>
      <td align="center"><b>20%</b></td>
      <td align="center">0%</td>
      <td align="center">0%</td>
    </tr>
    <tr>
      <td align="center"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"> <b>3 Stars (Skilled)</b></td>
      <td align="center"><b>50%</b></td>
      <td align="center"><b>35%</b></td>
      <td align="center"><b>15%</b></td>
      <td align="center">0%</td>
    </tr>
    <tr>
      <td align="center"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"> <b>4 Stars (Expert)</b></td>
      <td align="center"><b>20%</b></td>
      <td align="center"><b>40%</b></td>
      <td align="center"><b>30%</b></td>
      <td align="center"><b>10%</b></td>
    </tr>
    <tr>
      <td align="center"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"> <b>5 Stars (Master)</b></td>
      <td align="center"><b>0%</b></td>
      <td align="center"><b>30%</b></td>
      <td align="center"><b>45%</b></td>
      <td align="center"><b>25%</b></td>
    </tr>
  </tbody>
</table>

---

### 3. Direct Biological Producer Registry (`cobblebase:producer`)
Direct producers periodically generate fixed items based on authentic species anatomy directly into pasture storage:

<table width="100%">
  <thead>
    <tr>
      <th width="20%" align="left">Production Category</th>
      <th width="32%" align="left">Item Output & Sprites</th>
      <th width="48%" align="left">Eligible Species Gallery (Gens 1–9)</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><b>Wool & Fiber</b></td>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/fluffy-tail.png" width="18" height="18" align="absmiddle"> <code>minecraft:white_wool</code> (x1–x2)</td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/179.png" width="22" height="22" align="absmiddle"> Mareep
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/180.png" width="22" height="22" align="absmiddle"> Flaaffy
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/181.png" width="22" height="22" align="absmiddle"> Ampharos
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/333.png" width="22" height="22" align="absmiddle"> Swablu
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/334.png" width="22" height="22" align="absmiddle"> Altaria
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/546.png" width="22" height="22" align="absmiddle"> Cottonee
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/547.png" width="22" height="22" align="absmiddle"> Whimsicott
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/831.png" width="22" height="22" align="absmiddle"> Wooloo
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/832.png" width="22" height="22" align="absmiddle"> Dubwool
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/830.png" width="22" height="22" align="absmiddle"> Eldegoss
      </td>
    </tr>
    <tr>
      <td><b>Silk & Webs</b></td>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/silver-powder.png" width="18" height="18" align="absmiddle"> <code>minecraft:string</code> (x1–x3)</td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/10.png" width="22" height="22" align="absmiddle"> Caterpie
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/13.png" width="22" height="22" align="absmiddle"> Weedle
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/167.png" width="22" height="22" align="absmiddle"> Spinarak
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/168.png" width="22" height="22" align="absmiddle"> Ariados
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/265.png" width="22" height="22" align="absmiddle"> Wurmple
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/540.png" width="22" height="22" align="absmiddle"> Sewaddle
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/542.png" width="22" height="22" align="absmiddle"> Leavanny
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/595.png" width="22" height="22" align="absmiddle"> Joltik
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/596.png" width="22" height="22" align="absmiddle"> Galvantula
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/751.png" width="22" height="22" align="absmiddle"> Dewpider
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/752.png" width="22" height="22" align="absmiddle"> Araquanid
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/872.png" width="22" height="22" align="absmiddle"> Snom
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/873.png" width="22" height="22" align="absmiddle"> Frosmoth
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/917.png" width="22" height="22" align="absmiddle"> Tarountula
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/918.png" width="22" height="22" align="absmiddle"> Spidops
      </td>
    </tr>
    <tr>
      <td><b>Dairy Milk</b></td>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/moomoo-milk.png" width="18" height="18" align="absmiddle"> <code>cobblemon:moomoo_milk</code> (x1–x2)</td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/128.png" width="22" height="22" align="absmiddle"> Tauros
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/241.png" width="22" height="22" align="absmiddle"> Miltank
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/626.png" width="22" height="22" align="absmiddle"> Bouffalant
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/672.png" width="22" height="22" align="absmiddle"> Skiddo
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/673.png" width="22" height="22" align="absmiddle"> Gogoat
      </td>
    </tr>
    <tr>
      <td><b>Poultry Eggs</b></td>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/lucky-egg.png" width="18" height="18" align="absmiddle"> <code>minecraft:egg</code> (x1–x2)</td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/16.png" width="22" height="22" align="absmiddle"> Pidgey
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/102.png" width="22" height="22" align="absmiddle"> Exeggcute
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/113.png" width="22" height="22" align="absmiddle"> Chansey
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/242.png" width="22" height="22" align="absmiddle"> Blissey
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/255.png" width="22" height="22" align="absmiddle"> Torchic
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/440.png" width="22" height="22" align="absmiddle"> Happiny
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/661.png" width="22" height="22" align="absmiddle"> Fletchling
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/663.png" width="22" height="22" align="absmiddle"> Talonflame
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/722.png" width="22" height="22" align="absmiddle"> Rowlet
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/940.png" width="22" height="22" align="absmiddle"> Wattrel
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/941.png" width="22" height="22" align="absmiddle"> Kilowattrel
      </td>
    </tr>
    <tr>
      <td><b>Honey & Comb</b></td>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/honey.png" width="18" height="18" align="absmiddle"> <code>minecraft:honeycomb</code> / <code>honey_bottle</code></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/15.png" width="22" height="22" align="absmiddle"> Beedrill
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/415.png" width="22" height="22" align="absmiddle"> Combee
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/416.png" width="22" height="22" align="absmiddle"> Vespiquen
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/742.png" width="22" height="22" align="absmiddle"> Cutiefly
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/743.png" width="22" height="22" align="absmiddle"> Ribombee
      </td>
    </tr>
    <tr>
      <td><b>Precious Metals</b></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/nugget.png" width="18" height="18" align="absmiddle"> <code>gold_nugget</code> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/iron.png" width="18" height="18" align="absmiddle"> <code>iron_nugget</code>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/52.png" width="22" height="22" align="absmiddle"> Meowth
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/53.png" width="22" height="22" align="absmiddle"> Persian
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/302.png" width="22" height="22" align="absmiddle"> Sableye
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/863.png" width="22" height="22" align="absmiddle"> Perrserker
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/999.png" width="22" height="22" align="absmiddle"> Gimmighoul
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1000.png" width="22" height="22" align="absmiddle"> Gholdengo
      </td>
    </tr>
    <tr>
      <td><b>Gems & Crystals</b></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="18" height="18" align="absmiddle"> <code>diamond</code> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/pearl.png" width="18" height="18" align="absmiddle"> <code>amethyst_shard</code>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/302.png" width="22" height="22" align="absmiddle"> Sableye
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/524.png" width="22" height="22" align="absmiddle"> Roggenrola
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/525.png" width="22" height="22" align="absmiddle"> Boldore
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/526.png" width="22" height="22" align="absmiddle"> Gigalith
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/703.png" width="22" height="22" align="absmiddle"> Carbink
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/719.png" width="22" height="22" align="absmiddle"> Diancie
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/932.png" width="22" height="22" align="absmiddle"> Nacli
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/933.png" width="22" height="22" align="absmiddle"> Naclstack
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/934.png" width="22" height="22" align="absmiddle"> Garganacl
      </td>
    </tr>
    <tr>
      <td><b>Orchard Fruits</b></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/oran-berry.png" width="18" height="18" align="absmiddle"> <code>apple</code> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/sitrus-berry.png" width="18" height="18" align="absmiddle"> <code>sweet_berries</code>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/357.png" width="22" height="22" align="absmiddle"> Tropius
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/420.png" width="22" height="22" align="absmiddle"> Cherubi
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/421.png" width="22" height="22" align="absmiddle"> Cherrim
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/761.png" width="22" height="22" align="absmiddle"> Bounsweet
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/763.png" width="22" height="22" align="absmiddle"> Tsareena
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/840.png" width="22" height="22" align="absmiddle"> Applin
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/841.png" width="22" height="22" align="absmiddle"> Flapple
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/842.png" width="22" height="22" align="absmiddle"> Appletun
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/928.png" width="22" height="22" align="absmiddle"> Smoliv
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/930.png" width="22" height="22" align="absmiddle"> Arboliva
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1011.png" width="22" height="22" align="absmiddle"> Dipplin
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1019.png" width="22" height="22" align="absmiddle"> Hydrapple
      </td>
    </tr>
    <tr>
      <td><b>Slime & Viscosity</b></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/sticky-barb.png" width="18" height="18" align="absmiddle"> <code>slime_ball</code> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/black-sludge.png" width="18" height="18" align="absmiddle"> <code>magma_cream</code>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/88.png" width="22" height="22" align="absmiddle"> Grimer
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/89.png" width="22" height="22" align="absmiddle"> Muk
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/218.png" width="22" height="22" align="absmiddle"> Slugma
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/219.png" width="22" height="22" align="absmiddle"> Magcargo
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/316.png" width="22" height="22" align="absmiddle"> Gulpin
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/317.png" width="22" height="22" align="absmiddle"> Swalot
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/422.png" width="22" height="22" align="absmiddle"> Shellos
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/423.png" width="22" height="22" align="absmiddle"> Gastrodon
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/704.png" width="22" height="22" align="absmiddle"> Goomy
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/706.png" width="22" height="22" align="absmiddle"> Goodra
      </td>
    </tr>
    <tr>
      <td><b>Fuel & Charcoal</b></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/charcoal.png" width="18" height="18" align="absmiddle"> <code>charcoal</code> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/magmarizer.png" width="18" height="18" align="absmiddle"> <code>blaze_powder</code>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/126.png" width="22" height="22" align="absmiddle"> Magmar
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/240.png" width="22" height="22" align="absmiddle"> Magby
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/322.png" width="22" height="22" align="absmiddle"> Numel
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/323.png" width="22" height="22" align="absmiddle"> Camerupt
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/324.png" width="22" height="22" align="absmiddle"> Torkoal
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/467.png" width="22" height="22" align="absmiddle"> Magmortar
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/837.png" width="22" height="22" align="absmiddle"> Rolycoly
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/838.png" width="22" height="22" align="absmiddle"> Carkol
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/839.png" width="22" height="22" align="absmiddle"> Coalossal
      </td>
    </tr>
    <tr>
      <td><b>Pearls & Prismarine</b></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/pearl.png" width="18" height="18" align="absmiddle"> <code>pearl</code> &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/prism-scale.png" width="18" height="18" align="absmiddle"> <code>prismarine_shard</code>
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/90.png" width="22" height="22" align="absmiddle"> Shellder
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/91.png" width="22" height="22" align="absmiddle"> Cloyster
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/366.png" width="22" height="22" align="absmiddle"> Clamperl
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/367.png" width="22" height="22" align="absmiddle"> Huntail
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/368.png" width="22" height="22" align="absmiddle"> Gorebyss
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/557.png" width="22" height="22" align="absmiddle"> Dwebble
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/558.png" width="22" height="22" align="absmiddle"> Crustle
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/688.png" width="22" height="22" align="absmiddle"> Binacle
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/689.png" width="22" height="22" align="absmiddle"> Barbaracle
      </td>
    </tr>
    <tr>
      <td><b>Fermented Juice & Teas</b></td>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/berry-juice.png" width="18" height="18" align="absmiddle"> <code>cobblemon:berry_juice</code> (x1)</td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/213.png" width="22" height="22" align="absmiddle"> Shuckle
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/854.png" width="22" height="22" align="absmiddle"> Sinistea
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/855.png" width="22" height="22" align="absmiddle"> Polteageist
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1012.png" width="22" height="22" align="absmiddle"> Poltchageist
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1013.png" width="22" height="22" align="absmiddle"> Sinistcha
      </td>
    </tr>
  </tbody>
</table>

---

### 4. The 12 Specialized Finder Subtypes (`cobblebase:finder_*`)
Finders forage for specialized loot tables based on their elemental typing, species traits, and movepool lore:

<table width="100%">
  <thead>
    <tr>
      <th width="18%" align="left">Finder Subtype</th>
      <th width="38%" align="left">Focus Target Items (With Individual Sprites)</th>
      <th width="44%" align="left">Eligible Species Gallery (Gens 1–9)</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><b>Alchemist</b><br><code>finder_evo</code></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/fire-stone.png" width="16" height="16" align="absmiddle"> Fire Stone &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/water-stone.png" width="16" height="16" align="absmiddle"> Water Stone &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/thunder-stone.png" width="16" height="16" align="absmiddle"> Thunder Stone &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/leaf-stone.png" width="16" height="16" align="absmiddle"> Leaf Stone &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/moon-stone.png" width="16" height="16" align="absmiddle"> Moon Stone &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/sun-stone.png" width="16" height="16" align="absmiddle"> Sun Stone &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/up-grade.png" width="16" height="16" align="absmiddle"> Linking Cord &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/ability-capsule.png" width="16" height="16" align="absmiddle"> Ability Patch &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/kings-rock.png" width="16" height="16" align="absmiddle"> King's Rock
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/65.png" width="22" height="22" align="absmiddle"> Alakazam
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/178.png" width="22" height="22" align="absmiddle"> Xatu
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/199.png" width="22" height="22" align="absmiddle"> Slowking
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/282.png" width="22" height="22" align="absmiddle"> Gardevoir
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/344.png" width="22" height="22" align="absmiddle"> Claydol
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/429.png" width="22" height="22" align="absmiddle"> Mismagius
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/561.png" width="22" height="22" align="absmiddle"> Sigilyph
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/576.png" width="22" height="22" align="absmiddle"> Gothitelle
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/579.png" width="22" height="22" align="absmiddle"> Reuniclus
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/606.png" width="22" height="22" align="absmiddle"> Beheeyem
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/655.png" width="22" height="22" align="absmiddle"> Delphox
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/858.png" width="22" height="22" align="absmiddle"> Hatterene
      </td>
    </tr>
    <tr>
      <td><b>Pharmacist</b><br><code>finder_hea</code></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/max-potion.png" width="16" height="16" align="absmiddle"> Max Potion &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/full-restore.png" width="16" height="16" align="absmiddle"> Full Restore &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/revive.png" width="16" height="16" align="absmiddle"> Revive &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/max-revive.png" width="16" height="16" align="absmiddle"> Max Revive &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/sacred-ash.png" width="16" height="16" align="absmiddle"> Sacred Ash &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/full-heal.png" width="16" height="16" align="absmiddle"> Full Heal &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/energy-root.png" width="16" height="16" align="absmiddle"> Energy Root
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/113.png" width="22" height="22" align="absmiddle"> Chansey
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/154.png" width="22" height="22" align="absmiddle"> Meganium
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/242.png" width="22" height="22" align="absmiddle"> Blissey
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/468.png" width="22" height="22" align="absmiddle"> Togekiss
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/531.png" width="22" height="22" align="absmiddle"> Audino
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/581.png" width="22" height="22" align="absmiddle"> Swanna
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/594.png" width="22" height="22" align="absmiddle"> Alomomola
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/671.png" width="22" height="22" align="absmiddle"> Florges
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/683.png" width="22" height="22" align="absmiddle"> Aromatisse
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/764.png" width="22" height="22" align="absmiddle"> Comfey
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/830.png" width="22" height="22" align="absmiddle"> Eldegoss
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/876.png" width="22" height="22" align="absmiddle"> Indeedee
      </td>
    </tr>
    <tr>
      <td><b>Architect</b><br><code>finder_bui</code></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/prism-scale.png" width="16" height="16" align="absmiddle"> Prismarine &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/sun-stone.png" width="16" height="16" align="absmiddle"> Sea Lantern &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/dusk-stone.png" width="16" height="16" align="absmiddle"> Crying Obsidian &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/hard-stone.png" width="16" height="16" align="absmiddle"> Quartz &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/smooth-rock.png" width="16" height="16" align="absmiddle"> Smooth Stone
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/82.png" width="22" height="22" align="absmiddle"> Magneton
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/137.png" width="22" height="22" align="absmiddle"> Porygon
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/233.png" width="22" height="22" align="absmiddle"> Porygon2
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/437.png" width="22" height="22" align="absmiddle"> Bronzong
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/462.png" width="22" height="22" align="absmiddle"> Magnezone
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/474.png" width="22" height="22" align="absmiddle"> Porygon-Z
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/479.png" width="22" height="22" align="absmiddle"> Rotom
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/532.png" width="22" height="22" align="absmiddle"> Timburr
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/533.png" width="22" height="22" align="absmiddle"> Gurdurr
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/534.png" width="22" height="22" align="absmiddle"> Conkeldurr
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/557.png" width="22" height="22" align="absmiddle"> Dwebble
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/558.png" width="22" height="22" align="absmiddle"> Crustle
      </td>
    </tr>
    <tr>
      <td><b>Excavator</b><br><code>finder_ore</code></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/iron.png" width="16" height="16" align="absmiddle"> Raw Iron &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/relic-copper.png" width="16" height="16" align="absmiddle"> Raw Copper &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/nugget.png" width="16" height="16" align="absmiddle"> Raw Gold &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"> Diamonds &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/everstone.png" width="16" height="16" align="absmiddle"> Emeralds &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/comet-shard.png" width="16" height="16" align="absmiddle"> Ancient Debris
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/27.png" width="22" height="22" align="absmiddle"> Sandshrew
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/28.png" width="22" height="22" align="absmiddle"> Sandslash
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/50.png" width="22" height="22" align="absmiddle"> Diglett
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/51.png" width="22" height="22" align="absmiddle"> Dugtrio
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/95.png" width="22" height="22" align="absmiddle"> Onix
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/208.png" width="22" height="22" align="absmiddle"> Steelix
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/232.png" width="22" height="22" align="absmiddle"> Donphan
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/444.png" width="22" height="22" align="absmiddle"> Gabite
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/445.png" width="22" height="22" align="absmiddle"> Garchomp
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/450.png" width="22" height="22" align="absmiddle"> Hippowdon
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/529.png" width="22" height="22" align="absmiddle"> Drilbur
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/530.png" width="22" height="22" align="absmiddle"> Excadrill
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/984.png" width="22" height="22" align="absmiddle"> Great Tusk
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1003.png" width="22" height="22" align="absmiddle"> Ting-Lu
      </td>
    </tr>
    <tr>
      <td><b>Botanist</b><br><code>finder_see</code></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/miracle-seed.png" width="16" height="16" align="absmiddle"> Apricorn Seeds &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/lum-berry.png" width="16" height="16" align="absmiddle"> Mint Seeds &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/energy-powder.png" width="16" height="16" align="absmiddle"> Fertilizers &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/heal-powder.png" width="16" height="16" align="absmiddle"> Organic Mulch
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png" width="22" height="22" align="absmiddle"> Venusaur
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/45.png" width="22" height="22" align="absmiddle"> Vileplume
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/182.png" width="22" height="22" align="absmiddle"> Bellossom
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/251.png" width="22" height="22" align="absmiddle"> Celebi
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/254.png" width="22" height="22" align="absmiddle"> Sceptile
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/407.png" width="22" height="22" align="absmiddle"> Roserade
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/470.png" width="22" height="22" align="absmiddle"> Leafeon
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/492.png" width="22" height="22" align="absmiddle"> Shaymin
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/549.png" width="22" height="22" align="absmiddle"> Lilligant
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/709.png" width="22" height="22" align="absmiddle"> Trevenant
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/724.png" width="22" height="22" align="absmiddle"> Decidueye
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/763.png" width="22" height="22" align="absmiddle"> Tsareena
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/908.png" width="22" height="22" align="absmiddle"> Meowscarada
      </td>
    </tr>
    <tr>
      <td><b>Collector</b><br><code>finder_bal</code></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/ultra-ball.png" width="16" height="16" align="absmiddle"> Ultra Ball &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/heavy-ball.png" width="16" height="16" align="absmiddle"> Heavy Ball &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/moon-ball.png" width="16" height="16" align="absmiddle"> Moon Ball &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/love-ball.png" width="16" height="16" align="absmiddle"> Love Ball &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/cherish-ball.png" width="16" height="16" align="absmiddle"> Cherish Ball &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/master-ball.png" width="16" height="16" align="absmiddle"> Master Ball
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/190.png" width="22" height="22" align="absmiddle"> Aipom
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/263.png" width="22" height="22" align="absmiddle"> Zigzagoon
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/264.png" width="22" height="22" align="absmiddle"> Linoone
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/417.png" width="22" height="22" align="absmiddle"> Pachirisu
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/424.png" width="22" height="22" align="absmiddle"> Ambipom
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/573.png" width="22" height="22" align="absmiddle"> Cinccino
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/587.png" width="22" height="22" align="absmiddle"> Emolga
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/702.png" width="22" height="22" align="absmiddle"> Dedenne
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/819.png" width="22" height="22" align="absmiddle"> Skwovet
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/820.png" width="22" height="22" align="absmiddle"> Greedent
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/877.png" width="22" height="22" align="absmiddle"> Morpeko
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/921.png" width="22" height="22" align="absmiddle"> Pawmi
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/923.png" width="22" height="22" align="absmiddle"> Pawmot
      </td>
    </tr>
    <tr>
      <td><b>Scholar</b><br><code>finder_exp</code></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/rare-candy.png" width="16" height="16" align="absmiddle"> Exp Candies (XS–XL) &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/exp-share.png" width="16" height="16" align="absmiddle"> Rare Candy
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/97.png" width="22" height="22" align="absmiddle"> Hypno
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/150.png" width="22" height="22" align="absmiddle"> Mewtwo
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/164.png" width="22" height="22" align="absmiddle"> Noctowl
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/199.png" width="22" height="22" align="absmiddle"> Slowking
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/376.png" width="22" height="22" align="absmiddle"> Metagross
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/480.png" width="22" height="22" align="absmiddle"> Uxie
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/481.png" width="22" height="22" align="absmiddle"> Mesprit
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/482.png" width="22" height="22" align="absmiddle"> Azelf
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/765.png" width="22" height="22" align="absmiddle"> Oranguru
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/826.png" width="22" height="22" align="absmiddle"> Orbeetle
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/954.png" width="22" height="22" align="absmiddle"> Rabsca
      </td>
    </tr>
    <tr>
      <td><b>Chef / Forager</b><br><code>finder_food</code></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/lava-cookie.png" width="16" height="16" align="absmiddle"> Lava Cookies &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/casteliacone.png" width="16" height="16" align="absmiddle"> Casteliacones &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/sweet-heart.png" width="16" height="16" align="absmiddle"> Pastries &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/sitrus-berry.png" width="16" height="16" align="absmiddle"> Rare Berries
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/122.png" width="22" height="22" align="absmiddle"> Mr. Mime
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/143.png" width="22" height="22" align="absmiddle"> Snorlax
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/235.png" width="22" height="22" align="absmiddle"> Smeargle
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/439.png" width="22" height="22" align="absmiddle"> Mime Jr.
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/446.png" width="22" height="22" align="absmiddle"> Munchlax
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/685.png" width="22" height="22" align="absmiddle"> Slurpuff
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/845.png" width="22" height="22" align="absmiddle"> Cramorant
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/869.png" width="22" height="22" align="absmiddle"> Alcremie
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/926.png" width="22" height="22" align="absmiddle"> Fidough
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/927.png" width="22" height="22" align="absmiddle"> Dachsbun
      </td>
    </tr>
    <tr>
      <td><b>Trainer</b><br><code>finder_stat</code></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/hp-up.png" width="16" height="16" align="absmiddle"> HP Up &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/protein.png" width="16" height="16" align="absmiddle"> Protein &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/iron.png" width="16" height="16" align="absmiddle"> Iron &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/carbos.png" width="16" height="16" align="absmiddle"> Carbos &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/calcium.png" width="16" height="16" align="absmiddle"> Calcium &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/zinc.png" width="16" height="16" align="absmiddle"> Zinc &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/power-bracer.png" width="16" height="16" align="absmiddle"> Power Items
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/66.png" width="22" height="22" align="absmiddle"> Machop
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/67.png" width="22" height="22" align="absmiddle"> Machoke
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/68.png" width="22" height="22" align="absmiddle"> Machamp
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/106.png" width="22" height="22" align="absmiddle"> Hitmonlee
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/107.png" width="22" height="22" align="absmiddle"> Hitmonchan
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/237.png" width="22" height="22" align="absmiddle"> Hitmontop
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/296.png" width="22" height="22" align="absmiddle"> Makuhita
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/297.png" width="22" height="22" align="absmiddle"> Hariyama
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/538.png" width="22" height="22" align="absmiddle"> Throh
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/539.png" width="22" height="22" align="absmiddle"> Sawk
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/740.png" width="22" height="22" align="absmiddle"> Crabominable
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/892.png" width="22" height="22" align="absmiddle"> Urshifu
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/979.png" width="22" height="22" align="absmiddle"> Annihilape
      </td>
    </tr>
    <tr>
      <td><b>Armorer</b><br><code>finder_held</code></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/choice-band.png" width="16" height="16" align="absmiddle"> Choice Band &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/choice-specs.png" width="16" height="16" align="absmiddle"> Choice Specs &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/choice-scarf.png" width="16" height="16" align="absmiddle"> Choice Scarf &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/life-orb.png" width="16" height="16" align="absmiddle"> Life Orb &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/focus-sash.png" width="16" height="16" align="absmiddle"> Focus Sash &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/leftovers.png" width="16" height="16" align="absmiddle"> Leftovers &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/assault-vest.png" width="16" height="16" align="absmiddle"> Assault Vest &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/rocky-helmet.png" width="16" height="16" align="absmiddle"> Rocky Helmet
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/227.png" width="22" height="22" align="absmiddle"> Skarmory
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/448.png" width="22" height="22" align="absmiddle"> Lucario
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/475.png" width="22" height="22" align="absmiddle"> Gallade
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/589.png" width="22" height="22" align="absmiddle"> Escavalier
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/625.png" width="22" height="22" align="absmiddle"> Bisharp
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/679.png" width="22" height="22" align="absmiddle"> Honedge
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/681.png" width="22" height="22" align="absmiddle"> Aegislash
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/823.png" width="22" height="22" align="absmiddle"> Corviknight
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/865.png" width="22" height="22" align="absmiddle"> Sirfetch'd
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/936.png" width="22" height="22" align="absmiddle"> Armarouge
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/937.png" width="22" height="22" align="absmiddle"> Ceruledge
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/983.png" width="22" height="22" align="absmiddle"> Kingambit
      </td>
    </tr>
    <tr>
      <td><b>Prospector</b><br><code>finder_treasure</code></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/relic-gold.png" width="16" height="16" align="absmiddle"> Relic Gold &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/relic-silver.png" width="16" height="16" align="absmiddle"> Relic Silver &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/relic-vase.png" width="16" height="16" align="absmiddle"> Ancient Sherds &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/nugget.png" width="16" height="16" align="absmiddle"> Gold Ingots &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/comet-shard.png" width="16" height="16" align="absmiddle"> Netherite Scrap
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/198.png" width="22" height="22" align="absmiddle"> Murkrow
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/215.png" width="22" height="22" align="absmiddle"> Sneasel
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/302.png" width="22" height="22" align="absmiddle"> Sableye
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/430.png" width="22" height="22" align="absmiddle"> Honchkrow
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/461.png" width="22" height="22" align="absmiddle"> Weavile
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/563.png" width="22" height="22" align="absmiddle"> Cofagrigus
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/827.png" width="22" height="22" align="absmiddle"> Nickit
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/828.png" width="22" height="22" align="absmiddle"> Thievul
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/867.png" width="22" height="22" align="absmiddle"> Runerigus
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/999.png" width="22" height="22" align="absmiddle"> Gimmighoul
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1000.png" width="22" height="22" align="absmiddle"> Gholdengo
      </td>
    </tr>
    <tr>
      <td><b>Smith</b><br><code>finder_smith</code></td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/metal-coat.png" width="16" height="16" align="absmiddle"> Metal Coat &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/protector.png" width="16" height="16" align="absmiddle"> Armor Trims &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/dragon-scale.png" width="16" height="16" align="absmiddle"> Smithing Templates &nbsp;
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/iron.png" width="16" height="16" align="absmiddle"> Heavy Cores
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/205.png" width="22" height="22" align="absmiddle"> Forretress
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/485.png" width="22" height="22" align="absmiddle"> Heatran
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/601.png" width="22" height="22" align="absmiddle"> Klinklang
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/809.png" width="22" height="22" align="absmiddle"> Melmetal
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/838.png" width="22" height="22" align="absmiddle"> Carkol
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/957.png" width="22" height="22" align="absmiddle"> Tinkatink
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/958.png" width="22" height="22" align="absmiddle"> Tinkatuff
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/959.png" width="22" height="22" align="absmiddle"> Tinkaton
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/966.png" width="22" height="22" align="absmiddle"> Revavroom
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/968.png" width="22" height="22" align="absmiddle"> Orthworm
      </td>
    </tr>
  </tbody>
</table>

---

### 5. Field Operations, Support Roles & Passive Team Auras

<table width="100%">
  <thead>
    <tr>
      <th width="24%" align="left">Field / Support Task</th>
      <th width="32%" align="left">Mechanics & Item Drops</th>
      <th width="44%" align="left">Representative Species Gallery</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/lum-berry.png" width="18" height="18" align="absmiddle"> <b>Harvester</b><br><code>cobblebase:harvester</code></td>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/miracle-seed.png" width="18" height="18" align="absmiddle"> 250+ Grass & Bug species harvest mature crops, apricorns, berries, and mints.</td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png" width="22" height="22" align="absmiddle"> Venusaur
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/154.png" width="22" height="22" align="absmiddle"> Meganium
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/251.png" width="22" height="22" align="absmiddle"> Celebi
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/254.png" width="22" height="22" align="absmiddle"> Sceptile
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/389.png" width="22" height="22" align="absmiddle"> Torterra
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/497.png" width="22" height="22" align="absmiddle"> Serperior
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/652.png" width="22" height="22" align="absmiddle"> Chesnaught
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/812.png" width="22" height="22" align="absmiddle"> Rillaboom
      </td>
    </tr>
    <tr>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/everstone.png" width="18" height="18" align="absmiddle"> <b>Mining</b><br><code>cobblebase:mining</code></td>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/hard-stone.png" width="18" height="18" align="absmiddle"> 200+ Rock, Ground & Steel species dig for regional ores scaled by proficiency.</td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/208.png" width="22" height="22" align="absmiddle"> Steelix
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/248.png" width="22" height="22" align="absmiddle"> Tyranitar
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/306.png" width="22" height="22" align="absmiddle"> Aggron
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/464.png" width="22" height="22" align="absmiddle"> Rhyperior
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/526.png" width="22" height="22" align="absmiddle"> Gigalith
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/530.png" width="22" height="22" align="absmiddle"> Excadrill
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1003.png" width="22" height="22" align="absmiddle"> Ting-Lu
      </td>
    </tr>
    <tr>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/super-rod.png" width="18" height="18" align="absmiddle"> <b>Fishing & Diving</b><br><code>cobblebase:fishing</code></td>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/super-rod.png" width="18" height="18" align="absmiddle"> 180+ Water species catch fish, marine treasures, and submerged artifacts.</td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/9.png" width="22" height="22" align="absmiddle"> Blastoise
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/130.png" width="22" height="22" align="absmiddle"> Gyarados
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/230.png" width="22" height="22" align="absmiddle"> Kingdra
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/321.png" width="22" height="22" align="absmiddle"> Wailord
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/382.png" width="22" height="22" align="absmiddle"> Kyogre
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/746.png" width="22" height="22" align="absmiddle"> Wishiwashi
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/977.png" width="22" height="22" align="absmiddle"> Dondozo
      </td>
    </tr>
    <tr>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/helix-fossil.png" width="18" height="18" align="absmiddle"> <b>Archeologist</b><br><code>cobblebase:archeologist</code></td>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/helix-fossil.png" width="18" height="18" align="absmiddle"> 120+ Fossil & Ancient species excavate relics, fossils, and sherds.</td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/31.png" width="22" height="22" align="absmiddle"> Nidoqueen
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/34.png" width="22" height="22" align="absmiddle"> Nidoking
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/142.png" width="22" height="22" align="absmiddle"> Aerodactyl
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/344.png" width="22" height="22" align="absmiddle"> Claydol
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/411.png" width="22" height="22" align="absmiddle"> Bastiodon
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/561.png" width="22" height="22" align="absmiddle"> Sigilyph
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/697.png" width="22" height="22" align="absmiddle"> Tyrantrum
      </td>
    </tr>
    <tr>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/full-restore.png" width="18" height="18" align="absmiddle"> <b>Healer</b><br><code>cobblebase:healer</code></td>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/full-restore.png" width="18" height="18" align="absmiddle"> Passively restores party HP and revives fainted team members over time.</td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/113.png" width="22" height="22" align="absmiddle"> Chansey
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/242.png" width="22" height="22" align="absmiddle"> Blissey
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/468.png" width="22" height="22" align="absmiddle"> Togekiss
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/531.png" width="22" height="22" align="absmiddle"> Audino
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/716.png" width="22" height="22" align="absmiddle"> Xerneas
      </td>
    </tr>
    <tr>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/exp-share.png" width="18" height="18" align="absmiddle"> <b>Mentor</b><br><code>cobblebase:mentor</code></td>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/exp-share.png" width="18" height="18" align="absmiddle"> Channels continuous passive XP to all pastured Pokémon.</td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/65.png" width="22" height="22" align="absmiddle"> Alakazam
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/150.png" width="22" height="22" align="absmiddle"> Mewtwo
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/381.png" width="22" height="22" align="absmiddle"> Latios
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/481.png" width="22" height="22" align="absmiddle"> Mesprit
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/898.png" width="22" height="22" align="absmiddle"> Calyrex
      </td>
    </tr>
    <tr>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/muscle-band.png" width="18" height="18" align="absmiddle"> <b>Guard</b><br><code>cobblebase:guard</code></td>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/muscle-band.png" width="18" height="18" align="absmiddle"> Patrols base borders and repels wild aggressive spawns for combat XP and loot.</td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/212.png" width="22" height="22" align="absmiddle"> Scizor
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/448.png" width="22" height="22" align="absmiddle"> Lucario
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/475.png" width="22" height="22" align="absmiddle"> Gallade
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/727.png" width="22" height="22" align="absmiddle"> Incineroar
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/892.png" width="22" height="22" align="absmiddle"> Urshifu
      </td>
    </tr>
    <tr>
      <td><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/light-clay.png" width="18" height="18" align="absmiddle"> <b>Global Team Auras</b></td>
      <td>
        • <b>Speed II</b>: <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/carbos.png" width="16" height="16" align="absmiddle"><br>
        • <b>Strength I</b>: <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/protein.png" width="16" height="16" align="absmiddle"><br>
        • <b>Resistance I</b>: <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/iron.png" width="16" height="16" align="absmiddle"><br>
        • <b>Shiny Luck (1.4–3x)</b>: <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/shiny-charm.png" width="16" height="16" align="absmiddle">
      </td>
      <td>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/101.png" width="20" height="20" align="absmiddle"> Electrode
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/135.png" width="20" height="20" align="absmiddle"> Jolteon
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/291.png" width="20" height="20" align="absmiddle"> Ninjask
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/894.png" width="20" height="20" align="absmiddle"> Regieleki<br>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/68.png" width="20" height="20" align="absmiddle"> Machamp
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/289.png" width="20" height="20" align="absmiddle"> Slaking
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/384.png" width="20" height="20" align="absmiddle"> Rayquaza
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/798.png" width="20" height="20" align="absmiddle"> Kartana<br>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/213.png" width="20" height="20" align="absmiddle"> Shuckle
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/377.png" width="20" height="20" align="absmiddle"> Regirock
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/379.png" width="20" height="20" align="absmiddle"> Registeel<br>
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/151.png" width="20" height="20" align="absmiddle"> Mew
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/385.png" width="20" height="20" align="absmiddle"> Jirachi
        <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/493.png" width="20" height="20" align="absmiddle"> Arceus
      </td>
    </tr>
  </tbody>
</table>

---

<a id="proficiency-scaling-system"></a>
## <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/stardust.png" width="24" height="24" align="absmiddle"> Proficiency Scaling System (1–5 Stars)

Production cooldowns scale dynamically with a Pokémon's skill proficiency rating:

$$\text{Effective Cooldown Ticks} = \text{Base Cooldown Seconds} \times 20 \times \frac{6 - \text{Proficiency}}{3}$$

<table width="100%">
  <thead>
    <tr>
      <th width="25%" align="center">Proficiency Rating</th>
      <th width="20%" align="left">Rank Level</th>
      <th width="15%" align="center">Multiplier</th>
      <th width="40%" align="left">Production Efficiency & Output</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td align="center"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"></td>
      <td><b>Novice (1 Star)</b></td>
      <td align="center"><code>1.67x</code></td>
      <td>Takes 67% longer than base (<code>5/3</code>). Entry-level speed.</td>
    </tr>
    <tr>
      <td align="center"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"></td>
      <td><b>Apprentice (2 Stars)</b></td>
      <td align="center"><code>1.33x</code></td>
      <td>Takes 33% longer than base (<code>4/3</code>). Moderate speed.</td>
    </tr>
    <tr>
      <td align="center"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"></td>
      <td><b>Skilled (3 Stars)</b></td>
      <td align="center"><code>1.00x</code></td>
      <td><b>Standard Baseline</b> (<code>3/3</code>). Default configured speed.</td>
    </tr>
    <tr>
      <td align="center"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"></td>
      <td><b>Expert (4 Stars)</b></td>
      <td align="center"><code>0.67x</code></td>
      <td><b>33% Faster</b> (<code>2/3</code>). Unlocks elevated rare drop tables.</td>
    </tr>
    <tr>
      <td align="center"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16" align="absmiddle"></td>
      <td><b>Master (5 Stars)</b></td>
      <td align="center"><code>0.33x</code></td>
      <td><b>3x Faster Speed</b> (<code>1/3</code>). Maximum rare yields & jackpot chances.</td>
    </tr>
  </tbody>
</table>

---

<a id="controls--automation-setup"></a>
## <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/super-rod.png" width="24" height="24" align="absmiddle"> Controls & Automation Setup

<table width="100%">
  <thead>
    <tr>
      <th width="10%" align="center">Step</th>
      <th width="25%" align="left">Action</th>
      <th width="65%" align="left">Description</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td align="center"><b>1</b></td>
      <td><b>Place Pasture Block</b></td>
      <td>Craft and place the Virtual Pasture block anywhere in your base.</td>
    </tr>
    <tr>
      <td align="center"><b>2</b></td>
      <td><b>Tether Pokémon</b></td>
      <td><b>Right-Click</b> the block to open the pasture menu and tether your Pokémon team.</td>
    </tr>
    <tr>
      <td align="center"><b>3</b></td>
      <td><b>Select 3D Visual Mode</b></td>
      <td>Click the <b>Visual Mode</b> button in the GUI to cycle modes: <code>Cyber Wireframe</code> -> <code>Sci-Fi Hologram</code> -> <code>Ethereal Ghost</code> -> <code>Disabled</code>.</td>
    </tr>
    <tr>
      <td align="center"><b>4</b></td>
      <td><b>Assign Base Jobs</b></td>
      <td>Click <b>Cobblebase</b> in the GUI to choose specific jobs, or leave unassigned for automatic highest-proficiency execution.</td>
    </tr>
    <tr>
      <td align="center"><b>5</b></td>
      <td><b>Connect Hopper Automation</b></td>
      <td>Place a vanilla or modded <b>hopper directly underneath</b> the pasture block to extract all generated items into chests.</td>
    </tr>
    <tr>
      <td align="center"><b>6</b></td>
      <td><b>Open 27-Slot Internal Storage</b></td>
      <td><b>Shift + Right Click</b> the pasture block at any time to open the internal container.</td>
    </tr>
    <tr>
      <td align="center"><b>7</b></td>
      <td><b>Interactive Draggable HUD</b></td>
      <td>Click the <b><code>[⚙]</code></b> button to drag and reposition the Pasture Stats overlay and control buttons on your screen.</td>
    </tr>
  </tbody>
</table>

---

<a id="configuration-reference"></a>
## <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/machine-part.png" width="24" height="24" align="absmiddle"> Configuration Reference

### 1. General Loot & Ticking (`config/PastureLoot.json`)
```json
{
  "tick_per_minute": 60,
  "drop_chance": 0.1,
  "item_blacklist": [
    "minecraft:dirt",
    "minecraft:cobblestone"
  ],
  "flatten_item_quantity": false
}
```
* **`tick_per_minute`** *(int, default: `60`)*: Frequency of virtual pasture loot processing per minute.
* **`drop_chance`** *(float, default: `0.1`)*: Base drop probability evaluated on each tick cycle.
* **`item_blacklist`** *(string array)*: Registry IDs of items forbidden from generating.
* **`flatten_item_quantity`** *(boolean, default: `false`)*: When true, forces all item yields to a single count of 1.

### 2. HUD Positioning (`config/virtualloot_hud.json`)
```json
{
  "cobblebaseOffsetX": 356,
  "cobblebaseOffsetY": 109,
  "visualModeOffsetX": 332,
  "visualModeOffsetY": -10,
  "virtualLootOffsetX": 291,
  "virtualLootOffsetY": -10,
  "hudBtnOffsetX": 311,
  "hudBtnOffsetY": -10
}
```
* Custom screen offsets saved automatically when moving buttons with the in-game draggable HUD editor `[⚙]`.

---

<a id="compatibility-matrix"></a>
## <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/poke-ball.png" width="24" height="24" align="absmiddle"> Compatibility Matrix

<table width="100%">
  <thead>
    <tr>
      <th width="20%" align="left">Dependency / Mod</th>
      <th width="15%" align="left">Required Version</th>
      <th width="20%" align="left">Loader Platform</th>
      <th width="45%" align="left">Role / Function</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><b>Minecraft</b></td>
      <td><code>1.21.1</code></td>
      <td>Fabric / NeoForge</td>
      <td>Base Game Engine</td>
    </tr>
    <tr>
      <td><b>Cobblemon</b></td>
      <td><code>>=1.7.3</code></td>
      <td>Fabric / NeoForge</td>
      <td>Core Pokémon Framework</td>
    </tr>
    <tr>
      <td><b>Architectury API</b></td>
      <td><code>>=13.0.8</code></td>
      <td>Fabric / NeoForge</td>
      <td>Cross-Loader Runtime Abstraction</td>
    </tr>
    <tr>
      <td><b>Fabric API</b></td>
      <td><code>>=0.116.12</code></td>
      <td>Fabric</td>
      <td>Required for Fabric Loader</td>
    </tr>
    <tr>
      <td><b>NeoForge</b></td>
      <td><code>>=21.1.234</code></td>
      <td>NeoForge</td>
      <td>Required for NeoForge Loader</td>
    </tr>
    <tr>
      <td><b><a href="https://modrinth.com/mod/cobblebase">Cobblebase</a></b></td>
      <td><code>>=2.0.0</code></td>
      <td>Supported Addon</td>
      <td>All 43 Virtual Palworld jobs, auras & activity log</td>
    </tr>
    <tr>
      <td><b><a href="https://modrinth.com/mod/cobbreeding">Cobbreeding</a></b></td>
      <td><code>>=2.2.1</code></td>
      <td>Supported Addon</td>
      <td>Virtual egg breeding, timers & Mirror Herb moves</td>
    </tr>
    <tr>
      <td><b><a href="https://modrinth.com/mod/cobbleworkers">Cobbleworkers</a></b></td>
      <td><code>>=2.0.4</code></td>
      <td>Supported Addon</td>
      <td>Automated worker tasks (Pickup, Fishing, Diving, Archaeology)</td>
    </tr>
    <tr>
      <td><b><a href="https://modrinth.com/mod/cloth-config">Cloth Config API</a></b></td>
      <td><code>>=15.0.140</code></td>
      <td>Supported Addon</td>
      <td>In-game configuration screen helper</td>
    </tr>
    <tr>
      <td><b><a href="https://modrinth.com/mod/iris">Iris / Sodium</a></b></td>
      <td><code>>=1.7.0</code></td>
      <td>Supported Addon</td>
      <td>Shader and high-performance rendering engines</td>
    </tr>
  </tbody>
</table>

---

<a id="credits--attributions"></a>
## <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/exp-share.png" width="24" height="24" align="absmiddle"> Credits & Attributions

* **[LunazStudios](https://github.com/LunazStudios)** — Original creator of **[Cobblemon Virtual Loot](https://github.com/LunazStudios/VirtualLoot)**.
* **[notlown](https://github.com/notlown)** — Creator of **[Cobblebase](https://github.com/notlown/cobblebase)**.
* **[The Cobblemon Team](https://cobblemon.com/)** — Creators of Cobblemon and the pasture framework.
* **[ludichat](https://github.com/ludichat)** — Creator of **[Cobbreeding](https://modrinth.com/mod/cobbreeding)**.
* **[Accieo](https://github.com/Accieo)** — Creator of **[Cobbleworkers](https://modrinth.com/mod/cobbleworkers)**.
* **gxenzy & Contributors** — Extended edition compatibility bridge, 3D visual projection engine, zero-push physics, draggable HUD editor, and automated release workflows.

*Licensed under the [MIT License](LICENSE.txt) with portions adapted under [MPL-2.0](https://mozilla.org/MPL/2.0/).*
