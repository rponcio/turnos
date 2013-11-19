package cl.rpgscorp.rpgturnos;

public class ObjetosClase{
	int id;
	String codigo;
	String nombre;
	//Constructor
	public ObjetosClase(int id, String codigo, String nombre) {
		super();
		this.id = id;
		this.codigo= codigo;
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	public int getId() {
		return id;
	}
	public String getCodigo() {
		return codigo;
	}
	
}