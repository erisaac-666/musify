# Reproductor de Música (Java)

Proyecto simple de reproductor de música en Java con algunas canciones incluidas.

**Descripción:**
- Aplicación de escritorio para reproducir archivos de audio (incluye WAV en el proyecto).

**Características:**
- Reproducir, pausar y detener canciones.
- Canciones incluidas en el proyecto dentro de la carpeta de assets.

**Requisitos:**
- Java 8+ (JDK instalado)
- Apache Ant (opcional, para compilar desde línea de comandos)
- NetBeans 

**Cómo ejecutar**

Opción 1 — Abrir en NetBeans:
- Abre el proyecto usando NetBeans (carpeta del proyecto). Ejecuta desde el IDE.

Opción 2 — Usando Ant desde terminal:
```bash
cd "$(dirname "$PWD")/$(basename "$PWD")"
ant
# Si existe un target run en build.xml:
ant run
```
.

**Estructura del proyecto (relevante):**
- `src/` : código fuente Java (`Reproductor.java`, `Reproducer.java`, etc.).
- `src/assets/wav/` : archivos de audio incluidos.
- `build.xml` : script Ant para compilar y construir.
- `nbproject/` : metadatos de NetBeans.

**Cómo añadir canciones:**
- Copia nuevos archivos de audio (.wav) a `src/assets/wav/`.


**Notas:**
- El proyecto está preparado para usarse con NetBeans; ejecutar desde el IDE es la opción más sencilla.


**Autor:**
- Isaac Gomez

Si quieres, puedo añadir instrucciones exactas para ejecutar la clase principal o generar un JAR ejecutable.```