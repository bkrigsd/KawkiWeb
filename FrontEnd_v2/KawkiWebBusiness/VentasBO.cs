using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using KawkiWebBusiness.KawkiWebWSUsuarios;
using KawkiWebBusiness.KawkiWebWSVentas;
using usuariosDTO = KawkiWebBusiness.KawkiWebWSVentas.usuariosDTO;

namespace KawkiWebBusiness
{
    public class VentasBO
    {
        private VentasClient clienteSOAP;

        public VentasBO()
        {
            this.clienteSOAP = new VentasClient();
        }

        public int InsertarVenta(usuariosDTO usuario, double total, descuentosDTO descuento)
        {
            return this.clienteSOAP.insertarVenta(usuario, total, descuento);
        }

        public int ModificarVenta(int ventaId, usuariosDTO usuario, double total, descuentosDTO descuento)
        {
            return this.clienteSOAP.modificarVenta(ventaId,usuario,total,descuento);
        }

        public ventasDTO ObtenerPorIdVenta(int ventaId)
        {
            return this.clienteSOAP.obtenerPorIdVenta(ventaId);
        }

        public IList<ventasDTO> ListarTodosVenta()
        {
            return this.clienteSOAP.listarTodosVenta();
        }

        public int EliminarVenta(int ventaId)
        {
            return this.clienteSOAP.eliminarVenta(ventaId);
        }
    }
}
