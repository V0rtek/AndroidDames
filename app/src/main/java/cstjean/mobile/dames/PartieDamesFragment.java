package cstjean.mobile.dames;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class PartieDamesFragment extends Fragment {
    /**
     * Clé utilisée pour stocker l'index courant dans une structure de données.
     */
    private static final String KEY_INDEXCOURANT = "indexcourant";
    /**
     * Variable représentant l'index courant.
     */
    private int indexCourant = 0;

    /**
     * La partie de Dames actuelle.
     */
    // private final PartieDames partie = PartieDames.getInstance();   // Singleton??

    /**
     * Les boutons de la grille de Dames.
     */
    private final List<Button> boutons = new ArrayList<>();

    /**
     * Le texte en haut de l'écran qui indique le joueur à qui c'est le tour.
     */
    private TextView joueurActuel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            indexCourant = savedInstanceState.getInt(KEY_INDEXCOURANT, 0);
        }

        View view = inflater.inflate(R.layout.fragment_partie_dames, container, false);

        GridLayout damier = view.findViewById(R.id.damier);

        // Créer les boutons
        for (int i = 0; i < 3 * 3; i++) {
            Button button = new Button(view.getContext());
            button.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            damier.getLayoutParams().width / 10,
                            damier.getLayoutParams().height / 10)
            );

            damier.addView(button);
            boutons.add(button);

            button.setOnClickListener(view1 -> cliquerCase(button));
        }

        joueurActuel = view.findViewById(R.id.textViewJoueur);
        // view.findViewById(R.id.reset).setOnClickListener(v -> recommencerPartie());

        return view;
    }

    private void cliquerCase(Button button) {
        // id?
    }
}
