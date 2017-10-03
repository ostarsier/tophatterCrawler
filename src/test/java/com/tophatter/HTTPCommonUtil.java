package com.tophatter;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;

public class HTTPCommonUtil {

  public static void trustEveryone() {
    try {
      HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
          return true;
        }
      });

      SSLContext context = SSLContext.getInstance("TLS");
      context.init(null, new X509TrustManager[]{new X509TrustManager() {
        public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
          return new X509Certificate[0];
        }
      }}, new SecureRandom());
      HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static Object getHttpHeaders(URL url, int timeout) {
    try {
      trustEveryone();
      Connection conn = HttpConnection.connect(url);
      conn.timeout(timeout);
      conn.header("Accept-Encoding", "gzip,deflate,sdch");
      conn.header("Connection", "close");
      conn.get();
      //String result=conn.response().body();
      Map<String, String> result = conn.response().headers();
      result.put("title", conn.response().parse().title());
      return result;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void main(String[] args) {
    try {
      URL url = new URL("https", "www.tophatter.com/catalogs/9200", -1, "");
      System.out.println(getHttpHeaders(url, 10000));
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }
} 