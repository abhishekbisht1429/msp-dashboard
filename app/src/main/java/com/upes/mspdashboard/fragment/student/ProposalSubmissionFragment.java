package com.upes.mspdashboard.fragment.student;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.upes.mspdashboard.R;
import com.upes.mspdashboard.model.Faculty;
import com.upes.mspdashboard.model.Student;
import com.upes.mspdashboard.util.GlobalConstants;
import com.upes.mspdashboard.util.SessionManager;
import com.upes.mspdashboard.util.Utility;
import com.upes.mspdashboard.util.retrofit.model.request.ProposalSubmissionRequest;

import static com.upes.mspdashboard.util.GlobalConstants.FACULTY_PARCEL_KEY;
import static com.upes.mspdashboard.util.GlobalConstants.SET_TOOLBAR_AS_ACTIONBAR;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProposalSubmissionFragment extends Fragment {
    private static final String TAG = "ProposalSubFragment";
    private static final int PROPOSAL_FILE_REQUEST_CODE = 1;
    private OnFragmentInteractionListener mListener;
    private Faculty faculty;
    private Button btnSubmit;
    private Button btnBrowse;
    private TextInputLayout tilTitle;
    private TextInputLayout tilDesc;
    private Uri proposalUri;
    private boolean setToolbarAsActionbar;
    private Toolbar toolbar;
    private TextInputLayout tilStu2;
    private TextInputLayout tilStu3;
    private TextView tvFileName;

    public ProposalSubmissionFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Faculty faculty) {
        Fragment fragment = new ProposalSubmissionFragment();
        Bundle args = new Bundle();
        args.putParcelable(FACULTY_PARCEL_KEY,faculty);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        faculty = args.getParcelable(FACULTY_PARCEL_KEY);
        setToolbarAsActionbar = args.getBoolean(SET_TOOLBAR_AS_ACTIONBAR);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        faculty = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_proposal_submission, container, false);
        toolbar = view.findViewById(R.id.toolbar_stu_prop_sub);
        btnSubmit = view.findViewById(R.id.btn_proposal_submit);
        btnBrowse = view.findViewById(R.id.btn_prop_sub_browse);
        tilTitle = view.findViewById(R.id.til_stu_prop_sub_title);
        tilDesc = view.findViewById(R.id.til_stu_prop_sub_desc);
        tilStu2 = view.findViewById(R.id.til_stu_prop_sub_mem2);
        tilStu3 = view.findViewById(R.id.til_stu_prop_sub_mem3);
        tvFileName = view.findViewById(R.id.txtv_stu_prop_sub_filename);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student stu = (Student) SessionManager.getInstance(ProposalSubmissionFragment.this.getContext())
                        .getUser();
                ProposalSubmissionRequest propReq = new ProposalSubmissionRequest();
                propReq.setMember1(stu);
                propReq.setMember2Sap(tilStu2.getEditText().getText().toString());
                propReq.setMember3Sap(tilStu3.getEditText().getText().toString());
                propReq.setMentor(faculty);
                propReq.setTitle(tilTitle.getEditText().getText().toString());
                propReq.setDescription(tilDesc.getEditText().getText().toString());
                propReq.setProposalUri(proposalUri);
                mListener.onClickProposalSubmit(propReq);
            }
        });
        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if storage permission is granted
                if(Utility.checkExtStorageWritePermission(getContext())) {
                    Log.i(TAG,"storage permission available");
                    chooseFile();

                } else { //request storage permission
                    Log.i(TAG,"requesting storage permission");
                    Utility.requestExtStorageWritePermission(ProposalSubmissionFragment.this);
                }
            }
        });
        if(setToolbarAsActionbar) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return view;
    }

    private void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent.createChooser(intent, "Choose a file to submit"),
                PROPOSAL_FILE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PROPOSAL_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            proposalUri = data.getData();
            Cursor cursor = getContext().getContentResolver().query(proposalUri,null,null,null,null);
            String cols[] = cursor.getColumnNames();
            for(int i=0;i<cols.length;++i) {
                Log.i(TAG,cols[i]);
            }
            Log.i(TAG, MediaStore.MediaColumns.DISPLAY_NAME);
            Log.i(TAG,cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME)+"");
            cursor.moveToNext();
            String fileName = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME));
            tvFileName.setText(fileName);
        } else {
            Log.e(TAG,"failed to fetch document");
        }
    }

    public interface OnFragmentInteractionListener {
        void onClickProposalSubmit(ProposalSubmissionRequest proposalReq);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i(TAG,"on request permission result");
        if(requestCode== GlobalConstants.RC_WRITE_EXTERNAL_STORAGE) {
            if(grantResults[0] == (PackageManager.PERMISSION_GRANTED)) {
                chooseFile();
            } else {
                Log.i(TAG,"Permission denied by the user");

            }
        }
    }
}
