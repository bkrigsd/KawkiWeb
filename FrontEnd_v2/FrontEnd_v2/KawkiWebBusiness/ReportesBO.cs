using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using KawkiWebBusiness.KawkiWebWSProductos;
using KawkiWebBusiness.KawkiWebWSReportes;

namespace KawkiWebBusiness
{
    public class ReportesBO
    {
        private ReportesClient clienteSOAP;

        public ReportesBO()
        {
            this.clienteSOAP = new ReportesClient();
        }
        public byte[] GenerarReporteVentasYTendencias(string fechaInicio, string fechaFin)
        {
            return this.clienteSOAP.generarReporteVentasYTendencias(fechaInicio,fechaFin);
        }

        public byte[] GenerarReporteEstadoStocks(string fechaInicio, string fechaFin)
        {
            return this.clienteSOAP.generarReporteEstadoStock(fechaInicio,fechaFin);
        }
    }
}
