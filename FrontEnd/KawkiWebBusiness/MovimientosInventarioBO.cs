using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using KawkiWebBusiness.KawkiWebWSEstilos;
using KawkiWebBusiness.KawkiWebWSMovimientosInventario;
using KawkiWebBusiness.KawkiWebWSUsuarios;
using usuariosDTO = KawkiWebBusiness.KawkiWebWSMovimientosInventario.usuariosDTO;

namespace KawkiWebBusiness
{
    public class MovimientosInventarioBO
    {
        private MovimientosInventarioClient clienteSOAP;

        public MovimientosInventarioBO()
        {
            this.clienteSOAP = new MovimientosInventarioClient();
        }
        public int InsertarMovInventario(int cantidad, string observacion, tiposMovimientoDTO tipo_movimiento, productosVariantesDTO prod_variante, usuariosDTO usuario)
        {
            return this.clienteSOAP.insertarMovInventario(cantidad, observacion,tipo_movimiento,prod_variante,usuario);
        }

        public int ModificarMovInventario(int movInventarioId, int cantidad, string observacion, tiposMovimientoDTO tipo_movimiento, productosVariantesDTO prod_variante, usuariosDTO usuario)
        {
            return this.clienteSOAP.modificarMovInventario(movInventarioId, cantidad, observacion, tipo_movimiento, prod_variante, usuario);
        }

        public movimientosInventarioDTO ObtenerPorIdMovInventario(int movInventarioId)
        {
            return this.clienteSOAP.obtenerPorIdMovInventario(movInventarioId);
        }

        public IList<movimientosInventarioDTO> ListarTodosMovInventario()
        {
            return this.clienteSOAP.listarTodosMovInventario();
        }

        public int CrearAjusteInventarioMovInventario(productosVariantesDTO prod_variante, int stockReal, string observacion, usuariosDTO usuario)
        {
            return this.clienteSOAP.crearAjusteInventarioMovInventario(prod_variante,stockReal,observacion,usuario);
        }

        public int RegistrarIngresoMovInventario(productosVariantesDTO prod_variante, int cantidad, string observacion, usuariosDTO usuario)
        {
            return this.clienteSOAP.registrarIngresoMovInventario(prod_variante, cantidad, observacion, usuario);
        }

        public int RegistrarSalidaMovInventario(productosVariantesDTO prod_variante, int cantidad, string observacion, usuariosDTO usuario)
        {
            return this.clienteSOAP.registrarSalidaMovInventario(prod_variante, cantidad, observacion, usuario);
        }

        public IList<movimientosInventarioDTO> ListarPorProductoVarianteMovInventario(int prod_variante_id)
        {
            return this.clienteSOAP.listarPorProductoVarianteMovInventario(prod_variante_id);
        }

        public IList<movimientosInventarioDTO> ListarPorRangoFechasMovInventario(localDateTime fechaInicio, localDateTime fechafin)
        {
            return this.clienteSOAP.listarPorRangoFechasMovInventario(fechaInicio, fechafin);
        }

        public IList<movimientosInventarioDTO> ListarPorTipoMovimientoMovInventario(int prod_variante_id)
        {
            return this.clienteSOAP.listarPorTipoMovimientoMovInventario(prod_variante_id);
        }

        public IList<movimientosInventarioDTO> ListarPorUsuarioMovInventario(int usuario_id)
        {
            return this.clienteSOAP.listarPorUsuarioMovInventario(usuario_id);
        }

        public IList<movimientosInventarioDTO> ListarMovimientosRecientesMovInventario(int limite)
        {
            return this.clienteSOAP.listarMovimientosRecientesMovInventario(limite);
        }
    }
}
