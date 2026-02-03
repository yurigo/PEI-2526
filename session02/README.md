# Sesión 02 - Programación Orientada a Objetos (POO)

**Fecha:** 3 de febrero de 2026

## Contenidos de la Sesión

En esta sesión hemos aprendido los conceptos fundamentales de la Programación Orientada a Objetos (POO) en Java:

### 1. Conceptos de POO
- **Clases y objetos**: Una clase es una plantilla que define las características y comportamientos de un tipo de objeto. Un objeto es una instancia concreta de una clase.
- **Atributos**: Variables que almacenan el estado de un objeto.
- **Métodos**: Funciones que definen el comportamiento de un objeto.
- **Encapsulamiento**: Principio que permite ocultar los detalles internos de una clase y controlar el acceso a sus atributos mediante modificadores de acceso (public, private, protected).
- **Constructores**: Métodos especiales que se ejecutan al crear un objeto, inicializando sus atributos.

### 2. Ejemplo Práctico: Sistema de Gestión de Gimnasio

En la carpeta `Gimnasio` encontrarás el ejemplo desarrollado en clase que demuestra estos conceptos.

#### Clase Persona

La clase `Persona` representa a un usuario del gimnasio con los siguientes atributos:

```java
public String nombre;        // Nombre de la persona (público)
private Date fechaEntrada;   // Fecha y hora de entrada al gimnasio (privado)
private Date fechaSalida;    // Fecha y hora de salida del gimnasio (privado)
private long id;             // Identificador único (privado)
private String DNI;          // Documento de identidad (privado)
private boolean estaDentro;  // Estado: dentro o fuera del gimnasio (privado)
```

**Constructor:**
```java
public Persona(String nombre, String DNI)
```
Inicializa una persona con su nombre y DNI.

**Métodos principales:**
- `muestrate()`: Muestra por consola toda la información de la persona.
- `registrarEntrada()`: Registra la entrada de la persona al gimnasio, actualizando la fecha de entrada y el estado.
- `registrarSalida()`: Registra la salida de la persona del gimnasio, actualizando la fecha de salida y el estado.

#### Diagrama UML de la Clase Persona

```
+---------------------------+
|        Persona            |
+---------------------------+
| + nombre: String          |
| - fechaEntrada: Date      |
| - fechaSalida: Date       |
| - id: long                |
| - DNI: String             |
| - estaDentro: boolean     |
+---------------------------+
| + Persona(nombre, DNI)    |
| + muestrate(): void       |
| + registrarEntrada(): void|
| + registrarSalida(): void |
+---------------------------+
```

### 3. Cómo Ejecutar el Ejemplo

1. Navega a la carpeta `Gimnasio`:
   ```bash
   cd session02/Gimnasio
   ```

2. Compila el proyecto:
   ```bash
   javac src/*.java
   ```

3. Ejecuta el programa:
   ```bash
   java -cp src Main
   ```

El programa crea tres personas (Alice, Bob y Charlie) y simula entradas y salidas al gimnasio, mostrando el estado de cada persona en cada momento.

### 4. Conceptos Clave Demostrados

- **Encapsulamiento**: Los atributos sensibles como `fechaEntrada`, `fechaSalida`, `id`, `DNI` y `estaDentro` son privados, protegiendo la información interna del objeto.
- **Métodos públicos**: Se proporcionan métodos públicos (`registrarEntrada()`, `registrarSalida()`, `muestrate()`) para interactuar con el objeto de forma controlada.
- **Constructores**: Se utiliza un constructor para inicializar los atributos del objeto al momento de su creación.
- **Referencias a objetos**: Se demostró la diferencia entre crear un nuevo objeto y crear una referencia a un objeto existente.

## Ejercicio para Casa

Crear **dos clases** con atributos y métodos propios. Algunas ideas:

1. **Clase Libro**: Con atributos como título, autor, ISBN, número de páginas, y métodos para prestar/devolver el libro.
2. **Clase Coche**: Con atributos como marca, modelo, color, velocidad, y métodos para acelerar, frenar, mostrar información.
3. **Clase CuentaBancaria**: Con atributos como número de cuenta, titular, saldo, y métodos para depositar, retirar, consultar saldo.
4. **Clase Estudiante**: Con atributos como nombre, matrícula, calificaciones, y métodos para agregar calificaciones, calcular promedio.

**Requisitos:**
- Cada clase debe tener al menos 3-4 atributos.
- Utilizar modificadores de acceso apropiados (público/privado).
- Implementar un constructor.
- Incluir al menos 2-3 métodos que manipulen los atributos.
- Crear una clase Main para probar tus clases creando objetos y llamando a sus métodos.

## Recursos Adicionales

- [Documentación oficial de Java - Clases y Objetos](https://docs.oracle.com/javase/tutorial/java/concepts/)
- [Introducción a la POO en Java](https://docs.oracle.com/javase/tutorial/java/concepts/object.html)
