using KawkiWebBusiness.KawkiWebWSUsuarios;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Xml.Linq;

namespace KawkiWebBusiness
{
    public class UsuarioBO
    {
        private UsuariosClient clienteSOAP;

        public UsuarioBO()
        {
            this.clienteSOAP = new UsuariosClient();
        }

        public int InsertarUsuario(string nombre, string apePaterno, string dni,
                                   string telefono, string correo, string nombreUsuario,
                                   string contrasenha, tiposUsuarioDTO tipoUsuario, bool activo)
        {
            return this.clienteSOAP.insertarUsuario(nombre, apePaterno, dni, telefono, correo,
                                                    nombreUsuario, contrasenha, tipoUsuario, activo);
        }

        public int ModificarUsuario(int usuarioId, string nombre, string apePaterno, string dni,
                                    string telefono, string correo, string nombreUsuario,
                                    string contrasenha, tiposUsuarioDTO tipoUsuario, bool activo)
        {
            return this.clienteSOAP.modificarUsuario(usuarioId, nombre, apePaterno, dni, telefono,
                                                     correo, nombreUsuario, contrasenha, tipoUsuario, activo);
        }

        // public int EliminarUsuario(int usuarioId)
        // {
        //     return this.clienteSOAP.eliminarUsuario(usuarioId);
        // }

        public usuariosDTO ObtenerPorIdUsuario(int usuarioId)
        {
            return this.clienteSOAP.obtenerPorIdUsuario(usuarioId);
        }

        public IList<usuariosDTO> ListarTodosUsuario()
        {
            return this.clienteSOAP.listarTodosUsuario();
        }

        public IList<usuariosDTO> ListarVendedoresActivos()
        {
            // 1 podría ser un enum TipoUsuario.Vendedor
            const int TIPO_VENDEDOR = 1;

            var usuarios = this.clienteSOAP.listarPorTipoUsuario(TIPO_VENDEDOR);

            return usuarios
                .Where(u => u != null && u.activo)
                .OrderBy(u => u.nombre)
                .ThenBy(u => u.apePaterno)
                .ToList();
        }

        public IList<usuariosDTO> ListarPorTipoUsuario(int tipoUsuarioId)
        {
            return this.clienteSOAP.listarPorTipoUsuario(tipoUsuarioId);
        }

        public bool CambiarContrasenhaUsuario(int usuarioId, string contrasenhaActual, string contrasenhaNueva)
        {
            return this.clienteSOAP.cambiarContrasenhaUsuario(usuarioId, contrasenhaActual, contrasenhaNueva);
        }

        public usuariosDTO AutenticarUsuario(string nombreUsuario, string contrasenha)
        {
            return this.clienteSOAP.autenticarUsuario(nombreUsuario, contrasenha);
        }

        public bool[] VerificarUnicidad(string correo, string nombreUsuario, string dni, int? usuarioIdExcluir)
        {
            try
            {
                UsuariosClient ws = new UsuariosClient();
                int idExclusion = usuarioIdExcluir ?? -1;

                // Llamada al WebService → devuelve bool?[]
                bool?[] respuestaNullable = ws.verificarUnicidad(
                    correo,
                    nombreUsuario,
                    dni,
                    idExclusion
                );

                // Convertir nullable a no-nullable
                bool[] respuesta = respuestaNullable
                    .Select(v => v.HasValue ? v.Value : false)
                    .ToArray();

                return respuesta;
            }
            catch
            {
                return new bool[] { false, false, false };
            }
        }

    }
}
