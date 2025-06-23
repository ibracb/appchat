# AppChat

Autoría: [María Ballester Martínez](https://github.com/Maria-Ballester), e [Ibrahim Cherif Barry](https://github.com/ibracb23).

Se trata de un proyecto puramente académico, de la asignatura de TDS. Consiste en un sistema de chat, inspirado en aplicaciones populares como WhatsApp.

Toda la documentación se encuentra en [la memoria](MemoriaTDS.pdf), pero igualmente consideramos importante redundar en cómo ejecutar AppChat sin problemas. Por ejemplo, en el sistema operativo Linux, simplemente tiene que ejecutar desde el shell la instrucción `sudo apt install maven`.

En primer lugar, aségurese de tener instalado Maven en su máquina. Puede vrificarlo ejecutando desde el terminal el comando `mvn --version`. Si no lo está, rogamos que se lo instale.

En segundo lugar, debe instalar una librería de chat en su repositorio local de Maven. Para ello, ejecute dentro del directorio ```AppChat``` (o bien ```AppChat-main```, que le puede salir eso si descarga el proyecto en GitHub) el siguiente comando desde el terminal:
```
mvn install:install-file -Dfile=chatWindowLib.jar -DgroupId=tds -DartifactId=chat-window -Dversion="1.0.0" -Dpackaging=jar -DgeneratePom=true
```

En tercer lugar, debe instalar el Driver de Persistencia de TDS en su repositorio local de Maven. Para ello, ejecute dentro del directorio ```AppChat/lib``` el siguiente comando desde el terminal:  
```
mvn install:install-file -Dfile=DriverPersistencia.jar -DpomFile=driverPersistencia-2.0.pom
```

Una vez hecho todo esto, ya puede ejecutar AppChat. Diríjase al paquete `umu.tds.apps.aplicacion`, y ejecute la clase `AppChat.java`.

Finalmente, tiene a su disposición de unos usuarios iniciales para iniciar sesión, además de ciertos contactos añadidos y grupos, y intercambios de mensajes. Son los siguientes:
|Nombre|Teléfono|Contraseña|
|------|--------|----------|
|maria bm|212121212|m|
|ibra cb|313131313|i|
|maria bm2|121212121|m|
|ibra cb2|131313131|i|
|laura cc|171717171|l|
|maria jose tr|999999999|mj|
|alex ll|777777777|a|
|jorge sr|666666666|j|
