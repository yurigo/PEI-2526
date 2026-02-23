
Repaso de las relaciones entre clases:

- dependencia
- asociacion
- agregación
- composición

reforzando el concepto de agregación y composición

Ejemplo aggregation example donde se comprueba que song está agregada a playlist.  en el momento que se borra playlist song no es recogida por el GC y sigue "viva" en el sistema.

Ejemplo de composition example donde se implementa un chessboard compuesto por 64 casillas y cuando se "borra" el chessboard las casillas son recogidas por el GC y borradas de memoria.


Se ha introducido herencia.  con el ejemplo de un zoo.  Animal como superclase y perro, gato, pajaro, y pez como subclase.

Se ha introducido el concepto de casting e instance of

para usar metodos de una subclase al trabajar con superclases.

Mejora de código introduciendo el polimorfismo y la sobrecarga de métodos.


Y por último se añade el concepto de clase abstracta y mostrar como se fuerza la instanciación de una clase derivada y se prohibe una instanciación de una clase abstracta.

Se añade el método abstracto y se comprueba como no implementarlo en una clase derivada produce un error sintáctico.

