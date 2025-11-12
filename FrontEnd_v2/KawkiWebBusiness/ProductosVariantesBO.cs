using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace KawkiWebBusiness
{
    public class ProductosVariantesBO
    {
        private ProductosVariantesClient clienteSOAP;

        public ProductosVariantesBO()
        {
            this.clienteSOAP = new ProductosVariantesClient();
        }

        /// <summary>
        /// Inserta una nueva variante de producto

        public int? Insertar(string sku, int stock, int stockMinimo,
            int productoId, ColoresDTO color, TallasDTO talla, string urlImagen, bool disponible = true)
        {
            try
            {
                // El web service maneja las validaciones
                // La alerta de stock se calcula automáticamente en el backend
                int resultado = this.clienteSOAP.insertarProdVariante(
                    sku,
                    stock,
                    stockMinimo,
                    productoId,
                    color,
                    talla,
                    urlImagen,
                    disponible
                );

                return resultado > 0 ? (int?)resultado : null;
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al insertar variante: {ex.Message}");
                return null;
            }
        }

        /// <summary>
        /// Obtiene una variante de producto por su ID

        public productosVariantesDTO ObtenerPorId(int? prodVarianteId)
        {
            try
            {
                if (prodVarianteId == null || prodVarianteId <= 0)
                {
                    System.Diagnostics.Debug.WriteLine("Error: ID de variante inválido");
                    return null;
                }

                return this.clienteSOAP.obtenerPorIdProdVariante(prodVarianteId);
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al obtener variante por ID: {ex.Message}");
                return null;
            }
        }

        /// <summary>
        /// Lista todas las variantes de producto

        public IList<productosVariantesDTO> ListarTodos()
        {
            try
            {
                var lista = this.clienteSOAP.listarTodosProdVariante();
                return lista ?? new List<productosVariantesDTO>();
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al listar variantes: {ex.Message}");
                return new List<productosVariantesDTO>();
            }
        }

        /// <summary>
        /// Modifica una variante de producto existente

        public int? Modificar(int? prodVarianteId, string sku, int stock,
            int stockMinimo, int productoId, ColoresDTO color, TallasDTO talla,
            string urlImagen, bool disponible)
        {
            try
            {
                if (prodVarianteId == null || prodVarianteId <= 0)
                {
                    System.Diagnostics.Debug.WriteLine("Error: ID de variante inválido");
                    return null;
                }

                // La alerta de stock se recalcula automáticamente en el backend
                int resultado = this.clienteSOAP.modificarProdVariante(
                    prodVarianteId,
                    sku,
                    stock,
                    stockMinimo,
                    productoId,
                    color,
                    talla,
                    urlImagen,
                    disponible
                );

                return resultado > 0 ? (int?)resultado : null;
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al modificar variante: {ex.Message}");
                return null;
            }
        }

        /// <summary>
        /// Elimina una variante de producto por su ID

        public int? Eliminar(int? prodVarianteId)
        {
            try
            {
                if (prodVarianteId == null || prodVarianteId <= 0)
                {
                    System.Diagnostics.Debug.WriteLine("Error: ID de variante inválido");
                    return null;
                }

                int resultado = this.clienteSOAP.eliminarProdVariante(prodVarianteId);
                return resultado > 0 ? (int?)resultado : null;
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al eliminar variante: {ex.Message}");
                return null;
            }
        }

        /// <summary>
        /// Actualiza solo el stock de una variante
        /// El backend recalcula automáticamente la alerta de stock

        public bool ActualizarStock(int? prodVarianteId, int? nuevoStock)
        {
            try
            {
                if (prodVarianteId == null || nuevoStock == null || nuevoStock < 0)
                {
                    System.Diagnostics.Debug.WriteLine("Error: Parámetros inválidos para actualizar stock");
                    return false;
                }

                return this.clienteSOAP.actualizarStockProdVariante(prodVarianteId, nuevoStock);

            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al actualizar stock: {ex.Message}");
                return false;
            }
        }

        /// <summary>
        /// Lista todas las variantes con stock bajo (alerta activada)

        public IList<productosVariantesDTO> ListarConStockBajo()
        {
            try
            {
                var lista = this.clienteSOAP.listarConStockBajoProdVariante();
                return lista ?? new List<productosVariantesDTO>();
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al listar variantes con stock bajo: {ex.Message}");
                return new List<productosVariantesDTO>();
            }
        }

        /// <summary>
        /// Lista variantes de un producto específico

        public IList<productosVariantesDTO> ListarPorProducto(int? productoId)
        {
            try
            {
                if (productoId == null || productoId <= 0)
                {
                    return new List<productosVariantesDTO>();
                }

                return this.clienteSOAP.listarPorProductoProdVariante(productoId);
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al listar variantes por producto: {ex.Message}");
                return new List<productosVariantesDTO>();
            }
        }

        /// <summary>
        /// Lista variantes por color

        public IList<productosVariantesDTO> ListarPorColor(int? colorId)
        {
            try
            {
                if (colorId == null || colorId <= 0)
                {
                    return new List<productosVariantesDTO>();
                }

                var lista = this.clienteSOAP.listarPorColorProdVariante(colorId);
                return lista ?? new List<productosVariantesDTO>();
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al listar variantes por color: {ex.Message}");
                return new List<productosVariantesDTO>();
            }
        }

        /// <summary>
        /// Lista variantes por talla

        public IList<productosVariantesDTO> ListarPorTalla(int? tallaId)
        {
            try
            {
                if (tallaId == null || tallaId <= 0)
                {
                    return new List<productosVariantesDTO>();
                }

                var lista = this.clienteSOAP.listarPorTallaProdVariante(tallaId);
                return lista ?? new List<productosVariantesDTO>();
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al listar variantes por talla: {ex.Message}");
                return new List<productosVariantesDTO>();
            }
        }

        /// <summary>
        /// Verifica si una variante tiene stock disponible

        public bool TieneStockDisponible(int prodVarianteId)
        {
            try
            {
                return this.clienteSOAP.tieneStockDisponibleProdVariante(prodVarianteId);
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Error al verificar stock disponible: {ex.Message}");
                return false;
            }
        }
        
    }
}
