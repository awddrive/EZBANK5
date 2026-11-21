# Ezbank APK con widget (se compila en la nube, sin instalar nada)

## Obtener la APK
1. Crea una cuenta en github.com y un repositorio nuevo (privado está bien).
2. Sube TODO el contenido de esta carpeta, incluida la carpeta oculta `.github`. Si tu explorador no la muestra o la web no la sube, en GitHub usa "Add file → Create new file", escribe `.github/workflows/build.yml` como nombre y pega el contenido de ese archivo.
3. Ve a la pestaña **Actions** → **Build APK** → **Run workflow**. Tarda unos 5-10 minutos.
4. Al terminar, entra a la ejecución y descarga **ezbank-apk** (viene en un zip con `app-debug.apk`).
5. Pasa la APK al celular y ábrela. Android te pedirá permitir "instalar apps desconocidas" para esa app (Archivos o Chrome).

## Widget
Mantén presionado un espacio vacío de la pantalla de inicio → Widgets → **Ezbank**. Muestra el velocímetro de hoy (verde, amarillo o rojo), lo gastado y lo que te queda. Se actualiza cada vez que abres o usas la app y se redibuja cada 30 min. Tocarlo abre Ezbank.

## Lo que cambia respecto a la versión web
- La voz con dictado del navegador NO funciona dentro de una APK (el WebView de Android no la incluye). Hay que agregar un plugin nativo de voz; se puede hacer después.
- La IA sigue usando tu llave de Gemini dentro de `www/index.html`.
- Es una APK de prueba (debug), válida para instalar y usar tú. Para Play Store hay que firmarla con tu propia llave.
- Si el build falla, copia el error de la pestaña Actions y lo corregimos.
