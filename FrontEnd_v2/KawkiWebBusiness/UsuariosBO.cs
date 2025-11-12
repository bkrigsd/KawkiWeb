using System;
using System.Collections.Generic;
using KawkiWebBusiness.KawkiWebWSUsuarios;

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
                                           string contrasenha, tiposUsuarioDTO tipoUsuario)
        {
            return this.clienteSOAP.insertarUsuario(nombre, apePaterno, dni, telefono, correo,
                                                    nombreUsuario, contrasenha, tipoUsuario);
        }
        

        public int ModificarUsuario(int usuarioId, string nombre, string apePaterno, string dni,
                                    string telefono, string correo, string nombreUsuario,
                                    string contrasenha, tiposUsuarioDTO tipoUsuario)
        {
            return this.clienteSOAP.modificarUsuario(usuarioId, nombre, apePaterno, dni, telefono,
                                                     correo, nombreUsuario, contrasenha, tipoUsuario);
        }

        public int EliminarUsuario(int usuarioId)
        {
            return this.clienteSOAP.eliminarUsuario(usuarioId);
        }

        public usuariosDTO ObtenerPorIdUsuario(int usuarioId)
        {
            return this.clienteSOAP.obtenerPorIdUsuario(usuarioId);
        }

        public IList<usuariosDTO> ListarTodosUsuario()
        {
            return this.clienteSOAP.listarTodosUsuario();
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
    }
}

