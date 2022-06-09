@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.android.movku.presentation.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android.movku.data.auth.model.User
import com.android.movku.databinding.ActivityProfileBinding
import com.android.movku.presentation.auth.login.LoginActivity
import com.android.movku.utils.RealPathUtil
import com.android.movku.utils.Resource
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.File


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private val binding by lazy { ActivityProfileBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<ProfileViewModel>()
    private var userId = -1
    private var email = ""
    private var password = ""
    private var imageUri: Uri? = null
    private var realPath = ""
    private val REQUEST_CODE_PERMISSION = 100

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                handleCameraImage(result.data)
                val uribitmap = result.data?.data
                val FILENAME = "image.png"
                val PATH = "/mnt/sdcard/$FILENAME"
                val f = File(PATH)
                val yourUri = Uri.fromFile(f)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    try {
                        grantUriPermission(
                            packageName,
                            uribitmap,
                            FLAG_GRANT_READ_URI_PERMISSION or FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                        )
                    } catch (e: IllegalArgumentException) {
                        // on Kitkat api only 0x3 is allowed (FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION)
                        grantUriPermission(
                            packageName,
                            uribitmap,
                            FLAG_GRANT_READ_URI_PERMISSION
                        )
                    } catch (e: SecurityException) {
                        Log.e("", e.toString())
                    }
                    try {
                        var takeFlags = intent.flags
                        takeFlags =
                            takeFlags and (FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                        uribitmap?.let { contentResolver.takePersistableUriPermission(it, takeFlags) }
                    } catch (e: SecurityException) {
                        Log.e("", e.toString())
                    }
                }
                imageUri = uribitmap
                if (Build.VERSION.SDK_INT < 11)
                    realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, uribitmap).toString();

                // SDK >= 11 && SDK < 19
                else if (Build.VERSION.SDK_INT < 19)
                    realPath = RealPathUtil.getRealPathFromURI_API11to18(this, uribitmap).toString();

                // SDK > 19 (Android 4.4)
                else
                    realPath = RealPathUtil.getRealPathFromURI_API19(this, uribitmap).toString();
            }
        }

    @SuppressLint("WrongConstant")
    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    grantUriPermission(
                        packageName,
                        result,
                        FLAG_GRANT_READ_URI_PERMISSION or FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                    )
                } catch (e: IllegalArgumentException) {
                    // on Kitkat api only 0x3 is allowed (FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION)
                    grantUriPermission(
                        packageName,
                        result,
                        FLAG_GRANT_READ_URI_PERMISSION
                    )
                } catch (e: SecurityException) {
                    Log.e("", e.toString())
                }
                try {
                    var takeFlags = intent.flags
                    takeFlags =
                        takeFlags and (FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                    result?.let { contentResolver.takePersistableUriPermission(it, takeFlags) }
                } catch (e: SecurityException) {
                    Log.e("", e.toString())
                }
            }
            imageUri = result
            if (Build.VERSION.SDK_INT < 11)
                realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, result).toString();

            // SDK >= 11 && SDK < 19
            else if (Build.VERSION.SDK_INT < 19)
                realPath = RealPathUtil.getRealPathFromURI_API11to18(this, result).toString();

            // SDK > 19 (Android 4.4)
            else
                realPath = RealPathUtil.getRealPathFromURI_API19(this, result).toString();
            binding.ivImage.setImageURI(result)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.apply {
            btnLogout.setOnClickListener {
                //create alerrt dialog
                val builder = AlertDialog.Builder(this@ProfileActivity)
                builder.setTitle("Logout")
                builder.setMessage("Are you sure you want to logout?")
                builder.setPositiveButton("Yes") { _, _ ->
                    viewModel.clearData()
                    startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
                    finish()
                }
                builder.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }

            ivImage.setOnClickListener {
                checkingPermissions()
            }

            btnUpdate.setOnClickListener {
                val email = edtEmail.text.toString()
                val username = edtUsername.text.toString()
                val birthday = edtBirthDate.text.toString()
                val address = edtAddress.text.toString()
                val user = User(
                    email = email, username = username, birthDate = birthday,
                    address = address, id = userId, password = password, profilePicture = realPath
                )

                user.let { it1 -> viewModel.updateUser(it1) }
                user.let { it1 -> viewModel.saveUser(it1, true) }
            }
        }

        observeUserId()
        observeEmailPass()
        observeMessage()
    }

    private fun observeUserId() {
        viewModel.getUserId().observe(this) { id ->
            userId = id
        }
    }

    private fun observeEmailPass() {
        viewModel.getEmailPassword().observe(this) { emailpas ->
            email = emailpas[0]
            password = emailpas[1]
            observeUser(emailpas[0], emailpas[1])
        }
    }

    private fun observeMessage() {
        viewModel.message.observe(this) {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(this, it.data, Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
                is Resource.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun observeUser(email: String, password: String) {
        viewModel.getUser(email, password).observe(this) { response ->
            when (response) {
                is Resource.Success -> {

                    binding.apply {
                        response.data?.apply {
                            edtUsername.setText(username)
                            edtEmail.setText(email)
                            edtBirthDate.setText(birthDate)
                            edtAddress.setText(address)
//                            Glide.with(this@ProfileActivity)
//                                .load(File(Uri.parse(profilePicture).path))
//                                .into(ivImage);
                            if (profilePicture != "") {
                                val imgFile = File(profilePicture)
                                if (imgFile.exists()) {
                                    realPath = profilePicture.toString()
                                    Glide.with(this@ProfileActivity)
                                        .load(File(Uri.parse(profilePicture).path))
                                        .into(ivImage);
//                                    ivImage.setImageURI(Uri.parse(profilePicture))
                                }

                            }
//                            ivImage.setImageURI(Uri.parse(profilePicture))
                        }
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }
    }

    private fun checkingPermissions() {
        if (isGranted(
                this,
                Manifest.permission.CAMERA,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.MANAGE_DOCUMENTS
                ),
                REQUEST_CODE_PERMISSION,
            )
        ) {
            openGallery()
        }
    }

    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int,
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton(
                "App Settings"
            ) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun chooseImageDialog() {
        AlertDialog.Builder(this)
            .setMessage("Pilih Gambar")
            .setPositiveButton("Gallery") { _, _ -> openGallery() }
            .setNegativeButton("Camera") { _, _ -> openCamera() }
            .show()
    }

    private fun openGallery() {
        intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        intent.type = "image/*"
        intent.action = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent.ACTION_OPEN_DOCUMENT
        } else {
            Intent.ACTION_PICK
        }
        intent.action = Intent.ACTION_OPEN_DOCUMENT
        galleryResult.launch("image/*")
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraResult.launch(cameraIntent)
    }

    private fun handleCameraImage(intent: Intent?) {
        val bitmap = intent?.extras?.get("data") as Bitmap
        binding.ivImage.setImageBitmap(bitmap)
    }


}