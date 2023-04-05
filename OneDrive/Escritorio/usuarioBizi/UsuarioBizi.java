package ayuntamiento;

public class UsuarioBizi {
    private int idUsuario;
    private int traslados;
    private int circular;
    private int total;

    public UsuarioBizi(int idUsuario,int traslados, int circular,int total) {
        this.idUsuario = idUsuario;
        this.traslados = traslados;
        this.circular = circular;
        this.total = total;
    }

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getTraslados() {
		return traslados;
	}

	public void setTraslados(int traslados) {
		this.traslados = traslados;
	}

	public int getCircular() {
		return circular;
	}

	public void setCircular(int circular) {
		this.circular = circular;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "idUsuario=" + idUsuario + ", traslados=" + traslados + ", circular=" + circular
				+ ", total=" + total + "]";
	}
}