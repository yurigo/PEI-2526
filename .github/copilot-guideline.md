# Guía de Estilo para Documentación del Curso PEI-2526

## Idioma
- **Toda la documentación debe estar en español (castellano).**

## Estructura de Sesiones

Cada sesión debe tener su propia carpeta con un README.md que siga esta estructura:

### 1. Encabezado
```markdown
# Sesión XX - Título de la Sesión

**Fecha:** DD de Mes de AAAA
```

### 2. Contenidos de la Sesión
- Lista clara de los temas principales cubiertos
- Explicación detallada de cada concepto con ejemplos de código
- Uso de emojis para claridad visual: ✅ ❌ 

### 3. Ejemplos Prácticos
- Sección dedicada a cada proyecto de ejemplo
- Incluir código representativo de la clase principal
- Destacar los conceptos demostrados con viñetas ✅
- Explicar el propósito y funcionamiento de cada ejemplo

### 4. Instrucciones de Ejecución
```bash
cd sessionXX/proyecto
javac src/*.java
java -cp src Main
```

### 5. Ejercicio para Casa
- Descripción clara del ejercicio
- Requisitos específicos
- Sugerencias de mejora o bonus

### 6. Recursos Adicionales
- Enlaces a documentación oficial
- Recursos de aprendizaje complementarios

## README Principal

El README.md principal debe contener:

### 1. Tabla de Sesiones Completadas
- Número de sesión
- Enlace al README de la sesión
- Resumen breve de temas
- Enlaces a proyectos/ejemplos

### 2. Estructura del Repositorio
- Árbol de directorios actualizado
- Descripción breve de cada carpeta

## Estilo de Código en la Documentación

### Bloques de Código
```java
// Siempre incluir comentarios en español
public class Ejemplo {
    private String atributo;  // Comentar atributos importantes
}
```

### Comparaciones ❌ vs ✅
Usar esta estructura para mostrar buenas y malas prácticas:

```java
// ❌ MAL: Explicación de por qué es malo
codigo_incorrecto();

// ✅ BIEN: Explicación de por qué es correcto
codigo_correcto();
```

### Resaltado de Conceptos
- **Negrita** para términos importantes
- `código` para nombres de clases, métodos, variables
- > Citas para notas importantes

## Tablas Comparativas

Usar tablas para comparar conceptos entre sesiones:

```markdown
| Aspecto       | Antes          | Después        |
| ------------- | -------------- | -------------- |
| Concepto 1    | Implementación | Mejora         |
```

## Tono y Lenguaje

- Usar un tono educativo pero accesible
- Explicar el "por qué" además del "cómo"
- Incluir beneficios de cada práctica
- Usar ejemplos del mundo real cuando sea posible

## Secciones Opcionales

Dependiendo del contenido de la sesión, se pueden añadir:

- **Diagramas UML**: Para clases complejas
- **Comparación con sesiones anteriores**: Para mostrar evolución
- **Preguntas frecuentes**: Si hay conceptos que generan dudas
- **Errores comunes**: Para ayudar a evitar problemas típicos

## Nomenclatura de Archivos

- `README.md` para documentación principal de cada sesión
- Nombres de carpetas en minúsculas cuando sea posible
- Nombres de proyectos descriptivos y en español

## Formato de Fechas

- Formato español: DD de Mes de AAAA
- Ejemplo: 9 de febrero de 2026

## Convenciones de Java

Cuando se documente código Java:

- Usar convenciones estándar de Java (camelCase para variables y métodos)
- Comentarios en español
- Incluir imports cuando sea relevante para el ejemplo
- Mostrar constructores completos en ejemplos de clases

## Principios de Diseño

Al documentar principios de diseño:

1. **Nombre del principio** en negrita
2. **Principio**: Definición clara
3. **Ejemplo**: Código demostrativo con comparación ❌ vs ✅
4. **Beneficios**: Lista de ventajas de seguir el principio

## Actualización del README Principal

Cada vez que se añade una nueva sesión:

1. Actualizar la tabla de "Sesiones Completadas"
2. Actualizar la "Estructura del Repositorio"
3. Verificar que todos los enlaces funcionen correctamente

---

**Nota**: Esta guía debe evolucionar con el curso. Si se identifican mejoras o nuevos patrones útiles, actualizar este documento.
