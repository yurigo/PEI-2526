# Sesión 16 – Modelo de Negocio laSallefy: JSON y Diagrama de Clases

**Fecha:** 13 de abril de 2026

## Contenidos de la Sesión

En esta sesión se han realizado dos actividades principales:

1. Se ha dibujado en la **pizarra** el JSON y el diagrama de clases del **modelo de negocio** del proyecto final [laSallefy](../session13/ENUNCIADO.md).
2. Se ha dado tiempo para implementar los ficheros JSON del proyecto: **`songs.json`** y **`playlists.json`**, así como su lectura y escritura con Gson.

---

## 1. Relaciones clave del Modelo de Negocio

Durante la sesión se han remarcado las siguientes relaciones entre las clases del modelo:

| Relación            | Clases implicadas                         | Tipo UML     |
|---------------------|-------------------------------------------|--------------|
| **Composición**     | `Song` contiene `Sound` (notas)           | ◆────        |
| **Agregación**      | `Playlist` referencia `Song` por ID       | ◇────        |
| **Asociación**      | `Song` tiene un `Mood` (enum)             | ────         |
| **Asociación**      | `Song` tiene un `Style` / `Timbre` (enum) | ────         |
| **Asociación**      | `Playlist` tiene un `Mood` (enum)         | ────         |

> 💡 Además del modelo, existen otras clases e interfaces relativas al patrón **MVC**: `Menu`, `DAO`, `Controller`… En la pizarra de esta sesión se ha hecho foco **solo en el modelo de dominio**.

---

## 2. Diagrama de Clases del Modelo

```
«enum»          «enum»          «enum»
Mood            Style           Timbre
─────────────   ─────────────   ─────────────
HAPPY           CHIPTUNE        SINE
SAD             CLASSICAL       SQUARE
RELAX           AMBIENT         TRIANGLE
ENERGETIC       GAME            SAWTOOTH

┌────────────────────────────────────┐
│                Song                │
├────────────────────────────────────┤
│ - id: String                       │
│ - title: String                    │
│ - artist: String                   │
│ - durationSeconds: int             │
│ - mood: Mood                       │
│ - style: Style                     │
│ - playable: boolean                │
│ - sounds: List<Sound>              │
├────────────────────────────────────┤
│ + isPlayable(): boolean            │
│ + toString(): String               │
└────────────────┬───────────────────┘
                 ◆ composición (1..*)
                 ▼
┌────────────────────────────────────┐
│               Sound                │
├────────────────────────────────────┤
│ - frequency: int                   │
│ - durationMs: int                  │
│ - timbre: Timbre                   │
└────────────────────────────────────┘

┌────────────────────────────────────┐
│             Playlist               │
├────────────────────────────────────┤
│ - id: String                       │
│ - name: String                     │
│ - description: String              │
│ - mood: Mood                       │
│ - songIds: List<String>            │
│ - random: boolean                  │
├────────────────────────────────────┤
│ + getTotalDuration(): int          │
│ + countPlayable(): int             │
└────────────────────────────────────┘
   ◇ agregación (por ID)
   └──────────────► Song
```

---

## 3. Estructura de los Ficheros JSON

### 3.1. `songs.json`

Contiene la **lista de canciones** con todos sus campos. Cada canción puede tener o no una lista de sonidos (`sounds`). Si no tiene `sounds` (o la lista está vacía), la canción **no es reproducible**.

📁 [`songs.json`](songs.json)

```json
[
  {
    "id": "s001",
    "title": "Happy Birthday",
    "artist": "Tradicional",
    "durationSeconds": 15,
    "mood": "HAPPY",
    "style": "CLASSICAL",
    "playable": true,
    "sounds": [
      { "frequency": 264, "durationMs": 300, "timbre": "SINE" },
      { "frequency": 264, "durationMs": 300, "timbre": "SINE" },
      { "frequency": 297, "durationMs": 600, "timbre": "SINE" },
      { "frequency": 264, "durationMs": 600, "timbre": "SINE" },
      { "frequency": 352, "durationMs": 600, "timbre": "SINE" },
      { "frequency": 330, "durationMs": 1200, "timbre": "SINE" }
    ]
  },
  {
    "id": "s002",
    "title": "Ambient Drift",
    "artist": "SynthLab",
    "durationSeconds": 180,
    "mood": "RELAX",
    "style": "AMBIENT",
    "playable": false,
    "sounds": []
  }
]
```

### 3.2. `playlists.json`

Contiene la **lista de playlists**. Cada playlist referencia las canciones **por ID** (no guarda los objetos `Song` completos).

📁 [`playlists.json`](playlists.json)

```json
[
  {
    "id": "p001",
    "name": "Morning Vibes",
    "description": "Canciones para empezar el día",
    "mood": "HAPPY",
    "songIds": ["s001", "s003", "s005"],
    "random": false
  },
  {
    "id": "p002",
    "name": "Random RELAX – 10min",
    "description": "Álbum generado automáticamente",
    "mood": "RELAX",
    "songIds": ["s002", "s004"],
    "random": true
  }
]
```

---

## 4. Leer y Escribir los Ficheros JSON con Gson

### 4.1. Clases del modelo en Java

Para que Gson funcione, los campos de las clases Java deben coincidir con las claves del JSON.

**`Sound.java`** (ejemplo)
```java
public class Sound {
    private int frequency;
    private int durationMs;
    private String timbre;  // o enum Timbre si prefieres

    public Sound(int frequency, int durationMs, String timbre) {
        this.frequency = frequency;
        this.durationMs = durationMs;
        this.timbre = timbre;
    }
    // getters...
}
```

**`Song.java`** (ejemplo)
```java
import java.util.List;

public class Song {
    private String id;
    private String title;
    private String artist;
    private int durationSeconds;
    private String mood;
    private String style;
    private boolean playable;
    private List<Sound> sounds;

    // getters...

    public boolean isPlayable() {
        return playable && sounds != null && !sounds.isEmpty();
    }
}
```

**`Playlist.java`** (ejemplo)
```java
import java.util.List;

public class Playlist {
    private String id;
    private String name;
    private String description;
    private String mood;
    private List<String> songIds;
    private boolean random;

    // getters...
}
```

### 4.2. Leer `songs.json`

```java
Gson gson = new Gson();
JsonReader reader = new JsonReader(new FileReader("songs.json"));
Song[] songs = gson.fromJson(reader, Song[].class);
List<Song> songList = new ArrayList<>(Arrays.asList(songs));
```

### 4.3. Leer `playlists.json`

```java
JsonReader reader2 = new JsonReader(new FileReader("playlists.json"));
Playlist[] playlists = gson.fromJson(reader2, Playlist[].class);
List<Playlist> playlistList = new ArrayList<>(Arrays.asList(playlists));
```

### 4.4. Guardar cambios en `songs.json`

```java
String json = gson.toJson(songList);
FileWriter fw = new FileWriter("songs.json");
fw.write(json);
fw.close();
```

### 4.5. Guardar con formato legible (pretty print)

```java
Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
String jsonFormateado = gsonPretty.toJson(songList);
```

---

## 5. Arquitectura completa: MVC + DAO

Aunque en la sesión solo se ha trabajado el **modelo**, el proyecto final requiere todas las capas:

```
┌─────────────────────────────────────────────────────┐
│  Main                                               │
│   └─ crea e inyecta dependencias                   │
└──────────────────────┬──────────────────────────────┘
                       ▼
┌─────────────────────────────────────────────────────┐
│  Controller                                         │
│   ├─ menu: Menu (interface)                         │
│   ├─ songsDAO: SongsDAO (interface)                 │
│   ├─ playlistsDAO: PlaylistsDAO (interface)         │
│   └─ synth: SoundSynth (abstract)                  │
└──────────────────────┬──────────────────────────────┘
       ┌───────────────┼───────────────┐
       ▼               ▼               ▼
 MenuConsole    JsonSongsDAO    JsonPlaylistsDAO
                (lee/escribe     (lee/escribe
                songs.json)      playlists.json)
```

### Patrón DAO aplicado al proyecto

| Interface      | Implementación JSON     | Fichero            |
|----------------|-------------------------|--------------------|
| `SongsDAO`     | `JsonSongsDAO`          | `songs.json`       |
| `PlaylistsDAO` | `JsonPlaylistsDAO`      | `playlists.json`   |

```java
// Interface SongsDAO
public interface SongsDAO {
    List<Song> getAll();
    void save(List<Song> songs);
}

// Implementación con Gson
public class JsonSongsDAO implements SongsDAO {
    private static final String FILENAME = "songs.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public List<Song> getAll() {
        try {
            JsonReader reader = new JsonReader(new FileReader(FILENAME));
            Song[] songs = gson.fromJson(reader, Song[].class);
            return new ArrayList<>(Arrays.asList(songs));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void save(List<Song> songs) {
        try {
            FileWriter fw = new FileWriter(FILENAME);
            fw.write(gson.toJson(songs));
            fw.close();
        } catch (Exception e) {
            System.out.println("Error guardando canciones: " + e.getMessage());
        }
    }
}
```

---

## 6. Resumen de Conceptos

| Concepto                      | Descripción                                                                 |
|-------------------------------|-----------------------------------------------------------------------------|
| **Composición** (`Song`↔`Sound`) | `Sound` solo existe dentro de `Song`; si se borra la canción, se borran sus notas |
| **Agregación por referencia** (`Playlist`↔`Song`) | `Playlist` guarda IDs, no objetos; `Song` existe independientemente |
| **DAO**                       | Objeto que encapsula el acceso a los datos (lectura/escritura JSON)         |
| **Gson.toJson()**             | Serializa una lista o objeto Java a texto JSON                              |
| **Gson.fromJson()**           | Deserializa texto JSON a objeto o lista Java                                |
| **GsonBuilder.setPrettyPrinting()** | Genera JSON con indentación legible                               |

---

## Recursos

- [Sesión 14 – JSON y Gson en Java](../session14/README.md)
- [Sesión 15 – Diagrama de Clases de laSallefy](../session15/README.md)
- [Enunciado del Proyecto Final – laSallefy](../session13/ENUNCIADO.md)
- [Sesión 09 – Interfaces y patrón DAO](../session09/README.md)
- [Sesión 10 – Arquitectura en Capas](../session10/README.md)
- 🔗 [Gson User Guide](https://github.com/google/gson/blob/main/UserGuide.md)
- 🔗 [GsonBuilder – API Javadoc](https://www.javadoc.io/doc/com.google.code.gson/gson/latest/com.google.gson/com/google/gson/GsonBuilder.html)
