<p align="center">
  <img src="logo.png" alt="Virtual Loot Extended Banner" width="100%">
</p>

<p align="center">
  <a href="https://minecraft.net"><img src="https://img.shields.io/badge/Minecraft-1.21.1-62B47A?style=for-the-badge&logo=minecraft&logoColor=white" alt="Minecraft 1.21.1"></a>
  <a href="https://cobblemon.com"><img src="https://img.shields.io/badge/Cobblemon-1.7.3+-E8532E?style=for-the-badge" alt="Cobblemon 1.7.3+"></a>
  <a href="https://fabricmc.net"><img src="https://img.shields.io/badge/Loader-Fabric%20%7C%20NeoForge-3A6EA5?style=for-the-badge" alt="Fabric / NeoForge"></a>
  <a href="https://adoptium.net"><img src="https://img.shields.io/badge/Java-21%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21"></a>
  <a href="LICENSE.txt"><img src="https://img.shields.io/badge/License-MIT%20%2F%20MPL--2.0-588157?style=for-the-badge" alt="License"></a>
</p>

---

## <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/town-map.png" width="24" height="24" align="center"> Overview

In standard Cobblemon pastures, placing multiple Pokémon in pasture blocks causes physical entities to wander the world. On multiplayer servers and large bases, this creates continuous entity ticking, AI navigation spikes, pathfinding overhead, and physical player collisions that rapidly degrade server TPS and client framerates.

**Virtual Loot: Extended** overhauls the Pasture Block into an automated **Virtual Base Automation & 3D Projection System**:

* **Zero Server Entity Ticking**: Pokémon exist virtually inside the pasture block entity. They work, harvest, mine, forage, breed, and provide party buffs in the background with **0 physical entities spawned** on the server.
* **Full Multi-Mod Ecosystem**: Seamlessly integrates **Cobblebase 2.0+** (42+ Palworld-style jobs), **Cobbreeding** (virtual egg incubation & Mirror Herb moves), and **Cobbleworkers** (field tasks).
* **Storage & Automation**: Every generated item, ore, crop, and egg is collected in a built-in **27-slot internal inventory** that can be extracted automatically using **hoppers underneath**.
* **Client-Side 3D Projections**: View tethered Pokémon in the world using client-rendered **Cyber Wireframe**, **Sci-Fi Hologram**, or **Ethereal Ghost** visual modes with **zero server performance cost**.

---

## <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="24" height="24" align="center"> Key Features

<table>
  <tr>
    <td width="50%" valign="top">
      <h4><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/thunder-stone.png" width="20" height="20" align="center"> Server Performance & Storage</h4>
      <ul>
        <li><b>0 Entity Server Overhead</b>: Supports 16+ Pokémon per block without server tick lag.</li>
        <li><b>27-Slot Storage</b>: Built-in container for all drops. Access via <b>Shift + Right Click</b>.</li>
        <li><b>Hopper Automation</b>: Place hoppers below the pasture for automatic item piping into chests.</li>
        <li><b>Activity Log Sync</b>: Completed jobs log directly to Cobblebase's in-game GUI.</li>
      </ul>
    </td>
    <td width="50%" valign="top">
      <h4><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/devon-scope.png" width="20" height="20" align="center"> 3D Visual Projections & Polish</h4>
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
      <h4><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/miracle-seed.png" width="20" height="20" align="center"> Cobblebase 2.0+ Automation</h4>
      <ul>
        <li><b>42+ Palworld Jobs</b>: Virtual execution of Producer, Finder, Harvester, Mining, and Guard tasks.</li>
        <li><b>12 Finder Subtypes</b>: Targeted foraging for stones, candies, held items, relics, and more.</li>
        <li><b>Proficiency Scaling</b>: Dynamic cooldowns scaled by 1–5 star ratings.</li>
        <li><b>Team Aura Buffs</b>: Passive speed, strength, resistance, healing, and shiny luck buffs.</li>
      </ul>
    </td>
    <td width="50%" valign="top">
      <h4><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/lucky-egg.png" width="20" height="20" align="center"> Ecosystem Compatibility</h4>
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

## <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/vs-seeker.png" width="24" height="24" align="center"> 3D Visual Projection Engine

Toggleable directly in the Pasture interface or PC menu:

| Visual Mode | Visual Style | In-Game Behavior & Rendering |
| :---: | :--- | :--- |
| **Mode 1** | **Cyber Wireframe** | • Electric cyan vector line lattice outlining model polygon edges.<br>• 100% see-through empty face interiors.<br>• Renders dynamic geometry including Charizard tail flames and wing membranes.<br>• High-performance buffered pipeline at **60–80+ FPS**.<br>• Walks firmly on ground terrain. |
| **Mode 2** | **Sci-Fi Hologram** | • Glowing electric cyan holographic energy projection.<br>• Real-time animated horizontal laser scanlines.<br>• Fullbright unshaded emissive lighting with zero dark shadows at night.<br>• Walks firmly on ground terrain. |
| **Mode 3** | **Ethereal Ghost** | • Translucent spirit model (40% Spectator opacity).<br>• Preserves natural Pokémon textures with a see-through spirit effect.<br>• Gentle soul flame particle mist.<br>• Hovers smoothly above the ground. |
| **Mode 0** | **Disabled** | Projections disabled for maximum client performance. |

---

## <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/miracle-seed.png" width="24" height="24" align="center"> Complete Job Catalog & Pokémon Assignments

### 1. Producer Job Map (`cobblebase:producer`)
Every Pokémon species listed below produces lore materials on configured cooldowns directly into pasture storage:

| Category | Example Pokémon (with Sprites) | Produced Item Output |
| :--- | :--- | :--- |
| **Wool & Textiles** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/831.png" width="28" height="28" align="center"> **Wooloo**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/832.png" width="28" height="28" align="center"> **Dubwool**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/179.png" width="28" height="28" align="center"> **Mareep**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/180.png" width="28" height="28" align="center"> **Flaaffy** | `minecraft:white_wool` (x1–x2) |
| **String & Silk** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/546.png" width="28" height="28" align="center"> **Cottonee**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/547.png" width="28" height="28" align="center"> **Whimsicott**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/167.png" width="28" height="28" align="center"> **Spinarak**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/595.png" width="28" height="28" align="center"> **Joltik**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/872.png" width="28" height="28" align="center"> **Snom** | `minecraft:string` (x1–x3) |
| **Dairy Milk** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/241.png" width="28" height="28" align="center"> **Miltank**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/673.png" width="28" height="28" align="center"> **Gogoat**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/672.png" width="28" height="28" align="center"> **Skiddo** | `cobblemon:moomoo_milk` (x1–x2) |
| **Poultry Eggs** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/113.png" width="28" height="28" align="center"> **Chansey**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/242.png" width="28" height="28" align="center"> **Blissey**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/440.png" width="28" height="28" align="center"> **Happiny**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/255.png" width="28" height="28" align="center"> **Torchic** | `minecraft:egg` (x1–x2) |
| **Honey & Comb** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/415.png" width="28" height="28" align="center"> **Combee**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/416.png" width="28" height="28" align="center"> **Vespiquen**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/743.png" width="28" height="28" align="center"> **Ribombee** | `minecraft:honeycomb` & `honey_bottle` |
| **Precious Metals** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/52.png" width="28" height="28" align="center"> **Meowth**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/53.png" width="28" height="28" align="center"> **Persian**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1000.png" width="28" height="28" align="center"> **Gholdengo**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/863.png" width="28" height="28" align="center"> **Perrserker** | `minecraft:gold_nugget` & `iron_nugget` |
| **Gems & Crystals** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/703.png" width="28" height="28" align="center"> **Carbink**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/719.png" width="28" height="28" align="center"> **Diancie**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/302.png" width="28" height="28" align="center"> **Sableye** | `minecraft:diamond` & `amethyst_shard` |
| **Orchard Fruits** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/357.png" width="28" height="28" align="center"> **Tropius**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/840.png" width="28" height="28" align="center"> **Applin**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/761.png" width="28" height="28" align="center"> **Bounsweet**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/420.png" width="28" height="28" align="center"> **Cherubi** | `minecraft:apple` & `sweet_berries` |
| **Slime & Magma** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/704.png" width="28" height="28" align="center"> **Goomy**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/706.png" width="28" height="28" align="center"> **Goodra**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/218.png" width="28" height="28" align="center"> **Slugma**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/219.png" width="28" height="28" align="center"> **Magcargo** | `minecraft:slime_ball` & `magma_cream` |
| **Fuel & Juice** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/324.png" width="28" height="28" align="center"> **Torkoal**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/839.png" width="28" height="28" align="center"> **Coalossal**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/213.png" width="28" height="28" align="center"> **Shuckle** | `minecraft:charcoal` & `berry_juice` |

---

### 2. 12 Specialized Finder Subtypes (`cobblebase:finder_*`)
Finders forage for specialized loot tables based on their skill focus:

| Finder Subtype | Role | Example Pokémon (with Sprites) | Focus Loot Category |
| :--- | :--- | :--- | :--- |
| **Alchemist** (`finder_evo`) | Evolution Materials | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/65.png" width="28" height="28" align="center"> **Alakazam**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/429.png" width="28" height="28" align="center"> **Mismagius** | Fire/Water/Thunder Stones, Linking Cords, Patches |
| **Pharmacist** (`finder_hea`) | Medical Supplies | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/531.png" width="28" height="28" align="center"> **Audino**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/683.png" width="28" height="28" align="center"> **Aromatisse** | Max Potions, Full Restores, Revives, Sacred Ash |
| **Architect** (`finder_bui`) | Construction | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/137.png" width="28" height="28" align="center"> **Porygon**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/557.png" width="28" height="28" align="center"> **Dwebble** | Prismarine, Sea Lanterns, Crying Obsidian, Quartz |
| **Excavator** (`finder_ore`) | Minerals | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/444.png" width="28" height="28" align="center"> **Gabite**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/529.png" width="28" height="28" align="center"> **Drilbur** | Raw Ores, Diamonds, Emeralds, Ancient Debris |
| **Botanist** (`finder_see`) | Agriculture | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/407.png" width="28" height="28" align="center"> **Roserade**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/182.png" width="28" height="28" align="center"> **Bellossom** | Apricorn Seeds, Mint Seeds, Fertilizers, Mulch |
| **Collector** (`finder_bal`) | Poké Balls | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/820.png" width="28" height="28" align="center"> **Greedent**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/263.png" width="28" height="28" align="center"> **Zigzagoon** | Ultra Balls, Apricorn Balls, Cherish Balls, Master Balls |
| **Scholar** (`finder_exp`) | Training | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/199.png" width="28" height="28" align="center"> **Slowking**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/826.png" width="28" height="28" align="center"> **Orbeetle** | Exp Candies XS–XL, Rare Candies |
| **Chef / Forager** (`finder_food`) | Food Supplies | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/685.png" width="28" height="28" align="center"> **Slurpuff**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/869.png" width="28" height="28" align="center"> **Alcremie** | Ponigiri, Lava Cookies, Rare Berries, Golden Apples |
| **Trainer** (`finder_stat`) | EV Vitamins | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/68.png" width="28" height="28" align="center"> **Machamp**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/297.png" width="28" height="28" align="center"> **Hariyama** | HP Up, Protein, Iron, Carbos, Calcium, Zinc |
| **Armorer** (`finder_held`) | Battle Gear | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/448.png" width="28" height="28" align="center"> **Lucario**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/625.png" width="28" height="28" align="center"> **Bisharp** | Choice Items, Life Orb, Focus Sash, Leftovers |
| **Prospector** (`finder_treasure`) | Valuables | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1000.png" width="28" height="28" align="center"> **Gholdengo**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/302.png" width="28" height="28" align="center"> **Sableye** | Relic Coins, Gold Ingots, Netherite Scrap |
| **Smith** (`finder_smith`) | Upgrades | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/959.png" width="28" height="28" align="center"> **Tinkaton**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/601.png" width="28" height="28" align="center"> **Klinklang** | Armor Trims, Netherite Upgrade Templates |

---

### 3. Gathering, Harvesting, Mining & Combat Support
* **Harvester (`cobblebase:harvester`)**: Gathers vanilla crops, apricorns, berries, and mints. (*Venusaur, Scizor, Celebi, Meganium, Torterra*).
* **Mining (`cobblebase:mining`)**: Digs for regional ores (Coal, Iron, Copper, Gold, Redstone, Amethyst) scaled by proficiency. (*Excadrill, Steelix, Roggenrola, Ting-Lu, Aggron*).
* **Fishing (`cobblebase:fishing`)**: Catches fish, treasures, and aquatic loot directly into pasture storage. (*Gyarados, Blastoise, Kyogre, Kingdra, Wishiwashi*).
* **Archeologist (`cobblebase:archeologist`)**: Excavates ancient relics, fossils, and pottery sherds. (*Nidoking, Nidoqueen, Claydol, Sigilyph*).
* **Healer (`cobblebase:healer`)**: Passively heals party Pokémon and revives fainted team members. (*Blissey, Chansey, Audino, Xerneas*).
* **Mentor (`cobblebase:mentor`)**: Grants passive XP over time to all Pokémon in the pasture. (*Mewtwo, Alakazam, Latios, Mesprit*).
* **Guard (`cobblebase:guard`)**: Patrols base borders and repels wild aggressive spawns for XP and loot. (*Gallade, Incineroar, Scizor, Lucario*).
* **Team Aura Buffs**: Speed II (*Ninjask, Regieleki*), Strength I (*Machamp, Kartana*), Resistance I (*Regirock, Steelix*), Lucky Charm (*Arceus, Jirachi, Mew, Victini*).

---

## <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/stardust.png" width="24" height="24" align="center"> Proficiency Multiplier System (1–5 Stars)

Production cooldowns scale dynamically with a Pokémon's skill proficiency rating:

$$\text{Effective Cooldown Ticks} = \text{Base Cooldown Seconds} \times 20 \times \frac{6 - \text{Proficiency}}{3}$$

| Proficiency Stars | Skill Level | Cooldown Multiplier | Production Efficiency |
| :---: | :--- | :---: | :--- |
| <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16"> | **Novice (1 Star)** | `1.67x` | Takes 67% longer than base (`5/3`). Entry-level speed. |
| <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16"> | **Apprentice (2 Stars)** | `1.33x` | Takes 33% longer than base (`4/3`). Moderate speed. |
| <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16"> | **Skilled (3 Stars)** | `1.00x` | **Standard Baseline** (`3/3`). Configured base speed. |
| <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16"> | **Expert (4 Stars)** | `0.67x` | **33% Faster** (`2/3`). Unlocks elevated loot tables. |
| <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16"><img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/star-piece.png" width="16" height="16"> | **Master (5 Stars)** | `0.33x` | **3x Faster Speed** (`1/3`). Maximum rare item yields. |

---

## <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/super-rod.png" width="24" height="24" align="center"> Quick Start & Automation Setup

1. **Place Pasture**: Craft and place the Virtual Pasture block in your base.
2. **Assign Pokémon**: Right-click the block to tether your Pokémon.
3. **Set Visual Mode**: Click the **Visual Mode** button in the Pasture/PC GUI to cycle modes (Wireframe, Hologram, Ghost, Off).
4. **Assign Jobs**: Click **Cobblebase** in the GUI to assign specific jobs or allow automatic task selection.
5. **Connect Hoppers**: Place a vanilla or modded **hopper directly underneath** the block to route items into chests and sorting networks.
6. **Open Storage**: **Shift + Right Click** the pasture block at any time to open its 27-slot inventory.
7. **Adjust HUD**: Click **`[⚙]`** to drag the Pasture Stats overlay and control buttons to custom screen positions.

---

## <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/poke-ball.png" width="24" height="24" align="center"> Dependencies & Compatibility

| Dependency | Required Version | Platform | Role |
| :--- | :--- | :--- | :--- |
| **Minecraft** | `1.21.1` | Fabric / NeoForge | Base Game |
| **Cobblemon** | `>=1.7.3` | Fabric / NeoForge | Core Pokémon Framework |
| **Architectury API** | `>=13.0.8` | Fabric / NeoForge | Cross-Loader Runtime Abstraction |
| **Fabric API** | `>=0.116.12` | Fabric | Required for Fabric Loader |
| **NeoForge** | `>=21.1.234` | NeoForge | Required for NeoForge Loader |
| **[Cobblebase](https://modrinth.com/mod/cobblebase)** | `>=2.0.0` | Supported Addon | Virtual base jobs, auras, and activity log |
| **[Cobbreeding](https://modrinth.com/mod/cobbreeding)** | `>=2.2.1` | Supported Addon | Virtual egg breeding and genetics |
| **[Cobbleworkers](https://modrinth.com/mod/cobbleworkers)** | `>=2.0.4` | Supported Addon | Field worker loot tasks |
| **[Cloth Config API](https://modrinth.com/mod/cloth-config)** | `>=15.0.140` | Supported Addon | In-game configuration screen helper |
| **[Iris / Sodium](https://modrinth.com/mod/iris)** | `>=1.7.0` | Supported Addon | Shader and performance rendering compatibility |

---

## <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/exp-share.png" width="24" height="24" align="center"> Open Source Credits & Attributions

* **[LunazStudios](https://github.com/LunazStudios)** — Original creator of **[Cobblemon Virtual Loot](https://github.com/LunazStudios/VirtualLoot)**.
* **[notlown](https://github.com/notlown)** — Creator of **[Cobblebase](https://github.com/notlown/cobblebase)**.
* **[The Cobblemon Team](https://cobblemon.com/)** — Creators of Cobblemon and the pasture framework.
* **[ludichat](https://github.com/ludichat)** — Creator of **[Cobbreeding](https://modrinth.com/mod/cobbreeding)**.
* **[Accieo](https://github.com/Accieo)** — Creator of **[Cobbleworkers](https://modrinth.com/mod/cobbleworkers)**.
* **[The SVCMC Team](https://github.com/the-svcmc-team)** — Creators of **[svc-holograms](https://github.com/the-svcmc-team/svc-holograms)** for the holographic shader pipeline.
* **gxenzy & Contributors** — Extended edition compatibility bridge, 3D visual projection engine, zero-push physics, draggable HUD editor, and automated release workflows.

*Licensed under the [MIT License](LICENSE.txt) with portions adapted under [MPL-2.0](https://mozilla.org/MPL/2.0/).*
