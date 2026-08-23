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
* **Full Multi-Mod Ecosystem**: Seamlessly integrates **Cobblebase 2.0+** (42+ Palworld-style jobs across all 1,000+ Pokémon species), **Cobbreeding** (virtual egg incubation & Mirror Herb moves), and **Cobbleworkers** (field tasks).
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
        <li><b>42+ Palworld Jobs</b>: Full coverage for all 1,367+ species across Gens 1–9.</li>
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

## <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/miracle-seed.png" width="24" height="24" align="center"> 1,000+ Species Database & Job Classification Engine

Cobblebase automatically maps every single one of the **1,367+ Pokémon species across Generations 1–9** into specialized work archetypes using elemental typings, biology, lore, natural abilities, movepools, and evolutionary stage.

If a Pokémon is tethered without an explicit job assigned, the system evaluates `SpeciesSkillRegistry` and automatically executes their **highest-proficiency skill**.

```
                           [1,367+ Pokémon Species Database]
                                          │
            ┌─────────────────────────────┼─────────────────────────────┐
            ▼                             ▼                             ▼
   [Direct Producers]            [12 Finder Subtypes]        [Gathering & Field Tasks]
   • Wool/Silk/Cotton            • Alchemist (Evo Items)     • Harvester (Crops/Mints)
   • Dairy Milk & Eggs           • Pharmacist (Medicine)     • Mining (Ores & Gems)
   • Honey & Comb                • Excavator (Deep Ores)     • Fishing (Marine Loot)
   • Gold/Iron Nuggets           • Botanist (Seeds/Apricorn) • Archeologist (Fossils)
   • Gems & Crystals             • Collector (Poké Balls)    • Guard (Base Defense)
   • Fruit & Charcoal            • Scholar (Exp Candies)     • Healer (Party HP Restore)
   • Slime & Magma               • Smith (Upgrade Templates) • Mentor (Passive Pasture Exp)
```

---

### 1. Direct Lore Producer Matrix (`cobblebase:producer`)
Direct producers generate authentic materials based on species biology:

| Production Category | Species Archetypes & Eligible Pokémon Examples | Generated Items |
| :--- | :--- | :--- |
| **Wool, Cotton & Fiber** | **All Ovine & Fluffy Species**: Wooloo, Dubwool, Mareep, Flaaffy, Ampharos, Cottonee, Whimsicott, Eldegoss, Altaria, Swablu | `minecraft:white_wool` (x1–x2) |
| **Silk & Webs** | **All Arachnid & Lepidopteran Species**: Spinarak, Ariados, Joltik, Galvantula, Sewaddle, Swadloon, Leavanny, Snom, Frosmoth, Tarountula, Spidops, Dewpider, Araquanid, Wurmple line, Caterpie line, Weedle line | `minecraft:string` (x1–x3) |
| **Dairy Milk** | **All Bovine & Caprine Species**: Miltank, Skiddo, Gogoat, Tauros, Bouffalant | `cobblemon:moomoo_milk` (x1–x2) |
| **Poultry & Eggs** | **All Avian, Reptilian & Oviparous Species**: Chansey, Blissey, Happiny, Exeggcute, Torchic, Combusken, Blaziken, Fletchling, Talonflame, Pidgey line, Spearow line, Rowlet line, Wattrel line, Kilowattrel | `minecraft:egg` (x1–x2) |
| **Honey & Comb** | **All Apian & Nectar Collectors**: Combee, Vespiquen, Cutiefly, Ribombee, Beedrill | `minecraft:honeycomb` & `honey_bottle` |
| **Precious Nuggets** | **All Feline, Thief & Coin Bearers**: Meowth, Persian, Alolan Meowth, Galarian Meowth, Perrserker, Gimmighoul, Gholdengo | `minecraft:gold_nugget` & `iron_nugget` |
| **Gems & Crystals** | **All Geode, Jewel & Crystalline Species**: Carbink, Diancie, Sableye, Roggenrola, Boldore, Gigalith, Nacli, Naclstack, Garganacl | `minecraft:diamond` & `amethyst_shard` |
| **Orchard Fruits** | **All Arboreal & Fruit-Bearing Species**: Tropius, Applin, Flapple, Appletun, Dipplin, Hydrapple, Bounsweet, Steenee, Tsareena, Cherubi, Cherrim, Smoliv, Dolliv, Arboliva | `minecraft:apple` & `sweet_berries` |
| **Slime & Viscosity** | **All Amorphous & Gastropod Species**: Goomy, Sliggoo, Goodra, Hisuian Goodra, Gulpin, Swalot, Grimer, Muk, Alolan Muk, Shellos, Gastrodon | `minecraft:slime_ball` & `magma_cream` |
| **Fuel & Volcanics** | **All Thermal, Furnace & Magma Species**: Torkoal, Rolycoly, Carkol, Coalossal, Slugma, Magcargo, Magby, Magmar, Magmortar, Camerupt, Numel | `minecraft:charcoal` & `blaze_powder` |
| **Ocean Pearls** | **All Bivalve, Clam & Mollusk Species**: Shellder, Cloyster, Clamperl, Huntail, Gorebyss, Binacle, Barbaracle, Dwebble, Crustle | `minecraft:pearl` & `prismarine_shard` |
| **Fermented Nectar** | **All Berry-Juice & Beverage Hosts**: Shuckle, Polteageist, Sinistea, Poltchageist, Sinistcha | `cobblemon:berry_juice` (x1) |

---

### 2. The 12 Specialized Finder Subtypes (`cobblebase:finder_*`)
Finder jobs allow hundreds of Pokémon to forage for specialized loot tables:

| Finder Subtype | Eligible Species Archetypes (All Generations) | Primary Target Loot |
| :--- | :--- | :--- |
| **Alchemist** (`finder_evo`) | **Psychic, Fairy & Mystical**: Alakazam, Mismagius, Delphox, Gardevoir, Hatterene, Reuniclus, Sigilyph, Chimecho, Xatu, Gothitelle, Slowking, Claydol, Beheeyem | Evolution Stones, Linking Cords, Ability Patches, King's Rocks |
| **Pharmacist** (`finder_hea`) | **Healers & Empaths**: Audino, Chansey, Blissey, Aromatisse, Meganium, Florges, Togekiss, Alomomola, Indeedee, Comfey, Eldegoss, Swanna | Max Potions, Full Restores, Revives, Sacred Ash, Full Heals |
| **Architect** (`finder_bui`) | **Synthetic, Builder & Matrix**: Porygon, Porygon2, Porygon-Z, Dwebble, Crustle, Conkeldurr, Timburr, Gurdurr, Bronzong, Magneton, Magnezone, Rotom | Prismarine, Sea Lanterns, Crying Obsidian, Quartz, Smooth Stone |
| **Excavator** (`finder_ore`) | **Ground, Steel & Burrowers**: Drilbur, Excadrill, Gabite, Garchomp, Diglett, Dugtrio, Sandshrew, Sandslash, Onix, Steelix, Donphan, Great Tusk, Ting-Lu, Hippowdon | Raw Iron/Copper/Gold, Diamonds, Emeralds, Ancient Debris |
| **Botanist** (`finder_see`) | **Grass, Flora & Forest**: Roserade, Bellossom, Vileplume, Tsareena, Sceptile, Venusaur, Lilligant, Leafeon, Decidueye, Meowscarada, Shaymin, Celebi, Trevenant | Apricorn Seeds, Mint Seeds, Fertilizers, Organic Mulch |
| **Collector** (`finder_bal`) | **Foragers & Rodents (Pickup Ability)**: Zigzagoon, Linoone, Greedent, Skwovet, Pachirisu, Emolga, Dedenne, Morpeko, Pawmi, Pawmot, Ambipom, Aipom, Cinccino | Ultra Balls, Apricorn Balls, Cherish Balls, Master Balls |
| **Scholar** (`finder_exp`) | **Intellectuals & Ancient Sages**: Slowking, Orbeetle, Uxie, Mesprit, Azelf, Mewtwo, Metagross, Noctowl, Hypno, Oranguru, Rabsca | Exp Candies XS–XL, Rare Candies |
| **Chef / Forager** (`finder_food`) | **Culinary, Sweet & Gluttonous**: Slurpuff, Alcremie, Dachsbun, Fidough, Smeargle, Mr. Mime, Mime Jr., Morpeko, Snorlax, Munchlax, Cramorant | Ponigiri, Lava Cookies, Rare Berries, Golden Apples |
| **Trainer** (`finder_stat`) | **Martial Artists & Athletes**: Machop line, Hariyama, Makuhita, Hitmonlee, Hitmonchan, Hitmontop, Throh, Sawk, Crabrawler, Passimian, Annihilape, Urshifu | HP Up, Protein, Iron, Carbos, Calcium, Zinc |
| **Armorer** (`finder_held`) | **Knights, Blade & Shield Bearers**: Lucario, Bisharp, Kingambit, Ceruledge, Armarouge, Gallade, Honedge line, Sirfetch'd, Corviknight, Escavalier, Skarmory | Choice Items, Life Orb, Focus Sash, Leftovers, Assault Vest |
| **Prospector** (`finder_treasure`) | **Gold, Gem & Relic Hoarders**: Gholdengo, Gimmighoul, Sableye, Murkrow, Honchkrow, Thievul, Nickit, Weavile, Sneasel, Perrserker, Runerigus, Cofagrigus | Relic Coins, Gold Ingots, Netherite Scrap, Emeralds |
| **Smith** (`finder_smith`) | **Hammer, Forge & Metalworkers**: Tinkatink, Tinkatuff, Tinkaton, Klinklang, Heatran, Carkol, Forretress, Orthworm, Revavroom, Melmetal | Armor Trims, Netherite Upgrade Templates, Tool Upgrades |

---

### 3. Gathering, Harvesting, Mining & Team Aura Buffs
* **Harvester (`cobblebase:harvester`)**: 250+ Grass & Bug species gather crops, apricorns, berries, and mints. (*Venusaur, Scizor, Celebi, Meganium, Torterra, Serperior, Chesnaught, Rillaboom*).
* **Mining (`cobblebase:mining`)**: 200+ Rock, Ground & Steel species dig for regional ores scaled by proficiency. (*Excadrill, Steelix, Roggenrola, Ting-Lu, Aggron, Tyranitar, Gigalith*).
* **Fishing (`cobblebase:fishing`)**: 180+ Water species catch fish, treasures, and marine loot. (*Gyarados, Blastoise, Kyogre, Kingdra, Wishiwashi, Dondozo, Wailord*).
* **Archeologist (`cobblebase:archeologist`)**: 120+ Fossil & Ancient species excavate relics, fossils, and sherds. (*Nidoking, Nidoqueen, Claydol, Sigilyph, Aerodactyl, Tyrantrum, Bastiodon*).
* **Healer (`cobblebase:healer`)**: Passively restores party HP and revives fainted members. (*Blissey, Chansey, Audino, Xerneas, Togekiss, Alomomola*).
* **Mentor (`cobblebase:mentor`)**: Channels continuous passive XP to all pastured Pokémon. (*Mewtwo, Alakazam, Latios, Mesprit, Uxie, Calyrex*).
* **Guard (`cobblebase:guard`)**: Patrols base perimeters and repels aggressive wild spawns. (*Gallade, Incineroar, Scizor, Lucario, Urshifu, Zacian, Zamazenta*).
* **Global Team Auras**:
  * **Speed II**: Electric & Agile species (*Ninjask, Regieleki, Jolteon, Deoxys-Speed, Electrode*).
  * **Strength I**: Apex Physical Attackers (*Machamp, Kartana, Rayquaza, Haxorus, Slaking*).
  * **Resistance I**: Fortress Defense species (*Regirock, Steelix, Bastiodon, Shuckle, Ting-Lu*).
  * **Shiny Charm Luck (1.4x–3.0x)**: Divine & Mythical species (*Arceus, Jirachi, Mew, Celebi, Victini*).

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
