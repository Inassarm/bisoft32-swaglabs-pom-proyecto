# Pruebas automatizadas – Swag Labs

## Requisitos
- JDK 17
- Maven 3.9+
- Google Chrome instalado (o configurar otro navegador)
- Conexión a Internet

## Ejecución
Desde esta carpeta ejecutar:

```bash
mvn test
```

La URL y el navegador se configuran en `src/test/resources/config.properties`.
Los resultados de Surefire se generan en `target/surefire-reports`.

> Antes de entregar, ejecutar la suite en el ambiente real, verificar selectores y guardar evidencias. No reportar pruebas como aprobadas si no se ejecutaron.
