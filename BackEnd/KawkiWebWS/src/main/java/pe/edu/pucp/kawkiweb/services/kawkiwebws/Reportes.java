package pe.edu.pucp.kawkiweb.services.kawkiwebws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.time.LocalDateTime;
import pe.edu.pucp.kawkiweb.reports.ReporteUtil;

@WebService(serviceName = "ReportesService")
public class Reportes {

    @WebMethod(operationName = "generarReporteVentasYTendencias")
    public byte[] generarReporteVentasYTendencias(
            @WebParam(name = "fechaInicio") String fechaInicio,
            @WebParam(name = "fechaFin") String fechaFin) {

        try {
            LocalDateTime inicio = LocalDateTime.parse(fechaInicio);
            LocalDateTime fin = LocalDateTime.parse(fechaFin);

            return ReporteUtil.reporteVentasYTendencias(inicio, fin);
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
            LocalDateTime inicio = LocalDateTime.parse(fechaInicio);
            LocalDateTime fin = LocalDateTime.parse(fechaFin);

            return ReporteUtil.reporteEstadoStock(inicio, fin);
        } catch (Exception e) {
            System.err.println("Error al generar reporte de stock: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
