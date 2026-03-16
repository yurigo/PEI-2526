# Sesión 13 – Proyecto Final: laSallefy

**Fecha:** 16 de marzo de 2026

## Contenidos de la Sesión

En esta sesión se ha presentado el **proyecto final de la asignatura** llamado **laSallefy**. Antes de presentar el enunciado se ha revisado el código del `SoundSynth` existente, se ha dibujado su diagrama UML en clase y se ha refactorizado la clase genérica `SoundSynth` a una **clase abstracta**, convirtiendo el `enum` `WaveType` en **subclases** de `SoundSynth`.

- 📄 **Enunciado del proyecto final:** [`ENUNCIADO.md`](ENUNCIADO.md)
- 🔗 **Código original (visto en clase):** [sound-synth-example](https://github.com/yurigo/sound-synth-example)
- 🔗 **Código refactorizado (compartido en clase):** [rama refactor-inheritance-sound-synth](https://github.com/yurigo/sound-synth-example/tree/refactor-inheritance-sound-synth)

---

## 1. Código original

El código presentado al inicio de la sesión constaba de **2 clases y 1 enum**:

```
SoundSynth.java   ← clase concreta con makeSound(WaveType, freq, durationMs)
WaveType.java     ← enum: SINE, SQUARE, TRIANGLE, SAWTOOTH
Main.java         ← demo: llama a makeSound con cada tipo de onda
```

### `WaveType.java`

```java
public enum WaveType {
    SINE,
    SQUARE,
    TRIANGLE,
    SAWTOOTH
}
```

### `SoundSynth.java`

```java
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SoundSynth {

    public void makeSound(WaveType type, int numberOfTimesFullFuncPerSec, int durationMs) throws LineUnavailableException {
        System.out.println(String.format("Starting to make sound %s %d at %d ms", type, numberOfTimesFullFuncPerSec, durationMs));
        byte[] buf = new byte[2];
        int frequency = 44100; //44100 sample points per 1 second
        AudioFormat af = new AudioFormat((float) frequency, 16, 1, true, false);
        SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
        sdl.open();
        sdl.start();
        for (int i = 0; i < durationMs * (float) frequency / 1000; i++) {
            float numberOfSamplesToRepresentFullFunc = (float) frequency / numberOfTimesFullFuncPerSec;
            double phase = (i % numberOfSamplesToRepresentFullFunc) / numberOfSamplesToRepresentFullFunc;

            double value = 0.0;

            switch (type) {
                case SINE:
                    value = Math.sin(2 * Math.PI * phase);
                    break;
                case SQUARE:
                    value = (phase < 0.5) ? 1.0 : -1.0;
                    break;
                case TRIANGLE:
                    value = 4 * Math.abs(phase - 0.5) - 1;
                    break;
                case SAWTOOTH:
                    value = 2 * phase - 1;
                    break;
            }

            short a = (short) (value * 32767);
            buf[0] = (byte) (a & 0xFF);
            buf[1] = (byte) (a >> 8);
            sdl.write(buf, 0, 2);
        }
        sdl.drain();
        sdl.stop();
    }
}
```

### `Main.java`

```java
public class Main {
    public static void main(String[] args) {
        SoundSynth synth = new SoundSynth();

        try {
            synth.makeSound(WaveType.SINE,     440, 1000);
            synth.makeSound(WaveType.SINE,     440,  200);
            synth.makeSound(WaveType.SINE,     700,  500);
            synth.makeSound(WaveType.SINE,     440, 1000);

            synth.makeSound(WaveType.SQUARE,   440, 1000);
            synth.makeSound(WaveType.SQUARE,   440,  200);
            synth.makeSound(WaveType.SQUARE,   700,  500);
            synth.makeSound(WaveType.SQUARE,   440, 1000);

            synth.makeSound(WaveType.TRIANGLE, 440, 1000);
            synth.makeSound(WaveType.TRIANGLE, 440,  200);
            synth.makeSound(WaveType.TRIANGLE, 700,  500);
            synth.makeSound(WaveType.TRIANGLE, 440, 1000);

            synth.makeSound(WaveType.SAWTOOTH, 440, 1000);
            synth.makeSound(WaveType.SAWTOOTH, 440,  200);
            synth.makeSound(WaveType.SAWTOOTH, 700,  500);
            synth.makeSound(WaveType.SAWTOOTH, 440, 1000);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
```

### Diagrama UML original (dibujado en clase)

```
┌─────────────────────────────────────────────────────────────────────┐
│                           SoundSynth                                 │
├─────────────────────────────────────────────────────────────────────┤
│ + makeSound(type: WaveType, numberOfTimesFullFuncPerSec: int,        │
│             durationMs: int)                                         │
└──────────────────────────────┬──────────────────────────────────────┘
                               │ usa (dependencia)
                               ▼
                  ┌────────────────────┐
                  │     «enum»         │
                  │     WaveType       │
                  ├────────────────────┤
                  │ SINE               │
                  │ SQUARE             │
                  │ TRIANGLE           │
                  │ SAWTOOTH           │
                  └────────────────────┘
```

---

## 2. Refactorización: de enum a jerarquía de clases

En clase se propuso sustituir el `enum` `WaveType` por **subclases** de una `SoundSynth` **abstracta**.

La idea clave es que el `switch` sobre el tipo de onda que vive dentro de `SoundSynth` se puede eliminar moviendo la lógica de cada caso a su propia clase. Así, cada tipo de onda es una clase independiente que hereda de `SoundSynth` e implementa `makeSound` a su manera.

### `SoundSynth.java` (refactorizado → clase abstracta)

```java
import javax.sound.sampled.LineUnavailableException;

public abstract class SoundSynth {

    public abstract void makeSound(
            int numberOfTimesFullFuncPerSec,
            int durationMs) throws LineUnavailableException;

}
```

### `SoundSynthSine.java` (implementada en clase)

```java
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SoundSynthSine extends SoundSynth {

    @Override
    public void makeSound(int numberOfTimesFullFuncPerSec, int durationMs) throws LineUnavailableException {
        System.out.println(String.format("Starting to make sound %s %d at %d ms", "sine", numberOfTimesFullFuncPerSec, durationMs));
        byte[] buf = new byte[2];
        int frequency = 44100; //44100 sample points per 1 second
        AudioFormat af = new AudioFormat((float) frequency, 16, 1, true, false);
        SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
        sdl.open();
        sdl.start();
        for (int i = 0; i < durationMs * (float) frequency / 1000; i++) {
            float numberOfSamplesToRepresentFullFunc = (float) frequency / numberOfTimesFullFuncPerSec;
            double phase = (i % numberOfSamplesToRepresentFullFunc) / numberOfSamplesToRepresentFullFunc;

            //////////////////////////////////////////
            double value = 0.0;
            value = Math.sin(2 * Math.PI * phase);
            //////////////////////////////////////////

            short a = (short) (value * 32767);
            buf[0] = (byte) (a & 0xFF);
            buf[1] = (byte) (a >> 8);
            sdl.write(buf, 0, 2);
        }
        sdl.drain();
        sdl.stop();
    }
}
```

### `Main.java` (refactorizado)

```java
public class Main {
    public static void main(String[] args) {

        //SoundSynth synth = new SoundSynth();
        SoundSynth sineSynth = new SoundSynthSine();

        // TODO:
        // SoundSynth squareSynth = new SoundSynthSquare();
        // SoundSynth triangleSynth = new SoundSynthTriange();
        // SoundSynth sawtoothSynth = new SoundSynthSawtooth();

        try {
            sineSynth.makeSound(440, 1000);
            sineSynth.makeSound(440,  200);
            sineSynth.makeSound(700,  500);
            sineSynth.makeSound(440, 1000);

            // TODO
            // squareSynth.makeSound(440, 1000);
            // ...
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
```

### Diagrama UML refactorizado

```
              ┌──────────────────────────────────────┐
              │             «abstract»               │
              │             SoundSynth               │
              ├──────────────────────────────────────┤
              │ + makeSound(numberOfTimesFullFuncPerSec: int,│
              │             durationMs: int)         │
              └─────────────────┬────────────────────┘
                                │
              ┌─────────────────┼─ · · · (por implementar)
              ▼                 ▼
  ┌──────────────────┐   ┌──────────────────┐
  │  SoundSynthSine  │   │ SoundSynthSquare │  · · ·
  ├──────────────────┤   ├──────────────────┤
  │ + makeSound(...) │   │ + makeSound(...) │
  └──────────────────┘   └──────────────────┘
```

---

## 3. Conceptos clave: Clases Abstractas

Las **clases abstractas** son clases que **no se pueden instanciar directamente**. Pueden contener **métodos abstractos** (declarados pero sin implementación) que obligan a las subclases a proporcionar su propia versión.

```java
// NO se puede hacer esto → error de compilación:
SoundSynth synth = new SoundSynth();

// SÍ se puede hacer esto → polimorfismo:
SoundSynth synth = new SoundSynthSine();
synth.makeSound(440, 1000);
```

| Regla | Descripción |
|---|---|
| No se puede instanciar | `new SoundSynth()` → **error de compilación** |
| Puede tener métodos abstractos | Sin cuerpo; las subclases **deben** implementarlos |
| Puede tener métodos concretos | Con cuerpo; las subclases los heredan |
| Una subclase debe implementar todos los abstractos | O ella misma debe declararse también `abstract` |

---

## Recursos

- 🔗 [sound-synth-example – rama principal](https://github.com/yurigo/sound-synth-example)
- 🔗 [sound-synth-example – rama refactorizada](https://github.com/yurigo/sound-synth-example/tree/refactor-inheritance-sound-synth)
- [Abstract Methods and Classes – The Java Tutorials (Oracle)](https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html)
- [Sesión 07 – Herencia y Polimorfismo](../session07/README.md)
- [Sesión 08 – Herencia, Visibilidad y Polimorfismo](../session08/README.md)
- [Sesión 09 – Interfaces en Java](../session09/README.md)

