using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using KawkiWebBusiness.KawkiWebWSEstilos;

namespace KawkiWebBusiness
{
    public class EstilosBO
    {
        private EstilosClient clienteSOAP;

        public EstilosBO()
        {
            this.clienteSOAP = new EstilosClient();
        }

        public int InsertarEstilo(string nombre)
        {
            return this.clienteSOAP.insertarEstilo(nombre);
        }

        public int ModificarEstilo(int estiloId, string nombreCategoria)
        {
            return this.clienteSOAP.modificarEstilo(estiloId, nombreCategoria);
        }

        public estilosDTO ObtenerPorIdCategoria(int estiloId)
        {
            return this.clienteSOAP.obtenerPorIdEstilo(estiloId);
        }

        public IList<estilosDTO> ListarTodosEstilo()
        {
            return this.clienteSOAP.listarTodosEstilo();
        }
    }
}
