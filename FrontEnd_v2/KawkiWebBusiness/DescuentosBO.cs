using KawkiWebBusiness.KawkiWebWSDescuentos;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace KawkiWebBusiness
{
    public class DescuentosBO
    {
        private DescuentosClient descuentoSOAP;

        public DescuentosBO()
        {
            this.descuentoSOAP = new DescuentosClient();
        }

        public int insertarDescuento(string descripcion, tiposCondicionDTO tipo_condicion, int valor_condicion, tiposBeneficioDTO tipo_beneficio, int valor_beneficio, 
                                     DateTime fechaInicioStr, DateTime fechaFinStr, bool activo)
        {
            string fecha_inicio = fechaInicioStr.ToString("yyyy-MM-ddTHH:mm:ss");
            string fecha_fin = fechaFinStr.ToString("yyyy-MM-ddTHH:mm:ss");
            return this.descuentoSOAP.insertarDescuento(descripcion, tipo_condicion, valor_condicion, tipo_beneficio, valor_beneficio, fecha_inicio, fecha_fin, activo);
        }

        public descuentosDTO obtenerPorIdDescuento(int descuentoId)
        {
            return this.descuentoSOAP.obtenerPorIdDescuento(descuentoId);
        }

        public List<descuentosDTO> listarTodosDescuento()
        {
            var array = this.descuentoSOAP.listarTodosDescuento();
            return new List<descuentosDTO>(array);
        }

        public int modificarDescuento(int descuentoId, string descripcion, tiposCondicionDTO tipo_condicion, int valor_condicion, tiposBeneficioDTO tipo_beneficio, int valor_beneficio, 
                                      DateTime fechaInicioStr, DateTime fechaFinStr, bool activo)
        {
            string fecha_inicio = fechaInicioStr.ToString("yyyy-MM-ddTHH:mm:ss");
            string fecha_fin = fechaFinStr.ToString("yyyy-MM-ddTHH:mm:ss");
            return this.descuentoSOAP.modificarDescuento(descuentoId, descripcion, tipo_condicion, valor_condicion, tipo_beneficio, valor_beneficio, fecha_inicio, fecha_fin, activo);
        }

        public bool activarDescuento(int descuentoId)
        {
            return this.descuentoSOAP.activarDescuento(descuentoId);
        }

        public bool desactivarDescuento(int descuentoId)
        {
            return this.descuentoSOAP.desactivarDescuento(descuentoId);
        }

        public List<descuentosDTO> listarActivasDescuento()
        {
            var array = this.descuentoSOAP.listarActivasDescuento();
            return new List<descuentosDTO>(array);
        }

        public List<descuentosDTO> listarVigentesDescuento()
        {
            var array = this.descuentoSOAP.listarVigentesDescuento();
            return new List<descuentosDTO>(array);
        }

        public bool esAplicableDescuento(int descuentoId, int cantidadProductos, double montoTotal)
        {
            return this.descuentoSOAP.esAplicableDescuento(descuentoId, cantidadProductos, montoTotal);
        }

        public double calcularDescuentoDescuento(int descuentoId, double montoTotal)
        {
            return this.descuentoSOAP.calcularDescuentoDescuento(descuentoId, montoTotal);
        }

    }
}
