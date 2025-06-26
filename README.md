# 🏥 Clínica Digital - Sistema de Gestión de Consultas Médicas

Este es un sistema de gestión clínica desarrollado en **Java (JDK 17)** usando la arquitectura **MVVM** (Model-View-ViewModel) y una interfaz gráfica construida en **Java Swing**, con integración a una base de datos en la nube mediante **Firebase Firestore**. Está diseñado para facilitar la administración de pacientes, médicos, consultas médicas e historias clínicas en centros asistenciales pequeños y medianos.

---

## 🎯 Objetivos del proyecto

- Gestionar de forma eficiente las **consultas médicas** entre pacientes y doctores.
- Mantener un **registro completo de la historia clínica** del paciente.
- Permitir a los **administradores** y **doctores** acceder a la información que les corresponde según su rol.
- Facilitar la creación, modificación y visualización de:
  - Consultas médicas
  - Pacientes
  - Doctores
  - Historiales clínicos

---

## 🛠️ Tecnologías utilizadas

| Componente        | Herramienta / Lenguaje          |
|------------------|----------------------------------|
| Lenguaje          | Java 17                         |
| GUI               | Java Swing                      |
| Arquitectura      | MVVM (Model-View-ViewModel)     |
| Base de datos     | Firebase Firestore (NoSQL)      |
| Librerías externas| Google Firebase Admin SDK       |
| Logging           | SLF4J (sin proveedor por defecto)|
| Build tool        | Gradle o Maven (según preferencia) |

---

## 📁 Estructura del proyecto

```
src/
├── com.clinica.model         → Clases del modelo (Doctor, Patient, Consult, MedRecord, etc.)
├── com.clinica.view          → Interfaz gráfica Swing (Login, Dashboards, formularios)
├── com.clinica.viewModel     → Lógica intermedia (AdminViewModel, UserViewModel)
├── com.clinica.firebase      → Inicialización de Firebase (FirebaseInitializer.java)
└── Main.java                 → Clase principal
resources/
└── credenciales/clave-firebase.json  → Clave de cuenta de servicio Firebase
```

---

## 👤 Roles de usuario

### 👩‍⚕️ Admin
- Crear doctores y pacientes.
- Agendar consultas.
- Modificar una consulta.
- Ver historia clínica de un paciente.
- Ver todas las consultas por doctor.

### 🧑‍⚕️ Doctor
- Modificar síntomas, diagnóstico y tratamiento de sus consultas.
- Ver su historial de consultas.
- Ver la historia clínica de sus pacientes.

---

## 🔐 Inicio de sesión

Al ejecutar el sistema, se muestra una ventana de login con:
- Nombre de usuario
- Contraseña
- Tipo de usuario: `Admin` o `Doctor`

Se valida contra las credenciales almacenadas en la colección correspondiente de Firestore (`admins` o `doctores`).

---

## 🧠 Arquitectura MVVM

El sistema implementa una arquitectura de capas MVVM:

- `Model`: Clases puras que representan entidades (Consult, Doctor, Patient, MedRecord).
- `View`: Interfaces gráficas en Swing. Solo presentan información y capturan entradas.
- `ViewModel`: Procesa la lógica de negocio, gestiona la comunicación con Firestore y retorna resultados a la vista.

Cada vista se comunica exclusivamente con su `ViewModel`, sin acceder directamente al modelo o la base de datos.

---

## 🔥 Configuración de Firebase

### 1. Crear proyecto Firebase
- Ingresa a https://console.firebase.google.com/
- Crea un proyecto con Firestore habilitado.

### 2. Generar clave de servicio
- Ve a **Configuración del proyecto > Cuentas de servicio > Generar nueva clave privada**.
- Descarga el archivo `.json` y colócalo en:  
  `src/main/resources/credenciales/clave-firebase.json`

### 3. Asegúrate que el código use:

```java
FileInputStream serviceAccount = new FileInputStream("src/main/resources/credenciales/clave-firebase.json");

FirebaseOptions options = FirebaseOptions.builder()
    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
    .build();

FirebaseApp.initializeApp(options);
```

---

## 🚀 Ejecución del proyecto

### Con IntelliJ / Eclipse

1. Asegúrate de tener Java 17 configurado.
2. Agrega las dependencias necesarias (Google Firebase Admin SDK).
3. Ejecuta la clase `Main.java`.
4. Inicia sesión con un usuario válido (puedes crear uno manualmente desde código o desde Firebase Console).

### Ejemplo de admin manual:

```java
Admin admin = Admin.builder()
    .id("admin001")
    .name("Administrador Principal")
    .username("admin")
    .password("admin123")
    .build();
```

---

## ✅ Funcionalidades implementadas

### Pacientes
- Crear paciente
- Listar pacientes
- Consultar historia clínica

### Doctores
- Crear doctor
- Listar doctores
- Ver citas agendadas

### Consultas
- Agendar consulta
- Modificar consulta
- Ver consultas por paciente o doctor

### Historia clínica
- Se gestiona a través de la clase `MedRecord`
- Se actualiza automáticamente con cada nueva consulta

---

## 💡 Mejoras futuras sugeridas

- Implementar autenticación real (Firebase Auth).
- Permitir exportar historias clínicas a PDF.
- Añadir control de fechas para evitar agendamiento duplicado.
- Mejorar diseño gráfico con librerías como FlatLaf.

---

## 📸 Capturas de pantalla



---

## 🧑‍💻 Autor y contribuciones

Desarrollado por: **[Tu nombre o equipo]**  
Licencia: MIT (o la que decidas usar)

Pull requests y mejoras son bienvenidas.

---

## 📬 Contacto

¿Preguntas o sugerencias? Escríbenos a: [tucorreo@ejemplo.com]
