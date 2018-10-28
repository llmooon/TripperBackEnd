package org.soma.tripper.push;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Service
public class AndroidPushNotificationService {
    private static final String FIREBASE_SERVER_KEY = "AAAAgAjFc4w:APA91bEFk9mgylnvQa0nz8J9zqW90sL5g1_zLNydbUXC3WhCnredV5VtYIKF74iTXaBvQc47k0hA2WCrPaBSKweO1cfBqujXLyn2XHNc0wxaDHDhlhlodoK9QTY20r-g2In_eK22mNHz";
    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

    @Async
    public CompletableFuture<String> send(HttpEntity<String> entity){
        RestTemplate restTemplate = new RestTemplate();

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization","key="+FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("content-Type","application/json"));
        restTemplate.setInterceptors(interceptors);
        String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL,entity,String.class);

        return CompletableFuture.completedFuture(firebaseResponse);
    }
}