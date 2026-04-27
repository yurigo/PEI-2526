# PEI-2526

## Sobre la Asignatura

**Programación para Entornos Interactivos (PEI)** es una asignatura fundamental que introduce los conceptos básicos de la programación orientada a objetos utilizando Java. En este repositorio encontrarás todos los apuntes y proyectos de cada sesión, desde los fundamentos del lenguaje hasta los principios clave de la POO (Programación Orientada a Objetos).

---

## Sesiones Completadas

| #   | Sesión                                                     | Temas                                                                       | Proyecto                                                                   |
| --- | ---------------------------------------------------------- | --------------------------------------------------------------------------- | -------------------------------------------------------------------------- |
| 1   | [Sesión 01 - Introducción a Java](session01/)              | Conceptos básicos de Java, instalación de IDE, primer programa "Hola Mundo" | [calculadora](session01/calculadora/), [helloworld](session01/helloworld/) |
| 2   | [Sesión 02 - Programación Orientada a Objetos](session02/) | Clases y objetos, atributos, métodos, encapsulamiento, constructores        | [gimnasio](session02/gimnasio/)                                            |
| 3   | [Sesión 03 - Encapsulamiento y Principios de Diseño](session03/) | Modificadores de acceso, getters/setters, principios GRASP, static, toString() | [pokedex](session03/pokedex/), [gimnasio](session03/gimnasio/), [mercado](session03/mercado/) |
| 4   | [Sesión 04 - Colecciones y Lectura de Archivos](session04/) | ArrayList, FileReader, BufferedReader, try/catch, manejo de excepciones | [mercado-con-arraylist](session04/mercado-con-arraylist/), [mercado-con-ficheros](session04/mercado-con-ficheros/) |
| 5   | [Sesión 05 - AC1: Sensors Biomètrics](session05/) | Primera actividad práctica: aplicación de consola con menú, procesamiento de sensores biomètricos, lectura/escritura CSV | AC1: Sistema de gestión de medidas de sensores |
| 6   | [Sesión 06 - Relaciones entre Clases](session06/) | Relaciones UML: Dependencia, Asociación, Agregación, Composición, Cardinalidad, Direccionalidad | [Ejercicios UML](session06/ejercicios/): UMLtoJAVA, TEXTtoUML, Questions |
| 7   | [Sesión 07 - Herencia y Polimorfismo](session07/) | Repaso relaciones, Herencia, Casting, instanceof, Polimorfismo, Sobrecarga, Clases Abstractas | [aggregation example](session07/aggregation%20example/), [composition example](session07/composition%20example/), [zoo example](session07/zoo%20example/) |
| 8   | [Sesión 08 - Herencia, Visibilidad y Polimorfismo](session08/) | Herencia en cadena, Modificadores de acceso (private/public/protected/package), Polimorfismo vertical (sobrescritura), Polimorfismo horizontal (sobrecarga) | [area-perimeter-calculator](session08/area-perimeter-calculator/) |
| 9   | [Sesión 09 - Interfaces en Java](session09/) | Interfaces (`interface`/`implements`), separación qué/cómo, patrón DAO, `List` como interfaz, `ArrayList`/`Vector`/`MySuperList`, principios OCP y DIP | [interface-examples](session09/interface-examples/) |
| 10  | [Sesión 10 - Herencia Múltiple, Interfaces y Arquitectura en Capas](session10/) | Problema del diamante, interfaz `Volador`, clases abstractas `Animal`/`Vehiculo`, patrón Controller, inyección de dependencias, arquitectura en capas | [partial-solution-sensors-biometrics](session10/partial-solution-sensors-biometrics/) |
| 11  | [Sesión 11 - Excepciones en Java](session11/) | Concepto de excepción, jerarquía `Throwable`, `try`/`catch`/`finally`, `RuntimeException`, valor centinela vs bucle de reintento, `throw`, limpieza del buffer del `Scanner` | [partial-solution-sensors-biometrics](session11/partial-solution-sensors-biometrics/) |
| 12  | [Sesión 12 - Excepciones Propias en Java](session12/) | Excepciones checked vs unchecked, crear excepciones propias extendiendo `Exception`/`RuntimeException`, encapsular el mensaje de error, jerarquías de excepciones, orden de `catch` | [exceptional-project](session12/exceptional-project/) |
| 13  | [Sesión 13 - Proyecto Final: laSallefy](session13/) | Revisión del SoundSynth (2 clases + enum), diagrama UML en clase, refactorización a clase abstracta + subclases (OCP), enum `Notes`, persistencia JSON con Gson, arquitectura en capas del proyecto final | [ENUNCIADO.md](session13/ENUNCIADO.md), [sound-synth-example](https://github.com/yurigo/sound-synth-example) |
| 14  | [Sesión 14 - JSON y Gson en Java](session14/) | Tipos de datos JSON, sintaxis correcta/incorrecta, serialización y deserialización, JSON vs XML, lectura/escritura con Gson, miniproyecto `json-derulo` | [json-derulo](session14/json-derulo/) |
| 15  | [Sesión 15 - Diagrama de Clases de laSallefy](session15/) | Actividad: diseño a mano del diagrama de clases del proyecto final; guía de relaciones UML (composición, agregación, herencia, realización), modelo de dominio completo | – |
| 16  | [Sesión 16 - Modelo de Negocio laSallefy: JSON y UML](session16/) | Diagrama de clases en pizarra (Song, Sound, Playlist, enums), relaciones composición/agregación, implementación `songs.json` y `playlists.json`, patrón DAO con Gson | [songs.json](session16/songs.json), [playlists.json](session16/playlists.json) |
| 17  | [Sesión 17 - Threads en Java](session17/) | Flipped classroom: qué es un Thread, `extends Thread` vs `implements Runnable`, condiciones de carrera, `synchronized`, `join()`, ejercicio reproductor de música en segundo plano | [thread-examples](session17/thread-examples/) |

---

## Evaluación – Fechas Importantes

| Evento                          | Fecha                         | Hora        |
| ------------------------------- | ----------------------------- | ----------- |
| 📝 Examen teórico final ordinario | 27 de mayo de 2026           | 10:00       |
| 📦 Entrega del Proyecto           | 28 de mayo de 2026           | 23:59       |
| 🎤 Entrevista del Proyecto        | 9 de junio de 2026           | 10:00       |

---

## Actividades y Proyecto

| #         | Enunciado                                                                     | Descripción                                                                                   |
| --------- | ----------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------- |
| **AC1**   | [Enunciado AC1 – Sensors Biomètrics](session05/README.md)                     | Aplicación de consola con menú, lectura/escritura CSV, clase `Mesura` con id autoincremental  |
| **AC2**   | [Enunciado AC2 – Arquitectura en Capas](session10/ACTIVIDAD.md)               | Refactor de AC1 a arquitectura en capas: Model, DAO, Controller, Vista                        |
| **PROYECTO** | [Enunciado del Proyecto Final – laSallefy](session13/ENUNCIADO.md)         | Proyecto final integrador: reproductor musical con SoundSynth, playlists, persistencia JSON y arquitectura en capas |

---

## Estructura del Repositorio

```
PEI-2526/
├── README.md
├── session01/          # Introducción a Java
│   ├── README.md
│   ├── calculadora/    # Proyecto: Calculadora básica
│   └── helloworld/     # Proyecto: Programa Hola Mundo
├── session02/          # Programación Orientada a Objetos
│   ├── README.md
│   └── Gimnasio/       # Proyecto: Sistema de gestión de gimnasio
├── session03/          # Encapsulamiento y Principios de Diseño
│   ├── README.md
│   ├── pokedex/        # Proyecto: Gestión de Pokémon con encapsulamiento
│   ├── gimnasio/       # Proyecto: Gimnasio mejorado con bajo acoplamiento
│   └── mercado/        # Proyecto: Sistema de productos con static
├── session04/          # Colecciones y Lectura de Archivos
│   ├── README.md
│   ├── mercado-con-arraylist/  # Proyecto: Productos con ArrayList
│   └── mercado-con-ficheros/   # Proyecto: Lectura de productos desde CSV
├── session05/          # AC1: Sensors Biomètrics
│   └── README.md       # Enunciado de la primera actividad práctica (AC1)
└── session06/          # Relaciones entre Clases
    ├── README.md       # Teoría completa sobre relaciones UML
    └── ejercicios/     # Ejercicios prácticos
        ├── UMLtoJAVA/       # Traducción de UML a código Java
        ├── TEXTtoUML/       # Diseño de diagramas desde texto
        └── questions/       # Ejercicios de examen resueltos
└── session07/          # Herencia y Polimorfismo
    ├── README.md                # Teoría completa sobre herencia y polimorfismo
    ├── aggregation example/     # Ejemplo: Playlist-Song (Agregación)
    ├── composition example/     # Ejemplo: Chessboard-Cell (Composición)
    ├── zoo example/             # Ejemplo: Zoo con herencia, polimorfismo y clases abstractas
    └── ejercicios/
        └── TEXTtoUML/           # DIABLO XII y FARM SIMULATOR con herencia
└── session08/          # Herencia, Visibilidad y Polimorfismo
    ├── README.md                # Teoría completa sobre visibilidad y polimorfismo vertical/horizontal
    └── area-perimeter-calculator/ # Ejemplo: Forma abstracta con Rectangulo, Cuadrado, Triangulo, Circulo
└── session09/          # Interfaces en Java
    ├── README.md                # Teoría completa sobre interfaces, patrón DAO y principios OCP/DIP
    └── interface-examples/      # Ejemplo: MenuInterface con MenuConsole, MenuColor, MenuWindows, MySuperList
└── session10/          # Herencia Múltiple, Interfaces y Arquitectura en Capas
    ├── README.md                # Teoría completa: herencia múltiple, interfaz Volador, Controller, inyección de dependencias
    ├── ACTIVIDAD.md             # AC2: Enunciado del refactor de Sensors Biométrics (arquitectura en capas)
    └── partial-solution-sensors-biometrics/  # Código base para la actividad AC2
        └── src/                 # Main, Controller, Medida, Menu, MenuConsole, MenuConsoleSuperior, DAOMedida, DAOMedidaCSVFile
└── session11/          # Excepciones en Java
    ├── README.md                # Teoría completa: try/catch/finally, RuntimeException, valor centinela vs bucle de reintento
    └── partial-solution-sensors-biometrics/  # Boilerplate AC2 con manejo de excepciones en getInteger y Controller
        └── src/                 # Main, Controller, Medida, Menu, MenuConsole, MenuConsoleSuperior, DAOMedida, DAOMedidaCSVFile
└── session12/          # Excepciones Propias en Java
    ├── README.md                # Teoría completa: checked vs unchecked, jerarquías, encapsulación de mensajes
    └── exceptional-project/     # Ejemplo: MyExceptionalException, MoreThanTenException, LessThanZeroException, NumberFiveException
└── session13/          # Proyecto Final: laSallefy
    ├── README.md                # Teoría: SoundSynth abstracta, refactorización enum→subclases, Gson/JSON, arquitectura laSallefy
    ├── ENUNCIADO.md             # Enunciado completo del proyecto final (generado desde HTML del campus)
    └── ENUNCIADO.html           # Enunciado original en HTML del campus eStudy
└── session14/          # JSON y Gson en Java
    ├── README.md                # Teoría completa: tipos JSON, sintaxis, serialización/deserialización, XML vs JSON, Gson
    ├── gson-2.13.2.jar          # Librería Gson (añadir al classpath del proyecto)
    └── json-derulo/             # Miniproyecto: lectura, creación y escritura de alumnos con Gson
        ├── alumno.json          # JSON con un único alumno
        ├── alumnos.json         # JSON con array de alumnos
        └── src/                 # Alumno.java (modelo) y Main.java (lectura/creación/escritura)
└── session15/          # Diagrama de Clases de laSallefy
    └── README.md                # Guía completa del diagrama UML: modelo, DAO, SoundSynth, relaciones, criterios
└── session16/          # Modelo de Negocio laSallefy: JSON y UML
    ├── README.md                # Diagrama pizarra, composición Song↔Sound, agregación Playlist↔Song, DAO con Gson
    ├── songs.json               # 10 canciones de prueba de distintos moods (ejemplo para el proyecto)
    └── playlists.json           # 4 playlists de ejemplo incluyendo una generada aleatoriamente
└── session17/          # Threads en Java
    ├── README.md                # Teoría completa: Thread, Runnable, race conditions, synchronized, join, ejercicio laSallefy
    └── thread-examples/         # Ejemplos progresivos de Threads
        ├── 01-extends-thread/   # Ejemplo: crear Thread extendiendo la clase Thread
        ├── 02-implements-runnable/  # Ejemplo: crear Thread con Runnable y lambdas
        ├── 03-race-condition/   # Ejemplo: condición de carrera sin sincronización
        └── 04-synchronized/     # Ejemplo: solución con synchronized y join()
```
