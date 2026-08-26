package com.menU.app;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private WebView webView;

    private static final int MIC_PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);

        setupWebView();

        requestMicrophonePermission();

        webView.loadUrl(
            "https://divinegamingblogspot-dot.github.io/me-n-u-voice/"
        );
    }

    private void setupWebView() {

        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setDomStorageEnabled(true);

        webView.getSettings().setDatabaseEnabled(true);

        webView.getSettings()
                .setMediaPlaybackRequiresUserGesture(false);

        webView.getSettings().setAllowFileAccess(false);

        webView.getSettings().setAllowContentAccess(false);

        webView.setWebViewClient(
            new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(
                        WebView view,
                        WebResourceRequest request) {

                    return false;
                }
            }
        );

        webView.setWebChromeClient(
            new WebChromeClient() {

                @Override
                public void onPermissionRequest(
                        final PermissionRequest request) {

                    runOnUiThread(() -> {

                        if (
                            checkSelfPermission(
                                Manifest.permission.RECORD_AUDIO
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {

                            request.grant(
                                new String[]{
                                    PermissionRequest
                                        .RESOURCE_AUDIO_CAPTURE
                                }
                            );

                        } else {

                            request.deny();

                        }
                    });
                }
            }
        );
    }

    private void requestMicrophonePermission() {

        if (
            checkSelfPermission(
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            requestPermissions(
                new String[]{
                    Manifest.permission.RECORD_AUDIO
                },
                MIC_PERMISSION_CODE
            );
        }
    }

    @Override
    public void onBackPressed() {

        if (
            webView != null &&
            webView.canGoBack()
        ) {

            webView.goBack();

        } else {

            super.onBackPressed();

        }
    }

    @Override
    protected void onDestroy() {

        if (webView != null) {

            webView.destroy();

            webView = null;
        }

        super.onDestroy();
    }
}
