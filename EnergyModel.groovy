import org.arl.fjage.*
import org.arl.unet.*
import org.arl.unet.phy.*
import java.math.*

class EnergyModel extends UnetAgent {

    int neighbor, addr
    float neighbor_distance;
    def ranging
    def  init_energy = 10
    def dist
    def data
    def depth
    def C = 1.3312e-9       // empirical constant
    static def Tot_bits
    def fr = 10           //carrier freq.(Khz)
    def d = 0.036*Math.pow(fr,1.5)    //Thorp's constant
    static def source
   
   public void startup() {
      AgentID phy = agentForService(Services.PHYSICAL);
      subscribe (topic(phy));

      ranging = agentForService Services.RANGING;
      subscribe topic(ranging);

      def nodeInfo = agentForService Services.NODE_INFO;
      addr = nodeInfo.address;
     
      depth = nodeInfo.location[2]        //fetching depth of the current node in meters
     }

   void processMessage(Message msg) {
             
      if (msg instanceof RxFrameNtf && msg.protocol == Protocol.DATA){
           data = msg.getData()       // getting data
           System.out.println "\tData is :"+data
           def bits=32
           Tot_bits = bits*data.size()      //caculating total number of bits
           System.out.println "\tNumber of bits received :"+Tot_bits
    
           BigDecimal Rx_EG = new BigDecimal("1"); // Or BigInteger.ONE 
          
           Rx_EG = Rx_EG.multiply(BigDecimal.valueOf(Tot_bits*50e-9));        // calculating receiving energy
           init_energy = init_energy - Rx_EG ;                                // deducting Rx_EG from initial energy i.e., 10 Joules
           String value = String.valueOf(Rx_EG.doubleValue());
           System.out.println '\n\tReception Energy : '+value+" Joules";
           System.out.println '\tRemaining Energy : '+(init_energy)+" Joules\n";
           System.out.println '\tTime : '+msg.getRxTime()
           System.out.println '\tNode ID : '+msg.getTo()
           System.out.println "ENERGY: -t <"+msg.getRxTime()+"> -i <"+msg.getTo()+"> -d <"+Tot_bits+"> -e <"+init_energy+">   R"
           println "ENERGY: -t <"+msg.getRxTime()+"> -i <"+msg.getTo()+"> -d <"+Tot_bits+"> -e <"+init_energy+">   R"
           
        
      }
      if (msg instanceof DatagramNtf && msg.protocol == Protocol.DATA) {     
        neighbor = msg.from;
        source = msg.from;
        data = msg.getData()
        //System.out.println " Beacon received from :" +neighbor+'  data is '+data
        def bits=32
        Tot_bits = bits*data.size()
           
        System.out.println "\tNumber of bits sent :"+Tot_bits
        
        ranging << new RangeReq(to: neighbor);
      }
       
        else if (msg instanceof RangeNtf ) {
             
           neighbor_distance = msg.getRange();          // getting distance in meters
         
        if(addr==source){
           
           dist = neighbor_distance/1000.0      // converting the distance in Km.
           System.out.println '\tDepth(Km.) = '+depth*(-0.001)+'\n\tDistance(Km.) = '+dist
           BigDecimal Tx_EG = new BigDecimal("1"); // Or BigInteger.ONE 
          
           Tx_EG = Tx_EG.multiply(BigDecimal.valueOf(Tot_bits*50e-9+ Tot_bits*(0.001)*dist*(depth*-0.001)*C*Math.pow(Math.E,d*dist))); 
           init_energy = init_energy - Tx_EG ;
           String value = String.valueOf(Tx_EG.doubleValue());
           System.out.println '\n\tTransmission Energy : '+value+" Joules";
           System.out.println '\tRemaining Energy : '+(init_energy)+" Joules\n";
          
           File file = new File("I:\\out.txt")
           def text = file.getText()
          
           System.out.println "ENERGY: -t <"+text+"> -i <"+msg.getTo()+"> -d <"+Tot_bits+"> -e <"+init_energy+">   T"
           println "ENERGY: -t <"+text+"> -i <"+source+"> -d <"+Tot_bits+"> -e <"+init_energy+">   T"
           }     
                           
       }

   }

   void setup() {
           
  }

}