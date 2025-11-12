using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

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
        public int? Insertar(string descripcion, int categoriaId, int estiloId, 
            double precioVenta, DateTime? fechaHoraCreacion = null)
        {
            try
            {
                // El web service maneja las validaciones
                int resultado = this.clienteSOAP.insertar_producto(
                    descripcion, 
                    categoriaId, 
                    estiloId, 
                    precioVenta, 
                    fechaHoraCreacion ?? DateTime.Now
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
        public productoDTO ObtenerPorId(int? productoId)
        {
            try
            {
                if (productoId == null || productoId <= 0)
                {
                    System.Diagnostics.Debug.WriteLine("Error: ID de producto inválido");
                    return null;
                }

                return this.clienteSOAP.obtenerPorId(productoId);
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al obtener producto por ID: {ex.Message}");
                return null;
            }
        }

        /// Lista todos los productos (con sus variantes)
        public IList<productoDTO> ListarTodos()
        {
            try
            {
                var lista = this.clienteSOAP.listarTodos();
                return lista ?? new List<productoDTO>();
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al listar productos: {ex.Message}");
                return new List<productoDTO>();
            }
        }

        /// Modifica un producto existente
        public int? Modificar(int? productoId, string descripcion, int categoriaId, 
            int estiloId, double precioVenta, DateTime fechaHoraCreacion)
        {
            try
            {
                if (productoId == null || productoId <= 0)
                {
                    System.Diagnostics.Debug.WriteLine("Error: ID de producto inválido");
                    return null;
                }

                int resultado = this.clienteSOAP.modificar(
                    productoId, 
                    descripcion, 
                    categoriaId, 
                    estiloId, 
                    precioVenta, 
                    fechaHoraCreacion
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
        public int? Eliminar(int? productoId)
        {
            try
            {
                if (productoId == null || productoId <= 0)
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

                int resultado = this.clienteSOAP.eliminar(productoId);
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
                var producto = this.ObtenerPorId(productoId);

                if (producto == null || producto.variantes == null)
                {
                    return false;
                }

                return producto.variantes.Any(v => v.stock > 0);
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
                var producto = this.ObtenerPorId(productoId);

                if (producto == null || producto.variantes == null)
                {
                    return 0;
                }

                return producto.variantes.Sum(v => v.stock);
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al calcular stock total: {ex.Message}");
                return 0;
            }
        }

        /// Lista productos por categoría
        public IList<productoDTO> ListarPorCategoria(int? categoriaId)
        {
            try
            {
                if (categoriaId == null || categoriaId <= 0)
                {
                    return new List<productoDTO>();
                }

                var todosLosProductos = this.ListarTodos();
                
                return todosLosProductos
                    .Where(p => p.categoria != null && p.categoria.categoria_id == categoriaId)
                    .ToList();
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al listar productos por categoría: {ex.Message}");
                return new List<productoDTO>();
            }
        }

        /// Lista productos por estilo
        public IList<productoDTO> ListarPorEstilo(int? estiloId)
        {
            try
            {
                if (estidloId == null || estiloId <= 0)
                {
                    return new List<productoDTO>();
                }

                var todosLosProductos = this.ListarTodos();
                
                return todosLosProductos
                    .Where(p => p.estilo != null && p.estilo.estilo_id == estiloId)
                    .ToList();
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al listar productos por estilo: {ex.Message}");
                return new List<productoDTO>();
            }
        }

        /// Lista productos con stock bajo (tienen variantes con alerta de stock activada)
        public IList<productoDTO> ListarConStockBajo()
        {
            try
            {
                var todosLosProductos = this.ListarTodos();
                
                return todosLosProductos
                    .Where(p => p.variantes != null && 
                                p.variantes.Any(v => v.alerta_stock))
                    .ToList();
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al listar productos con stock bajo: {ex.Message}");
                return new List<productoDTO>();
            }
        }

        /// Lista productos con stock disponible
        public IList<productoDTO> ListarConStock()
        {
            try
            {
                var todosLosProductos = this.ListarTodos();
                
                return todosLosProductos
                    .Where(p => p.variantes != null && 
                                p.variantes.Any(v => v.stock > 0))
                    .ToList();
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al listar productos con stock: {ex.Message}");
                return new List<productoDTO>();
            }
        }
    }
}
