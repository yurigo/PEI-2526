# Sesión 15 – Diagrama de Clases del Proyecto Final: laSallefy

**Fecha:** 7 de abril de 2026

## Contenidos de la Sesión

En esta sesión la actividad principal ha sido **diseñar y entregar a mano el diagrama de clases** del proyecto final [laSallefy](../session13/ENUNCIADO.md). El objetivo es plasmar en UML todas las clases, interfaces, relaciones y jerarquías que compondrán la aplicación.

---

## 1. Actividad: Diagrama de Clases de laSallefy

**Instrucciones:**

> Diseña y entrega **a mano** (en papel) el diagrama de clases UML del proyecto final laSallefy.  
> El diagrama debe reflejar, como mínimo:
>
> - Las clases del **modelo** (`Song`, `Playlist`, `Mood`, `Style`…)
> - Las **interfaces** y sus implementaciones (`SongsDAO`, `PlaylistsDAO`, `JsonSongsDAO`, `JsonPlaylistsDAO`, `Menu`, `MenuConsole`…)
> - La jerarquía de **SoundSynth** (clase abstracta y sus subclases)
> - El **Controller** y su relación con DAOs y Vista
> - Las **relaciones** UML correctas: composición, agregación, asociación, dependencia, herencia, realización

---

## 2. Guía para el Diagrama de Clases

### 2.1. Modelo de Dominio

El núcleo del sistema son las clases `Song` y `Playlist`:

```
┌──────────────────────────────────────┐
│                Song                  │
├──────────────────────────────────────┤
│ - id: String                         │
│ - title: String                      │
│ - artist: String                     │
│ - durationSeconds: int               │
│ - mood: Mood                         │
│ - style: Style                       │
│ - playable: boolean                  │
│ - sounds: List<Sound>                │
├──────────────────────────────────────┤
│ + isPlayable(): boolean              │
│ + getDurationSeconds(): int          │
│ + toString(): String                 │
└──────────────────────────────────────┘
         ◆ (composición)
         │ 1..*
         ▼
┌──────────────────────────────────────┐
│                Sound                 │
├──────────────────────────────────────┤
│ - frequency: int                     │
│ - durationMs: int                    │
└──────────────────────────────────────┘

┌──────────────────────────────────────┐
│              Playlist                │
├──────────────────────────────────────┤
│ - id: String                         │
│ - name: String                       │
│ - description: String                │
│ - mood: Mood                         │
│ - songIds: List<String>              │
│ - random: boolean                    │
├──────────────────────────────────────┤
│ + getTotalDuration(songs): int       │
│ + countPlayable(songs): int          │
└──────────────────────────────────────┘
```

> 💡 `Playlist` guarda **solo los IDs** de las canciones (no los objetos `Song`). Esto es **agregación por referencia**: la playlist "conoce" las canciones pero no las contiene físicamente.

### 2.2. Enumeraciones

```
«enum»                   «enum»
Mood                     Style
─────────────────        ─────────────────
HAPPY                    CHIPTUNE
SAD                      CLASSICAL
RELAX                    AMBIENT
ENERGETIC                GAME
```

### 2.3. Jerarquía de SoundSynth

```
         ┌──────────────────────────────────┐
         │          «abstract»              │
         │          SoundSynth              │
         ├──────────────────────────────────┤
         │ + makeSound(freq: int,           │
         │             durationMs: int)     │
         └──────────────┬───────────────────┘
                        │ herencia
         ┌──────────────┼──────────────┐
         ▼              ▼              ▼
┌──────────────┐ ┌──────────────┐ ┌──────────────┐
│SoundSynthSine│ │SoundSynthSquare│ │SoundSynthTriangle│
├──────────────┤ ├──────────────┤ ├──────────────┤
│+makeSound(…) │ │+makeSound(…) │ │+makeSound(…) │
└──────────────┘ └──────────────┘ └──────────────┘
```

### 2.4. Capa DAO

```
«interface»               «interface»
SongsDAO                  PlaylistsDAO
─────────────────────     ─────────────────────
+ getAll(): List<Song>    + getAll(): List<Playlist>
+ save(songs): void       + save(playlists): void

       △                         △
       │ realización             │ realización
       │                         │
JsonSongsDAO              JsonPlaylistsDAO
─────────────────────     ─────────────────────
- FILENAME: String        - FILENAME: String
+ getAll(): List<Song>    + getAll(): List<Playlist>
+ save(songs): void       + save(playlists): void
```

### 2.5. Arquitectura en Capas

```
┌───────────────────────────────────────────────────────┐
│                    Main                                │
└───────────────────────┬───────────────────────────────┘
                        │ crea e inyecta
                        ▼
┌───────────────────────────────────────────────────────┐
│                  Controller                            │
│  - menu: Menu                                         │
│  - songsDAO: SongsDAO                                 │
│  - playlistsDAO: PlaylistsDAO                         │
│  - synth: SoundSynth                                  │
│  + run(): void                                        │
└───────┬───────────────┬──────────────────┬────────────┘
        │               │                  │
        ▼               ▼                  ▼
  «interface»     «interface»        «abstract»
    Menu           SongsDAO          SoundSynth
     △                △                  △
     │                │                  │
MenuConsole    JsonSongsDAO        SoundSynthSine
```

### 2.6. Relaciones UML a representar

| Relación        | Símbolo UML          | Ejemplo en laSallefy                          |
|-----------------|----------------------|-----------------------------------------------|
| **Composición** | ◆──────              | `Song` ◆── `Sound` (Sound vive dentro de Song)|
| **Agregación**  | ◇──────              | `Playlist` ◇── `Song` (por ID, no por objeto) |
| **Asociación**  | ──────               | `Controller` ─── `SongsDAO`                   |
| **Dependencia** | ─ ─ ─ ─ ►           | `Controller` usa `Menu` (pasado como parámetro)|
| **Herencia**    | ──────△              | `SoundSynthSine` extiende `SoundSynth`        |
| **Realización** | ─ ─ ─ ─ △           | `JsonSongsDAO` implementa `SongsDAO`          |

---

## 3. Ejemplo de Diagrama Completo (texto)

```
Main
 └─ crea ──► Controller
                ├─ asociación ──► Menu (interface)
                │                    △ realización
                │                    └── MenuConsole
                ├─ asociación ──► SongsDAO (interface)
                │                    △ realización
                │                    └── JsonSongsDAO
                ├─ asociación ──► PlaylistsDAO (interface)
                │                    △ realización
                │                    └── JsonPlaylistsDAO
                └─ asociación ──► SoundSynth (abstract)
                                     △ herencia
                                     ├── SoundSynthSine
                                     ├── SoundSynthSquare
                                     └── SoundSynthTriangle

Song ◆──── Sound        (composición: Sound solo existe dentro de Song)
Playlist ◇── Song       (agregación por ID: Playlist referencia Songs)
Song ──── Mood (enum)   (asociación)
Song ──── Style (enum)  (asociación)
Playlist ──── Mood (enum) (asociación)
```

---

## 4. Criterios de Evaluación del Diagrama

| Criterio                                          | Puntos |
|---------------------------------------------------|--------|
| Clases del modelo correctas con atributos         | 25 %   |
| Relaciones correctas (tipo y cardinalidad)        | 30 %   |
| Interfaces y realizaciones                        | 20 %   |
| Jerarquía de SoundSynth                           | 15 %   |
| Legibilidad y organización del diagrama           | 10 %   |

---

## Recursos

- [Sesión 06 – Relaciones UML](../session06/README.md)
- [Sesión 07 – Herencia y Polimorfismo](../session07/README.md)
- [Sesión 09 – Interfaces en Java](../session09/README.md)
- [Sesión 10 – Arquitectura en Capas](../session10/README.md)
- [Enunciado del Proyecto Final – laSallefy](../session13/ENUNCIADO.md)
- 🔗 [UML Class Diagram Tutorial – Lucidchart](https://www.lucidchart.com/pages/uml-class-diagram)
- 🔗 [UML Class Diagrams – The Java Tutorials (Oracle)](https://www.oracle.com/technical-resources/articles/javase/uml.html)
