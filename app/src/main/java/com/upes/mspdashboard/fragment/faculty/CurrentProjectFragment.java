package com.upes.mspdashboard.fragment.faculty;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.upes.mspdashboard.R;
import com.upes.mspdashboard.util.GlobalConstants;
import com.upes.mspdashboard.util.SessionManager;
import com.upes.mspdashboard.util.Utility;
import com.upes.mspdashboard.util.retrofit.RetrofitApiClient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentProjectFragment extends Fragment {
    public static final String TAG = "CurrentProjectFragment";
    private static final int RC_EXCEL_FILE_CREATION = 1;
    private OnFragmentInteractionListener mListener;
    private Toolbar toolbar;

    public CurrentProjectFragment() {
        // Required empty public constructor
    }

    public static CurrentProjectFragment newInstance() {
        return new CurrentProjectFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fac_current_project, container, false);
        toolbar = view.findViewById(R.id.toolbar_fac_current_project);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.abm_frag_fac_current_project,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if(item.getItemId()==R.id.action_download_list) {
            requestFileCreation("current_projects.csv",GlobalConstants.MIME_TYPE_CSV);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == RC_EXCEL_FILE_CREATION) {
            if(resultCode== Activity.RESULT_OK) {
                Uri excelFileuri = data.getData();
                downloadExcelFile(excelFileuri);
            } else {
                Log.e(TAG,"Document Creation Failed");
            }
        }
    }

    private void downloadExcelFile(final Uri excelFileuri) {
        int fac_id = SessionManager.getInstance(this.getContext())
                .getUser().getUserId();
        Log.i(TAG,"fac_id: "+fac_id);
        RetrofitApiClient.getInstance().getDataClient()
                .downloadFile(Utility.authHeader(this.getContext()),fac_id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response!=null) {
                            writeContentToDisk(response.body(),excelFileuri);
                        } else {
                            Log.e(TAG,"Response is null");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        Log.e(TAG,"Failed to download file!");
                    }
                });
    }

    private void writeContentToDisk(ResponseBody body,Uri outUri) {
        try(OutputStream os = getActivity().getContentResolver().openOutputStream(outUri);
            InputStream is = body.byteStream()){
            int b;
            while((b=is.read())!=-1) {
                os.write(b);
                Log.i(TAG,"written : "+b);
            }
            Log.i(TAG,"Written bytes to disk");
            mListener.onExcelFileDownload(true,"Download successful!");
        } catch(FileNotFoundException fnfE) {
            fnfE.printStackTrace();
            Log.e(TAG,"File not found!");
            mListener.onExcelFileDownload(false,"File not found!");
        } catch (IOException ioE) {
            ioE.printStackTrace();
            mListener.onExcelFileDownload(false,"IO Exception");
        } catch (Exception e) {
            e.printStackTrace();
            mListener.onExcelFileDownload(false,"Exception'");
        }
    }

    void requestFileCreation(String fileName, String mimeType) {
        Intent fileIntent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        fileIntent.addCategory(Intent.CATEGORY_OPENABLE);
        fileIntent.setType(mimeType);
        fileIntent.putExtra(Intent.EXTRA_TITLE,fileName);
        startActivityForResult(fileIntent, RC_EXCEL_FILE_CREATION);
    }

    public interface OnFragmentInteractionListener {
        void onExcelFileDownload(boolean success,String msg);
    }
}
