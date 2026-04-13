# Sesión 14 – JSON y Gson en Java

**Fecha:** 17 de marzo de 2026

## Contenidos de la Sesión

En esta sesión se ha trabajado en grupos de 2/3 personas una presentación sobre **JSON**. Cada grupo ha expuesto los conceptos básicos del formato, su sintaxis, tipos de datos y comparación con XML. En la segunda parte de la sesión se ha creado un **miniproyecto en Java** para leer y escribir JSON usando la librería **Gson** de Google.

📁 Proyecto de la sesión: [`json-derulo/`](json-derulo/)

---

## 1. ¿Qué es JSON?

**JSON** (JavaScript Object Notation) es un formato de intercambio de datos **ligero, legible por humanos y fácil de procesar por máquinas**. Nació en el ecosistema JavaScript pero hoy es independiente del lenguaje y se usa en prácticamente todas las tecnologías modernas (APIs REST, configuraciones, persistencia local, etc.).

### 1.1. Tipos de datos en JSON

| Tipo        | Ejemplo                        | Descripción                                    |
|-------------|--------------------------------|------------------------------------------------|
| `String`    | `"Hola mundo"`                 | Cadena de texto entre comillas dobles          |
| `Number`    | `42`, `3.14`, `-7`             | Enteros y decimales (sin comillas)             |
| `Boolean`   | `true`, `false`                | Solo los literales `true` o `false`            |
| `null`      | `null`                         | Ausencia de valor                              |
| `Object`    | `{ "clave": "valor" }`         | Colección de pares clave-valor entre `{ }`     |
| `Array`     | `[1, 2, 3]`                    | Lista ordenada de valores entre `[ ]`          |

> ⚠️ En JSON las claves **siempre** van entre comillas dobles. No existe el tipo `undefined` de JavaScript.

### 1.2. Sintaxis: ejemplos correctos e incorrectos

**✅ Correcto:**
```json
{
  "nombre": "Alice",
  "edad": 20,
  "aprobado": true,
  "notas": [8.5, 7.0, 9.2],
  "direccion": null
}
```

**❌ Incorrecto** (errores comunes):
```json
{
  nombre: "Alice",          // ❌ clave sin comillas dobles
  "edad": 20,
  "aprobado": True,         // ❌ booleano con mayúscula (solo true/false)
  "notas": (8.5, 7.0),      // ❌ paréntesis en lugar de corchetes
  "extra": undefined        // ❌ undefined no existe en JSON
}
```

---

## 2. Ejemplo Específico: Alumno/s

### 2.1. Un único alumno (`alumno.json`)

📁 [`json-derulo/alumno.json`](json-derulo/alumno.json)

```json
{
  "nombre": "Alice",
  "edad": 20,
  "aprobado": true
}
```

### 2.2. Array de alumnos con asignaturas (`alumnos.json`)

📁 [`json-derulo/alumnos.json`](json-derulo/alumnos.json)

```json
[
  {
    "nombre": "Alice",
    "edad": 20,
    "aprobado": true
  },
  {
    "nombre": "Bob",
    "edad": 20,
    "aprobado": false
  },
  {
    "nombre": "Charlie",
    "edad": 80,
    "aprobado": true
  },
  {
    "nombre": "Dave",
    "edad": 3,
    "aprobado": true
  }
]
```

---

## 3. Serialización y Deserialización

| Concepto            | Definición                                                                 | Dirección          |
|---------------------|----------------------------------------------------------------------------|--------------------|
| **Serialización**   | Convertir un **objeto Java** en una **cadena JSON** (o fichero JSON)       | Java → JSON        |
| **Deserialización** | Convertir una **cadena JSON** (o fichero JSON) en un **objeto Java**       | JSON → Java        |

```
┌──────────────┐   serialización    ┌──────────────┐
│  Objeto Java │  ──────────────►  │  Texto JSON  │
│  (Alumno)    │  ◄──────────────  │  (fichero)   │
└──────────────┘  deserialización   └──────────────┘
```

### 3.1. ¿Para qué sirven?

- **Persistencia local**: guardar el estado de un objeto en disco y recuperarlo más tarde.
- **Comunicación en red**: enviar objetos entre cliente y servidor a través de una API REST.
- **Configuración**: leer parámetros de configuración desde un archivo `.json`.

---

## 4. JSON vs XML

**XML** (eXtensible Markup Language) fue durante años el estándar de facto para el intercambio de datos. JSON lo ha superado en popularidad para APIs web, aunque XML sigue siendo habitual en entornos empresariales y de configuración (Maven, Android Manifest, etc.).

### El mismo alumno en ambos formatos

**JSON:**
```json
{
  "nombre": "Alice",
  "edad": 20,
  "aprobado": true,
  "asignaturas": ["Matemáticas", "Física", "PEI"]
}
```

**XML:**
```xml
<alumno>
  <nombre>Alice</nombre>
  <edad>20</edad>
  <aprobado>true</aprobado>
  <asignaturas>
    <asignatura>Matemáticas</asignatura>
    <asignatura>Física</asignatura>
    <asignatura>PEI</asignatura>
  </asignaturas>
</alumno>
```

### Diferencias clave

| Aspecto             | JSON                              | XML                                   |
|---------------------|-----------------------------------|---------------------------------------|
| **Verbosidad**      | Más conciso                       | Más verboso (etiquetas de apertura y cierre) |
| **Legibilidad**     | Más fácil de leer para humanos    | Más difícil en estructuras complejas  |
| **Tipos nativos**   | ✅ Soporta booleanos, números, null | ❌ Todo es texto; los tipos hay que interpretarlos |
| **Arrays**          | `[ ]` nativos                     | Hay que repetir el tag padre          |
| **Comentarios**     | ❌ No soporta comentarios          | ✅ Soporta `<!-- comentarios -->`      |
| **Esquema / validación** | JSON Schema (opcional)      | XSD (maduro y extendido)              |
| **Popularidad hoy** | API REST, aplicaciones web        | Sistemas empresariales, configuración |

---

## 5. Trabajar con JSON en Java: Librería Gson

**Gson** es una librería de Google que permite **serializar y deserializar** objetos Java a/desde JSON con muy pocas líneas de código.

### 5.1. Añadir Gson al proyecto

Para un proyecto IntelliJ sin Maven/Gradle, se descarga el `.jar` y se añade al classpath:

📁 [`gson-2.13.2.jar`](../gson-2.13.2.jar) (disponible en la raíz de `session14/`)

### 5.2. La clase `Alumno`

📁 [`json-derulo/src/Alumno.java`](json-derulo/src/Alumno.java)

```java
public class Alumno {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED   = "\u001B[31m";

    private String nombre;
    private int    edad;
    private boolean aprobado;

    // Constructor para crear alumnos desde código
    public Alumno(String nombre, int edad, boolean aprobado) {
        this.nombre   = nombre;
        this.edad     = edad;
        this.aprobado = aprobado;
    }

    @Override
    public String toString() {
        return colorAprobado() + "mi nombre es: " + nombre
                + " y tengo " + edad + " años y " + estoyAprobado() + ANSI_RESET;
    }

    private String estoyAprobado() {
        return aprobado ? "estoy aprobado" : "no estoy aprobado";
    }

    private String colorAprobado() {
        return aprobado ? ANSI_GREEN : ANSI_RED;
    }
}
```

> 💡 Gson utiliza los **nombres de los campos** de la clase para mapear las propiedades del JSON. No hace falta ninguna anotación especial si los nombres coinciden.

### 5.3. Deserializar un único objeto

```java
Gson gson = new Gson();
JsonReader reader = new JsonReader(new FileReader("alumno.json"));
Alumno a = gson.fromJson(reader, Alumno.class);
System.out.println(a);
```

### 5.4. Deserializar un array de objetos

```java
Gson gson = new Gson();
JsonReader reader = new JsonReader(new FileReader("alumnos.json"));
Alumno[] alumnos = gson.fromJson(reader, Alumno[].class);
for (Alumno a : alumnos) {
    System.out.println(a);
}
```

---

## 6. Miniproyecto: `json-derulo`

📁 Proyecto completo: [`json-derulo/`](json-derulo/)

### 6.1. Leer un array de alumnos (hecho en clase)

```java
Alumno[] aA = gson.fromJson(reader, Alumno[].class);
for (Alumno a : aA) {
    System.out.println(a);
}
```

### 6.2. ✅ Crear un alumno nuevo y añadirlo al array

```java
// Convertir el array a lista para poder añadir
List<Alumno> lista = new ArrayList<>(Arrays.asList(aA));

// Crear un nuevo alumno desde código
Alumno nuevoAlumno = new Alumno("Eva", 22, true);
lista.add(nuevoAlumno);

// Mostrar el resultado por consola
for (Alumno a : lista) {
    System.out.println(a);
}
```

### 6.3. ✅ Crear un alumno desde Scanner y añadirlo

```java
Scanner sc = new Scanner(System.in);

System.out.print("Nombre: ");
String nombre = sc.nextLine();

System.out.print("Edad: ");
int edad = Integer.parseInt(sc.nextLine());

System.out.print("¿Aprobado? (true/false): ");
boolean aprobado = Boolean.parseBoolean(sc.nextLine());

Alumno alumnoScanner = new Alumno(nombre, edad, aprobado);
lista.add(alumnoScanner);
```

### 6.4. ✅ Guardar el array de alumnos en un nuevo fichero JSON

```java
String jsonOutput = gson.toJson(lista);
FileWriter writer = new FileWriter("alumnos_updated.json");
writer.write(jsonOutput);
writer.close();
System.out.println("Fichero guardado: alumnos_updated.json");
```

### 6.5. Código completo (`Main.java`)

📁 [`json-derulo/src/Main.java`](json-derulo/src/Main.java)

```java
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Gson gson = new Gson();
        String FILENAME_ARR = "alumnos.json";

        try {
            // 1. Leer el array de alumnos desde el fichero
            JsonReader reader = new JsonReader(new FileReader(FILENAME_ARR));
            Alumno[] aA = gson.fromJson(reader, Alumno[].class);

            System.out.println("=== Alumnos leídos del fichero ===");
            for (Alumno a : aA) {
                System.out.println(a);
            }

            // Convertir a lista para poder añadir elementos
            List<Alumno> lista = new ArrayList<>(Arrays.asList(aA));

            // 2. Crear un alumno nuevo desde código y añadirlo
            Alumno nuevoAlumno = new Alumno("Eva", 22, true);
            lista.add(nuevoAlumno);

            System.out.println("\n=== Lista tras añadir Eva ===");
            for (Alumno a : lista) {
                System.out.println(a);
            }

            // 3. Crear un alumno desde datos del usuario (Scanner)
            Scanner sc = new Scanner(System.in);

            System.out.print("\nNombre del nuevo alumno: ");
            String nombre = sc.nextLine();

            System.out.print("Edad: ");
            int edad = Integer.parseInt(sc.nextLine());

            System.out.print("¿Aprobado? (true/false): ");
            boolean aprobado = Boolean.parseBoolean(sc.nextLine());

            Alumno alumnoScanner = new Alumno(nombre, edad, aprobado);
            lista.add(alumnoScanner);

            System.out.println("\n=== Lista final ===");
            for (Alumno a : lista) {
                System.out.println(a);
            }

            // 4. Guardar la lista actualizada en un nuevo fichero JSON
            String jsonOutput = gson.toJson(lista);
            FileWriter fw = new FileWriter("alumnos_updated.json");
            fw.write(jsonOutput);
            fw.close();
            System.out.println("\nFichero guardado: alumnos_updated.json");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
```

---

## 7. Resumen de Métodos Gson

| Método                                       | Qué hace                                                   |
|----------------------------------------------|------------------------------------------------------------|
| `gson.fromJson(reader, Alumno.class)`        | Deserializa JSON → objeto `Alumno`                        |
| `gson.fromJson(reader, Alumno[].class)`      | Deserializa JSON → array de `Alumno`                      |
| `gson.toJson(objeto)`                        | Serializa objeto Java → cadena JSON                       |
| `gson.toJson(lista)`                         | Serializa `List<Alumno>` → cadena JSON (array JSON)       |
| `new JsonReader(new FileReader("f.json"))`   | Abre un fichero para leerlo con Gson                      |
| `new FileWriter("f.json")`                   | Abre (o crea) un fichero para escribir texto              |

---

## 8. Buenas Prácticas

1. **Cierra siempre los recursos** (`FileReader`, `FileWriter`) al terminar. Usa `try-with-resources` o ciérralos explícitamente en `finally`.
2. **Nombres de campos coincidentes**: Gson mapea por nombre, así que el nombre del campo Java debe coincidir con la clave del JSON. Si no coinciden, usa la anotación `@SerializedName("nombre_en_json")`.
3. **Gson es hilo-seguro** para `toJson`/`fromJson`: puedes reutilizar la instancia `Gson` en toda la aplicación.
4. **No serialices datos sensibles** (contraseñas, tokens): Gson serializa **todos** los campos por defecto. Usa `transient` para excluir un campo.

---

## Recursos

- 🔗 [Gson – GitHub oficial (google/gson)](https://github.com/google/gson)
- 🔗 [Gson User Guide](https://github.com/google/gson/blob/main/UserGuide.md)
- 🔗 [JSON.org – Especificación oficial](https://www.json.org/json-es.html)
- 🔗 [JSON Schema](https://json-schema.org/)
- 🔗 [Introducción a XML (MDN)](https://developer.mozilla.org/es/docs/Web/XML/XML_introduction)
- [Sesión 13 – Proyecto Final: laSallefy](../session13/README.md)

