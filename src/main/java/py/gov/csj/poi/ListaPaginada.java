package py.gov.csj.poi;

import java.util.List;

public class ListaPaginada<T> {

	public static final int TODOS = -1;

	private List<T> lista;
	private int inicio;
	private int cantidad;
	private int total;

	public ListaPaginada(List<T> lista, int total, int inicio, int cantidad) {
		this.lista = lista;
		this.inicio = inicio;
		this.cantidad = cantidad;
		this.total = total;
	}
	
	public ListaPaginada(List<T> lista, int inicio, int cantidad) {
		this.lista = lista;
		this.inicio = inicio;
		this.cantidad = cantidad;
		this.total = lista.size();
	}

	public List<T> getLista() {
		return lista;
	}

	public void setLista(List<T> lista) {
		this.lista = lista;
	}

	public int getInicio() {
		return inicio;
	}

	public void setInicio(int inicio) {
		this.inicio = inicio;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
