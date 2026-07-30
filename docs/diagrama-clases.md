# Diagrama de clases (dominio)

Generado a partir del código fuente en `umu.tds.apps.dominio`, no del boceto original de la memoria, para que refleje fielmente el modelo final.

```mermaid
classDiagram
    class Usuario {
        -int id
        -String nombre
        -LocalDate fechaNacimiento
        -LocalDate fechaRegistro
        -String email
        -String imagen
        -String movil
        -String contraseña
        -String saludo
        -boolean premium
        -Descuento descuento
        -Set~Contacto~ contactos
        +createGrupo(nombre, imagen) boolean
        +addContacto(ContactoIndividual)
        +removeContacto(ContactoIndividual) boolean
        +getDescuentoCalculado() double
        +updateDescuento()
        +getAllMensajes() Set~Mensaje~
    }

    class Contacto {
        <<abstract>>
        -int id
        -String nombre
        -Set~Mensaje~ mensajes
        +addMensaje(Mensaje)
        +nuevoMensaje(texto, emoticono, tipo) Mensaje
        +getSubTotalMensajesEnviadosUltimoMes() int
        +getUltimoMensaje() Mensaje
    }

    class ContactoIndividual {
        -Usuario usuario
        +getMovil() String
        +getSaludo() String
        +isAñadido() boolean
    }

    class Grupo {
        -Optional~String~ imagen
        -Set~ContactoIndividual~ miembros
        +addMiembro(ContactoIndividual) boolean
        +removeMiembro(ContactoIndividual) boolean
    }

    class Mensaje {
        -int id
        -LocalDateTime momentoEnvio
        -String texto
        -int emoticono
        -TipoMensaje tipo
    }

    class TipoMensaje {
        <<enumeration>>
        ENVIADO
        RECIBIDO
    }

    class Descuento {
        <<interface>>
        +isAplicable(Usuario) boolean
        +getNumDescuentosAdicionales(Usuario) int
        +getDescuento(precio, Usuario) double
        +getNombreDescuento() String
    }

    class DescuentoNull
    class DescuentoPorFecha
    class DescuentoPorMensaje

    class FactoriaDescuentos {
        <<enumeration>>
        INSTANCE
        +createDescuento(Usuario) Descuento
    }

    Contacto <|-- ContactoIndividual
    Contacto <|-- Grupo
    Usuario "1" o-- "*" Contacto : contactos
    Contacto "1" o-- "*" Mensaje : mensajes
    Mensaje --> TipoMensaje
    ContactoIndividual --> Usuario : referencia
    Grupo "1" o-- "*" ContactoIndividual : miembros
    Usuario --> Descuento
    Descuento <|.. DescuentoNull
    Descuento <|.. DescuentoPorFecha
    Descuento <|.. DescuentoPorMensaje
    FactoriaDescuentos ..> Descuento : crea
```

## Notas sobre el modelo

- `Contacto` es abstracta y raíz común de `ContactoIndividual` y `Grupo` — permite que `Usuario` guarde ambos en la misma colección (`Set<Contacto>`).
- Un `ContactoIndividual` referencia al `Usuario` real de AppChat al que representa (así se obtiene su móvil o saludo actualizados).
- `Grupo` mantiene su propia colección de `ContactoIndividual` como miembros, independiente de los contactos del usuario que creó el grupo.
- El descuento de un `Usuario` se calcula mediante `FactoriaDescuentos` (patrón Strategy + Factory), ver [patrones-diseno.md](patrones-diseno.md).