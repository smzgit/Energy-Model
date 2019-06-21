import org.arl.unet.*
import org.arl.unet.phy.*
import org.arl.fjage.Message

// documentation for the 'transmit' command/*
doc['transmit'] = '''transmit - transmit data to a remote node

Examples:
  transmit 2,[1,2,3]             //transmit data [1,2,3] to node-2
  
'''
subscribe phy
transmit = { addr, data ->
    println "TRANSMIT $addr, $data"
    
    println  phy << new DatagramReq(to: addr, data: data)
    
    println 'Address :'+addr+'\nData :'+data
    
    def txNtf = receive(TxFrameNtf, 1000)
   
    System.out.println txNtf    
    txtime =    txNtf.getTxTime()        
    File file = new File("I:\\out.txt")
    file.write ""+txtime
 }
 
