using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using KawkiWebBusiness.KawkiWebWSTiposBeneficio;

namespace KawkiWebBusiness
{
    public class TiposBeneficioBO
    {
        private TiposBeneficioClient beneficioSOAP;
        public TiposBeneficioBO()
        {
            this.beneficioSOAP = new TiposBeneficioClient();
        }
        public tiposBeneficioDTO obtenerPorIdTipoBeneficio(int tipoBeneficioId)
        {
            return this.beneficioSOAP.obtenerPorIdTipoBeneficio(tipoBeneficioId);
        }
        public IList<tiposBeneficioDTO> listarTodosTipoBeneficio()
        {
            return this.beneficioSOAP.listarTodosTipoBeneficio();
        }
    }

}
