package cn.zjh.com.myapplication.networks;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * Created by zhuojh on 2019/1/17.
 */

public class OkHttpManager {
     private static OkHttpManager okHttpManager = null;
    private InputStream mTrustrCertificate;

    public InputStream getmTrustrCertificate() {
        return mTrustrCertificate;
    }

    public void setmTrustrCertificate(InputStream mTrustrCertificate) {
        this.mTrustrCertificate = mTrustrCertificate;
    }


     public static OkHttpManager getOkHttpManagerInstance(){
        if(okHttpManager == null){
            okHttpManager = new OkHttpManager();
        }
        return okHttpManager;
    }

    private KeyStore newEmptyKeyStore(char [] passWord) throws GeneralSecurityException {
        KeyStore keyStore = null;
        try {
             keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            // By convention, 'null' creates an empty key store.
            InputStream in = null;
            keyStore.load(in,passWord);
            return  keyStore;
        } catch (IOException e) {
            new AssertionError(e);
        }
        return keyStore;
    }

    private X509TrustManager trustManagerForCertificates(InputStream in) throws GeneralSecurityException {

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = (Collection<? extends Certificate>) certificateFactory.generateCertificate(in);

        if(certificates.isEmpty()){
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }


        char[] password = "123456".toCharArray();//密码随意，你高兴就好
        KeyStore keyStore = newEmptyKeyStore(password);
        //将证书放在密钥存储区中。
        int index = 0;
        for(Certificate certificate:certificates){
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias,certificate);
        }


        //使用它构建一个X509信任管理器。
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore,password);

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);

        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

        if(trustManagers.length!=1 || !(trustManagers[0] instanceof  TrustManager)){
            throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
        }

         return (X509TrustManager) trustManagers[0];
    }


    public OkHttpClient build(){
         OkHttpClient okHttpClient = null;
         //如果获取证书的流不为空
         if(getmTrustrCertificate()!=null){

             X509TrustManager trustManager;
             SSLSocketFactory sslSocketFactory;
             try {
                 trustManager = trustManagerForCertificates(getmTrustrCertificate());
                 SSLContext sslContext = SSLContext.getInstance("TSL");
                 sslContext.init(null,new TrustManager[]{trustManager},null);

                 sslSocketFactory = sslContext.getSocketFactory();
             } catch (GeneralSecurityException e) {
                 throw new RuntimeException(e);
             }
             okHttpClient = new OkHttpClient.Builder().
                     sslSocketFactory(sslSocketFactory,trustManager).
                     build();
         }else{
             okHttpClient=new OkHttpClient.Builder()
                     .build();
         }
        return okHttpClient;
    }
}
