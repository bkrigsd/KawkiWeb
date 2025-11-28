using KawkiWebBusiness.KawkiWebWSCategorias;
using KawkiWebBusiness.KawkiWebWSTiposCondicion;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace KawkiWebBusiness
{
    public class TiposCondicionBO
    {
        private TiposCondicionClient condicionSOAP;

        public TiposCondicionBO()
        {
            this.condicionSOAP = new TiposCondicionClient();
        }

        public tiposCondicionDTO obtenerPorIdTipoCondicion (int tipoCondicionId)
        {
            return this.condicionSOAP.obtenerPorIdTipoCondicion(tipoCondicionId);
        }

        public IList<tiposCondicionDTO> listarTodosTipoCondicion()
        {
            return this.condicionSOAP.listarTodosTipoCondicion();
        }
    }
}
