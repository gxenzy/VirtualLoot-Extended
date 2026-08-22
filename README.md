<p align="center">
    <img src="common/src/main/resources/icon.png" alt="Virtual Loot Extended Logo" width="128" height="128">
</p>

<p align="center">
    <img src="https://img.shields.io/badge/Minecraft-1.21.1-brightgreen?style=for-the-badge&logo=minecraft" alt="Minecraft 1.21.1">
    <img src="https://img.shields.io/badge/Cobblemon-1.7.3+-orange?style=for-the-badge" alt="Cobblemon 1.7.3">
    <img src="https://img.shields.io/badge/Platform-Fabric%20%7C%20NeoForge-blue?style=for-the-badge" alt="Fabric & NeoForge">
    <img src="https://img.shields.io/badge/Edition-Extended-blueviolet?style=for-the-badge" alt="Extended Edition">
    <img src="https://img.shields.io/badge/License-MIT-green?style=for-the-badge" alt="MIT License">
</p>

---

# 🌌 Virtual Loot: Extended Edition

**Virtual Loot: Extended** is an expanded, multi-mod virtual pasture ecosystem for Cobblemon. It combines the zero-lag virtual pasture architecture with native, out-of-the-box compatibility for **Cobblebase**, **Cobbreeding**, **Cobbleworkers**, and **Cobblemon**.

Instead of spawning 16–64 physical Pokémon entities per pasture (which destroys server TPS and client FPS), Virtual Loot keeps your Pokémon virtually assigned to the block while executing all of their Palworld-style jobs, breeding, and drop tables in the background!

All produce, mined minerals, harvested crops, and drops are collected directly into the **Virtual Pasture's 27-slot inventory** and can be automatically extracted with **hoppers below** straight into your storage chests!

---

## 🌟 Key Features

* **⚡ Zero Entity Lag:** Manage dozens of pastured Pokémon with **0 physical entities spawned**, completely eliminating pasture entity tick lag and pathfinding spikes.
* **🏗️ Full Cobblebase Virtual Jobs:** Assigned Cobblebase jobs (Producer, Mining, Harvester, Finder, Auras, Passive XP) run virtually on their proficiency-scaled cooldowns.
* **📦 27-Slot Internal Storage:** All generated produce, ores, crops, and drops are deposited inside the block's internal chest inventory.
* **🔄 Automated Hopper Extraction:** Place vanilla or modded hoppers directly beneath the Virtual Pasture block to automatically drain all collected items into chests, barrels, or sorting systems.
* **📊 Cobblebase Log Sync:** Every virtual job completion is recorded in Cobblebase's activity log system, making all production history viewable in-game through the Cobblebase GUI.
* **🥚 Cobbreeding Integration:** Supports virtual egg generation, breeding toggles, timers, and inheritance without physical entity breeding loops.
* **🎒 Cobbleworkers Integration:** Supports automated worker jobs (Pickup, Fishing, Diving, Archaeology).
* **💤 Relaxing / Idle Drops:** Pokémon set to Relax / Idle automatically generate their standard Cobblemon species defeat drop tables.

---

## 🛠️ How It Works

1. **Craft & Place a Virtual Pasture:** Place the block down anywhere in your base.
2. **Assign Pokémon:** Right-click the block to open the Pasture PC interface and deposit your Pokémon.
3. **Assign Cobblebase Jobs:** Click the **Cobblebase** button in the top corner of the pasture screen to assign roles:
   * **Producer (Wooloo, Miltank, Combee, Meowth, etc.):** Generates lore-based items (Wool, Moomoo Milk, Honey, Gold Nuggets, Diamonds, Slime Balls, etc.) into the inventory.
   * **Mining:** Yields ores and minerals based on Pokémon proficiency.
   * **Harvester:** Gathers crops, berries, and wood.
   * **Finder / Forager:** Rolls configured loot tables.
   * **Healer & Mentor:** Provides passive team buffs, healing, and passive XP to nearby players.
4. **Collect Loot:**
   * **Manual:** **Shift + Right Click** the Virtual Pasture to open its 27-slot inventory.
   * **Automated:** Place a **Hopper** underneath pointing into a chest, barrel, or storage pipe.

---

## 📋 Compatibility Matrix & Dependencies

| Component | Supported Version | Status |
| :--- | :--- | :--- |
| **Minecraft** | `1.21.1` | ✅ Required |
| **Cobblemon** | `1.7.3+` | ✅ Required |
| **Architectury API** | `13.0.8+` | ✅ Required |
| **Fabric API / NeoForge** | `0.116.12+` / `21.1.234+` | ✅ Required |
| **Cobblebase** | `2.0.0+` | 🌟 Optional (Full Virtual Job Support) |
| **Cobbreeding** | `2.2.1+` | 🥚 Optional (Virtual Egg Generation) |
| **Cobbleworkers** | `2.0.4+` | ⛏️ Optional (Automated Job Loot) |

---

## ⚙️ Configuration

The configuration file is automatically generated at `config/PastureLoot.json`:

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
* **Open-Source Compliance:** Under the terms of the MIT and MPL-2.0 licenses, you are free to use, modify, distribute, include in modpacks, and build upon this code, provided that original copyright notices and license permissions are preserved.
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
