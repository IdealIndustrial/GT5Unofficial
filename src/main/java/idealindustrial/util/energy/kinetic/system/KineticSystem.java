package idealindustrial.util.energy.kinetic.system;

import idealindustrial.util.energy.kinetic.KUConsumer;
import idealindustrial.util.energy.kinetic.KUProducer;

import java.util.List;

public class KineticSystem {
    KUProducer producer;
    List<KUConsumer> consumers;
    KineticConnection connection;

    public void update() {
        int requestPower = 0;
        int[] requests = new int[consumers.size()];
        for (int i = 0; i < consumers.size(); i++) {
            KUConsumer consumer = consumers.get(i);
            requests[i] = consumer.getPowerUsage();
            requestPower += requests[i];
        }
        if (requestPower == 0) {
            return;
        }
        float powerModification = ((float) producer.getTotalPower()) / requestPower;
        powerModification = Math.min(powerModification, 1f);
        int calculatedSpeed = producer.getSpeed(requestPower);
        for (int i = 0; i < consumers.size(); i++) {
            KUConsumer consumer = consumers.get(i);
            consumer.supply((int) (powerModification * requests[i]), calculatedSpeed);
        }
    }
}
