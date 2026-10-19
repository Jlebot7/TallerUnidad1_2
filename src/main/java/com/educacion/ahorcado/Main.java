package com.educacion.ahorcado;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final String[] PALABRAS = {
            "JAVA", "CLASE", "OBJETO", "METODO", "BUCLE", "VARIABLE",
            "PROGRAMA", "ARREGLO", "CADENA", "HERENCIA", "INTELLIJ", "COMPILADOR"
    };
    private static final String SEPARADOR = "=".repeat(52);
    private static final double PUNTAJE_INICIAL = 100.0;
    private static final double PENALIZACION_FALLO = 10.0;
    private static final double BONO_VICTORIA = 50.0;
    private static final double BONO_EFICIENCIA_MAXIMO = 50.0;

    private static int partidasJugadas = 0;
    private static int partidasGanadas = 0;
    private static double puntajeAcumulado = 0.0;
    private static double mejorPuntaje = 0.0;

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        System.out.printf("%s%n", SEPARADOR);
        System.out.printf("ADIVINA LA PALABRA SECRETA - AHORCADO DE CONSOLA%n");
        System.out.printf("%s%n", SEPARADOR);

        String nombreJugador = leerNombre(scanner);
        System.out.printf("¡Bienvenido, %s!%n", nombreJugador);

        int opcion;
        do {
            System.out.printf("%n===== MENÚ PRINCIPAL =====%n");
            System.out.printf("1. Jugar%n");
            System.out.printf("2. Ver Estadísticas%n");
            System.out.printf("3. Salir%n");
            opcion = leerOpcionMenu(scanner);

            switch (opcion) {
                case 1:
                    jugarPartida(scanner, nombreJugador);
                    break;
                case 2:
                    mostrarEstadisticas(nombreJugador);
                    break;
                case 3:
                    System.out.printf("Gracias por jugar, %s. ¡Hasta pronto!%n", nombreJugador);
                    break;
            }
        } while (opcion != 3);

        scanner.close();
    }

    private static String leerNombre(Scanner scanner) {
        String nombre = "";
        while (nombre.length() == 0) {
            System.out.printf("Ingrese su nombre: ");
            nombre = scanner.nextLine().trim().toUpperCase();
            if (nombre.length() == 0) {
                System.out.printf("Error: el nombre no puede estar vacío.%n");
            }
        }
        return nombre;
    }

    private static int leerOpcionMenu(Scanner scanner) {
        int opcion = 0;
        boolean valida = false;

        while (!valida) {
            System.out.printf("Seleccione una opción: ");
            String linea = scanner.nextLine().trim();
            try {
                opcion = Integer.parseInt(linea);
                if (opcion >= 1 && opcion <= 3) {
                    valida = true;
                } else {
                    System.out.printf("Opción fuera de rango. Elija 1, 2 o 3.%n");
                }
            } catch (NumberFormatException e) {
                System.out.printf("Entrada no válida: \"%s\" no es un número entero. Elija 1, 2 o 3.%n", linea);
            }
        }

        return opcion;
    }

    private static int calcularMaxIntentos(int longitudPalabra) {
        if (longitudPalabra <= 4) {
            return 6;
        } else if (longitudPalabra <= 7) {
            return 7;
        } else {
            return 8;
        }
    }

    private static void jugarPartida(Scanner scanner, String nombreJugador) {
        String palabraSecreta = PALABRAS[(int) (Math.random() * PALABRAS.length)];
        final int MAX_INTENTOS = calcularMaxIntentos(palabraSecreta.length());

        int intentosRestantes = MAX_INTENTOS;
        double puntaje = PUNTAJE_INICIAL;
        boolean juegoGanado = false;
        boolean rendido = false;
        boolean pistaMostrada = false;
        char ultimaLetra = ' ';
        int turnos = 0;
        int aciertos = 0;

        StringBuilder mascara = new StringBuilder();
        for (int i = 0; i < palabraSecreta.length(); i++) {
            if (i > 0) {
                mascara.append(' ');
            }
            mascara.append('_');
        }
        StringBuilder historial = new StringBuilder();

        System.out.printf("%n¡Nueva partida! La palabra tiene %d letras y dispones de %d intentos.%n",
                palabraSecreta.length(), MAX_INTENTOS);
        System.out.printf("Escribe una letra por turno o \"0\" para rendirte.%n");

        while (intentosRestantes > 0 && !juegoGanado) {
            System.out.printf("%nPalabra: %s%n", mascara);
            System.out.printf("Intentos restantes: %d / %d%n", intentosRestantes, MAX_INTENTOS);
            System.out.printf("Letras usadas: %s%n", historial.length() == 0 ? "(ninguna)" : historial.toString().trim());

            if (intentosRestantes == 1 && !pistaMostrada) {
                int posicion = mascara.indexOf("_");
                String pista = palabraSecreta.substring(posicion / 2, posicion / 2 + 1);
                System.out.printf("PISTA: la palabra contiene la letra \"%s\".%n", pista);
                pistaMostrada = true;
            }

            System.out.printf("Letra: ");
            String entrada = scanner.nextLine().trim().toUpperCase();

            if (entrada.length() == 0) {
                System.out.printf("Error: la entrada no puede estar vacía.%n");
                continue;
            }

            if (entrada.equals("0")) {
                rendido = true;
                break;
            }

            if (entrada.length() != 1) {
                System.out.printf("Error: ingrese una sola letra por turno.%n");
                continue;
            }

            char letra = entrada.charAt(0);

            if (!Character.isLetter(letra)) {
                System.out.printf("Error: \"%c\" no es una letra válida.%n", letra);
                continue;
            }

            if (historial.indexOf(String.valueOf(letra)) >= 0) {
                System.out.printf("La letra %c ya fue utilizada. Intenta con otra.%n", letra);
                continue;
            }

            turnos++;
            ultimaLetra = letra;
            historial.append(letra).append(' ');

            boolean acierto = false;
            for (int i = 0; i < palabraSecreta.length(); i++) {
                if (palabraSecreta.substring(i, i + 1).equalsIgnoreCase(String.valueOf(letra))) {
                    mascara.setCharAt(i * 2, letra);
                    acierto = true;
                }
            }

            if (acierto) {
                aciertos++;
            } else {
                intentosRestantes--;
                puntaje = Math.max(0.0, puntaje - PENALIZACION_FALLO);
            }

            String mensaje = acierto
                    ? "¡Acierto! La letra " + ultimaLetra + " está en la palabra."
                    : "Fallo. La letra " + ultimaLetra + " no está en la palabra.";
            System.out.printf("%s%n", mensaje);

            if (mascara.indexOf("_") < 0) {
                juegoGanado = true;
                break;
            }

            if (intentosRestantes == 0) {
                break;
            }
        }

        String estadoFinal;
        if (juegoGanado) {
            estadoFinal = "¡VICTORIA!";
            double eficiencia = (double) aciertos / turnos;
            puntaje += BONO_VICTORIA + eficiencia * BONO_EFICIENCIA_MAXIMO;
        } else if (rendido) {
            estadoFinal = "RENDICIÓN";
            puntaje = 0.0;
        } else {
            estadoFinal = "DERROTA";
            puntaje = 0.0;
        }

        double porcentajeAciertos = turnos > 0 ? (double) aciertos / turnos * 100 : 0.0;

        partidasJugadas++;
        if (juegoGanado) {
            partidasGanadas++;
        }
        puntajeAcumulado += puntaje;
        if (puntaje > mejorPuntaje) {
            mejorPuntaje = puntaje;
        }

        mostrarResumen(nombreJugador, estadoFinal, palabraSecreta, intentosRestantes, MAX_INTENTOS, porcentajeAciertos, puntaje);
    }

    private static void mostrarResumen(String nombreJugador, String estadoFinal, String palabraSecreta,
                                       int intentosRestantes, int maxIntentos, double porcentajeAciertos, double puntaje) {
        System.out.printf("%n%s%n", SEPARADOR);
        System.out.printf("RESUMEN DE LA PARTIDA%n");
        System.out.printf("%s%n", SEPARADOR);
        System.out.printf("Jugador: %s%n", nombreJugador);
        System.out.printf("Estado Final: %s%n", estadoFinal);
        System.out.printf("Palabra Secreta: %s%n", palabraSecreta);
        System.out.printf("Intentos Restantes: %d / %d%n", intentosRestantes, maxIntentos);
        System.out.printf("Porcentaje Aciertos: %.2f%%%n", porcentajeAciertos);
        System.out.printf("Puntaje Final: %.2f pts%n", puntaje);
        System.out.printf("%s%n", SEPARADOR);
    }

    private static void mostrarEstadisticas(String nombreJugador) {
        System.out.printf("%n%s%n", SEPARADOR);
        System.out.printf("ESTADÍSTICAS DE LA SESIÓN%n");
        System.out.printf("%s%n", SEPARADOR);
        System.out.printf("Jugador: %s%n", nombreJugador);

        if (partidasJugadas == 0) {
            System.out.printf("Aún no has jugado ninguna partida.%n");
        } else {
            int partidasPerdidas = partidasJugadas - partidasGanadas;
            double tasaVictorias = (double) partidasGanadas / partidasJugadas * 100;
            double promedio = puntajeAcumulado / partidasJugadas;
            System.out.printf("Partidas jugadas: %d%n", partidasJugadas);
            System.out.printf("Partidas ganadas: %d%n", partidasGanadas);
            System.out.printf("Partidas perdidas o abandonadas: %d%n", partidasPerdidas);
            System.out.printf("Tasa de victorias: %.2f%%%n", tasaVictorias);
            System.out.printf("Mejor puntaje: %.2f pts%n", mejorPuntaje);
            System.out.printf("Puntaje acumulado: %.2f pts%n", puntajeAcumulado);
            System.out.printf("Puntaje promedio: %.2f pts%n", promedio);
        }

        System.out.printf("%s%n", SEPARADOR);
    }
}