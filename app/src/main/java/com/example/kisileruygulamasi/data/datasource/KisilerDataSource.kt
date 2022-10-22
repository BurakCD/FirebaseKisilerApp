package com.example.kisileruygulamasi.data.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.kisileruygulamasi.data.entity.Kisiler
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KisilerDataSource(var refKisiler : DatabaseReference) {
    val kisilerListe = MutableLiveData<List<Kisiler>>()

    fun kaydet(kisi_ad:String,kisi_tel:String){
        val yeniKisi = Kisiler("", kisi_ad, kisi_tel)
        refKisiler.push().setValue(yeniKisi)
    }

    fun guncelle(kisi_id:String,kisi_ad:String,kisi_tel:String){
        val bilgiler = HashMap<String, Any>()
        bilgiler["kisi_ad"] = kisi_ad
        bilgiler["kisi_tel"] = kisi_tel
        refKisiler.child(kisi_id).updateChildren(bilgiler)
    }

    fun kisileriYukle() : MutableLiveData<List<Kisiler>> {
        refKisiler.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val liste = ArrayList<Kisiler>()

                for (d in snapshot.children){
                    val kisi = d.getValue(Kisiler::class.java)
                    if (kisi != null){
                        kisi.kisi_id = d.key
                        liste.add(kisi)
                    }
                }
                kisilerListe.value = liste
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        return kisilerListe
    }

    fun ara(aramaKelimesi:String) : MutableLiveData<List<Kisiler>> {
        refKisiler.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val liste = ArrayList<Kisiler>()

                for (d in snapshot.children){
                    val kisi = d.getValue(Kisiler::class.java)
                    if (kisi != null){
                        if (kisi.kisi_ad!!.lowercase().contains(aramaKelimesi.toString())){
                            kisi.kisi_id = d.key
                            liste.add(kisi)
                        }
                    }
                }
                kisilerListe.value = liste
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        return kisilerListe
    }

    fun sil(kisi_id:String) {
        refKisiler.child(kisi_id).removeValue()
    }
}