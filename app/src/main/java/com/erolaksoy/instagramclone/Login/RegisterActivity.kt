package com.erolaksoy.instagramclone.Login

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.erolaksoy.instagramclone.R
import com.erolaksoy.instagramclone.utils.EventBusDataEvents
import kotlinx.android.synthetic.main.activity_register.*
import org.greenrobot.eventbus.EventBus

class RegisterActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {
    lateinit var manager:FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        manager=supportFragmentManager
        manager.addOnBackStackChangedListener(this)
        init()
    }

    private fun init() {
        tvEposta.setOnClickListener {
            viewTelefon.visibility= View.INVISIBLE
            viewEposta.visibility=View.VISIBLE
            etGirisYontemi.setText("")
            etGirisYontemi.inputType=InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            etGirisYontemi.setHint("E posta")
        }

        tvTelefon.setOnClickListener {
            viewTelefon.visibility= View.VISIBLE
            viewEposta.visibility=View.INVISIBLE
            etGirisYontemi.setText("")
            etGirisYontemi.inputType=InputType.TYPE_CLASS_NUMBER
            etGirisYontemi.setHint("Telefon")
        }

        etGirisYontemi.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               if(start+before+count>=10){
                   btnIleri.isEnabled=true
                   btnIleri.setTextColor(ContextCompat.getColor(this@RegisterActivity,R.color.beyaz))
                   btnIleri.setBackgroundColor(ContextCompat.getColor(this@RegisterActivity,R.color.mavi))

               }else{
                   btnIleri.isEnabled=false
                   btnIleri.setTextColor(ContextCompat.getColor(this@RegisterActivity,R.color.beyaz))
                   btnIleri.setBackgroundColor(ContextCompat.getColor(this@RegisterActivity,R.color.siyah))
               }

                btnIleri.setOnClickListener{



                    if(etGirisYontemi.hint.toString().equals("Telefon")){

                        if(isValidTelefon(etGirisYontemi.text.toString())){
                            loginRoot.visibility=View.GONE
                            loginContainer.visibility=View.VISIBLE

                            var transaction=supportFragmentManager.beginTransaction()
                            transaction.replace(R.id.loginContainer,TelefonKoduGirFragment())
                            transaction.addToBackStack("Telefon kodu gir fragment eklendi")
                            transaction.commit()
                            EventBus.getDefault().postSticky(EventBusDataEvents.KayitBilgileriniGonder(etGirisYontemi.text.toString(),null,null,null,false))

                        }else{
                            Toast.makeText(this@RegisterActivity,"Lütfen geçerli bir telefon numarası giriniz.",Toast.LENGTH_SHORT).show()
                        }




                    }else{
                        if(isValidEmail(etGirisYontemi.text.toString())){


                        loginRoot.visibility=View.GONE
                        loginContainer.visibility=View.VISIBLE
                        var transaction=supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.loginContainer,KayitFragment())
                        transaction.addToBackStack("Email giris  eklendi")
                        transaction.commit()
                        EventBus.getDefault().postSticky(EventBusDataEvents.KayitBilgileriniGonder(null,etGirisYontemi.text.toString(),null,null,true))
                        }else{
                            Toast.makeText(this@RegisterActivity,"Lütfen geçerli bir E-Mail adresi giriniz.",Toast.LENGTH_SHORT).show()
                        }
                    }

                }

            }
        })

    }


    override fun onBackStackChanged() {
        val elemanSayisi = manager.backStackEntryCount
        if(elemanSayisi==0){
            loginRoot.visibility=View.VISIBLE

        }
    }


    fun isValidEmail(kontrolEdilecekMail:String):Boolean{
        if(kontrolEdilecekMail==null){
            return false
        }
        return android.util.Patterns.EMAIL_ADDRESS.matcher(kontrolEdilecekMail).matches()
    }

    fun isValidTelefon(kontrolEdilecekTelefon:String):Boolean{
        if(kontrolEdilecekTelefon==null || kontrolEdilecekTelefon.length>10){
            return false
        }
        return android.util.Patterns.PHONE.matcher(kontrolEdilecekTelefon).matches()
    }





}
