package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import pe.edu.pucp.kawkiweb.reports.ReporteUtil;

@WebService(serviceName = "ReportesService")
public class Reportes {

    // Formatos aceptados para fechas
    private static final DateTimeFormatter FORMATTER_ISO = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final DateTimeFormatter FORMATTER_CUSTOM = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter FORMATTER_DATE_ONLY = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @WebMethod(operationName = "generarReporteVentasYTendencias")
    public byte[] generarReporteVentasYTendencias(
            @WebParam(name = "fechaInicio") String fechaInicio,
            @WebParam(name = "fechaFin") String fechaFin) {

        try {
            LocalDateTime inicio = parseFecha(fechaInicio);
            LocalDateTime fin = parseFecha(fechaFin);

            // Validar que fechaFin sea posterior a fechaInicio
            if (inicio != null && fin != null && fin.isBefore(inicio)) {
                System.err.println("Error: fechaFin debe ser posterior a fechaInicio");
                return null;
            }

            return ReporteUtil.reporteVentasYTendencias(inicio, fin);

        } catch (DateTimeParseException e) {
            System.err.println("Error: Formato de fecha inválido. Use ISO-8601 (2024-01-15T10:30:00) o dd/MM/yyyy HH:mm:ss");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("Error al generar reporte de ventas: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod(operationName = "generarReporteEstadoStock")
    public byte[] generarReporteEstadoStock(
            @WebParam(name = "fechaInicio") String fechaInicio,
            @WebParam(name = "fechaFin") String fechaFin) {

        try {
            LocalDateTime inicio = parseFecha(fechaInicio);
            LocalDateTime fin = parseFecha(fechaFin);

            // Validar coherencia de fechas
            if (inicio != null && fin != null && fin.isBefore(inicio)) {
                System.err.println("Error: fechaFin debe ser posterior a fechaInicio");
                return null;
            }

            return ReporteUtil.reporteEstadoStock(inicio, fin);

        } catch (DateTimeParseException e) {
            System.err.println("Error: Formato de fecha inválido. Use ISO-8601 o dd/MM/yyyy HH:mm:ss");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("Error al generar reporte de stock: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parsea una fecha desde String a LocalDateTime Soporta múltiples formatos
     * y maneja null/vacío
     *
     * @param fechaStr String con la fecha
     * @return LocalDateTime parseado, o null si fechaStr es null/vacío
     * @throws DateTimeParseException si el formato es inválido
     */
    private LocalDateTime parseFecha(String fechaStr) throws DateTimeParseException {
        // Si es null o vacío, retornar null (traer todos los registros)
        if (fechaStr == null || fechaStr.trim().isEmpty()) {
            return null;
        }

        fechaStr = fechaStr.trim();

        // Intentar formato ISO primero (2024-01-15T10:30:00)
        try {
            return LocalDateTime.parse(fechaStr, FORMATTER_ISO);
        } catch (DateTimeParseException e1) {
            // Intentar formato personalizado con hora (15/01/2024 10:30:00)
            try {
                return LocalDateTime.parse(fechaStr, FORMATTER_CUSTOM);
            } catch (DateTimeParseException e2) {
                // Intentar formato solo fecha (15/01/2024) - agregar hora 00:00:00
                try {
                    return LocalDateTime.parse(fechaStr + " 00:00:00", FORMATTER_CUSTOM);
                } catch (DateTimeParseException e3) {
                    // Si ninguno funciona, lanzar la excepción original
                    throw new DateTimeParseException(
                            "Formato de fecha no válido. Use: ISO-8601 (2024-01-15T10:30:00) o dd/MM/yyyy HH:mm:ss",
                            fechaStr, 0
                    );
                }
            }
        }
    }
}
