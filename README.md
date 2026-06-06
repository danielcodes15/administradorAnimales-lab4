# Mundo de Razas - Laboratorio 4

Aplicación web para consultar razas y tipos de animales clasificados por
categorías como Domestico, Carnivoro, Herbivoro, De compania y Guardian. Fue desarrollada
con arquitectura MVC, Spring Boot, Thymeleaf, JPA, Spring Security y MySQL.

## Funcionalidades

- Página de inicio con todas las razas.
- Filtro de razas por categoría de clasificación.
- CRUD de categorías, solo para administrador.
- CRUD de razas, solo para administrador.
- Vista pública para usuarios sin permisos de edición.
- Login de administrador.
- Relación de muchos a uno entre `Raza` y `CategoriaAnimal`.
- Campo de especie libre para registrar animales como Leon, Caballo, Aguila o Serpiente.
- Carga de imágenes JPEG, PNG, GIF o WebP.
- Persistencia de imágenes como BLOB dentro de MySQL.
- Validación de formularios.
- Protección al eliminar categorías que todavía tienen razas asociadas.
- Datos iniciales con distintas especies y categorias.
- Base de datos MySQL con creación y actualización automática de tablas.

## Arquitectura

```text
src/main/java/com/lab/demo
|-- configuracion   Datos iniciales
|-- controlador     Controladores MVC
|-- modelo          Entidades JPA
|-- repositorio     Repositorios Spring Data
`-- servicio        Lógica de negocio
```

Las vistas se encuentran en `src/main/resources/templates` y los estilos en
`src/main/resources/static/css`.

## Ejecutar el proyecto

Requisitos: Java 21 y MySQL 8 o superior.

La configuración predeterminada usa:

```text
Base de datos: animales_db
Puerto MySQL: 3306
```

El usuario y la contraseña de MySQL se configuran con variables de entorno o en
`application.properties`, según la instalación local.

Los visitantes pueden entrar sin login a `Inicio` y `Razas`; solo el admin puede
crear, editar o eliminar.

Si el usuario de MySQL no puede crear bases automáticamente, ejecutar:

```sql
CREATE DATABASE animales_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

Las credenciales pueden cambiarse con variables de entorno:

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="tu_clave"
```

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Luego abrir `http://localhost:8092`.

## Pruebas

```powershell
.\mvnw.cmd test
```

Las pruebas usan una base H2 temporal en memoria y no modifican MySQL.

## Repositorio

Repositorio en GitHub:

```text
https://github.com/danielcodes15/administradorAnimales-lab4
```

Ramas principales:

- `main`
- `desarrollo_Lab4`

Para clonar el proyecto:

```powershell
git clone https://github.com/danielcodes15/administradorAnimales-lab4.git
cd administradorAnimales-lab4
git switch desarrollo_Lab4
```
