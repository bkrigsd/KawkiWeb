package pe.edu.pucp.kawkiweb.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.kawkiweb.dao.RedesSocialesDAO;
import pe.edu.pucp.kawkiweb.daoImp.util.Columna;
import pe.edu.pucp.kawkiweb.model.utilVenta.RedesSocialesDTO;

public class RedesSocialesDAOImpl extends BaseDAOImpl implements RedesSocialesDAO {

    private RedesSocialesDTO redSocial;

    public RedesSocialesDAOImpl() {
        super("REDES_SOCIALES");
        this.redSocial = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("RED_SOCIAL_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.redSocial.getNombre());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.redSocial.getNombre());
        this.statement.setInt(2, this.redSocial.getRedSocialId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.redSocial.getRedSocialId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.redSocial.getRedSocialId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.redSocial = new RedesSocialesDTO();
        this.redSocial.setRedSocialId(this.resultSet.getInt("RED_SOCIAL_ID"));
        this.redSocial.setNombre(this.resultSet.getString("NOMBRE"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.redSocial = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.redSocial);
    }

    @Override
    public Integer insertar(RedesSocialesDTO redSocial) {
        this.redSocial = redSocial;
        return super.insertar();
    }

    @Override
    public RedesSocialesDTO obtenerPorId(Integer redSocialId) {
        this.redSocial = new RedesSocialesDTO();
        this.redSocial.setRedSocialId(redSocialId);
        super.obtenerPorId();
        return this.redSocial;
    }

    @Override
    public ArrayList<RedesSocialesDTO> listarTodos() {
        return (ArrayList<RedesSocialesDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(RedesSocialesDTO redSocial) {
        this.redSocial = redSocial;
        return super.modificar();
    }

    @Override
    public Integer eliminar(RedesSocialesDTO redSocial) {
        this.redSocial = redSocial;
        return super.eliminar();
    }
}
