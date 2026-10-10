package com.taller.practico;

public class ErrorAsignacion {
    public static void main(String[] args) {
        boolean cookieActiva = false;
        if (cookieActiva == true) {
            System.out.println("Estado de sesión: Sesión del usuario activa.");
        } else {
            System.out.println("Estado de sesión: Sesión inactiva.");
        }
    }
}
