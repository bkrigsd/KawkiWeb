package pe.edu.pucp.kawkiweb.reports;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
     * Convierte LocalDateTime a java.util.Date (compatible con JasperReports)
     * Si el parámetro es null, retorna null
     */
    private static Date convertirADate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Genera el reporte de ventas y tendencias de productos Si fechaInicio y
     * fechaFin son null, trae TODOS los registros sin filtrar
     *
     * @param fechaInicio Fecha y hora de inicio del periodo (puede ser null)
     * @param fechaFin Fecha y hora de fin del periodo (puede ser null)
     * @return byte[] con el PDF del reporte, o null si hay error
     */
    public static byte[] reporteVentasYTendencias(
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin
    ) {
        HashMap<String, Object> parametros = new HashMap<>();

        // Usar nombres con prefijo 'p' como está en el Jasper
        parametros.put("pfechaInicio", convertirADate(fechaInicio));
        parametros.put("pfechaFin", convertirADate(fechaFin));

        return invocarReporte("reporte_ventas_tendencias", parametros);
    }

    /**
     * Genera el reporte de estado de stock y productos en riesgo Si fechaInicio
     * y fechaFin son null, incluye todos los movimientos sin filtrar
     *
     * @param fechaInicio Fecha y hora de inicio del periodo (puede ser null)
     * @param fechaFin Fecha y hora de fin del periodo (puede ser null)
     * @return byte[] con el PDF del reporte, o null si hay error
     */
    public static byte[] reporteEstadoStock(
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin
    ) {
        HashMap<String, Object> parametros = new HashMap<>();

        //Usar nombres con prefijo 'p'
        parametros.put("pfechaInicio", convertirADate(fechaInicio));
        parametros.put("pfechaFin", convertirADate(fechaFin));

        return invocarReporte("reporte_estado_stock", parametros);
    }

}
