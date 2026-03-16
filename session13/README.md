# Sesión 13 – Proyecto Final: laSallefy

**Fecha:** 16 de marzo de 2026

## Contenidos de la Sesión

En esta sesión se ha presentado el **proyecto final de la asignatura** llamado **laSallefy**: un reproductor de canciones sintetizadas construido sobre el generador de sonidos desarrollado en clase. Antes de presentar el enunciado se ha revisado el código del `SoundSynth` existente, se ha dibujado su diagrama UML y se ha refactorizado la clase genérica `SoundSynth` a una **clase abstracta**, convirtiendo el `enum` de formas de onda en **subclases** de `SoundSynth`.

- 📄 **Enunciado del proyecto final:** [`ENUNCIADO.md`](ENUNCIADO.md) *(generado desde el HTML del campus)*
- 🔗 **Código original en clase:** [sound-synth-example](https://github.com/yurigo/sound-synth-example)
- 🔗 **Código refactorizado:** [rama refactor-inheritance-sound-synth](https://github.com/yurigo/sound-synth-example/tree/refactor-inheritance-sound-synth)

---

## 1. Revisión del SoundSynth original

El código visto al inicio de la sesión constaba de **2 clases y 1 enum**:

```
SoundSynth        ← clase concreta con un método makeSound()
Notes             ← enum con las notas musicales (DO, RE, MI, …)
WaveForm          ← enum con los tipos de onda (SINE, SQUARE, SAWTOOTH, TRIANGLE)
```

### 1.1. Diagrama UML original (dibujado en clase)

```
┌─────────────────────────────────┐
│           SoundSynth             │
├─────────────────────────────────┤
│ - waveForm : WaveForm           │
│ - sampleRate : int              │
├─────────────────────────────────┤
│ + makeSound(freq, durationMs)   │
│ + setWaveForm(waveForm)         │
└──────────────┬──────────────────┘
               │ usa (dependencia)
               ▼
┌──────────────────┐   ┌──────────────────────┐
│    WaveForm      │   │        Notes          │
├──────────────────┤   ├──────────────────────┤
│ SINE             │   │ DO, RE, MI, FA, SOL   │
│ SQUARE           │   │ LA, SI, DO2, RE2...   │
│ SAWTOOTH         │   └──────────────────────┘
│ TRIANGLE         │
└──────────────────┘
```

### 1.2. Código original (simplificado)

```java
// Enum de formas de onda
public enum WaveForm {
    SINE, SQUARE, SAWTOOTH, TRIANGLE
}

// Clase SoundSynth concreta
public class SoundSynth {
    private WaveForm waveForm;
    private int sampleRate;

    public SoundSynth(WaveForm waveForm) {
        this.waveForm = waveForm;
        this.sampleRate = 44100;
    }

    public void makeSound(double frequency, int durationMs) {
        // genera la onda en función de this.waveForm
        // ...
    }
}
```

**Uso:**
```java
SoundSynth synth = new SoundSynth(WaveForm.SINE);
synth.makeSound(440.0, 500); // La4 durante 500ms
```

---

## 2. Refactorización: de enum a jerarquía de clases

En clase se propuso sustituir el enum `WaveForm` por **subclases** de una `SoundSynth` abstracta. Esta refactorización aplica el principio **Open/Closed (OCP)**: se puede añadir un nuevo tipo de onda sin modificar el código existente, simplemente creando una nueva subclase.

### 2.1. Motivación

| Enfoque con enum | Enfoque con herencia |
|---|---|
| `switch/if` en `makeSound` para cada forma | Cada subclase implementa su propia lógica |
| Añadir una forma de onda ⟹ modificar `SoundSynth` | Añadir una forma de onda ⟹ crear una nueva subclase |
| No se puede personalizar estado por tipo | Cada subclase puede tener atributos propios |
| Viola el principio OCP | Cumple el principio OCP |

### 2.2. Diagrama UML refactorizado

```
        ┌──────────────────────────────────┐
        │         «abstract»               │
        │          SoundSynth              │
        ├──────────────────────────────────┤
        │ # sampleRate : int               │
        ├──────────────────────────────────┤
        │ + makeSound(freq, durationMs)    │ ← abstracto
        │ + getSampleRate() : int          │
        └────────────────┬─────────────────┘
                         │
          ┌──────────────┼──────────────┬──────────────┐
          ▼              ▼              ▼              ▼
┌──────────────┐ ┌──────────────┐ ┌──────────────┐ ┌──────────────┐
│SoundSynthSine│ │SoundSynthSqr │ │SoundSynthSaw │ │SoundSynthTri │
├──────────────┤ ├──────────────┤ ├──────────────┤ ├──────────────┤
│ (implementa  │ │ (implementa  │ │ (implementa  │ │ (implementa  │
│  onda seno)  │ │  onda cuadr) │ │  onda sierra)│ │  onda triang)│
└──────────────┘ └──────────────┘ └──────────────┘ └──────────────┘
```

### 2.3. Código refactorizado

**Clase abstracta base:**

```java
public abstract class SoundSynth {
    protected int sampleRate;

    public SoundSynth() {
        this.sampleRate = 44100;
    }

    // Método abstracto: cada subclase define cómo genera su onda
    public abstract void makeSound(double frequency, int durationMs);

    public int getSampleRate() {
        return sampleRate;
    }
}
```

**Subclase SoundSynthSine (implementada en clase):**

```java
public class SoundSynthSine extends SoundSynth {

    @Override
    public void makeSound(double frequency, int durationMs) {
        int numSamples = (int) (sampleRate * durationMs / 1000.0);
        byte[] buffer = new byte[numSamples];

        for (int i = 0; i < numSamples; i++) {
            // Onda senoidal: y(t) = A * sin(2π * f * t)
            double angle = 2.0 * Math.PI * i * frequency / sampleRate;
            buffer[i] = (byte) (Math.sin(angle) * 127);
        }

        // Enviar buffer al sistema de audio (javax.sound.sampled)
        playBuffer(buffer);
    }

    private void playBuffer(byte[] buffer) {
        // ... lógica de reproducción con AudioSystem ...
    }
}
```

**Subclases pendientes (ejercicio para los alumnos):**

```java
// Onda cuadrada: alterna entre +1 y -1 según si sin(ángulo) > 0
public class SoundSynthSquare extends SoundSynth {
    @Override
    public void makeSound(double frequency, int durationMs) {
        int numSamples = (int) (sampleRate * durationMs / 1000.0);
        byte[] buffer = new byte[numSamples];

        for (int i = 0; i < numSamples; i++) {
            double angle = 2.0 * Math.PI * i * frequency / sampleRate;
            buffer[i] = (byte) (Math.sin(angle) >= 0 ? 127 : -128);
        }
        playBuffer(buffer);
    }

    private void playBuffer(byte[] buffer) { /* ... */ }
}

// Onda de sierra: sube linealmente de -1 a +1 en cada período
public class SoundSynthSawtooth extends SoundSynth {
    @Override
    public void makeSound(double frequency, int durationMs) {
        int numSamples = (int) (sampleRate * durationMs / 1000.0);
        byte[] buffer = new byte[numSamples];
        double period = sampleRate / frequency;

        for (int i = 0; i < numSamples; i++) {
            double t = (i % period) / period; // 0..1 dentro del periodo
            buffer[i] = (byte) ((t * 2 - 1) * 127);  // -127..127
        }
        playBuffer(buffer);
    }

    private void playBuffer(byte[] buffer) { /* ... */ }
}

// Onda triangular: sube de -1 a +1 y baja de +1 a -1
public class SoundSynthTriangle extends SoundSynth {
    @Override
    public void makeSound(double frequency, int durationMs) {
        int numSamples = (int) (sampleRate * durationMs / 1000.0);
        byte[] buffer = new byte[numSamples];
        double period = sampleRate / frequency;

        for (int i = 0; i < numSamples; i++) {
            double t = (i % period) / period; // 0..1
            double val = t < 0.5 ? (4 * t - 1) : (3 - 4 * t); // -1..1
            buffer[i] = (byte) (val * 127);
        }
        playBuffer(buffer);
    }

    private void playBuffer(byte[] buffer) { /* ... */ }
}
```

**Uso del código refactorizado:**

```java
// Polimorfismo: la variable es de tipo SoundSynth (clase abstracta)
SoundSynth synth = new SoundSynthSine();
synth.makeSound(440.0, 500); // La4, 500ms, onda senoidal

synth = new SoundSynthSquare();
synth.makeSound(440.0, 500); // La4, 500ms, onda cuadrada
```

> 💡 Ahora se puede cambiar el tipo de sintetizador en tiempo de ejecución sin modificar el código que lo usa (**polimorfismo**).

---

## 3. El enum `Notes` – Notas musicales

Para facilitar la composición de secuencias de notas, se usa un enum con las frecuencias de cada nota:

```java
public enum Notes {
    //         Octava 3        Octava 4          Octava 5
    DO3(130.81), RE3(146.83), MI3(164.81), FA3(174.61),
    SOL3(196.00), LA3(220.00), SI3(246.94),

    DO4(261.63), RE4(293.66), MI4(329.63), FA4(349.23),
    SOL4(392.00), LA4(440.00), SI4(493.88),

    DO5(523.25), RE5(587.33), MI5(659.25), FA5(698.46),
    SOL5(783.99), LA5(880.00), SI5(987.77),

    SILENCE(0.0);

    private final double frequency;

    Notes(double frequency) {
        this.frequency = frequency;
    }

    public double getFrequency() {
        return frequency;
    }
}
```

**Uso con el sintetizador:**

```java
SoundSynth synth = new SoundSynthSine();

// Tocar Do-Mi-Sol (acorde Do Mayor, una nota cada vez)
synth.makeSound(Notes.DO4.getFrequency(), 400);
synth.makeSound(Notes.MI4.getFrequency(), 400);
synth.makeSound(Notes.SOL4.getFrequency(), 400);
```

---

## 4. Conceptos clave: Clases Abstractas

Las **clases abstractas** son clases que **no se pueden instanciar directamente** y que pueden contener métodos abstractos (sin implementación). Se usan cuando varias clases comparten estructura y comportamiento común, pero una parte del comportamiento varía entre ellas.

### 4.1. Sintaxis

```java
// Declarar clase abstracta con la palabra clave abstract
public abstract class Animal {
    protected String nombre;

    public Animal(String nombre) {
        this.nombre = nombre;
    }

    // Método abstracto: sin implementación, obliga a las subclases a implementarlo
    public abstract void hacerSonido();

    // Método concreto: tiene implementación, las subclases pueden usarlo o sobreescribirlo
    public String getNombre() {
        return nombre;
    }
}
```

```java
public class Perro extends Animal {
    public Perro(String nombre) {
        super(nombre);
    }

    @Override
    public void hacerSonido() {
        System.out.println(nombre + " dice: ¡Guau!");
    }
}

public class Gato extends Animal {
    public Gato(String nombre) {
        super(nombre);
    }

    @Override
    public void hacerSonido() {
        System.out.println(nombre + " dice: ¡Miau!");
    }
}
```

### 4.2. Reglas de las clases abstractas

| Regla | Descripción |
|---|---|
| No se puede instanciar | `new Animal()` → **error de compilación** |
| Puede tener métodos abstractos | Sin cuerpo, las subclases deben implementarlos |
| Puede tener métodos concretos | Con cuerpo, las subclases los heredan |
| Puede tener atributos y constructores | La subclase llama a `super(...)` |
| Una subclase debe implementar todos los abstractos | O ella misma debe ser abstracta |

### 4.3. Comparación: Clase Abstracta vs Interfaz

| Característica | Clase Abstracta | Interfaz |
|---|---|---|
| Instanciable | ❌ No | ❌ No |
| Herencia múltiple | ❌ Solo una clase base | ✅ Se pueden implementar varias |
| Puede tener atributos de instancia | ✅ Sí | ❌ Solo constantes (`static final`) |
| Puede tener constructores | ✅ Sí | ❌ No |
| Puede tener métodos concretos | ✅ Sí | ✅ Sí (desde Java 8, con `default`) |
| Relación semántica | "**es un**" | "**puede hacer**" |
| Cuándo usar | Compartir código y estado entre clases relacionadas | Definir un contrato sin imponer jerarquía |

> 💡 **Regla práctica:** Si las clases comparten estado (atributos) y una relación "es un", usa clase abstracta. Si solo comparten comportamiento sin relación de herencia, usa interfaz.

---

## 5. Persistencia con JSON (Gson)

El proyecto final requiere guardar y cargar datos en formato JSON. La librería más usada en clase es **Gson** (de Google).

### 5.1. Añadir Gson al proyecto (Maven)

```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.10.1</version>
</dependency>
```

### 5.2. Serializar (objeto → JSON)

```java
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonSongsDAO implements SongsDAO {

    private static final String FILE_PATH = "songs.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void saveAll(List<Song> songs) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(songs, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar canciones: " + e.getMessage(), e);
        }
    }
}
```

### 5.3. Deserializar (JSON → objeto)

```java
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

// En JsonSongsDAO:
@Override
public List<Song> findAll() {
    try (FileReader reader = new FileReader(FILE_PATH)) {
        Type listType = new TypeToken<ArrayList<Song>>() {}.getType();
        List<Song> songs = gson.fromJson(reader, listType);
        return songs != null ? songs : new ArrayList<>();
    } catch (IOException e) {
        return new ArrayList<>(); // Si no existe el fichero, devuelve lista vacía
    }
}
```

### 5.4. Ejemplo de `songs.json`

```json
[
  {
    "id": "1",
    "title": "Cumpleaños Feliz",
    "artist": "Popular",
    "durationSeconds": 30,
    "mood": "HAPPY",
    "style": "CLASSICAL",
    "playable": true,
    "notes": [
      { "note": "SOL4", "durationMs": 300 },
      { "note": "SOL4", "durationMs": 300 },
      { "note": "LA4",  "durationMs": 600 },
      { "note": "SOL4", "durationMs": 600 },
      { "note": "DO5",  "durationMs": 600 },
      { "note": "SI4",  "durationMs": 1200 }
    ]
  },
  {
    "id": "2",
    "title": "Sinfonía Ambient",
    "artist": "Desconocido",
    "durationSeconds": 180,
    "mood": "RELAX",
    "style": "AMBIENT",
    "playable": false,
    "notes": null
  }
]
```

---

## 6. Arquitectura del Proyecto laSallefy

El proyecto debe seguir una **arquitectura en capas** como la vista en sesiones anteriores (session10), aplicando los principios GRASP.

### 6.1. Estructura de paquetes

```
lasallefy/
├── model/
│   ├── Song.java
│   ├── Playlist.java
│   ├── NoteEntry.java        ← (note, durationMs)
│   ├── Mood.java             ← enum: HAPPY, SAD, RELAX, ENERGETIC
│   └── Style.java            ← enum: CHIPTUNE, CLASSICAL, GAME, AMBIENT
├── dao/
│   ├── SongsDAO.java         ← interfaz
│   ├── PlaylistsDAO.java     ← interfaz
│   ├── JsonSongsDAO.java     ← implementación con Gson
│   └── JsonPlaylistsDAO.java ← implementación con Gson
├── manager/
│   ├── LibraryManager.java   ← gestión de canciones y playlists
│   ├── PlaybackManager.java  ← lógica de reproducción
│   └── AlbumGenerator.java   ← generación de álbum random por mood
├── controller/
│   └── Controller.java       ← coordina vista ↔ managers ↔ DAOs
├── view/
│   ├── Menu.java             ← interfaz de presentación
│   └── MenuConsole.java      ← implementación en consola
├── synth/
│   ├── SoundSynth.java       ← clase abstracta
│   ├── SoundSynthSine.java
│   ├── SoundSynthSquare.java
│   ├── SoundSynthSawtooth.java
│   └── SoundSynthTriangle.java
└── Main.java
```

### 6.2. Diagrama UML simplificado de clases

```
┌──────────┐     usa      ┌──────────────────┐
│   Main   │─────────────>│   Controller     │
└──────────┘              ├──────────────────┤
                          │ - libraryManager │
                          │ - playbackMgr    │
                          │ - albumGenerator │
                          │ - menu           │
                          └────────┬─────────┘
                                   │ usa
              ┌────────────────────┼────────────────────┐
              ▼                    ▼                     ▼
   ┌──────────────────┐  ┌──────────────────┐  ┌──────────────────┐
   │  LibraryManager  │  │ PlaybackManager  │  │  AlbumGenerator  │
   ├──────────────────┤  ├──────────────────┤  ├──────────────────┤
   │ - songsDAO       │  │ - synth          │  │ - libraryMgr     │
   │ - playlistsDAO   │  └──────────────────┘  └──────────────────┘
   └──────────────────┘
          │ usa (interfaz)
   ┌──────┴─────┐
   ▼             ▼
┌──────────┐  ┌──────────────┐
│ SongsDAO │  │ PlaylistsDAO │  ← interfaces
└────┬─────┘  └──────┬───────┘
     ▼                ▼
┌──────────────┐  ┌──────────────────┐
│JsonSongsDAO  │  │JsonPlaylistsDAO  │  ← implementaciones concretas
└──────────────┘  └──────────────────┘
```

### 6.3. Las interfaces DAO

```java
// SongsDAO.java
import java.util.List;

public interface SongsDAO {
    List<Song> findAll();
    Song findById(String id);
    void save(Song song);
    void delete(String id);
    void saveAll(List<Song> songs);
}

// PlaylistsDAO.java
import java.util.List;

public interface PlaylistsDAO {
    List<Playlist> findAll();
    Playlist findById(String id);
    void save(Playlist playlist);
    void delete(String id);
    void saveAll(List<Playlist> playlists);
}
```

---

## 7. Funcionalidades principales

### 7.1. Algoritmo de Álbum Random (Greedy)

```java
public class AlbumGenerator {

    private LibraryManager libraryManager;

    public AlbumGenerator(LibraryManager libraryManager) {
        this.libraryManager = libraryManager;
    }

    public Playlist generateAlbum(Mood mood, int targetMinutes) {
        int targetSeconds = targetMinutes * 60;

        // 1. Obtener canciones del mood pedido que sean reproducibles
        List<Song> candidates = libraryManager.getAllSongs().stream()
            .filter(s -> s.getMood() == mood && s.isPlayable())
            .collect(Collectors.toList());

        // 2. Mezclar aleatoriamente (algoritmo greedy)
        Collections.shuffle(candidates);

        // 3. Añadir canciones hasta alcanzar la duración objetivo
        List<String> selectedIds = new ArrayList<>();
        int totalSeconds = 0;

        for (Song song : candidates) {
            if (totalSeconds >= targetSeconds) break;
            selectedIds.add(song.getId());
            totalSeconds += song.getDurationSeconds();
        }

        // 4. Crear la playlist
        Playlist album = new Playlist();
        album.setId(UUID.randomUUID().toString());
        album.setName("[RANDOM] " + mood.name() + " – " + targetMinutes + "min");
        album.setMood(mood);
        album.setSongIds(selectedIds);

        return album;
    }
}
```

### 7.2. Reproducción de una playlist

```java
public class PlaybackManager {

    private SoundSynth synth;
    private LibraryManager libraryManager;
    private Menu menu;

    public PlaybackManager(SoundSynth synth, LibraryManager libraryManager, Menu menu) {
        this.synth = synth;
        this.libraryManager = libraryManager;
        this.menu = menu;
    }

    public void playPlaylist(Playlist playlist) {
        for (String songId : playlist.getSongIds()) {
            Song song = libraryManager.findSongById(songId);
            if (song == null) continue;

            if (!song.isPlayable()) {
                menu.show("⏭  Saltando '" + song.getTitle() + "' (NOT PLAYABLE)");
                continue;
            }

            menu.show("▶  Reproduciendo: " + song.getTitle() + " – " + song.getArtist());
            playSong(song);
        }
    }

    private void playSong(Song song) {
        for (NoteEntry entry : song.getNotes()) {
            Notes note = Notes.valueOf(entry.getNote());
            synth.makeSound(note.getFrequency(), entry.getDurationMs());
        }
    }
}
```

---

## 8. Resumen de Conceptos

| Concepto | Descripción |
|---|---|
| **Clase abstracta** | No se puede instanciar; puede tener métodos abstractos (sin implementación) que las subclases deben implementar |
| **Método abstracto** | Declarado con `abstract`, sin cuerpo; obliga a las subclases a implementarlo |
| **Polimorfismo** | Una variable de tipo `SoundSynth` puede apuntar a `SoundSynthSine`, `SoundSynthSquare`, etc. |
| **Enum** | Tipo con un conjunto fijo de valores constantes; puede tener atributos y métodos |
| **JSON / Gson** | Formato de intercambio de datos; `Gson` serializa/deserializa objetos Java a/desde JSON |
| **Patrón DAO** | Separa la lógica de acceso a datos del resto de la aplicación mediante una interfaz |
| **Arquitectura en capas** | Model → DAO → Manager → Controller → View; cada capa solo conoce la capa inferior |
| **Principio OCP** | Open for extension, Closed for modification: añadir nuevas subclases sin modificar el código existente |

---

## 9. Recursos Adicionales

### Código de referencia
- 🔗 [sound-synth-example (rama principal)](https://github.com/yurigo/sound-synth-example) – Código original con 2 clases y enum
- 🔗 [sound-synth-example (rama refactorizada)](https://github.com/yurigo/sound-synth-example/tree/refactor-inheritance-sound-synth) – SoundSynth abstracta + SoundSynthSine implementada

### Documentación oficial
- [Abstract Methods and Classes – The Java Tutorials (Oracle)](https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html)
- [Enum Types – The Java Tutorials (Oracle)](https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html)
- [Gson User Guide – Google](https://github.com/google/gson/blob/main/UserGuide.md)
- [javax.sound.sampled – Java SE 17 API](https://docs.oracle.com/en/java/se/17/docs/api/java.desktop/javax/sound/sampled/package-summary.html)
- [Collections.shuffle – Java SE 17 API](https://docs.oracle.com/en/java/se/17/docs/api/java.base/java/util/Collections.html#shuffle(java.util.List))

### Sesiones relacionadas
- [Sesión 07 – Herencia y Polimorfismo](../session07/README.md)
- [Sesión 08 – Herencia, Visibilidad y Polimorfismo](../session08/README.md)
- [Sesión 09 – Interfaces en Java](../session09/README.md)
- [Sesión 10 – Herencia Múltiple, Interfaces y Arquitectura en Capas](../session10/README.md)
- [Sesión 11 – Excepciones en Java](../session11/README.md)
- [Sesión 12 – Excepciones Propias en Java](../session12/README.md)