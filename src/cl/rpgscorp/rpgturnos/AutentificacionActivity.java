package cl.rpgscorp.rpgturnos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.LinkedList;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class AutentificacionActivity extends Activity {

	private Button botonAceptar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.autentificacion);

		//cargarSpinnerUsuariosManual();

		// WebServer Request URL
		String serverURL = "http://rpg.comxa.com/rpg/SoporteRPG.php?t=u";

		// Utilice AsyncTask ejecutar método para prevenir ANR Problema
		new cargarSpinnerUsuarios().execute(serverURL);

		botonAceptar = (Button) findViewById(R.id.btAceptar);
		botonAceptar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				ValidarUsuario(null);
			}
		});
		
		
	}

	public void ValidarUsuario(View view) {
		EditText clave = (EditText) findViewById(R.id.eTclaveUsuario);
		String stringClave = clave.getText().toString();
		
		Spinner spusuario = (Spinner) findViewById(R.id.spUsuarios);
		
		//Toast.makeText(this, stringClave, Toast.LENGTH_SHORT).show();
		
	}
	
	
	// Clase con clase extends AsyncTask
	private class cargarSpinnerUsuarios extends AsyncTask<String, Void, Void> {

		// inicialización requerida
		private final HttpClient Client = new DefaultHttpClient();
		private String Content;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(AutentificacionActivity.this);
		String data = "";

		TextView tvEstadoComunicacion = (TextView) findViewById(R.id.tvEstadoComunicacion);
		// ///TextView uiUpdate = (TextView) findViewById(R.id.output);
		// ///TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
		int sizeData = 0;

		// EditText serverText = (EditText) findViewById(R.id.serverText);

		protected void onPreExecute() {
			// NOTA: Puede llamar Element UI aquí.

			// Inicio de diálogo de progreso (Mensaje)

			Dialog.setMessage("Un momento por favor.");
			Dialog.show();

			try {
				// Ajuste el parámetro Solicitud
				// ///data +="&" + URLEncoder.encode("data", "UTF-8") +
				// "="+serverText.getText();
				data += "&" + URLEncoder.encode("data", "UTF-8");

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		}

		// Llame después método OnPreExecute
		protected Void doInBackground(String... urls) {

			/************ Hacer Mensaje Call To servidor Web ***********/
			BufferedReader reader = null;

			// Enviar datos
			try {

				// Definido URL dónde enviar los datos
				URL url = new URL(urls[0]);

				// Enviar solicitud de datos POST

				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
				OutputStreamWriter wr = new OutputStreamWriter(
						conn.getOutputStream());
				wr.write(data);
				wr.flush();

				// Obtener la respuesta del servidor

				reader = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = null;

				// Leer respuesta del servidor
				while ((line = reader.readLine()) != null) {
					// Respuesta del servidor en la cadena Append
					sb.append(line + "\n");
				}

				// Append respuesta del servidor a una cadena de contenido
				Content = sb.toString();
			} catch (Exception ex) {
				Error = ex.getMessage();
			} finally {
				try {

					reader.close();
				}

				catch (Exception ex) {
				}
			}

			/*****************************************************/
			return null;
		}

		protected void onPostExecute(Void unused) {
			// NOTA: Puede llamar Element UI aquí.

			// Cerrar diálogo de progreso
			Dialog.dismiss();

			LinkedList<ObjetosClase> objUsuarios = new LinkedList<ObjetosClase>();
			objUsuarios.add(new ObjetosClase(0, "", "Seleccione Usuario",""));			
			if (Error != null) {

				tvEstadoComunicacion.setText("Output : " + Error);

			} else {

				// Mostrar respuesta JSON en pantalla (actividad)
				//tvEstadoComunicacion.setText(Content);

				/****************** Iniciar analizar la respuesta JSON datos *************/

				String OutputData = "";
				JSONObject jsonResponse;

				try {

					/******
					 * Crea un nuevo JSONObject con nombre / valor de las
					 * asignaciones de la cadena JSON.
					 ********/
					jsonResponse = new JSONObject(Content);

					/*****
					 * Devuelve el valor asignado por el nombre si existe y es
					 * una JSONArray.
					 ***/
					/******* Devuelve NULL de otro modo. *******/
					JSONArray jsonMainNode = jsonResponse
							.optJSONArray("USUARIOS");

					/*********** Procesar cada nodo JSON ************/

					int lengthJsonArr = jsonMainNode.length();

					for (int i = 0; i < lengthJsonArr; i++) {
						/****** Obtenga objeto para cada nodo JSON. ***********/
						JSONObject jsonChildNode = jsonMainNode
								.getJSONObject(i);

						/******* Recuperar valores de nodo **********/
						String login = jsonChildNode.optString("login").toString();
						String nombre = jsonChildNode.optString("nombre").toString();
						String clave = jsonChildNode.optString("clave").toString();
						
						objUsuarios.add(new ObjetosClase(i, login, nombre, clave));
				
						// Creamos la lista
						//LinkedList<ObjetosClase> objUsuarios = new LinkedList<ObjetosClase>();

						// La poblamos con los ejemplos
						//objUsuarios.add(new ObjetosClase(0, "", "Seleccione Usuario"));
						//objUsuarios.add(new ObjetosClase(1, "CM", "Carlos Muñoz"));
						//objUsuarios.add(new ObjetosClase(2, "CV", "Cristian Valverde"));
						//objUsuarios.add(new ObjetosClase(3, "DI", "Damian Ibaceta"));
						//objUsuarios.add(new ObjetosClase(4, "RP", "Ramón Ponce"));
						//objUsuarios.add(new ObjetosClase(5, "ML", "Michael Lobos"));

						/*
						OutputData += " login 		    : "
								+ login
								+ " \n "
								+ "nombre 		: "
								+ nombre
								+ " \n "
								+ "clave 				: "
								+ clave
								+ " \n "
								+ "--------------------------------------------------\n";
						 */
						// Log.i("JSON parse", song_name);
					}
					/****************** Fin analizar la respuesta JSON Data *************/

					// Creamos el adaptador
					ArrayAdapter<ObjetosClase> spinner_adapter = new ArrayAdapter<ObjetosClase>(AutentificacionActivity.this, android.R.layout.simple_spinner_item, objUsuarios);
					// Añadimos el layout para el menú y se lo damos al spinner
					spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					Spinner spusuario = (Spinner) findViewById(R.id.spUsuarios);
					spusuario.setAdapter(spinner_adapter);
					
					// Mostrar salida analizada en la pantalla (actividad)
					//tvEstadoComunicacion.setText(OutputData);

				} catch (JSONException e) {

					e.printStackTrace();
				}

			}
		}

	}

	public void cargarSpinnerUsuariosManual() {

		Spinner spusuario = (Spinner) findViewById(R.id.spUsuarios);
		// Creamos la lista
		LinkedList<ObjetosClase> objUsuarios = new LinkedList<ObjetosClase>();

		// La poblamos con los ejemplos
		objUsuarios.add(new ObjetosClase(0, "", "Seleccione Usuario",""));
		objUsuarios.add(new ObjetosClase(1, "CM", "Carlos Muñoz",""));
		objUsuarios.add(new ObjetosClase(2, "CV", "Cristian Valverde",""));
		objUsuarios.add(new ObjetosClase(3, "DI", "Damian Ibaceta",""));
		objUsuarios.add(new ObjetosClase(4, "RP", "Ramón Ponce",""));
		objUsuarios.add(new ObjetosClase(5, "ML", "Michael Lobos",""));

		// Creamos el adaptador
		ArrayAdapter<ObjetosClase> spinner_adapter = new ArrayAdapter<ObjetosClase>(this, android.R.layout.simple_spinner_item, objUsuarios);
		// Añadimos el layout para el menú y se lo damos al spinner 
		spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spusuario.setAdapter(spinner_adapter);

	}
}


