using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using KawkiWebBusiness.KawkiWebWSProductos;

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
        public int? Insertar(string descripcion, categoriasDTO categoria, estilosDTO estilo, 
            double precioVenta)
        {
            try
            {
                // El web service maneja las validaciones
                int resultado = this.clienteSOAP.insertarProducto(
                    descripcion, 
                    categoria, 
                    estilo, 
                    precioVenta
                );

                return resultado > 0 ? (int?)resultado : null;
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al insertar producto: {ex.Message}");
                return null;
            }
        }

        /// Obtiene un producto por su ID
        public productosDTO ObtenerPorId(int productoId)
        {
            try
            {
                if (productoId <= 0)
                {
                    System.Diagnostics.Debug.WriteLine("Error: ID de producto inválido");
                    return null;
                }

                return this.clienteSOAP.obtenerPorIdProducto(productoId);
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al obtener producto por ID: {ex.Message}");
                return null;
            }
        }

        /// Lista todos los productos (con sus variantes)
        public IList<productosDTO> ListarTodos()
        {
            try
            {
                var lista = this.clienteSOAP.listarTodosProducto();
                return lista != null ? new List<productosDTO>(lista) : new List<productosDTO>();
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al listar productos: {ex.Message}");
                return new List<productosDTO>();
            }
        }

        /// Modifica un producto existente
        public int? Modificar(int? productoId, string descripcion, categoriasDTO categoria, estilosDTO estilo, double precioVenta)
        {
            try
            {
                if (productoId == null || productoId <= 0)
                {
                    System.Diagnostics.Debug.WriteLine("Error: ID de producto inválido");
                    return null;
                }

                int resultado = this.clienteSOAP.modificarProducto(
                    productoId, 
                    descripcion, 
                    categoria, 
                    estilo, 
                    precioVenta
                );

                return resultado > 0 ? (int?)resultado : null;
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al modificar producto: {ex.Message}");
                return null;
            }
        }

        /// Elimina un producto por su ID
        public int? Eliminar(int productoId)
        {
            try
            {
                if (productoId <= 0)
                {
                    System.Diagnostics.Debug.WriteLine("Error: ID de producto inválido");
                    return null;
                }

                // Verificar si el producto tiene variantes
                var producto = this.ObtenerPorId(productoId);
                if (producto != null && producto.variantes != null && producto.variantes.Length > 0)
                {
                    System.Diagnostics.Debug.WriteLine("Error: No se puede eliminar un producto con variantes asociadas");
                    return null;
                }

                int resultado = this.clienteSOAP.eliminarProducto(productoId);
                return resultado > 0 ? (int?)resultado : null;
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al eliminar producto: {ex.Message}");
                return null;
            }
        }

        /// Verifica si un producto tiene stock disponible en alguna de sus variantes
        public bool TieneStockDisponible(int productoId)
        {
            try
            {
                return this.clienteSOAP.tieneStockDisponibleProducto(productoId);
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al verificar stock del producto: {ex.Message}");
                return false;
            }
        }

        /// Calcula el stock total de un producto sumando todas sus variantes
        public int CalcularStockTotal(int productoId)
        {
            try
            {
                return this.clienteSOAP.calcularStockTotalProducto(productoId);
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al calcular stock total: {ex.Message}");
                return 0;
            }
        }

        /// Lista productos por categoría
        public IList<productosDTO> ListarPorCategoria(int categoriaId)
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
        public IList<productosDTO> ListarPorEstilo(int estiloId)
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
        public IList<productosDTO> ListarConStockBajo()
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
