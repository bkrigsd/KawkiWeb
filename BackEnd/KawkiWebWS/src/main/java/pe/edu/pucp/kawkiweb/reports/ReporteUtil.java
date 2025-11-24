package pe.edu.pucp.kawkiweb.reports;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import pe.edu.pucp.kawkiWeb.db.DBManager;

public class ReporteUtil {

    private static byte[] invocarReporte(String nombreReporte) {
        return invocarReporte(nombreReporte, null);
    }

    private static byte[] invocarReporte(String nombreReporte, HashMap parametros) {
        byte[] reporte = null;
        Connection conexion = DBManager.getInstance().getConnection();
        String nmReporte = "/" + nombreReporte + ".jasper";
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(ReporteUtil.class.getResource(nmReporte));
            JasperPrint jp = JasperFillManager.fillReport(jr, parametros, conexion);
            reporte = JasperExportManager.exportReportToPdf(jp);
        } catch (JRException ex) {
            System.getLogger(ReporteUtil.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            try {
                conexion.close();
            } catch (SQLException ex) {
                System.getLogger(ReporteUtil.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        return reporte;
    }

    /**
     * Genera el reporte de ventas y tendencias de productos
     *
     * @param fechaInicio Fecha y hora de inicio del periodo
     * @param fechaFin Fecha y hora de fin del periodo
     * @return byte[] con el PDF del reporte
     */
    public static byte[] reporteVentasYTendencias(
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin
    ) {
        HashMap<String, Object> parametros = new HashMap<>();

        // Convertir LocalDateTime a java.sql.Timestamp para JasperReports
        parametros.put("pFechaInicio", java.sql.Timestamp.valueOf(fechaInicio));
        parametros.put("pFechaFin", java.sql.Timestamp.valueOf(fechaFin));

        // Parámetros adicionales para mostrar en el reporte
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        parametros.put("pFechaInicioTexto", fechaInicio.format(formatter));
        parametros.put("pFechaFinTexto", fechaFin.format(formatter));

        return invocarReporte("reporte_ventas_tendencias", parametros);
    }

    /**
     * Genera el reporte de estado de stock y productos en riesgo
     *
     * @param fechaInicio Fecha y hora de inicio del periodo
     * @param fechaFin Fecha y hora de fin del periodo
     * @return byte[] con el PDF del reporte
     */
    public static byte[] reporteEstadoStock(
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin
    ) {
        HashMap<String, Object> parametros = new HashMap<>();

        parametros.put("pFechaInicio", java.sql.Timestamp.valueOf(fechaInicio));
        parametros.put("pFechaFin", java.sql.Timestamp.valueOf(fechaFin));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        parametros.put("pFechaInicioTexto", fechaInicio.format(formatter));
        parametros.put("pFechaFinTexto", fechaFin.format(formatter));

        return invocarReporte("reporte_estado_stock", parametros);
    }

//    public static byte[] reportePorTipoUsuario() {
//        return invocarReporte("reporte1");
//    }
//
//    public static byte[] reportePorParametros(Integer parametro) {
//        HashMap parametros = new HashMap();
//        parametros.put("pParametro", parametro);
//        return invocarReporte("reporte_parametros", parametros);
//    }
}
