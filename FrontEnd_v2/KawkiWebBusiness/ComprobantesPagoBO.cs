using KawkiWebBusiness.KawkiWebWSComprobantesPago;
using System.Collections.Generic;

namespace KawkiWebBusiness
{
    public class ComprobantesPagoBO
    {
        private ComprobantesPagoClient clienteSOAP;

        public ComprobantesPagoBO()
        {
            this.clienteSOAP = new ComprobantesPagoClient();
        }

        // Insertar comprobante de pago
        public int InsertarComprobantePago(
            tiposComprobanteDTO tipoComprobante,
            string dniCliente,
            string nombreCliente,
            string rucCliente,
            string razonSocialCliente,
            string direccionFiscalCliente,
            string telefonoCliente,
            double total,
            ventasDTO venta,
            metodosPagoDTO metodoPago)
        {
            return this.clienteSOAP.insertarComprobPago(
                tipoComprobante,
                dniCliente,
                nombreCliente,
                rucCliente,
                razonSocialCliente,
                direccionFiscalCliente,
                telefonoCliente,
                total,
                venta,
                metodoPago
            );
        }

        // Obtener comprobante por ID
        public comprobantesPagoDTO ObtenerPorIdComprobante(int comprobanteId)
        {
            return this.clienteSOAP.obtenerPorIdComprobPago(comprobanteId);
        }

        // Listar todos
        public IList<comprobantesPagoDTO> ListarTodosComprobantes()
        {
            return this.clienteSOAP.listarTodosComprobantePago();
        }

        // Modificar comprobante
        public int ModificarComprobantePago(
            int comprobanteId,
            tiposComprobanteDTO tipoComprobante,
            string dniCliente,
            string nombreCliente,
            string rucCliente,
            string razonSocialCliente,
            string direccionFiscalCliente,
            string telefonoCliente,
            double total,
            ventasDTO venta,
            metodosPagoDTO metodoPago
        )
        {
            return this.clienteSOAP.modificarComprobPago(
                comprobanteId,
                tipoComprobante,
                dniCliente,
                nombreCliente,
                rucCliente,
                razonSocialCliente,
                direccionFiscalCliente,
                telefonoCliente,
                total,
                venta,
                metodoPago
            );
        }
    }
}
