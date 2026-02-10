# Sesión 04 - Colecciones y Lectura de Archivos

**Fecha:** 10 de febrero de 2026

## Contenidos de la Sesión

En esta sesión hemos aprendido conceptos esenciales para trabajar con colecciones de datos y lectura de archivos en Java:

### 1. Colecciones en Java: ArrayList

**ArrayList** es una de las estructuras de datos más importantes en Java. Es una colección dinámica que permite almacenar múltiples elementos del mismo tipo sin necesidad de especificar un tamaño fijo como los arrays tradicionales.

#### Arrays vs ArrayList

**Arrays tradicionales** (tamaño fijo):
```java
// Debemos especificar el tamaño al crear el array
Producto[] parray = new Producto[4];

// Solo podemos almacenar 4 productos
parray[0] = new Producto("Producto1", "Desc1", 10.0F, 100, null);
parray[1] = new Producto("Producto2", "Desc2", 20.0F, 200, null);
// Si intentamos añadir más de 4, obtendremos un error
```

**ArrayList** (tamaño dinámico):
```java
// No necesitamos especificar el tamaño
ArrayList<Producto> productos = new ArrayList<Producto>();

// Podemos añadir tantos productos como queramos
productos.add(new Producto("Producto1", "Desc1", 10.0F, 100, null));
productos.add(new Producto("Producto2", "Desc2", 20.0F, 200, null));
productos.add(new Producto("Producto3", "Desc3", 30.0F, 300, null));
// El ArrayList crece automáticamente según nuestras necesidades
```

#### Operaciones Básicas con ArrayList

```java
import java.util.ArrayList;

// 1. Crear un ArrayList
ArrayList<Producto> productos = new ArrayList<Producto>();

// 2. Añadir elementos
productos.add(producto1);
productos.add(producto2);

// 3. Obtener el tamaño (número de elementos)
int cantidad = productos.size();

// 4. Acceder a un elemento por índice (como un array)
Producto primerProducto = productos.get(0);

// 5. Recorrer todos los elementos
for (int i = 0; i < productos.size(); i++) {
    System.out.println(productos.get(i));
}
```

**Ventajas de ArrayList:**
- ✅ Tamaño dinámico: crece automáticamente cuando añadimos elementos
- ✅ Métodos útiles: `add()`, `get()`, `size()`, `remove()`, etc.
- ✅ Más flexible que los arrays tradicionales
- ✅ Parte del Java Collections Framework

**Cuándo usar ArrayList:**
- Cuando no sabemos cuántos elementos vamos a almacenar
- Cuando necesitamos añadir o eliminar elementos dinámicamente
- Cuando queremos aprovechar los métodos de la clase ArrayList

### 2. Lectura de Archivos con FileReader y BufferedReader

Java proporciona clases para leer archivos de texto de forma eficiente. La combinación de **FileReader** y **BufferedReader** es la forma estándar de leer archivos línea por línea.

#### FileReader

`FileReader` es una clase que permite leer archivos de texto carácter por carácter. Sin embargo, leer carácter por carácter es ineficiente, por eso se suele combinar con `BufferedReader`.

```java
import java.io.FileReader;

FileReader fileReader = new FileReader("archivo.txt");
```

#### BufferedReader

`BufferedReader` envuelve un `FileReader` y proporciona un buffer (memoria intermedia) que hace la lectura más eficiente. Además, ofrece el método `readLine()` para leer líneas completas.

```java
import java.io.BufferedReader;
import java.io.FileReader;

FileReader fileReader = new FileReader("archivo.txt");
BufferedReader bufferedReader = new BufferedReader(fileReader);
```

#### Lectura Línea por Línea

```java
String linea;
while ((linea = bufferedReader.readLine()) != null) {
    // Procesar cada línea
    System.out.println(linea);
}
```

**¿Cómo funciona?**
- `bufferedReader.readLine()`: Lee una línea completa del archivo
- Retorna `null` cuando llega al final del archivo
- El bucle `while` continúa hasta que no hay más líneas

#### Procesamiento de Archivos CSV

CSV (Comma-Separated Values) es un formato común para almacenar datos tabulares. En nuestro caso, usamos punto y coma (`;`) como separador:

```java
// Archivo productos.csv:
// nombrea;descripcion puede tener espacios;10.1;10;null
// nombreb;descripcion puede tener espacios;10.2;10;null

String linea;
while ((linea = bufferedReader.readLine()) != null) {
    // Dividir la línea en pedazos usando el separador ';'
    String[] pedazos = linea.split(";");
    
    // pedazos[0] = nombre
    // pedazos[1] = descripcion
    // pedazos[2] = precio (como String)
    // pedazos[3] = stock (como String)
    
    // Crear un producto con los datos leídos
    Producto aux = new Producto(
        pedazos[0],                      // nombre
        pedazos[1],                      // descripcion
        Float.parseFloat(pedazos[2]),    // convertir String a Float
        Integer.parseInt(pedazos[3]),    // convertir String a Integer
        null
    );
    
    productos.add(aux);
}
```

**Conversión de tipos:**
- `Float.parseFloat(String)`: Convierte un String a Float
- `Integer.parseInt(String)`: Convierte un String a Integer

### 3. Manejo de Excepciones: try/catch

Cuando trabajamos con archivos, pueden ocurrir errores:
- El archivo no existe
- No tenemos permisos para leer el archivo
- Hay un error al leer el contenido

Java requiere que manejemos estos posibles errores usando bloques **try/catch**.

#### Estructura de try/catch

```java
try {
    // Código que puede generar una excepción
    FileReader fileReader = new FileReader("archivo.txt");
    BufferedReader bufferedReader = new BufferedReader(fileReader);
    String linea;
    while ((linea = bufferedReader.readLine()) != null) {
        // Procesar línea
    }
} catch (FileNotFoundException e) {
    // Qué hacer si el archivo no existe
    System.out.println("No se puede leer el archivo");
} catch (IOException e) {
    // Qué hacer si hay un error de lectura
    System.out.println("Error al leer el archivo");
}
```

#### Tipos de Excepciones

**FileNotFoundException**: Se lanza cuando el archivo especificado no existe o no se puede acceder a él.

```java
catch (FileNotFoundException e) {
    System.out.println("No se puede leer el archivo");
}
```

**IOException**: Es una excepción más general que cubre errores de entrada/salida, incluyendo problemas al leer líneas con `readLine()`.

```java
catch (IOException e) {
    System.out.println("Error al leer el archivo");
}
```

**Importante:**
- Los bloques `catch` deben ordenarse de más específico a más general
- `FileNotFoundException` es más específico que `IOException`
- Java nos obliga a manejar estas excepciones porque son "checked exceptions"

#### ¿Por qué necesitamos try/catch?

```java
// ❌ ESTO NO COMPILA - Java requiere manejo de excepciones
FileReader fileReader = new FileReader("archivo.txt");

// ✅ ESTO SÍ COMPILA - Manejamos la excepción
try {
    FileReader fileReader = new FileReader("archivo.txt");
} catch (FileNotFoundException e) {
    System.out.println("Error: archivo no encontrado");
}
```

Java nos obliga a manejar excepciones para que nuestros programas sean más robustos y no se detengan inesperadamente.

## 4. Ejemplos Prácticos

### 4.1. Mercado con ArrayList

**Ubicación:** `mercado-con-arraylist/`

Este proyecto demuestra el uso de ArrayList para almacenar productos introducidos por el usuario de forma dinámica.

#### Características

- Permite al usuario introducir tantos productos como desee
- Los productos se almacenan en un ArrayList
- Usa un bucle `while(true)` con condición de salida
- Demuestra el uso de `Scanner` para entrada de datos
- Muestra todos los productos al final

#### Código Principal

```java
ArrayList<Producto> productos = new ArrayList<Producto>();

while (true) {
    System.out.print("Introduce un producto (nombre): ");
    String lecturaNombre = lector.nextLine();

    System.out.print("Introduce un producto (descripcion): ");
    String lecturaDescripcion = lector.nextLine();

    System.out.print("Introduce un producto (precio): ");
    Float lecturaPrecio = lector.nextFloat();
    lector.nextLine();  // Limpiar el buffer

    System.out.print("Introduce un producto (stock): ");
    Integer lecturaStock = lector.nextInt();
    lector.nextLine();  // Limpiar el buffer

    Producto aux = new Producto(
        lecturaNombre,
        lecturaDescripcion,
        lecturaPrecio,
        lecturaStock,
        null
    );

    productos.add(aux);

    System.out.print("Quiere añadir otro producto? (y/n): ");
    String lecturaOtro = lector.nextLine();

    if (!lecturaOtro.equalsIgnoreCase("y")) {
        break;  // Salir del bucle
    }
}

// Mostrar todos los productos
for (int i = 0; i < productos.size(); i++) {
    System.out.println(productos.get(i));
}
```

**Conceptos demostrados:**
- ✅ Uso de ArrayList para colección dinámica
- ✅ Bucle infinito con condición de salida (`break`)
- ✅ Entrada de usuario con Scanner
- ✅ Limpieza del buffer con `nextLine()` después de `nextInt()` o `nextFloat()`
- ✅ Uso de `equalsIgnoreCase()` para comparación sin importar mayúsculas/minúsculas
- ✅ Recorrido de ArrayList con bucle `for`

#### Ejemplo de Ejecución

```
Hello Mercado
Inventario
Introduce tantos productos como quieras!:
Introduce un producto (nombre): Manzanas
Introduce un producto (descripcion): Manzanas rojas frescas
Introduce un producto (precio): 2.5
Introduce un producto (stock): 100
Quiere añadir otro producto? (y/n): y
Introduce un producto (nombre): Leche
Introduce un producto (descripcion): Leche entera 1L
Introduce un producto (precio): 1.2
Introduce un producto (stock): 50
Quiere añadir otro producto? (y/n): n
Manzanas Manzanas rojas frescas 2.5 100 null 1
Leche Leche entera 1L 1.2 50 null 2
```

### 4.2. Mercado con Ficheros

**Ubicación:** `mercado-con-ficheros/`

Este proyecto extiende el anterior añadiendo la capacidad de leer productos desde un archivo CSV.

#### Características

- Lee productos iniciales desde un archivo CSV (`assets/productos.csv`)
- Permite añadir más productos manualmente
- Combina lectura de archivos con entrada de usuario
- Demuestra el manejo de excepciones con try/catch
- Procesa archivos CSV con delimitador `;`

#### Estructura del Archivo CSV

```csv
nombrea;descripcion puede tener espacios;10.1;10;null
nombreb;descripcion puede tener espacios;10.2;10;null
nombrec;descripcion puede tener espacios;10.3;10;null
```

Cada línea representa un producto con:
- Campo 1: Nombre
- Campo 2: Descripción (puede contener espacios)
- Campo 3: Precio (Float)
- Campo 4: Stock (Integer)
- Campo 5: Fecha de caducidad (null en este caso)

#### Código de Lectura de Archivo

```java
ArrayList<Producto> productos = new ArrayList<Producto>();

String nombreArchivo = "assets/productos.csv";

try {
    FileReader fileReader = new FileReader(nombreArchivo);
    BufferedReader bufferedReader = new BufferedReader(fileReader);
    String linea;
    
    while ((linea = bufferedReader.readLine()) != null) {
        // Dividir la línea en campos
        String[] pedazos = linea.split(";");
        
        // Crear producto con los datos leídos
        Producto aux = new Producto(
            pedazos[0],                      // nombre
            pedazos[1],                      // descripcion
            Float.parseFloat(pedazos[2]),    // precio
            Integer.parseInt(pedazos[3]),    // stock
            null
        );
        
        productos.add(aux);
    }
} catch (FileNotFoundException e) {
    System.out.println("No se puede leer el archivo");
} catch (IOException e) {
    System.out.println("Error al leer el archivo");
}

// Mostrar productos leídos del archivo
for (int i = 0; i < productos.size(); i++) {
    System.out.println(productos.get(i));
}

// Después permite añadir más productos manualmente (igual que mercado-con-arraylist)
```

**Conceptos demostrados:**
- ✅ Lectura de archivos con FileReader y BufferedReader
- ✅ Manejo de excepciones con try/catch múltiples
- ✅ Procesamiento de archivos CSV con `split()`
- ✅ Conversión de String a tipos numéricos con `parseFloat()` y `parseInt()`
- ✅ Combinación de datos de archivo con entrada de usuario
- ✅ Uso de rutas relativas para archivos (`assets/productos.csv`)

#### Ejemplo de Ejecución

```
Hello Mercado
Inventario
Introduce tantos productos como quieras!:
nombrea descripcion puede tener espacios 10.1 10 null 1
nombreb descripcion puede tener espacios 10.2 10 null 2
nombrec descripcion puede tener espacios 10.3 10 null 3
Introduce un producto (nombre): Pan
Introduce un producto (descripcion): Pan integral
Introduce un producto (precio): 1.5
Introduce un producto (stock): 200
Quiere añadir otro producto? (y/n): n
nombrea descripcion puede tener espacios 10.1 10 null 1
nombreb descripcion puede tener espacios 10.2 10 null 2
nombrec descripcion puede tener espacios 10.3 10 null 3
Pan Pan integral 1.5 200 null 4
```

#### Clase Producto (Reutilizada)

Ambos proyectos utilizan la misma clase `Producto` de la Sesión 03:

```java
public class Producto {
    public String nombre;
    private String descripcion;
    private Float precio;
    private Integer stock;
    private Date caducidad;
    private Long id;
    public static Long contador = 0L;

    public Producto(String nombre, String descripcion, Float precio, 
                    Integer stock, Date caducidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.caducidad = caducidad;
        this.id = ++contador;  // ID único auto-incrementado
    }

    public String toString() {
        return nombre + " " + descripcion + " " + precio + " " 
               + stock + " " + caducidad + " " + id;
    }
}
```

## 5. Conceptos Importantes de Esta Sesión

### 5.1. Buffer del Scanner

Un detalle importante al usar `Scanner` es limpiar el buffer después de `nextInt()` o `nextFloat()`:

```java
Float lecturaPrecio = lector.nextFloat();
lector.nextLine();  // ¡IMPORTANTE! Limpiar el salto de línea del buffer

Integer lecturaStock = lector.nextInt();
lector.nextLine();  // ¡IMPORTANTE! Limpiar el salto de línea del buffer
```

**¿Por qué?**
- `nextInt()` y `nextFloat()` leen el número pero dejan el salto de línea en el buffer
- Si no limpiamos el buffer, el siguiente `nextLine()` leerá ese salto de línea vacío
- Esto puede causar que se salte la lectura de datos

### 5.2. Rutas de Archivos

En el proyecto usamos una ruta relativa:

```java
String nombreArchivo = "assets/productos.csv";
```

Esto significa que el archivo debe estar en la carpeta `assets/` dentro del directorio del proyecto. La ruta es relativa al directorio desde donde se ejecuta el programa.

### 5.3. Método split() de String

El método `split()` divide un String en un array usando un separador:

```java
String linea = "nombre;descripcion;10.5;100";
String[] partes = linea.split(";");

// partes[0] = "nombre"
// partes[1] = "descripcion"
// partes[2] = "10.5"
// partes[3] = "100"
```

### 5.4. Conversión de Strings a Números

Java proporciona métodos estáticos para convertir Strings a tipos numéricos:

```java
String precio = "10.5";
Float precioFloat = Float.parseFloat(precio);  // 10.5f

String cantidad = "100";
Integer cantidadInt = Integer.parseInt(cantidad);  // 100
```

**Importante:** Estos métodos pueden lanzar `NumberFormatException` si el String no representa un número válido.

## Resumen

En esta sesión aprendimos:

- ✅ **ArrayList**: Colección dinámica para almacenar múltiples objetos sin tamaño fijo
- ✅ **FileReader y BufferedReader**: Clases para leer archivos de texto de forma eficiente
- ✅ **try/catch**: Manejo de excepciones para código robusto (FileNotFoundException, IOException)
- ✅ **readLine()**: Método para leer archivos línea por línea
- ✅ **split()**: División de Strings usando separadores
- ✅ **parseFloat() y parseInt()**: Conversión de Strings a números
- ✅ **Procesamiento de CSV**: Lectura y procesamiento de archivos de datos tabulares
- ✅ Dos proyectos prácticos: mercado-con-arraylist y mercado-con-ficheros
- ✅ Combinación de lectura de archivos con entrada de usuario

## Ejercicio para Casa

Crear un programa que:

1. **Lea datos desde un archivo CSV** de tu elección (por ejemplo, estudiantes, libros, películas)
2. **Almacene los datos en un ArrayList** de objetos de una clase que tú definas
3. **Permita añadir más elementos** por consola
4. **Muestre todos los elementos** al final
5. **Maneje correctamente las excepciones** que puedan ocurrir

**Requisitos:**
- Crear tu propia clase (no usar Producto)
- Tener al menos 4 atributos en la clase
- Crear un archivo CSV con al menos 3 registros iniciales
- Implementar manejo de excepciones con try/catch
- Usar ArrayList para almacenar los objetos
- Permitir entrada de datos por consola

**Ejemplo de ideas:**
- Sistema de biblioteca: leer libros desde un CSV y añadir más
- Registro de estudiantes: leer estudiantes desde un CSV y añadir más
- Catálogo de películas: leer películas desde un CSV y añadir más
- Lista de contactos: leer contactos desde un CSV y añadir más

## Recursos Adicionales

- [Documentación oficial de ArrayList](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html)
- [Guía de Java Collections Framework](https://docs.oracle.com/javase/tutorial/collections/)
- [Lectura de archivos en Java](https://docs.oracle.com/javase/tutorial/essential/io/file.html)
- [Manejo de excepciones en Java](https://docs.oracle.com/javase/tutorial/essential/exceptions/)
- [BufferedReader Documentation](https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html)
