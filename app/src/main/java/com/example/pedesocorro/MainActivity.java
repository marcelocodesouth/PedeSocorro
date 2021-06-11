package com.example.pedesocorro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView txtCidade;
    private TextView txtEstado;
    private TextView txtPais;
    private Address Endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Button btnCadastrar_Prog = findViewById(R.id.btnCadastrar);
        Button btnPedirAjuda_Prog = findViewById(R.id.btnHelp);
        txtCidade = (TextView) findViewById(R.id.txtCidade);
        txtEstado = (TextView) findViewById(R.id.txtEstado);
        txtPais = (TextView) findViewById(R.id.txtPais);



        btnCadastrar_Prog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Cadastro.class);
                startActivity(intent);
            }
        });

        btnPedirAjuda_Prog.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                solicitarPermissao();
            }

        });
    }

    private void solicitarPermissao() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else ativarServicoGPS();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ativarServicoGPS();
                } else {
                    Toast.makeText(this, "Sem conexão com o GPS!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


    public void ativarServicoGPS(){
        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location)
                {
                    try {
                        escreverLocalizacao(location);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                public void onStatusChanged(String provider, int status, Bundle extras) { }
                public void onProviderEnabled(String provider) { }
                public void onProviderDisabled(String provider) { }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, locationListener);
        }catch(SecurityException ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void escreverLocalizacao(Location location) throws IOException {
        Double longitude = location.getLongitude();
        Double latitude = location.getLatitude();

        //inicio da API que vai recuperar os dados gravados
        SharedPreferences pref = getApplicationContext().getSharedPreferences("PEDE_SOCORRO_DATA", MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();

        //recupera os dados da API
        String nomeDB=pref.getString("name", null);
        String nomeContatoConfiancaDB=pref.getString("nomeContatoConfianca", null);
        String numeroTelefoneContatoConfiancaDB=pref.getString("numeroTelefoneContatoConfianca", null);

        Endereco = buscarEndereco(latitude, longitude);
        txtCidade.setText("Cidade: "+Endereco.getThoroughfare()+", "+Endereco.getFeatureName()+", "+Endereco.getSubLocality()+", "+Endereco.getSubAdminArea());
        txtEstado.setText("Estado: "+ Endereco.getAdminArea());
        txtPais.setText("Pais: "+ Endereco.getCountryName()
                //teste do retorno dos dados gravados
                + "\nNome: " + nomeDB +
                "\nContato de Confiança: " + nomeContatoConfiancaDB +
                System.getProperty("line.separator") + "Telefone do Contato: " + numeroTelefoneContatoConfiancaDB);


    }

    public Address buscarEndereco(double latitude, double longitude) throws IOException {
        Geocoder geocoder;
        Address address = null;
        List<Address> addresses;

        geocoder = new Geocoder(getApplicationContext());
        addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if(addresses.size()>0){
                address = addresses.get(0);
            }
        return address;
    }
}
