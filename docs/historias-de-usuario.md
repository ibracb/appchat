# Historias de usuario

## 1. Iniciar sesión
Como usuario, quiero iniciar sesión para enviar y recibir mensajes.

- Si el usuario está registrado e introduce sus credenciales correctamente, el sistema muestra la ventana principal.
- Si el usuario no está registrado, el sistema vuelve a la ventana de login mostrando el error "Usuario no registrado".
- Si la contraseña es incorrecta, el sistema vuelve a la ventana de login mostrando el error "Contraseña incorrecta".

## 2. Registrar
Como usuario, quiero registrarme para poder enviar y/o recibir mensajes.

- Al pulsar "registrar" en la ventana de login, el sistema muestra los campos necesarios para completar el registro.
- Si faltan campos obligatorios (todos menos saludo e imagen), el sistema muestra un error y permite reintentar.
- Si las contraseñas introducidas no coinciden, el sistema muestra un error y permite reintentar.

## 3. Añadir contacto
Como usuario, quiero añadir un número de teléfono a mis contactos para agregarlo a la lista de contactos y poder enviarle mensajes y crear grupos con él.

- Al pulsar el botón "+" junto a un mensaje de un número no agregado, se abre la ventana de añadir contacto con el teléfono autocompletado.
- Al completar nombre y teléfono de un número existente en AppChat y pulsar "Aceptar", el contacto se añade a la lista.
- Si el teléfono introducido no corresponde a un usuario registrado en AppChat, el sistema muestra el error "No se puedo registrar el contacto".

## 4. Crear grupo
Como usuario, quiero crear un grupo para poder enviar mensajes a un conjunto de contactos.

- Al pulsar "añadir grupo" en la ventana de contactos, el sistema muestra los campos para completar la creación (nombre obligatorio, imagen opcional, miembros).
- Si no se completa el nombre, el sistema muestra un error indicando el campo que falta.

## 5. Aplicar filtros
Como usuario, quiero aplicar filtros para buscar mensajes.

- Al completar uno o varios campos de filtro y pulsar "Buscar", el sistema muestra los mensajes coincidentes con emisor y/o receptor.
- Si se pulsa "Buscar" sin completar ningún filtro, el sistema muestra el aviso "No hay datos que buscar. Por favor, complete al menos uno de los campos.".