using System.Collections.Generic;
using KawkiWebBusiness.KawkiWebWSDetalleVentas;

namespace KawkiWebBusiness.BO
{
    public class DetalleVentasBO
    {
        private DetalleVentasClient cliente;

        public DetalleVentasBO()
        {
            cliente = new DetalleVentasClient();
        }

        public int InsertarDetalleVenta(productosVariantesDTO productoVar, int ventaId, int cantidad,
            double precioUnitario, double subtotal)
        {
            return cliente.insertarDetalleVenta(productoVar,ventaId, cantidad, precioUnitario,subtotal);
        }

        public detalleVentasDTO ObtenerPorId(int detalleId)
        {
            return cliente.obtenerPorIdDetalleVenta(detalleId);
        }

        public List<detalleVentasDTO> ListarTodos()
        {
            var lista = cliente.listarTodosDetalleVenta();
            return new List<detalleVentasDTO>(lista);
        }

        public List<detalleVentasDTO> ListarPorVentaId(int ventaId)
        {
            var lista = cliente.listarPorVentaIdDetalleVenta(ventaId);
            return new List<detalleVentasDTO>(lista);
        }

        public int ModificarDetalleVenta(int detalleId, productosVariantesDTO productoVar,int ventaId,int cantidad,
            double precioUnitario,double subtotal)
        {
            return cliente.modificarDetalleVenta(detalleId, productoVar,ventaId, cantidad, precioUnitario,subtotal);
        }
    }
}

