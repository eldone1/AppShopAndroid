package com.idone.tiendavirtualapp.Vendedor

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.idone.tiendavirtualapp.R
import com.idone.tiendavirtualapp.Vendedor.Buttom_Nav_Fragments_Vendedor.FragmentMisProductosV
import com.idone.tiendavirtualapp.Vendedor.Buttom_Nav_Fragments_Vendedor.FragmentOrdenesV
import com.idone.tiendavirtualapp.Vendedor.Nav_Fragments_Vendedor.FragmentInicioV
import com.idone.tiendavirtualapp.Vendedor.Nav_Fragments_Vendedor.FragmentMiTiendaV
import com.idone.tiendavirtualapp.Vendedor.Nav_Fragments_Vendedor.FragmentReseniaV
import com.idone.tiendavirtualapp.databinding.ActivityMainVendedorBinding

class MainActivityVendedor : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding : ActivityMainVendedorBinding
    private var firebaseAuth : FirebaseAuth?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainVendedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        firebaseAuth = FirebaseAuth.getInstance()
        comprobarSesion()

        binding.navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        
        replaceFragment(FragmentInicioV())
        binding.navigationView.setCheckedItem(R.id.op_inicio_v)
    }

    private fun comprobarSesion() {
        /*Si no ha iniciado sesion go to RV*/
        if(firebaseAuth!!.currentUser==null){
            startActivity(Intent(applicationContext, RegistroVendedorActivity::class.java))
            Toast.makeText(applicationContext, "Vendedor no registrado o logeado", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(applicationContext, "Vendedor en linea", Toast.LENGTH_SHORT).show()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.navFragment, fragment)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.op_inicio_v->{
                replaceFragment(FragmentInicioV())
            }
            R.id.op_mi_tienda_v->{
                replaceFragment(FragmentMiTiendaV())
            }
            R.id.op_resenia_v->{
                replaceFragment(FragmentReseniaV())
            }
            R.id.op_cerrar_sesion_v->{
                Toast.makeText(applicationContext, "Saliste de la aplicacion", Toast.LENGTH_SHORT).show()
            }
            R.id.op_mis_productos_v->{
                replaceFragment(FragmentMisProductosV())
            }
            R.id.op_mis_ordenes_v->{
                replaceFragment(FragmentOrdenesV())
            }

        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}