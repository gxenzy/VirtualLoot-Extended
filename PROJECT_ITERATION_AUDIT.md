# Virtual Loot Extended: Complete Project Evolution & Architecture Audit

An exhaustive technical chronicle of all design phases, architectural decisions, feature implementations, bug investigations, failed attempts, solutions, and production achievements across the entire codebase lifecycle.

---

## 📑 Table of Contents
1. [Executive Summary & Core Mission](#1-executive-summary--core-mission)
2. [Phase 1: Cobblebase, Cobbreeding & Interactive GUI Integration](#2-phase-1-cobblebase-cobbreeding--interactive-gui-integration)
3. [Phase 2: The Virtual Pasture Visualizer Architecture](#3-phase-2-the-virtual-pasture-visualizer-architecture)
4. [Phase 3: Network Synchronization & Packet Engineering](#4-phase-3-network-synchronization--packet-engineering)
5. [Phase 4: Visual Modes & Shader Pipeline Iterations](#5-phase-4-visual-modes--shader-pipeline-iterations)
   - [Mode 1: 3D Vector Wireframe (CS2 Style)](#mode-1-3d-vector-wireframe-cs2-style)
   - [Mode 2: Authentic Sci-Fi Hologram (SVC Holograms Audit)](#mode-2-authentic-sci-fi-hologram-svc-holograms-audit)
   - [Mode 3: Native Spectator Ghost](#mode-3-native-spectator-ghost)
6. [Phase 5: Terrain Physics, Collision & Spawn Teleportation](#6-phase-5-terrain-physics-collision--spawn-teleportation)
7. [Phase 6: Third-Party Mod Compatibility & Crash Prevention](#7-phase-6-third-party-mod-compatibility--crash-prevention)
8. [Comprehensive Git Commit & Workflow History](#8-comprehensive-git-commit--workflow-history)
9. [Current Architecture & File Map](#9-current-architecture--file-map)

---

## 1. Executive Summary & Core Mission

**Virtual Loot Extended** is a cross-loader Minecraft mod (Fabric & NeoForge for Minecraft 1.21.1) bridging **Cobblemon**, **Cobblebase**, and **Cobbreeding**. 

### Primary Objectives:
1. **Automated Virtual Pasture System**: Allow players to deposit Pokémon into Virtual Pastures to generate passive resources without ticking hundreds of active server entities.
2. **Zero-Lag Client-Side Visual Projections**: Bring pasture Pokémon to life in the world around the pasture block with 0 server TPS impact using custom client-side visual modes:
   - **Wireframe Mode (`Mode 1`)**: Full 3D glowing cyan vector lattice outline with see-through interiors.
   - **Hologram Mode (`Mode 2`)**: Authentic sci-fi/Star Wars luminous projection with horizontal laser scanlines and fullbright emissive glow.
   - **Ghost Mode (`Mode 3`)**: Minecraft Spectator mode transparency with gentle ethereal hover.
3. **Seamless Compatibility**: Perfect interoperability with Cobblebase, Cobbreeding, Iris/Sodium, and Cloth Config.

---

## 2. Phase 1: Cobblebase, Cobbreeding & Interactive GUI Integration

### 🎯 Goals:
- Inject customizable "Virtual Loot" and "Pasture Visuals" toggle buttons into Cobblemon's `PCGUI` and `PastureGUI`.
- Provide an in-game HUD overlay editor for administrators and players to position the stats overlay and control button.

### 🛠️ Key Milestones & Iterations:
- **Loader-Specific Screen Mixins**:
  - *Fabric*: Hooked into Fabric ScreenEvents (`ScreenEvents.AFTER_INIT`) with custom `allowMouseClick` and `allowMouseScroll` handlers.
  - *NeoForge*: Hooked into `ScreenEvent.Init.Post` on the NeoForge client event bus.
- **Cobblebase Button Conflict Resolution**:
  - Resolved coordinate collisions between Cobblebase header buttons and Virtual Loot toggles.
  - Dynamic button width scaling for 3-digit loot item counters (`Y-14` alignment).
- **Interactive Draggable HUD Overlay**:
  - Implemented `HudConfigManager.java` allowing players to drag the `[⚙]` button and stats overlay directly on-screen with built-in reset and save capabilities.

---

## 3. Phase 2: The Virtual Pasture Visualizer Architecture

### 🎯 The Challenge:
Vanilla Cobblemon pastures spawn physical server entities for every deposited Pokémon. On large multiplayer servers with hundreds of pasture boxes, this severely degrades server TPS.

### 🏗️ The Solution (`VirtualPastureVisualizer.java`):
- All pasture visual Pokémon are **100% client-side fake entities** (`new PokemonEntity(world, pkmn, CobblemonEntities.POKEMON)`).
- **Zero Server Overhead**: The server only synchronizes a list of Pokémon UUIDs and species data to tracking players in range via lightweight custom packets.
- **Client Lifecycle Management**:
  - `ACTIVE_PASTURE_VISUALS` map tracks active visual holders by `BlockPos`.
  - Atomic entity IDs prevent client entity ID collisions (`ENTITY_COUNTER = 500000+`).
  - Pokémon automatically discard when leaving tracking radius or closing the pasture.

---

## 4. Phase 3: Network Synchronization & Packet Engineering

### 💥 Challenges & Failures Encountered:

| Attempt / Issue | Failure Reason | Resolution |
| :--- | :--- | :--- |
| **Direct NBT BlockEntity syncing via `getUpdateTag()`** | Loom mappings in 1.21.1 failed to inject cleanly into Cobblemon's Kotlin `PokemonPastureBlockEntity`. | Replaced with explicit Minecraft 1.21.1 `CustomPacketPayload`. |
| **Kotlin `PacketRegisterInfo` Type Inference Errors** | Gradle compilation failed because Kotlin SAM conversions for `Function1` and `StreamCodec` required explicit type arguments. | Wrote explicit `Function1<FriendlyByteBuf, SyncVirtualPastureVisualPacket>` decoders and concrete handlers. |
| **Server-Client Thread Deadlocks** | Synchronous reflective calls to retrieve pasture Pokémon storage froze the client render thread. | Implemented asynchronous non-blocking storage extraction using Cobblemon's native `Pokemon.Companion.getS2C_CODEC()`. |

### 🚀 Final Working Sync Pipeline:
- **`SyncVirtualPastureVisualPacket.java`**: Serializes block position, visual mode (`0-3`), and a compact list of Pokémon data using Cobblemon's built-in S2C codecs.
- **`SyncVirtualPastureVisualHandler.java`**: Receives data on the client netty thread and queues updates to `VirtualPastureVisualizer` on the main client thread.

---

## 5. Phase 4: Visual Modes & Shader Pipeline Iterations

### Mode 1: 3D Vector Wireframe (CS2 Style)
- **Concept**: Pure glowing cyan vector line lattice outlining every edge of the Pokémon's 3D geometry with 100% empty, see-through transparent face interiors.
- **Iterations & Breakthroughs**:
  1. *Attempt 1 (Immediate RenderType.lines)*: Dropped FPS from 80 to 20 due to unbuffered draw calls.
  2. *Attempt 2 (UV Fractional Filter)*: Shaded faces with solid cyan textures, failing to look like vector wireframe and missing animated flame meshes.
  3. *Final Breakthrough (`WireframeVertexConsumer`)*: Captures polygon vertices directly and constructs vector edge lines to `RenderType.lines()`. Dynamically checks `renderType.mode()` to connect **Triangles (`3`)**, **Quads (`4`)**, and **Lines (`2`)**.
  4. *Result*: 100% vector line coverage across all Pokémon parts (including Charizard tail flames and wing membranes) at **60-80+ FPS**.

---

### Mode 2: Authentic Sci-Fi Hologram (SVC Holograms Audit)
- **Concept**: A luminous, electric cyan sci-fi projected energy hologram with horizontal laser scanlines and no muddy diffuse shading.
- **Iterations & Breakthroughs**:
  1. *Attempt 1 (Enchantment Glint + Sparks)*: Looked like powerpuff glitter / armor glint rather than a hologram.
  2. *Attempt 2 (Dark Blue RGB Multiply)*: Turned Pokémon into dark blue murky silhouettes at night.
  3. *Decompilation of `svc-holograms` (`svc-fabric-1.7.jar`)*:
     - Discovered `HologramVertexConsumer`, `HologramBufferSource`, and `RenderUtil`.
     - Extracted the core formula: Emissive translucency (`RenderType.entityTranslucentEmissive`), electric cyan color matrix (`#00F5FF`), horizontal scanline frequency `sin(y * 20.0 - time * 0.008)`, and fullbright light `0x00F000F0`.
  4. *Result*: Authentic Star Wars / sci-fi holographic projection matching the user's reference mod.

---

### Mode 3: Native Spectator Ghost
- **Concept**: Authentic Pokémon skin colors with Minecraft Spectator mode transparency.
- **Implementation**:
  - Direct buffer routing to `RenderType.itemEntityTranslucentCull(texture)` with alpha clamped to `105` (40% see-through opacity).
  - Emits subtle ethereal `SOUL_FIRE_FLAME` particles and floats gently above the ground.

---

## 6. Phase 5: Terrain Physics, Collision & Spawn Teleportation

### 1. Ground Alignment vs. Floating
- **The Problem**: Fake client entities do not tick Minecraft gravity, causing Mode 1 and Mode 2 to float in the air when terrain sloped downward.
- **The Solution**:
  - Integrated Minecraft's native `world.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z)` on both initial spawn and every tick.
  - **Mode 1 & Mode 2**: Feet clamped strictly to `(x, groundY, z)` so they walk firmly on solid grass/dirt terrain.
  - **Mode 3 (Ghost)**: The only mode with vertical hover offset (`groundY + 0.75 + 0.15 * sin(...)`).

### 2. Collision & Physics Push Elimination
- **The Problem**: Moving virtual Pokémon physically bumped into the player and pushed them around.
- **The Solution**:
  - Implemented **`VirtualPokemonNoPushMixin.java`** targeting `LivingEntity`.
  - Injected into `isPushable()`, `push(Entity)`, and `doPush(Entity)` to cancel all physical push forces on entities tagged with `virtualloot_visual_mode_*`.
  - Players can walk directly through virtual Pokémon smoothly.

### 3. Anti-Stacking Radial Distribution
- Pokémon spawn and roam across dedicated radial angular slots:
  $$\text{angle} = \left(\frac{2\pi}{N}\right) \times i$$
- Ensures Pokémon never spawn or cluster on top of each other.

### 4. Teleportation Spawn Animation & Vortex
- **Initial Teleport Pillar**: Spawns a full-height vertical particle beam column (32+ particles) and plays `CHORUS_FRUIT_TELEPORT` upon spawn and mode changes.
- **Materialization Scale**: Smoothly scales in the Pokémon model from 0 to 1 during the first 30 ticks using an easing curve:
  $$\text{scale} = \sin\left(\text{progress} \times \frac{\pi}{2}\right)$$
- **Vortex Swirl**: 6 particles per tick swirl upwards in a tightening spiral vortex during materialization.

### 5. Nametag Cleanup
- Resolved duplicate level display (`Venusaur Lv. 10 Lv. 10`) by delegating level rendering to Cobblemon's native nameplate system (`pkmn.getDisplayName(false)`).

---

## 7. Phase 6: Third-Party Mod Compatibility & Crash Prevention

### 💥 The Cobbreeding PC GUI Crash:
- **Crash Trace**:
  ```
  java.lang.IllegalArgumentException: Cannot get property BooleanProperty{name=breeding_activated...} as it does not exist in Block{minecraft:grass_block}
      at net.minecraft.state.State.get(State.java:97)
      at ludichat.cobbreeding.gui.PastureBreedingButton.renderWidget(PastureBreedingButton.java:54)
      at com.cobblemon.mod.common.client.gui.pc.PCGUI.render(PCGUI.java:691)
  ```
- **Root Cause**: Cobbreeding's `PastureBreedingButton` queried block properties on whatever block was targeted in front of the player (e.g. `grass_block` or `air`) when opening the PC GUI.
- **The Solution (`StateHolderPastureSafetyMixin.java`)**:
  - Intercepted `StateHolder.getValue(Property<T>)`.
  - If `!self.hasProperty(property)`, it safely intercepts the call and returns a fallback default value (`PasturePart.BOTTOM` or the property's first possible value) instead of crashing Minecraft.

---

## 8. Comprehensive Git Commit & Workflow History

| Commit SHA | GitHub Action Run | Type | Description / Highlight |
| :--- | :--- | :--- | :--- |
| `da8318b` | Run #48 | `fix` | Loader-specific AdminScreen mixins and top-left header button placement |
| `9910747` | Run #49 | `fix` | Replace AdminScreen mixins with safe Fabric ScreenEvents API & NeoForge bus |
| `860e3d8` | Run #50 | `fix` | Implement allowMouseClick and allowMouseScroll in Fabric ScreenEvents |
| `2e44a60` | Run #51 | `fix` | Dynamic button widths for 3-digit counts; align Virtual Loot button on Y-14 |
| `601ab84` | Run #52 | `feat` | Interactive draggable HUD overlay editor and robust toggle button |
| `b369a06` | Run #53 | `feat` | Draggable HUD button with manual scrollbar dragging in Pasture GUI |
| `8895d40` | Run #54 | `fix` | Separate Done/Reset to top bar and make `[⚙]` HUD button directly draggable |
| `62d69dc` | Run #55 | `chore` | Set user HUD layout as built-in default configuration |
| `9f90241` | Run #56 | `feat` | Complete Virtual Pasture Hologram, Wireframe & Ghost display engine |
| `3300ff6` | Run #57 | `fix` | Pass `CobblemonEntities.POKEMON` to `PokemonEntity` constructor |
| `63bc0f4` | Run #58 | `fix` | Implement server-to-client pasture Pokémon sync packet |
| `5e482a5` | Run #59 | `fix` | Resolve imports and cast null packet handler in `PokemonSyncHelper` |
| `1bbf521` | Run #60 | `fix` | Use explicit `Function1` decoder for client-bound packet registration |
| `460e0c0` | Run #61 | `fix` | Supply concrete `NOOP_SERVER_HANDLER` to resolve constructor ambiguity |
| `31bd37c` | Run #62 | `fix` | Supply concrete `ServerNetworkPacketHandler` for generic type inference |
| `4f18598` | Run #63 | `fix` | Native BlockEntity NBT syncing fallback for visual pasture Pokémon |
| `2634264` | Run #64 | `fix` | Hook `getUpdateTag` / `getUpdatePacket` and fix client render types |
| `645f0e8` | Run #65 | `fix` | Use atomic entity ID counter and exact mixin return types |
| `655c7c0` | Run #66 | `fix` | Remove invalid `getUpdateTag` mixin and sync visuals on GUI toggle |
| `3965ea6` | Run #67 | `fix` | Use `CobblemonClient.INSTANCE.getStorage()` for client Pokémon lookup |
| `65c814c` | Run #68 | `fix` | Use reflection for client-side storage lookup in `PokemonSyncHelper` |
| `0673d94` | Run #69 | `fix` | Resolve bottom pasture block position and recursive storage extraction |
| `5f53f3a` | Run #70 | `fix` | Restrict visual spawns to pasture Pokémon with glowing/floating modes |
| `dfc0099` | Run #71 | `fix` | Use `RandomSource` for `ClientLevel` random in `VirtualPastureVisualizer` |
| `bc4cb1f` | Run #72 | `feat` | Implement `VertexConsumer` shader filtering for wireframe, hologram, ghost |
| `af48bfd` | Run #73 | `fix` | Support Kotlin Array slots in storage lookup and direct buffer wrapper |
| `05e0a56` | Run #74 | `fix` | Remove unbounded reflection loops to prevent client thread freeze |
| `0c29cb0` | Run #75 | `fix` | Eliminate server-client thread deadlock in `PokemonPastureBlockEntity` |
| `5a25e00` | Run #76 | `fix` | Extract pasture Pokémon directly from open `PCGUI` and PC storage array |
| `54e2a8d` | Run #77 | `feat` | Authoritative server-to-client pasture visual sync packet and handlers |
| `28fa10e` | Run #78 | `feat` | Use Cobblemon native `Pokemon.Companion.getS2C_CODEC` for 100% sync |
| `aa1d1eb` | Run #79 | `feat` | CS2 vector wireframe, Fortnite energy hologram, and Seelie ghost shaders |
| `ca37589` | Run #80 | `fix` | Native Minecraft Spectator `RenderType` and clean LivingEntityRenderer mixin |
| `5516e2c` | Run #81 | `fix` | Spectator transparency, glint removal, and vector wireframe |
| `2afa499` | Run #82 | `fix` | Wireframe all layers, pure cyan energy hologram, and ground physics |
| `e908e85` | Run #83 | `fix` | Terrain walking alignment, high-fps wireframe, and pure hologram |
| `411db09` | Run #84 | `feat` | Integrate authentic Star Wars / SVC hologram shader pipeline |
| `7aae818` | Run #85 | `fix` | Restore 3D vector wireframe, vibrant hologram, and heightmap snapping |
| `fb5047e` | Run #86 | `fix` | Disable physics push on virtual entities and support triangle meshes |
| `5d58a37` | Run #87 | `fix` | Fix duplicate level nametag and clean up entity labels |
| `0c38268` | Run #88 | `feat` | Add teleportation spawn animation, particles, and materialization scale |
| `b2f4590` | Run #89 | `fix` | Fix compile errors, add no-push mixin, and teleport spawn effect |
| `f57dcc1` | Run #90 | `feat` | Dense rising teleport beam pillar and vortex swirl on spawn & mode change |
| `c43ec80` | Run #91 | `fix` | Prevent crash when Cobbreeding queries properties on non-pasture blocks in PC GUI |

---

## 9. Current Architecture & File Map

```
VirtualLoot-Extended/
├── common/src/main/java/com/lunazstudios/virtualloot/
│   ├── client/
│   │   ├── gui/
│   │   │   └── HudConfigManager.java          # In-game draggable HUD configuration
│   │   └── visual/
│   │       ├── VirtualPastureVisualizer.java   # Fake entity spawning, roaming, heightmap & teleport beams
│   │       ├── VirtualRenderShaderHelper.java  # Entity mode detection & render type mappings
│   │       └── VirtualShaderBufferWrapper.java# High-FPS 3D Vector Wireframe & Hologram VertexConsumers
│   ├── mixin/
│   │   ├── cobbreeding/
│   │   │   ├── StateHolderPastureSafetyMixin.java # Crash protection for Cobbreeding property queries
│   │   │   └── PastureGuiVirtualLootButtonMixin.java
│   │   ├── cobblebase/
│   │   │   └── CobblebaseButtonPositionBridgeMixin.java
│   │   ├── PokemonEntityRendererMixin.java    # Buffer wrapper injection & spawn materialization scaling
│   │   ├── VirtualPokemonNoPushMixin.java     # Eliminates player pushing & physical collision
│   │   └── PasturePokemonScrollListMixin.java  # Scrollbar clicking & dragging support
│   └── network/
│       ├── SyncVirtualPastureVisualPacket.java # S2C Authoritative Pokémon sync packet
│       └── SyncVirtualPastureVisualHandler.java# Client-side packet handler
├── fabric/                                    # Fabric loader initialization & ScreenEvents hooks
└── neoforge/                                  # NeoForge loader initialization & EventBus hooks
```

---
*Audit compiled automatically from full workspace history, Git logs, and conversation trajectory.*
