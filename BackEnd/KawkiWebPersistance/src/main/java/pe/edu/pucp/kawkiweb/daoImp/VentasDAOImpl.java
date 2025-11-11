package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.DetalleVentasDTO;
import pe.edu.pucp.kawkiweb.model.VentasDTO;
import pe.edu.pucp.kawkiweb.model.DescuentosDTO;
import pe.edu.pucp.kawkiweb.model.UsuariosDTO;
import pe.edu.pucp.kawkiweb.dao.DescuentosDAO;
import pe.edu.pucp.kawkiweb.dao.DetalleVentasDAO;
import pe.edu.pucp.kawkiweb.dao.UsuariosDAO;
import pe.edu.pucp.kawkiweb.dao.VentasDAO;

public class VentasDAOImpl extends BaseDAOImpl implements VentasDAO {

    private VentasDTO venta;
    private UsuariosDAO usuarioDAO;
    private DescuentosDAO descuentoDAO;
    private DetalleVentasDAO detalleVentaDAO;

    public VentasDAOImpl() {
        super("VENTAS");
        this.venta = null;
        this.retornarLlavePrimaria = true;
        this.usuarioDAO = new UsuariosDAOImpl();
        this.descuentoDAO = new DescuentosDAOImpl();
        this.detalleVentaDAO = new DetalleVentasDAOImpl();
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("VENTA_ID", true, true));
        this.listaColumnas.add(new Columna("USUARIO_ID", false, false));
        this.listaColumnas.add(new Columna("FECHA_HORA_CREACION", false, false));
        this.listaColumnas.add(new Columna("TOTAL", false, false));
        this.listaColumnas.add(new Columna("DESCUENTO_ID", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.venta.getUsuario().getUsuarioId());
        this.statement.setTimestamp(2, java.sql.Timestamp.valueOf(this.venta.getFecha_hora_creacion()));
        this.statement.setDouble(3, this.venta.getTotal());

        DescuentosDTO descuento = this.venta.getDescuento();
        if (descuento != null && descuento.getDescuento_id() != null) {
            this.statement.setInt(4, descuento.getDescuento_id());
        } else {
            this.statement.setNull(4, java.sql.Types.INTEGER);
        }
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.venta.getUsuario().getUsuarioId());
        this.statement.setTimestamp(2, java.sql.Timestamp.valueOf(this.venta.getFecha_hora_creacion()));
        this.statement.setDouble(3, this.venta.getTotal());

        DescuentosDTO descuento = this.venta.getDescuento();
        if (descuento != null && descuento.getDescuento_id() != null) {
            this.statement.setInt(4, descuento.getDescuento_id());
        } else {
            this.statement.setNull(4, java.sql.Types.INTEGER);
        }

        this.statement.setInt(5, this.venta.getVenta_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.venta.getVenta_id());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.venta.getVenta_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.venta = new VentasDTO();
        this.venta.setVenta_id(this.resultSet.getInt("VENTA_ID"));

        // Obtener usuario completo usando DAO
        Integer usuario_id = this.resultSet.getInt("USUARIO_ID");
        UsuariosDTO usuario = this.usuarioDAO.obtenerPorId(usuario_id);
        this.venta.setUsuario(usuario);

        this.venta.setFecha_hora_creacion(
                this.resultSet.getTimestamp("FECHA_HORA_CREACION").toLocalDateTime()
        );
        this.venta.setTotal(this.resultSet.getDouble("TOTAL"));

        // Obtener Promoción completa usando DAO (puede ser null)
        Integer descuento_id = (Integer) this.resultSet.getObject("DESCUENTO_ID");
        if (descuento_id != null) {
            DescuentosDTO descuento = this.descuentoDAO.obtenerPorId(descuento_id);
            this.venta.setDescuento(descuento);
        } else {
            this.venta.setDescuento(null);
        }

        // Cargar automáticamente los detalles del venta
        ArrayList<DetalleVentasDTO> detalles = 
                this.detalleVentaDAO.listarPorVentaId(this.venta.getVenta_id());
        this.venta.setDetalles(detalles);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.venta = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.venta);
    }

    @Override
    public Integer insertar(VentasDTO venta) {
        this.venta = venta;
        return super.insertar();
    }

    @Override
    public VentasDTO obtenerPorId(Integer ventaId) {
        this.venta = new VentasDTO();
        this.venta.setVenta_id(ventaId);
        super.obtenerPorId();
        return this.venta;
    }

    @Override
    public ArrayList<VentasDTO> listarTodos() {
        return (ArrayList<VentasDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(VentasDTO venta) {
        this.venta = venta;
        return super.modificar();
    }

    @Override
    public Integer eliminar(VentasDTO venta) {
        this.venta = venta;
        return super.eliminar();
    }

}
