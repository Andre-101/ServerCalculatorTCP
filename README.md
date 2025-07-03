# Servidor TCP para Cálculos Matemáticos 

Este proyecto implementa un **servidor backend** que procesa **operaciones matemáticas simples** utilizando el **protocolo TCP**. El servidor recibe tramas codificadas en hexadecimal, interpreta la operación, la ejecuta y devuelve el resultado también en formato hexadecimal.

---

## ¿Cómo funciona?

El servidor escucha conexiones entrantes en un puerto configurado (por defecto `5000`) y espera recibir una **trama en hexadecimal** que contiene una operación como:

-     3+4
-     5*8 
-     -3/2


Cada operación es encapsulada en una estructura personalizada con las siguientes reglas:

### Estructura de la Trama

- `1 byte`: Tamaño del mensaje
- `n bytes`: Datos (la operación en sí)
- `2 bytes`: Checksum (verificación de integridad)

La trama completa es enviada como **string en hexadecimal**, y el servidor la interpreta usando su lógica interna.

---

## ¿Qué operaciones admite?

Actualmente, el servidor soporta:

- Suma (`+`)
- Resta (`-`)
- Multiplicación (`*`)
- División (`/`)

*Solo se aceptan operaciones entre dos números enteros.*

---

## Requisitos

- Java 17 o superior
- Maven

---

## Pruebas

### cmd
   ```bash
   telnet localhost 5000
   ```

### Cliente
Tambien esta disponble una interfaz por CLI y GUI en java: [Cliente](https://github.com/Andre-101/ClientCalculatorTCP.git)
