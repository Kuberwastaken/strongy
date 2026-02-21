# Strongy

**A prettier, faster Minecraft stronghold calculator** — built on top of [Ninjabrain-Bot](https://github.com/Ninjabrain1/Ninjabrain-Bot).

I was bored of the boring themes, so I forked NB and gave it a proper glow-up: modern light & dark mode, performance improvements, and multi-stronghold tracking across rings.

All the brilliant triangulation math is from [Ninjabrain1](https://github.com/Ninjabrain1) — see [triangulation.pdf](triangulation.pdf) for the underlying mathematics.

![Strongy](https://i.imgur.com/oLJo3Cn.png)

## What's Different from Ninjabrain-Bot

| Feature | Ninjabrain-Bot | Strongy |
|---|---|---|
| **UI framework** | Swing | JavaFX with CSS themes |
| **Themes** | 10 hardcoded themes | Light + Dark mode, custom CSS themes |
| **Clipboard reading** | 100ms polling loop | Event-driven (`FlavorListener`) |
| **Calculation speed** | Standard | Separable convolution, parallel conditioning |
| **Multi-stronghold** | Shows closest only | Toggle to track across all rings |
| **Java version** | Java 8 | Java 17+ |

## Getting Started

Download the [latest release](https://github.com/YOUR_USERNAME/Strongy/releases/latest) and run the jar.

1. Throw an ender eye in Minecraft
2. Look at it and press **F3+C**
3. Repeat for as many eyes as you like
4. Strongy gives you a certainty percentage and stronghold coordinates

For subpixel accuracy, set hotkeys for "+0.01/-0.01 to last angle" in advanced options and calibrate your standard deviation.

## Features

* **Most accurate known triangulation algorithm** (inherited from Ninjabrain-Bot)
    * Accounts for user error and all known stronghold generation mechanics
    * Certainty percentage describes prediction confidence
* **Modern UI** with light/dark mode toggle
* **Multi-stronghold tracking** — see predictions across all stronghold rings
* **Subpixel adjustment** for perfect travel
* **Blind coord evaluation** (press F3+C in the nether)
* **Fossil divine** — look at fossil start and press F3+I
* **OBS overlay** with auto-hide
* **Built-in HTTP API** for external tool integrations
* **Event-driven clipboard** — no more CPU-wasting polling

## FAQ

#### Is this legal to use in speedruns?

Yes. Strongy uses the same calculation engine as Ninjabrain-Bot, which has been legal since the calculator unban on 2021-12-09.

#### The calculator said 100% certain but missed the stronghold?

You most likely moved while throwing an eye. It takes longer than you'd think for the player to fully stop — even when F3 coordinates appear stationary, there's client-server desync. Push yourself into a corner between two blocks to stop quickly.

#### How do I use subpixel adjustment?

Set hotkeys for "+0.01 to last angle" and "-0.01 to last angle" in advanced settings. See the [perfect travel document](https://docs.google.com/document/d/1JTMOIiS-Hl6_giEB0IQ5ki7UV-gvUXnNmoxhYoSgEAA/edit#heading=h.agb0mdup7ims) for details.

#### What does "Display stronghold location" mean?

It controls how coordinates are presented — does not affect accuracy:
* **(4, 4)** — starter staircase coordinates
* **(8, 8)** — chunk center coordinates
* **Chunk** — chunk coordinates

#### What is "Standard deviation"?

Describes how accurately you measure ender eye angles. Lower = bot trusts your readings more. Typical ranges:
* **0.050 - 0.200** — quake pro FOV
* **0.020 - 0.040** — 30 FOV
* **0.005 - 0.010** — 30 FOV with subpixel adjustment

Use "Calibrate standard deviation" in settings to find your optimal value.

#### What is "Multi-stronghold tracking"?

When enabled, Strongy shows predictions for strongholds across multiple rings simultaneously, not just the closest one. Useful for advanced routing.

## Credits

* **[Ninjabrain1](https://github.com/Ninjabrain1)** — original [Ninjabrain-Bot](https://github.com/Ninjabrain1/Ninjabrain-Bot) and the triangulation math
* **Strongy** — modern UI, performance improvements, and multi-stronghold tracking

## License

[GPLv3](LICENSE) — same as the original Ninjabrain-Bot.
