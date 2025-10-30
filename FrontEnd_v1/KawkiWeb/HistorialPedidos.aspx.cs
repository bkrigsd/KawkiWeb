using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace KawkiWeb
{
    public partial class HistorialPedidos : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarHistorialPedidos();
            }
        }

        private void CargarHistorialPedidos()
        {
            // Datos de ejemplo para el diseño de interfaz
            List<Pedido> pedidos = ObtenerPedidosEjemplo();

            if (pedidos.Count > 0)
            {
                rptPedidos.DataSource = pedidos;
                rptPedidos.DataBind();
                pnlSinPedidos.Visible = false;
            }
            else
            {
                pnlSinPedidos.Visible = true;
            }
        }

        private List<Pedido> ObtenerPedidosEjemplo()
        {
            // Datos de ejemplo con diferentes estados
            List<Pedido> pedidos = new List<Pedido>
            {
                new Pedido
                {
                    NumeroPedido = "PED-2025-001",
                    Fecha = new DateTime(2025, 06, 25),
                    Estado = "Entregado",
                    Total = 671.70m,
                    Productos = new List<ProductoPedido>
                    {
                        new ProductoPedido { Cantidad = 2, Nombre = "Oxford Premium Negro", Precio = 250.90m },
                        new ProductoPedido { Cantidad = 1, Nombre = "Derby Clasico Negro", Precio = 169.90m }
                    }
                },
                new Pedido
                {
                    NumeroPedido = "PED-2025-002",
                    Fecha = new DateTime(2025, 07, 10),
                    Estado = "Pendiente",
                    Total = 537.70m,
                    Productos = new List<ProductoPedido>
                    {
                        new ProductoPedido { Cantidad = 1, Nombre = "Derby Elegante Marrón", Precio = 215.90m },
                        new ProductoPedido { Cantidad = 2, Nombre = "Oxford Bicolor Café", Precio = 160.90m }
                    }
                },
                new Pedido
                {
                    NumeroPedido = "PED-2025-003",
                    Fecha = new DateTime(2025, 08, 28),
                    Estado = "Anulado",
                    Total = 150.90m,
                    Productos = new List<ProductoPedido>
                    {
                        new ProductoPedido { Cantidad = 1, Nombre = "Oxford Clásico Beige", Precio = 150.90m }
                    }
                }
            };

            return pedidos;
        }

        protected string GetEstadoStyle(string estado)
        {
            // Retorna el estilo CSS inline según el estado del pedido
            switch (estado.ToLower())
            {
                case "pendiente":
                    return "background-color: #fff3cd; color: #856404;";
                case "pagado":
                    return "background-color: #d1ecf1; color: #0c5460;";
                case "preparacion":
                    return "background-color: #d1d3e2; color: #383d41;";
                case "listo para entregar":
                    return "background-color: #c3e6cb; color: #155724;";
                case "entregado":
                    return "background-color: #d4edda; color: #155724;";
                case "anulado":
                    return "background-color: #f8d7da; color: #721c24;";
                default:
                    return "background-color: #e2e3e5; color: #383d41;";
            }
        }

        protected void btnVerComprobante_Click(object sender, EventArgs e)
        {
            // Por ahora este botón no hace nada (funcionalidad futura)
            // Se implementará cuando se desarrolle la generación de comprobantes
            Button btn = (Button)sender;
            string numeroPedido = btn.CommandArgument;
            
            // Aquí iría la lógica para mostrar el comprobante
        }

        // Clases auxiliares para los datos de ejemplo
        public class Pedido
        {
            public string NumeroPedido { get; set; }
            public DateTime Fecha { get; set; }
            public string Estado { get; set; }
            public decimal Total { get; set; }
            public List<ProductoPedido> Productos { get; set; }
        }

        public class ProductoPedido
        {
            public int Cantidad { get; set; }
            public string Nombre { get; set; }
            public decimal Precio { get; set; }
        }
    }
}