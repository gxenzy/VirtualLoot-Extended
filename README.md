<p align="center">
    <img src="common/src/main/resources/icon.png" alt="Virtual Loot Extended Logo" width="128" height="128">
</p>

<h1 align="center">🌌 Virtual Loot: Extended Edition</h1>

<p align="center"><b>Lag-Free Virtual Pasture Base Management for Cobblemon — Featuring Deep Native Integration with Cobblebase, Cobbreeding, Cobbleworkers & More.</b></p>

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

In traditional Cobblemon pastures, placing dozens of Pokémon causes massive server tick delays (TPS drop) and client FPS lag due to 3D entity rendering, physics, and pathfinding calculations. Furthermore, item drops scatter randomly across the terrain, creating world clutter.

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

## 🌾 Comprehensive Cobblebase Job Mechanics

Cobblebase includes **42 hand-crafted jobs** across 7 distinct categories for **1,367 Pokémon species**. In Virtual Loot: Extended, these jobs operate seamlessly inside the Virtual Pasture without entity lag:

### 1. 🍯 Gathering & Production Jobs
| Job | Role & Virtual Pasture Drops | Example Pokémon |
| :--- | :--- | :--- |
| 🍯 **Producer** | Generates lore items (Wool, Moomoo Milk, Honey, Gold Nuggets, Diamonds, Slime Balls, Silk, Pearls, etc.) directly into inventory on proficiency cooldowns. | Wooloo, Mareep, Miltank, Combee, Carbink, Meowth, Shuckle |
| 🌾 **Harvester** | Gathers crops, apricorns, berries, and mints into the block inventory. | Venusaur, Scizor, Celebi, Meganium, Torterra |
| ⛏️ **Mining** | Digs for ores (Coal, Raw Iron, Raw Copper, Gold, Redstone, Amethyst, Fossils) scaled by proficiency. | Steelix, Excadrill, Roggenrola, Ting-Lu, Aggron |
| 🎣 **Fishing** | Catches fish, treasures, and aquatic loot directly into inventory. | Gyarados, Blastoise, Kyogre, Kingdra, Wishiwashi |
| 🏺 **Archeologist** | Excavates ancient relics, fossils, pottery sherds, and artifacts. | Nidoking, Nidoqueen, Steelix, Claydol |

### 2. 🔍 12 Specialized Finder Subtypes
| Subtype | Focus Loot Table | Example Generated Items |
| :--- | :--- | :--- |
| 🧪 **Alchemist** | Evolution Items | Fire/Thunder/Water Stones, Linking Cords, Ability Patches |
| 💊 **Pharmacist** | Healing Items | Max Potions, Revives, Full Restores, Sacred Ash |
| 🏗️ **Architect** | Rare Building Blocks | Prismarine, Sea Lanterns, Crying Obsidian, Quartz |
| ⛏️ **Excavator** | Deep Ores & Minerals | Raw Iron/Gold, Diamonds, Emeralds, Ancient Debris |
| 🌱 **Botanist** | Agricultural Supplies | Apricorn Seeds, Mint Seeds, Fertilizers, Mulch |
| 📦 **Collector** | Poké Balls | Ultra Balls, Apricorn Balls, Cherish Balls, Master Balls |
| 📚 **Scholar** | XP Candies | Exp Candy XS–XL, Rare Candies |
| 🍳 **Chef** | Food & Cooking | Ponigiri, Lava Cookies, Rare Berries, Golden Apples |
| 💪 **Trainer** | EV Vitamins | HP Up, Protein, Iron, Carbos, Calcium, Zinc |
| ⚔️ **Armorer** | Battle Held Items | Choice Band, Choice Specs, Life Orb, Focus Sash, Leftovers |
| 💰 **Prospector** | Valuables & Relics | Relic Coins, Gold Ingots, Netherite Scrap, Emeralds |
| 🔨 **Smith** | Smithing & Armor Trims | Armor Trims, Netherite Upgrade Templates, Tool Upgrades |

### 3. 💚 Support, Combat & Passive Team Buffs
| Category | Job / Buff | Effect | Example Pokémon |
| :--- | :--- | :--- | :--- |
| 💚 **Support** | **Healer** | Passively heals injured party Pokémon and revives fainted team members. | Blissey, Chansey, Audino, Xerneas |
| 🎓 **Support** | **Mentor** | Grants passive XP over time to all Pokémon in the pasture. | Alakazam, Latios, Mesprit, Mewtwo |
| 🛡️ **Combat** | **Guard** | Patrols base borders and repels wild aggressive spawns for XP and loot. | Gallade, Scizor, Incineroar, Lucario |
| ✨ **Passive Auras** | **Speed / Strength / Res / Haste** | Applies potion status effects (Speed II, Strength I, Resistance I, Haste I, Saturation, Luck) to nearby players. | Ninjask, Machamp, Regirock, Arceus |

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

1. **Craft & Place the Virtual Pasture:** Place the block down in your base.
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
* **gxenzy & Contributors** — Extended edition compatibility bridge, virtual job executor, and automated release workflows.
