package br.com.amsj.groovydemo

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager
import java.security.cert.CertificateException
import java.security.cert.X509Certificate;

class SslConnectionInsecure {

    static void main(def args) throws Exception {

        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new BlindTrustManager();


        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        URL url = new URL("https://google.com.br");
        URLConnection con = url.openConnection();
        Reader reader = new InputStreamReader(con.getInputStream());
        while (true) {
            int ch = reader.read();
            if (ch==-1) {
                break;
            }
            System.out.print((char)ch);
        }
    }
}

class BlindTrustManager implements X509TrustManager {

    X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }
}
