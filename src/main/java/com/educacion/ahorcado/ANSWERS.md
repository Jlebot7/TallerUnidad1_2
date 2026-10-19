# Taller Integrador: Ahorcado de Consola

Código fuente en `AhorcadoConsola/src/com/educacion/ahorcado/Main.java`. El código no lleva comentarios, por lo que las explicaciones están en este documento. Incluye las cuatro fases obligatorias y los tres retos de expansión.

## Mapa de requisitos

| Requisito del taller | Dónde está en `Main.java` |
|---|---|
| Menú `do-while` con `switch` (1 Jugar, 2 Estadísticas, 3 Salir) | `main`: el menú se repite hasta elegir la opción 3. |
| Validación de opción con `while` y `Integer.parseInt()` | `leerOpcionMenu`: lee la línea con `nextLine()`, la parsea y captura `NumberFormatException`. Si el valor no es un número o está fuera de 1 a 3, muestra un aviso y vuelve a pedirlo. |
| Variables de estado | `MAX_INTENTOS` (`final int`), `intentosRestantes` (`int`), `puntaje` (`double`), `juegoGanado` (`boolean`), `ultimaLetra` (`char`) y `nombreJugador` (`String`). |
| Normalización del nombre | `leerNombre`: `.trim()`, `.toUpperCase()` y comprobación de `.length()`. Si queda vacío, repite la pregunta. |
| Máscara y historial con `StringBuilder` | `mascara` guarda `_` separados por espacios (`J _ V _`). `historial` acumula las letras usadas. |
| `charAt`, `equalsIgnoreCase` | La letra se extrae con `entrada.charAt(0)` solo después de comprobar que la entrada tiene exactamente un carácter. Cada posición se compara con `equalsIgnoreCase`. |
| `break` | Se usa en victoria, en rendición (`0`) y cuando se agotan los intentos. |
| `continue` | Se usa con entrada vacía, entrada de más de un carácter, carácter que no es letra y letra repetida. En esos casos el turno no cuenta. |
| Operador ternario para el mensaje | `acierto ? "¡Acierto! ..." : "Fallo. ..."`. |
| Decremento solo en fallo | `intentosRestantes--` ocurre únicamente si la letra no está en la palabra. |
| Reporte final con `printf` | `mostrarResumen` usa `%s`, `%d` y `%.2f` y respeta el formato del bloque de salida del taller. |
| Casting para el porcentaje | `(double) aciertos / turnos * 100`. |

## Fórmulas elegidas

El enunciado muestra un resumen de ejemplo (JAVA, 4/6 intentos, 83.33 %, 150.00 pts) pero no define cómo se calculan esos valores. Además, no es posible reproducirlo con una definición coherente: con 4 de 6 intentos restantes hay 2 fallos, y un porcentaje de 83.33 % equivale a 5 de 6 turnos acertados, lo que implicaría un solo fallo. Por eso definí las fórmulas explícitamente; el formato del reporte sí es idéntico al del ejemplo.

- **Porcentaje de aciertos** = letras distintas acertadas ÷ turnos válidos × 100 (un turno válido es una letra nueva, acertada o fallada). Para JAVA con 2 fallos serían 3 aciertos en 5 turnos: **60.00 %**.
- **Puntaje (Reto 3):**
    - El puntaje parte de 100.0 y cada fallo resta 10.0 (mínimo 0).
    - Si se gana, suma un bono de 50.0 más un bono de eficiencia de hasta 50.0, calculado como `(aciertos / turnos) × 50`. Una partida perfecta (cero fallos) suma 50 de eficiencia y una con muchos fallos suma menos.
    - Si se pierde o se abandona, el puntaje final es 0.00.
    - Ejemplo (JAVA, 2 fallos): 100 − 20 = 80; bono de victoria 50; eficiencia 3/5 × 50 = 30; total **160.00 pts**.

## Retos de expansión

1. **Pista inteligente:** cuando queda un solo intento, el programa busca la primera `_` de la máscara con `indexOf` y muestra esa letra de la palabra obtenida con `.substring()`. Aparece una sola vez por partida y no tiene penalización.
2. **Dificultad dinámica:** `calcularMaxIntentos` fija `MAX_INTENTOS` según `.length()` de la palabra: 4 letras o menos, 6 intentos; de 5 a 7 letras, 7 intentos; 8 o más, 8 intentos. Se declara como `final int` local, asignado una sola vez al iniciar cada partida, de modo que sigue siendo inmutable durante la partida. JAVA (4 letras) conserva los 6 intentos del ejemplo.
3. **Multiplicador de puntaje:** el bono de eficiencia mencionado arriba combina la velocidad (turnos totales) y los fallos (aciertos ÷ turnos), y la penalización de 10 puntos por fallo.

## Matriz de depuración (Fase 6)

| Error | Cómo lo evita el código |
|---|---|
| Comparar cadenas con `==` | Ninguna comparación de texto usa `==`. Se usa `equals` (rendición con `"0"`) y `equalsIgnoreCase` (letras). |
| Bucles infinitos en el menú por mezclar lecturas numéricas y de texto | Toda la entrada se lee con `nextLine()` y los números se obtienen con `Integer.parseInt`. Nunca se usa `nextInt()`, así que no quedan saltos de línea residuales en el búfer. |
| `StringIndexOutOfBoundsException` | `charAt(0)` solo se invoca después de verificar que la entrada no está vacía y que tiene exactamente un carácter. Los índices de `substring` y `setCharAt` siempre salen de recorrer la palabra con `i < length()`. |

## Decisiones de diseño

- **Una sola letra por turno:** si el jugador escribe varias letras, se pide una sola y no se descuenta ningún intento.
- **Salida de la partida:** el jugador abandona escribiendo `0`, lo que corta el bucle con `break` y registra la partida como rendición.
- **Nombre:** se solicita una sola vez al iniciar el programa y se reutiliza en todas las partidas.
- **Estadísticas:** la opción 2 muestra partidas jugadas, ganadas, perdidas, tasa de victorias, mejor puntaje, acumulado y promedio de la sesión. No se guardan en disco.
- **Formato de decimales:** `Locale.setDefault(Locale.US)` hace que `printf` use punto decimal, como en el ejemplo del taller.