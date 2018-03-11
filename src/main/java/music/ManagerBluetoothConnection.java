//package music;
//
//import javax.bluetooth.*;
//import java.io.IOException;
//
//public class ManagerBluetoothConnection {
//
//    public boolean tryToConnect(){
//        LocalDevice local = null;
//        try {
//            local = LocalDevice.getLocalDevice();
//            DiscoveryAgent agent = local.getDiscoveryAgent();
//
//            boolean complete = agent.startInquiry(DiscoveryAgent.GIAC, new DiscoveryListener() {
//                public void deviceDiscovered(RemoteDevice device, DeviceClass cod) {
//                    try {
//                        System.out.println("Discovered: " + device.getFriendlyName(true));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void servicesDiscovered(int i, ServiceRecord[] serviceRecords) {
//                    System.out.println("servicesDiscovered");
//
//                }
//
//                @Override
//                public void serviceSearchCompleted(int i, int i1) {
//                    System.out.println("serviceSearchCompleted");
//
//                }
//
//                @Override
//                public void inquiryCompleted(int i) {
//                    System.out.println("inquiryCompleted");
//
//                }
//            });
//            while(!complete) {
//                System.out.println("while(!complete)");
//                // wait until discovery completes before continuing
//            }
//            System.out.println("while(!complete)");
//        } catch (BluetoothStateException e) {
//            e.printStackTrace();
//        }
//
//
//        return false;
//    }
//}
