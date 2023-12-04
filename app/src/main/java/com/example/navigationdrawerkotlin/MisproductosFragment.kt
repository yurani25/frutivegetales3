import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.navigationdrawerkotlin.R
import com.example.navigationdrawerkotlin.data.RetrofitProduc
import com.example.navigationdrawerkotlin.reponse.Producto
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Callback
import java.io.InputStream
import retrofit2.Call
import retrofit2.Response




class MisproductosFragment : Fragment() {
    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    private lateinit var selectedImageUri: Uri

    private lateinit var nombreEditText: EditText
    private lateinit var tiempoReclamoEditText: EditText
    private lateinit var precioEditText: EditText
    private lateinit var descripcionEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_misproductos, container, false)

        nombreEditText = view.findViewById(R.id.nombreEditText)
        tiempoReclamoEditText = view.findViewById(R.id.tiempoReclamoEditText)
        precioEditText = view.findViewById(R.id.precioEditText)
        descripcionEditText = view.findViewById(R.id.descripcionEditText)

        val seleccionarImagenButton: Button = view.findViewById(R.id.seleccionarImagenButton)
        seleccionarImagenButton.setOnClickListener {
            openGalleryForImage()
        }

        val crearProductoButton: Button = view.findViewById(R.id.crearProductoButton)
        crearProductoButton.setOnClickListener {
            createProduct()
        }

        return view
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                selectedImageUri = uri
                Log.d("ImageSelection", "URI de la imagen seleccionada: $selectedImageUri")
            }
        }
    }

    private fun createProduct() {
        if (!::selectedImageUri.isInitialized) {
            Toast.makeText(requireContext(), "Selecciona una imagen primero", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val productApi = RetrofitProduc.instance

        val requestBodyMap = HashMap<String, RequestBody>()
        requestBodyMap["nombres"] = createPartFromString(nombreEditText.text.toString())
        requestBodyMap["tiempo_reclamo"] =
            createPartFromString(tiempoReclamoEditText.text.toString())
        requestBodyMap["precio"] = createPartFromString(precioEditText.text.toString())
        requestBodyMap["descripcion"] = createPartFromString(descripcionEditText.text.toString())
        requestBodyMap["user_id"] = createPartFromString("1") // Cambia esto según tus requisitos

        // Obtener el archivo de la imagen seleccionada
        val imageInputStream: InputStream? =
            requireContext().contentResolver.openInputStream(selectedImageUri)
        val imageByteArray: ByteArray? = imageInputStream?.readBytes()

        // Crear el cuerpo de la solicitud multipart para la imagen
        val imageRequestBody = imageByteArray?.toRequestBody("image/*".toMediaTypeOrNull())
        val imagePart =
            MultipartBody.Part.createFormData("imagen", "product_image.jpg", imageRequestBody!!)

        // Agregar los campos del producto como partes multipart
        val parts = ArrayList<MultipartBody.Part>()
        for ((key, value) in requestBodyMap) {
            parts.add(MultipartBody.Part.createFormData(key, value.toString()))
        }

        // Agregar la parte de la imagen
        parts.add(imagePart)

        // Realizar la llamada al servidor para crear el producto
        val call = productApi.createProduct(
            parts[0], // nombres
            parts[1], // tiempo_reclamo
            parts[2], // precio
            parts[3], // descripcion
            parts[4], // user_id
            parts[5]  // imagen
        )

        call.enqueue(object : Callback<Producto> {
            override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                if (response.isSuccessful) {
                    // Manejar la respuesta exitosa aquí
                    val createdProduct = response.body()
                    Log.d("ProductCreation", "Producto creado con éxito: $createdProduct")
                } else {
                    // Manejar errores de respuesta no exitosa
                    Log.e("ProductCreation", "Error en la respuesta del servidor. Código: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Producto>, t: Throwable) {
                // Manejar errores de la llamada al servidor
                Log.e("ProductCreation", "Error al realizar la llamada: ${t.message}")
            }
        })
    }

    private fun createPartFromString(value: String): RequestBody {
        return value.toRequestBody(MultipartBody.FORM)
    }
}

