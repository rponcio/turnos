package cl.rpgscorp.rpgturnos;

import java.util.LinkedList;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AutentificacionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.autentificacion);

		Spinner spusuario = (Spinner) findViewById(R.id.spUsuarios);
		// Creamos la lista
		LinkedList<ObjetosClase> objUsuarios = new LinkedList<ObjetosClase>();

		// La poblamos con los ejemplos
		objUsuarios.add(new ObjetosClase(0, "", "Seleccione Usuario"));
		objUsuarios.add(new ObjetosClase(1, "CM", "Carlos Mu�oz"));
		objUsuarios.add(new ObjetosClase(2, "CV", "Cristian Valverde"));
		objUsuarios.add(new ObjetosClase(3, "DI", "Damian Ibaceta"));
		objUsuarios.add(new ObjetosClase(4, "RP", "Ram�n Ponce"));
		objUsuarios.add(new ObjetosClase(5, "ML", "Michael Lobos"));
		
		// Creamos el adaptador
		ArrayAdapter<ObjetosClase> spinner_adapter = new ArrayAdapter<ObjetosClase>(
				this, android.R.layout.simple_spinner_item, objUsuarios);
		// A�adimos el layout para el men� y se lo damos al spinner
		spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spusuario.setAdapter(spinner_adapter);

	}

}
