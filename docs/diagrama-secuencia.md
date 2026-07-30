# Diagrama de secuencia: añadir contacto a un grupo

Ejemplifica el patrón MVC seguido en AppChat: la Vista (`VentanaGestionarMiembros`) nunca accede directamente al Modelo, todas las peticiones pasan por el Controlador (Fachada), que coordina el Modelo (`Grupo`) y la Persistencia (`GrupoDAO`).

```mermaid
sequenceDiagram
    actor Usuario as Usuario (persona)
    participant Vista as VentanaGestionarMiembros
    participant Ctrl as Controlador
    participant Modelo as Grupo (modelo)
    participant DAO as GrupoDAO

    Usuario ->> Vista: selecciona grupo, pulsa "Añadir miembros"
    Vista ->> Ctrl: getUsuariosNoPertenecientesAlGrupo(grupo)
    Ctrl -->> Vista: lista de contactos
    Vista -->> Usuario: muestra contactos disponibles
    Usuario ->> Vista: selecciona contacto y confirma
    Vista ->> Ctrl: añadirContacto(contacto, grupo)
    Ctrl ->> Modelo: addMiembro(contacto)
    Modelo -->> Ctrl: ok
    Vista ->> Ctrl: actualizarGrupo(grupo)
    Ctrl ->> DAO: update(grupo)
    DAO -->> Ctrl: ok
    Ctrl -->> Vista: ok
    Vista -->> Usuario: contacto añadido al grupo
    Note over Vista,Ctrl: Se repite para cada contacto seleccionado
```
