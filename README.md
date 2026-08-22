<p align="center">
    <img src="logo.png" alt="Virtual Loot Extended" width="100%">
</p>

<p align="center">
    <img src="https://img.shields.io/badge/Minecraft-1.21.1-62B47A?style=for-the-badge&logo=minecraft" alt="Minecraft 1.21.1">
    <img src="https://img.shields.io/badge/Cobblemon-1.7.3+-E8532E?style=for-the-badge" alt="Cobblemon 1.7.3">
    <img src="https://img.shields.io/badge/Platform-Fabric%20%7C%20NeoForge-blue?style=for-the-badge" alt="Fabric & NeoForge">
    <img src="https://img.shields.io/badge/Environment-Client%20%26%20Server-purple?style=for-the-badge" alt="Client & Server">
    <img src="https://img.shields.io/badge/Edition-Extended-blueviolet?style=for-the-badge" alt="Extended Edition">
    <img src="https://img.shields.io/badge/License-MIT%20%2F%20MPL--2.0-green?style=for-the-badge" alt="License">
</p>

---

## 📖 Overview

In traditional Cobblemon pastures, placing dozens of Pokémon causes severe server tick rate drops (TPS degradation) and client FPS stuttering due to 3D entity rendering, continuous physics collision, and pathfinding calculations. Furthermore, item drops scatter randomly across the terrain, creating world clutter.

**Virtual Loot: Extended** solves this completely by transforming the Pasture Block into an intelligent **Virtual Base Management System**. 

Instead of spawning physical entities into the world, your Pokémon are held virtually inside the **Virtual Pasture**. The mod executes **Cobblebase's complete Palworld-style job system** (over 42 unique jobs across 1,367 Pokémon species), **Cobbreeding's egg generation**, and **Cobbleworkers' automated looting** 100% virtually in the background!

All produce, mined minerals, harvested crops, rare treasures, and eggs are neatly collected inside the block's internal **27-slot storage inventory** and can be automatically extracted using **hoppers underneath** directly into automated sorting and storage networks.

---

## 🌟 Key Features

* **⚡ Zero Entity Lag:** Manage up to 16+ Pokémon per pasture block with **0 physical entities spawned**, completely eliminating pasture entity tick lag and pathfinding spikes.
* **🏗️ Full Cobblebase 2.0+ Virtual Jobs:** All 42+ Cobblebase jobs (Producer, 12 Finder subtypes, Mining, Harvester, Guard, Support Healers/Mentors, and Auras) run virtually on proficiency-scaled cooldowns.
* **📦 27-Slot Internal Storage:** All generated produce, ores, crops, and drops are deposited inside the block's internal chest inventory. Access anytime with **Shift + Right Click**.
* **🔄 Automated Hopper Extraction:** Place vanilla or modded hoppers directly beneath the Virtual Pasture block to automatically drain all collected items into chests, barrels, or sorting systems.
* **📊 Cobblebase Log Sync:** Every virtual job completion is recorded in Cobblebase's activity log system, making all production history viewable in-game through the Cobblebase GUI.
* **🥚 Cobbreeding Integration:** Supports virtual egg generation, breeding toggles, timers, and inheritance without physical entity breeding loops.
* **⛏️ Cobbleworkers Integration:** Supports automated worker jobs (Pickup, Fishing, Diving, Archaeology).
* **💤 Relaxing / Idle Drops:** Pokémon set to Relax / Idle automatically generate their standard Cobblemon species defeat drop tables.
* **🌐 Multiplayer & Singleplayer Ready:** Full owner-permission locking, packet synchronization, and singleplayer/dedicated server compatibility.

---

## 📌 Environment & Metadata Specifications

| Attribute | Specification |
| :--- | :--- |
| **Mod Name** | **Virtual Loot: Extended** (`virtualloot`) |
| **Supported Loaders** | **Fabric** (>=0.19.3), **NeoForge** (>=21.1.234) |
| **Target Minecraft** | **1.21.1** |
| **Target Cobblemon** | **1.7.3+** (also compatible with 1.7.0+) |
| **Java Runtime** | **Java 21 (JDK 21+)** |
| **Environment** | 💻 **Client & Server (Required on both sides)** |
| **Singleplayer Support** | ✅ Fully supported out-of-the-box (integrated server + client). |
| **Dedicated Server Support** | ✅ Fully supported. Install the `.jar` on both the server and client modpacks. |

---

## 📦 Dependencies & Compatibility Matrix

### 🟢 Required Dependencies
| Dependency | Version | Loader | Description |
| :--- | :--- | :--- | :--- |
| **Minecraft** | `1.21.1` | Both | Base Game |
| **Cobblemon** | `>=1.7.3` | Both | Core Pokémon Framework |
| **Architectury API** | `>=13.0.8` | Both | Cross-loader runtime abstraction |
| **Fabric API** | `>=0.116.12` | Fabric | Required for Fabric Loader |
| **NeoForge** | `>=21.1.234` | NeoForge | Required for NeoForge Loader |

### 🌟 Supported Integrations & Recommended Addons
| Mod | Recommended Version | Role & Features in Extended Edition |
| :--- | :--- | :--- |
| **[Cobblebase](https://modrinth.com/mod/cobblebase)** | `>=2.0.0` | **Palworld-Style Base Jobs:** Executes all 42 jobs (Producer, Finder, Harvester, Mining, Auras) virtually inside the pasture. |
| **[Cobbreeding](https://modrinth.com/mod/cobbreeding)** | `>=2.2.1` | **Virtual Egg Breeding:** Generates eggs, ivs, egg moves, and shiny rolls directly into pasture storage without entity mating loops. |
| **[Cobbleworkers](https://modrinth.com/mod/cobbleworkers)** | `>=2.0.4` | **Automated Worker Jobs:** Pickup, Fishing, Diving, and Archaeology virtual job processing. |
| **[Cloth Config API](https://modrinth.com/mod/cloth-config)** | `>=15.0.140` | Required config screen helper used by Cobblebase & VirtualLoot. |
| **[Xaero's Minimap](https://modrinth.com/mod/xaeros-minimap)** | `>=24.7.0` | Integrates with Cobblebase base waypoint tracking. |

---

## 🌾 Verified Job Mechanics & Pokémon Sprite Catalog

Cobblebase defines exact, hand-crafted produce and drop data for **1,367 Pokémon species**. In Virtual Loot: Extended, these jobs operate seamlessly inside the Virtual Pasture without entity lag:

### 1. Producer Output Map
| Category | Sprite & Pokémon Species | Item Output |
| :--- | :--- | :--- |
| **Wool & Textiles** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/831.png" width="30" height="30" align="center"> **Wooloo**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/179.png" width="30" height="30" align="center"> **Mareep**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/832.png" width="30" height="30" align="center"> **Dubwool**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/180.png" width="30" height="30" align="center"> **Flaaffy** | `minecraft:white_wool` (x1–x2) |
| **String & Silk** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/546.png" width="30" height="30" align="center"> **Cottonee**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/167.png" width="30" height="30" align="center"> **Spinarak**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/595.png" width="30" height="30" align="center"> **Joltik**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/872.png" width="30" height="30" align="center"> **Snom** | `minecraft:string` (x1–x3) |
| **Dairy & Milk** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/241.png" width="30" height="30" align="center"> **Miltank**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/673.png" width="30" height="30" align="center"> **Gogoat**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/672.png" width="30" height="30" align="center"> **Skiddo** | `cobblemon:moomoo_milk` (x1–x2) |
| **Poultry Eggs** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/113.png" width="30" height="30" align="center"> **Chansey**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/242.png" width="30" height="30" align="center"> **Blissey**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/255.png" width="30" height="30" align="center"> **Torchic** | `minecraft:egg` (x1–x2) |
| **Honey & Nectar** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/415.png" width="30" height="30" align="center"> **Combee**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/416.png" width="30" height="30" align="center"> **Vespiquen** | `minecraft:honeycomb` (x1–x2) |
| **Bottled Honey** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/743.png" width="30" height="30" align="center"> **Ribombee**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/742.png" width="30" height="30" align="center"> **Cutiefly** | `minecraft:honey_bottle` (x1) |
| **Gold Nuggets** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/52.png" width="30" height="30" align="center"> **Meowth**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1000.png" width="30" height="30" align="center"> **Gholdengo**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/999.png" width="30" height="30" align="center"> **Gimmighoul** | `minecraft:gold_nugget` (x1–x3) |
| **Iron Nuggets** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/863.png" width="30" height="30" align="center"> **Perrserker**, **Galarian Meowth** | `minecraft:iron_nugget` (x1) |
| **Diamonds** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/703.png" width="30" height="30" align="center"> **Carbink**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/719.png" width="30" height="30" align="center"> **Diancie** | `minecraft:diamond` (x1) |
| **Amethyst Gems** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/302.png" width="30" height="30" align="center"> **Sableye** | `minecraft:amethyst_shard` (x1) |
| **Ocean Shards** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/366.png" width="30" height="30" align="center"> **Clamperl** | `minecraft:prismarine_shard` (x2) |
| **Orchard Apples** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/357.png" width="30" height="30" align="center"> **Tropius**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/840.png" width="30" height="30" align="center"> **Applin**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/841.png" width="30" height="30" align="center"> **Flapple** | `minecraft:apple` (x1–x2) |
| **Sweet Berries** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/761.png" width="30" height="30" align="center"> **Bounsweet**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/762.png" width="30" height="30" align="center"> **Steenee**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/420.png" width="30" height="30" align="center"> **Cherubi** | `minecraft:sweet_berries` (x1–x3) |
| **Cookies** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/225.png" width="30" height="30" align="center"> **Delibird** | `minecraft:cookie` (x2) |
| **Slime Balls** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/704.png" width="30" height="30" align="center"> **Goomy**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/705.png" width="30" height="30" align="center"> **Sliggoo**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/706.png" width="30" height="30" align="center"> **Goodra** | `minecraft:slime_ball` (x1–x3) |
| **Magma Cream** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/218.png" width="30" height="30" align="center"> **Slugma**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/219.png" width="30" height="30" align="center"> **Magcargo** | `minecraft:magma_cream` (x1–x2) |
| **Charcoal & Fuel** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/324.png" width="30" height="30" align="center"> **Torkoal**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/839.png" width="30" height="30" align="center"> **Coalossal** | `minecraft:charcoal` (x2–x3) |
| **Pearls** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/90.png" width="30" height="30" align="center"> **Shellder**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/91.png" width="30" height="30" align="center"> **Cloyster** | `minecraft:pearl` (x1) |

---

### 2. 12 Specialized Finder Subtypes
| Subtype | Icon & Representative Pokémon | Focus Loot Table | Example Generated Items |
| :--- | :--- | :--- | :--- |
| **Alchemist** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/65.png" width="30" height="30" align="center"> **Alakazam**, **Mismagius** | Evolution Items | Fire/Thunder/Water/Leaf Stones, Linking Cords, Ability Patches |
| **Pharmacist** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/531.png" width="30" height="30" align="center"> **Audino**, **Aromatisse** | Healing Supplies | Max Potions, Full Heals, Revives, Full Restores, Sacred Ash |
| **Architect** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/137.png" width="30" height="30" align="center"> **Porygon**, **Dwebble** | Building Blocks | Prismarine, Sea Lanterns, Crying Obsidian, Quartz |
| **Excavator** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/444.png" width="30" height="30" align="center"> **Gabite**, **Drilbur** | Deep Ores & Minerals | Raw Iron/Gold/Copper, Diamonds, Emeralds, Ancient Debris |
| **Botanist** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/407.png" width="30" height="30" align="center"> **Roserade**, **Bellossom** | Agricultural Supplies | Apricorn Seeds, Mint Seeds, Fertilizers, Mulch |
| **Collector** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/820.png" width="30" height="30" align="center"> **Greedent**, **Zigzagoon** | Poké Balls | Ultra Balls, Apricorn Balls, Cherish Balls, Master Balls |
| **Scholar** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/199.png" width="30" height="30" align="center"> **Slowking**, **Orbeetle** | Experience Candies | Exp Candy XS–XL, Rare Candies |
| **Chef** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/685.png" width="30" height="30" align="center"> **Slurpuff**, **Alcremie** | Food & Cooking | Ponigiri, Lava Cookies, Rare Berries, Golden Apples |
| **Trainer** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/68.png" width="30" height="30" align="center"> **Machamp**, **Hariyama** | EV Vitamins | HP Up, Protein, Iron, Carbos, Calcium, Zinc |
| **Armorer** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/448.png" width="30" height="30" align="center"> **Lucario**, **Bisharp** | Battle Held Items | Choice Band, Choice Specs, Life Orb, Focus Sash, Leftovers |
| **Prospector** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1000.png" width="30" height="30" align="center"> **Gholdengo**, **Sableye** | Relics & Valuables | Relic Coins, Gold Ingots, Netherite Scrap, Emeralds |
| **Smith** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/959.png" width="30" height="30" align="center"> **Tinkaton**, **Klinklang** | Smithing Templates | Armor Trims, Netherite Upgrade Templates, Tool Upgrades |

---

### 3. Gathering, Harvester & Mining
| Job | Representative Pokémon | Role & Virtual Pasture Drops |
| :--- | :--- | :--- |
| **Harvester** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png" width="30" height="30" align="center"> **Venusaur**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/212.png" width="30" height="30" align="center"> **Scizor**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/251.png" width="30" height="30" align="center"> **Celebi** | Gathers crops (Wheat, Carrots, Potatoes, Beetroot, Sugar Cane), apricorns, berries, and mints. |
| **Mining** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/530.png" width="30" height="30" align="center"> **Excadrill**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/208.png" width="30" height="30" align="center"> **Steelix**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/524.png" width="30" height="30" align="center"> **Roggenrola** | Digs for regional ores (Coal, Raw Iron, Raw Copper, Gold, Redstone, Amethyst) scaled by proficiency. |
| **Fishing** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/130.png" width="30" height="30" align="center"> **Gyarados**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/9.png" width="30" height="30" align="center"> **Blastoise**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/382.png" width="30" height="30" align="center"> **Kyogre** | Catches fish, treasures, and aquatic loot directly into inventory. |
| **Archeologist** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/34.png" width="30" height="30" align="center"> **Nidoking**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/31.png" width="30" height="30" align="center"> **Nidoqueen**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/344.png" width="30" height="30" align="center"> **Claydol** | Excavates ancient relics, fossils, pottery sherds, and artifacts. |

---

### 4. Support, Combat & Passive Team Buffs
| Category | Job / Buff | Representative Sprite | Effect |
| :--- | :--- | :--- | :--- |
| **Support** | **Healer** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/242.png" width="30" height="30" align="center"> **Blissey**, **Chansey** | Passively heals injured party Pokémon and revives fainted team members. |
| **Support** | **Mentor** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/150.png" width="30" height="30" align="center"> **Mewtwo**, **Alakazam** | Grants passive XP over time to all Pokémon in the pasture. |
| **Combat** | **Guard** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/475.png" width="30" height="30" align="center"> **Gallade**, **Incineroar** | Patrols base borders and repels wild aggressive spawns for XP and loot. |
| **Passive Auras** | **Speed / Strength / Res / Haste** | <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/291.png" width="30" height="30" align="center"> **Ninjask**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/384.png" width="30" height="30" align="center"> **Rayquaza**, <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/493.png" width="30" height="30" align="center"> **Arceus** | Applies potion status effects (Speed II, Strength I, Resistance I, Haste I, Saturation, Luck) to nearby players. |

---

## ⭐ Proficiency Multiplier System (1–5 Stars)

Every Pokémon species possesses individual proficiency ratings (1 to 5 Stars) for each skill:

| Stars | Level | Cooldown Multiplier | Production Efficiency |
| :---: | :--- | :---: | :--- |
| ⭐ | **Novice** | `1.67x` | Standard base drop rates. |
| ⭐⭐ | **Apprentice** | `1.33x` | Faster production cycle. |
| ⭐⭐⭐ | **Skilled** | `1.00x` | Normal standard rates. |
| ⭐⭐⭐⭐ | **Expert** | `0.67x` | Fast cooldowns, unlocks higher loot tiers. |
| ⭐⭐⭐⭐⭐ | **Master** | `0.33x` | 3x faster production, maximum item yield and ultra-rare loot rates. |

---

## 🛠️ How To Use & Setup Automation

1. **Craft & Place the Virtual Pasture:** Place the block down anywhere in your base.
2. **Assign Pokémon:** Right-click the block to open the Pasture PC interface and tether your Pokémon.
3. **Assign Cobblebase Jobs:** Click the **Cobblebase** button in the top corner of the pasture interface to select a job (or let them auto-produce).
4. **Automate with Hoppers:** Place a vanilla **Hopper** (or any modded item pipe) directly **underneath** the Virtual Pasture block. The hopper will automatically pull all items from the 27-slot inventory and pipe them into your storage chests!
5. **Manual Inspection:** **Shift + Right Click** the Virtual Pasture block at any time to open its 27-slot inventory directly.

---

## ⚙️ Configuration File

Located at `config/PastureLoot.json`:

```json
{
  "tick_per_minute": 1200,
  "drop_chance_per_minute": 0.05,
  "item_blacklist": [
    "minecraft:porkchop",
    "minecraft:beef",
    "minecraft:chicken"
  ],
  "flatten_item_quantity": false
}
```

* `tick_per_minute`: Number of game ticks treated as one minute (default: `1200`).
* `drop_chance_per_minute`: Chance for unassigned/idle Pokémon to roll standard species drops per minute (default: `0.05` = 5%).
* `item_blacklist`: Blacklist specific item IDs from being generated.
* `flatten_item_quantity`: If `true`, forces all generated drop stacks to a quantity of 1.

---

## 📜 Legal Notice & Open-Source Compliance

This project is a compatibility bridge and open-source contribution combining the architectures of **Cobblemon Virtual Loot** and **Cobblebase**.

* **License:** This software is licensed under the **[MIT License](LICENSE.txt)** with portions adapted from Cobblemon under the **[Mozilla Public License 2.0 (MPL-2.0)](https://mozilla.org/MPL/2.0/)**.
* **Open-Source Compliance:** Under the terms of the MIT and MPL-2.0 licenses, you are free to use, study, modify, distribute, and include this mod in any modpack, provided that original copyright notices and license permissions are preserved.
* **Disclaimer:** This mod is an independent open-source project and is not officially affiliated with, endorsed by, or associated with Mojang AB, Microsoft, CobbledStudios, or Pocketpair.
  * *Minecraft* is a registered trademark of Mojang Synergies AB.
  * *Cobblemon* is an open-source Pokémon mod by CobbledStudios.
  * *Pokémon* is a registered trademark of Nintendo, Game Freak, and Creatures Inc.

---

## 👥 Credits & Attributions

We express our deepest gratitude to the talented creators whose work made this integration possible:

* **[LunazStudios](https://github.com/LunazStudios)** — Creator of the original **[Cobblemon Virtual Loot](https://github.com/LunazStudios/VirtualLoot)** ([Modrinth Page](https://modrinth.com/mod/cobblemon-virtual-loot)).
* **[notlown](https://github.com/notlown)** — Creator of **[Cobblebase](https://github.com/notlown/cobblebase)** ([Modrinth Page](https://modrinth.com/mod/cobblebase)).
* **[The Cobblemon Team](https://cobblemon.com/)** — Creators of the Cobblemon mod and the core pasture API.
* **[ludichat](https://github.com/ludichat)** — Creator of **[Cobbreeding](https://modrinth.com/mod/cobbreeding)**.
* **[Accieo](https://github.com/Accieo)** — Creator of **[Cobbleworkers](https://modrinth.com/mod/cobbleworkers)**.
* **gxenzy & Contributors** — Extended edition compatibility bridge, virtual job engine, and automated release workflows.
