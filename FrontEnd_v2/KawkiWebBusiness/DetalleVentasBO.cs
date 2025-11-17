using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using KawkiWebBusiness.KawkiWebWSDetalleVentas;
using KawkiWebBusiness.KawkiWebWSProductos;
using KawkiWebBusiness.KawkiWebWSVentas;
using detalleVentasDTO = KawkiWebBusiness.KawkiWebWSDetalleVentas.detalleVentasDTO;
using productosVariantesDTO = KawkiWebBusiness.KawkiWebWSDetalleVentas.productosVariantesDTO;

namespace KawkiWebBusiness
{
    public class DetalleVentasBO
    {
        private DetalleVentasClient clienteSOAP;

        public DetalleVentasBO()
        {
            this.clienteSOAP = new DetalleVentasClient();
        }

        public int InsertarDetalleVenta(productosVariantesDTO productoVAR, int ventaId, int cantidad, double precioUnitario, double subtotal)
        {
            return this.clienteSOAP.insertarDetalleVenta(productoVAR, ventaId, cantidad, precioUnitario, subtotal);
        }
        
        public int ModificarDetalleVenta(int detalleVentaId,productosVariantesDTO productoVAR, int ventaId, int cantidad, double precioUnitario, double subtotal)
        {
            return this.clienteSOAP.modificarDetalleVenta(detalleVentaId, productoVAR, ventaId, cantidad, precioUnitario, subtotal);
        }

        public detalleVentasDTO ObtenerPorIdDetalleVenta(int detalleVentaId)
        {
                return this.clienteSOAP.obtenerPorIdDetalleVenta(detalleVentaId);
        }

        public IList<detalleVentasDTO> listarTodos()
        {
            return this.clienteSOAP.listarTodosDetalleVenta();
        }

        public IList<detalleVentasDTO> listarPorVentaId(int ventaId)
        {
            return this.clienteSOAP.listarPorVentaIdDetalleVenta(ventaId);
        }
    }
}
