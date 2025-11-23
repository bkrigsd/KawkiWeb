using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using KawkiWebBusiness.KawkiWebWSCategorias;
using KawkiWebBusiness.KawkiWebWSColores;

namespace KawkiWebBusiness
{
    public class ColoresBO
    {
        private ColoresClient clienteSOAP;

        public ColoresBO()
        {
            this.clienteSOAP = new ColoresClient();
        }

        public int InsertarColor(string nombre)
        {
            return this.clienteSOAP.insertarColor(nombre);
        }

        public int ModificarColor(int colorId, string nombreColor)
        {
            return this.clienteSOAP.modificarColor(colorId, nombreColor);
        }

        public coloresDTO ObtenerPorIdColor(int colorId)
        {
            return this.clienteSOAP.obtenerPorIdColor(colorId);
        }

        public IList<coloresDTO> ListarTodosColor()
        {
            return this.clienteSOAP.listarTodosColor();
        }
    }
}
