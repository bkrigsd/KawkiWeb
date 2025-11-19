using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using KawkiWebBusiness;
using KawkiWebBusiness.KawkiWebWSProductosVariantes;
using coloresDTO = KawkiWebBusiness.KawkiWebWSProductosVariantes.coloresDTO;
using tallasDTO = KawkiWebBusiness.KawkiWebWSProductosVariantes.tallasDTO;
using usuariosDTO = KawkiWebBusiness.KawkiWebWSProductosVariantes.usuariosDTO;

namespace KawkiWeb
{
    public partial class GestionVariantes : Page
    {
        private ProductosVariantesBO variantesBO;
        private ProductosBO productosBO;
        private ColoresBO coloresBO;
        private TallasBO tallasBO;
        private int productoId = 0;

        protected void Page_Load(object sender, EventArgs e)
        {
            variantesBO = new ProductosVariantesBO();
            productosBO = new ProductosBO();
            coloresBO = new ColoresBO();
            tallasBO = new TallasBO();

            if (string.IsNullOrEmpty(Request.QueryString["productoId"]))
            {
                Response.Redirect("GestionProductos.aspx");
                return;
            }

            productoId = Convert.ToInt32(Request.QueryString["productoId"]);
            hfProductoId.Value = productoId.ToString();

            if (!IsPostBack)
            {
                CargarDatosIniciales();
            }
        }

        private void CargarDatosIniciales()
        {
            try
            {
                var producto = productosBO.ObtenerPorId(productoId);
                if (producto == null)
                {
                    MostrarError("Producto no encontrado");
                    Response.Redirect("GestionProductos.aspx");
                    return;
                }

                lblTituloProducto.Text = $"{producto.categoria?.nombre ?? ""} {producto.estilo?.nombre ?? ""}".Trim();
                lblProductoInfo.Text = $"📦 {producto.descripcion} | Precio: S/. {producto.precio_venta:N2}";
                lblProductoModal.Text = producto.descripcion;

                CargarColores();
                CargarTallas();
                CargarVariantes();
            }
            catch (Exception ex)
            {
                MostrarError("Error al cargar datos: " + ex.Message);
            }
        }

        private void CargarColores()
        {
            var lista = coloresBO.ListarTodosColor();
            ddlColor.DataSource = lista;
            ddlColor.DataTextField = "nombre";
            ddlColor.DataValueField = "color_id";
            ddlColor.DataBind();
            ddlColor.Items.Insert(0, new ListItem("-- Seleccione un color --", "0"));
        }

        private void CargarTallas()
        {
            var lista = tallasBO.ListarTodosTalla();
            ddlTalla.DataSource = lista;
            ddlTalla.DataTextField = "numero";
            ddlTalla.DataValueField = "talla_id";
            ddlTalla.DataBind();
            ddlTalla.Items.Insert(0, new ListItem("-- Seleccione una talla --", "0"));
        }

        private void CargarVariantes()
        {
            try
            {
                var variantes = variantesBO.ListarPorProducto(productoId);

                var variantesAgrupadas = variantes
                    .GroupBy(v => v.color?.color_id ?? 0)
                    .Select(g => new
                    {
                        ColorId = g.Key,
                        ColorNombre = g.First().color?.nombre ?? "Sin color",
                        UrlImagen = g.First().url_imagen ?? "",
                        Tallas = g.Select(t => new
                        {
                            VarianteId = t.prod_variante_id,
                            ColorNombre = t.color?.nombre ?? "Sin color",
                            ColorId = t.color?.color_id ?? 0,
                            TallaId = t.talla?.talla_id ?? 0,
                            TallaNombre = t.talla?.numero ?? 0,
                            Stock = t.stock,
                            StockMinimo = t.stock_minimo,
                            AlertaStock = t.alerta_stock,
                            Disponible = t.disponible,
                            UrlImagen = t.url_imagen ?? ""
                        }).OrderBy(t => t.TallaNombre).ToList()
                    }).ToList();

                rptColores.DataSource = variantesAgrupadas;
                rptColores.DataBind();

                lblTotalVariantes.Text = variantesAgrupadas.Count.ToString();
            }
            catch (Exception ex)
            {
                MostrarError("Error al cargar variantes: " + ex.Message);
            }
        }

        protected void btnGuardar_Click(object sender, EventArgs e)
        {
            lblMensaje.Text = "";
            lblMensaje.CssClass = "text-danger d-block mb-2";

            try
            {
                int colorId = Convert.ToInt32(ddlColor.SelectedValue);
                string urlImagen = txtUrlImagen.Text.Trim();
                bool disponible = Request.Form["hdnDisponible"] == "true";
                var usuario = ObtenerUsuarioSesion();

                if (usuario == null || usuario.usuarioId <= 0)
                {
                    lblMensaje.Text = "⚠ Error: No hay sesión de usuario válida.";
                    MantenerModalAbierto();
                    return;
                }

                if (colorId == 0)
                {
                    lblMensaje.Text = "⚠ Debe seleccionar un color.";
                    MantenerModalAbierto();
                    return;
                }

                // Verificar si ya existe este color para el producto
                var variantes = variantesBO.ListarPorProducto(productoId);
                if (variantes.Any(v => v.color?.color_id == colorId))
                {
                    lblMensaje.Text = "⚠ Este color ya existe para este producto. Seleccione otro color.";
                    MantenerModalAbierto();
                    return;
                }

                string tallasText = txtTallas.Text.Trim();
                string stocksText = txtStocks.Text.Trim();
                string stocksMinimoText = txtStocksMinimos.Text.Trim();

                if (string.IsNullOrEmpty(tallasText) || string.IsNullOrEmpty(stocksText))
                {
                    lblMensaje.Text = "⚠ Debe ingresar tallas y stocks.";
                    MantenerModalAbierto();
                    return;
                }

                if (string.IsNullOrEmpty(urlImagen))
                {
                    lblMensaje.Text = "⚠ Debe ingresar una URL para la imagen del producto.";
                    MantenerModalAbierto();
                    return;
                }

                var tallasStocks = ParsearTallasYStocks(tallasText, stocksText, stocksMinimoText);

                if (tallasStocks.Count == 0)
                {
                    lblMensaje.Text = "⚠ Formato inválido o tallas no válidas. Verifique los datos.";
                    MantenerModalAbierto();
                    return;
                }

                var color = new coloresDTO { color_id = colorId };
                string urlCompleta = "/Images/Productos/" + urlImagen;

                int insertadas = 0;
                int fallidas = 0;
                List<string> errores = new List<string>();

                foreach (var item in tallasStocks)
                {
                    int tallaId = item.TallaId;
                    int stock = item.Stock;
                    int stockMinimo = item.StockMinimo;

                    if (stock < 0)
                    {
                        lblMensaje.Text = "⚠ El stock no puede ser negativo.";
                        MantenerModalAbierto();
                        return;
                    }

                    if (stockMinimo < 0)
                    {
                        lblMensaje.Text = "⚠ El stock mínimo no puede ser negativo.";
                        MantenerModalAbierto();
                        return;
                    }

                    var talla = new tallasDTO { talla_id = tallaId };

                    // ✅ IGUAL QUE EN USUARIOS: Capturar y verificar el resultado
                    int? resultado = variantesBO.Insertar(
                        stock,
                        stockMinimo,
                        productoId,
                        color,
                        talla,
                        urlCompleta,
                        disponible,
                        usuario
                    );

                    if (resultado == null || resultado <= 0)
                    {
                        fallidas++;
                        errores.Add($"Talla {tallaId}: No se pudo insertar (puede que ya exista esta combinación)");
                    }
                    else
                    {
                        insertadas++;
                    }
                }

                // Mostrar resultado
                if (insertadas > 0 && fallidas == 0)
                {
                    // Todo bien
                    lblMensaje.CssClass = "text-success d-block mb-2";
                    lblMensaje.Text = $"✓ {insertadas} variante(s) creada(s) correctamente.";
                    LimpiarFormulario();
                    CargarVariantes();

                    ScriptManager.RegisterStartupScript(
                        this,
                        GetType(),
                        "CerrarModal",
                        "cerrarModal();",
                        true
                    );
                }
                else if (insertadas > 0 && fallidas > 0)
                {
                    // Algunas se insertaron, otras no
                    lblMensaje.CssClass = "text-warning d-block mb-2";
                    lblMensaje.Text = $"⚠ Se insertaron {insertadas} de {tallasStocks.Count} variantes.<br/>" +
                                    string.Join("<br/>", errores);
                    CargarVariantes();
                    MantenerModalAbierto();
                }
                else
                {
                    // Ninguna se insertó
                    lblMensaje.CssClass = "text-danger d-block mb-2";
                    lblMensaje.Text = "❌ No se pudo crear ninguna variante.<br/>" +
                                    string.Join("<br/>", errores);
                    MantenerModalAbierto();
                }
            }
            catch (Exception ex)
            {
                lblMensaje.CssClass = "text-danger d-block mb-2";
                lblMensaje.Text = "❌ Error: " + ex.Message;
                MantenerModalAbierto();
            }
        }

        protected void btnGuardarStock_Click(object sender, EventArgs e)
        {
            lblMensajeStock.Text = "";
            lblMensajeStock.CssClass = "text-danger d-block mb-2";

            try
            {
                int varianteId = Convert.ToInt32(hfVarianteId.Value);

                string stockText = txtStockEditar.Text?.Trim() ?? "";
                string stockMinimoText = txtStockMinimoEditar.Text?.Trim() ?? "";

                if (string.IsNullOrEmpty(stockText))
                {
                    lblMensajeStock.Text = "⚠ Debe ingresar un valor para el stock.";
                    return;
                }

                if (string.IsNullOrEmpty(stockMinimoText))
                {
                    lblMensajeStock.Text = "⚠ Debe ingresar un valor para el stock mínimo.";
                    return;
                }

                if (!int.TryParse(stockText, out int nuevoStock))
                {
                    lblMensajeStock.Text = "⚠ El stock debe ser un número válido.";
                    return;
                }

                if (!int.TryParse(stockMinimoText, out int nuevoStockMinimo))
                {
                    lblMensajeStock.Text = "⚠ El stock mínimo debe ser un número válido.";
                    return;
                }

                var usuario = ObtenerUsuarioSesion();

                if (nuevoStock < 0)
                {
                    lblMensajeStock.Text = "⚠ El stock no puede ser negativo.";
                    return;
                }

                if (nuevoStockMinimo < 0)
                {
                    lblMensajeStock.Text = "⚠ El stock mínimo no puede ser negativo.";
                    return;
                }

                var variante = variantesBO.ObtenerPorId(varianteId);
                if (variante == null)
                {
                    lblMensajeStock.Text = "⚠ Variante no encontrada.";
                    return;
                }

                // ✅ IGUAL QUE EN USUARIOS: Capturar y verificar el resultado
                int? resultado = variantesBO.Modificar(
                    varianteId,
                    nuevoStock,
                    nuevoStockMinimo,
                    variante.producto_id,
                    variante.color,
                    variante.talla,
                    variante.url_imagen,
                    variante.disponible,
                    usuario
                );

                if (resultado == null || resultado <= 0)
                {
                    lblMensajeStock.CssClass = "text-danger d-block mb-2";
                    lblMensajeStock.Text = "❌ No se pudo actualizar el stock. Verifique que los datos sean válidos.";
                    return;
                }

                lblMensajeStock.CssClass = "text-success d-block mb-2";
                lblMensajeStock.Text = "✓ Stock actualizado correctamente.";

                CargarVariantes();

                ScriptManager.RegisterStartupScript(
                    this,
                    GetType(),
                    "CerrarModalStock",
                    "cerrarModalStock();",
                    true
                );
            }
            catch (Exception ex)
            {
                lblMensajeStock.CssClass = "text-danger d-block mb-2";
                lblMensajeStock.Text = "❌ Error: " + ex.Message;
            }
        }

        protected void btnGuardarTalla_Click(object sender, EventArgs e)
        {
            lblMensajeTalla.Text = "";
            lblMensajeTalla.CssClass = "text-danger d-block mb-2";

            try
            {
                int colorId = Convert.ToInt32(hfColorIdTalla.Value);
                int tallaId = Convert.ToInt32(ddlTalla.SelectedValue);
                int stock = Convert.ToInt32(txtStockTalla.Text.Trim());
                int stockMinimo = Convert.ToInt32(txtStockMinimoTalla.Text.Trim());
                var usuario = ObtenerUsuarioSesion();

                if (tallaId == 0)
                {
                    lblMensajeTalla.Text = "⚠ Seleccione una talla.";
                    return;
                }

                if (stock < 0)
                {
                    lblMensajeTalla.Text = "⚠ El stock no puede ser negativo.";
                    return;
                }

                if (stockMinimo < 0)
                {
                    lblMensajeTalla.Text = "⚠ El stock mínimo no puede ser negativo.";
                    return;
                }

                var variantes = variantesBO.ListarPorProducto(productoId);

                // Verificar si ya existe esta combinación
                if (variantes.Any(v => v.color?.color_id == colorId && v.talla?.talla_id == tallaId))
                {
                    lblMensajeTalla.Text = "⚠ Esta talla ya existe para este color. Seleccione otra talla.";
                    return;
                }

                // Obtener la URL de imagen del color existente
                var varianteConMismoColor = variantes.FirstOrDefault(v => v.color?.color_id == colorId);
                string urlImagen = varianteConMismoColor?.url_imagen ?? "";

                if (string.IsNullOrEmpty(urlImagen))
                {
                    lblMensajeTalla.Text = "⚠ No se encontró URL de imagen para este color.";
                    return;
                }

                var color = new coloresDTO { color_id = colorId };
                var talla = new tallasDTO { talla_id = tallaId };

                // ✅ IGUAL QUE EN USUARIOS: Capturar y verificar el resultado
                int? resultado = variantesBO.Insertar(
                    stock,
                    stockMinimo,
                    productoId,
                    color,
                    talla,
                    urlImagen,
                    true,
                    usuario
                );

                if (resultado == null || resultado <= 0)
                {
                    lblMensajeTalla.CssClass = "text-danger d-block mb-2";
                    lblMensajeTalla.Text = "❌ No se pudo agregar la talla. Verifique que los datos sean válidos.";
                    return;
                }

                lblMensajeTalla.CssClass = "text-success d-block mb-2";
                lblMensajeTalla.Text = "✓ Talla agregada correctamente.";

                CargarVariantes();

                ScriptManager.RegisterStartupScript(
                    this,
                    GetType(),
                    "CerrarModalTalla",
                    "cerrarModalTalla();",
                    true
                );
            }
            catch (Exception ex)
            {
                lblMensajeTalla.CssClass = "text-danger d-block mb-2";
                lblMensajeTalla.Text = "❌ Error: " + ex.Message;
            }
        }

        private List<TallaStockItem> ParsearTallasYStocks(string tallasText, string stocksText, string stocksMinimoText)
        {
            var resultado = new List<TallaStockItem>();
            var tallas = tallasText.Split(new[] { ',' }, System.StringSplitOptions.RemoveEmptyEntries);
            var stocks = stocksText.Split(new[] { ',' }, System.StringSplitOptions.RemoveEmptyEntries);
            var stocksMinimos = stocksMinimoText.Split(new[] { ',' }, System.StringSplitOptions.RemoveEmptyEntries);

            var tallasValidas = tallasBO.ListarTodosTalla();

            for (int i = 0; i < tallas.Length; i++)
            {
                if (!int.TryParse(tallas[i].Trim(), out int tallaNum))
                    continue;

                int stock = 0;
                if (i < stocks.Length && int.TryParse(stocks[i].Trim(), out int s))
                    stock = s;

                int stockMinimo = 5;
                if (i < stocksMinimos.Length && int.TryParse(stocksMinimos[i].Trim(), out int sm))
                    stockMinimo = sm;

                var tallaBD = tallasValidas.FirstOrDefault(t => t.numero == tallaNum);

                if (tallaBD != null)
                {
                    resultado.Add(new TallaStockItem
                    {
                        TallaId = tallaBD.talla_id,
                        Stock = stock,
                        StockMinimo = stockMinimo
                    });
                }
            }

            return resultado;
        }

        protected void btnVolver_Click(object sender, EventArgs e)
        {
            Response.Redirect("GestionProductos.aspx");
        }

        private void MantenerModalAbierto()
        {
            ScriptManager.RegisterStartupScript(this, GetType(), "MantenerModal", "abrirModalRegistro();", true);
        }

        private void LimpiarFormulario()
        {
            ddlColor.SelectedIndex = 0;
            txtTallas.Text = "";
            txtStocks.Text = "";
            txtStocksMinimos.Text = "";
            txtUrlImagen.Text = "";
            lblMensaje.Text = "";
        }

        private usuariosDTO ObtenerUsuarioSesion()
        {
            return new usuariosDTO
            {
                usuarioId = Convert.ToInt32(Session["UsuarioId"])
            };
        }

        private void MostrarError(string mensaje)
        {
            ScriptManager.RegisterStartupScript(this, GetType(), "Error",
                $"mostrarMensajeError('{mensaje.Replace("'", "\\'")}');", true);
        }

        private void MostrarExito(string mensaje)
        {
            ScriptManager.RegisterStartupScript(this, GetType(), "Exito",
                $"mostrarMensajeExito('{mensaje.Replace("'", "\\'")}');", true);
        }

        protected string RenderVariantesConRowspan(object dataItem)
        {
            var grupo = dataItem as dynamic;
            var tallas = grupo.Tallas as System.Collections.IEnumerable;

            if (tallas == null) return "";

            var tallasList = tallas.Cast<dynamic>().ToList();
            int rowspan = tallasList.Count;

            System.Text.StringBuilder html = new System.Text.StringBuilder();

            for (int i = 0; i < tallasList.Count; i++)
            {
                var talla = tallasList[i];

                html.Append("<tr>");

                if (i == 0)
                {
                    string urlImagen = string.IsNullOrEmpty(talla.UrlImagen.ToString())
                        ? "images/no-image.png"
                        : talla.UrlImagen.ToString();

                    html.AppendFormat(@"
                <td rowspan='{0}' style='text-align: center;'>
                    <img src='{1}' alt='{2}' class='img-color' />
                </td>
                <td rowspan='{0}'>
                    <div style='display: flex; justify-content: space-between; align-items: center; gap: 10px;'>
                        <span>{2}</span>
                        <button type='button' class='btn-agregar btn-sm' 
                            onclick='abrirModalAgregarTalla(""{3}"", ""{2}"")' 
                            title='Agregar otra talla a este color'>
                            <i class='fas fa-plus'></i>
                        </button>
                    </div>
                </td>",
                        rowspan,
                        urlImagen,
                        talla.ColorNombre,
                        grupo.ColorId
                    );
                }

                html.AppendFormat(@"
            <td><strong>{0}</strong></td>
            <td style='text-align: center;'><strong>{1}</strong></td>
            <td style='text-align: center;'>{2}</td>
            <td style='text-align: center;'>
                {3}
            </td>",
                    talla.TallaNombre,
                    talla.Stock,
                    talla.StockMinimo,
                    ((bool)talla.AlertaStock && talla.Stock < talla.StockMinimo) ? "<span class='badge-alerta badge-bajo'>⚠ Bajo</span>" : ""
                );

                string activeNo = !(bool)talla.Disponible ? "active" : "";
                string activeSi = (bool)talla.Disponible ? "active" : "";

                html.AppendFormat(@"
            <td style='text-align: center;'>
                <div class='toggle-disponible'>
                    <button type='button' class='btn-no {0}'
                        onclick='cambiarDisponibilidad(this, ""{1}"", false)'>
                        <i class='fas fa-times'></i> No
                    </button>
                    <button type='button' class='btn-si {2}'
                        onclick='cambiarDisponibilidad(this, ""{1}"", true)'>
                        <i class='fas fa-check'></i> Sí
                    </button>
                </div>
            </td>",
                    activeNo,
                    talla.VarianteId,
                    activeSi
                );

                html.AppendFormat(@"
            <td>
                <button type='button' class='btn-editar btn-sm' 
                    onclick='abrirModalEditarStock(""{0}"", ""{1}"", ""{2}"")'>
                    <i class='fas fa-edit'></i> Editar
                </button>
            </td>",
                    talla.VarianteId,
                    talla.ColorNombre,
                    talla.TallaNombre
                );

                html.Append("</tr>");
            }

            return html.ToString();
        }
    }

    public class TallaStockItem
    {
        public int TallaId { get; set; }
        public int Stock { get; set; }
        public int StockMinimo { get; set; }
    }
}