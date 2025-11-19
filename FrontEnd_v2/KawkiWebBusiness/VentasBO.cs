using KawkiWebBusiness.KawkiWebWSUsuarios;
using KawkiWebBusiness.KawkiWebWSVentas;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace KawkiWebBusiness
{
    public class VentasBO
    {
        private VentasClient clienteSOAP;

        public VentasBO()
        {
            this.clienteSOAP = new VentasClient();
        }

        public int InsertarVenta(KawkiWebWSVentas.usuariosDTO usuario, double total,
                                 descuentosDTO descuento, redesSocialesDTO redSocial)
        {
            return this.clienteSOAP.insertarVenta(usuario, total, descuento, redSocial);
        }

        public ventasDTO ObtenerPorIdVenta(int ventaId)
        {
            return this.clienteSOAP.obtenerPorIdVenta(ventaId);
        }

        public IList<ventasDTO> ListarTodosVenta()
        {
            return this.clienteSOAP.listarTodosVenta();
        }

        public int ModificarVenta(int ventaId, KawkiWebWSVentas.usuariosDTO usuario,
                                  double total, descuentosDTO descuento,
                                  redesSocialesDTO redSocial, bool esValida)
        {
            return this.clienteSOAP.modificarVenta(
                ventaId, usuario, total, descuento, redSocial, esValida
            );
        }

        // public int EliminarVenta(int ventaId)
        // {
        //     return this.clienteSOAP.eliminarVenta(ventaId);
        // }
    }
}
