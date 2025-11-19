using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using KawkiWebBusiness.KawkiWebWSProductos;
using KawkiWebBusiness.KawkiWebWSUsuarios;
using usuariosDTO = KawkiWebBusiness.KawkiWebWSProductos.usuariosDTO;

namespace KawkiWebBusiness
{
    public class ProductosBO
    {
        private ProductosClient clienteSOAP;

        public ProductosBO()
        {
            this.clienteSOAP = new ProductosClient();
        }

        /// Inserta un nuevo producto en la base de datos
        public int InsertarProducto(string descripcion, categoriasDTO categoria, estilosDTO estilo, 
            double precio_Venta, usuariosDTO usuario)
        {
            //return this.clienteSOAP.insertarProducto(descripcion, categoria, estilo, precioVenta, usuario);
            try
            {
                int resultado = this.clienteSOAP.insertarProducto(descripcion, categoria, estilo, precio_Venta, usuario);

                System.Diagnostics.Debug.WriteLine($"✅ Respuesta del servidor: {resultado}");

                return resultado;
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"❌ EXCEPCIÓN en InsertarProducto: {ex.Message}");
                System.Diagnostics.Debug.WriteLine($"StackTrace: {ex.StackTrace}");

                if (ex.InnerException != null)
                {
                    System.Diagnostics.Debug.WriteLine($"Inner Exception: {ex.InnerException.Message}");
                }

                return 0;
            }
        }

        /// Obtiene un producto por su ID
        public productosDTO ObtenerPorIdProducto(int productoId)
        {
            return this.clienteSOAP.obtenerPorIdProducto(productoId);
        }

        /// Lista todos los productos (con sus variantes)
        public IList<productosDTO> ListarTodosProducto()
        {
            return this.clienteSOAP.listarTodosProducto();
        }

        /// Modifica un producto existente
        public int ModificarProducto(int productoId, string descripcion, categoriasDTO categoria, estilosDTO estilo, double precioVenta, usuariosDTO usuario)
        {
            return this.clienteSOAP.modificarProducto(productoId, descripcion, categoria, estilo, precioVenta, usuario);
        }

        /// Elimina un producto por su ID
        //public int? Eliminar(int productoId)
        //{
        //    try
        //    {
        //        if (productoId <= 0)
        //        {
        //            System.Diagnostics.Debug.WriteLine("Error: ID de producto inválido");
        //            return null;
        //        }

        //        // Verificar si el producto tiene variantes
        //        var producto = this.ObtenerPorId(productoId);
        //        if (producto != null && producto.variantes != null && producto.variantes.Length > 0)
        //        {
        //            System.Diagnostics.Debug.WriteLine("Error: No se puede eliminar un producto con variantes asociadas");
        //            return null;
        //        }

        //        int resultado = this.clienteSOAP.eliminarProducto(productoId);
        //        return resultado > 0 ? (int?)resultado : null;
        //    }
        //    catch (Exception ex)
        //    {
        //        System.Diagnostics.Debug.WriteLine($"Error al eliminar producto: {ex.Message}");
        //        return null;
        //    }
        //}

        /// Verifica si un producto tiene stock disponible en alguna de sus variantes
        public bool TieneStockDisponibleProducto(int productoId)
        {
            return this.clienteSOAP.tieneStockDisponibleProducto(productoId);
        }

        /// Calcula el stock total de un producto sumando todas sus variantes
        public int CalcularStockTotalProducto(int productoId)
        {
            return this.clienteSOAP.calcularStockTotalProducto(productoId);
        }

        /// Lista productos por categoría
        public IList<productosDTO> ListarPorCategoriaProducto(int categoriaId)
        {
            try
            {
                if (categoriaId <= 0)
                {
                    return new List<productosDTO>();
                }

                var lista = this.clienteSOAP.listarPorCategoriaProducto(categoriaId);
                if (lista == null) return new List<productosDTO>();
                return new List<productosDTO>(lista);
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al listar productos por categoría: {ex.Message}");
                return new List<productosDTO>();
            }
        }

        /// Lista productos por estilo
        public IList<productosDTO> ListarPorEstiloProducto(int estiloId)
        {
            try
            {
                if (estiloId <= 0)
                {
                    return new List<productosDTO>();
                }

                var lista = this.clienteSOAP.listarPorEstiloProducto(estiloId);
                if (lista == null) return new List<productosDTO>();
                return new List<productosDTO>(lista);
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al listar productos por estilo: {ex.Message}");
                return new List<productosDTO>();
            }
        }

        /// Lista productos con stock bajo (tienen variantes con alerta de stock activada)
        public IList<productosDTO> ListarConStockBajoProducto()
        {
            try
            {
                var lista = this.clienteSOAP.listarConStockBajoProducto();
                if (lista == null) return new List<productosDTO>();
                return new List<productosDTO>(lista);
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al listar productos con stock bajo: {ex.Message}");
                return new List<productosDTO>();
            }
        }
        
    }
}
