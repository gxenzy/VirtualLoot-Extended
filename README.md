<p align="center">
    <img src="https://i.imgur.com/e1XkYwv.png" alt="Cobblemon Virtual Loot Logo">
</p>

<p align="center">
    <img src="https://i.imgur.com/D40j3RV.png" alt="Fabric" width="105" height="28">
    <img src="https://i.imgur.com/ou0Id20.png" alt="NeoForge" width="127" height="28">
    <img src="https://i.imgur.com/pIrXd83.png" alt="Requires ArchitecturyAPI" width="222" height="28">
    <img src="https://i.imgur.com/48cXpQd.png" alt="Requires Cobblemon" width="211" height="28">
    <img src="https://i.imgur.com/Yr2BrdD.png" alt="Requires FabricAPI (For Fabric)" width="211" height="28">
</p>

**Cobblemon Virtual Loot** adds a Virtual Pasture to Cobblemon, inspired by the idea behind **Cobblemon Pasture Loot**.

After seeing players report lag issues from having many Pokemon entities walking around and also wanting a way to keep Pokemon outside purely as cute decorative companions without them constantly dropping items on the ground, this mod was created as a cleaner alternative. Instead of spawning the Pokemon outside, the Virtual Pasture keeps them virtually assigned through the Cobblemon pasture system and generates their Cobblemon loot directly inside its own inventory. This helps reduce world clutter, avoids item drops piling up on the ground, and lets decorative Pokemon stay decorative.

<p align="center">
  <a href="https://x.com/LunazStudios">
    <img src="https://img.shields.io/badge/TWITTER-91132a?style=for-the-badge&logo=x&logoColor=FFFFFF&labelColor=ff758e" />
  </a>
</p>

***

## How It Works

Place a **Virtual Pasture** and use it like a Cobblemon pasture. Pokemon assigned to the block stay virtual, so they do not appear as entities in the world, but the block still processes their loot generation.

The generated loot is based on the Pokémon own Cobblemon defeat drops. In practice, the mod reads the assigned Pokemon, checks that it is valid and not fainted, uses the Pokemon form's drop table, rolls one of the generated item drops, and inserts the result into the Virtual Pasture inventory.

Generated items are stored in the Virtual Pasture's 27-slot inventory. Players can open this inventory directly by using **Shift + Right Click** on the block. Items can also be extracted automatically with hoppers below the Virtual Pasture or similar item-transport systems.

The mod is available for both **Fabric** and **NeoForge**.

***

## Configuration

The config file is generated at:

```json
config/PastureLoot.json
```

Default config:

```json
{
  "tick_per_minute": 1200,
  "drop_chance_per_minute": 0.05,
  "item_blacklist": [
    "minecraft:porkchop",
    "minecraft:beef",
    "minecraft:chicken",
    "minecraft:mutton",
    "minecraft:rabbit",
    "minecraft:fish",
    "minecraft:cooked_porkchop",
    "minecraft:cooked_beef",
    "minecraft:cooked_chicken",
    "minecraft:cooked_mutton",
    "minecraft:cooked_rabbit",
    "minecraft:cooked_fish",
    "minecraft:leather",
    "minecraft:bone",
    "minecraft:spider_eye",
    "minecraft:rotten_flesh",
    "minecraft:rabbit_hide",
    "minecraft:rabbit_foot",
    "minecraft:cod",
    "minecraft:pufferfish",
    "minecraft:bone_block",
    "minecraft:bone_meal",
    "cobblemon:sharp_beak",
    "minecraft:honey_bottle",
    "minecraft:salmon",
    "minecraft:white_wool"
  ],
  "flatten_item_quantity": false
}
```

`tick_per_minute` controls how many game ticks the mod treats as one loot minute. The default value, `1200`, is the same as 60 seconds in normal Minecraft timing.

`drop_chance_per_minute` controls the chance for each assigned Pokemon to generate loot during that configured minute. The default value, `0.05`, means each Pokemon has a 5% chance per configured minute. Internally, this is converted into a per-tick chance, so generation stays smooth instead of happening all at once.

`item_blacklist` blocks specific item IDs from being generated. If a Pokemon's rolled drop is in this list, that drop is ignored.

`flatten_item_quantity` controls stack size behavior. When disabled, generated items keep the quantity rolled by the Pokemon's drop data. When enabled, every valid generated item stack is forced to 1 item.

***

## Optional Compatibility

**Cobblemon Virtual Loot** works on its own with Cobblemon, but it also includes optional compatibility with other Cobblemon addons. These addons are not required. If they are not installed, the mod will keep loading normally and the extra compatibility features will simply stay disabled.

### Cobbreeding

When **Cobbreeding** is installed, Pokemon inside the Virtual Pasture can generate eggs using Cobbreeding's own breeding systems.

The compatibility respects Cobbreeding's rules, configs, timers, toggles, egg generation, inheritance, shiny chances, egg moves, egg item data, and related behavior. Eggs are inserted into the same Virtual Pasture inventory as the normal loot, without spawning Pokemon entities in the world.

The Cobbreeding button can still be used to enable or disable breeding. A stick button is also added beside it, allowing players to toggle Virtual Loot generation separately. This means a Virtual Pasture can be set to generate only loot, only eggs, both, or neither.

### Cobbleworkers

When **Cobbleworkers** is installed, the Virtual Pasture can generate loot for selected Cobbleworkers jobs without spawning the Pokemon in the world.

Supported jobs:

- `cobbleworkers:pickup_looter`
- `cobbleworkers:fishing_looter`
- `cobbleworkers:dive_looter`
- `cobbleworkers:archaeologist`

`pickup_looter` works directly from eligible Pokemon. `fishing_looter` and `dive_looter` require at least one water block with air above it within 5 blocks of the Virtual Pasture. `archaeologist` requires at least one valid archaeology block with air above it within 5 blocks of the Virtual Pasture.

All generated Cobbleworkers loot goes into the same 27-slot Virtual Pasture inventory and follows the Virtual Loot toggle.

***

## Frequently Asked Questions

**Q: Can I use this mod in my modpack or create content with it?** **A:** Absolutely! You're welcome to include **Cobblemon Virtual Loot** in your modpack or feature it in your videos. Just remember to credit the mod appropriately and avoid claiming it as your own work.

**Q: Does this mod spawn Pokemon into the world?** **A:** No. The Virtual Pasture keeps Pokemon virtually assigned to the block and generates loot internally.

**Q: Does this mod drop generated items on the ground?** **A:** No. Generated items are placed inside the Virtual Pasture inventory.

**Q: Do I need Cobbreeding or Cobbleworkers?** **A:** No. They are optional compatibilities. The mod works without them.

**Q: How do I collect the generated loot?** **A:** Sneak-use the Virtual Pasture to open its inventory, or place hoppers below the block to extract items automatically.

***

## Credits

Special thanks to the **Cobblemon** team for creating Cobblemon and the pasture system that made this mod possible.

Special thanks to the creators of **Cobbreeding** and **Cobbleworkers** for the addons supported by the optional compatibility systems.

***

<div align="center">
    <p><i>Cobblemon Virtual Loot is not affiliated with Cobblemon nor Minecraft.</i></p>
    <p><i>Minecraft is a trademark of Mojang Synergies AB | Cobblemon is a trademark of CobbledStudios</i></p>
</div>
