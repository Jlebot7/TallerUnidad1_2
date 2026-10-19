# Taller: Operadores y Estructuras de Decisión en Java

Código fuente en `TallerUnidad1_2/src/com/taller/practico/`: `Main.java` (Secciones 1 a 4) y cuatro clases con los fragmentos de la Sección 5 ya corregidos. El código no lleva comentarios, por lo que las explicaciones están en este documento.

## Sección 1: Tabla de evaluación de expresiones

Cada fila parte de forma independiente de `int x = 7;` y `boolean flag = false;`. Los valores se obtienen por análisis de las reglas del lenguaje; `Main.ejecutarSeccion1()` imprime los mismos resultados para que los compares en IntelliJ.

| Expresión | Resultado | Razonamiento |
|---|---|---|
| `int seg = 86400 % 3600;` | `seg = 0` | 86400 = 24 × 3600, así que la división es exacta y el residuo es 0. |
| `int y = ++x + x++;` | `y = 16`, `x = 9` | `++x` incrementa primero (x = 8) y aporta 8. Después `x++` aporta el valor actual, 8, y recién entonces x pasa a 9. La suma es 8 + 8 = 16. |
| `double div1 = 15 / 2; double div2 = 15.0 / 2;` | `div1 = 7.0`, `div2 = 7.5` | `15 / 2` es división entera (7) y solo después se promueve a `double`. En `15.0 / 2`, el entero 2 se promueve a `double` antes de dividir. |
| `boolean res = flag && (++x > 0);` | `res = false`, `x = 7` | `flag` es `false`, así que `&&` hace cortocircuito y no evalúa el lado derecho. `++x` nunca se ejecuta y x conserva su valor. |

## Sección 2

- **Reto 1 (edades):** cascada `if / else if / else` con los rangos del enunciado: `< 12` Infante, `12 a 17` Adolescente, `18 a 64` Adulto, `>= 65` Adulto Mayor. Añadí una rama para edades negativas.
- **Reto 2 (notas y asistencia):** aprobado con `nota >= 70.0 && asistencia >= 80`. Si no, pendiente de examen extraordinario con `entregoProyectoFinal && nota >= 60.0`. Si no, reprobado. También valida que los datos estén en rango (0 a 100).
- **Reto 3 (tarifas):** VIP ×1.10, ESTUDIANTE ×0.50, ADULTO_MAYOR ×0.60 y GENERAL sin cambio, sobre `precioBase = 100.0`. Las cadenas se comparan con `equals`, no con `==` (ver Fragmento 2).
- **Ternario:**

```java
String estadoConexion = cookieSesionActiva ? "ACTIVA" : "INACTIVA";
```

## Sección 3: Gestor de días

El enunciado no define qué jornada corresponde a cada día, así que usé esta regla: lunes a viernes jornada laboral completa, sábado media jornada y domingo descanso. Las dos versiones están en `Main`:

- `obtenerJornadaTradicional(int)`: `switch` clásico con `case`, `break` y `default`.
- `obtenerJornadaModerna(int)`: `switch` con `->` como expresión. `case 1, 2, 3, 4, 5 ->` agrupa varios valores sin `break`.

El programa pide el número del día por consola con `Scanner` y muestra el resultado de ambas versiones.

## Sección 4: Reto integrador

| | Caso A (3, VIP, PROMO1 año) | Caso B (1, NUEVO, PROMO1 año) |
|---|---|---|
| Subtotal base | $750.00 | $250.00 |
| Descuento por categoría | -$150.00 (20 %) | -$12.50 (5 %) |
| Subtotal con descuento | $600.00 | $237.50 |
| Cupón | -$50.00 (aplica, 600 ≥ 500) | $0.00 (no aplica, 237.50 < 500) |
| Base imponible | $550.00 | $237.50 |
| IVA 16 % | $88.00 | $38.00 |
| **Total a pagar** | **$638.00** | **$275.50** |

El descuento por categoría usa `switch` y el cupón usa `if-else` con `&&`. El código del cupón está escrito exactamente como aparece en el documento, `"PROMO1 año"`.

`Main` fija `Locale.setDefault(Locale.US)` al inicio. Con la configuración regional de Colombia, `printf` mostraría `638,00`, y el `Scanner` esperaría comas en los decimales.

## Sección 5: Depuración

### Fragmento 1: asignación en lugar de comparación

- **Qué ocurre:** `cookieActiva = true` es una asignación, no una comparación. Primero guarda `true` en la variable y luego esa expresión vale `true`, así que el `if` siempre entra por la rama activa. Imprime "Sesión del usuario activa." aunque la variable valía `false`. En el depurador se ve cómo `cookieActiva` pasa de `false` a `true` al evaluar la condición.
- **Por qué compila:** en Java una asignación booleana dentro del `if` es válida. Con `int` sería un error de compilación, pero con `boolean` no.
- **Corrección:** como `cookieActiva` ya es booleana, se escribe `if (cookieActiva)`. También sería válido `cookieActiva == true`, pero es redundante. El código corregido imprime "Sesión inactiva."

### Fragmento 2: comparación de cadenas con `==`

- **Qué ocurre:** `new String("OneTrust")` crea un objeto nuevo en el heap, mientras que el literal `"OneTrust"` apunta al objeto compartido del *string pool*. `==` compara las referencias, que son distintas, y el programa imprime "Los proveedores son distintos según la comparación por referencia." aunque el texto es idéntico.
- **Corrección:** `proveedor1.equals(proveedor2)` compara el contenido y devuelve `true`. `equalsIgnoreCase` hace lo mismo sin distinguir mayúsculas.

### Fragmento 3: caída en cascada (fall-through)

- **Qué ocurre:** con `opcionCategoria = 1`, el `switch` entra en `case 1` y, al no haber `break`, sigue ejecutando todo lo que viene después. La salida es:

```
Categoría Selección: Lunes (Jornada Laboral)
Categoría Selección: Martes (Jornada Laboral)
Categoría Selección: Miércoles (Jornada Laboral)
Día no identificado
```

- **Corrección:** añadir `break;` al final de cada `case`. Con eso solo se imprime la línea del lunes. La alternativa moderna, `case 1 ->`, elimina este riesgo por diseño.

### Fragmento 4: pérdida de precisión por división entera

- **Qué ocurre:** `tareasCompletadas / tareasTotales` es una división entre dos `int`: `3 / 4` da `0`, porque trunca los decimales. Luego `0 * 100` es `0` y se convierte a `0.0`. Imprime `0.0%` en lugar de `75.0%`. El truncamiento ocurre antes de multiplicar por 100 y antes de asignar a un `double`.
- **Corrección:** `((double) tareasCompletadas / tareasTotales) * 100`. El cast convierte primero el numerador a `double`, así que la división ya es decimal y da `75.0`. También sirve `(tareasCompletadas * 100.0) / tareasTotales`.

### Guía del depurador (resumen)

1. Pon un breakpoint en la primera línea de `main` (clic en el margen izquierdo).
2. Ejecuta con `Shift + F9`.
3. Avanza con `F8` (Step Over) y observa la pestaña Variables.
4. Usa `Alt + F8` (Evaluate Expression) para evaluar expresiones, por ejemplo `tareasCompletadas / tareasTotales`.