package com.example.marcoaliasketaz.photoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.DatabaseErrorHandler;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends ActionBarActivity {

    DBHandler db;
    Photo photoAffichee;
    ImageView imageView;
    EditText libelleTextView;
    EditText commentairesTextView;
    TextView dateLieu ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        db= new DBHandler(this);

        //on recupere l'id dans l'intent
        int id = getIntent().getIntExtra("id",0);
        //puis on recupere la photo
        photoAffichee=db.getPhoto(id);

        libelleTextView = (EditText) findViewById(R.id.libelle);
        libelleTextView.setText(photoAffichee.get_libelle());

        imageView = (ImageView) findViewById(R.id.image);
        if (photoAffichee.get_photouri()!= null)
            imageView.setImageURI(Uri.parse(photoAffichee.get_photouri()));

        commentairesTextView=(EditText) findViewById(R.id.commentaire);
        commentairesTextView.setText(photoAffichee.get_commentaires());

        dateLieu=(TextView) findViewById(R.id.date);
        //todo : changer "a lyon" par la bonne ville = voir localisation
        dateLieu.setText("Photo prise le "+photoAffichee.get_date()+" a Lyon");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Voulez-vous vraiment supprimer cette photo ?")
                    .setCancelable(false)
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.deletePhoto(photoAffichee);
                            finish();
                        }
                    }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
