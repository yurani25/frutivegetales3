import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.navigationdrawerkotlin.ListaproductosFragment
import com.example.navigationdrawerkotlin.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream

class MisproductosFragment : Fragment() {
    private lateinit var myRef: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageBitmap: Bitmap? = null // Declarado a nivel de clase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_misproductos, container, false)

        val database = FirebaseDatabase.getInstance()
        myRef = database.getReference("productos")

        storage = FirebaseStorage.getInstance()

        val editTextNombre = view.findViewById<EditText>(R.id.editTextNombre)
        val editTextPrecio = view.findViewById<EditText>(R.id.editTextPrecio)
        val editTextDescripcion = view.findViewById<EditText>(R.id.editTextDescripcion)
        val btnAgregarProducto = view.findViewById<Button>(R.id.btnAgregarProducto)

        btnAgregarProducto.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val precio = editTextPrecio.text.toString().toDoubleOrNull() ?: 0.0
            val descripcion = editTextDescripcion.text.toString()
            if (nombre.isNotBlank() && precio > 0) {
                val nuevoProducto: MutableMap<String, Any> = hashMapOf(
                    "nombre" to nombre,
                    "precio" to precio,
                    "descripcion" to descripcion,
                    "imageUrl" to "" // Esto se establecerá después de subir la imagen
                )

                val imagenProducto = obtenerImagenDeAlgunaFuente()

                agregarProductoConImagen(imagenProducto, nuevoProducto)

                // Obtén el FragmentManager desde la actividad que contiene el fragmento actual
                val fragmentManager = requireActivity().supportFragmentManager

                // Reemplaza el fragmento actual con ListadoProductosFragment
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, ListaproductosFragment())
                fragmentTransaction.addToBackStack(null)  // Agrega la transacción a la pila para que se pueda volver atrás
                fragmentTransaction.commit()


            } else {
                // Manejar caso de valores no válidos
            }
        }

        val imageViewProducto = view.findViewById<ImageView>(R.id.imageViewProducto)
        val btnSeleccionarImagen = view.findViewById<Button>(R.id.btnSeleccionarImagen)

        btnSeleccionarImagen.setOnClickListener {
            abrirGaleria()
        }

        return view
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data
            selectedImageBitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, imageUri)
            val imageViewProducto = view?.findViewById<ImageView>(R.id.imageViewProducto)
            imageViewProducto?.setImageBitmap(selectedImageBitmap)
        }
    }

    private fun agregarProductoConImagen(bitmap: Bitmap?, nuevoProducto: MutableMap<String, Any>) {
        val nuevoProductoKey = myRef.push().key

        if (bitmap != null && nuevoProductoKey != null) {
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            val imagenRef: StorageReference =
                storage.reference.child("imagenes/$nuevoProductoKey.jpg")

            val uploadTask: UploadTask = imagenRef.putBytes(data)
            uploadTask.addOnSuccessListener { taskSnapshot ->
                imagenRef.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    nuevoProducto["imageUrl"] = imageUrl
                    myRef.child(nuevoProductoKey).setValue(nuevoProducto)
                }
            }.addOnFailureListener {
                // Manejar errores de carga de imagen
            }
        }
    }

    private fun obtenerImagenDeAlgunaFuente(): Bitmap? {
        return selectedImageBitmap
    }
}