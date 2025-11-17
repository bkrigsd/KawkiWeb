using KawkiWebBusiness.KawkiWebWSUsuarios;
using System;
using System.Collections.Generic;
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

        public String validarNombre(string nombre) {
            return this.clienteSOAP.validarNombre(nombre);
        }
    
        public String validarApellidoPaterno(string apePaterno) {
            return this.clienteSOAP.validarApellidoPaterno(apePaterno);
        }

        public String validarNombreUsuario(string nombreUsuario) {
            return this.clienteSOAP.validarNombreUsuario(nombreUsuario);
        }

        public String validarDni(string dni) {
            return this.clienteSOAP.validarDni(dni);
        }

        public String validarCorreo(string correo) {
            return this.clienteSOAP.validarCorreo(correo);
        }

        public String validarTelefono(string telefono)
        {
            return this.clienteSOAP.validarTelefono(telefono);
        }

    }
}