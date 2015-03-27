package net.haviss;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by H�vard on 3/27/2015.
 */
public class havissIoTClient extends messageGenerator {
    private MqttClient mclient;
    private String brokerAddress = "tcp://";
    private int brokerPort = 1883; //Default port is used if not else specified in connect
    private String clientID;
    private MemoryPersistence persistence = new MemoryPersistence();
    private int qos = 2;

    public havissIoTClient(String ID) { //Class constructor
        clientID = ID;
    }
    public void connect(String address) {
        brokerAddress += (address + ":" + Integer.toString(brokerPort));
        try {
            mclient = new MqttClient(brokerAddress, clientID, persistence); //Setting up MQTT client and connect to broker
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            mclient.connect(connOpts);
        } catch (MqttException me) {
            //TODO: Handle exception
        }
    }
    //Overloaded for use with other ports than default
    public void connect(String address, int port) {
        brokerPort = port;
        brokerAddress += (address + ":" + Integer.toString(brokerPort));
        try {
            mclient = new MqttClient(brokerAddress, clientID, persistence); //Setting up MQTT client and connect to broker
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            mclient.connect(connOpts); //Connecting to broker
        } catch (MqttException me) {
            //TODO: Handle exception
        }
    }
    public void publishMessage(String topic, String message) { //Publish message to topic
        try {
            MqttMessage pubMessage = new MqttMessage(message.getBytes());
            mclient.publish(topic, pubMessage);
        } catch (MqttException me) {
            //TODO: Handle exception
        }
    }
    public void subscribeToTopic(String topic, int qos) {
        try {
            mclient.subscribe(topic, qos);
        } catch (MqttException me) {
            //TODO: Handle exceptions
        }
    }

}
