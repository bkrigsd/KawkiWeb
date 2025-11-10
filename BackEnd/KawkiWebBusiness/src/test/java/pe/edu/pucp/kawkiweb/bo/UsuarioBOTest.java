//
//
//package pe.edu.pucp.kawkiweb.bo;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import pe.edu.pucp.kawkiweb.model.utilUsuario.TipoUsuario;
//import pe.edu.pucp.kawkiweb.model.UsuarioDTO;
//
//
//public class UsuarioBOTest {
//    
//    private UsuarioBO usuarioBO;
//    
//    public UsuarioBOTest() {
//        this.usuarioBO = new UsuarioBO();
//    }
//    
//    @Test
//    public void testInsertar() {
//        
//        System.out.println("Insertar");
//        ArrayList<Integer>listaUsuatiosID = new ArrayList<>();
//        this.insertarUsuarios(listaUsuatiosID);
//        this.eliminarTodo();
//    }
//    
//    private void insertarUsuarios(ArrayList<Integer> listaUsuariosId){
//        
//        UsuarioDTO usuarioDTO = new UsuarioDTO();
//        
//        usuarioDTO.setNombre("Eros");
//        usuarioDTO.setApePaterno("Sotelo");
//        usuarioDTO.setApeMaterno("La Torre");
//        usuarioDTO.setDni("77722211");
//        usuarioDTO.setFechaNacimiento(LocalDate.now());
//        usuarioDTO.setTelefono("999111555");
//        usuarioDTO.setDireccion("Calle 20. Las Casuarinas, La Molina");
//        usuarioDTO.setCorreo("soygenialGaa@gmail.com");
//        usuarioDTO.setNombreUsuario("LalaMasna");
//        usuarioDTO.setContrasenha("jijiji321");
//        usuarioDTO.setFechaHoraCreacion(LocalDateTime.now());
//        usuarioDTO.setTipoUsuario(TipoUsuario.CLIENTE);
////        usuarioDTO.setRuc("10234567893");
////        usuarioDTO.setRazon_social("Kawki Zapateria");
////        usuarioDTO.setDireccion_fiscal("Caminos del Inca, San Borja calle 13");   
//        Integer resultado = this.usuarioBO.Insertar(usuarioDTO.getNombre(),
//                usuarioDTO.getApePaterno(),usuarioDTO.getApeMaterno(),usuarioDTO.getDni(),
//                usuarioDTO.getFechaNacimiento(),usuarioDTO.getTelefono(),
//                usuarioDTO.getDireccion(),usuarioDTO.getCorreo(),
//                usuarioDTO.getNombreUsuario(),usuarioDTO.getContrasenha(),
//                usuarioDTO.getFechaHoraCreacion(),usuarioDTO.getTipoUsuario());
//        
//        assertTrue(resultado != 0);
//        listaUsuariosId.add(resultado);
//    }
//    
//    
//    @Test
//    public void testObtenerPorId() {
//        
//        System.out.println("test obtenerPorId");
//        ArrayList<Integer> listaUsuariosId = new ArrayList<>();
//        insertarUsuarios(listaUsuariosId);
//        UsuarioDTO usuarioDTO = this.usuarioBO.obtenerPorId(listaUsuariosId.get(0));
//        assertEquals(usuarioDTO.getUsuarioId(), listaUsuariosId.get(0));
//        
//        eliminarTodo();
//    }
//    
//    
//    @Test
//    public void testListarTodos(){
//        System.out.println("ListarTodos");
//        ArrayList<Integer> listaUsuariosId = new ArrayList<>();
//        this.insertarUsuarios(listaUsuariosId);
//        
//        ArrayList<UsuarioDTO> listaUsuarios = this.usuarioBO.listarTodos();
//        assertEquals(listaUsuariosId.size(),listaUsuarios.size());
//        for(int i = 0; i<listaUsuariosId.size();i++){
//            assertEquals(listaUsuariosId.get(i), listaUsuarios.get(i).getUsuarioId());
//        }
//        this.eliminarTodo();
//    }
//    
//    
//    //@Test
//    public void testModificar() {
//        System.out.println("modificar");
//        ArrayList<Integer> listaUsuariosId = new ArrayList<>();
//        this.insertarUsuarios(listaUsuariosId);
//        
//        ArrayList<UsuarioDTO> listaUsuarios = this.usuarioBO.listarTodos();
//        assertEquals(listaUsuariosId.size(),listaUsuarios.size());
//           
//        listaUsuarios.get(0).setNombre("Pepe");
//        listaUsuarios.get(0).setTelefono("988877611");
//        int resultado = this.usuarioBO.modificar(listaUsuarios.get(0).getUsuarioId(),listaUsuarios.get(0).getNombre(),
//                listaUsuarios.get(0).getApePaterno(),listaUsuarios.get(0).getApeMaterno(),listaUsuarios.get(0).getDni(),
//                listaUsuarios.get(0).getFechaNacimiento(),listaUsuarios.get(0).getTelefono(),
//                listaUsuarios.get(0).getDireccion(),listaUsuarios.get(0).getCorreo(),
//                listaUsuarios.get(0).getNombreUsuario(),listaUsuarios.get(0).getContrasenha(),
//                listaUsuarios.get(0).getFechaHoraCreacion(),listaUsuarios.get(0).getTipoUsuario());
//        assertTrue(resultado!=0);
//        this.eliminarTodo();
//    }
//    
//    
//    @Test
//    public void testEliminar(){
//        System.out.println("Eliminar");
//        ArrayList<Integer> listaUsuarioId = new ArrayList<>();
//        insertarUsuarios(listaUsuarioId);
//        this.eliminarTodo();
//    }
//    
//    
//    private void eliminarTodo(){                
//        ArrayList<UsuarioDTO> listaUsuarios = this.usuarioBO.listarTodos();
//        for (Integer i = 0; i < listaUsuarios.size(); i++) {
//            Integer resultado = this.usuarioBO.eliminar(listaUsuarios.get(i).getUsuarioId());
//            assertNotEquals(0, resultado);
//            UsuarioDTO usuarioDTO = this.usuarioBO.obtenerPorId(listaUsuarios.get(i).getUsuarioId());
//            assertNull(usuarioDTO);
//        }
//    }
//
//    
//}
