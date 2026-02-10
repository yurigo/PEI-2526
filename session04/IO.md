# I/O

## Input

### Lectura de ficheros

Tenemos a nuestra disposición: `java.io.FileReader`, `java.nio.file.Files`, `java.util.Scanner` y otras librerías de terceros que se especializan en tipos de fichero, por ejemplo: json (`com.google.code.gson`) o xml (`com.fasterxml.jackson.dataformat`), entre otros.

#### Comparación entre `java.io.FileReader`, `java.nio.file.Files` y `java.util.Scanner`:

##### `java.io.FileReader`:

- **Orientado a Caracteres**: `FileReader` se utiliza principalmente para leer caracteres de un archivo, y es más adecuado cuando necesitas trabajar con archivos de texto.

- **Lectura Lineal**: `FileReader` suele leer archivos de manera lineal, una línea a la vez. Es apropiado cuando necesitas procesar archivos de texto línea por línea.

- **Menos Abstracción**: `FileReader` es una clase más antigua y proporciona un nivel de abstracción más bajo en comparación con `java.nio.file.Files`. Puedes trabajar directamente con flujos de caracteres y caracteres individuales.

```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LecturaDeFicherosReader {
    public static void main(String[] args) {
        // Nombre del archivo que deseas leer
        String nombreArchivo = "archivo.txt";

        try {
            // Crear un objeto FileReader para leer el archivo
            FileReader fileReader = new FileReader(nombreArchivo);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Inicializar contador de palabras
            int contadorPalabras = 0;

            String linea;
            // Leer el archivo línea por línea
            while ((linea = bufferedReader.readLine()) != null) {
                // Dividir la línea en palabras utilizando espacios como delimitadores
                String[] palabras = linea.split(" ");
                // Incrementar el contador de palabras por la cantidad de palabras en la línea
                contadorPalabras += palabras.length;
            }

            // Cerrar el FileReader
            bufferedReader.close();

            // Mostrar el resultado
            System.out.println("El archivo " + nombreArchivo + " contiene " + contadorPalabras + " palabras.");

        } catch (IOException e) {
            // Manejo de excepción en caso de errores de lectura
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
```

##### `java.nio.file.Files`:

- **API más Amplia**: `java.nio.file.Files` es parte del paquete `java.nio`, que ofrece una API más amplia y versátil para trabajar con archivos y directorios, incluyendo la manipulación de rutas, copia, movimiento, borrado, entre otros.

- **Abstracción de Archivos**: `Files` proporciona métodos para leer y escribir archivos como matrices de bytes, lo que lo hace más versátil para trabajar con diferentes tipos de archivos, no solo archivos de texto.

- **Lectura Eficiente**: `Files` puede ser más eficiente en términos de uso de recursos para leer archivos grandes, ya que ofrece métodos que leen todo el contenido del archivo en una sola llamada.

```java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LecturaDeFicherosNioFiles {
    public static void main(String[] args) {
        // Ruta al archivo que deseas leer
        String rutaArchivo = "archivo.txt";

        try {
            // Leer todas las líneas del archivo en una lista
            Path archivo = Paths.get(rutaArchivo);
            List<String> lineas = Files.readAllLines(archivo);

            // Inicializar contador de palabras
            int contadorPalabras = 0;

            // Recorrer cada línea y contar las palabras
            for (String linea : lineas) {
                // Dividir la línea en palabras utilizando espacios como delimitadores
                String[] palabras = linea.split(" ");
                // Incrementar el contador de palabras por la cantidad de palabras en la línea
                contadorPalabras += palabras.length;
            }

            // Mostrar el resultado
            System.out.println("El archivo " + rutaArchivo + " contiene " + contadorPalabras + " palabras.");

        } catch (IOException e) {
            // Manejo de excepción en caso de errores de lectura
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
```

##### `java.util.Scanner`:

- **Versatilidad en la Lectura**: `Scanner` es versátil y puede ser utilizado para leer diferentes tipos de datos desde un archivo, no solo texto. Puede analizar tokens separados por delimitadores, lo que es útil para la lectura de archivos CSV o archivos con estructura específica.

- **Fácil Conversión de Tipos**: `Scanner` facilita la conversión de datos leídos desde el archivo en tipos específicos de Java. Puedes leer enteros, flotantes y otros tipos primitivos directamente.

- **Mayor Capacidad de Análisis**: `Scanner` es útil cuando deseas procesar el contenido del archivo de una manera más estructurada y analizar patrones específicos en el texto.

```java
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LecturaDeFicherosScanner {
    public static void main(String[] args) {
        // Nombre del archivo que deseas leer
        String nombreArchivo = "archivo.txt";

        try {
            // Crear un objeto Scanner para leer el archivo
            Scanner scanner = new Scanner(new File(nombreArchivo));

            // Inicializar contador de palabras
            int contadorPalabras = 0;

            // Leer el archivo línea por línea
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                // Dividir la línea en palabras utilizando espacios como delimitadores
                String[] palabras = linea.split(" ");
                // Incrementar el contador de palabras por la cantidad de palabras en la línea
                contadorPalabras += palabras.length;
            }

            // Cerrar el scanner
            scanner.close();

            // Mostrar el resultado
            System.out.println("El archivo " + nombreArchivo + " contiene " + contadorPalabras + " palabras.");

        } catch (FileNotFoundException e) {
            // Manejo de excepción si el archivo no se encuentra
            System.err.println("No se pudo encontrar el archivo: " + nombreArchivo);
        }
    }
}
```

##### `Elección Recomendada`:

- Si solo necesitas leer archivos de texto y procesarlos línea por línea, `FileReader` o `java.util.Scanner` son opciones adecuadas. `Scanner` es más versátil para la lectura de tipos de datos mixtos.

- Si planeas realizar una amplia variedad de operaciones de archivos, incluyendo la manipulación de archivos y directorios, `java.nio.file.Files` proporciona una API más completa.

- La elección entre `FileReader` y `Scanner` dependerá de si necesitas análisis adicional o si solo deseas leer y procesar líneas de texto.

En resumen, la elección entre estas clases dependerá de tus necesidades específicas y del tipo de operaciones que debas realizar en tus archivos.

### Lectura de consola

#### `java.util.Scanner`:

`java.util.Scanner` es una clase en Java que se utiliza para leer datos de entrada de la consola o de otros flujos de entrada, como archivos o cadenas. Proporciona métodos convenientes para analizar y procesar datos de entrada en varios formatos, como números enteros, números decimales y cadenas de texto.

Algunos de los métodos más comunes proporcionados por la clase `Scanner` para leer datos de la consola incluyen:

1. **nextLine()**: Lee una línea completa de texto ingresada por el usuario (hasta que se presione la tecla "Enter") y la devuelve como una cadena de caracteres (`String`).

```java
Scanner scanner = new Scanner(System.in);
System.out.print("Ingresa tu nombre: ");
String nombre = scanner.nextLine();
```

2. **nextInt()**: Lee un número entero ingresado por el usuario y lo devuelve como un entero (`int`).

```java
Scanner scanner = new Scanner(System.in);
System.out.print("Ingresa tu edad: ");
int edad = scanner.nextInt();
```

3. **nextDouble()**: Lee un número decimal ingresado por el usuario y lo devuelve como un número de punto flotante (`double`).

```java
Scanner scanner = new Scanner(System.in);
System.out.print("Ingresa tu altura en metros: ");
double altura = scanner.nextDouble();
```

4. **next()**: Lee la siguiente palabra (secuencia de caracteres sin espacios en blanco) ingresada por el usuario y la devuelve como una cadena de caracteres (`String`).

```java
Scanner scanner = new Scanner(System.in);
System.out.print("Ingresa una palabra: ");
String palabra = scanner.next();
```

5. **hasNext()**: Comprueba si hay más datos disponibles en la entrada sin consumirlos. Puede ser útil en bucles para leer datos hasta que se alcance un cierto criterio.

```java
Scanner scanner = new Scanner(System.in);
while (scanner.hasNext()) {
   String linea = scanner.nextLine();
   // Procesar la línea
}
```

Es importante recordar que después de usar métodos como `nextInt()` o `nextDouble()`, es una buena práctica consumir el carácter de nueva línea (`\n`) restante en la entrada utilizando `nextLine()` si planeas leer texto después. Esto evita problemas de lectura incorrecta.

```java
int numero = scanner.nextInt();
scanner.nextLine(); // Consumir el carácter de nueva línea
String texto = scanner.nextLine();
```

`Scanner` es una herramienta muy útil para interactuar con la entrada de consola en aplicaciones de Java y permite la lectura y procesamiento de datos ingresados por el usuario de manera flexible y versátil.

```java
import java.util.Scanner;

public class PersonRetriever
{
    public static void main(String[] args)
    {
        // Declarar el objeto e inicializar con
        // el objeto de entrada estándar predefinido

        Scanner sc = new Scanner(System.in);

        // entrada de una cadena

        String name = sc.nextLine();
        // entrada de un carácter
        char gender = sc.next().charAt(0);

        // Entrada de datos numéricos
        // byte, short y float
        int age = sc.nextInt();
        long mobileNo = sc.nextLong();
        double average = sc.nextDouble();

        // Imprima los valores para verificar si la entrada
        // fue obtenida correctamente.
        System.out.println("Nombre: "+name);
        System.out.println("Género: "+gender);
        System.out.println("Edad: "+age);
        System.out.println("Teléfono: "+mobileNo);
        System.out.println("Promedio: "+average);
    }
}
```

## Output

### Escritura de ficheros

Tenemos a nuestra disposición `BufferedWriter` y `FileWriter`.

`FileWriter` y `BufferedWriter` son clases en Java utilizadas para escribir datos en archivos de texto.

- `FileWriter`: Esta clase se utiliza para escribir caracteres en un archivo de texto. Es útil para escribir datos en un archivo de texto de manera simple.

Ejemplo de uso de `FileWriter`:

```java
FileWriter writer = new FileWriter("archivo.txt");
writer.write("Hola, mundo!");
writer.close(); // Es importante cerrar el escritor después de su uso.
```

- `BufferedWriter`: Esta clase se utiliza para escribir caracteres en un archivo de texto de manera más eficiente, especialmente cuando se deben realizar múltiples operaciones de escritura. `BufferedWriter` almacena temporalmente los datos en un búfer antes de escribirlos en el archivo, lo que puede mejorar el rendimiento al reducir el número de operaciones de escritura físicas en disco.

Ejemplo de uso de `BufferedWriter`:

```java
BufferedWriter writer = new BufferedWriter(new FileWriter("archivo.txt"));
writer.write("Hola, mundo!");
writer.close(); // Es importante cerrar el escritor después de su uso.
```

En resumen, `FileWriter` es útil para escribir datos en archivos de texto de manera simple, mientras que `BufferedWriter` proporciona un rendimiento mejorado al escribir datos en archivos de texto gracias al uso de un búfer interno. Ambas clases son importantes para trabajar con archivos de texto en Java, y es esencial cerrar los escritores después de su uso para asegurarse de que los datos se escriban correctamente y no se pierdan.

#### Ejemplo

```java
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class EscrituraDeFicheros {
    public static void main(String[] args) {
        // Nombre del archivo en el que escribir
        String nombreArchivo = "archivo_salida.txt";

        try {
            // Crear un objeto FileWriter para abrir o crear el archivo
            FileWriter archivo = new FileWriter(nombreArchivo);

            // Crear un objeto BufferedWriter para escribir en el archivo de manera eficiente
            BufferedWriter escritor = new BufferedWriter(archivo);

            // Datos a escribir en el archivo
            String linea1 = "Hola, este es un ejemplo de escritura en un archivo.";
            String linea2 = "Añadiremos algunas líneas más.";

            // Escribir las líneas en el archivo
            escritor.write(linea1);
            escritor.newLine(); // Agregar una nueva línea
            escritor.write(linea2);

            // Cerrar el archivo
            escritor.close();

            System.out.println("Se ha escrito en el archivo correctamente.");
        } catch (IOException e) {
            // Captura y maneja excepciones relacionadas con la escritura de archivos
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
}
```

### Escritura en consola

Tenemos a nuestra disposición `System.out`. 
`System.out` es un objeto en Java que se utiliza para la salida estándar (standard output) de un programa Java. Este objeto está vinculado al flujo de salida estándar del sistema, generalmente la consola o la ventana de terminal donde se ejecuta el programa. Se utiliza principalmente para mostrar mensajes, datos o resultados en la pantalla para que el usuario los vea o para propósitos de depuración.

`System.out` es una herramienta útil para interactuar con el usuario y depurar tu programa en Java. Puedes redirigir su salida a archivos u otros flujos en lugar de la consola según tus necesidades.

```java
System.out.println("Hello world!");
```

En Java, además de `println`, el objeto `System.out` proporciona varios otros métodos útiles para la salida estándar. Aquí tienes algunos de los métodos más comunes:

1. **print(String s)**: Este método imprime una cadena de caracteres en la salida estándar sin agregar un salto de línea al final. Es útil cuando deseas imprimir múltiples valores en la misma línea.

```java
System.out.print("Hola, ");
System.out.print("mundo!");
```

Salida:
```
Hola, mundo!
```

2. **printf(String format, Object... args)**: Este método permite imprimir una cadena formateada utilizando especificadores de formato. Puedes utilizarlo para imprimir valores con un formato específico, como números decimales con un número fijo de decimales.

```java
double precio = 19.99;
System.out.printf("El precio es %.2f dólares.", precio);
```

Salida:
```
El precio es 19.99 dólares.
```

3. **println()**: Como mencionaste, este método imprime una línea en blanco en la salida estándar, lo que te permite avanzar a la siguiente línea.

```java
System.out.println("Hola");
System.out.println("Mundo");
```

Salida:
```
Hola
Mundo
```

4. **print(Object obj)**: Este método imprime la representación en cadena del objeto proporcionado en la salida estándar. Si el objeto no es una cadena, se llamará automáticamente a su método `toString()` para obtener su representación en cadena.

```java
int numero = 42;
System.out.print(numero);
```

Salida:
```
42
```

Estos son algunos de los métodos más comunes disponibles en `System.out`. Cada uno tiene un propósito específico para imprimir datos en la salida estándar en diferentes formatos.


<br />
<br />
<br />
<br />

> Quizá te pueda interesar
>
> ```java
> public static final String ANSI_RESET = "\u001B[0m";
> public static final String ANSI_BLACK = "\u001B[30m";
> public static final String ANSI_RED = "\u001B[31m";
> public static final String ANSI_GREEN = "\u001B[32m";
> public static final String ANSI_YELLOW = "\u001B[33m";
> public static final String ANSI_BLUE = "\u001B[34m";
> public static final String ANSI_PURPLE = "\u001B[35m";
> public static final String ANSI_CYAN = "\u001B[36m";
> public static final String ANSI_WHITE = "\u001B[37m";
>
> // ....
>
> System.out.println(ANSI_GREEN + "Hello world!" + ANSI_RESET);
> ```

