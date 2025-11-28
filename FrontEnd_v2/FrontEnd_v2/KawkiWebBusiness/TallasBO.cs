using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using KawkiWebBusiness.KawkiWebWSCategorias;
using KawkiWebBusiness.KawkiWebWSTallas;

namespace KawkiWebBusiness
{
    public class TallasBO
    {
        private TallasClient clienteSOAP;

        public TallasBO()
        {
            this.clienteSOAP = new TallasClient();
        }

        public int InsertarTalla(int numeroTalla)
        {
            return this.clienteSOAP.insertarTalla(numeroTalla);
        }

        public int ModificarTalla(int tallaId, int valorTalla)
        {
            return this.clienteSOAP.modificarTalla(tallaId, valorTalla);
        }

        public tallasDTO ObtenerPorIdTalla(int tallaId)
        {
            return this.clienteSOAP.obtenerPorIdTalla(tallaId);
        }

        public IList<tallasDTO> ListarTodosTalla()
        {
            return this.clienteSOAP.listarTodosTalla();
        }
    }
}
