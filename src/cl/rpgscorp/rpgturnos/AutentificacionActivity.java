package cl.rpgscorp.rpgturnos;

import java.util.LinkedList;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class AutentificacionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.autentificacion);
	}
	
	//Creamos la lista
	LinkedList<ObjetosClase> comidas = new LinkedList<ObjetosClase>();
	
	//La poblamos con los ejemplos
	comidas.add(new ObjetosClase(1, "Salchichas"));
	comidas.add(new ObjetosClase(2, "Huevos"));
	comidas.add(new ObjetosClase(3, "Tomate"));

	//Creamos el adaptador
	ArrayAdapter<ObjetosClase> spinner_adapter = new ArrayAdapter<ObjetosClase>(this, android.R.layout.simple_spinner_item, comidas);
	//Añadimos el layout para el menú y se lo damos al spinner
	spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	spUsuarios.setAdapter(spinner_adapter);

	
	
	
}


