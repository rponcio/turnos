package cl.rpgscorp.rpgturnos;

public class ObjetosClase{
	int id;
	String codigo;
	String nombre;
	String clave;
	//Constructor
	public ObjetosClase(int id, String codigo, String nombre, String clave) {
		super();
		this.id = id;
		this.codigo= codigo;
		this.nombre = nombre;
		this.clave = clave;
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
	public String getClave() {
		return clave;
	}
	
}