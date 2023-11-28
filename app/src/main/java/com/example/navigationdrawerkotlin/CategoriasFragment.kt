import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.navigationdrawerkotlin.FrutasFragment
import com.example.navigationdrawerkotlin.OrganicosFragment
import com.example.navigationdrawerkotlin.R
import com.example.navigationdrawerkotlin.VerdurasFragment
import com.example.navigationdrawerkotlin.inorganicosFragment

class CategoriasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categorias, container, false)


        val organicosLayout = view.findViewById<LinearLayout>(R.id.organicosLayout)

        organicosLayout.setOnClickListener {
            val organicosFragment = OrganicosFragment()
            val fragmentManager: FragmentManager? = fragmentManager
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, organicosFragment)
                ?.addToBackStack(null)
                ?.commit()
        }




        val inorganicosLayout = view.findViewById<LinearLayout>(R.id.inorganicosLayout)


        inorganicosLayout.setOnClickListener {

            val inorganicosFragment = inorganicosFragment()
            val fragmentManager: FragmentManager? = fragmentManager
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, inorganicosFragment)
                ?.addToBackStack(null)
                ?.commit()
        }

        // Obtén la referencia al LinearLayout de verduras
        val verdurasLayout = view.findViewById<LinearLayout>(R.id.verdurasLayout)

        // Configura el OnClickListener para abrir el fragmento de verduras
        verdurasLayout.setOnClickListener {
            val verdurasFragment = VerdurasFragment() // Reemplaza con el nombre correcto de tu fragmento de verduras
            val fragmentManager: FragmentManager? = fragmentManager
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, verdurasFragment)
                ?.addToBackStack(null)
                ?.commit()
        }


        // Obtén la referencia al LinearLayout de frutas
        val frutasLayout = view.findViewById<LinearLayout>(R.id.frutasLayout)

        // Configura el OnClickListener para abrir el fragmento de frutas
        frutasLayout.setOnClickListener {
            val frutasFragment = FrutasFragment() // Reemplaza con el nombre correcto de tu fragmento de frutas
            val fragmentManager: FragmentManager? = fragmentManager
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, frutasFragment)
                ?.addToBackStack(null)
                ?.commit()
        }


        return view
    }
}
