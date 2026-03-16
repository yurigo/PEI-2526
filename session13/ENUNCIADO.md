# 🎧 laSallefy – Enunciado del Proyecto Final

> **Asignatura:** Programación para Entornos Interactivos (PEI)  
> **Fecha límite de entrega:** 28 de mayo de 2026

En este proyecto vamos a ampliar el ejercicio del **generador de sonidos** para convertirlo en un **reproductor de "canciones"** con:

- Listas de reproducción (playlists)
- Biblioteca de canciones
- Reproducción usando vuestro `SoundSynth`
- Persistencia en **JSON**
- Generación de **álbumes aleatorios por "mood"** (estado de ánimo)

---

## 1. Contexto general

Imaginad un **mini reproductor musical** que no trabaja con MP3, sino con un **sintetizador propio** (el que habéis ido construyendo en clase).

- Cada *canción* es una **secuencia de notas** (o más adelante, de frecuencias y duraciones).
- Algunas canciones pueden tener solo **metadatos** (título, artista, duración estimada, mood…), pero **no** la secuencia de notas → se pueden listar, pero **no se pueden reproducir**.
- Las listas de reproducción (**playlists**) sólo guardan **referencias a canciones por id**, no copias completas de las canciones.

El objetivo es que el usuario pueda:

1. Gestionar una **biblioteca de canciones**.
2. Gestionar **playlists** que referencian esas canciones.
3. Reproducir canciones o playlists usando vuestro `SoundSynth`.
4. Pedir al sistema que cree un **"álbum random"** de un determinado **mood** (p. ej. *HAPPY*) y una duración aproximada en minutos.

---

## 2. Modelo de dominio mínimo

### 2.1. Canciones (`Song`)

Cada canción debe tener, como mínimo:

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | `String` o `int` | Identificador único |
| `title` | `String` | Título de la canción |
| `artist` | `String` | Nombre del artista |
| `durationSeconds` | `int` | Duración aproximada en segundos |
| `mood` | `enum` | p. ej. `HAPPY`, `SAD`, `RELAX`, `ENERGETIC`… |
| `style` | `String` o `enum` | p. ej. `CHIPTUNE`, `CLASSICAL`, `GAME`, `AMBIENT`… |
| `playable` | `boolean` | Indica si tiene datos de reproducción |
| Datos de reproducción | (opcional) | Lista de `(note, durationMs)` o `(frequency, durationMs)` |

**Requisito importante:**

- Debe ser posible que **una canción no tenga datos de reproducción**, solo metadatos.
- Al **listar las canciones** se debe indicar claramente si la canción es:
  - `[PLAYABLE]` → tiene notas / frecuencias definidas
  - `[NOT PLAYABLE]` → no se puede reproducir

### 2.2. Playlists (`Playlist`)

Cada playlist tendrá al menos:

| Campo | Descripción |
|---|---|
| `id` | Identificador único (`String` o `int`) |
| `name` | Nombre de la playlist |
| `description` | (opcional) |
| `mood` | Mood principal (campo independiente o derivado de las canciones) |
| Lista de ids | **Referencias** a canciones por id (no objetos `Song`) |

La playlist debe tener métodos para calcular:
- Duración total (suma de las duraciones de las canciones referenciadas)
- Número de canciones reproducibles / no reproducibles

---

## 3. Persistencia en JSON

Se gestionarán **al menos dos ficheros JSON**:

1. **`songs.json`** – Lista de canciones con todos sus campos (incluyendo, si procede, la estructura de notas).
2. **`playlists.json`** – Lista de playlists, con las referencias a canciones mediante `id`.

### 3.1. Requisitos técnicos

- Uso de una librería JSON (**Gson** o similar).
- Definir **interfaces DAO**:
  - `SongsDAO`
  - `PlaylistsDAO`
- Implementaciones concretas que trabajen con JSON:
  - `JsonSongsDAO implements SongsDAO`
  - `JsonPlaylistsDAO implements PlaylistsDAO`
- El resto de la aplicación trabajará **contra las interfaces**, no contra las clases concretas (aplicar GRASP Experto y Bajo Acoplamiento).

---

## 4. Funcionalidades mínimas

### 4.1. Menú principal (modo texto)

Aplicación de **línea de comandos** (consola) con un menú principal similar a:

```
■■■ SoundPlayer ■■■
1. Gestionar canciones
2. Gestionar playlists
3. Reproducir
4. Generar álbum random por mood
Q. Salir
```

### 4.2. Gestión de canciones

Opciones mínimas:

- **Listar canciones**
  - Mostrar: `id`, título, artista, duración, mood, style
  - Indicador `[PLAYABLE]` o `[NOT PLAYABLE]`
- **Añadir canción**
  - Pedir datos básicos (título, artista, duración, mood, estilo).
  - Permitir marcar si será reproducible o no.
  - Si es reproducible, escoger:
    - una de varias canciones predefinidas en código (p. ej. "Happy birthday"), o
    - introducir una **estructura simplificada de notas** (en el formato que diseñéis).
- **Eliminar canción** (solo si no está usada en ninguna playlist, o avisar al usuario).
- *(Opcional pero recomendable)* **Editar canción**.

Todas las operaciones deben **actualizar `songs.json`**.

### 4.3. Gestión de playlists

Opciones mínimas:

- **Crear playlist** – Pedir nombre, descripción (opcional), mood objetivo (opcional). Generar `id` automáticamente.
- **Añadir canción a playlist** – Elegir playlist; elegir canción desde el listado.
- **Eliminar canción de playlist**
- **Listar playlists** – Mostrar por playlist: nombre, número de canciones, duración total (min/s), cuántas son reproducibles.
- **Eliminar playlist**

Todas las operaciones deben **actualizar `playlists.json`**.

---

## 5. Reproducción con SoundSynth

La aplicación debe permitir:

- **Reproducir una canción**
  - Lista las canciones reproducibles.
  - El usuario elige una.
  - Se llama al sintetizador construido en clase (`SoundSynth` y sus subclases) para recorrer la secuencia de notas e invocar `makeSound(frequency, durationMs)` por cada nota.

- **Reproducir una playlist**
  - El usuario elige una playlist.
  - El sistema recorre sus canciones.
  - Solo reproduce aquellas que son `PLAYABLE`.
  - Muestra por consola qué canción se está reproduciendo.

> 💡 Se puede asumir **reproducción monofónica y sencilla** (una nota cada vez, sin acordes simultáneos).

---

## 6. Álbum random por "mood"

El usuario elige:
- Un `mood` (por ejemplo: `HAPPY`)
- Una **duración objetivo en minutos** (X minutos)

El sistema genera automáticamente una nueva playlist ("álbum") que:
- Incluye **solo canciones cuyo `mood` coincida** con el seleccionado.
- Preferiblemente, **solo reproducibles**.
- Intenta que la **suma de duraciones** sea:
  - ≥ duración objetivo
  - y que no se pase "demasiado" (podéis permitir un sobrepaso máximo de una canción).

La playlist generada debe:
- Guardarse en `playlists.json`.
- Ser visible en el listado normal de playlists, indicando que es `random` (mediante un campo o un prefijo en el nombre).

**Algoritmo sugerido (greedy):**
1. Mezclar aleatoriamente las canciones de ese mood.
2. Ir añadiendo mientras no se supere la duración objetivo + margen (por ejemplo, una canción más).

No se pide optimización perfecta, solo un algoritmo razonable y claro.

---

## 7. Arquitectura y diseño (POO)

Se espera **arquitectura por capas** y aplicación de principios GRASP.

### 7.1. Capas mínimas

| Capa | Clases ejemplo |
|---|---|
| **Model** | `Song`, `Playlist`, `NoteSequence`, `Mood` (enum)… |
| **DAO** | `SongsDAO`, `PlaylistsDAO` (interfaces) + `JsonSongsDAO`, `JsonPlaylistsDAO` |
| **Manager / Service** | `LibraryManager`, `PlaybackManager`, `AlbumGenerator` |
| **Controller** | Gestiona flujo de menús y coordina vista ↔ managers ↔ DAOs |
| **View (consola)** | Toda interacción con el usuario (`System.in` / `System.out`) |

### 7.2. Requisitos de POO

En el proyecto se deben ver claramente:

- **Encapsulamiento** – atributos privados, getters/setters cuando tenga sentido.
- **Herencia y polimorfismo** – por ejemplo:
  - `abstract class SoundSynth` y subclases `SoundSynthSinus`, `SoundSynthSquare`, etc.
  - O una jerarquía de tipos de pista para "generated tracks" vs "external tracks".
- **Interfaces** – mínimo para los DAOs y la Presentación. Podéis añadir alguna más (p. ej. `Playable`).
- **Colecciones** – `ArrayList`, `HashMap`, etc.

---

## 8. Robustez y manejo de errores

La aplicación debe:

- Controlar entradas inválidas en menús (opciones fuera de rango, letras donde espera números, etc.).
- Evitar:
  - Añadir a una playlist un id de canción inexistente.
  - Reproducir una canción que no es `PLAYABLE`.
- Manejar correctamente excepciones de I/O (lectura/escritura de JSON) con mensajes claros; no hacer `System.out.println(e.printStackTrace())` directamente en mitad de la lógica.

---

## 9. Entrega

La entrega debe incluir:

- **Código fuente completo** del proyecto (estructura de paquetes clara: `model`, `dao`, `manager`, `controller`, `view`, etc.).
- Ficheros incluidos en el proyecto:
  - **`songs.json`** – con al menos 8–10 canciones de prueba de distintos moods.
  - **`playlists.json`** – con algunas playlists de ejemplo, incluida al menos una playlist reproducible.
- **Diagrama UML de clases** – que refleje las clases principales, relaciones y jerarquías de herencia/interfaz.
- *(Opcional pero muy recomendable)* Un `README.md` con instrucciones para compilar/ejecutar, ejemplos de uso y explicación de la estructura.
- **Memoria** con los siguientes apartados:
  1. Portada
  2. Índice
  3. Resumen del enunciado
  4. Diagrama de clases UML
  5. Explicación del diagrama de clases diseñado (decisiones de diseño, patrones seguidos, ventajas/inconvenientes, cosas a mejorar, problemas encontrados).
  6. Opcionales y extras (si aplica).
  7. Pruebas realizadas.
  8. Dedicación en horas (estimación detallada).
  9. Conclusiones (personales y técnicas).
  10. Bibliografía (norma ISO 69:2010 o APA 6th).

> ⚠️ La mala redacción, faltas de ortografía y errores gramaticales pueden influir negativamente en la nota.

> 🤖 **Uso de IA:** Si utilizáis herramientas de IA para este proyecto, recordad que debéis indicarlo explícitamente según la guía académica (qué habéis pedido y cómo lo habéis usado).

---

## 10. Evaluación (orientativa)

| Criterio | Peso |
|---|---|
| Diseño orientado a objetos (encapsulamiento, herencia, polimorfismo, interfaces, GRASP) | 20 % |
| Arquitectura por capas y DAOs (separación Model/DAO/Manager/Controller/View, interfaces DAO + JSON) | 20 % |
| Persistencia y funcionalidad básica (gestión de canciones, playlists, lectura/escritura JSON) | 25 % |
| Reproducción y álbum random (sintetizador, canciones reproducibles/no, generación por mood y duración) | 25 % |
| Calidad general (legibilidad, nombres, comentarios, manejo de errores, organización, UML) | 10 % |
