import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.navigationdrawerkotlin.R
import com.example.navigationdrawerkotlin.ViewModel.ProductViewModel
import com.example.navigationdrawerkotlin.model.ProductSend
import com.google.android.material.textfield.TextInputEditText

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var crearProductoButton: Button
private lateinit var viewModel: ProductViewModel


class MisproductosFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var nombreEditText: TextInputEditText
    private lateinit var descripcionEditText: TextInputEditText
    private lateinit var tiempoReclamoEditText: TextInputEditText
    private lateinit var precioEditText: TextInputEditText
    private lateinit var userIdEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_misproductos, container, false)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        crearProductoButton = view.findViewById(R.id.crearProductoButton)

        crearProductoButton.setOnClickListener {
            // Abrir el selector de imágenes
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        }
        viewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        nombreEditText = view.findViewById(R.id.nombreEditText)
        descripcionEditText = view.findViewById(R.id.descripcionEditText)

        tiempoReclamoEditText = view.findViewById(R.id.tiempoReclamoEditText)
        precioEditText = view.findViewById(R.id.precioEditText)
        val userIdEditText: TextInputEditText = view.findViewById(R.id.userIdEditText)

        val crearProductoButton: Button = view.findViewById(R.id.crearProductoButton)
        crearProductoButton.setOnClickListener {
            val nombres = nombreEditText.text.toString()
            val descripcion = descripcionEditText.text.toString()
            val precio = precioEditText.text.toString().toInt()
            val tiempo_reclamo = tiempoReclamoEditText.text.toString()
            val user_id = userIdEditText.text.toString().toInt()

            crearProduct(nombres, descripcion, precio, tiempo_reclamo, user_id)
        }


    }

    fun crearProduct(
        nombres: String, descripcion: String,
        precio: Int, tiempo_reclamo: String, user_id: Int
    ) {
        val productos = ProductSend(
            nombres = nombres,
            descripcion = descripcion,
            precio= precio,
            tiempo_reclamo = tiempo_reclamo,
            user_id = user_id
        )

        viewModel.subirProducto(productos) { respuesta ->
            if (respuesta?.id == null) {
                // Manejar el error
                Toast.makeText(requireContext(), "Error al crear producto", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Creación exitosa", Toast.LENGTH_SHORT).show()
            }
        }
    }

}