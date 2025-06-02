# Elvis_Gestion_ganadera_proyecto
Proyecto realizado para ejecutar en una ganadería 
Sistema de Gestión Ganadera
Este es un sistema de gestión ganadera desarrollado en Java que permite administrar diferentes aspectos de una explotación ganadera, incluyendo el manejo de ganado, clientes, proveedores, pastos y registros sanitarios.

Requisitos Previos
Java JDK 17 o superior
MySQL 8.0 o superior
Maven
Configuración del Proyecto
Clonar el repositorio:
git clone [URL-del-repositorio]
cd Elvis
Configurar la base de datos:

Crear una base de datos MySQL
Ejecutar los scripts SQL ubicados en src/main/resources/db/ en el siguiente orden:
create_razas_table.sql
init_admin.sql
init_clientes.sql
init_proveedores.sql
init_pastos.sql
init_ganado.sql
init_vacunacion.sql
init_facturas.sql
Compilar el proyecto:

mvn clean install
Ejecución del Proyecto
Para ejecutar el proyecto:

mvn exec:java
O ejecutar directamente la clase SistemaLogin desde tu IDE.

Funcionalidades Principales
El sistema cuenta con los siguientes módulos:

Gestión de Clientes

Registro de nuevos clientes
Actualización de información
Consulta de historial
Gestión de Ganado

Registro de ganado
Control de razas
Seguimiento individual
Gestión de Pastos

Control de áreas de pastoreo
Gestión de rotación
Registro de disponibilidad
Gestión Sanitaria

Registro de vacunaciones
Control sanitario
Historial médico
Gestión de Proveedores

Registro de proveedores
Control de suministros
Historial de compras
Facturación

Emisión de facturas
Control de ventas
Reportes financieros
Estructura del Proyecto
src/main/java/Datos/ - Capa de acceso a datos (DAO)
src/main/java/Modelo/ - Clases de modelo
src/main/java/Negocio/ - Lógica de negocio
src/main/java/Presentacion/ - Interfaces gráficas
src/main/java/Util/ - Clases utilitarias
src/main/resources/ - Recursos y scripts SQL
Tecnologías Utilizadas
Java 17
Maven
MySQL
Hibernate
JCalendar
JPA
Swing (para la interfaz gráfica)
Contribución
Para contribuir al proyecto:

Hacer fork del repositorio
Crear una rama para tu funcionalidad (git checkout -b feature/nueva-funcionalidad)
Hacer commit de tus cambios (git commit -am 'Añadir nueva funcionalidad')
Push a la rama (git push origin feature/nueva-funcionalidad)
Crear un Pull Request
Licencia
[Especificar la licencia del proyecto]
