# Sesión 01 - Introducción a Java

## Contenido de la Sesión

Esta sesión cubre los conceptos fundamentales de programación en Java, desde la instalación del entorno de desarrollo hasta la creación de programas básicos.

## 1. Introducción a Java

Java es un lenguaje de programación orientado a objetos, multiplataforma y de propósito general. Fue desarrollado por Sun Microsystems (ahora Oracle) y se caracteriza por:

- **Portabilidad**: "Write Once, Run Anywhere" (WORA) - escribe una vez, ejecuta en cualquier lugar
- **Orientado a objetos**: Todo en Java es un objeto (excepto los tipos primitivos)
- **Robusto y seguro**: Manejo automático de memoria (Garbage Collection)
- **Compilado e interpretado**: El código se compila a bytecode y se ejecuta en la JVM (Java Virtual Machine)

## 2. Instalación de IntelliJ IDEA

IntelliJ IDEA es un IDE (Entorno de Desarrollo Integrado) potente para Java. Pasos básicos para la instalación:

1. Descargar IntelliJ IDEA desde [jetbrains.com](https://www.jetbrains.com/idea/)
2. Elegir entre la versión Community (gratuita) o Ultimate (de pago)
3. Instalar siguiendo el asistente de instalación
4. Configurar el JDK (Java Development Kit) en el IDE

## 3. Primer Programa: "Hola Mundo"

El programa más básico en Java es el "Hola Mundo":

```java
class Main {
    public static void main(String[] args) {
        System.out.println("Hola mundo");
    }
}
```

Este programa imprime "Hola mundo" en la consola.

## 4. Estructura Básica de un Programa Java

Un programa Java típico tiene la siguiente estructura:

```java
class NombreClase {
    public static void main(String[] args) {
        // Código del programa
    }
}
```

**Elementos clave:**
- `class`: Define una clase
- `NombreClase`: Nombre de la clase (debe coincidir con el nombre del archivo)
- `public static void main(String[] args)`: Método principal donde comienza la ejecución
- `{}`: Delimitadores de bloque de código
- `//`: Comentarios de una línea

## 5. Compilación y Ejecución

Para compilar y ejecutar un programa Java:

```bash
# Compilar (genera un archivo .class)
javac Main.java

# Ejecutar
java Main
```

En IntelliJ IDEA, puedes simplemente hacer clic en el botón "Run" o presionar Shift+F10.

## 6. Tipos de Datos Primitivos

Java tiene 8 tipos de datos primitivos:

```java
// Enteros
byte b = 0;        // 8 bits  (-128 a 127)
short s = 100;     // 16 bits (-32,768 a 32,767)
int i = 0;         // 32 bits (más común)
long w = 56L;      // 64 bits (sufijo 'L')

// Punto flotante
float f = 45.0f;   // 32 bits (sufijo 'f')
double d = 45.0;   // 64 bits (más preciso)

// Booleano
boolean flag = true;  // true o false

// Caracter
char c = 'A';      // Un solo carácter Unicode
```

**Clases Wrapper**: Cada tipo primitivo tiene una clase equivalente (Byte, Short, Integer, Long, Float, Double, Boolean, Character).

```java
Byte b2 = 10;
Short s2 = 100;
Integer i2 = 45;       // Autoboxing: int a Integer
Long w2 = 56L;
Boolean flag2 = Boolean.TRUE;
Float f2 = 45.0f;
Double d2 = 45.0;
```

## 7. Estructuras de Control

### 7.1. Condicional if-else

```java
if (i == 1) {
    System.out.println("el numero es 1");
} else if (i == 2) {
    System.out.println("el numero es 2");
} else if (i == 3) {
    System.out.println("el numero es 3");
} else {
    System.out.println("el numero es otro");
}
```

### 7.2. Switch

```java
switch (i) {
    case 1:
    case 2:
        System.out.println("el numero es 1 o 2");
        break;
    case 3:
        System.out.println("el numero es 3");
        break;
    case 4:
        System.out.println("el numero es 4");
        break;
    default:
        System.out.println("otro número");
}
```

### 7.3. Bucle for

```java
for (int k = 0; k < 20; k++) {
    System.out.println("La k es " + k);
    
    if (esPar(k)) {
        System.out.println("La k es par");
    }
}
```

### 7.4. Bucle while

```java
int i = 0;
while (i < 10) {
    i++;
    System.out.println("Valor de i: " + i);
}
```

### 7.5. Bucle do-while

```java
int i = 0;
do {
    System.out.println("Valor de i: " + i);
    i++;
} while (i < 10);
```

### 7.6. Control de flujo: break y continue

```java
while(true) {
    i++;
    if (i >= 10) {
        break;      // Sale del bucle
    }
    if (esPar(i)) {
        continue;   // Salta a la siguiente iteración
    }
    System.out.println("Número impar: " + i);
}
```

## 8. I/O Básica en Java

### 8.1. Salida: System.out

```java
// Imprime con salto de línea
System.out.println("Hola mundo");

// Imprime sin salto de línea
System.out.print("Hola mundo");

// Concatenación de strings
System.out.println("String" + " " + "se pueden concatenar");
```

### 8.2. Entrada: Scanner

```java
import java.util.Scanner;

Scanner lectorDeTeclado = new Scanner(System.in);

// Leer un entero
System.out.println("Introduce un numero:");
int numero = lectorDeTeclado.nextInt();

// Leer un String
System.out.println("Introduce texto:");
String texto = lectorDeTeclado.next();

// Importante: Cerrar el Scanner para liberar recursos
lectorDeTeclado.close();
```

**Nota**: Es una buena práctica cerrar el Scanner después de usarlo, o usar try-with-resources para gestión automática de recursos.

## 9. Funciones (Métodos)

Las funciones permiten reutilizar código y organizar mejor los programas:

```java
public static boolean esPar(int k) {
    return k % 2 == 0;
}

public static int suma(int a, int b) {
    return a + b;
}

public static int resta(int a, int b) {
    return a - b;
}

public static int multiplicacion(int a, int b) {
    return a * b;
}

public static int division(int a, int b) {
    // Nota: Esta función lanzará ArithmeticException si b es 0
    // En producción, se debería validar: if (b == 0) return 0; o lanzar una excepción controlada
    return a / b;
}
```

**Componentes de una función:**
- `public static`: Modificadores de acceso y tipo
- `int` / `boolean` / `void`: Tipo de retorno
- `nombreFuncion`: Nombre de la función
- `(parametros)`: Parámetros de entrada
- `return`: Devuelve un valor (si no es void)

## 10. Demos en Clase

### 10.1. Hello World

Ver código completo en: `helloworld/src/Main.java`

Este programa demuestra:
- Declaración de variables de tipos primitivos
- Uso de System.out.println y System.out.print
- Estructuras de control (if, switch, while, for)
- Funciones personalizadas

### 10.2. Calculadora Simple

Ver código completo en: `calculadora/src/Main.java`

Una calculadora interactiva que:
1. Solicita dos números al usuario
2. Pregunta qué operación realizar (suma, resta, multiplicación, división)
3. Muestra el resultado usando funciones específicas para cada operación

**Ejemplo de ejecución:**
```
Bienvenido a la calculadora
Introduce un numero:
10
tu numero es: 10
Introduce otro numero:
5
tu numero es: 5
Introduce la operacion (S: suma, R: resta, M: multi, D: division):
S
15
```

## Resumen

En esta sesión aprendimos:
- ✅ Conceptos básicos de Java y su ecosistema
- ✅ Instalación y configuración de IntelliJ IDEA
- ✅ Estructura de un programa Java
- ✅ Tipos de datos primitivos y sus wrappers
- ✅ Estructuras de control (if, switch, for, while, do-while)
- ✅ Entrada y salida básica (Scanner y System.out)
- ✅ Creación y uso de funciones
- ✅ Dos proyectos prácticos: Hello World y Calculadora

## Recursos Adicionales

- [Documentación oficial de Java](https://docs.oracle.com/en/java/)
- [Tutorial de Java de Oracle](https://docs.oracle.com/javase/tutorial/)
- [IntelliJ IDEA Documentation](https://www.jetbrains.com/help/idea/)
