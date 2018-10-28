package org.soma.tripper.controller;

import com.fasterxml.jackson.core.io.JsonEOFException;
import org.json.simple.JSONObject;
import org.soma.tripper.push.AndroidPushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/push")
public class PushController {

    private final String TOPIC = "JavaSampleApproch";

    @Autowired
    AndroidPushNotificationService androidPushNotificationService;

    @RequestMapping(value="/send",method= RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> send()throws Exception{
        JSONObject body = new JSONObject();
        body.put("to","/topics/"+TOPIC);
        body.put("priority","high");

        JSONObject notification = new JSONObject();
        notification.put("title","test");
        notification.put("body","push test");

        JSONObject data = new JSONObject();
        data.put("data","data");

        body.put("notification",notification);
        body.put("data",data);

        HttpEntity<String> request = new HttpEntity<>(body.toString());
        CompletableFuture<String> pushNotification = androidPushNotificationService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try{
            String firebaseResponse = pushNotification.get();
            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        catch (ExecutionException e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Push Notification Error",HttpStatus.BAD_REQUEST);
    }
}