using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using KawkiWebBusiness.KawkiWebWSCategorias;
using KawkiWebBusiness.KawkiWebWSVentas;

namespace KawkiWebBusiness
{
    public class CategoriasBO
    {
        private CategoriasClient clienteSOAP;

        public CategoriasBO()
        {
            this.clienteSOAP = new CategoriasClient();
        }

        public int InsertarCategoria(string nombre)
        {
            return this.clienteSOAP.insertarCategoria(nombre);
        }

        public int ModificarCategoria(int categoriaId, string nombreCategoria)
        {
            return this.clienteSOAP.modificarCategoria(categoriaId, nombreCategoria);
        }

        public categoriasDTO ObtenerPorIdCategoria(int categoriaId)
        {
            return this.clienteSOAP.obtenerPorIdCategoria(categoriaId);
        }

        public IList<categoriasDTO> ListarTodosCategoria()
        {
            return this.clienteSOAP.listarTodosCategoria();
        }
    }
}
