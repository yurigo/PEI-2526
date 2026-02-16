# Sesión 05 - Actividad 1: Sensors Biomètrics

**Fecha:** 16 de febrero de 2026

## Contenidos de la Sesión

En esta sesión se ha presentado el enunciado de la primera actividad del curso, donde aplicaremos los conocimientos adquiridos en las sesiones anteriores sobre clases, objetos, colecciones y manejo de archivos CSV.

## Enunciado de la Actividad

### AC1 – Sensors Biomètrics

#### Processament de mesures de sensors biomètrics

En un laboratori s'estan recollint mesures de sensors biomètrics (per exemple, **temperatura**, **pols**, **SpO₂**). Disposem d'un fitxer `mesures.csv` amb dades inicials que volem **llegir, llistar, representar en consola** i **ampliar** amb noves mesures introduïdes per teclat.

Implementaràs la classe `Mesura` i una aplicació de consola amb menú.

### 1) Classe `Mesura`

#### Atributs mínims:

- `id` (enter) → **autoincremental** i **únic** per instància.
- `tipus` (String) → p. ex. `TEMPERATURA`, `POLS`, `SPO2`.
- `valor` (double) → p. ex. 36.7, 72.0, 97.0.
- `unitat` (String) → p. ex. `ºC`, `bpm`, `%`.
- `instant` (String) → format simple ISO, p. ex. `2025-10-01 10:30`.

#### Requisits:

- Control d'**id autoincremental** amb un **camp de classe** (`static`).
- **Dos constructors**:
  - Amb paràmetres (`tipus, valor, unitat, instant`).
  - Que permeti construir a partir d'una línia del CSV (ignorant les línies que comencin per `#`).
- Mètodes bàsics (`toString()`, getters/setters amb validacions).

### 2) Aplicació de consola (menú)

En engegar, el sistema carrega `mesures.csv` i mostra:

```
■■■ Sensors ■■■
1. Llistar mesures
2. Representació ASCII
3. Afegir mesura
4. Buscar per tipus
5. Sortir

Escull una opció:
```

#### Opcions

**1. Llistar mesures**

Mostra totes les mesures amb format:

```
■ <id>: <tipus> = <valor><unitat> @ <instant>
```

**2. Representació ASCII (resum per tipus)**

- Es mostra un "gràfic de barres" per cada tipus de sensor.
- Es pot triar estratègia:
  - Últim valor registrat, o
  - Mitjana de totes les mesures d'aquell tipus.
- Escala senzilla:
  - TEMPERATURA (35–40 ºC → cada 0.5 ºC = "#")
  - POLS (40–180 bpm → cada 10 bpm = "#")
  - SPO2 (80–100 % → cada 2 % = "#")
- Exemple:

```
T: #### (36.8 ºC)
P: ### (68.5 bpm)
S: ######### (96 %)
```

**3. Afegir mesura**

- Demana dades per teclat (tipus, valor, unitat, instant).
- Valida: tipus vàlid (`TEMPERATURA|POLS|SPO2`), valor numèric, unitat coherent, instant amb format plausible.
- Assigna id automàtic i guarda al CSV.

**4. Buscar per tipus**

- Demana un tipus i mostra **totes les mesures** d'aquell tipus.
- Exemple:

```
Tipus: TEMPERATURA
■ 1: TEMPERATURA = 36.7ºC @ 2025-09-30 10:15
■ 4: TEMPERATURA = 37.2ºC @ 2025-09-30 11:00
■ 7: TEMPERATURA = 36.5ºC @ 2025-09-30 12:20
```

**5. Sortir**

- Finalitza l'aplicació.

### 3) Fitxer `mesures.csv`

Format:

```
id,tipus,valor,unitat,instant
```

Exemple inicial:

```
# id,tipus,valor,unitat,instant
1,TEMPERATURA,36.7,ºC,2025-09-30 10:15
2,POLS,72,bpm,2025-09-30 10:16
3,SPO2,97,%,2025-09-30 10:17
# aqui hi ha un comentari
4,TEMPERATURA,37.2,ºC,2025-09-30 11:00
5,POLS,65,bpm,2025-09-30 11:05
6,SPO2,95,%,2025-09-30 11:10
7,TEMPERATURA,36.5,ºC,2025-09-30 12:20
```

### 4) Lliurament

**Data límit**: 1/03/2025 a les 23:55.

Entrega un ZIP amb el projecte IntelliJ que contindrà, com a mínim:

1. `Mesura.java` i `Main.java`.
2. Fitxer `mesures.csv` (dades inicials + noves).
3. Diagrama UML de la classe `Mesura`.
4. README amb breu explicació de decisions (estratègia ASCII, validacions, etc.).

## Conceptos Aplicados

Esta actividad integra los siguientes conceptos aprendidos en sesiones anteriores:

### Sesión 02 - Programación Orientada a Objetos
- ✅ Creación de la clase `Mesura` con atributos y métodos
- ✅ Uso de constructores múltiples
- ✅ Encapsulamiento con getters y setters

### Sesión 03 - Encapsulamiento y Principios de Diseño
- ✅ Atributo estático (`static`) para el contador de IDs
- ✅ Implementación del método `toString()`
- ✅ Validaciones en setters

### Sesión 04 - Colecciones y Lectura de Archivos
- ✅ Uso de `ArrayList` para almacenar múltiples medidas
- ✅ Lectura de archivo CSV con `FileReader` y `BufferedReader`
- ✅ Manejo de excepciones con `try/catch`
- ✅ Procesamiento de líneas CSV con `split()`
- ✅ Escritura de datos al archivo CSV

## Consejos para la Implementación

### 1. Estructura del Proyecto

```
AC1-Sensors/
├── src/
│   ├── Mesura.java      # Clase principal
│   └── Main.java        # Aplicación con menú
├── assets/
│   └── mesures.csv      # Archivo de datos
├── docs/
│   └── diagrama-uml.png # Diagrama UML
└── README.md            # Documentación
```

### 2. Validaciones Importantes

**Tipo de sensor:**
```java
public static boolean esTipusValid(String tipus) {
    return tipus.equals("TEMPERATURA") || 
           tipus.equals("POLS") || 
           tipus.equals("SPO2");
}
```

**Unidad coherente con el tipo:**
```java
public static boolean esUnitatCoherent(String tipus, String unitat) {
    if (tipus.equals("TEMPERATURA")) return unitat.equals("ºC");
    if (tipus.equals("POLS")) return unitat.equals("bpm");
    if (tipus.equals("SPO2")) return unitat.equals("%");
    return false;
}
```

### 3. Constructor desde CSV

```java
// Constructor que parsea una línea CSV
public Mesura(String liniaCsv) {
    // Ignorar líneas de comentario
    if (liniaCsv.startsWith("#")) {
        return;
    }
    
    String[] parts = liniaCsv.split(",");
    // parts[0] = id (ignorar, se asignará automáticamente)
    this.tipus = parts[1];
    this.valor = Double.parseDouble(parts[2]);
    this.unitat = parts[3];
    this.instant = parts[4];
    // El id se asigna automáticamente
}
```

### 4. Representación ASCII

Para calcular el número de caracteres "#" en la representación ASCII:

```java
public static int calcularBarres(String tipus, double valor) {
    if (tipus.equals("TEMPERATURA")) {
        // Rango: 35-40ºC, cada 0.5ºC = 1 barra
        return (int)((valor - 35.0) / 0.5);
    } else if (tipus.equals("POLS")) {
        // Rango: 40-180 bpm, cada 10 bpm = 1 barra
        return (int)((valor - 40.0) / 10.0);
    } else if (tipus.equals("SPO2")) {
        // Rango: 80-100%, cada 2% = 1 barra
        return (int)((valor - 80.0) / 2.0);
    }
    return 0;
}
```

### 5. Guardar al CSV

Después de añadir una nueva medida:

```java
public static void guardarMesures(ArrayList<Mesura> mesures, String nomFitxer) {
    try {
        FileWriter fw = new FileWriter(nomFitxer);
        BufferedWriter bw = new BufferedWriter(fw);
        
        // Escribir encabezado
        bw.write("id,tipus,valor,unitat,instant");
        bw.newLine();
        
        // Escribir cada medida
        for (Mesura m : mesures) {
            bw.write(m.toCSV());
            bw.newLine();
        }
        
        bw.close();
    } catch (IOException e) {
        System.out.println("Error al guardar el archivo");
    }
}
```

## Criterios de Evaluación

- ✅ **Clase `Mesura` bien diseñada**: Atributos correctos, constructores funcionales, métodos útiles
- ✅ **ID autoincremental**: Uso correcto de atributo `static`
- ✅ **Lectura de CSV**: Correcta lectura y parseo del archivo
- ✅ **Validaciones**: Tipo, unidad coherente, valores numéricos
- ✅ **Menú funcional**: Todas las opciones implementadas correctamente
- ✅ **Representación ASCII**: Cálculo correcto de barras según el tipo
- ✅ **Escritura de CSV**: Guardar nuevas medidas correctamente
- ✅ **Manejo de errores**: try/catch apropiados
- ✅ **Código limpio**: Nombres descriptivos, comentarios, buena estructura
- ✅ **Documentación**: README explicativo y diagrama UML claro

## Recursos Adicionales

- [ArrayList en Java](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html)
- [Lectura de archivos](https://docs.oracle.com/javase/tutorial/essential/io/file.html)
- [Scanner para entrada de usuario](https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html)
- [String.split() para parsear CSV](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#split-java.lang.String-)

## Resumen

En esta actividad pondrás en práctica:

- ✅ Diseño de clases con atributos estáticos
- ✅ Múltiples constructores
- ✅ Lectura y escritura de archivos CSV
- ✅ Uso de ArrayList para colecciones
- ✅ Validación de datos de entrada
- ✅ Manejo de excepciones
- ✅ Interfaz de consola con menú
- ✅ Representación de datos de forma visual

¡Buena suerte con la actividad! 🚀
