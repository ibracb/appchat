# Diagrama de secuencia: crear grupo

Ejemplifica el patrón MVC seguido en AppChat: la Vista (`VentanaGrupos`) nunca accede directamente al Modelo, todas las peticiones pasan por el Controlador (Fachada), que coordina el Modelo (`Usuario`, `Grupo`) y la Persistencia (`GrupoDAO`).

```mermaid
sequenceDiagram
    actor Usuario as Usuario (persona)
    participant Vista as VentanaGrupos
    participant Ctrl as Controlador
    participant Modelo as Usuario (modelo)
    participant DAO as GrupoDAO

    Usuario ->> Vista: introduce nombre y pulsa "Aceptar"
    Vista ->> Ctrl: registrarGrupo(nombre)
    Ctrl ->> Ctrl: recuperarGrupo(nombre)
    alt el grupo ya existe
        Ctrl -->> Vista: false
        Vista -->> Usuario: muestra error
    else no existe
        Ctrl ->> Modelo: crearGrupo(nombre)
        Modelo ->> Modelo: new Grupo(nombre)
        Modelo -->> Ctrl: grupo creado
        Ctrl ->> DAO: create(grupo)
        DAO -->> Ctrl: ok
        Ctrl -->> Vista: true
        Vista -->> Usuario: muestra grupo creado
    end
```