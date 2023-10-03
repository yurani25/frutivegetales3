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


        return view
    }
}
