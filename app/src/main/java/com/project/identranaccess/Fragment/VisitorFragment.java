package com.project.identranaccess.Fragment;

import static android.content.ContentValues.TAG;
import static android.content.Context.WINDOW_SERVICE;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.identranaccess.Activity.QRPageActivity;
import com.project.identranaccess.R;
import com.project.identranaccess.database.MyLocalDatabase;
import com.project.identranaccess.database.RetrofitClient;
import com.project.identranaccess.database.Utility;
import com.project.identranaccess.databinding.FragmentVisitorBinding;
import com.project.identranaccess.model.FavData;
import com.project.identranaccess.model.QRClass;
import com.google.gson.Gson;
import com.project.identranaccess.model.VisitorData;

import java.util.ArrayList;
import java.util.Calendar;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VisitorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VisitorFragment extends Fragment {
    FragmentVisitorBinding binding;
    public MyLocalDatabase myLocalDatabase;
    Context mContext;
    int i = 0;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<QRClass> myheroList;
    ArrayList<QRClass> temp;
    QRGEncoder qrgEncoder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    boolean clicked = true;
    boolean value = true;
    FirebaseFirestore db;


    public VisitorFragment() {

    }

    public static VisitorFragment newInstance(String param1, String param2) {
        VisitorFragment fragment = new VisitorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVisitorBinding.inflate(inflater, container, false);
        mContext = getActivity();

        myLocalDatabase = new MyLocalDatabase(mContext);
        db =  FirebaseFirestore.getInstance();

        callQrApi();

        Bundle bundle_data = getArguments();
        if (bundle_data != null) {
            String NameVT = bundle_data.getString("NAME");
            String LastNameVT = bundle_data.getString("LASTNAME");
            String VisitReasonVT = bundle_data.getString("VISITREASON");
            String DateVT = bundle_data.getString("VISITDATE");
            String commentTV = bundle_data.getString("COMMENT");
            String CODETV = bundle_data.getString("CODE");
            String code = bundle_data.getString("FirstCODE");

            Log.e("myApp", "onCreateView: " + NameVT + LastNameVT + VisitReasonVT + DateVT + commentTV + CODETV+code);
            binding.fullNameET.setText(NameVT);
            binding.lastNameET.setText(LastNameVT);
            binding.reasonForET.setText(VisitReasonVT);
            binding.visitDateET.setText(DateVT);
            binding.CommentET.setText(commentTV);
            binding.textUser.setText("DETALLES DEL REGISTRO");


            binding.completeRegBtn.setVisibility(View.GONE);
            binding.swOnOff.setVisibility(View.GONE);

            binding.fullNameET.setEnabled(false);
            binding.lastNameET.setEnabled(false);
            binding.reasonForET.setEnabled(false);
            binding.visitDateET.setEnabled(false);
            binding.CommentET.setEnabled(false);

            qrGenerateMethod(CODETV);

            if(code!=null){
                binding.fullNameET.setText(NameVT);
                binding.lastNameET.setText(LastNameVT);
                binding.reasonForET.setText(VisitReasonVT);
                binding.visitDateET.setText(DateVT);
                binding.CommentET.setText(commentTV);
                binding.reasonForET.setEnabled(true);
                binding.visitDateET.setEnabled(true);
                binding.completeRegBtn.setVisibility(View.VISIBLE);
                binding.QRImage.setVisibility(View.GONE);
                binding.swOnOff.setVisibility(View.GONE);
                binding.swOnOff.setVisibility(View.GONE);
            }
        }

        init();

        binding.crossIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                HomeFragment llf = new HomeFragment();
                ft.replace(R.id.fragment_container, llf);
                ft.commit();
            }
        });

        binding.swOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();

                if (binding.fullNameET.getText().toString().isEmpty()) {
                    binding.fullNameET.setError("por favor ingrese el nombre");
                    binding.fullNameET.requestFocus();
                } else if (binding.lastNameET.getText().toString().isEmpty()) {
                    binding.lastNameET.setError("por favor ingresa el apellido");
                    binding.lastNameET.requestFocus();
                }
                else if (binding.reasonForET.getText().toString().isEmpty()) {
                    binding.reasonForET.setError("Por favor ingrese el motivo de la visita");
                    binding.reasonForET.requestFocus();
                }
                else if (binding.visitDateET.getText().toString().isEmpty()) {
                    binding.visitDateET.setError("Por favor ingrese la fecha de visit");
                    binding.visitDateET.requestFocus();
                }
                else if (binding.CommentET.getText().toString().isEmpty()) {
                    binding.CommentET.setError("Por favor ingrese el comentario");
                    binding.CommentET.requestFocus();
                } else {
                    if (clicked) {
                        binding.swOnOff.setImageResource(R.drawable.heart);
                        clicked = true;

                        if (Utility.getPreferences(requireActivity(), "QrCodeId", 0) == 0) {
                            Log.e("Cibstsjgbksgbksbgsb ", "iffffffffffffff");

                            i = Utility.getPreferences(requireActivity(), "QrCodeId", 0) + 1;

                            if (Utility.getPreferences(requireActivity(), "QrCodeId", 0) <= myheroList.size()) {
                                Intent intent = new Intent(getActivity().getApplicationContext(), QRPageActivity.class);
                                intent.putExtra("indexno", myheroList.get(0).getCode());
                                startActivity(intent);

                                addDatatoFirebase(binding.fullNameET.getText().toString().trim(), binding.lastNameET.getText().toString().trim(), binding.visitDateET.getText().toString().trim()
                                        , binding.reasonForET.getText().toString().trim(), binding.CommentET.getText().toString().trim(), myheroList.get(0).getCode(),ts);

                                addfavListData(binding.fullNameET.getText().toString().trim(), binding.lastNameET.getText().toString().trim(),
                                        binding.reasonForET.getText().toString().trim(), binding.visitDateET.getText().toString().trim(), binding.CommentET.getText().toString().trim(), myheroList.get(0).getCode(),ts);
                            }

                            saveMethod(i);
                            setnullText();
                        } else {
                            Log.e("Cibstsjgbksgbksbgsb ", "elseeeeeeeeeeeeeeeeeee"+myheroList.size());

                            i = Utility.getPreferences(requireActivity(), "QrCodeId", 0) + 1;
                            if (Utility.getPreferences(requireActivity(), "QrCodeId", 0) <= myheroList.size()) {
                                Intent intent = new Intent(getActivity().getApplicationContext(), QRPageActivity.class);
                                intent.putExtra("indexno", myheroList.get(i).getCode());
                                startActivity(intent);

                                addDatatoFirebase(binding.fullNameET.getText().toString().trim(), binding.lastNameET.getText().toString().trim(), binding.visitDateET.getText().toString().trim()
                                        , binding.reasonForET.getText().toString().trim(), binding.CommentET.getText().toString().trim(), myheroList.get(i).getCode(),ts);

                                addfavListData(binding.fullNameET.getText().toString().trim(), binding.lastNameET.getText().toString().trim(),
                                        binding.reasonForET.getText().toString().trim(), binding.visitDateET.getText().toString().trim(), binding.CommentET.getText().toString().trim(), myheroList.get(i).getCode(),ts);
                            }

                            Log.e("sgcvecgertcewtw ", i + "");
                            Log.e("cwfwefwcxwcr", myheroList.get(i).getCode() + "");
                            saveMethod(i);

                            setnullText();
                        }
                    } else {
                        binding.swOnOff.setImageResource(R.drawable.like_button);
                    }
                }
            }
        });

        binding.visitDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        mContext,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                binding.visitDateET.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        binding.completeRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();

                if (binding.fullNameET.getText().toString().isEmpty()) {
                    binding.fullNameET.setError("por favor ingrese el nombre");
                    binding.fullNameET.requestFocus();
                } else if (binding.lastNameET.getText().toString().isEmpty()) {
                    binding.lastNameET.setError("por favor ingresa el apellido");
                    binding.lastNameET.requestFocus();
                }
                /*else if (binding.reasonForET.getText().toString().isEmpty()) {
                    binding.reasonForET.setError("Por favor ingrese el motivo de la visita");
                    binding.reasonForET.requestFocus();
                }*/ else if (binding.visitDateET.getText().toString().isEmpty()) {
                    binding.visitDateET.setError("Por favor ingrese la fecha de visit");
                    binding.visitDateET.requestFocus();
                }
                /*else if (binding.CommentET.getText().toString().isEmpty()) {
                    binding.CommentET.setError("Por favor ingrese el comentario");
                    binding.CommentET.requestFocus();
                }*/ else {

                    if (Utility.getPreferences(requireActivity(), "QrCodeId", 0) == 0) {
                        Log.e("Cibstsjgbksgbksbgsb ", "iffffffffffffff");

                        i = Utility.getPreferences(requireActivity(), "QrCodeId", 0) + 1;

                        if (Utility.getPreferences(requireActivity(), "QrCodeId", 0) <= myheroList.size()) {
                            Intent intent = new Intent(getActivity().getApplicationContext(), QRPageActivity.class);
                            intent.putExtra("indexno", myheroList.get(0).getCode());
                            startActivity(intent);

                            addDatatoFirebase(binding.fullNameET.getText().toString().trim(), binding.lastNameET.getText().toString().trim(), binding.visitDateET.getText().toString().trim()
                                    , binding.reasonForET.getText().toString().trim(), binding.CommentET.getText().toString().trim(), myheroList.get(0).getCode(),ts);

                        }

                        saveMethod(i);
                        setnullText();

                    } else {
                        Log.e("Cibstsjgbksgbksbgsb ", "elseeeeeeeeeeeeeeeeeee");

                        i = Utility.getPreferences(requireActivity(), "QrCodeId", 0) + 1;
                        if (Utility.getPreferences(requireActivity(), "QrCodeId", 0) <= myheroList.size()) {
                            Intent intent = new Intent(getActivity().getApplicationContext(), QRPageActivity.class);
                            intent.putExtra("indexno", myheroList.get(i).getCode());
                            startActivity(intent);

                            addDatatoFirebase(binding.fullNameET.getText().toString().trim(), binding.lastNameET.getText().toString().trim(), binding.visitDateET.getText().toString().trim()
                                    , binding.reasonForET.getText().toString().trim(), binding.CommentET.getText().toString().trim(), myheroList.get(i).getCode(),ts);     }

                        Log.e("sgcvecgertcewtw ", i + "");
                        Log.e("cwfwefwcxwcr", myheroList.get(i).getCode() + "");
                        saveMethod(i);
                        setnullText();
                    }
                }
            }
        });

        return binding.getRoot();
    }

    private void addfavListData(String name, String lastname, String visitdata, String reason, String comment, String code, String ts) {
        CollectionReference dbCourses = db.collection("favouriteUserList");

        FavData favdata = new FavData( name,  lastname,  visitdata,  reason,  comment,  code,ts);
        dbCourses.add(favdata).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
        });

    }

    private void addDatatoFirebase(String name, String lastname, String visitData, String reasonofvisit, String comment, String code, String ts) {

        CollectionReference dbCourses = db.collection("createNewUser");

        // adding our data to our courses object class.
        VisitorData dataModel = new VisitorData( name,  lastname,  visitData,  reasonofvisit,  comment,  code,ts);

        dbCourses.add(dataModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
        });


    }

    public void setnullText() {
        binding.fullNameET.setText(null);
        binding.lastNameET.setText(null);
        binding.visitDateET.setText(null);
        binding.reasonForET.setText(null);
        binding.CommentET.setText(null);
    }


    public void callQrApi() {
        Call<ArrayList<QRClass>> call = RetrofitClient.getInstance().getMyApi().getAllData();
        call.enqueue(new Callback<ArrayList<QRClass>>() {
            @Override
            public void onResponse(Call<ArrayList<QRClass>> call, Response<ArrayList<QRClass>> response) {
                myheroList = response.body();
                Gson gson = new Gson();
             /*         json = gson.toJson(myheroList);
                   Log.v("userListData", json);

                    qr_codes = new String[myheroList.size()];

              for ( int i = 0; i < myheroList.size();i++ ) {
                    codelist.add(myheroList.get(i));
                    qr_codes[i] = String.valueOf(myheroList.get(i).getCode());

                    Random random = new Random();
                    int randomIndex = random.nextInt(myheroList.size());
                    int randomNumber = Integer.parseInt(myheroList.get(randomIndex).getCode());

                 //   qrGenerateMethod(qr_codes[i]);

                    // Toast.makeText(QRPageActivity.this, qr_codes[j], Toast.LENGTH_SHORT).show();
                    Log.v("userList", qr_codes[i]);
                }*/
            }

            @Override
            public void onFailure(Call<ArrayList<QRClass>> call, Throwable t) {
                //   Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void saveMethod(int index) {
        Utility.addPreferences(requireActivity(), "QrCodeId", index);
    }

    public void qrGenerateMethod(String qr_code) {

        Log.d("arraylist", "qrGenerateMethod: " + qr_code);
        //    Toast.makeText(this, qr_code, Toast.LENGTH_SHORT).show();
        WindowManager manager = (WindowManager) mContext.getSystemService(WINDOW_SERVICE);

        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        int width = point.x;
        int height = point.y;
        // generating dimension from width and height.
        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;

        qrgEncoder = new QRGEncoder(qr_code, null, QRGContents.Type.TEXT, dimen);
        Bitmap bitmap = qrgEncoder.getBitmap();
        binding.QRImage.setImageBitmap(bitmap);
    }


    private void init() {
        //  mContext = getContext();
        binding.fullNameET.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        binding.lastNameET.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        binding.reasonForET.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        binding.CommentET.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

    }
}