import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.navigationdrawerkotlin.OrganicosFragment
import com.example.navigationdrawerkotlin.R
import com.example.navigationdrawerkotlin.inorganicosFragment

class CategoriasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categorias, container, false)

        // Encuentra el LinearLayout de "Orgánicos" por su ID
        val organicosLayout = view.findViewById<LinearLayout>(R.id.organicosLayout)

        // Agrega un OnClickListener al LinearLayout de "Orgánicos"
        organicosLayout.setOnClickListener {
            // Abre la nueva vista (DetalleOrganicosFragment) cuando se hace clic en "Orgánicos"
            val organicosFragment = OrganicosFragment()
            val fragmentManager: FragmentManager? = fragmentManager
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, organicosFragment)
                ?.addToBackStack(null)
                ?.commit()
        }



// Encuentra el LinearLayout de "Orgánicos" por su ID
        val inorganicosLayout = view.findViewById<LinearLayout>(R.id.inorganicosLayout)

        // Agrega un OnClickListener al LinearLayout de "Orgánicos"
        inorganicosLayout.setOnClickListener {

            val inorganicosFragment = inorganicosFragment()
            val fragmentManager: FragmentManager? = fragmentManager
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, inorganicosFragment)
                ?.addToBackStack(null)
                ?.commit()
        }


        return view
    }
}
