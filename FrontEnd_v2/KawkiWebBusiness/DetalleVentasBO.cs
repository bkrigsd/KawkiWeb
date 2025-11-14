using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using KawkiWebBusiness.KawkiWebWSDetalleVentas;

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


    }
}
