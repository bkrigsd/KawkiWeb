using System.Collections.Generic;
using KawkiWebBusiness.KawkiWebWSComprobantesPago;

namespace KawkiWebBusiness.BO
{
    public class ComprobantesPagoBO
    {
        private ComprobantesPagoClient cliente;

        public ComprobantesPagoBO()
        {
            cliente = new ComprobantesPagoClient();
        }

        public int InsertarComprobante(
            tiposComprobanteDTO tipoComprobante,
            string dniCliente,
            string nombreCliente,
            string rucCliente,
            string razonSocial,
            string direccionFiscal,
            string telefono,
            double total,
            ventasDTO venta,
            metodosPagoDTO metodoPago)
        {
            return cliente.insertarComprobPago(
                tipoComprobante,
                dniCliente,
                nombreCliente,
                rucCliente,
                razonSocial,
                direccionFiscal,
                telefono,
                total,
                venta,
                metodoPago
            );
        }

        public comprobantesPagoDTO ObtenerPorId(int comprobanteId)
        {
            return cliente.obtenerPorIdComprobPago(comprobanteId);
        }

        public List<comprobantesPagoDTO> ListarTodos()
        {
            var array = cliente.listarTodosComprobantePago();
            return new List<comprobantesPagoDTO>(array);
        }

        public int ModificarComprobante(
            int comprobanteId,
            tiposComprobanteDTO tipoComprobante,
            string dniCliente,
            string nombreCliente,
            string rucCliente,
            string razonSocial,
            string direccionFiscal,
            string telefono,
            double total,
            ventasDTO venta,
            metodosPagoDTO metodoPago)
        {
            return cliente.modificarComprobPago(
                comprobanteId,
                tipoComprobante,
                dniCliente,
                nombreCliente,
                rucCliente,
                razonSocial,
                direccionFiscal,
                telefono,
                total,
                venta,
                metodoPago
            );
        }

        public comprobantesPagoDTO ObtenerPorVentaId(int ventaId)
        {
            return cliente.obtenerPorVentaIdComprobPago(ventaId);
        }
    }
}
