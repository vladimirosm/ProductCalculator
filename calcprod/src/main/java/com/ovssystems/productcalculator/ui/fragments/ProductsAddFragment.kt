package com.ovssystems.productcalculator.ui.fragments

import android.Manifest
import android.Manifest.permission.RECORD_AUDIO
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ovssystems.productcalculator.APP_ACTIVITY
import com.ovssystems.productcalculator.R
import com.ovssystems.productcalculator.adapters.ProductsAddAdapter
import com.ovssystems.productcalculator.model.ProductModel
import com.ovssystems.productcalculator.productDataGlobal
import kotlinx.android.synthetic.main.basket_add_items.*
import java.util.*


class ProductsAddFragment : Fragment(R.layout.basket_add_items) {

    lateinit var productAddListAdapter: ProductsAddAdapter
    private val speechRecognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(APP_ACTIVITY);
    private val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    private val RecordAudioRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        productAddListAdapter = ProductsAddAdapter()
        basket_item_add_list.adapter = productAddListAdapter
        product_add_done_button.setOnClickListener { onDoneProduct() }


        btn_speech_recognizer.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN ->  { speechRecognizer.startListening(speechRecognizerIntent);  btn_speech_recognizer. setImageResource(R.drawable.ic_mic_black_24dp); }
                    MotionEvent.ACTION_UP -> {  speechRecognizer.stopListening();  btn_speech_recognizer.setImageResource(R.drawable.ic_mic_black_off) }
                }
                return true;
            }
        })

        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (ContextCompat.checkSelfPermission(APP_ACTIVITY, RECORD_AUDIO ) == PackageManager.PERMISSION_DENIED  ) {
            checkPermission();
        }

        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {

            }

            override fun onBeginningOfSpeech() {

            }

            override fun onRmsChanged(rmsdB: Float) {

            }

            override fun onBufferReceived(buffer: ByteArray?) {

            }

            override fun onEndOfSpeech() {

            }

            override fun onError(error: Int) {
                Toast.makeText(APP_ACTIVITY, "Error $error", Toast.LENGTH_SHORT).show();
            }

            override fun onResults(results: Bundle?) {
                btn_speech_recognizer.setImageResource(R.drawable.ic_mic_black_off)
                val data: ArrayList<String> = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) as ArrayList<String>
 //               Toast.makeText(APP_ACTIVITY, data.toString(), Toast.LENGTH_SHORT).show();

                productAddListAdapter.addText(data)
            }

            override fun onPartialResults(partialResults: Bundle?) {

            }

            override fun onEvent(eventType: Int, params: Bundle?) {

            }
        })
    }

    private fun onDoneProduct() {
        val products: MutableList<ProductModel> = ArrayList<ProductModel>()
        productAddListAdapter.selectedProductList.forEach {
            if (it.name.isNotEmpty())
                products.add(it)
        }
        productDataGlobal.db.addProductsToBasket(products)
        activity?.onBackPressed();
//        APP_ACTIVITY.navigateUpTo(   Intent(APP_ACTIVITY, MainActivity::class.java))
//        activity?.supportFragmentManager?.popBackStack()
    }

    override fun onStop() {
 //       speechRecognizer.destroy();
        APP_ACTIVITY.hideKeyboard()
        super.onStop()
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(APP_ACTIVITY, arrayOf(Manifest.permission.RECORD_AUDIO), RecordAudioRequestCode)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RecordAudioRequestCode && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) Toast.makeText(APP_ACTIVITY, "Permission Granted", Toast.LENGTH_SHORT).show()
        }
    }
}