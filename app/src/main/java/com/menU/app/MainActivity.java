package com.menU.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int MIC_PERMISSION_CODE = 1001;

    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);

        requestMicrophonePermission();

        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setDomStorageEnabled(true);

        webView.getSettings().setDatabaseEnabled(true);

        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);

        webView.getSettings().setAllowFileAccess(false);

        webView.getSettings().setAllowContentAccess(false);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view,
                    WebResourceRequest request) {

                return false;
            }

        });

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onPermissionRequest(
                    final PermissionRequest request) {

                runOnUiThread(() -> {

                    String[] resources =
                            request.getResources();

                    for (String resource : resources) {

                        if (PermissionRequest.RESOURCE_AUDIO_CAPTURE
                                .equals(resource)) {

                            request.grant(
                                    new String[]{
                                            PermissionRequest
                                                    .RESOURCE_AUDIO_CAPTURE
                                    }
                            );

                            return;
                        }
                    }

                    request.deny();
                });
            }
        });

        webView.loadUrl(
                "https://divinegamingblogspot-dot.github.io/me-n-u-voice/"
        );
    }

    private void requestMicrophonePermission() {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
        ) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.RECORD_AUDIO
                    },
                    MIC_PERMISSION_CODE
            );
        }
    }

    @Override
    public void onBackPressed() {

        if (webView.canGoBack()) {

            webView.goBack();

        } else {

            super.onBackPressed();

        }
    }

    @Override
    protected void onDestroy() {

        if (webView != null) {

            webView.destroy();

        }

        super.onDestroy();
    }
}
