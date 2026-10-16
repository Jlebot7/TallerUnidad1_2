package com.taller2;

import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        seccion1Operadores();
        seccion2Decisiones();
        seccion3Switch();
        seccion4Integrador();
    }
    static void seccion1Operadores() {
        titulo("SECCIÓN 1: OPERADORES");
        int seg = 86400 % 3600;
        System.out.println("1) seg = " + seg);
        int x = 7;
        int y = ++x + x++;
        System.out.println("2) y = " + y + " | x = " + x);
        double div1 = 15 / 2;
        double div2 = 15.0 / 2;
        System.out.println("3) div1 = " + div1 + " | div2 = " + div2);
        x = 7;
        boolean flag = false;
        boolean res = flag && (++x > 0);
        System.out.println("4) res = " + res + " | x = " + x);
    }
    static void seccion2Decisiones() {
        titulo("SECCIÓN 2: IF-ELSE Y TERNARIO");

        System.out.println("--- Reto 1: Clasificación de edades ---");
        int[] edades = {10, 15, 28, 68};
        for (int edadUsuario : edades) {
            clasificarEdad(edadUsuario);
        }

        System.out.println("\n--- Reto 2: Aprobación de notas y asistencia ---");
        evaluarAlumno(85.0, 90, false);  // Aprobado
        evaluarAlumno(65.0, 70, true);   // Pendiente de Examen Extraordinario
        evaluarAlumno(75.0, 60, true);   // Pendiente (nota ok, asistencia no)
        evaluarAlumno(50.0, 95, true);   // Reprobado (nota < 60)
        evaluarAlumno(65.0, 90, false);  // Reprobado (sin proyecto)

        System.out.println("\n--- Reto 3: Tarifas de entrada ---");
        calcularEntrada("VIP");
        calcularEntrada("ESTUDIANTE");
        calcularEntrada("ADULTO_MAYOR");
        calcularEntrada("GENERAL");
        calcularEntrada("OTRO");

        System.out.println("\n--- Refactorización a operador ternario ---");
        boolean cookieSesionActiva = true;
        // Una sola línea que reemplaza todo el bloque if-else:
        String estadoConexion = cookieSesionActiva ? "ACTIVA" : "INACTIVA";
        System.out.println("estadoConexion = " + estadoConexion);
    }

    static void clasificarEdad(int edadUsuario) {
        if (edadUsuario < 0) {
            System.out.println("Edad " + edadUsuario + " -> Edad inválida");
        } else if (edadUsuario < 12) {
            System.out.println("Edad " + edadUsuario + " -> Categoría: Infante | Perfil: requiere tutor de cuenta");
        } else if (edadUsuario <= 17) {
            System.out.println("Edad " + edadUsuario + " -> Categoría: Adolescente | Perfil: acceso restringido a foros comunitarios");
        } else if (edadUsuario <= 64) {
            System.out.println("Edad " + edadUsuario + " -> Categoría: Adulto | Perfil: acceso a funcionalidades estándar");
        } else {
            System.out.println("Edad " + edadUsuario + " -> Categoría: Adulto Mayor | Perfil: acceso con interfaz adaptada");
        }
    }

    static void evaluarAlumno(double calificacionFinal, int porcentajeAsistencia, boolean entregoProyectoFinal) {
        String dictamen;
        if (calificacionFinal >= 70.0 && porcentajeAsistencia >= 80) {
            dictamen = "Aprobado";
        } else if (entregoProyectoFinal && calificacionFinal >= 60.0) {
            dictamen = "Pendiente de Examen Extraordinario";
        } else {
            dictamen = "Reprobado";
        }
        System.out.println("Nota: " + calificacionFinal + " | Asistencia: " + porcentajeAsistencia
                + "% | Proyecto: " + entregoProyectoFinal + " -> " + dictamen);
    }

    static void calcularEntrada(String tipoEntrada) {
        double precioBase = 100.0;
        double precioFinal;

        if (tipoEntrada.equals("VIP")) {
            precioFinal = precioBase * 1.10;          // +10% recargo
        } else if (tipoEntrada.equals("ESTUDIANTE")) {
            precioFinal = precioBase * 0.50;          // -50%
        } else if (tipoEntrada.equals("ADULTO_MAYOR")) {
            precioFinal = precioBase * 0.60;          // -40%
        } else if (tipoEntrada.equals("GENERAL")) {
            precioFinal = precioBase;                 // sin cambios
        } else {
            System.out.println("Tipo de entrada '" + tipoEntrada + "' no reconocido");
            return;
        }
        System.out.println(String.format(Locale.US, "Entrada %s -> Monto final a pagar: $%.2f", tipoEntrada, precioFinal));
    }
    static void seccion3Switch() {
        titulo("SECCIÓN 3: SWITCH");

        int[] dias = {1, 3, 5, 6, 7, 9};
        for (int dia : dias) {
            System.out.println("Día " + dia + " | Tradicional: " + jornadaTradicional(dia));
            System.out.println("Día " + dia + " | Moderno:     " + jornadaModerna(dia));
        }
    }
    static String jornadaTradicional(int dia) {
        String jornada;
        switch (dia) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                jornada = "Jornada laboral completa";
                break;
            case 6:
                jornada = "Jornada laboral parcial (medio día)";
                break;
            case 7:
                jornada = "Día de descanso";
                break;
            default:
                jornada = "Día inválido (use un número del 1 al 7)";
                break;
        }
        return jornada;
    }

    static String jornadaModerna(int dia) {
        return switch (dia) {
            case 1, 2, 3, 4, 5 -> "Jornada laboral completa";
            case 6 -> "Jornada laboral parcial (medio día)";
            case 7 -> "Día de descanso";
            default -> "Día inválido (use un número del 1 al 7)";
        };
    }

    static void seccion4Integrador() {
        titulo("SECCIÓN 4: RETO INTEGRADOR");

        System.out.println("--- Caso de Prueba A (esperado: 638.00) ---");
        liquidar(3, "VIP", "PROMO1 año");

        System.out.println("\n--- Caso de Prueba B (esperado: 275.50) ---");
        liquidar(1, "NUEVO", "PROMO1 año");
    }

    static void liquidar(int cantidad, String tipoCliente, String cupon) {
        final double PRECIO_UNITARIO = 250.00;
        final double IVA = 0.16;
        final double MONTO_MINIMO_CUPON = 500.00;
        final double DESCUENTO_CUPON = 50.00;
        double subtotalBase = cantidad * PRECIO_UNITARIO;
        double porcentajeDescuento = switch (tipoCliente) {
            case "VIP" -> 0.20;
            case "REGULAR" -> 0.10;
            case "NUEVO" -> 0.05;
            default -> 0.0;
        };
        double descuentoCliente = subtotalBase * porcentajeDescuento;
        double subtotalConDescuento = subtotalBase - descuentoCliente;

        double descuentoCupon;
        if (cupon.equals("PROMO1 año") && subtotalConDescuento >= MONTO_MINIMO_CUPON) {
            descuentoCupon = DESCUENTO_CUPON;
        } else {
            descuentoCupon = 0.0;
        }

        double baseImponible = subtotalConDescuento - descuentoCupon;
        double iva = baseImponible * IVA;
        double total = baseImponible + iva;

        System.out.println(String.format(Locale.US, "Subtotal base:          $%.2f", subtotalBase));
        System.out.println(String.format(Locale.US, "Descuento %-10s -$%.2f", tipoCliente + ":", descuentoCliente));
        System.out.println(String.format(Locale.US, "Subtotal con descuento: $%.2f", subtotalConDescuento));
        System.out.println(String.format(Locale.US, "Descuento cupón:       -$%.2f", descuentoCupon));
        System.out.println(String.format(Locale.US, "Base imponible:         $%.2f", baseImponible));
        System.out.println(String.format(Locale.US, "IVA (16%%):             +$%.2f", iva));
        System.out.println(String.format(Locale.US, "Total a pagar = $%.2f", total));
    }

    static void titulo(String texto) {
        System.out.println("\n==================================================");
        System.out.println(texto);
        System.out.println("==================================================");
    }
}
